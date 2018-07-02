package com.dodsport.fragment.operatingfloorfragment.membercardoperate;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.eventBus.ObjectEvent;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
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
 * 会员卡 停卡或者开卡
 */
public class StopOrStartCardFragment extends BaseFragment {


    @Bind(R.id.ivStop)
    ImageView mIvStop;
    @Bind(R.id.tvStop)
    TextView mTvStop;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;
    @Bind(R.id.Abnormity)
    LinearLayout mAbnormity;
    private View mView;
    private Activity mActivity;
    private FragmentCallBack mFragmentCallBack;
    private EventBus mEventBus;
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean;
    private String TAG = "****停卡碎片--";


    public StopOrStartCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_stop_or_start_card, container, false);
        }
        ButterKnife.bind(this, mView);
        Bundle data = getArguments();//获得从activity中传递过来的值
        mMemberCardBean = (MemberMyCardListBean.DatasBean.MembercardrelationListBean) data.getSerializable("MemberCardBean");
        initView();

        return mView;
    }

    private void initView() {
        mAbnormity.setVisibility(View.GONE);
        mLlConfirm.setVisibility(View.VISIBLE);
        if (mMemberCardBean.getCardStatus() == 1) { //启用状态
            mTvStop.setText("确认停卡!");
            mIvStop.setImageResource(R.drawable.qi_yong);
        } else if (mMemberCardBean.getCardStatus() == 2) {  //停卡状态
            mTvStop.setText("确认启用!");
            mIvStop.setImageResource(R.drawable.ting_ka);
        } else {
            mAbnormity.setVisibility(View.VISIBLE);
            mLlConfirm.setVisibility(View.GONE);
        }
    }


    @Override
    protected void lazyLoad() {

    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(ObjectEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取对应的卡价格阶梯
        if (event.getType().equals("StopOrStartCard")) {
            mMemberCardBean = event.getMemberCardBean();
            initView();
            Log.i(TAG, "接收到的对象--->" + mMemberCardBean.toString() + "");

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.llConfirm)
    public void onViewClicked() {
        String chooseType = "";
        final String relationId =mMemberCardBean.getId();
        String id = SPUtils.getUserDataBean(mActivity).getId();
        if (mMemberCardBean.getCardStatus()==1){        //该卡启用中
            chooseType = "2";
        }else if (mMemberCardBean.getCardStatus() ==2){ //该卡已停卡
            chooseType = "1";
        }
        final String finalChooseType = chooseType;
        OperatingFloorRequest.memberCardStopOrStart(relationId, chooseType, id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "停卡操作--->"+response.toString()+"");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (resultBean.getCode().equals("0")){
                    ToastUtils.showToCenters(mActivity,"操作成功!",1000);
                    Bundle bundle = new Bundle();
                    bundle.putString("Operating","Stop");
                    mFragmentCallBack.CallBackFun(bundle);

                    mAbnormity.setVisibility(View.GONE);
                    mLlConfirm.setVisibility(View.VISIBLE);

                    if (mMemberCardBean.getCardStatus() == 2) {
                        mTvStop.setText("确认停卡!");
                        mIvStop.setImageResource(R.drawable.qi_yong);
                    } else if (mMemberCardBean.getCardStatus() == 1) {
                        mTvStop.setText("确认启用!");
                        mIvStop.setImageResource(R.drawable.ting_ka);
                    } else {
                        mAbnormity.setVisibility(View.VISIBLE);
                        mLlConfirm.setVisibility(View.GONE);
                    }
                }else {
                    mAbnormity.setVisibility(View.VISIBLE);
                    mLlConfirm.setVisibility(View.GONE);
                    ToastUtils.showToCenters(mActivity,"操作失败,请稍后重试!",1000);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"操作失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
