package com.dodsport.activity.cardcoupons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.CardTypeEvent;
import com.dodsport.model.MemberCardListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.popupwindow.CardDaletePopupWindow;
import com.dodsport.weight.popupwindow.CardEditPopupWindow;
import com.dodsport.weight.popupwindow.CardPopupWindow;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;
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

import static com.dodsport.R.id.textView1;

/**
 * 卡券设置
 */
public class CardCouponsIntercalateActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(textView1)
    TextView mTextView1;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.textView3)
    TextView mTextView3;
    @Bind(R.id.textView4)
    TextView mTextView4;
    @Bind(R.id.textView5)
    TextView mTextView5;
    @Bind(R.id.textView6)
    TextView mTextView6;
    @Bind(R.id.rvCard)
    RecyclerView mRvCard;
    @Bind(R.id.card_loadView)
    LoadingView mCardLoadView;
    @Bind(R.id.wsrlyout)
    WaveSwipeRefreshLayout mWsrlyout;

    private EventBus mEventBus;
    private String TAG = "****卡券设置--";
    private CommonAdapter<MemberCardListBean.DatasBean.MemberCardList> mCommonAdapter;
    private Context mContext;
    private String MemberCardId = "";
    private List<MemberCardListBean.DatasBean.MemberCardList> mMemberCardList = new ArrayList<>();
    private int positions = 999;
    private PopupWindow PupWindow;
    private String Position = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_coupons_intercalate);
        ButterKnife.bind(this);
        mContext = this;
        mEventBus.getDefault().register(this);  //注册
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setText("添加卡类型");
        mHeadTvOK.setTextSize(16);
        mHeadTvTitle.setText("卡券设置");
        mRvCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCardLoadView.showLoadingView();

        getMemberCardTypeList();



        //下拉刷新 上拉加载
        int homepage_refresh_spacing = 40;
        mWsrlyout.setProgressViewOffset(false, -homepage_refresh_spacing * 2, homepage_refresh_spacing);
        mWsrlyout.setColorSchemeResources(R.color.colorAccent);
        mWsrlyout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mWsrlyout!=null){
                            mWsrlyout.setLoading(false);
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoad() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mWsrlyout!=null){

                        }
                        mWsrlyout.setLoading(false);
                    }
                }, 2000);
            }

            @Override
            public boolean canLoadMore() {
                return true;
            }

            @Override
            public boolean canRefresh() {
                return true;
            }


        });


    }

    @OnClick({R.id.head_ivBack, R.id.head_tvOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_tvOK:    //添加卡类型
                CardPopupWindow mPopupWindow = new CardPopupWindow(this, new viewClick(), "");
                mPopupWindow.showAtLocation(mHeadTvOK, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    /**
     * 弹框中的点击事件
     */
    private class viewClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btConservation:   //删除会员卡
                    if (PupWindow != null) {
                        PupWindow.dismiss();
                    }
                    deleteMemberCard();
                    break;
            }
        }
    }

    /**
     * 删除会员卡
     */
    private void deleteMemberCard() {
        if (TextUtils.isEmpty(MemberCardId)) {
            return;
        }
        OperatingFloorRequest.delMembercardById(MemberCardId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "删除成功-->" + response.get().toString() + "");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")) {
                        ToastUtils.showToCenters(mContext, "删除失败,请稍后重试!", 1000);
                        return;
                    }
                    mMemberCardList.remove(positions);
                    mCommonAdapter.notifyItemRemoved(positions);
                    ToastUtils.showToCenters(mContext, "删除成功!", 1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "删除失败-->" + response.get().toString() + "");
                ToastUtils.showToCenters(mContext, "删除失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(CardTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //添加卡类型
        if (event.getType().equals("Card")) {
            String name = event.getName();
            String cardTypeId = event.getCardType();
            String msg = event.getMsg();
            Position = event.getPositions();
            addMemberCardType(name, cardTypeId, msg);

        } else if (event.getType().equals("CardEdit")) {      //卡编辑
            String name = event.getName();
            String cardTypeId = event.getCardType();
            String msg = event.getMsg();
            Position = event.getPositions();
            EditCardInfo(name, Position, msg);
            //Log.i(TAG, "编辑返回"+"name-->"+name+"\tcardTypeId-->"+cardTypeId+"\tmsg-->"+msg+"");
        }

    }

    /**
     * 编辑卡信息
     *
     * @param name
     * @param positions
     * @param msg
     */
    private void EditCardInfo(String name, String positions, String msg) {
        Integer membcardType = 1;
        if (TextUtils.isEmpty(positions)) {
            membcardType = Integer.parseInt(positions);
        }
        OperatingFloorRequest.updateMembercard(name, membcardType, msg, MemberCardId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "编辑成功!-->" + response.toString() + "");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "编辑失败!-->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 添加卡类型
     *
     * @param membcardName
     * @param membcardTypeId
     * @param remark
     */
    private void addMemberCardType(String membcardName, String membcardTypeId, String remark) {
        String creator = SPUtils.getUserDataBean(this).getId(); //创建人ID
        String businessId = SPUtils.getUserDataBean(this).getBusinessId();

        OperatingFloorRequest.addMemberCard(businessId, membcardName, membcardTypeId, remark, creator, Position, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "添加会员卡数据成功返回-->" + response.get().toString() + "");

                getMemberCardTypeList();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "添加会员卡数据失败返回-->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 获取卡类型列表
     */
    private void getMemberCardTypeList() {
        String businessId = SPUtils.getUserDataBean(this).getBusinessId();
        OperatingFloorRequest.getListMemberCard(businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取员卡数据列表成功返回-->" + response.get().toString() + "");
                MemberCardListBean memberCardListBean = JSON.parseObject(response.get(), MemberCardListBean.class);
                if (!memberCardListBean.getResult().getCode().equals("0")) {
                    CardLoadView();     //获取失败
                    return;
                }
                if (mCardLoadView != null)
                    mCardLoadView.showContentView();
                loadOnAdapter(memberCardListBean.getDatas().getMemberCardList());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取会员卡数据列表失败返回-->" + response.toString() + "");
                CardLoadView();     //获取失败

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void CardLoadView() {
        if (mCardLoadView != null)
            mCardLoadView.showErrorView(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMemberCardTypeList();
                }
            });
    }

    /**
     * 加载适配器
     *
     * @param memberCardList
     */
    private void loadOnAdapter(final List<MemberCardListBean.DatasBean.MemberCardList> memberCardList) {
        mMemberCardList.clear();
        mMemberCardList.addAll(memberCardList);
        mCommonAdapter = new CommonAdapter<MemberCardListBean.DatasBean.MemberCardList>(this, R.layout.card_coupons_intercalate_item, mMemberCardList) {
            @Override
            protected void convert(ViewHolder holder, final MemberCardListBean.DatasBean.MemberCardList memberCard, final int position) {
                TextView tvCardName = holder.getView(R.id.tvCardName);
                TextView textView1 = holder.getView(R.id.textView1);
                TextView textView2 = holder.getView(R.id.textView2);
                TextView textView3 = holder.getView(R.id.textView3);
                TextView textView4 = holder.getView(R.id.textView4);
                TextView textView5 = holder.getView(R.id.textView5);
                TextView textView6 = holder.getView(R.id.textView6);
                TextView textView7 = holder.getView(R.id.textView7);

                tvCardName.setText("【" + memberCard.getMembcardName() + "】");
                if (memberCard.getMembcardType().equals("1")) {
                    textView1.setText("次卡");
                } else {
                    textView1.setText("期限卡");
                }
                String sumcard = memberCard.getSumcard();
                if (TextUtils.isEmpty(sumcard)) {
                    textView2.setText(0 + "人");
                } else {
                    textView2.setText(memberCard.getSumcard() + "人");
                }

                //配置
                textView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                //设定
                textView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, SetPricesActivity.class);
                        intent.putExtra("CardInfo", memberCard);
                        startActivity(intent);
                    }
                });
                //绑定
                textView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, RelevanceStoreActivity.class);
                        intent.putExtra("CardInfo", memberCard);
                        startActivity(intent);
                    }
                });

                //编辑
                textView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MemberCardId = memberCard.getId();
                        PupWindow = new CardEditPopupWindow(CardCouponsIntercalateActivity.this, memberCard, "");
                        PupWindow.showAtLocation(mHeadTvOK, Gravity.BOTTOM, 0, 0);
                    }
                });

                //删除
                textView7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MemberCardId = memberCard.getId();
                        positions = position;
                        PupWindow = new CardDaletePopupWindow(CardCouponsIntercalateActivity.this, new viewClick(), "");
                        PupWindow.showAtLocation(mHeadTvOK, Gravity.BOTTOM, 0, 0);
                    }
                });
            }
        };

        mRvCard.setAdapter(mCommonAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.getDefault().unregister(this);    //反注册
    }
}
