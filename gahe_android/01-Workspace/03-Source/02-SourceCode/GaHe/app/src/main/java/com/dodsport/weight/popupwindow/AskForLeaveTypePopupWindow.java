package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.dodsport.R;
import com.dodsport.utils.ScreenUtils;
import com.dodsport.weight.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * 请假休假单 类型选择器
 */

public class AskForLeaveTypePopupWindow extends PopupWindow{

    private Activity mActivity;
    private View mMenuView;
    private MultiLineRadioGroup mMultiLineRadioGroup;


    public AskForLeaveTypePopupWindow(Activity activity, final View view) {
        super(activity);
        this.mActivity = activity;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_ask_type, null);
        mMultiLineRadioGroup = mMenuView.findViewById(R.id.MultiLineRadioGroup);

        List<String> data = new ArrayList<>();
        data.add("事假");
        data.add("病假");
        data.add("丧假");
        data.add("婚假");
        data.add("产假");
        data.add("护理假");
        data.add("休假");
        data.add("其他");
        mMultiLineRadioGroup.addAll(data);
        setParams();

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
