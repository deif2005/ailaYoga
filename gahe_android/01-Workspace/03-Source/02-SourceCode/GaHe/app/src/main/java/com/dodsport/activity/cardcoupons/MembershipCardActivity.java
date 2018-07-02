package com.dodsport.activity.cardcoupons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.CardTypeEvent;
import com.dodsport.model.MemberCardRelationListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.CircleImageView;
import com.dodsport.weight.popupwindow.PreparationByScreeningPopupWindow;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员卡模块
 */
public class MembershipCardActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.evSearch)
    EditText mEvSearch;
    @Bind(R.id.btSearch)
    Button mBtSearch;
    @Bind(R.id.btActivateACard)
    Button mBtActivateACard;
    @Bind(R.id.rvMembershipCard)
    RecyclerView mRvMembershipCard;
    @Bind(R.id.card_loadView)
    LoadingView mCardLoadView;

    private CommonAdapter<MemberCardRelationListBean.DatasBean.MembercardRelationList> mCommonAdapter;
    private String TAG = "****会员卡管理--";
    private PopupWindow mWindow;
    private String memberName="";
    private String phoneNum="";
    private String opencardSerialId="";
    private Integer page = 1;
    private Integer flagtimes = 0;
    private Integer flagdays = 0;
    private String membcardId ="";
    private EventBus mEventBus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_card);
        ButterKnife.bind(this);
        initView();
    }

    //初始化
    private void initView() {
        mRvMembershipCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadIvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("会员卡");
        mHeadIvOK.setImageResource(R.drawable.shai_xuan);
        mHeadTvOK.setText("筛选");

        mCardLoadView.showLoadingView();
        mEventBus.getDefault().register(this);      //注册

        getNetData();
    }

    /**
     *  查询门店中开卡信息
     *
     * token	string		是	口令
     * storeId	String		是	门店id
     * memberName	string			口令
     * phoneNum	String			电话
     * opencardSerialId	String			卡号
     * page	Integer		是	当前页
     * flagtimes	String			按剩余次数查询
     * flagdays	String			按剩余天数查询
     * membcardId	Integer		是	会员卡id
     * */
    private void getNetData() {
        String storeId = SPUtils.getUserDataBean(this).getStoreId();
        Log.i(TAG, "获取店家会员列表 -->" + storeId + "");
        String Search = "";
        if (!TextUtils.isEmpty(mEvSearch.getText().toString())){
            Search = mEvSearch.getText().toString();
        }

        OperatingFloorRequest.queryOpencardInfo(storeId,Search,page,flagtimes,flagdays,membcardId,new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "成功 -->" + response.get().toString() + "");

                    MemberCardRelationListBean memberListBean = JSON.parseObject(response.get(), MemberCardRelationListBean.class);
                    if (!memberListBean.getResult().getCode().equals("0")) {
                        switch (memberListBean.getResult().getCode()) {
                            case "5002":
                                ToastUtils.showToCenters(MembershipCardActivity.this, "未找到该门店数据!", 800);
                                break;
                        }
                        CardLoadView();
                        return;
                    }
                    if (mCardLoadView!=null){
                        mCardLoadView.showContentView();
                    }
                    loadOnAdapter(memberListBean.getDatas().getMembercardrelationList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "失败 -->" + response.toString() + "");
                CardLoadView();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 加载适配器
     *
     * @param baseMemberList
     */
    private void loadOnAdapter(List<MemberCardRelationListBean.DatasBean.MembercardRelationList> baseMemberList) {
        mCommonAdapter = new CommonAdapter<MemberCardRelationListBean.DatasBean.MembercardRelationList>(this, R.layout.membership_card_item, baseMemberList) {
            @Override
            protected void convert(ViewHolder holder, MemberCardRelationListBean.DatasBean.MembercardRelationList memberListBean, int position) {
                CircleImageView cvUserHead = holder.getView(R.id.cvUserHead);
                TextView tvUserCardNumber =  holder.getView(R.id.tvUserCardNumber);
                TextView tvUserName = holder.getView(R.id.tvUserName);
                TextView tvNamePhone = holder.getView(R.id.tvNamePhone);
                TextView tvActivationDate = holder.getView(R.id.tvActivationDate);
                Button btCardStatus = holder.getView(R.id.btCardStatus);
                TextView tvCardTypeName = holder.getView(R.id.tvCardTypeName);
                TextView tvCardNumber = holder.getView(R.id.tvCardNumber);
                TextView tvOperate = holder.getView(R.id.tvOperate);//操作
                TextView tvRecord = holder.getView(R.id.tvRecord);  //记录
                TextView tvNumber = holder.getView(R.id.tvNumber);  //卡的数量编号

//                tvNumber.setText(memberListBean.);
                tvUserCardNumber.setText("卡号:"+memberListBean.getOpencardSerialId()+"");
                tvUserName.setText(memberListBean.getMemberName());
                tvNamePhone.setText(memberListBean.getPhoneNum());
                String time = "";
                if (!TextUtils.isEmpty(memberListBean.getSpecactTime())){
                    time = memberListBean.getValidityTime().substring(0,10);
                    tvActivationDate.setText(time); //激活时间
                }else {
                    tvActivationDate.setText("未激活"); //激活时间
                }
                btCardStatus.setTextColor(mContext.getResources().getColor(R.color.white));
                if (memberListBean.getCardStatus()==1){
                    btCardStatus.setText("使用中");
                    btCardStatus.setBackground(mContext.getDrawable(R.drawable.shape_card_status_green_style));
                }else if (memberListBean.getCardStatus()==2){
                    btCardStatus.setText("已停卡");
                    btCardStatus.setBackground(mContext.getDrawable(R.drawable.shape_card_status_red_style));
                }
                if (memberListBean.getMembcardType()==1){
                    tvCardTypeName.setText(memberListBean.getMembcardName());
                    tvCardNumber.setText(memberListBean.getTimes()+" 次");
                }else if (memberListBean.getMembcardType()==2){
                    tvCardTypeName.setText(memberListBean.getMembcardName());
                    tvCardNumber.setText(memberListBean.getDays()+" 天");
                }


                //操作
                tvOperate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //记录
                tvRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };
        mRvMembershipCard.setAdapter(mCommonAdapter);
    }

    @OnClick({R.id.head_ivBack, R.id.head_ivOK, R.id.head_tvOK, R.id.btSearch, R.id.btActivateACard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_ivOK:    //选项
            case R.id.head_tvOK:
                    mWindow = new PreparationByScreeningPopupWindow(this,mBtSearch,new onClick());
                    mWindow.showAsDropDown(mBtSearch, Gravity.TOP,0,50);
                break;
            case R.id.btSearch:     //搜索
                if (TextUtils.isEmpty(mEvSearch.getText().toString())){

                    ToastUtils.showToCenters(this,"请输入关键字!",1000);
                    return;
                }
                /**
                * 搜索 （模糊查询）
                * */
                getNetData();
                break;
            case R.id.btActivateACard:  //开卡
                startActivity(new Intent(this, OpenMemberCardActivity.class));
                break;
        }
    }


    private class onClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
                switch (view.getId()){
                    case R.id.llNumber:     //按剩余次数
                        flagtimes = 1;
                        flagdays = 0;
                        membcardId ="";
                        Log.i(TAG, "现在是按次数");
                        break;
                    case R.id.llNumberOfDays:   //按剩余天数
                        Log.i(TAG, "现在是按天数---");
                        flagdays = 1;
                        flagtimes = 0;
                        membcardId ="";
                        break;
                }
            getNetData();
            if (mWindow!=null){
                mWindow.dismiss();
            }
        }
    }



    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(CardTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //添加卡类型
        if (event.getType().equals("CardName")) {
            membcardId = event.getPositions();
            getNetData();
        }

    }


    private void CardLoadView(){
        if (mCardLoadView!=null)
            mCardLoadView.showErrorView(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getNetData();
                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.getDefault().unregister(this);    //反注册
    }
}
