package com.dodsport.consumer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * Created by Administrator on 2016/2/15.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mInflaterView;

    public CommonAdapter(Context context, List<T> datas, int view) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mInflaterView = view;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder = ViewHolder.get(mContext.getApplicationContext(), convertView, parent,
                //下面这个是你要动态加载的那个布局
              //  R.layout.item_listview, position);
              mInflaterView , position);
        convert(holder,getItem(position),position);
        return holder.getConvertView();
    }
    public abstract void convert(ViewHolder holder,T t,int position);

}