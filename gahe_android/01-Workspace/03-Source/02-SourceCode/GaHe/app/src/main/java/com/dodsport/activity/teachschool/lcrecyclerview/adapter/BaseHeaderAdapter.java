package com.dodsport.activity.teachschool.lcrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.oushangfeng.pinnedsectionitemdecoration.utils.FullSpanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oubowu on 2016/8/3 20:40.
 * 继承BaseMultiItemQuickAdapter的一个适配器基类
 */
public abstract class BaseHeaderAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_DATA = 2;
    protected List<T> mDatas;
    public BaseHeaderAdapter(ArrayList<T> data) {
        super(data);
        this.mDatas = data;
        addItemTypes();
    }



    protected abstract void addItemTypes();

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, TYPE_HEADER);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, TYPE_HEADER);
    }

}
