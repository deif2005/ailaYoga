package com.dodsport.consumer.activity;

import android.Manifest;
import android.annotation.TargetApi;
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
import com.dodsport.consumer.rxbus.ResponseBodyBean;
import com.dodsport.consumer.rxbus.RxBus;
import com.dodsport.consumer.util.PhoneFormatCheckUtils;
import com.dodsport.consumer.util.ToastUtils;
import com.dodsport.consumer.util.authcode.AuthCode;
import com.dodsport.consumer.util.authcode.CodeConfig;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 忘记密码
 *
 * @author Administrator
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ForgetPasswordActivity extends BaseActivity {

    private final static int CODE_REPWAT_SECONDS = 60;
    @BindView(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @BindView(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.btcode)
    Button mBtcode;
    @BindView(R.id.account)
    EditText mAccount;
    @BindView(R.id.passwordText)
    TextView mPasswordText;
    @BindView(R.id.evCode)
    EditText mEvCode;
    @BindView(R.id.Pheno)
    TextView mPheno;
    @BindView(R.id.btNextStep)
    Button mBtNextStep;
    private Timer time;
    private TimerTask task;
    private int currentSecond;
    private int REQUEST_PERMISSION_CODE = 0;
    private String Code;
    private String Phone;
    private String TAG = "******";
    private Activity mActivity;
    private String RXBUSTYPE ="ForgetPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
        doSubscribe();
    }

    private void initView() {
        handlePermission();
        mHeadTvTitle.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if (key.equals("Login")){
            mHeadTvTitle.setText("忘记密码");
        }else {
            mHeadTvTitle.setText("设置密码");
        }
        mHeadIvBack.setVisibility(View.VISIBLE);
    }

    /**接收消息*/
    private void doSubscribe(){
        Subscription subscription = RxBus.getInstance()
                .tObservable(ResponseBodyBean.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    switch (model.getType()) {
                        case "RXBUSTYPE":
                            Log.i(TAG, "接收到数据");
                            break;
                        default:
                            break;
                    }
                });
        RxBus.getInstance().addSubscription(this, subscription);
    }


    @OnClick({R.id.head_ivBack, R.id.btcode, R.id.btNextStep})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.btcode:
                try {
                    boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
                    if (phoneLegal == true) {
                        getCode();     //发送请求
                        startTimer();
                    } else {
                        ToastUtils.showToCenters(mActivity,getString( R.string.telphone_error), ApiCode.Request.UNKNOWN);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btNextStep:
                boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
                if (phoneLegal && !TextUtils.isEmpty(mEvCode.getText().toString())) {
                    Code = mEvCode.getText().toString();
                    Phone = mAccount.getText().toString();
                    proofreadCode();
                }else {
                    ToastUtils.showToCenters(mActivity,"输入手机号或验证码!",1000);
                }
                    break;
                default:
                    break;
        }
    }

    /**
     * 网络请求
     * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void proofreadCode(){
        Map<String,String> map = new ArrayMap<>();
        map.put("identifyingCode",Code);
        map.put("phoneNum",Phone);
        map.put("flag","2");        //标识验证码类型1、注册  2、忘记密码
        String url = UrlInterfaceManager.PROOFREADCODE;
        new VisApi().post(url,map)
        .subscribe(new BaseSubscriber<JsonObject>() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "校对验证捕获异常--->"+e.toString()+"");
            }

            @Override
            public void onNext(JsonObject body) {
                try {
                    ResultBean resultBean = JSON.parseObject(body.toString(), ResultBean.class);
                    Log.i(TAG, "校对验证返回--->"+resultBean.getResult().toString()+"");
                    if (resultBean.getResult().getCode().equals("0")) {
                        Intent intent = new Intent(mActivity,InTeRcaLatePasswordActivity.class);
                        intent.putExtra("phone", Phone);
                        intent.putExtra("register","forgetpassword");
                        startActivity(intent);
                        finish();
                    }
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
     * 获取验证码
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getCode() {

        //显示手机号码
        mPheno.setVisibility(View.VISIBLE);
        String phone = mAccount.getText().toString();
        String substring = phone.substring(0, 3);
        String substring1 = phone.substring(3, 7);
        String substring2 = phone.substring(7, 11);
        String text = substring + "-" + substring1 + "-" + substring2;
        mPheno.setText("验证码已发送至手机: " + text + "");

        Map<String,String> map = new ArrayMap<>();
        map.put("phoneNum",phone);
        new VisApi().getCode(map)
                .subscribe(new BaseSubscriber<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "获取验证码捕获异常--->"+e.toString()+"");
                    }

                    @Override
                    public void onNext(ResponseBody body) {

                        try {
                            Log.i(TAG, "获取验证码返回--->"+body.string().toString()+"");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        /**自动获取验证码*/
        int parseInt = Integer.parseInt(substring);
        CodeConfig config = new CodeConfig.Builder()
                .codeLength(4) // 设置验证码长度
                .smsFromStart(parseInt) // 设置验证码发送号码前几位数字
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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_PERMISSION_CODE);
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
        RxBus.getInstance().unSubscribe(this);
        //销毁获取验证码操作
        AuthCode.getInstance().onDestroy();
    }


}
