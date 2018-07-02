package com.dodsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.model.RegisterInfoBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.MD5EncryptUtils;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 确认用户的个人信息
 */
public class ConfirmUserInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvName)
    TextView mTvName;
    @Bind(R.id.tvPhone)
    TextView mTvPhone;
    @Bind(R.id.tvSex)
    TextView mTvSex;
    @Bind(R.id.tvIDCard)
    TextView mTvIDCard;
    @Bind(R.id.tvAddress)
    TextView mTvAddress;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.password2)
    EditText mPassword2;
    @Bind(R.id.btOK)
    Button mBtOK;
    @Bind(R.id.btCancel)
    Button mBtCancel;
    @Bind(R.id.cbShow1)
    CheckBox mCbShow1;
    @Bind(R.id.cbShow2)
    CheckBox mCbShow2;
    @Bind(R.id.tvCompanyName)
    TextView mTvCompanyName;
    @Bind(R.id.ivCompanyName)
    ImageView mIvCompanyName;
    @Bind(R.id.rlCompanyName)
    RelativeLayout mRlCompanyName;

    private String TAG = "*****";
    private RegisterInfoBean userDataBean;
    private RegisterInfoBean.DatasBean.RempBean mUserDataBean;
    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_user_info);
        ButterKnife.bind(this);

        try {
            Intent intent = getIntent();
            userDataBean = (RegisterInfoBean) intent.getSerializableExtra("userInfo");
            mUserDataBean = userDataBean.getDatas().getRemp();
            if (mUserDataBean!=null){
                userDataBean = null;
            }
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().register(this); //注册

    }

    private void initView() {
        mHeadTvTitle.setVisibility(View.VISIBLE);
//        mHeadIvBack.setVisibility(View.INVISIBLE);
        mHeadTvTitle.setText("确认个人信息");
//       mHeadIvBack.setOnClickListener(this);
        mBtOK.setOnClickListener(this);
        mBtCancel.setOnClickListener(this);
        mRlCompanyName.setOnClickListener(this);

        mTvName.setText(mUserDataBean.getEmployeeName()+"");
        mTvPhone.setText(mUserDataBean.getPhoneNum());

        if (mUserDataBean.getSex().equals("1")){
            mTvSex.setText("男");
        }else if (mUserDataBean.getSex().equals("2")){
            mTvSex.setText("女");
        }
        mTvIDCard.setText(mUserDataBean.getIdCard()+"");
        mTvAddress.setText(mUserDataBean.getBusinessName()+"");

        mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        /**
         * 显示和隐藏密码
         */
        mCbShow1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //如果选中，显示密码
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
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
                if (isChecked) {
                    //如果选中，显示密码
                    mPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mPassword2.setSelection(mPassword2.getText().length());
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
//                finish();
                break;
            case R.id.btOK:         //确定

                userRegister();
                //主页
//                startActivity(new Intent(this, MainActivity.class));
//                removeALLActivity();
//                finish();
                break;
            case R.id.btCancel:     //取消
                //登陆页面
                Intent intent = new Intent(ConfirmUserInfoActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //设置Activity过渡动画
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
                break;
            case R.id.rlCompanyName:    //展开店铺列表
//                mIvCompanyName.setImageResource(R.drawable.xiang_xia);
//                CompanyNamePopupWindow  companyNamePopupWindow = new CompanyNamePopupWindow(this,mIvCompanyName,mTvAddress,mCompanyNameBean.getBusinessList());
//                companyNamePopupWindow.showAsDropDown(mRlCompanyName);  //显示在指定控件的下面
                break;

        }
    }

    private void userRegister(){

        if (!TextUtils.isEmpty(mPassword.getText().toString()) && !TextUtils.isEmpty(mPassword2.getText().toString())){
            String phone = mUserDataBean.getPhoneNum();
            String password = mPassword.getText().toString();
            String password2 = mPassword2.getText().toString();
            if (password.equals(password2) && !TextUtils.isEmpty(phone)) {
                password  = MD5EncryptUtils.md5(password,phone);
                password2 = MD5EncryptUtils.md5(password2,phone);

            }else {
                ToastUtils.showToCenters(this,"请保持两个密码一致!",1000);
                return;
            }

            PunchTheClockRequest.userRegister(phone,password, password2, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Response<String> response) {

                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);

                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        ToastUtils.showToCenters(ConfirmUserInfoActivity.this,"注册失败!",1000);
                        return;
                    }
                    try {
                        String datas = statusBean.getDatas();
                        String substring1 = datas.substring(0, 1);
                        String substring2 = datas.substring(1, 2);
                        //过滤掉没有返回对象 进行跳转操作
                        if (substring1.equals("{") && substring2.equals("}")){
                            Intent intent = new Intent(ConfirmUserInfoActivity.this,RegisterErrorPageActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        ToastUtils.showToCenters(ConfirmUserInfoActivity.this,"注册成功,请登录!",1000);

                        Intent intent = new Intent(ConfirmUserInfoActivity.this,LoginActivity.class);
                        startActivity(intent);
                        //设置Activity过渡动画
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    } catch (Exception e) {
                        Intent intent = new Intent(ConfirmUserInfoActivity.this,LoginActivity.class);
                        startActivity(intent);
                        //设置Activity过渡动画
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                        e.printStackTrace();
                    }



                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    ToastUtils.showToCenters(ConfirmUserInfoActivity.this,"注册失败,请稍后重试!",1000);
                    //登陆页面
                    Intent intent = new Intent(ConfirmUserInfoActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //设置Activity过渡动画
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    finish();
                }

                @Override
                public void onFinish(int what) {

                }
            });

        }else {
            ToastUtils.showToCenters(this,"密码不能为空!",800);
        }
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(AskForLeaveTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event ==null)
            return;
        //获取用户选中请假类型
        if (event.getType().equals("companyname")) {
         this.position = event.getmPosition();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);  //反注册
    }
}
