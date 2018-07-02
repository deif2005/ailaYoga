package com.dodsport.fragment.operatingfloorfragment.membercardoperate;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
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
 * 会员卡操作 赠送功能模块
 */
public class LargessFragment extends BaseFragment  {


    @Bind(R.id.tvLargess)
    TextView mTvLargess;
    @Bind(R.id.etLargess)
    EditText mEtLargess;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;

    private View mView;
    private Activity mActivity;
    private FragmentCallBack mFragmentCallBack;
    private EventBus mEventBus;
    private int position = 0;
    private String TAG = "***赠送--";
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean; //选中的会员卡

    public LargessFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_largess, container, false);
        }
        ButterKnife.bind(this, mView);

        Bundle data = getArguments();//获得从activity中传递过来的值
        mMemberCardBean = (MemberMyCardListBean.DatasBean.MembercardrelationListBean) data.getSerializable("MemberCardBean");
        initView();
        return mView;
    }

    private void initView(){
        if (position ==1){
            mEtLargess.setHint("输入赠送的次数");
            mTvLargess.setText("赠送次数");
        }else {
            mEtLargess.setHint("输入赠送的天数");
            mTvLargess.setText("赠送天数");
        }
    }

    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(ObjectEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取对应的卡价格阶梯
        if (event.getType().equals("Largess")) {
            mMemberCardBean = event.getMemberCardBean();
            position = mMemberCardBean.getMembcardType();
            initView();
        }

    }


    @OnClick(R.id.llConfirm)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mEtLargess.getText().toString())){
            ToastUtils.showToCenters(getActivity(),"请输入要赠送的天数/次数!",1000);
            return;
        }
        final String relationId =  mMemberCardBean.getId();
        int giveTimes = Integer.parseInt(mEtLargess.getText().toString());
        String id = SPUtils.getUserDataBean(getActivity().getApplicationContext()).getId();
        OperatingFloorRequest.memberCardGive(relationId, giveTimes, id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (resultBean.getCode().equals("0")){
                        Bundle bundle = new Bundle();
                        bundle.putString("Operating","Largess");
                        mFragmentCallBack.CallBackFun(bundle);
                        ToastUtils.showToCenters(mActivity,"赠送成功!",1000);
                        mEtLargess.setText("");
                    }else {
                        ToastUtils.showToCenters(mActivity,"赠送失败,请稍后重试!",1000);
                    }
                } catch (Exception e) {
                    ToastUtils.showToCenters(mActivity,"赠送失败,请稍后重试!",1000);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"赠送失败,请稍后重试!",1000);

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mFragmentCallBack = (FragmentCallBack) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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


}
