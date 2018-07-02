package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.CardTypeEvent;
import com.dodsport.model.CardTypeListBean;
import com.dodsport.model.MemberCardListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择照片的popupWindow + 男女选择
 */

public class CardEditPopupWindow extends PopupWindow{

    private View mMenuView;
    private Activity mContext;
    public RelativeLayout RvCarddType;
    private RecyclerView rvCardTypeName;
    public ImageView ivType;
    public TextView tvCardTypeName;
    public LinearLayout llCardType;
    private List<String> CardTypeName = new ArrayList<>();
    public int type = 9999;
    public String CardTypeId = "";
    private CommonAdapter<String> mCommonAdapter;
    private MemberCardListBean.DatasBean.MemberCardList memberCard;
    private String memberCardType = "";


    /**
     *
     * @param context
     * @param strings   内容数组
     */
    public CardEditPopupWindow(Activity context, MemberCardListBean.DatasBean.MemberCardList memberCard, String... strings) {
        super(context);
        this.mContext = context;
        this.memberCard = memberCard;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popuowindow_cardedit, null);

        RvCarddType = mMenuView.findViewById(R.id.rlCarddType);//卡类型
        Button btConservation = mMenuView.findViewById(R.id.btConservation); //保存
        Button btCancel = mMenuView.findViewById(R.id.btCancel);    //取消
        final EditText evCardName = mMenuView.findViewById(R.id.evCardName);  //卡名称
        final EditText evCardIntroduce = mMenuView.findViewById(R.id.evCardIntroduce);    //卡简介
        llCardType = mMenuView.findViewById(R.id.llCardType);
        rvCardTypeName = mMenuView.findViewById(R.id.rvCardTypeName);
        rvCardTypeName.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        ivType = mMenuView.findViewById(R.id.ivType);
        tvCardTypeName = mMenuView.findViewById(R.id.tvCardTypeName);

        Log.i("****", "会员卡信息"+memberCard.toString()+"");
        evCardName.setText(memberCard.getMembcardName());
        evCardIntroduce.setText(memberCard.getRemark());
        if (memberCard.getMembcardType().equals("1")){
            tvCardTypeName.setText("次卡");
            memberCardType = "1";
        }else {
            tvCardTypeName.setText("期限卡");
            memberCardType = "2";
        }

        //展示卡种类型
        RvCarddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvCarddType.setEnabled(false);
                OperatingFloorRequest.getListMemberCardType(new OnResponseListener<String>() {
                    @Override
                    public void onStart(int what) {
                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        CardTypeListBean cardTypeListBean = JSON.parseObject(response.get(), CardTypeListBean.class);
                        if (!cardTypeListBean.getResult().getCode().equals("0")) {
                            switch (cardTypeListBean.getResult().getCode()) {
                                case "4003":
                                    ToastUtils.showToCenters(mContext, "获取卡类型失败,请稍后重试!", 800);
                                    break;
                            }
                            RvCarddType.setEnabled(true);
                            return;
                        }
                        if (CardTypeName.size() != 0) {
                            CardTypeName.clear();
                        }
                        for (int i = 0; i < cardTypeListBean.getDatas().getMemberCardTypeList().size(); i++) {
                            CardTypeName.add(cardTypeListBean.getDatas().getMemberCardTypeList().get(i).getMembcardTypeName());
                        }
                        showCardTypeNmae(CardTypeName, cardTypeListBean);
                        Log.i("****回去卡券类型", "成功返回--> " + response.get().toString() + "");
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        ToastUtils.showToCenters(mContext, "获取卡类型失败,请稍后重试!", 800);
                        RvCarddType.setEnabled(true);
                    }

                    @Override
                    public void onFinish(int what) {

                    }
                });
                llCardType.setVisibility(View.VISIBLE);
                ivType.setImageResource(R.drawable.xiang_xia);
            }
        });


    //保存按钮
        btConservation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String CardName = evCardName.getText().toString();
                if (TextUtils.isEmpty(CardName)){
                    ToastUtils.showToCenters(mContext,"卡类名称不能为空!",1000);
                }else {
                    CardTypeEvent cardTypeEvent = new CardTypeEvent();
                    cardTypeEvent.setType("CardEdit");
                    cardTypeEvent.setName(CardName);
                    cardTypeEvent.setMsg(evCardIntroduce.getText().toString());
                    cardTypeEvent.setCardType(CardTypeId);
                    cardTypeEvent.setPositions(memberCardType);
                    EventBus.getDefault().post(cardTypeEvent);
                    dismiss();
                }
            }
        });

        //取消按钮
        btCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
           }
        });
        setParams();
    }


    /**加载卡券类型列表*/
    private void showCardTypeNmae(List<String> cardTypeName, final CardTypeListBean cardTypeListBean){
        mCommonAdapter = new CommonAdapter<String>(mContext,R.layout.companyname_item,cardTypeName) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                final TextView tvCompanyName_item = holder.getView(R.id.tvCompanyName_item);
                final LinearLayout llName = holder.getView(R.id.llName);

                if (memberCard.getMembcardTypeId().equals(cardTypeListBean.getDatas().getMemberCardTypeList().get(position).getId()) || type == position){
                    type = position;
                    CardTypeId = cardTypeListBean.getDatas().getMemberCardTypeList().get(position).getId();
                    tvCompanyName_item.setTextColor(mContext.getResources().getColor(R.color.white));
                    llName.setBackgroundColor(mContext.getResources().getColor(R.color.home_text_selected));
                }else {
                    tvCompanyName_item.setTextColor(mContext.getResources().getColor(R.color.home_text_normal));
                    llName.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                tvCompanyName_item.setText(s.toString());
                llName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RvCarddType.setEnabled(true);
                        type = position;
                        ivType.setImageResource(R.drawable.xiayibu);
                        tvCardTypeName.setText(CardTypeName.get(position));
                        tvCompanyName_item.setTextColor(mContext.getResources().getColor(R.color.white));
                        llName.setBackgroundColor(mContext.getResources().getColor(R.color.home_text_selected));
                        llCardType.setVisibility(View.GONE);
                        CardTypeId = cardTypeListBean.getDatas().getMemberCardTypeList().get(position).getId();
                    }
                });
            }
        };

        rvCardTypeName.setAdapter(mCommonAdapter);
    }


    //属性设置
    private void setParams() {
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.take_photo_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        this.setBackgroundDrawable(dw);
        if(true)
        {
            setBackgroundAlpha(mContext,0.7f);
        }
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.rl_height).getTop();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        //添加pop窗口关闭事件
        this.setOnDismissListener(new poponDismissListener());
    }

    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明
     */
    public void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }


    private class poponDismissListener implements OnDismissListener {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(mContext,1f);
        }
    }
}
