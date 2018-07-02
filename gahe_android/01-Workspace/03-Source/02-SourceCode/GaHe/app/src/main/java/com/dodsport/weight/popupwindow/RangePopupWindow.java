package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.CardTypeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;


/**
 * 考勤规则 选择WIFI或者考勤范围
 */

public class RangePopupWindow extends PopupWindow {

    private RecyclerView mRecyclerView;
    private List<String> data = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;

    private View mMenuView;
    private Activity mContext;
    private boolean isSetAlpha = true;
    private int po = 0;
    private String range = "";


    /**
     * @param context
     * @param itemsOnClick 事件
     * @param b            是否显示第三、四的控件
     * @param b            是否设置window背景透明度
     * @param strings      内容数组
     */
    public RangePopupWindow(Activity context, boolean isSetAlpha,String s, List<String> data,int poi,String... strings) {
        super(context);
        this.mContext = context;
        this.isSetAlpha =isSetAlpha;
        this.range = s;
        this.data = data;
        this.po = poi;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_range, null);
        mRecyclerView = mMenuView.findViewById(R.id.rvRange);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        isSetAlpha();

        mCommonAdapter = new CommonAdapter<String>(mContext,R.layout.popupwindow_range_item,data) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                RadioButton mRadioButton = holder.getView(R.id.RadioButton1);
                TextView tvRange = holder.getView(R.id.tvRange);
                tvRange.setText(s);

                    if (po == position){
                        mRadioButton.setChecked(true);
                    }else {
                        mRadioButton.setChecked(false);
                }

                mRadioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        po = position;
                        mCommonAdapter.notifyDataSetChanged();
                        CardTypeEvent m = new CardTypeEvent();
                        m.setPosition(po);
                        m.setType(range);
                        EventBus.getDefault().post(m);
                    }
                });
            }
        };

        mRecyclerView.setAdapter(mCommonAdapter);

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

            @Override
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


    private class poponDismissListener implements OnDismissListener {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(mContext, 1f);
        }
    }
}
