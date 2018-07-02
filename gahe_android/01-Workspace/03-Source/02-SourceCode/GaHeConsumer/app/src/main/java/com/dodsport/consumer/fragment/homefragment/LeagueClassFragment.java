package com.dodsport.consumer.fragment.homefragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.consumer.R;
import com.dodsport.consumer.activity.aboutclass.AboutClassInfoActivity;
import com.dodsport.consumer.activity.aboutclass.CoachScheduleActivity;
import com.dodsport.consumer.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.consumer.extension.BaseSubscriber;
import com.dodsport.consumer.fragment.BaseFragment;
import com.dodsport.consumer.model.ApiCode;
import com.dodsport.consumer.model.ClientCourseOrderListBean;
import com.dodsport.consumer.model.CoachInfoListBean;
import com.dodsport.consumer.model.ResultBean;
import com.dodsport.consumer.model.UserBean;
import com.dodsport.consumer.net.api.VisApi;
import com.dodsport.consumer.request.UrlInterfaceManager;
import com.dodsport.consumer.rxbus.PopupWindowBus;
import com.dodsport.consumer.rxbus.RxBus;
import com.dodsport.consumer.util.SPUtils;
import com.dodsport.consumer.util.TimeUtils;
import com.dodsport.consumer.util.ToastUtils;
import com.dodsport.consumer.view.LoadingView;
import com.dodsport.consumer.weight.popupwindow.CompanyNamePopupWindow;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class LeagueClassFragment extends BaseFragment {
    @BindView(R.id.rvWeek)
    RecyclerView mRvWeek;
    @BindView(R.id.scroll_toolbar)
    Toolbar mScrollToolbar;
    @BindView(R.id.tvFilter)
    TextView mTvFilter;
    @BindView(R.id.rlFilter)
    RelativeLayout mRlFilter;
    @BindView(R.id.scroll_collapsingToolbarLayout)
    CollapsingToolbarLayout mScrollCollapsingToolbarLayout;
    @BindView(R.id.scroll_appbar)
    AppBarLayout mScrollAppbar;
    @BindView(R.id.scroll_toolbar2)
    Toolbar mScrollToolbar2;
    @BindView(R.id.rvMember)
    RecyclerView mRvMember;
    @BindView(R.id.CoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.LoadingView)
    LoadingView mLoadingView;
    @BindView(R.id.rvTeaCher)
    RecyclerView mRvTeaCher;
    @BindView(R.id.tvFilter1)
    TextView mTvFilter1;
    @BindView(R.id.rlFilter1)
    RelativeLayout mRlFilter1;
    @BindView(R.id.llTeaCher)
    LinearLayout mLlTeaCher;

    private View mView;
    private Activity mActivity;
    private String TAG = "***平台用户信息--";
    private List<String> week = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;
    private CommonAdapter<ClientCourseOrderListBean.DatasBean.ClientCourseOrderList> mAdapter;
    private CommonAdapter<CoachInfoListBean.DatasBean.CoachInfoList> mListCommonAdapter;
    private int sWeek = 0;  //星期的下标
    private String sDate = "";  //选中日期
    private boolean layout = false;
    private int size = 0;
    private List<String> weeks = new ArrayList<>();     //星期集合
    private boolean showAdapter = true;
    private List<ClientCourseOrderListBean.DatasBean.ClientCourseOrderList> clientCourseOrderList = new ArrayList<>();

    public LeagueClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_member_about_class, container, false);
        }
        ButterKnife.bind(this, mView);
        mActivity = getActivity();
        initView();
        doSubscribe();
        return mView;
    }


    private void initView() {
        mLoadingView.showLoadingView();
        mRvWeek.setLayoutManager(new GridLayoutManager(mActivity, 5));
        mRvMember.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRvTeaCher.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

        String datetime = TimeUtils.currentDatetime();
        sDate = datetime;
        int i = TimeUtils.dateToWeeks(datetime.substring(0,10));

        switch ((i+1)){
            case 1:
                weeks.add("周日");
                weeks.add("周一");
                weeks.add("周二");
                weeks.add("周三");
                weeks.add("周四");
                weeks.add("周五");
                weeks.add("周六");
                break;
            case 2:
                weeks.add("周一");
                weeks.add("周二");
                weeks.add("周三");
                weeks.add("周四");
                weeks.add("周五");
                weeks.add("周六");
                weeks.add("下周日");
                break;
            case 3:
                weeks.add("周二");
                weeks.add("周三");
                weeks.add("周四");
                weeks.add("周五");
                weeks.add("周六");
                weeks.add("下周日");
                weeks.add("下周一");
                break;
            case 4:
                weeks.add("周三");
                weeks.add("周四");
                weeks.add("周五");
                weeks.add("周六");
                weeks.add("下周日");
                weeks.add("下周一");
                weeks.add("下周二");
                break;
            case 5:
                weeks.add("周四");
                weeks.add("周五");
                weeks.add("周六");
                weeks.add("下周日");
                weeks.add("下周一");
                weeks.add("下周二");
                weeks.add("下周三");
                break;
            case 6:
                weeks.add("周五");
                weeks.add("周六");
                weeks.add("下周日");
                weeks.add("下周一");
                weeks.add("下周二");
                weeks.add("下周三");
                weeks.add("下周四");
                break;
            case 7:
                weeks.add("周六");
                weeks.add("周日");
                weeks.add("下周一");
                weeks.add("下周二");
                weeks.add("下周三");
                weeks.add("下周四");
                weeks.add("下周五");
                break;
                default:
                    break;
        }
        week.clear();
        for (int s = 0; s < weeks.size(); s++) {
            String date = TimeUtils.getDate(datetime.substring(0, 10), s);
            week.add(date);
        }
        //获取课程列表
        getCourderList();

    }

    /**
     * 获取课程列表
     *
     * storeId	String		是	门店id
     * isExperience	String		否	是否只获取体验课 1:是
     * memberId	String		是	会员id
     * classDate	String		是	排课日期
     * page	String		是	页码
     */
    private void getCourderList() {
        Map<String, String> map = new ArrayMap<>();
        String url = UrlInterfaceManager.GETCOURSELIST;
        UserBean.DatasBean.LoginClientUserInfoBean userDataBean = SPUtils.getUserDataBean(mActivity);
        map.put("storeId", userDataBean.getStoreId());
        map.put("memberId", userDataBean.getId());
        map.put("classDate", sDate.substring(0, sDate.length()));
        map.put("page", "1");
        new VisApi().post(url, map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        mLoadingView.showErrorView(view -> getCourderList());
                    }

                    @Override
                    public void onNext(JsonObject body) {
                        try {
                            ClientCourseOrderListBean clientCourseOrder = JSON.parseObject(body.toString(),ClientCourseOrderListBean.class);
                            Log.i(TAG, "解析后的数据--->" + body.toString() + "");
                            if (!clientCourseOrder.getResult().getCode().equals("0")) {
                                ToastUtils.showToCenters(mActivity, "获取列表失败!", 1000);
                                mLoadingView.showErrorView(view -> getCourderList());
                                return;
                            }
                            clientCourseOrderList.clear();
                            clientCourseOrderList = clientCourseOrder.getDatas().getClientCourseOrderList();
                            adapter(clientCourseOrder.getDatas().getClientCourseOrderList());

                        } catch (Exception e) {
                            mLoadingView.showErrorView(view -> getCourderList());
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 加载适配
     *
     * @param clientCourseOrderList
     */
    private void adapter(List<ClientCourseOrderListBean.DatasBean.ClientCourseOrderList> clientCourseOrderList) {
        size = clientCourseOrderList.size();
        if (clientCourseOrderList.size() >= 5) {
            layout = true;
            clientCourseOrderList.add(clientCourseOrderList.get(0));
        }


        mAdapter = new CommonAdapter<ClientCourseOrderListBean.DatasBean.ClientCourseOrderList>(mActivity, R.layout.course_item, clientCourseOrderList) {
            @Override
            protected void convert(ViewHolder holder, ClientCourseOrderListBean.DatasBean.ClientCourseOrderList bean, int position) {

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
//                            .transform(new CircleImageTransformation())//展示圆形图片
                            .centerCrop()
                            .into(ivUserHead);
                }
                tvCourseName.setText(bean.getCourseName());
                tvName.setText(bean.getEmployeeName() + "  " + "\n" + bean.getDuration() + "分钟");
                tvDate.setText(bean.getClassDatetime());

                //当前时间
//                String startDate = "";
//                startDate = sDate.substring(11, 16);
//                String s = "01:00" + "-" + startDate;
//                boolean inTime = TimeUtils.isInTime(s, bean.getClassDatetime());
//                //已经过期
//                if (inTime) {
//                    tvStatus.setText("已过期");
//                    tvStatus.setTextColor(getResources().getColor(R.color.home_text_normal));
//                    llStatus.setVisibility(View.GONE);
//                    tvSubscribeNumber.setVisibility(View.GONE);
//                    //还没有过期
//                } else {

                    SpannableStringBuilder span = new SpannableStringBuilder("还可预约" + bean.getPermitPersons() + "人");
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.home_text_selected)), 4, 4+bean.getPermitPersons().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.home_text_normal)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.home_text_normal)), bean.getPermitPersons().length()+4, span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvSubscribeNumber.setText(span);

                    tvStatus.setVisibility(View.VISIBLE);
                    llStatus.setVisibility(View.GONE);

                if (bean.getPermitPersons().equals("0")) {
                    tvStatus.setText("已约满");
                    tvStatus.setTextColor(getResources().getColor(R.color.text_red));
                } else {
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
                        case "0":
                            tvStatus.setText("可预约");
                            tvStatus.setTextColor(getResources().getColor(R.color.bookable));
                            break;
                        default:
                            break;
                    }
                    }


                if (layout = true) {
                    if (size >= position) {
                        CourseLayout.setVisibility(View.VISIBLE);
                    } else {
                        CourseLayout.setVisibility(View.INVISIBLE);
                    }
                } else {
                    CourseLayout.setVisibility(View.VISIBLE);
                }

                //课程列表点击事件
                CourseLayout.setOnClickListener(view -> {
                    Intent intent = new Intent(mActivity, AboutClassInfoActivity.class);
                    intent.putExtra("object", bean);
                    intent.putExtra("date", sDate);
                    startActivityForResult(intent, ApiCode.Request.CONVERT_ERROR);
                });
                //取消预约课程
                llStatus.setOnClickListener(view -> {
                    cancelAboutClass(bean.getCourseOrderId(),tvStatus,llStatus);
                });
            }
        };

        mRvMember.setAdapter(mAdapter);

        /**星期的适配器*/
        mCommonAdapter = new CommonAdapter<String>(mActivity, R.layout.week_item, weeks) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView weekText = holder.getView(R.id.tvWeek);
                TextView tvDate = holder.getView(R.id.tvDate);
                LinearLayout llWeek = holder.getView(R.id.llWeek);
                weekText.setText(s);
                tvDate.setText(week.get(position).substring(5,10));
                if(position == sWeek){
                    llWeek.setBackground(getResources().getDrawable(R.drawable.radio_whitenull_back2));
                    weekText.setTextColor(getResources().getColor(R.color.home_text_selected));
                    tvDate.setTextColor(getResources().getColor(R.color.home_text_selected));
                }else {
                    llWeek.setBackground(getResources().getDrawable(R.drawable.radio_whitenull_back));
                    weekText.setTextColor(getResources().getColor(R.color.text_color));
                    tvDate.setTextColor(getResources().getColor(R.color.text_color));
                }
                llWeek.setOnClickListener(view -> {
                    sDate = week.get(position)+sDate.substring(10,sDate.length());
                    sWeek =  position;
                    mCommonAdapter.notifyDataSetChanged();
                    getCourderList();
                });
            }
        };

        mRvWeek.setAdapter(mCommonAdapter);
        mLoadingView.showContentView();
        if (mCoordinatorLayout.getVisibility() == View.GONE) {
            mLlTeaCher.setVisibility(View.GONE);
            mCoordinatorLayout.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 取消约课
     */
    private void cancelAboutClass(String id, TextView tvStatus, LinearLayout llStatus) {
        String url = UrlInterfaceManager.CANCELCOURSEORDER;
        Map<String, String> map = new ArrayMap<>();
        map.put("id",id);
        Log.i(TAG, "取消约课传参--->" + map.toString() + "");
        new VisApi().post(url, map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "取消预约捕获异常-->" + e.getLocalizedMessage().toString() + "");
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Log.i(TAG, "取消预约-->" + jsonObject.toString() + "");
                        ResultBean resultBean = JSON.parseObject(jsonObject.toString(), ResultBean.class);
                        if (!resultBean.getResult().getCode().equals("0")) {
                            switch (resultBean.getResult().getCode()) {
                                case "5042":
                                    ToastUtils.showToCenters(mActivity, "该课程不允许取消课程预约!", 1000);
                                    break;
                                default:
                                    break;
                            }
                            return;
                        }

                        tvStatus.setText("可预约");
                        tvStatus.setTextColor(getResources().getColor(R.color.bookable));
                        llStatus.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ApiCode.Request.CONVERT_ERROR){
            switch (requestCode){
                case ApiCode.Request.CONVERT_ERROR:
                    mLoadingView.showLoadingView();
                    //获取课程列表
                    getCourderList();
                    break;
                    default:
                        break;
            }
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.rlFilter1, R.id.rlFilter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlFilter1:
            case R.id.rlFilter:
                List<String> data = new ArrayList<>();
                data.add("按日期选课");
                data.add("按老师选课");
                String de = "Attendance";
                CompanyNamePopupWindow popupWindow = new CompanyNamePopupWindow(mActivity, null, mTvFilter, data, de);
                popupWindow.showAsDropDown(mRlFilter, 0, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 接收消息
     */
    private void doSubscribe() {
        Subscription subscription = RxBus.getInstance()
                .tObservable(PopupWindowBus.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    switch (model.getType()) {
                        case "Attendance":
                            mLoadingView.showLoadingView();
                            if (model.getMsg().equals("按老师选课")) {
                                mTvFilter1.setText("按老师选课");
                                listCourseOrderTeacher();
                            } else { //按日期选课
                                mTvFilter.setText("按日期选课");
                                //获取课程列表
                                getCourderList();
                            }

                            break;
                        default:
                            break;
                    }
                });
        RxBus.getInstance().addSubscription(this, subscription);
    }

    /**
     * 获取可预约老师列表
     * <p>
     * storeId	String		是	门店id
     * page	String		是	页码
     **/
    private void listCourseOrderTeacher() {
        String url = UrlInterfaceManager.LISTCOURSEORDERTEACHER;
        String page = "1";
        Map<String, String> map = new ArrayMap<>();
        map.put("storeId", SPUtils.getUserDataBean(mActivity).getStoreId());
        map.put("page", "1");
        new VisApi().post(url, map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        mLoadingView.showErrorView(view -> listCourseOrderTeacher());
                    }

                    @Override
                    public void onNext(JsonObject body) {
                        try {
                            Log.i(TAG, "老师列表成功--->" + body.toString() + "");
                            CoachInfoListBean coachInfoListBean = JSON.parseObject(body.toString(), CoachInfoListBean.class);
                            if (!coachInfoListBean.getResult().getCode().equals("0")) {
                                mLoadingView.showErrorView(view -> listCourseOrderTeacher());
                                return;
                            }
                            teaCherAdapter(coachInfoListBean.getDatas().getCoachInfoList());

                        } catch (Exception e) {
                            mLoadingView.showErrorView(view -> listCourseOrderTeacher());
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 加载老师列表适配器
     * */
    private void teaCherAdapter(List<CoachInfoListBean.DatasBean.CoachInfoList> coachInfoList) {
        List<String> teaCherName = new ArrayList<>();
        for (int i = 0; i < coachInfoList.size(); i++) {
            teaCherName.add(coachInfoList.get(i).getEmployeeName());
        }
        mListCommonAdapter = new CommonAdapter<CoachInfoListBean.DatasBean.CoachInfoList>(mActivity, R.layout.teacher_list_item, coachInfoList) {
            @Override
            protected void convert(ViewHolder holder, CoachInfoListBean.DatasBean.CoachInfoList coachInfoList, int position) {

                ImageView ivUserHead = holder.getView(R.id.ivUserHead);
                TextView tvTeacherName = holder.getView(R.id.tvTeacherName);
                TextView tvTeacherTechnicalTitle = holder.getView(R.id.tvTeacherTechnicalTitle);
                RelativeLayout rlTeacher = holder.getView(R.id.rlTeacher);

                //头像
                if (!TextUtils.isEmpty(coachInfoList.getEmpHead())){
                    Picasso.with(mActivity)
                            .load(coachInfoList.getEmpHead())
                            .placeholder(R.drawable.user_head)
                            .error(R.drawable.user_head)
                            .resize(150, 150)
//                                        .transform(new CircleImageTransformation())//展示圆形图片
                            .centerCrop()
                            .into(ivUserHead);
                }
                tvTeacherName.setText(coachInfoList.getEmployeeName());
                int jobTitle = coachInfoList.getJobTitle();
                tvTeacherTechnicalTitle.setText(ApiCode.Constant.jobTitle[jobTitle]);
                //选中老师
                rlTeacher.setOnClickListener(view -> {
                    Intent intent = new Intent(mActivity, CoachScheduleActivity.class);
                    intent.putExtra("object",coachInfoList);
                    startActivity(intent);
                });
            }
        };
        mLoadingView.showContentView();
        mRvTeaCher.setAdapter(mListCommonAdapter);
        if (mLlTeaCher.getVisibility() == View.GONE) {
            mLlTeaCher.setVisibility(View.VISIBLE);
            mCoordinatorLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
