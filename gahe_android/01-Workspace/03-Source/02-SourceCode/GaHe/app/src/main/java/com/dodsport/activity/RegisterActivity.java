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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.model.RegisterInfoBean;
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
 * 用户注册
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
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
    @Bind(R.id.agree)
    CheckBox mAgree;
    @Bind(R.id.reg_tvAgreement)
    TextView mRegTvAgreement;


    private static final int REQUEST_PERMISSION_CODE = 0;
    private final static int CODE_REPWAT_SECONDS = 60;
    private Timer time;
    private TimerTask task;
    private int currentSecond;
    private boolean mChecked = false;
    private String TAG = "****注册";
    private  String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("注册");
        //用户注册协议监听
        mAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mChecked = b;
            }
        });

    }


    /**
     * 获取验证码
     */
    public void getCode() {
        handlePermission();
        //显示手机号码
        mPheno.setVisibility(View.VISIBLE);
        String phone = mAccount.getText().toString();
        final String substring = phone.substring(0, 3);
        String substring1 = phone.substring(3, 7);
        String substring2 = phone.substring(7, 11);
        String text = substring + "-" + substring1 + "-" + substring2;
        mPheno.setText("验证码已发送至手机: "+text+"");
        String operationId = "1";//表示注册
        PunchTheClockRequest.getCode(mAccount.getText().toString(),operationId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "onSucceed:获取验证码成功 "+response.toString()+"");
                try {



                } catch (Exception e) {
                    e.printStackTrace();
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
                .codeLength(4) // 设置验证码长度46
                .smsFromStart(1069) // 设置验证码发送号码前几位数字
                //.smsFrom(1690123456789) // 如果验证码发送号码固定，则可以设置验证码发送完整号码
                .smsBodyStartWith("【多点运动助手】") // 设置验证码短信开头文字
                .smsBodyContains("验证码") // 设置验证码短信内容包含文字
                .build();
        AuthCode.getInstance().with(RegisterActivity.this).config(config).into(mEvCode);


    }


    @OnClick({R.id.head_ivBack,R.id.btcode, R.id.btNextStep, R.id.reg_tvAgreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:  //返回
                finish();
                break;
            case R.id.btcode:       //获取验证码
                try {
                    boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
                    if (phoneLegal == true) {
                        /*if(!mChecked){
                            Toast("请同意用户协议！");
                            return;
                        }*/
                        getCode();     //发送请求
                        startTimer();  //启动计时器
                    } else {
                        showToast(R.string.telphone_error);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btNextStep:   //下一步
                boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
                if (phoneLegal == true && !TextUtils.isEmpty(mEvCode.getText().toString())) {
                    if (!mChecked){
                        Toast("请同意用户协议！");
                        return;
                   }

                    mBtNextStep.setEnabled(false);
                    String account = mAccount.getText().toString();
                    String evcode = mEvCode.getText().toString();
                    String operationId = "1";       //表示注册
                    /**获取用户信息*/
                    PunchTheClockRequest.codeValidate(account,evcode,operationId, new OnResponseListener<String>() {
                        @Override
                        public void onStart(int what) {
                        }

                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            try {
                                RegisterInfoBean registerInfo = JSON.parseObject(response.get(), RegisterInfoBean.class);
                                //Log.i(TAG, "----》"+registerInfo.toString()+"");
                                if (!registerInfo.getResult().getCode().equals("0")){
                                    switch (registerInfo.getResult().getCode()){
                                        case "5001":
                                            ToastUtils.showToCenters(RegisterActivity.this,"验证码错误！",800);
                                            break;
                                        case "5009":
                                            ToastUtils.showToCenters(RegisterActivity.this,"该用户已存在,请直接登录！",800);
                                            break;
                                        case "5002":    //用户不存在
                                            Intent intent = new Intent(RegisterActivity.this,RegisterErrorPageActivity.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        default:
                                            break;
                                    }
                                    mBtNextStep.setEnabled(true);
                                    return;
                                }
                                    Intent companyIntent = new Intent(RegisterActivity.this, ConfirmUserInfoActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("userInfo", registerInfo);
                                    companyIntent.putExtras(bundle);
                                    startActivity(companyIntent);
                                    finish();

                            } catch (Exception e) {
                                mBtNextStep.setEnabled(true);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailed(int what, Response<String> response) {
                            mBtNextStep.setEnabled(true);

                            Log.i(TAG, "获取用户信息失败\t"+response.toString()+"");

                        }

                        @Override
                        public void onFinish(int what) {

                        }
                    });

                }else {
                    //注册错误页面
                    startActivity(new Intent(RegisterActivity.this,RegisterErrorPageActivity.class));
                    finish();
                    return;
                }
                break;
            case R.id.reg_tvAgreement:  //查看协议

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
     * 简单处理了短信权限
     */
    private void handlePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS}, REQUEST_PERMISSION_CODE);
        }
        Log.i(TAG, "handlePermission: ");
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
