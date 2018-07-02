package com.dodsport.consumer.activity.aboutclass;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.consumer.R;
import com.dodsport.consumer.activity.BaseActivity;
import com.dodsport.consumer.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.consumer.extension.BaseSubscriber;
import com.dodsport.consumer.model.ApiCode;
import com.dodsport.consumer.model.ClientCourseOrderListBean;
import com.dodsport.consumer.model.CoachInfoListBean;
import com.dodsport.consumer.net.api.VisApi;
import com.dodsport.consumer.request.UrlInterfaceManager;
import com.dodsport.consumer.util.CircleImageTransformation;
import com.dodsport.consumer.util.SPUtils;
import com.dodsport.consumer.util.TimeUtils;
import com.dodsport.consumer.util.ToastUtils;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 *         团课教练 排课计划
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class CoachScheduleActivity extends BaseActivity {


    @BindView(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @BindView(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @BindView(R.id.ivUserHead)
    ImageView mIvUserHead;
    @BindView(R.id.tvTeacherName)
    TextView mTvTeacherName;
    @BindView(R.id.tvTeacherTechnicalTitle)
    TextView mTvTeacherTechnicalTitle;
    @BindView(R.id.tvTeacherIntroduce)
    TextView mTvTeacherIntroduce;
    @BindView(R.id.rlTeacher)
    RelativeLayout mRlTeacher;
    @BindView(R.id.rlScheduleOfCourses)
    RecyclerView mRlScheduleOfCourses;
    @BindView(R.id.llShow)
    LinearLayout mLlShow;
    private CoachInfoListBean.DatasBean.CoachInfoList coachInfo;
    private Activity mActivity;
    private CommonAdapter<ClientCourseOrderListBean.DatasBean.ClientCourseOrderList> mCommonAdapter;
    //页数
    private String page = "1";
    private String TAG = "***老师排课-->";
    private String sDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_schedule);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        coachInfo = (CoachInfoListBean.DatasBean.CoachInfoList) intent.getSerializableExtra("object");
        mRlScheduleOfCourses.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        sDate = TimeUtils.currentDatetime();
        //老师头像
        if (!TextUtils.isEmpty(coachInfo.getEmpHead())) {

        }
        mTvTeacherName.setText(coachInfo.getEmployeeName());
        int jobTitle = coachInfo.getJobTitle();
        mTvTeacherTechnicalTitle.setText(ApiCode.Constant.jobTitle[jobTitle]);
        mTvTeacherIntroduce.setText(coachInfo.getSelfIntroduction());

        getListCourseOrderByTeacher();
    }

    /**
     * 获取老师的排课计划
     * <p>
     * storeId	String		是	门店id
     * isExperience	String		否	是否只获取体验课 1:是
     * memberId	String		是	会员id
     * employeeId	String		是	授课老师
     * page	String		是	页码
     */
    private void getListCourseOrderByTeacher() {
        Map<String, String> map = new ArrayMap<>();
        String url = UrlInterfaceManager.LISTCOURSEORDERBYTEACHER;
        map.put("storeId", SPUtils.getUserDataBean(mActivity).getStoreId());
        map.put("memberId", SPUtils.getUserDataBean(mActivity).getId());
        map.put("employeeId", coachInfo.getId());
        map.put("page", page);

        new VisApi().post(url, map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "捕获异常---> " + e.getLocalizedMessage().toString() + "");

                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Log.i(TAG, "获取列表---> " + jsonObject.toString().toString() + "");
                        ClientCourseOrderListBean bean = JSON.parseObject(jsonObject.toString(), ClientCourseOrderListBean.class);
                        if (!bean.getResult().getCode().equals("0")) {
                            ToastUtils.showToCenters(mActivity, "获取老师课程列表失败!", 1000);
                            return;
                        }
                        adapter(bean.getDatas().getClientCourseOrderList());
                    }
                });
    }

    /**
     * 加载适配器
     *
     * @param clientCourseOrderList
     */
    private void adapter(List<ClientCourseOrderListBean.DatasBean.ClientCourseOrderList> clientCourseOrderList) {
        if (clientCourseOrderList.size()==0){
            mLlShow.setVisibility(View.VISIBLE);
            mRlScheduleOfCourses.setVisibility(View.GONE);
            return;
        }
        mLlShow.setVisibility(View.GONE);
        mRlScheduleOfCourses.setVisibility(View.VISIBLE);
        mCommonAdapter = new CommonAdapter<ClientCourseOrderListBean.DatasBean.ClientCourseOrderList>(mActivity, R.layout.course_item, clientCourseOrderList) {
            @Override
            protected void convert(ViewHolder holder, ClientCourseOrderListBean.DatasBean.ClientCourseOrderList bean, int position) {

                try {
                    ImageView ivUserHead = holder.getView(R.id.ivUserHead);
                    TextView tvCourseName = holder.getView(R.id.tvCourseName);
                    TextView tvName = holder.getView(R.id.tvName);
                    TextView tvDate = holder.getView(R.id.tvDate);
                    TextView tvSubscribeNumber = holder.getView(R.id.tvSubscribeNumber);
                    TextView tvStatus = holder.getView(R.id.tvStatus);
                    TextView tvStatus2 = holder.getView(R.id.tvStatus2);
                    LinearLayout llStatus = holder.getView(R.id.llStatus);
                    LinearLayout CourseLayout = holder.getView(R.id.CourseLayout);

                    //用户头像
                    if (!TextUtils.isEmpty(bean.getIconPath())) {
                        Picasso.with(mActivity)
                                .load(bean.getIconPath())
                                .placeholder(R.drawable.user_head)
                                .error(R.drawable.user_head)
                                .resize(150, 150)
                                .transform(new CircleImageTransformation())//展示圆形图片
                                .centerCrop()
                                .into(ivUserHead);
                    }
                    tvCourseName.setText(bean.getCourseName());
                    tvName.setText(bean.getEmployeeName() + "  " + "\n" + bean.getDuration() + "分钟");
                    tvDate.setText(bean.getClassDatetime());


                    //当前时间
//                    String startDate = null;
//                    startDate = sDate.substring(11, 16);
//                    String s = "01:00"+"-"+startDate;
//                    boolean inTime = TimeUtils.isInTime(s, bean.getClassDatetime());
//                    //已经过期
//                    if (inTime) {
//                        tvStatus.setText("已过期");
//                        tvStatus.setTextColor(getResources().getColor(R.color.home_text_normal));
//                        llStatus.setVisibility(View.GONE);
//                        tvSubscribeNumber.setVisibility(View.GONE);
//                        //还没有过期
//                    } else {

                        SpannableStringBuilder span = new SpannableStringBuilder("还可预约" + bean.getPermitPersons() + "人");
                        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.home_text_selected)), 4, 4 + bean.getPermitPersons().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.home_text_normal)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.home_text_normal)), bean.getPermitPersons().length() + 4, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvSubscribeNumber.setText(span);

                        tvStatus.setVisibility(View.VISIBLE);
                        llStatus.setVisibility(View.GONE);

                        switch (bean.getOrderStatus()) {
                            //已预约
                            case "1":
                                tvStatus.setText("已预约");
                                tvStatus.setTextColor(getResources().getColor(R.color.home_text_selected));
                                llStatus.setVisibility(View.VISIBLE);
                                break;
                            //已签到
                            case "2":
                                break;
                            //爽约
                            case "3":
                                break;
                            //取消
                            case "4":
                                break;
                            default:
                                break;
                        }

                        if (bean.getPermitPersons().equals("0")) {
                            tvStatus.setText("已约满");
                            tvStatus.setTextColor(getResources().getColor(R.color.text_red));
                        } else {
                            tvStatus.setText("可预约");
                            tvStatus.setTextColor(getResources().getColor(R.color.bookable));
                        }
//                    }

                    //课程列表点击事件
                    CourseLayout.setOnClickListener(view -> {
                        Intent intent = new Intent(mActivity, AboutClassInfoActivity.class);
                        intent.putExtra("object", bean);
                        intent.putExtra("date", sDate);
                        startActivityForResult(intent, ApiCode.Response.SIGN_ERROR);
                    });
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

        mRlScheduleOfCourses.setAdapter(mCommonAdapter);
    }

    @OnClick({R.id.head_ivBack, R.id.rlTeacher})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            //教练照片
            case R.id.rlTeacher:

                break;
            default:
                break;
        }
    }
}
