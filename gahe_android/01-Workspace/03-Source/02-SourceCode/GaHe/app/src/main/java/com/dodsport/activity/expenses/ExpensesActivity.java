package com.dodsport.activity.expenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.TimeUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 财务模块 费用支出页面
 */
public class ExpensesActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "******";
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.rlexpenses)
    RecyclerView mRlexpenses;
    @Bind(R.id.llmonth)
    LinearLayout mLlmonth;
    //    @Bind(R.id.pullToRefreshScrollView_expenses)
//    PullToRefreshScrollView mPullToRefreshScrollViewExpenses;
    @Bind(R.id.follow_loadView)
    LoadingView mFollowLoadView;

    @Bind(R.id.tvmoon)
    TextView mTvmoon;
    @Bind(R.id.tvyear)
    TextView mTvyear;
    @Bind(R.id.ivdownward)
    ImageView mIvdownward;
    @Bind(R.id.wsrlyout)
    WaveSwipeRefreshLayout mWsrlyout;

    private TimePickerView pvTime;

    private CommonAdapter<String> mCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        ButterKnife.bind(this);
        initView();
        addTime();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("支出");
        mHeadIvBack.setOnClickListener(this);
        mLlmonth.setOnClickListener(this);

        mRlexpenses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add("测试数据");
        }

        setAdapter(mData);

        mRlexpenses.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy -= dy;
                if (dy < 10) {
//                    mLlmonth.setVisibility(View.VISIBLE);
                } else if (dy > 5) {
//                    mLlmonth.setVisibility(View.GONE);
                }
            }
        });



        //下拉刷新 上拉加载
        int homepage_refresh_spacing = 40;
        mWsrlyout.setProgressViewOffset(false, -homepage_refresh_spacing * 2, homepage_refresh_spacing);
        mWsrlyout.setColorSchemeResources(R.color.colorAccent);
        mWsrlyout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWsrlyout.setRefreshing(false);
                    }
                },2000);
            }

            @Override
            public void onLoad() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWsrlyout.setLoading(false);
                    }
                },2000);
            }

            @Override
            public boolean canLoadMore() {
                return true;
            }

            @Override
            public boolean canRefresh() {
                return true;
            }


        });

//
//        mPullToRefreshScrollViewExpenses.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//
//                mPullToRefreshScrollViewExpenses.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //  pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间:");
//                        mPullToRefreshScrollViewExpenses.getLoadingLayoutProxy().setPullLabel("下拉刷新");
//                        mPullToRefreshScrollViewExpenses.onRefreshComplete();
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                mPullToRefreshScrollViewExpenses.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // mFollowTextdata.setVisibility(View.VISIBLE);
//                        mPullToRefreshScrollViewExpenses.getLoadingLayoutProxy().setPullLabel("上拉刷新");
//
//
//                        mPullToRefreshScrollViewExpenses.onRefreshComplete();
//                    }
//                }, 2000);
//            }
//        });

    }

    /**
     * 加载适配器
     */
    private void setAdapter(List<String> mData) {

        mCommonAdapter = new CommonAdapter<String>(this, R.layout.expenses_order_for_item, mData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView mTitle = holder.getView(R.id.tvTitle); //标题
                TextView mLink_relative_ratio = holder.getView(R.id.tvLink_relative_ratio); //环比值
                TextView mOn_year_on_year_basis = holder.getView(R.id.tvOn_year_on_year_basis); //同比值
                TextView mPay = holder.getView(R.id.tvPay); //支付费用
                TextView mDetail = holder.getView(R.id.tvDetail); //查看详情

                //金额转换
                NumberFormat nf = new DecimalFormat(",##0");//如果保留 写成,##0.00
                String format = nf.format(100000000.00);


                //跳转到 详情页面
                mDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ExpensesActivity.this, ExpenditureDetailsActivity.class));
                    }
                });

            }
        };

        mRlexpenses.setAdapter(mCommonAdapter);
    }


    /**
     * 时间选择器
     */
    private void addTime() {
        // 时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAYS);
        // 控制时间范围
//		 Calendar calendar = Calendar.getInstance();
//		 pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
//		 calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                try {

                    mIvdownward.setImageResource(R.drawable.xiayibu);

                    String datetime = TimeUtils.parseDate(date);

                    long currentTimeMillis = System.currentTimeMillis();

                    long time = date.getTime();

                    if (time > currentTimeMillis) {
                        ToastUtils.showToCenter(ExpensesActivity.this, "不能大于当前时间", 0);
                    } else {
                        String mYear = datetime.substring(0, 4);
                        String moon = datetime.substring(5, 7);
                        String mMoon = null;
                        String Year = null;

                        if (!TextUtils.isEmpty(moon)) {
                            int i = Integer.parseInt(moon);
                            int s = i + 1;
                            if (s == 13) {
                                s = 1;
                            }
                            if (s == 1) {
                                int year = Integer.parseInt(mYear);
                                Year = (year + 1) + "";
                            } else {
                                Year = mYear;
                            }
                            if (s > 9 || s == 12) {
                                mMoon = s + "";
                            } else {
                                mMoon = s + "";
                                mMoon = "0" + mMoon;
                            }

                        }
                        mTvyear.setText(Year + "年");
                        mTvmoon.setText(mMoon + "月");

                        //Log.i(TAG, "onTimeSelect: 选择的时间" + datetime + "\t" + mYear + "\t" + mMoon + "\t" + moon);
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llmonth:
                mIvdownward.setImageResource(R.drawable.xiang_xia);
                pvTime.show();
                break;
        }
    }


}
