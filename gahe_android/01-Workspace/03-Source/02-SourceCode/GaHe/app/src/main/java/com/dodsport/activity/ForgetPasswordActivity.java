package com.dodsport.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.PhoneFormatCheckUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.authcode.AuthCode;
import com.dodsport.utils.authcode.CodeConfig;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {

    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.btcode)
    Button mBtcode;
    @Bind(R.id.account)
    EditText mAccount;
    @Bind(R.id.evCode)
    EditText mEvCode;
    @Bind(R.id.Pheno)
    TextView mPheno;
    @Bind(R.id.btNextStep)
    Button mBtNextStep;



    private final static int CODE_REPWAT_SECONDS = 60;
    private Timer time;
    private TimerTask task;
    private int currentSecond;
    private int REQUEST_PERMISSION_CODE = 0;
    private String Code;
    private String Phone;
    private String TAG = "******";
    //RegisterErrorPage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        handlePermission();
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("忘记密码");
        mHeadIvBack.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.head_ivBack, R.id.btcode, R.id.btNextStep})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:      //返回
                finish();
                break;
            case R.id.btcode:           //发送验证码
                try {
                    boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
                    if (phoneLegal == true) {
                        getCode();     //发送请求
                        startTimer();

                    } else {
                        showToast(R.string.telphone_error);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btNextStep:       //下一步(设置登录密码)
                boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
                if (phoneLegal && !TextUtils.isEmpty(mEvCode.getText().toString())){
                    Code = mEvCode.getText().toString();
                    Phone = mAccount.getText().toString();
                    String operationId = "2";//表示忘记密码
                    //验证码验证
                    PunchTheClockRequest.codeValidate(Phone, Code,operationId, new OnResponseListener<String>() {
                        @Override
                        public void onStart(int what) {

                        }

                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            Log.i(TAG, "忘记密码 验证码校对---->"+response.get().toString()+"");
                            StatusBean statusBean = JSON.parseObject(response.get(),StatusBean.class);
                            ResultBean resultBean = JSON.parseObject(statusBean.getResult(),ResultBean.class);
                            if (!resultBean.getCode().equals("0")){
                                switch (resultBean.getCode()){
                                    case "5002":
                                        ToastUtils.showToCenters(ForgetPasswordActivity.this,"该用户不存在！",800);
                                        break;
                                    case "5001":
                                        ToastUtils.showToCenters(ForgetPasswordActivity.this,"验证码错误！",800);
                                        break;
                                    default:
                                        break;
                                }
                                return;
                            }
                            Intent intent = new Intent(ForgetPasswordActivity.this,InTeRcaLatePasswordActivity.class);
                            intent.putExtra("phone",Phone);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailed(int what, Response<String> response) {

                        }

                        @Override
                        public void onFinish(int what) {

                        }
                    });

                }else if (!phoneLegal){
                    ToastUtils.showToCenters(this,"请输入正确的手机号！",800);
                }else if (TextUtils.isEmpty(mEvCode.getText().toString())){
                    ToastUtils.showToCenters(this,"请输入验证码！",800);
                }

                break;
            default:
                break;
        }
    }

    //启动验证码定时器
    private void startTimer() {
        mBtcode.setBackgroundDrawable(getResources().getDrawable(R.drawable.bind_email_yzm));
        time = new Timer();
        mBtcode.setEnabled(false);
        currentSecond = CODE_REPWAT_SECONDS;
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentSecond--;
                        mBtcode.setEnabled(false);
                        String value = String.format(getString(R.string.resend_code), currentSecond);
                        if (currentSecond == 0) {
                            mBtcode.setEnabled(true);
                            time.cancel();
                            time = null;
                            task.cancel();
                            task = null;
                            mBtcode.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_reg_yzm_blue));
                            value = getString(R.string.request_code);
                        }
                        mBtcode.setText(value);
                    }
                });
            }
        };
        time.schedule(task, 0, 1000);
    }

    /**
     * 获取验证码
     */
    public void getCode() {

        //显示手机号码
        mPheno.setVisibility(View.VISIBLE);
        String phone = mAccount.getText().toString();
        String substring = phone.substring(0, 3);
        String substring1 = phone.substring(3, 7);
        String substring2 = phone.substring(7, 11);
        String text = substring + "-" + substring1 + "-" + substring2;
        mPheno.setText("验证码已发送至手机: "+text+"");
        String operationId = "2";//表示忘记密码
        PunchTheClockRequest.getCode(mAccount.getText().toString(),operationId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "onSucceed: ----->"+response.get().toString()+"");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().toString().equals("0")){
                    switch (resultBean.getCode().toString()){

                        case "5004":

                            break;
                        default:
                            break;
                    }
                    return;
                }


            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });


        /**自动获取验证码*/
        int parseInt = Integer.parseInt(substring);
        CodeConfig config = new CodeConfig.Builder()
                .codeLength(6) // 设置验证码长度
                .smsFromStart(10690999) // 设置验证码发送号码前几位数字
                //.smsFrom(1690123456789) // 如果验证码发送号码固定，则可以设置验证码发送完整号码
                .smsBodyStartWith("多点运动") // 设置验证码短信开头文字
                .smsBodyContains("验证码") // 设置验证码短信内容包含文字
                .build();
        AuthCode.getInstance().with(ForgetPasswordActivity.this).config(config).into(mEvCode);

    }

    /**
     * 简单处理了短信权限
     */
    private void handlePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS}, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length != 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "您阻止了app读取您的短信，您可以自己手动输入验证码", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //销毁获取验证码操作
        AuthCode.getInstance().onDestroy();
    }
}
