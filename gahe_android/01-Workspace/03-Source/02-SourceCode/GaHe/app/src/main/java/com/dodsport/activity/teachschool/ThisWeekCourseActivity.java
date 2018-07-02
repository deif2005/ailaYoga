package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.CoursePlanListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.weight.StickyItemDescoration;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 本周排课
 */

public class ThisWeekCourseActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.rvThisWeekCourse)
    RecyclerView mRvThisWeekCourse;
    private String TAG = "****本周排课--";
    private Intent mIntent;
    private Activity mActivity;
    private String mClassDate = "";
    private String mStoreId = "";
    private ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> mPlanList = new ArrayList<>();
    private CommonAdapter<CoursePlanListBean.DatasBean.CoursePlanList> mCommonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_this_week_course);
        ButterKnife.bind(this);
        mActivity = this;
        mIntent = getIntent();
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("创建计划");
        mClassDate = mIntent.getStringExtra("ClassDate");
        mStoreId = mIntent.getStringExtra("StoreId");
        mRvThisWeekCourse.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        getLeagueLecture();
    }

        /**
         * 获取该老师的一周排课（团课）
         */
        private void getLeagueLecture() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse(mClassDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(date);
            int weekIndex = calendar.get(Calendar.WEEK_OF_YEAR);
            String classyear = mClassDate.substring(0, 4);
            weekIndex = weekIndex - 1;
            Log.i(TAG, "传参 "+mStoreId+"\t"+classyear+"\t"+weekIndex+"\t");

            OperatingFloorRequest.listCoursePlanByWeek(mStoreId, classyear, weekIndex + "", new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    try {
                        Log.i(TAG, "获取某一周的课程计划成功---》"+response.get().toString()+"");

                        CoursePlanListBean coursePlanListBean = JSON.parseObject(response.get(), CoursePlanListBean.class);
                        if (!coursePlanListBean.getResult().getCode().equals("0")) {
                            return;
                        }

                        LeagueLectureAdapter(coursePlanListBean.getDatas().getCoursePlanList());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    Log.i(TAG, "获取某一周的课程计划失败---》"+response.toString()+"");
                }

                @Override
                public void onFinish(int what) {

                }
            });
        }



    private void LeagueLectureAdapter(final List<CoursePlanListBean.DatasBean.CoursePlanList> coursePlanList) {
        mCommonAdapter = new CommonAdapter<CoursePlanListBean.DatasBean.CoursePlanList>(mActivity, R.layout.course_plan_item, coursePlanList) {
            @Override
            protected void convert(ViewHolder holder, CoursePlanListBean.DatasBean.CoursePlanList course, int position) {
                TextView text1 = holder.getView(R.id.text1);
                TextView text2 = holder.getView(R.id.text2);
                TextView text3 = holder.getView(R.id.text3);
                String substring = course.getClassDatetime().substring(11, 16);
                String substring1 = substring.substring(0, 2);
                String substring2 = substring.substring(4, 5);
                int time = Integer.parseInt(substring1);
                int i = Integer.parseInt(substring2);
                if ((time + 1) > 12) {
                    String hh = "";
                    if (i <= 9) {
                        hh = "0" + i + "";
                    } else if (i == 10) {
                        hh = i + "0";
                    } else {
                        hh = i + "";
                    }
                    text1.setText(substring + " - " + "01" + ":" + hh + "");
                } else {
                    time = time + 1;
                    String hh = "";
                    if (i <= 9) {
                        hh = "0" + i + "";
                    } else if (i == 10) {
                        hh = i + "0";
                    } else {
                        hh = i + "";
                    }
                    text1.setText(substring + " - " + time + ":" + hh);
                }

                text2.setText(course.getClassRoomName());
                text3.setText(course.getCourseName());

                //Log.i(TAG, "转换后的时间戳" + substring1 + "\t" + substring2 + "\t");

            }
        };
        mRvThisWeekCourse.addItemDecoration(new DividerItemDecoration(mActivity,DividerItemDecoration.VERTICAL));//下划线可以叠加


        mRvThisWeekCourse.addItemDecoration(new StickyItemDescoration(mActivity, new StickyItemDescoration.DecorationCallback() {

            @Override
            public long getGroupId(int position) {
                //Log.i(TAG, "----》"+position+"");
                return 1;
            }

            //Character.toUpperCase(list.get(position).charAt(0))
            //list.get(position).substring(0, 1).toUpperCase()
            @Override
            public String getGroupFirstLine(int position) {
                return "周一";
            }
        }));

        mRvThisWeekCourse.setAdapter(mCommonAdapter);
    }


    /**加载适配器*/



    @OnClick(R.id.head_ivBack)
    public void onViewClicked() {
        finish();
    }
}
