package com.dodsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.MD5EncryptUtils;
import com.dodsport.utils.NetUtils;
import com.dodsport.utils.PhoneFormatCheckUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.dialog.LoadingDialog;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 * 登录页面
 * @author Administrator
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.ivDelete)
    ImageView mIvDelete;
    @Bind(R.id.account)
    EditText mAccount;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.btLogin)
    Button mBtLogin;
    @Bind(R.id.register)
    TextView mRegister;
    @Bind(R.id.forgetPassword)
    TextView mForgetPassword;

    public final static int PASSWORD_MIN_LENGTH = 6;
    public final static int PASSWORD_MAX_LENGTH = 21;
    private LoadingDialog dialog;
    private String TAG = "******";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getToken(true);
        initView();

    }

    private void initView() {

    }

    @OnClick({R.id.ivDelete, R.id.btLogin, R.id.register, R.id.forgetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivDelete:     //清除账号输入框
                if (!TextUtils.isEmpty(mAccount.getText().toString())) {
                    mAccount.setText("");
                }
                break;
            case R.id.btLogin:      //登录
                login();
                break;
            case R.id.register:     //注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.forgetPassword:   //忘记密码
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     * */
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
                startLogin();
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
     * 登录请求
     * */
    private void startLogin(){
        String token = SPUtils.getToken(this);
        String phone = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        password  = MD5EncryptUtils.md5(password,phone);
        Log.i(TAG, "登录密码----->"+password+"");
        PunchTheClockRequest.userLogin(token, phone, password, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "登录----->"+response.toString()+"");
                try {
                    if (dialog!=null){
                        dialog.cancel();
                    }

                    UserDataBean userDataBean = JSON.parseObject(response.get(), UserDataBean.class);
                    if (!userDataBean.getResult().getCode().equals("0")){
                        switch (userDataBean.getResult().getCode()){
                            case "5002":    //密码错误
                                ToastUtils.showToCenters(LoginActivity.this,"账号不存在",1000);
                                break;
                            case "5004":    //密码错误
                                ToastUtils.showToCenters(LoginActivity.this,"账号或者密码错误!",1000);
                                break;
                            case "5037":    //密码错误
                                ToastUtils.showToCenters(LoginActivity.this,"该账号未注册!",1000);
                                break;
                            case "5008":    //密码错误
                                ToastUtils.showToCenters(LoginActivity.this,"账号或者密码错误!",1000);
                                break;
                            default:
                                break;
                        }
                        return;
                    }


                    if (!TextUtils.isEmpty(userDataBean.getDatas().toString())) {
                        Intent companyIntent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userInfo", userDataBean);
                        companyIntent.putExtras(bundle);
                        startActivity(companyIntent);
                        SPUtils.setUserDataBean(LoginActivity.this,userDataBean);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (dialog!=null){
                    dialog.cancel();
                }
                ToastUtils.showToCenters(LoginActivity.this,"登陆失败，请稍后重试!",800);

            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewShow();
    }



    /**
     * 判断输入框是否有值
     */
    public void getViewShow() {

        //用户名的输入变化
        mAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getView(s);

                if (s.length() > 0) {
                    //loginUserClose.setVisibility(View.VISIBLE);
                } else {
                    //loginUserClose.setVisibility(View.GONE);
                }

            }
        });

        //密码的输入变化
        mPassword.addTextChangedListener(new TextWatcher() {
            int cou = 0;
            int selectionEnd = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                cou = before + count;
                cou = mPassword.length();
            }

            @Override
            public void afterTextChanged(Editable s) {

                getView(s);
                if (cou > 20) {
                    selectionEnd = mPassword.getSelectionEnd();
                    s.delete(20, selectionEnd);
                    ToastUtils.show(LoginActivity.this, "密码长度最大为20位", 1000);
                }
            }
        });
        mPassword.setFilters(new InputFilter[]{filter});

    }

    /**
     * 监听手机登陆时EditText 是否有空格存在
     */
    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals(" ") || source.toString().contentEquals("\n")) {

                return "";
            } else {
                return null;
            }

        }
    };

    public void getView(Editable s) {
        if (!s.equals("") && !TextUtils.isEmpty(mAccount.getText().toString()) &&
                !TextUtils.isEmpty(mPassword.getText().toString())) {
            //mImmediatelybackBt.setVisibility(View.GONE);

        }
        if (TextUtils.isEmpty(s)) {
            //mImmediatelybackBt.setVisibility(View.VISIBLE);
        }

    }



    /**
     * 判断是否有效
     *
     * @return true有效
     */
    public boolean isValid() {
        boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mAccount.getText().toString());
        if (phoneLegal == false) {
            showToast(R.string.telphone_error);
            return false;
        }
        if (mPassword.getText().length() < PASSWORD_MIN_LENGTH
                || mPassword.getText().length() > PASSWORD_MAX_LENGTH) {
            showToast(R.string.password_error);
            return false;
        }

        return true;
    }

    /**
     * 显示加载动画
     */
    private void showLoading() {
        dialog = new LoadingDialog(this, 0.5f).builder();
        dialog.show();
        //  dialog.setCancelable(false);
    }


}

