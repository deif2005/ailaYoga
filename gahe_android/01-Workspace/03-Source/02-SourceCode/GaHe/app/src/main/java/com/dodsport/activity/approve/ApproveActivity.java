package com.dodsport.activity.approve;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.ListFragmentPagerAdapter;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.fragment.operatingfloorfragment.Approve.ApproveFragment;
import com.dodsport.fragment.operatingfloorfragment.Approve.ApproveOKFragment;
import com.dodsport.model.BIllDetailBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.JsonUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.popupwindow.BillOfDocumentTypePopupWindow;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yanzhenjie.nohttp.NoHttp.getContext;


/**
 * 审批模块
 */
public class ApproveActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;
    @Bind(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @Bind(R.id.approveViewPager)
    ViewPager mApproveViewPager;


    private EventBus mEventBus;
    private List<String> beanList = new ArrayList<>();
    private ColorTransitionPagerTitleView simplePagerTitleView;
    private ApproveFragment mApproveFragment;
    private ApproveOKFragment mApproveOKFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ListFragmentPagerAdapter listStripFragmentPagerAdapter;
    private String TAG = "******";
    private String type;
    private int typeId = 0;    //默认全部单据
    private BillOfDocumentTypePopupWindow mBillOfDocumentType;
    private  List<BIllDetailBean.BillsBean> data = new ArrayList<>();
    public final List<BIllDetailBean.BillsBean> data1 = new ArrayList<BIllDetailBean.BillsBean>();
    final List<BIllDetailBean.BillsBean> data2 = new ArrayList<BIllDetailBean.BillsBean>();
    private Integer page = 1;   //1表示加载第一页
    private int typeId2;    //标记筛选单据类型
    public String approveStatus  = "1";     //1、待审批，2、已审批

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        ButterKnife.bind(this);
        mEventBus.getDefault().register(this);

        initView();
        pullUpdate();
    }


    /**
     * 初始化控件
     * */
    private void initView(){
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadIvOK.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("审批");
        mHeadTvOK.setText("筛选");
        mHeadIvOK.setImageResource(R.drawable.shai_xuan);


        beanList.add("待审批");
        beanList.add("已审批");
        mApproveFragment = new ApproveFragment();
        mApproveOKFragment = new ApproveOKFragment();
        fragmentList.add(mApproveFragment);
        fragmentList.add(mApproveOKFragment);
        listStripFragmentPagerAdapter = new ListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, beanList);
        mApproveViewPager.setAdapter(listStripFragmentPagerAdapter);
        focuTable();
    }

    /**下拉更新*/
    public void pullUpdate(){
        page = 1;
        getListBillInfo();
    }

    /**上滑加载*/
    public void pullLoading(){
        page = page + 1;
        getListBillInfo();
    }



    /**获取全部单据网络数据*/
    private void getListBillInfo(){
        String storeId = SPUtils.getUserDataBean(this).getStoreId();    //店铺ID
        Integer billType = typeId;      //单据类型
        String id = SPUtils.getUserDataBean(getContext().getApplicationContext()).getId();  //用户Id

        Log.i(TAG, "请求传参--->"+"storeId-->"+storeId+"\tbillType--->"+billType+"\tpage--->"+page+"\tapproveStatus--->"+approveStatus+"\t");
        //page 刷新页数
        OperatingFloorRequest.getListBillInfo(storeId, billType, page,"",approveStatus, new OnResponseListener<String>() {

            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "所有单据返回成功---->"+response.get().toString());
                try {
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")) {
                        switch (resultBean.getCode()) {

                            case "":

                                break;
                        }
                        return;
                    }
                    JSONObject json = new JSONObject(statusBean.getDatas());
                    JSONArray bills = json.getJSONArray("bills");

                    List<BIllDetailBean.BillsBean> billsBeen = JsonUtils.fromJSONArrayToList(bills, BIllDetailBean.BillsBean.class);
                    if (page == 1){
                        data.clear();
                        if (approveStatus.equals("1")){
                            data1.clear();
                        }else if (approveStatus.equals("2")){
                            data2.clear();
                        }
                    }
                    if (billsBeen.size() ==0  ||billsBeen == null){
                        if (page!=1){
                            ToastUtils.showButtom(ApproveActivity.this,"已经没有更多数据了!",800);
                        }
                        page = page-1;
                        return;
                    }else {
                        if (approveStatus.equals("1")){
                            data1.addAll(billsBeen);
                            mApproveOKFragment.addThis(ApproveActivity.this);
                            mApproveFragment.addData(ApproveActivity.this,data1);
                        }else {
                            data2.addAll(billsBeen);
                            mApproveOKFragment.addData(ApproveActivity.this,data2);
                        }
                        //data.addAll(billsBeen);
                        billsBeen.clear();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "所有单据返回失败---->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 初始化标题
     */
    private void focuTable() {

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return beanList == null ? 0 : beanList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(beanList.get(index));
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setNormalColor(Color.parseColor("#474747"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#49ACEB"));

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mApproveViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            /**
             * 设置底部线条
             * @param context
             * @return
             */
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#49ACEB"));
                indicator.setLineHeight(5);
                indicator.setLineWidth(200);
                return indicator;
            }

        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 100);
            }
        });
        ViewPagerHelper.bind(mMagicIndicator, mApproveViewPager);
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(AskForLeaveTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取用户选中请假类型
        if (event.getType().equals("ask")) {
            type = event.getMsg();
            typeId2 = event.getmPosition();
        }
    }

    @OnClick({R.id.head_ivBack, R.id.head_ivOK, R.id.head_tvOK, R.id.relativeLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_ivOK:
            case R.id.head_tvOK:
                mBillOfDocumentType = new BillOfDocumentTypePopupWindow(this,mHeadIvOK,new BillofDocumentClick());
                mBillOfDocumentType.showAtLocation(mHeadIvOK, Gravity.BOTTOM, 0,0);
                break;
            case R.id.relativeLayout:
                break;
        }
    }


    /**
     * 选中筛选类型
     * */
    private class BillofDocumentClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btDocument:       //选中类型的确定按钮
                    typeId = typeId2;
                    pullUpdate();
                    if (mBillOfDocumentType != null){
                        mBillOfDocumentType.dismiss();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.getDefault().unregister(this);
    }
}
