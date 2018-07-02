package com.dodsport.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/2/15.
 * 万能ViewHolder
 */
public class ViewHolder {

    /**
     * 缓存item视图以便复用item view的容器
     */
    private SparseArray<View> mViews;
    private int mPosition;//占时用不上
    private View mConvertView;
    private int t;
    private int i;
    private Context context;
    //    private ImageLoader imageLoader;
    private String path = "";//存储到SD卡
//    private AsyncImageLoader asyncLoader;
    public ViewHolder() {

    }

    private ViewHolder(Context context, ViewGroup parent, int layoutID) {
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        mConvertView.setTag(this);
    }
    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.context = context.getApplicationContext();
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 获取ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutID    item视图 布局ID
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutID) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutID);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 通过ViewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 给TextView赋值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        t++;
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 给控件设置背景
     *
     * @param viewId
     * @param background
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ViewHolder setViewBackground(int viewId, Drawable background) {
        View view = getView(viewId);
        view.setBackground(background);
        return this;
    }

    /**
     * 给图片赋值
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * 给Bitmap赋值
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Url访问网络赋值-图片-还未实现
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageURL(int viewId, String url) {
        i++;
        ImageView iv = getView(viewId);
        return this;
    }

    /**
     * 根据ImageView加载图片
     *
     * @param iv
     * @param url
     * @return
     */
    public ViewHolder setImageURL(ImageView iv, String url) {
        return this;
    }

}
