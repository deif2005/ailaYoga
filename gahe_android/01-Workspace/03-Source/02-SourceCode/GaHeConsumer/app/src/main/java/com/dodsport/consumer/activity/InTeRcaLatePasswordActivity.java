package com.dodsport.consumer.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodsport.consumer.R;
import com.dodsport.consumer.extension.BaseSubscriber;
import com.dodsport.consumer.model.ResultBean;
import com.dodsport.consumer.net.api.VisApi;
import com.dodsport.consumer.request.UrlInterfaceManager;
import com.dodsport.consumer.util.JsonUtils;
import com.dodsport.consumer.util.MD5EncryptUtils;
import com.dodsport.consumer.util.ToastUtils;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 设置登录密码
 * @author Administrator
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class InTeRcaLatePasswordActivity extends BaseActivity {

    @BindView(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @BindView(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.cbShow1)
    CheckBox mCbShow1;
    @BindView(R.id.password2)
    EditText mPassword2;
    @BindView(R.id.cbShow2)
    CheckBox mCbShow2;
    @BindView(R.id.btOK)
    Button mBtOK;
    private String TAG = "****";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_te_rca_late_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("设置登录密码");

        mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        /**
         * 显示和隐藏密码
         */
        mCbShow1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO Auto-generated method stub
            if (isChecked) {
                //如果选中，显示密码
                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mPassword.setSelection(mPassword.getText().length());
        });

        mCbShow2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO Auto-generated method stub
            if (isChecked) {
                //如果选中，显示密码
                mPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                mPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mPassword2.setSelection(mPassword2.getText().length());
        });
    }

    /**
     * 发送请求
     */
    private void sendRequest() {
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        String register = intent.getStringExtra("register");
        String password = mPassword.getText().toString();
        String repassword = mPassword2.getText().toString();

        password = MD5EncryptUtils.md5(password, phone);
        repassword = MD5EncryptUtils.md5(repassword, phone);
        String url = "";
        Map<String,String> map = new ArrayMap<>();
        map.put("phoneNum",phone);
        map.put("password",password);
        map.put("repassword",repassword);

        //注册
        if (register.equals(register) && !TextUtils.isEmpty(register)){

            url = UrlInterfaceManager.REGISTERCLIENTUSER;
        }else {
            //忘记密码
            url = UrlInterfaceManager.INTERCALATEPASSWORD;
        }

        new VisApi().posts(url,map)
                .subscribe(new BaseSubscriber<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {

                        try {
                            ResultBean resultBean = JsonUtils.fromStringToJson(body.string(), ResultBean.class);
                            if (!resultBean.getResult().getCode().toString().trim().equals("0")){
                                return;
                            }

                            ToastUtils.showToCenters(InTeRcaLatePasswordActivity.this, "操作成功,请重新登录!", 800);
                            Intent intent = new Intent(InTeRcaLatePasswordActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @OnClick({R.id.head_ivBack, R.id.cbShow1, R.id.cbShow2, R.id.btOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.btOK:
                if (TextUtils.isEmpty(mPassword.getText().toString()) || TextUtils.isEmpty(mPassword2.getText().toString()) || !mPassword.getText().toString().equals(mPassword2.getText().toString())) {
                    ToastUtils.showToCenters(this, "保持两个密码一致", 1000);
                    return;
                }
                //发送请求
                sendRequest();
                break;
                default:
                    break;
        }
    }

}
