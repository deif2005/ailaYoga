package com.dodsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.MD5EncryptUtils;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设置登录密码
 */
public class InTeRcaLatePasswordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.cbShow1)
    CheckBox mCbShow1;
    @Bind(R.id.password2)
    EditText mPassword2;
    @Bind(R.id.cbShow2)
    CheckBox mCbShow2;
    @Bind(R.id.btOK)
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
        mHeadIvBack.setOnClickListener(this);
        mBtOK.setOnClickListener(this);
        mHeadTvTitle.setText("设置登录密码");

        mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        /**
         * 显示和隐藏密码
         */
        mCbShow1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mPassword.setSelection(mPassword.getText().length());
            }
        });

        mCbShow2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    mPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    mPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mPassword2.setSelection(mPassword2.getText().length());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.btOK:         //修改成功后跳转到登录页面
                if (TextUtils.isEmpty(mPassword.getText().toString()) || TextUtils.isEmpty(mPassword2.getText().toString()) || !mPassword.getText().toString().equals(mPassword2.getText().toString())){
                    ToastUtils.showToCenters(this,"保持两个密码一致",1000);
                    return;
                }
                //发送请求
                sendRequest();
                break;
        }
    }

    /**
     * 发送请求
     * */
    private void sendRequest(){
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        String password = mPassword.getText().toString();
        String repassword = mPassword2.getText().toString();

        password  = MD5EncryptUtils.md5(password,phone);
        repassword  = MD5EncryptUtils.md5(repassword,phone);

        PunchTheClockRequest.userForgetPassword(phone, password, repassword, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "修改密码----》"+response.get().toString()+"");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")){
                    switch (resultBean.getCode().toString()){
                        case  "":

                            break;
                    }
                    return;
                }
                ToastUtils.showToCenters(InTeRcaLatePasswordActivity.this,"密码修改成功",800);
                Intent intent = new Intent(InTeRcaLatePasswordActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    }
}
