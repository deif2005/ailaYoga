package com.dodsport.fragment.operatingfloorfragment.membercardoperate;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.cardcoupons.ReissueActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.ObjectEvent;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.MemberCardListBean;
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 换卡功能的Fragment
 */
public class ReplaceCardFragment extends BaseFragment {


    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.tvItem2)
    TextView mTvItem2;
    @Bind(R.id.tvItem3)
    TextView mTvItem3;
    @Bind(R.id.Price_RecyclerView)
    RecyclerView mPriceRecyclerView;
    @Bind(R.id.layout_price)
    LinearLayout mLayoutPrice;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;
    @Bind(R.id.tvTiTleText)
    TextView mTvTiTleText;
    private Activity mActivity;
    private View mView;
    private CommonAdapter<MemberCardListBean.DatasBean.MemberCardList> commonAdapter;
    private String TAG = "****换卡碎片";
    private int operate = 0;
    private String PriceId = "";//选中的价格ID
    private List<String> functionName = new ArrayList<>();  //功能模块名称
    private int Clickedposition = 0;
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean; //选中的会员卡
    private ArrayList<MemberCardListBean.DatasBean.MemberCardList> priceList = new ArrayList<>();     //会员卡价格阶梯集合
    private FragmentCallBack mFragmentCallBack;
    private EventBus mEventBus;
    private String storeId = "";
    private String name = "";


    public ReplaceCardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_replace_card, container, false);
        }

        ButterKnife.bind(this, mView);

        mPriceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        Bundle data = getArguments();//获得从activity中传递过来的值
        mMemberCardBean = (MemberMyCardListBean.DatasBean.MembercardrelationListBean) data.getSerializable("MemberCardBean");

        initView();

        return mView;
    }

    private void initView() {

        mTvItem1.setText("卡名");
        mTvItem2.setText("类型");
        getNetData();
    }


    /**
     * 获取价格阶梯List
     */
    private void getNetData() {
        if (mMemberCardBean == null) {
            return;
        }
        storeId = mMemberCardBean.getStoreId();
        int membcardType = mMemberCardBean.getMembcardType();
        OperatingFloorRequest.queryMembercardListByStoreId(storeId,membcardType+"", new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "成功: " + response.toString() + "");
                    MemberCardListBean memberCardListBean = JSON.parseObject(response.get(), MemberCardListBean.class);
                    if (!memberCardListBean.getResult().getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "查找失败,请稍后重试!", 1000);
                        return;
                    }
                    loadOnAdapter(memberCardListBean.getDatas().getMemberCardList());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "查找失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    //加载适配器
    private void loadOnAdapter(final List<MemberCardListBean.DatasBean.MemberCardList> list) {
        if (priceList.size() != 0) {
            priceList.clear();
        }
        priceList.addAll(list);
        commonAdapter = new CommonAdapter<MemberCardListBean.DatasBean.MemberCardList>(mActivity, R.layout.priceslist_item, list) {
            @Override
            protected void convert(ViewHolder holder, final MemberCardListBean.DatasBean.MemberCardList listBean, final int position) {
                TextView tvItem1 = holder.getView(R.id.tvItem1);
                TextView tvItem11 = holder.getView(R.id.tvItem11);
                TextView tvItem2 = holder.getView(R.id.tvItem2);
                TextView tvItem3 = holder.getView(R.id.tvItem3);
                RadioButton RadioButton1 = holder.getView(R.id.RadioButton1);
                RadioButton1.setVisibility(View.VISIBLE);
                tvItem3.setVisibility(View.GONE);
                View view = holder.getView(R.id.view);
                mTvItem1.setText("卡名");
                mTvItem2.setText("类型");
                tvItem1.setVisibility(View.GONE);
                tvItem11.setVisibility(View.VISIBLE);
                tvItem11.setText(listBean.getMembcardName());
                if (listBean.getMembcardType().equals("1")){
                    tvItem2.setText("次卡");
                }else if (listBean.getMembcardType().equals("2")){
                    tvItem2.setText("期限卡");
                }

                if (position == (list.size() - 1)) {
                    view.setVisibility(View.GONE);
                }
                if (position == operate) {
                    PriceId = listBean.getId(); //选中Id
                    name = listBean.getMembcardName();
                    RadioButton1.setChecked(true);
                } else {
                    RadioButton1.setChecked(false);
                }

                //选中 卡类型
                RadioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PriceId = listBean.getId(); //价格Id
                        operate = position;
                        name = listBean.getMembcardName();
                        commonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        mPriceRecyclerView.setAdapter(commonAdapter);
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(ObjectEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取对应的卡价格阶梯
        if (event.getType().equals("Recharge")) {
            mMemberCardBean = event.getMemberCardBean();
            initView();
            commonAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 换卡后返回
                case RESULT_FIRST_USER:
                    Bundle bundle = new Bundle();
                    bundle.putString("Operating","Reissue");
                    mFragmentCallBack.CallBackFun(bundle);
                    break;
            }
        }

    }

    @OnClick(R.id.llConfirm)
    public void onViewClicked() {
        if (mMemberCardBean.getFlagType()== 2){
            ToastUtils.showToCenters(mActivity,"该卡已经换过了",1000);
            return;
        }
        Intent intent = new Intent(mActivity, ReissueActivity.class);
        intent.putExtra("object",mMemberCardBean);
        intent.putExtra("ReplaceCardId",PriceId);
        intent.putExtra("ReplaceCardName",name);
        startActivityForResult(intent,RESULT_FIRST_USER);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
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
