package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dodsport.R;


/**
 * 选择照片的popupWindow + 男女选择
 */

public class SelectPicPopupWindow extends PopupWindow{

    private TextView btn_take_photo, btn_pick_photo;
    private LinearLayout btn_cancel;
    private View mMenuView;
    private Activity mContext;

    private LinearLayout llReport;
    private TextView tvThree,tvFour;

    /**
     *
     * @param context
     * @param itemsOnClick   事件
     * @param b      是否显示第三、四的控件
     * @param b      是否设置window背景透明度
     * @param strings   内容数组
     */
    public SelectPicPopupWindow(Activity context, View.OnClickListener itemsOnClick, boolean b, boolean isSetAlpha, String... strings) {
        super(context);
        this.mContext =  context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_select_photo, null);
        btn_take_photo = (TextView) mMenuView.findViewById(R.id.photograph);  //拍照
        btn_pick_photo = (TextView) mMenuView.findViewById(R.id.albums);  //从相册获取
        btn_cancel = (LinearLayout) mMenuView.findViewById(R.id.cancel);

        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
           }
        });

        btn_take_photo.setText(strings[0]+"");
        btn_pick_photo.setText(strings[1]+"");

        if(b)
        {

            tvThree = (TextView) mMenuView.findViewById(R.id.tvThree);  //3
            tvFour = (TextView) mMenuView.findViewById(R.id.tvFour);  //4
            llReport = (LinearLayout) mMenuView.findViewById(R.id.llReportVisible);
            llReport.setVisibility(View.VISIBLE);

            tvThree.setText(strings[2]+"");
            tvFour.setText(strings[3]+"");

            tvThree.setOnClickListener(itemsOnClick);
            tvFour.setOnClickListener(itemsOnClick);
        }else {
         //   llReport.setVisibility(View.GONE);
        }

        //设置按钮监听
        btn_pick_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);

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
        if(isSetAlpha)
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
