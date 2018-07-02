package com.dodsport.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.model.MembercardRecordListBean;

import java.util.ArrayList;

/**
 * <p>
 * 主播信息 动态模块Recyclview适配器
 */

public class CardRecordXRecyclerAdapter extends RecyclerView.Adapter<CardRecordXRecyclerAdapter.MyViewHolder> {

    private final static String TAG = "*****Adapter";

    private Activity context = null;
    private ArrayList<MembercardRecordListBean.DatasBean.MembercardRecordList> mMembercardRecordList = new ArrayList<>();

    public CardRecordXRecyclerAdapter(Activity context, ArrayList<MembercardRecordListBean.DatasBean.MembercardRecordList> membercardRecordList) {
        this.mMembercardRecordList  = membercardRecordList;
        this.context = context;

        Log.i(TAG, "适配器进来---->");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.swingcard_record_item, parent, false);

        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            if (!TextUtils.isEmpty(mMembercardRecordList.get(position).getContentStr().toString()) && holder.tvitem1!=null){
                holder.tvitem1.setText(mMembercardRecordList.get(position).getContentStr()+"");
            }
            if (!TextUtils.isEmpty(mMembercardRecordList.get(position).getCreaterTime())){
                String itme1 = mMembercardRecordList.get(position).getCreaterTime().substring(0, 10);
                String itme2 = mMembercardRecordList.get(position).getCreaterTime().substring(10, 19);
                holder.tvitem2.setText(itme1+"\n"+itme2+"");
            }
            if (!TextUtils.isEmpty(mMembercardRecordList.get(position).getEmpName())){
                holder.tvitem3.setText(mMembercardRecordList.get(position).getEmpName()+"");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "适配器捕获到异常");
        }


    }


    @Override
    public int getItemCount() {
        return mMembercardRecordList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    //初始化控件
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvitem1,tvitem2,tvitem3;



                    public MyViewHolder(View itemView) {
                        super(itemView);

                        tvitem1 = (TextView) itemView.findViewById(R.id.TextItem1);
                        tvitem2 = (TextView) itemView.findViewById(R.id.TextItem2);
                        tvitem3 = (TextView) itemView.findViewById(R.id.TextItem3);


                    }



            }

}
