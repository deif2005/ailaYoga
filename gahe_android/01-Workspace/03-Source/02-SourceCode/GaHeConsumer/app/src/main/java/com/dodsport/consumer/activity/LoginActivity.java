package com.dodsport.consumer.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodsport.consumer.R;
import com.dodsport.consumer.extension.BaseSubscriber;
import com.dodsport.consumer.model.ApiCode;
import com.dodsport.consumer.model.UserBean;
import com.dodsport.consumer.net.api.VisApi;
import com.dodsport.consumer.util.JsonUtils;
import com.dodsport.consumer.util.MD5EncryptUtils;
import com.dodsport.consumer.util.NetUtils;
import com.dodsport.consumer.util.PhoneFormatCheckUtils;
import com.dodsport.consumer.util.SPUtils;
import com.dodsport.consumer.util.ToastUtils;
import com.dodsport.consumer.view.dialog2.LoadingDialog;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * A login screen that offers login via email/password.
 * <p>
 * 登录页面
 * @author Administrator
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.ivDelete)
    ImageView mIvDelete;
    @BindView(R.id.account)
    EditText mAccount;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.btLogin)
    Button mBtLogin;
    @BindView(R.id.register)
    TextView mRegister;
    @BindView(R.id.forgetPassword)
    TextView mForgetPassword;

    private String TAG = "***登录--->";
    private Activity mActivity;
    public final static int PASSWORD_MIN_LENGTH = 6;
    public final static int PASSWORD_MAX_LENGTH = 21;
    private LoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示状态栏
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.ivDelete, R.id.btLogin, R.id.register, R.id.forgetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivDelete:
                mAccount.setText("");
                break;
                //登录
            case R.id.btLogin:
                login();
                break;
                //新用户注册
            case R.id.register:

                break;
                //忘记密码
            case R.id.forgetPassword:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
                default:
                    break;
        }
    }


    /**
     * 登录
     * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void login(){
        if (!isValid()){
            return;
        }
        showLoading();
        mBtLogin.setEnabled(false);
        try {
            //判断网络是否连接
            boolean connected = NetUtils.isConnected(LoginActivity.this);
            if (connected) {
                //有网络
                loginRequest();
            } else {
                if (dialog!=null){
                    dialog.cancel();
                }
                //无网络状态 弹框提示打开WIFI
                // NetUtils.setNetwork(LoginActivity.this);
                ToastUtils.showToCenter(this, "当前没有网络", 0);
            }
            mBtLogin.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
            mBtLogin.setEnabled(true);
        }

    }

    /**
     * 显示加载动画
     */
    private void showLoading() {
        dialog = new LoadingDialog(this, 0.5f).builder();
        dialog.show();
        //  dialog.setCancelable(false);
    }

    /**请求登录*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loginRequest() {
        Map<String,String> map = new ArrayMap<>();
        String phone = mAccount.getText().toString().trim();
        String password = mPassword.getText().toString();
        password  = MD5EncryptUtils.md5(password,phone);
        map.put("phoneNum",phone);
        map.put("password",password);
        new VisApi().login(map)
                .subscribe(new BaseSubscriber<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onNext: 登录捕获异常"+e.toString()+"");
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {

                            UserBean userBean = JsonUtils.fromStringToJson(body.string(), UserBean.class);
                            Log.i(TAG, "onNext: 登录返回"+userBean.toString()+"");
                            if (!userBean.getResult().getCode().toString().equals("0")){

                                return;
                            }
                            SPUtils.setUserDataBean(mActivity,userBean);
                            startActivity(new Intent(mActivity,MainActivity.class));
                            finish();
                            if (dialog!=null){
                                dialog.cancel();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 判断是否有效
     *
     * @return true有效
     */
    public boolean isValid() {
        boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
        if (phoneLegal == false) {
            ToastUtils.showToCenters(mActivity, (String) getResources().getText(R.string.telphone_error), ApiCode.Request.TIMEOUT_ERROR);
            return false;
        }
        if (mPassword.getText().length() < PASSWORD_MIN_LENGTH
                || mPassword.getText().length() > PASSWORD_MAX_LENGTH) {
            ToastUtils.showToCenters(mActivity, (String) getResources().getText(R.string.password_error), ApiCode.Request.TIMEOUT_ERROR);
            return false;
        }

        return true;
    }
}

