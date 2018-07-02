package com.dodsport.consumer.adapter.adapter_recyclerview.base;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(com.dodsport.consumer.adapter.adapter_recyclerview.base.ViewHolder holder, T t, int position);

}
