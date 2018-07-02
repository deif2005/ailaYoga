package com.dodsport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.CompanyListActivity;
import com.dodsport.model.CompanyListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 *
 * 门店列表展示 适配器
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private List<CompanyListBean.DatasBean.BusinessInfoListBean> groupArray;
    private List<List<CompanyListBean.DatasBean.BusinessInfoListBean.StoreInfoListBean>> childArray;
    private CompanyListActivity mContext;

    public ExpandableAdapter(CompanyListActivity context, List<CompanyListBean.DatasBean.BusinessInfoListBean> groupArray, List<List<CompanyListBean.DatasBean.BusinessInfoListBean.StoreInfoListBean>> childArray){
        mContext = context;
        this.groupArray = groupArray;
        this.childArray = childArray;
    }

    @Override
    public int getGroupCount() {
        return groupArray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if(view == null){
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.expandlist_group, null);
            holder.groupName = (TextView)view.findViewById(R.id.tv_group_name);
            holder.arrow = (ImageView)view.findViewById(R.id.iv_arrow);
            view.setTag(holder);
        }else{
            holder = (GroupHolder)view.getTag();
        }

        final int size = childArray.get(groupPosition).size();

        //判断是否已经打开列表
        if(isExpanded){
            if (size!=0){
                holder.arrow.setBackgroundResource(R.drawable.xiang_xia);
            }
        }else{
            holder.arrow.setBackgroundResource(R.drawable.xiayibu);
        }

        holder.groupName.setText(groupArray.get(groupPosition).getBusinessName()+"");
        holder.groupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (size == 0){
                    mContext.Blocked_Out(groupArray.get(groupPosition));
                }
//                Log.i("*****", "选中的商家--->"+groupArray.get(groupPosition).toString()+"");
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if(view == null){
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.expandlist_item, null);
            holder.childName = (TextView)view.findViewById(R.id.tv_child_name);

            view.setTag(holder);
        }else{
            holder = (ChildHolder)view.getTag();
        }
        holder.childName.setText(childArray.get(groupPosition).get(childPosition).getStoreName());

        holder.childName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.initV(childArray.get(groupPosition).get(childPosition));

            }
        });
        return view;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        public TextView groupName;
        public ImageView arrow;
    }

    class ChildHolder{
        public TextView childName;
        public ImageView sound;
        public ImageView divider;
        public LinearLayout llayout;
    }
}
