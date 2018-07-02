package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.teachschool.lcrecyclerview.adapter.BaseHeaderAdapter;
import com.dodsport.activity.teachschool.lcrecyclerview.entitiy.PinnedHeaderEntity;
import com.dodsport.model.CoursePlanListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.TimeUtils;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.oushangfeng.pinnedsectionitemdecoration.callback.OnHeaderClickListener;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 团课排课
 */
public class LeagueClassActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.llLastWeek)
    LinearLayout mLlLastWeek;
    @Bind(R.id.llThisWeek)
    LinearLayout mLlThisWeek;
    @Bind(R.id.llNextWeek)
    LinearLayout mLlNextWeek;
    @Bind(R.id.rvLeagueClassList)
    RecyclerView mRvLeagueClassList;
    @Bind(R.id.loadingView)
    LoadingView mLoadingView;
    @Bind(R.id.llErrorDisplay)
    LinearLayout mLlErrorDisplay;
    @Bind(R.id.CoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    private Activity mActivity;
    private static final int REQUEST_CAMERA_CODE = 10;
    private Handler mHandler;
    private String TAG = "***周课排课--->";
    private String netTime = "";
    private Date date = null;
    private ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> coursePlanList;      //一周计划
    private BaseHeaderAdapter<PinnedHeaderEntity<CoursePlanListBean.DatasBean.CoursePlanList>> mAdapter;
    private int weekIndex = 0;  //周序号
    private int weekIndex2 = 0; //本周
    private String classyear = "";  //  年
    private boolean adapter = true;


    public class mHanlder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            try {
                switch (msg.what) {
                    case 1:
                        if (TextUtils.isEmpty(netTime)) {
                            Date now = TimeUtils.now();
                            netTime = TimeUtils.parseDate(now);
                        }
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        date = format.parse(netTime);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setFirstDayOfWeek(Calendar.MONDAY);
                        calendar.setTime(date);
                        weekIndex = calendar.get(Calendar.WEEK_OF_YEAR);
                        classyear = netTime.substring(0, 4);
                        weekIndex = weekIndex - 1;  //周序号
                        weekIndex2 = weekIndex;     //本周
                        getLeagueLecture();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_class);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("团课排课");
        mHeadTvOK.setText("创建计划");
        mHandler = new mHanlder();
        mLoadingView.showLoadingView();

        /**RecyclerView设置*/
        mRvLeagueClassList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        //获取时间
        getNetTime();

    }

    /**
     * 加载适配器
     */
    private void initAdapter() {

        final List<PinnedHeaderEntity<CoursePlanListBean.DatasBean.CoursePlanList>> data = new ArrayList<>();
        ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> data1 = new ArrayList<>();
        ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> data2 = new ArrayList<>();
        ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> data3 = new ArrayList<>();
        ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> data4 = new ArrayList<>();
        ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> data5 = new ArrayList<>();
        ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> data6 = new ArrayList<>();
        ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> data7 = new ArrayList<>();


        for (int i = 0; i < coursePlanList.size(); i++) {
            String classDatetime = coursePlanList.get(i).getClassDatetime();
            String week = TimeUtils.dateToWeek(classDatetime.substring(0, 10));
            switch (week) {
                case "周一":
                    data1.add(coursePlanList.get(i));
                    break;
                case "周二":
                    data2.add(coursePlanList.get(i));
                    break;
                case "周三":
                    data3.add(coursePlanList.get(i));
                    break;
                case "周四":
                    data4.add(coursePlanList.get(i));
                    break;
                case "周五":
                    data5.add(coursePlanList.get(i));
                    break;
                case "周六":
                    data6.add(coursePlanList.get(i));
                    break;
                case "周日":
                    data7.add(coursePlanList.get(i));
                    break;
            }
        }

        if (data1.size() != 0) {
            String classDatetime = data1.get(0).getClassDatetime();
            String substring = classDatetime.substring(5, 10);
            substring = substring + "  周一";
            data.add(new PinnedHeaderEntity<>(coursePlanList.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < data1.size(); i++) {
                data.add(new PinnedHeaderEntity<>(data1.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }
        if (data2.size() != 0) {
            String classDatetime = data2.get(0).getClassDatetime();
            String substring = classDatetime.substring(5, 10);
            substring = substring + "  周二";
            data.add(new PinnedHeaderEntity<>(coursePlanList.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < data2.size(); i++) {
                data.add(new PinnedHeaderEntity<>(data2.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }

        if (data3.size() != 0) {
            String classDatetime = data3.get(0).getClassDatetime();
            String substring = classDatetime.substring(5, 10);
            substring = substring + "  周三";
            data.add(new PinnedHeaderEntity<>(coursePlanList.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < data3.size(); i++) {
                data.add(new PinnedHeaderEntity<>(data3.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }

        if (data4.size() != 0) {
            String classDatetime = data4.get(0).getClassDatetime();
            String substring = classDatetime.substring(5, 10);
            substring = substring + "  周四";
            data.add(new PinnedHeaderEntity<>(coursePlanList.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < data4.size(); i++) {
                data.add(new PinnedHeaderEntity<>(data4.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }

        if (data5.size() != 0) {
            String classDatetime = data5.get(0).getClassDatetime();
            String substring = classDatetime.substring(5, 10);
            substring = substring + "  周五";
            data.add(new PinnedHeaderEntity<>(coursePlanList.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < data5.size(); i++) {
                data.add(new PinnedHeaderEntity<>(data5.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }

        if (data6.size() != 0) {
            String classDatetime = data6.get(0).getClassDatetime();
            String substring = classDatetime.substring(5, 10);
            substring = substring + "  周六";
            data.add(new PinnedHeaderEntity<>(coursePlanList.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < data6.size(); i++) {
                data.add(new PinnedHeaderEntity<>(data6.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }

        if (data7.size() != 0) {
            String classDatetime = data7.get(0).getClassDatetime();
            String substring = classDatetime.substring(5, 10);
            substring = substring + "  周日";
            data.add(new PinnedHeaderEntity<>(coursePlanList.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < data7.size(); i++) {
                data.add(new PinnedHeaderEntity<>(data7.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }

        if (mAdapter != null) {
            mAdapter = null;
        }

        mAdapter = new BaseHeaderAdapter<PinnedHeaderEntity<CoursePlanListBean.DatasBean.CoursePlanList>>((ArrayList<PinnedHeaderEntity<CoursePlanListBean.DatasBean.CoursePlanList>>) data) {

            @Override
            protected void convert(BaseViewHolder holder, PinnedHeaderEntity<CoursePlanListBean.DatasBean.CoursePlanList> coursePlanList) {
                try {
                    switch (holder.getItemViewType()) {
                        case BaseHeaderAdapter.TYPE_HEADER:
                            holder.setText(R.id.tv_animal, coursePlanList.getPinnedHeaderName());
                            holder.setBackgroundColor(R.id.layout,getResources().getColor(R.color.white));
                            break;
                        case BaseHeaderAdapter.TYPE_DATA:
                            final int position = holder.getLayoutPosition();
                            if (mRvLeagueClassList.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                                // 瀑布流布局记录随机高度，就不会导致Item由于高度变化乱跑，导致画分隔线出现问题
                                // 随机高度, 模拟瀑布效果.

                                if (mRandomHeights == null) {
                                    mRandomHeights = new SparseIntArray(getItemCount());
                                }

                                if (mRandomHeights.get(position) == 0) {
                                    mRandomHeights.put(position, dip2px(mActivity, (int) (100 + Math.random() * 100)));
                                }

                                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                                lp.height = mRandomHeights.get(position);
                                holder.itemView.setLayoutParams(lp);

                            }

                            TextView tvTime = holder.getView(R.id.tvTime);
                            TextView tvCourseName = holder.getView(R.id.tvCourseName);
                            TextView tvTeacherName = holder.getView(R.id.tvTeacherName);
                            TextView tvClassRoomName = holder.getView(R.id.tvClassRoomName);
                            RelativeLayout rl = holder.getView(R.id.rl);
                            tvCourseName.setText(coursePlanList.getData().getCourseName());
                            tvTeacherName.setText(coursePlanList.getData().getEmployeeName());
                            tvClassRoomName.setText(coursePlanList.getData().getClassRoomName());


                            String classDatetime = coursePlanList.getData().getClassDatetime();
                            String duration = coursePlanList.getData().getDuration();
                            int EndTime = Integer.parseInt(duration);
                            /*计算课程的开始时间和结束时间*/
                            String endTime = TimeUtils.addDateMinut(classDatetime, EndTime);
                            String substring = endTime.substring(11, 16);
                            String startTime = coursePlanList.getData().getClassDatetime().substring(11, 16);
                            tvTime.setText(startTime + " -- " + substring);

                            break;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



            private SparseIntArray mRandomHeights;

            @Override
            protected void addItemTypes() {
                addItemType(BaseHeaderAdapter.TYPE_HEADER, R.layout.item_pinned_header);
                addItemType(BaseHeaderAdapter.TYPE_DATA, R.layout.leagueclass_item);
            }

        };
        mAdapter.onAttachedToRecyclerView(mRvLeagueClassList);
        if (adapter) {
            adapter = false;
            mRvLeagueClassList.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    switch (mAdapter.getItemViewType(i)) {
                        case BaseHeaderAdapter.TYPE_DATA:   //排课列表点击
                            baseQuickAdapter.getItemCount();
                            Log.i("*****", "点击的是-->" + i + "size-->"+coursePlanList.size()+"\t-->"+baseQuickAdapter.getFooterViewsCount()+"--->"+baseQuickAdapter.getEmptyViewCount()+"");
                            Intent intent = new Intent(mActivity, EstablishLCPlanActivity.class);
                            intent.putExtra("Object", data.get(i).getData());
                            intent.putExtra("key", "ed");
                            startActivityForResult(intent, REQUEST_CAMERA_CODE);
                            break;
                        case BaseHeaderAdapter.TYPE_HEADER: //标题点击
                            break;
                    }
                }
            });

            /**标签的点击事件*/
            OnHeaderClickListener headerClickListener = new OnHeaderClickListener() {
                @Override
                public void onHeaderClick(View view, int id, int position) {
                }

                @Override
                public void onHeaderLongClick(View view, int id, int position) {
                }

                @Override
                public void onHeaderDoubleClick(View view, int id, int position) {
                }
            };

            mRvLeagueClassList.addItemDecoration(new PinnedHeaderItemDecoration
                    .Builder(BaseHeaderAdapter.TYPE_HEADER)
//                    .setDividerId(R.drawable.divider)
                    .enableDivider(false)
                    .disableHeaderClick(true)
                    .setHeaderClickListener(headerClickListener).create());
        }


        mRvLeagueClassList.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick({R.id.head_ivBack, R.id.head_tvOK, R.id.llLastWeek, R.id.llThisWeek, R.id.llNextWeek})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_tvOK:    //创建计划
                Intent intent = new Intent(mActivity, EstablishLCPlanActivity.class);
                intent.putExtra("key", "add");
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
                break;
            case R.id.llLastWeek:   //上周
                weekIndex = weekIndex - 1;
                getLeagueLecture();
                break;
            case R.id.llThisWeek:   //本周
                weekIndex = weekIndex2;
                getLeagueLecture();
                break;
            case R.id.llNextWeek:   //下周
                weekIndex = weekIndex + 1;
                getLeagueLecture();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == REQUEST_CAMERA_CODE) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    getLeagueLecture();
                    break;
            }


        }
    }


    /**
     * 获取该老师的一周排课（团课）
     */
    private void getLeagueLecture() {
        String mStoreId = SPUtils.getUserDataBean(mActivity).getStoreId();
        //Log.i(TAG, "传参 "+mStoreId+"\t"+classyear+"\t"+weekIndex+"\t");
        OperatingFloorRequest.listCoursePlanByWeek(mStoreId, classyear, weekIndex + "", new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "获取某一周的课程计划成功---》" + response.get().toString() + "");
                    CoursePlanListBean coursePlanListBean = JSON.parseObject(response.get(), CoursePlanListBean.class);
                    if (!coursePlanListBean.getResult().getCode().equals("0")) {
                        show(); //显示错误页面
                        return;
                    }
                    if (weekIndex != weekIndex2) {
                        coursePlanList.clear();
                    }
                    coursePlanList = (ArrayList<CoursePlanListBean.DatasBean.CoursePlanList>) coursePlanListBean.getDatas().getCoursePlanList();
                    show();//显示错误页面

                    initAdapter();

                } catch (Exception e) {
                    mLoadingView.showErrorView(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            getLeagueLecture();
                        }
                    });
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {

                mLoadingView.showErrorView(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        getLeagueLecture();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });

        mLoadingView.showContentView();
    }

    //显示错误页面
    private void show(){
        if (coursePlanList.size() == 0 || coursePlanList == null) {
            mLlErrorDisplay.setVisibility(View.VISIBLE);
            mCoordinatorLayout.setVisibility(View.GONE);
        } else {
            mLlErrorDisplay.setVisibility(View.GONE);
            mCoordinatorLayout.setVisibility(View.VISIBLE);
        }
    }


    //获取网络时间
    private void getNetTime() {
        URL url = null;//取得资源对象
        try {
            //url = new URL("http://www.baidu.com");
            url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            //url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            netTime = formatter.format(calendar.getTime());
            mHandler.sendEmptyMessage(1);
        } catch (Exception e) {
            mHandler.sendEmptyMessage(1);
            e.printStackTrace();
        }

    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
