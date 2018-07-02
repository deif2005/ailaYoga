package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.model.ListCoursePlanSignInfoBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.TimeUtils;
import com.dodsport.weight.popupwindow.CompanyNamePopupWindow;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * 课程签到
 */

public class CourseAttendanceActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvWeek)
    TextView mTvWeek;
    @Bind(R.id.tvDate)
    TextView mTvDate;
    @Bind(R.id.tvTime)
    TextView mTvTime;
    @Bind(R.id.tvType)
    TextView mTvType;
    @Bind(R.id.icon2)
    ImageView mIcon2;
    @Bind(R.id.tvItemText1)
    TextView mTvItemText1;
    @Bind(R.id.tvItemText2)
    TextView mTvItemText2;
    @Bind(R.id.tvItemText3)
    TextView mTvItemText3;
    @Bind(R.id.tvItemText4)
    TextView mTvItemText4;
    @Bind(R.id.tvItemText5)
    TextView mTvItemText5;
    @Bind(R.id.rvCourse)
    RecyclerView mRvCourse;
    @Bind(R.id.llErrorDisplay)
    LinearLayout mLlErrorDisplay;
    @Bind(R.id.llErrorDisplay1)
    LinearLayout mLlErrorDisplay1;
    @Bind(R.id.WaveSwipeRefresh_Layout)
    WaveSwipeRefreshLayout mWsrlyout;
    @Bind(R.id.LoadingView)
    LoadingView mLoadingView;


    private Activity mActivity;
    private Handler mHandler;
    private String netTime = "";        //当前日期
    private String courseMeans = "1";   //授课形式 1、团课     2、私教
    private String dataType = "1";      //当前数据
    private int pages = 1;          //当前页数
    private String TAG = "***课程签到--";
    private List<ListCoursePlanSignInfoBean.DatasBean.ListCoursePlanSignInfo> listCoursePlanSignInfo = new ArrayList<>();//团课课程列表
    private CommonAdapter<ListCoursePlanSignInfoBean.DatasBean.ListCoursePlanSignInfo> mCommonAdapter;
    private CompanyNamePopupWindow mPopupWindow;
    private EventBus mEventBus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_attendance);
        ButterKnife.bind(this);
        mActivity = this;
        mEventBus.getDefault().register(this);
        initView();
    }


    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("课程签到");
        mTvItemText1.setText("时间");
        mTvItemText2.setText("老师名称");
        mTvItemText3.setText("课程名称");
        mTvItemText4.setText("课室");
        mTvItemText5.setText("预约人数");
        mRvCourse.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

        mLoadingView.showLoadingView();

        mHandler = new mHanlder();

        //获取网络时间
        //请求网络资源是耗时操作。放到子线程中进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                getNetTime();
            }
        }).start();


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
                        if (mWsrlyout!=null){
                            mWsrlyout.setRefreshing(false);
                        }
                        pages = 1;
                        getListCoursePlanSign();

                    }
                }, 2000);
            }

            @Override
            public void onLoad() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mWsrlyout!=null){
                            mWsrlyout.setLoading(false);
                        }
                        pages = pages + 1;
                        getListCoursePlanSign();


                    }
                }, 2000);
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

    }

    private class mHanlder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (TextUtils.isEmpty(netTime)) {
                        Date now = TimeUtils.now();
                        try {
                            netTime = TimeUtils.parseDate(now);
                            if (!TextUtils.isEmpty(netTime)) {
                                String year = netTime.substring(0, 4);
                                String date = netTime.substring(5, 10);
                                mTvDate.setText(year + "年");
                                String date2 = date.substring(0, 2);
                                String date3 = date.substring(3, 5);
                                mTvTime.setText(date2 + "月" + date3 + "日");

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        mTvDate.setText(netTime.substring(0, 4) + "年");
                    }
                    String week = TimeUtils.dateToWeek(netTime.substring(0, 10));
                    mTvWeek.setText(week);
                    String date = netTime.substring(5, 10);
                    String date2 = date.substring(0, 2);
                    String date3 = date.substring(3, 5);
                    mTvTime.setText(date2 + "月" + date3 + "日");
                    getListCoursePlanSign();
                    break;
            }
        }
    }

    /**
     * 获取排课签到列表
     */
    private void getListCoursePlanSign() {
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();//"57f22b9c-eb5c-45dc-8493-3203217aae3f";
        String id = "2167d84e-d886-40ae-b290-555d22d63afa";//教练id
        String classDate = netTime.substring(0, 10);

        String page = pages+"";

        OperatingFloorRequest.getListCoursePlanSign(storeId, id, classDate, courseMeans, dataType, page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    mLoadingView.showContentView();
                    ListCoursePlanSignInfoBean listCoursePlanSignInfoBean = JSON.parseObject(response.get(), ListCoursePlanSignInfoBean.class);
                    if (!listCoursePlanSignInfoBean.getResult().getCode().equals("0")) {
                        switch (listCoursePlanSignInfoBean.getResult().getCode()) {

                        }

                        mLoadingView.showErrorView(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getListCoursePlanSign();
                            }
                        });
                        return;
                    }
                    if (listCoursePlanSignInfoBean.getDatas().getListCoursePlanSignInfo().size() != 0) {
                        listCoursePlanSignInfo.clear();
                        mRvCourse.setVisibility(View.VISIBLE);
                        mLlErrorDisplay1.setVisibility(View.GONE);
                        mLlErrorDisplay.setVisibility(View.GONE);
                    }else {
                        if (pages==1){
                            mRvCourse.setVisibility(View.GONE);
                            mLlErrorDisplay1.setVisibility(View.VISIBLE);
                            mLlErrorDisplay.setVisibility(View.VISIBLE);
                        }else {
                            pages = pages - 1;
                            ToastUtils.showButtom(mActivity,"已经没有更多!",1000);
                            return;
                        }
                    }
                    listCoursePlanSignInfo = listCoursePlanSignInfoBean.getDatas().getListCoursePlanSignInfo();
//                    listCoursePlanSignInfo.addAll(listCoursePlanSignInfo);
//                    listCoursePlanSignInfo.addAll(listCoursePlanSignInfo);
//                    listCoursePlanSignInfo.addAll(listCoursePlanSignInfo);
//                    listCoursePlanSignInfo.addAll(listCoursePlanSignInfo);
//                    listCoursePlanSignInfo.addAll(listCoursePlanSignInfo);
                    adapter();

                    Log.i(TAG, "获取课程列表成功-->" + response.get() + "");
                } catch (Exception e) {
                    mLoadingView.showErrorView(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getListCoursePlanSign();
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
                        getListCoursePlanSign();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 加载适配器
     */
    private void adapter() {

        mCommonAdapter = new CommonAdapter<ListCoursePlanSignInfoBean.DatasBean.ListCoursePlanSignInfo>(mActivity, R.layout.course_list_item, listCoursePlanSignInfo) {
            @Override
            protected void convert(ViewHolder holder, ListCoursePlanSignInfoBean.DatasBean.ListCoursePlanSignInfo listCoursePlanSignInfo, int position) {

                TextView tiem = holder.getView(R.id.tvItemText1);
                TextView techerName = holder.getView(R.id.tvItemText2);
                TextView courseName = holder.getView(R.id.tvItemText3);
                TextView classRoom = holder.getView(R.id.tvItemText4);
                TextView number = holder.getView(R.id.tvItemText5);

                String classTime = listCoursePlanSignInfo.getClassTime();
                String time1 = classTime.substring(0, 5);
                String tiem2 = classTime.substring(6, 11);
                tiem.setText(time1 + "\n" + "\t--\n" + tiem2);
                techerName.setText(listCoursePlanSignInfo.getEmployeeName());
                courseName.setText(listCoursePlanSignInfo.getCourseName());
                classRoom.setText(listCoursePlanSignInfo.getClassroomName());
                number.setText(listCoursePlanSignInfo.getPersons()+"");

            }
        };

        /**
         * 点击事件
         **/
        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mActivity, CourseStatusParticularsActivity.class);
                intent.putExtra("object", listCoursePlanSignInfo.get(position));
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mRvCourse.setAdapter(mCommonAdapter);
    }


    @OnClick({R.id.head_ivBack, R.id.tvType, R.id.icon2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.tvType:   //选择类型
            case R.id.icon2:
                String de = "Attendance";
                List<String> data = new ArrayList<>();
                String text = mTvType.getText().toString();
                if (text.equals("团课")) {
                    data.add("私教");
                } else {
                    data.add("团课");
                }
                mPopupWindow = new CompanyNamePopupWindow(mActivity, mIcon2, mTvType, data,de);
                mPopupWindow.showAsDropDown(mTvType);
                break;
        }
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(AskForLeaveTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取用户选中请假类型
        if (event.getType().equals("Attendance")) {
            String text = mTvType.getText().toString();
            if (text.equals("团课")) { //团课
                courseMeans = "1";
            } else {     //私教
                courseMeans = "2";
            }
            pages = 1;
            //获取数据
            getListCoursePlanSign();
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
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
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            netTime = formatter.format(calendar.getTime());
            mHandler.sendEmptyMessage(1);
        } catch (Exception e) {
            mHandler.sendEmptyMessage(1);
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.getDefault().unregister(this);
    }
}
