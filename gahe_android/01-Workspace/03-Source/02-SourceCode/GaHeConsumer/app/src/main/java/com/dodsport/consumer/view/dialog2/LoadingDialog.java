package com.dodsport.consumer.view.dialog2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.consumer.R;


/**
 * 正在加载的动画
 */

public class LoadingDialog {


    View view;
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private Display display;
    private float size ;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    TextView textView;


    public LoadingDialog(Context context, float size)
    {
        this.context = context;
        this.size = size;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public LoadingDialog builder() {
        // 获取自定义Dialog布局中的控件
        view = LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * size), LinearLayout.LayoutParams.WRAP_CONTENT));


        setLayout();

        return this;
    }
    private void setLayout() {

        imageView = (ImageView) view.findViewById(R.id.loading_iv);

        textView  = (TextView) view.findViewById(R.id.loading_tv);
        imageView.setBackgroundResource(R.drawable.loading_anim_list);

        animationDrawable = (AnimationDrawable) imageView.getBackground();



    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title)
    {
        textView.setText(title+"");
    }

    public LoadingDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        animationDrawable.stop();
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        //setLayout();
        try {
            animationDrawable.start();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭
     */
    public void cancel()
    {
        dialog.cancel();
        animationDrawable.stop();
    }
}
