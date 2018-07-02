package com.dodsport.consumer.view.dialog2;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.consumer.R;


/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class SimpleDialog {

    View view;
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private TextView btn_pos;
    private Display display;
    private float size ;

    public SimpleDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        builder();
    }


    public SimpleDialog(Context context, float size)
    {
        this.context = context;
        this.size = size;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

        builder();
    }

    public SimpleDialog builder() {
        // 获取自定义Dialog布局中的控件
        view = LayoutInflater.from(context).inflate(R.layout.dialog_simple,null);

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

        txt_title = (TextView) view.findViewById(R.id.dialog_title);
        btn_pos= (TextView) view.findViewById(R.id.dialog_enter);
        txt_msg= (TextView) view.findViewById(R.id.dialog_msg);
    }
    public SimpleDialog setTitle(String title) {
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public SimpleDialog setMsg(String msg) {
        if ("".equals(msg)) {
            if(txt_msg!=null)
                txt_msg.setText("内容");
        } else {
            if(txt_msg!=null)
                txt_msg.setText(msg);
        }
        return this;
    }

    public SimpleDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public SimpleDialog setPositiveButton(String text,
                                              final View.OnClickListener listener) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 显示
     */
    public void show() {
//        setLayout();
        try {

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
    }
}
