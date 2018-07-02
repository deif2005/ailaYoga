package com.dodsport.consumer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dodsport.consumer.R;
import com.dodsport.consumer.extension.BaseSubscriber;
import com.dodsport.consumer.model.ApiCode;
import com.dodsport.consumer.model.ResultBean;
import com.dodsport.consumer.net.api.VisApi;
import com.dodsport.consumer.request.UrlInterfaceManager;
import com.dodsport.consumer.util.PhoneFormatCheckUtils;
import com.dodsport.consumer.util.ToastUtils;
import com.dodsport.consumer.util.authcode.AuthCode;
import com.dodsport.consumer.util.authcode.CodeConfig;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户注册
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RegisterActivity extends BaseActivity {
    private static final int REQUEST_PERMISSION_CODE = 0;
    private final static int CODE_REPWAT_SECONDS = 60;
    @BindView(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @BindView(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @BindView(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @BindView(R.id.head_tvOK)
    TextView mHeadTvOK;
    @BindView(R.id.btcode)
    Button mBtcode;
    @BindView(R.id.account)
    EditText mAccount;
    @BindView(R.id.evCode)
    EditText mEvCode;
    @BindView(R.id.Pheno)
    TextView mPheno;
    @BindView(R.id.btNextStep)
    Button mBtNextStep;
    @BindView(R.id.agree)
    CheckBox mAgree;
    @BindView(R.id.reg_tvAgreement)
    TextView mRegTvAgreement;

    private Timer time;
    private TimerTask task;
    private int currentSecond;
    private boolean mChecked = false;
    private String TAG = "****注册";
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("新用户注册");
        //用户注册协议监听
        mAgree.setOnCheckedChangeListener((compoundButton, b) -> {
            mChecked = b;
        });

    }


    /**
     * 获取验证码
     *
     * phoneNum	String		是	员工电话
     * platformId	String		是	1:客户端;2:商家端;3:服务端(新增)
     * operationId	String		是	1:注册;2:忘记密码(新增)
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
        mPheno.setText("验证码已发送至手机: " + text + "");
        String url = UrlInterfaceManager.CODE;
        Map<String,String> map = new ArrayMap<>();
        map.put("phoneNum",mAccount.getText().toString().trim());
        map.put("platformId","1");
        map.put("operationId","1");
         new VisApi().post(url,map)
                 .subscribe(new BaseSubscriber<JsonObject>() {
                     @Override
                     public void onError(Throwable e) {
                         Log.i(TAG, "获取验证码异常 " + e.getLocalizedMessage().toString() + "");
                     }

                     @Override
                     public void onNext(JsonObject jsonObject) {
                         Log.i(TAG, "获取验证码成功 " + jsonObject.toString() + "");
                     }
                 });



        /**自动获取验证码*/
        int parseInt = Integer.parseInt(substring);
        CodeConfig config = new CodeConfig.Builder()
                .codeLength(4) // 设置验证码长度46
                .smsFromStart(1069) // 设置验证码发送号码前几位数字
                //.smsFrom(1690123456789) // 如果验证码发送号码固定，则可以设置验证码发送完整号码
                .smsBodyStartWith("多点运动助手") // 设置验证码短信开头文字
                .smsBodyContains("验证码") // 设置验证码短信内容包含文字
                .build();
        AuthCode.getInstance().with(RegisterActivity.this).config(config).into(mEvCode);

    }


    @OnClick({R.id.head_ivBack, R.id.btcode, R.id.btNextStep, R.id.reg_tvAgreement})
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
                        ToastUtils.showToCenters(mActivity,getString( R.string.telphone_error), ApiCode.Request.UNKNOWN);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btNextStep:   //下一步
                boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
                if (phoneLegal == true && !TextUtils.isEmpty(mEvCode.getText().toString())) {
                    if (!mChecked) {
                        Toast("请同意用户协议！");
                        return;
                    }
                    mBtNextStep.setEnabled(false);
                    String account = mAccount.getText().toString();
                    String evcode = mEvCode.getText().toString();
                    proofreadCode(account,evcode);

                } else {

                    ToastUtils.showToCenters(mActivity,getString( R.string.telphone_error), ApiCode.Request.UNKNOWN);
                    //注册错误页面
//                    startActivity(new Intent(RegisterActivity.this, RegisterErrorPageActivity.class));
//                    finish();
                    return;
                }

                break;
            case R.id.reg_tvAgreement:  //查看协议

                break;
            default:
                break;
        }
    }


    /**
     * 网络请求
     *
     * phoneNum	String		是	员工电话
     * identifyingCode	String		是	验证码
     * platformId	String		是	1:客户端;2:商家端;3:服务端(新增)
     * operationId	String		是	1:注册;2:忘记密码(新增)
     * @param account
     * @param evcode*/

    private void proofreadCode(String account, String evcode){
        Map<String,String> map = new ArrayMap<>();
        map.put("identifyingCode",evcode);
        map.put("phoneNum",account);
        map.put("platformId","1");
        map.put("operationId","1");
        String url = UrlInterfaceManager.PROOFREADCODE;
        new VisApi().post(url,map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "校对验证捕获异常--->"+e.getLocalizedMessage().toString()+"");
                    }

                    @Override
                    public void onNext(JsonObject body) {
                        try {
                            ResultBean resultBean = JSON.parseObject(body.toString(), ResultBean.class);
                            Log.i(TAG, "校对验证返回--->" + resultBean.getResult().toString() + "");
                            if (!resultBean.getResult().getCode().equals("0")){
                            switch (resultBean.getResult().getCode()) {
                                case "5001":
                                    ToastUtils.showToCenters(mActivity, "验证码错误！", 800);
                                    break;
                                case "5008":
                                case "5045":
                                    ToastUtils.showToCenters(mActivity, "该用户已存在,请直接登录！", 800);
                                    break;
                                case "5002":    //用户不存在
//                                    Intent intent = new Intent(RegisterActivity.this, RegisterErrorPageActivity.class);
//                                    startActivity(intent);
                                    finish();
                                    break;
                                default:
                                    break;
                            }
                            mBtNextStep.setEnabled(true);
                            return;

                        }
                            Intent intent = new Intent(mActivity,InTeRcaLatePasswordActivity.class);
                            intent.putExtra("phone", account);
                            intent.putExtra("register", "register");
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            Log.i(TAG, "校对验证捕获请求异常--->"+e.toString()+"");
                            e.printStackTrace();
                        }
                    }
                });
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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_PERMISSION_CODE);
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
