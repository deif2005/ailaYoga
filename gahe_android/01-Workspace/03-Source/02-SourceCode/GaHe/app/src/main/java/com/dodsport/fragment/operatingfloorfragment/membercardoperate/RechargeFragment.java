package com.dodsport.fragment.operatingfloorfragment.membercardoperate;

import android.app.Activity;
import android.os.Bundle;
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
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.ObjectEvent;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.model.PricesListBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员卡操作 充值Fragment
 */
public class RechargeFragment extends BaseFragment {

    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.tvItem2)
    TextView mTvItem2;
    @Bind(R.id.layout_price)
    LinearLayout mLayoutPrice;
    @Bind(R.id.Price_RecyclerView)
    RecyclerView mPriceRecyclerView;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;

    private Activity mActivity;
    private View mView;
    private CommonAdapter<PricesListBean.DatasBean.ListBean> commonAdapter;
    private String TAG = "****充值碎片";
    private int operate = 0;
    private String PriceId = "";//选中的价格ID
    private List<String> functionName = new ArrayList<>();  //功能模块名称
    private int Clickedposition = 0;
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean; //选中的会员卡
    private ArrayList<PricesListBean.DatasBean.ListBean> priceList = new ArrayList<>();     //会员卡价格阶梯集合
    private FragmentCallBack mFragmentCallBack;
    private EventBus mEventBus;
    private String mMemberCardId = "";

    public RechargeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_recharge, container, false);
        }
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        mPriceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        Bundle data = getArguments();//获得从activity中传递过来的值
        mMemberCardBean = (MemberMyCardListBean.DatasBean.MembercardrelationListBean) data.getSerializable("MemberCardBean");
        getNetData();
    }


    /**
     * 获取价格阶梯List
     */
    private void getNetData() {
        if (mMemberCardBean == null) {
            return;
        }
        mMemberCardId = mMemberCardBean.getMembcardId();
        OperatingFloorRequest.getRechargegradList(mMemberCardId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "成功:---> " + response.get().toString() + "");
                    PricesListBean pricesListBean = JSON.parseObject(response.get(), PricesListBean.class);
                    if (!pricesListBean.getResult().getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "查找失败,请稍后重试!", 1000);
                        return;
                    }
                    loadOnAdapter(pricesListBean.getDatas().getList());

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
    private void loadOnAdapter(final List<PricesListBean.DatasBean.ListBean> list) {
        if (priceList.size() != 0) {
            priceList.clear();
        }
        priceList.addAll(list);
        commonAdapter = new CommonAdapter<PricesListBean.DatasBean.ListBean>(mActivity, R.layout.priceslist_item, list) {
            @Override
            protected void convert(ViewHolder holder, final PricesListBean.DatasBean.ListBean listBean, final int position) {
                TextView tvItem1 = holder.getView(R.id.tvItem1);
                TextView tvItem2 = holder.getView(R.id.tvItem2);
                TextView tvItem3 = holder.getView(R.id.tvItem3);
                RadioButton RadioButton1 = holder.getView(R.id.RadioButton1);
                RadioButton1.setVisibility(View.VISIBLE);
                tvItem3.setVisibility(View.GONE);
                View view = holder.getView(R.id.view);
                if (mMemberCardBean.getMembcardType() == 2) {
                    tvItem1.setText(listBean.getMonths() + "");
                    mTvItem1.setText("月数");
                } else {
                    tvItem1.setText(listBean.getTimes() + "");
                    mTvItem1.setText("次数");
                }
                tvItem2.setText(listBean.getNominalAmount() + "");
                if (position == (list.size() - 1)) {
                    view.setVisibility(View.GONE);
                }
                if (position == operate) {
                    PriceId = listBean.getId(); //价格Id
                    RadioButton1.setChecked(true);
                } else {
                    RadioButton1.setChecked(false);
                }

                //选中 卡价格阶梯
                RadioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PriceId = listBean.getId(); //价格Id
                        operate = position;
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
            getNetData();
            commonAdapter.notifyDataSetChanged();
        }

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

    @OnClick(R.id.llConfirm)
    public void onViewClicked() {
        String id = SPUtils.getUserDataBean(getActivity().getApplicationContext()).getId();
        final String relationId  = mMemberCardBean.getId();
        OperatingFloorRequest.memberCardRecharge(relationId, PriceId, id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "充值成功--->"+response.get().toString()+"");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")){
                    switch (resultBean.getCode()){
                        case "":
                            break;
                    }
                    ToastUtils.showToCenters(mActivity,"充值失败,请稍后重试!",1000);
                    return;
                }

                ToastUtils.showToCenters(mActivity,"充值成功!",1000);
                Bundle bundle = new Bundle();
                bundle.putString("Operating","Recharge");
                mFragmentCallBack.CallBackFun(bundle);

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"充值失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
