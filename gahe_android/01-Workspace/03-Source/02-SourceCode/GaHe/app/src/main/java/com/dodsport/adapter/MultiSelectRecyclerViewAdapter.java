package com.dodsport.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.model.StoreInfoListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 *
 * 关联门店 适配器
 */

@SuppressWarnings("unused")
public class MultiSelectRecyclerViewAdapter extends RecyclerView.Adapter {

    private SparseBooleanArray selectedItems;
    private ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> mArrayList;
    @SuppressWarnings("FieldCanBeLocal")
    private Context context;
    private ViewHolder.ClickListener clickListener;
    protected int mLayoutId;

    public MultiSelectRecyclerViewAdapter(ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> mArrayList, Context context, ViewHolder.ClickListener clickListener,final int layoutId) {
        this.mArrayList = mArrayList;
        this.context = context;
        this.clickListener = clickListener;
        this.selectedItems = new SparseBooleanArray();
        this.mLayoutId = layoutId;
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void switchSelectedState(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelectedState() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(itemLayoutView, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tvName.setText(mArrayList.get(position).getStoreName()+"");

        ((ViewHolder) holder).llStoreName.setBackgroundColor(isSelected(position) ? context.getResources().getColor(R.color.home_text_selected):context.getResources().getColor(R.color.gray_color) );
        ((ViewHolder) holder).tvName.setTextColor(isSelected(position) ? context.getResources().getColor(R.color.white):context.getResources().getColor(R.color.home_text_normal));
        //((ViewHolder) holder).tvName.setBackgroundColor(isSelected(position) ? Color.DKGRAY : Color.LTGRAY);
        //((ViewHolder) holder).tvName.setTextColor(isSelected(position) ? Color.RED : Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tvName;
        private ClickListener listener;
        private LinearLayout llStoreName;

        public ViewHolder(View itemLayoutView, ClickListener listener) {
            super(itemLayoutView);
            this.listener = listener;
            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);
            llStoreName = (LinearLayout)itemLayoutView.findViewById(R.id.llStoreName);
            itemLayoutView.setOnClickListener(this);
            itemLayoutView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return listener != null && listener.onItemLongClicked(getAdapterPosition());
        }

        public interface ClickListener {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }
    }
}