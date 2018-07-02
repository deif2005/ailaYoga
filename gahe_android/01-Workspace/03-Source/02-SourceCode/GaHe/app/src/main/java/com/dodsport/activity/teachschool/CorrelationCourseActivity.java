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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.BusiEmployeeListBean;
import com.dodsport.model.TeacherAndCourseListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.R.id.tvItem2;


/**
 * 查看老师关联课程
 */
public class CorrelationCourseActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvCourseName)
    TextView mTvCourseName;
    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(tvItem2)
    TextView mTvItem2;
    @Bind(R.id.tvItem3)
    TextView mTvItem3;
    @Bind(R.id.Price_RecyclerView)
    RecyclerView mPriceRecyclerView;
    @Bind(R.id.llErrorDisplay)
    LinearLayout mLlErrorDisplay;
    @Bind(R.id.btConservation)
    Button mBtConservation;
    @Bind(R.id.btCancel)
    Button mBtCancel;
    @Bind(R.id.cancel)
    RelativeLayout mCancel;
    @Bind(R.id.tvJobTitle)
    TextView mTvJobTitle;



    private BusiEmployeeListBean.DatasBean.EvalListBean busiEmployee;   //老师对象
    private String TAG = "***老师关联课程--";
    private Activity mActivity;
    private List<TeacherAndCourseListBean.DatasBean.TeacherAndCourseList> andCourseList = new ArrayList<>();    //关联课程集合
    List<TeacherAndCourseListBean.DatasBean.TeacherAndCourseList> andCourseList2 = new ArrayList<>();
    private CommonAdapter<TeacherAndCourseListBean.DatasBean.TeacherAndCourseList> mCommonAdapter;
    private String[] type = {"","私教","团课","团课&私教"};
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usable_card);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("可用卡券");
        mTvItem1.setText("课程名称");
        mTvItem2.setText("课程类型");
        mTvItem3.setText("关联");
        mPriceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        busiEmployee = (BusiEmployeeListBean.DatasBean.EvalListBean) intent.getSerializableExtra("object");
        mTvCourseName.setText(busiEmployee.getEmployeeName());
        mTvJobTitle.setVisibility(View.VISIBLE);
        String jobTitle = busiEmployee.getJobTitle();
        if (!TextUtils.isEmpty(jobTitle)){
            int i = Integer.parseInt(jobTitle);
            mTvJobTitle.setText("( "+ UrlInterfaceManager.jobTitle[i]+" )");
        }


        getListTeacherAndCourse();

    }


    /**
     * 获取老师关联的列表
     * */
    public void getListTeacherAndCourse() {
        String businessId = busiEmployee.getBusinessId();
        String employeeId = busiEmployee.getId();
        OperatingFloorRequest.getListTeacherAndCourse(businessId, employeeId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                Log.i(TAG, "获取关联列表成功--> "+response.get().toString()+"");
                try {
                    TeacherAndCourseListBean teacherAndCourseList = JSON.parseObject(response.get(), TeacherAndCourseListBean.class);
                    if (!teacherAndCourseList.getResult().getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "获取失败,请稍后重试!", 1000);
                        return;
                    }
                    andCourseList = teacherAndCourseList.getDatas().getTeacherAndCourseList();
                    if (andCourseList.size() != 0 || andCourseList != null) {
                        andCourseList2.clear();
                        mPriceRecyclerView.setVisibility(View.VISIBLE);
                        mLlErrorDisplay.setVisibility(View.GONE);
                        mCancel.setVisibility(View.VISIBLE);
                    } else {
                        mCancel.setVisibility(View.GONE);
                        mPriceRecyclerView.setVisibility(View.GONE);
                        mLlErrorDisplay.setVisibility(View.VISIBLE);
                        return;
                    }
                    andCourseList2.addAll(andCourseList);
                    adapter();

                } catch (Exception e) {

                    mCancel.setVisibility(View.GONE);
                    mPriceRecyclerView.setVisibility(View.GONE);
                    mLlErrorDisplay.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mCancel.setVisibility(View.GONE);
                mPriceRecyclerView.setVisibility(View.GONE);
                mLlErrorDisplay.setVisibility(View.VISIBLE);
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
        mCommonAdapter = new CommonAdapter<TeacherAndCourseListBean.DatasBean.TeacherAndCourseList>(this, R.layout.priceslist_item, andCourseList) {
            @Override
            protected void convert(ViewHolder holder, final TeacherAndCourseListBean.DatasBean.TeacherAndCourseList teacherAndCourse, final int position) {

                TextView tvItem1 = holder.getView(R.id.tvItem1);
                TextView tvItem2 = holder.getView(R.id.tvItem2);
                TextView tvItem3 = holder.getView(R.id.tvItem3);
                tvItem3.setVisibility(View.GONE);
                final RadioButton mRadioButton2 = holder.getView(R.id.RadioButton2);
                mRadioButton2.setVisibility(View.VISIBLE);

                tvItem1.setText(teacherAndCourse.getCourseName());
                int i = Integer.parseInt(teacherAndCourse.getCourseMeans());
                tvItem2.setText(type[i]);

                //一是 表示该教练可可以交这个课程， 二是 表示该教练不可以交这个课程
                if (TextUtils.isEmpty(teacherAndCourse.getEmployeeId()) && TextUtils.isEmpty(andCourseList.get(position).getStatus())) {
                    andCourseList.get(position).setStatus("1");
                } else if (!TextUtils.isEmpty(teacherAndCourse.getEmployeeId()) && TextUtils.isEmpty(andCourseList.get(position).getStatus())) {
                    andCourseList.get(position).setStatus("2");
                }
                if (andCourseList.get(position).getStatus().equals("1")) {
                    mRadioButton2.setChecked(false);
                } else {
                    data.add(teacherAndCourse.getCourseId());
                    mRadioButton2.setChecked(true);
                }

                mRadioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (andCourseList.get(position).getStatus().equals("1")) {
                            andCourseList.get(position).setStatus("2");
                        } else {
                            andCourseList.get(position).setStatus("1");
                        }
                        if (data.size() != 0) {
                            data.clear();
                        }
                        mCommonAdapter.notifyDataSetChanged();

                    }
                });
            }
        };

        mPriceRecyclerView.setAdapter(mCommonAdapter);
    }


    @OnClick({R.id.head_ivBack, R.id.btConservation, R.id.btCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.btConservation:   //保存
                addTeacherAndCourse();
                break;
            case R.id.btCancel:     //取消
                andCourseList.clear();
                data.clear();
                andCourseList.addAll(andCourseList2);
                mCommonAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 保存老师关联课程操作
     * */
    private void addTeacherAndCourse() {
        String employeeId = busiEmployee.getId();
        String courseIdStr = "";
        for (int i = 0; i < data.size(); i++) {
            if (i == (data.size() - 1)) {
                courseIdStr = courseIdStr + data.get(i);
            } else {
                courseIdStr = courseIdStr + data.get(i) + ",";
            }
        }
        OperatingFloorRequest.addTeacherAndCourse(employeeId, courseIdStr, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "保存成功!", 1000);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "保存失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


}
