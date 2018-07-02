package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.CourseListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 课程设置
 */
public class CourseOfferedActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.rvCard)
    RecyclerView mRvCard;
    @Bind(R.id.card_loadView)
    LoadingView mCardLoadView;
    @Bind(R.id.llErrorDisplay)
    LinearLayout mLlErrorDisplay;
    @Bind(R.id.btSearch)
    Button mBtSearch;
    @Bind(R.id.evCourseName)
    EditText mEvCourseName;


    private CommonAdapter<CourseListBean.DatasBean.CourseList> mCommonAdapter;
    private String TAG = "***课程设置--->";
    private Activity mActivity;
    private final static int SKIP_CODE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_offered);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mRvCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("课程");
        mHeadTvOK.setText("添加课程");

        getListCourseInfo();


        //下拉刷新 上拉加载
       /* int homepage_refresh_spacing = 40;
        mWsrlyout.setProgressViewOffset(false, -homepage_refresh_spacing * 2, homepage_refresh_spacing);
        mWsrlyout.setColorSchemeResources(R.color.colorAccent);
        mWsrlyout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mWsrlyout != null) {
                            mWsrlyout.setRefreshing(false);

                        }


                    }
                }, 2000);
            }

            @Override
            public void onLoad() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mWsrlyout != null) {
                            mWsrlyout.setLoading(false);
                        }


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


        });*/

    }

    /**
     * 获取课程列表
     */
    private void getListCourseInfo() {
        String businessId = SPUtils.getUserDataBean(this).getBusinessId();
        OperatingFloorRequest.getListCourseInfo(businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {

                    Log.i(TAG, "获取列表成功-->" + response.get().toString() + "");
                    mCardLoadView.showContentView();
                    CourseListBean courseListBean = JSON.parseObject(response.get(), CourseListBean.class);
                    if (!courseListBean.getResult().getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "获取课程列表失败,请稍后重试!", 1000);
                        mCardLoadView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getListCourseInfo();
                            }
                        });
                        return;
                    }

                    //判断是否有值
                    if (courseListBean.getDatas().getCourseList().size() == 0 || courseListBean.getDatas().getCourseList() == null) {
                        mRvCard.setVisibility(View.GONE);
                        mLlErrorDisplay.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        mRvCard.setVisibility(View.VISIBLE);
                        mLlErrorDisplay.setVisibility(View.GONE);
                    }

                    //加载适配器
                    loadOnAdapter(courseListBean.getDatas().getCourseList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mCardLoadView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getListCourseInfo();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 加载课程列表适配器
     */
    private void loadOnAdapter(List<CourseListBean.DatasBean.CourseList> courseList) {

        mCommonAdapter = new CommonAdapter<CourseListBean.DatasBean.CourseList>(this, R.layout.course_offered_item, courseList) {
            @Override
            protected void convert(ViewHolder holder, final CourseListBean.DatasBean.CourseList courseList, int position) {
                TextView textView1 = holder.getView(R.id.textView1);
                TextView textView2 = holder.getView(R.id.textView2);
                final TextView textView3 = holder.getView(R.id.textView3);
                final TextView textView4 = holder.getView(R.id.textView4);
                TextView textView5 = holder.getView(R.id.textView5);
                TextView textView6 = holder.getView(R.id.textView6);
                final LinearLayout llStatus1 = holder.getView(R.id.llStatus1);
                final LinearLayout llStatus2 = holder.getView(R.id.llStatus2);
                ImageView ivCourse = holder.getView(R.id.ivCourse);

                try {

                    if (!TextUtils.isEmpty(courseList.getIconPath())) {
                        Picasso.with(mActivity)
                                .load(courseList.getIconPath())
                                .placeholder(R.drawable.publish_add)
                                .error(R.drawable.publish_add)
                                .resize(150, 150)
                                //.transform(new CircleImageTransformation())//展示圆形图片
                                .centerCrop()
                                .into(ivCourse);
                    }

                    textView1.setText(courseList.getCourseName());
                    if (courseList.getCourseMeans().equals("1")) {
                        textView2.setText("私教");
                    } else if (courseList.getCourseMeans().equals("2")) {
                        textView2.setText("团课");
                    } else if (courseList.getCourseMeans().equals("3")) {
                        textView2.setText("团课&私教");
                    }

                    if (courseList.getCourseStatus().equals("1")) { //启用
                        textView3.setText("启用");
                        llStatus1.setBackground(getResources().getDrawable(R.drawable.shape_card_status_green2_style));

                    } else {     //禁用
                        textView3.setText("禁用");
                        llStatus1.setBackground(getResources().getDrawable(R.drawable.shape_cancel_style));
                    }

                    if (courseList.getIsExperience().equals("1")) {  //支持

                        textView4.setText("支持");
                        llStatus2.setBackground(getResources().getDrawable(R.drawable.shape_card_status_green2_style));
                    } else {     //不支持
                        textView4.setText("不支持");
                        llStatus2.setBackground(getResources().getDrawable(R.drawable.shape_cancel_style));
                    }

                /*可用卡券*/
                    textView5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(CourseOfferedActivity.this, UsableCardActivity.class);
                            intent.putExtra("object", courseList);
                            startActivityForResult(intent, SKIP_CODE);
                        }
                    });

                /*编辑*/
                    textView6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(CourseOfferedActivity.this, EditCoursesActivity.class);
                            intent.putExtra("CourseId", courseList.getId());
                            startActivityForResult(intent, SKIP_CODE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mRvCard.setAdapter(mCommonAdapter);
    }

    /**
     * 回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SKIP_CODE) {
            switch (requestCode) {
                case SKIP_CODE:     //获取课程列表
                    getListCourseInfo();
                    break;
            }
        }
    }


    @OnClick({R.id.head_ivBack, R.id.head_tvOK, R.id.btSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_tvOK:
                Intent intent = new Intent(mActivity, EditCoursesActivity.class);
                intent.putExtra("CourseId", "add");
                startActivityForResult(intent, SKIP_CODE);
                break;
            case R.id.btSearch:     //搜索
                getSearch();
                break;
        }
    }


    /**
     * 根据课程名称搜索
     */
    private void getSearch() {
        String courseName = mEvCourseName.getText().toString();
        courseName = courseName.trim();
        if (TextUtils.isEmpty(courseName)){
            ToastUtils.showToCenters(mActivity,"请输入课程名称!",1000);
            return;
        }

        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        OperatingFloorRequest.getListCourseByName(courseName, businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {

                    Log.i(TAG, "获取列表成功-->" + response.get().toString() + "");
                    mCardLoadView.showContentView();
                    CourseListBean courseListBean = JSON.parseObject(response.get(), CourseListBean.class);
                    if (!courseListBean.getResult().getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "获取课程列表失败,请稍后重试!", 1000);
                        mCardLoadView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getListCourseInfo();
                            }
                        });
                        return;
                    }

                    //判断是否有值
                    if (courseListBean.getDatas().getCourseList().size() == 0 || courseListBean.getDatas().getCourseList() == null) {
                        mRvCard.setVisibility(View.GONE);
                        mLlErrorDisplay.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        mRvCard.setVisibility(View.VISIBLE);
                        mLlErrorDisplay.setVisibility(View.GONE);
                    }

                    //加载适配器
                    loadOnAdapter(courseListBean.getDatas().getCourseList());
                } catch (Exception e) {
                    mCardLoadView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getListCourseInfo();
                        }
                    });
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mCardLoadView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getListCourseInfo();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
