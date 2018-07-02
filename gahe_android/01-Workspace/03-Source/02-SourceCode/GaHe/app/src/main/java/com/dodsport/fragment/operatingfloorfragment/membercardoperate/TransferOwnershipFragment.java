package com.dodsport.fragment.operatingfloorfragment.membercardoperate;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.eventBus.ObjectEvent;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.MemberInfoBean;
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.PhoneFormatCheckUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.TransformationUtils;
import com.dodsport.weight.CircleImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 *
 * 过户功能Fragment
 */
public class TransferOwnershipFragment extends BaseFragment {


    @Bind(R.id.etSearchPhone)
    EditText mEtSearchPhone;
    @Bind(R.id.llSearch)
    LinearLayout mLlSearch;
    @Bind(R.id.cvUserHead)
    CircleImageView mCvUserHead;
    @Bind(R.id.tvUserName)
    TextView mTvUserName;
    @Bind(R.id.tvPhone)
    TextView mTvPhone;
    @Bind(R.id.tvSex)
    TextView mTvSex;
    @Bind(R.id.tvBirthday)
    TextView mTvBirthday;
    @Bind(R.id.llMemberInfo)
    LinearLayout mLlMemberInfo;
    @Bind(R.id.llHave)
    LinearLayout mLlHave;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;
    private View mView;
    private Activity mActivity;
    private FragmentCallBack mFragmentCallBack;
    private EventBus mEventBus;
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean;
    private String mMemberId = "";
    private String TAG = "***过户Fragment--";

    public TransferOwnershipFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_transfer_ownership, container, false);
        }
        Bundle data = getArguments();//获得从activity中传递过来的值
        mMemberCardBean = (MemberMyCardListBean.DatasBean.MembercardrelationListBean) data.getSerializable("MemberCardBean");
        initView();
        ButterKnife.bind(this, mView);
        return mView;
    }

    private void initView() {


    }



    @OnClick({R.id.llSearch, R.id.llConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
                boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mEtSearchPhone.getText().toString());
                if (phoneLegal) {
                    getSearchInfo(mEtSearchPhone.getText().toString());
                } else {
                    ToastUtils.showToCenters(mActivity, "请输入正确的手机号码!", 1000);
                }
                break;
            case R.id.llConfirm:
                if (TextUtils.isEmpty(mMemberId)){
                    ToastUtils.showToCenters(mActivity, "请搜索你想要过户的会员!", 1000);
                    return;
                }
                getmemberCardTransfer();
                break;
        }
    }

    /**
     * 提交过户请求
     * */
    private void getmemberCardTransfer(){
        String relationId = mMemberCardBean.getId();
        String creator = SPUtils.getUserDataBean(mActivity).getId();
        OperatingFloorRequest.memberCardTransfer(relationId, mMemberId, creator, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                try {
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        ToastUtils.showToCenters(mActivity, "过户失败,请稍后重试!", 1000);
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("Operating","TO");
                    mFragmentCallBack.CallBackFun(bundle);
                    ToastUtils.showToCenters(mActivity, "过户成功!", 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Log.i(TAG, "过户成功--->"+response.get().toString()+"");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "过户失败--->"+response.toString()+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 搜索用户信息
     *
     * @param phone
     */
    private void getSearchInfo(String phone) {
        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        OperatingFloorRequest.queryMemberinfoByPhoneNum(phone, businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    //Log.i(TAG, "获取用户信息成功--->" + response.get().toString() + "");
                    MemberInfoBean memberInfoBean = JSON.parseObject(response.get(), MemberInfoBean.class);
                    if (!memberInfoBean.getResult().getCode().equals("0")) {
                        switch (memberInfoBean.getResult().getCode()) {
                            case "5002":
                                ToastUtils.showToCenters(mActivity, "没有找到该会员!", 1000);
                                break;
                        }
                        mLlMemberInfo.setVisibility(View.GONE);
                        return;
                    }
                    mLlMemberInfo.setVisibility(View.VISIBLE);
                    //会员信息
                    MemberInfoBean.DatasBean.BaseMemberBean baseMember = memberInfoBean.getDatas().getBaseMember();
                    if (!TextUtils.isEmpty(baseMember.getMemberHead())) {
                        Picasso.with(mActivity).load(baseMember.getMemberHead())
                                .resize(400, 400)
                                .config(Bitmap.Config.RGB_565)
                                .error(R.drawable.user_head)
                                .placeholder(R.drawable.user_head)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .centerCrop()
                                .transform(TransformationUtils.zipImage(mCvUserHead)).into(mCvUserHead);
                    }
                    mMemberId = baseMember.getId();
                    mTvUserName.setText(baseMember.getMemberName());
                    mTvPhone.setText("电话：" + baseMember.getPhoneNum());
                    if (baseMember.getSex() == 1) {
                        mTvSex.setText("性别：男");
                    } else if (baseMember.getSex() == 2) {
                        mTvSex.setText("性别：女");
                    }
                    if (!TextUtils.isEmpty(baseMember.getBirthday())) {
                        mTvBirthday.setText("生日：" + baseMember.getBirthday().substring(0, 10));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "查找失败,请稍后重试!", 1000);
                mLlMemberInfo.setVisibility(View.GONE);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(ObjectEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取对应的卡价格阶梯
        if (event.getType().equals("TransferOwnership")) {
            mMemberCardBean = event.getMemberCardBean();

        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mFragmentCallBack = (FragmentCallBack) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.getDefault().unregister(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
