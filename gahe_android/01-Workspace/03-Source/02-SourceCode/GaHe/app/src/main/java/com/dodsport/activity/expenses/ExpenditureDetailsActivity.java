package com.dodsport.activity.expenses;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.view.LoadingView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 财务模块
 * <p>
 * 支出详情页面Activity
 */
public class ExpenditureDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "******";
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.rvExpenditureDetails)
    RecyclerView mRvExpenditureDetails;
    @Bind(R.id.pullToRefreshScrollView_ExpenditureDetails)
    PullToRefreshScrollView mPullToRefreshScrollViewExpenditureDetails;
    @Bind(R.id.ExpenditureDetails_loadView)
    LoadingView mExpenditureDetailsLoadView;

    private CommonAdapter<String> mCommonAdapter;
    private CommonAdapter<String> mCommonAdapter2;

    private List<String>ItemData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_details);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("店铺详情");
        mHeadIvBack.setOnClickListener(this);
        //设置RecyclerView为竖型布局
        mRvExpenditureDetails.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        final List<String> mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            mData.add("测试数据");
            ItemData.add("测试数据");
        }

        setCommonAdapter(mData);
    }


    public void setCommonAdapter(List<String> mData) {

        //添加是适配器 Sub layout
        mCommonAdapter = new CommonAdapter<String>(this,R.layout.expendituredetails_item_layout,mData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                RecyclerView mRved_sublayout = holder.getView(R.id.rved_sublayout);

                mRved_sublayout.setLayoutManager(new LinearLayoutManager(ExpenditureDetailsActivity.this));
                mCommonAdapter2 = new CommonAdapter<String>(ExpenditureDetailsActivity.this,R.layout.ed_item_itemlayout,ItemData) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                    }
                };
                mRved_sublayout.setAdapter(mCommonAdapter2);

            }
        };

        mRvExpenditureDetails.setAdapter(mCommonAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
        }
    }

}
