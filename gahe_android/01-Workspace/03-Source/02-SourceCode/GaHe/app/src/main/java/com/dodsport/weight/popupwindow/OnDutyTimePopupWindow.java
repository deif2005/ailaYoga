package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.askforleave.PickerView;
import com.dodsport.eventBus.CardTypeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 签到规则 选择排班时间
 */

public class OnDutyTimePopupWindow extends PopupWindow {

    Button mBtnCancel,mBtnSubmit;
    PickerView mStartDays,mStartMinute,mEndDays,mEndMinute;

    private View mMenuView;
    private Activity mContext;
    private boolean isSetAlpha = true;
    private String startDays="12";    //开始时间 小时
    private String startMinute = "30";//开始时间 分钟
    private String endDays ="12";     //结束时间 小时
    private String endMinute = "30";  //结束时间 分钟
    private String time = "";


    private List<String> StartMinute = new ArrayList<>();
    private List<String> EndMinute = new ArrayList<>();

    private List<String> StartMinute2 = new ArrayList<>();
    private List<String> EndMinute2 = new ArrayList<>();



    /**
     * @param context
     * @param itemsOnClick 事件
     * @param b            是否显示第三、四的控件
     * @param b            是否设置window背景透明度
     * @param strings      内容数组
     */
    public OnDutyTimePopupWindow(Activity context, boolean isSetAlpha, final String time, String... strings) {
        super(context);
        this.mContext = context;
        this.isSetAlpha =isSetAlpha;
        this.time = time;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_on_duty_time, null);
        mStartDays = mMenuView.findViewById(R.id.startDays);
        mStartMinute = mMenuView.findViewById(R.id.startMinute);
        mEndDays = mMenuView.findViewById(R.id.endDays);
        mEndMinute = mMenuView.findViewById(R.id.endMinute);
        mBtnCancel = mMenuView.findViewById(R.id.btnCancel);
        mBtnSubmit = mMenuView.findViewById(R.id.btnSubmit);

        isSetAlpha();

        //开始时间
        for (int i=0;i<60;i++){
            if (i < 10){
                EndMinute.add("0"+i);
            }else {
                 EndMinute.add(i+"");
            }
        }
        for (int a =0;a<24;a++){
            if (a<10){
                StartMinute.add("0"+a);
            }else {
                StartMinute.add(a+"");
            }
        }


        //结束时间
        for (int i=0;i<60;i++){
            if (i < 10){
                EndMinute2.add("0"+i);
            }else {
                EndMinute2.add(i+"");
            }
        }
        for (int a =0;a<24;a++){
            if (a<10){
                StartMinute2.add("0"+a);
            }else {
                StartMinute2.add(a+"");
            }
        }

        mStartDays.setData(StartMinute);
        mStartMinute.setData(EndMinute);
        mEndDays.setData(StartMinute2);
        mEndMinute.setData(EndMinute2);


        //开始时间（小时）
        mStartDays.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                startDays = text;
                //Log.i("***", "开始-->: "+text+"");
            }
        });
        //开始时间（分钟）
        mStartMinute.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                startMinute = text;
                //Log.i("***", "开始分钟-->: "+text+"");
            }
        });

        //结束时间（小时）
        mEndDays.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                endDays = text;
                //Log.i("***", "结束-->: "+text+"");
            }
        });

        //结束时间（分钟）
        mEndMinute.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                endMinute = text;
                //Log.i("***", "结束分钟-->: "+text+"");
            }
        });

        //确定
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardTypeEvent mCardTypeEvent = new CardTypeEvent();
                mCardTypeEvent.setType(time);
                mCardTypeEvent.setPositions(startDays); //开始 小时
                mCardTypeEvent.setCardType(startMinute);//开始 分钟
                mCardTypeEvent.setMsg(endDays); //结束 小时
                mCardTypeEvent.setName(endMinute);  //结束 分钟
                EventBus.getDefault().post(mCardTypeEvent);
            }
        });
        //取消
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    private void isSetAlpha(){
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
        if (isSetAlpha) {
            setBackgroundAlpha(mContext, 0.7f);
        }
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.rl_height).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
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
     *
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

    @OnClick({R.id.btnCancel, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                //销毁弹出框
                dismiss();
                break;
            case R.id.btnSubmit:

                break;
        }
    }


    private class poponDismissListener implements OnDismissListener {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(mContext, 1f);
        }
    }
}
