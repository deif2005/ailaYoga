package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.CardTypeEvent;
import com.dodsport.model.MemberCardListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ScreenUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


/**
 * 会员卡列表 筛选
 */

public class PreparationByScreeningPopupWindow extends PopupWindow{
    private Activity mActivity;
    private View mMenuView;
    private View.OnClickListener billofDocumentClick;
    private String TAG = "****筛选弹框--";
    private CommonAdapter<MemberCardListBean.DatasBean.MemberCardList> mCommonAdapter;
    private RecyclerView mRvPBS;


    public PreparationByScreeningPopupWindow(Activity activity, final View view, View.OnClickListener billofDocumentClick) {
        super(activity);
        this.mActivity = activity;
        this.billofDocumentClick = billofDocumentClick;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_pb_screening, null);
        LinearLayout mLlNumber = mMenuView.findViewById(R.id.llNumber); //按剩余次数
        LinearLayout mLlNumberOfDays = mMenuView.findViewById(R.id.llNumberOfDays);//按剩余天数
        mRvPBS = mMenuView.findViewById(R.id.rvPBS);
        mRvPBS.setLayoutManager(new GridLayoutManager(mActivity,2));


        mLlNumber.setOnClickListener(billofDocumentClick);
        mLlNumberOfDays.setOnClickListener(billofDocumentClick);
        setParams();
        getNetData();

    }

    /**
     * 获取网络数据
     * */
    private void getNetData(){
        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        OperatingFloorRequest.getListMemberCard(businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    MemberCardListBean memberCardListBean = JSON.parseObject(response.get(), MemberCardListBean.class);
                    if (!memberCardListBean.getResult().getCode().equals("0")) {
                            //获取失败
                        return;
                    }
                    ArrayList<MemberCardListBean.DatasBean.MemberCardList> memberCardList = (ArrayList<MemberCardListBean.DatasBean.MemberCardList>) memberCardListBean.getDatas().getMemberCardList();
                    loadOnAdapter(memberCardList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取会员卡数据列表失败返回-->" + response.toString() + "");
               //获取失败

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 加载适配器
     * */
    private void loadOnAdapter(ArrayList<MemberCardListBean.DatasBean.MemberCardList> memberCardList) {
        mCommonAdapter = new CommonAdapter<MemberCardListBean.DatasBean.MemberCardList>(mActivity,R.layout.pbs_item,memberCardList) {

            @Override
            protected void convert(ViewHolder holder, final MemberCardListBean.DatasBean.MemberCardList memberCardList, final int position) {

                Log.i(TAG, "获取员卡数据列表成功返回-->" + memberCardList.toString() + "");
                LinearLayout mLlCardType1 = holder.getView(R.id.llCardType1);   //按VIP计次卡
                TextView tvCardTypeName = holder.getView(R.id.tvCardTypename);
                tvCardTypeName.setText(memberCardList.getMembcardName());

                mLlCardType1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CardTypeEvent m = new CardTypeEvent();
                        m.setType("CardName");
                        m.setPositions(memberCardList.getId());
                        EventBus.getDefault().post(m);
                        dismiss();
                    }
                });

            }
        };
        mRvPBS.setAdapter(mCommonAdapter);

    }

    /**
     * 设置基本参数
     */
    private void setParams() {

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.take_photo_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        this.setBackgroundDrawable(dw);
        ScreenUtils.setBackgroundAlpha(mActivity, 0.7f);
//        setBackgroundAlpha(0.7f);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.relatview).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        ScreenUtils.setBackgroundAlpha(mActivity, 1.0f);
                        dismiss();
                    }
                }
                return true;

            }
        });
        //添加pop窗口关闭事件
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenUtils.setBackgroundAlpha(mActivity, 1.0f);
            }
        });
    }

}
