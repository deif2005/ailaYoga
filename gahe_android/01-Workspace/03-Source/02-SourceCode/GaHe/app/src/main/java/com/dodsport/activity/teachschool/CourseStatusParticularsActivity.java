package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.CourseOrderListBean;
import com.dodsport.model.ListCoursePlanSignInfoBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.dialog.ClearCacheDialog;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 课程状态详情（签到详情）
 */
public class CourseStatusParticularsActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvItemText1)
    TextView mTvItemText1;
    @Bind(R.id.tvItemText2)
    TextView mTvItemText2;
    @Bind(R.id.tvItemText3)
    TextView mTvItemText3;
    @Bind(R.id.rvCourseStatus)
    RecyclerView mRvCourseStatus;
    @Bind(R.id.WaveSwipeRefresh_Layout)
    WaveSwipeRefreshLayout mWsrlyout;
    @Bind(R.id.LoadingView)
    LoadingView mLoadingView;
    @Bind(R.id.llBottom)
    LinearLayout mLlBottom;
    @Bind(R.id.tvCourseSignIn)
    TextView mTvCourseSignIn;
    @Bind(R.id.llCourseSignIn)
    LinearLayout mLlCourseSignIn;
    @Bind(R.id.tvCancelCourse)
    TextView mTvCancelCourse;
    @Bind(R.id.llCancelCourse)
    LinearLayout mLlCancelCourse;

    private ListCoursePlanSignInfoBean.DatasBean.ListCoursePlanSignInfo mCoursePlanSignInfo;
    private int pages = 1;
    private String TAG = "***约课人数信息--";
    private CommonAdapter<CourseOrderListBean.DatasBean.CourseOrderList> mCommonAdapter;
    private List<String> data = new ArrayList<>();
    private Activity mActivity;
    private List<CourseOrderListBean.DatasBean.CourseOrderList> courseOrderList = new ArrayList<>();    //约课详情列表
    private String orderStatus = "";    //预约状态
    private ClearCacheDialog mDialog;
    private int coun = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_status_particulars);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mLoadingView.showLoadingView();
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("约课详情");
        mTvItemText1.setText("个人信息");
        mTvItemText2.setText("状态");
        mTvItemText3.setText("操作");
        mRvCourseStatus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        showDialog();
        Intent intent = getIntent();
        mCoursePlanSignInfo = (ListCoursePlanSignInfoBean.DatasBean.ListCoursePlanSignInfo) intent.getSerializableExtra("object");

        getListCourseOrder();

        for (int i = 0; i < 15; i++) {
            data.add("测试");
        }

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
                        if (mWsrlyout != null) {
                            mWsrlyout.setRefreshing(false);
                        }
                        pages = 1;
                        getListCourseOrder();

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
                        mLlBottom.setVisibility(View.GONE);

                        pages = pages + 1;
                        getListCourseOrder();

                        try {
                            Thread.sleep(2000);
                            mLlBottom.setVisibility(View.VISIBLE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
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


        });


    }

    /**
     * 获取约课人员列表
     */
    public void getListCourseOrder() {
        String id = "090da7e4-7d6d-4a1d-a353-dd94a5160f77";//mCoursePlanSignInfo.getId();
        final String page = pages + "";
        OperatingFloorRequest.getListCourseOrder(id, page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取列表成功-->" + response.get() + "");
                CourseOrderListBean courseOrderListBean = JSON.parseObject(response.get(), CourseOrderListBean.class);
                if (!courseOrderListBean.getResult().getCode().equals("0")){

                    ToastUtils.showToCenters(mActivity,"获取列表失败,请稍后重试!",1000);
                    return;
                }
                if (courseOrderListBean.getDatas().getCourseOrderList().size()== 0 && pages != 0){
                    ToastUtils.showButtom(mActivity,"已经没有更多!",1000);
                    pages = pages -1;
                    return;
                }

                courseOrderList = courseOrderListBean.getDatas().getCourseOrderList();
                adapter();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取列表失败-->" + response.get() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void adapter(){

        mCommonAdapter = new CommonAdapter<CourseOrderListBean.DatasBean.CourseOrderList>(mActivity, R.layout.course_status_item, courseOrderList) {
            @Override
            protected void convert(ViewHolder holder, final CourseOrderListBean.DatasBean.CourseOrderList courseOrder, final int position) {
                ImageView ivUserHead = holder.getView(R.id.ivUserHead);
                TextView UserName = holder.getView(R.id.textView1);
                TextView tvSex = holder.getView(R.id.tvSex);
                TextView textView2 = holder.getView(R.id.textView2);
                TextView textView3 = holder.getView(R.id.textView3);
                LinearLayout llStatus1 = holder.getView(R.id.llStatus1);    //约课状态
                LinearLayout llRemind = holder.getView(R.id.llRemind);
                LinearLayout llSignIn = holder.getView(R.id.llSignIn);
                LinearLayout llBreakAnAppointment = holder.getView(R.id.llBreakAnAppointment);
                LinearLayout llCancel = holder.getView(R.id.llCancel);
                TextView tvRemind = holder.getView(R.id.tvRemind);
                TextView tvSignIn = holder.getView(R.id.tvSignIn);
                TextView tvBreakAnAppointment = holder.getView(R.id.tvBreakAnAppointment);
                TextView tvCancel = holder.getView(R.id.tvCancel);

                UserName.setText(courseOrder.getMemberName()+"测试sdgsdgfgtrey");
                textView2.setText(courseOrder.getMembcardName());
                if (courseOrder.getSex().equals("1")){
                    tvSex.setText("男");
                }else {
                    tvSex.setText("女");
                }
                //头像
                if (!TextUtils.isEmpty(courseOrder.getMemberHead())){
                    Picasso.with(mActivity)
                            .load(courseOrder.getMemberHead())
                            .placeholder(R.drawable.publish_add)
                            .error(R.drawable.publish_add)
                            .resize(150, 150)
                            //                                    .transform(new CircleImageTransformation())//展示圆形图片
                            .centerCrop()
                            .into(ivUserHead);
                }


                switch (courseOrder.getOrderStatus()){
                    case "1":   //已预约
                        textView3.setText("已预约");
                        llStatus1.setBackground(getResources().getDrawable(R.drawable.shape_login_style));
                        orderStatus = "1";
                        break;
                    case "2":   //已签到
                        textView3.setText("已签到");
                        llStatus1.setBackground(getResources().getDrawable(R.drawable.shape_card_status_green2_style));
                        break;
                    case "3":   //已爽约
                        textView3.setText("已爽约");
                        llStatus1.setBackground(getResources().getDrawable(R.drawable.shape_red_style));
                        break;
                    case "4":   //已取消
                        textView3.setText("已取消");
                        llStatus1.setBackground(getResources().getDrawable(R.drawable.shape_cancel_style));
                        break;

                }
                if (!courseOrder.getOrderStatus().equals("1")){

                    llRemind.setBackground(getResources().getDrawable(R.drawable.radio_whitenull2_back));
                    tvRemind.setTextColor(getResources().getColor(R.color.userinfo));
                    llSignIn.setBackground(getResources().getDrawable(R.drawable.radio_whitenull2_back));
                    tvSignIn.setTextColor(getResources().getColor(R.color.userinfo));
                    llBreakAnAppointment.setBackground(getResources().getDrawable(R.drawable.radio_whitenull2_back));
                    tvBreakAnAppointment.setTextColor(getResources().getColor(R.color.userinfo));
                    llCancel.setBackground(getResources().getDrawable(R.drawable.radio_whitenull2_back));
                    tvCancel.setTextColor(getResources().getColor(R.color.userinfo));
                }else {

                    llRemind.setBackground(getResources().getDrawable(R.drawable.radio_button_selected_background));
                    tvRemind.setTextColor(getResources().getColor(R.color.home_text_selected));
                    llSignIn.setBackground(getResources().getDrawable(R.drawable.radio_button_selected_background));
                    tvSignIn.setTextColor(getResources().getColor(R.color.home_text_selected));
                    llBreakAnAppointment.setBackground(getResources().getDrawable(R.drawable.radio_button_selected_background));
                    tvBreakAnAppointment.setTextColor(getResources().getColor(R.color.home_text_selected));
                    llCancel.setBackground(getResources().getDrawable(R.drawable.radio_button_selected_background));
                    tvCancel.setTextColor(getResources().getColor(R.color.home_text_selected));
                }



                /*提醒*/
                llRemind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    courseOrderList.get(position).setOrderStatus("1");
                        mCommonAdapter.notifyDataSetChanged();
                        if (courseOrderList.get(position).getOrderStatus().equals("1")){
                            mDialog.setMsg("确定给该学员发送提醒通知!");
                            mDialog.show();

                        }

                    }
                });

                 /*签到*/
                llSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (courseOrderList.get(position).getOrderStatus().equals("1")){
                            orderStatus = "2";
                            coun = position;
                            mDialog.setMsg("确认对该学员进行签到操作!");
                            mDialog.show();

                        }


                    }
                });

                 /*爽约*/
                llBreakAnAppointment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (courseOrderList.get(position).getOrderStatus().equals("1")) {
                            orderStatus = "3";
                            coun = position;
                            mDialog.setMsg("确认对该学员进行爽约操作!");
                            mDialog.show();
                        }
                    }
                });

                 /*取消*/
                llCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (courseOrderList.get(position).getOrderStatus().equals("1")) {
                            orderStatus = "4";
                            coun = position;
                            mDialog.setMsg("确认对该学员进行取消操作!");
                            mDialog.show();
                        }
                    }
                });

            }
        };

        mRvCourseStatus.setAdapter(mCommonAdapter);
        mLoadingView.showContentView();

    }

    /**
     * 发送上课通知
     * */
    private void sendCourseStartAttention(CourseOrderListBean.DatasBean.CourseOrderList courseOrder) {
        String memberId = SPUtils.getUserDataBean(mActivity).getId();
        String courseplanId = courseOrder.getCourseplanId();
        OperatingFloorRequest.sendCourseStartAttention(memberId, courseplanId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "发送通知成功-->"+response.get().toString()+"");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        ToastUtils.showToCenters(mActivity,"发送通知失败,请稍后重试!",1000);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"发送通知失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**弹出提示框*/
    private void showDialog(){
        mDialog = new ClearCacheDialog(this,0.7f);
        mDialog.setCancelable(true);
        mDialog.setTitle("温馨提示");
        mDialog.setPositiveButton("取消",new DialogClick());
        mDialog.setNegativeButton("确定", new DialogClick());

    }

    private class DialogClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.dialog_cancel:    //确定
                    if (courseOrderList.get(coun).getOrderStatus().equals("1")){
                        sendCourseStartAttention(courseOrderList.get(coun));        //发送通知
                    }else {
                        editMemberSignStatus(courseOrderList.get(coun),coun);       //改变约课状态
                    }

                    if (mDialog!=null){
                        mDialog.cancel();
                    }
                    break;
                case R.id.dialog_enter:     //取消
                    if (mDialog!=null){
                        mDialog.cancel();
                    }
                    break;
            }

        }
    }

    /**
     * 更新会员约课状态
     *
     * employeeId	String		是	签到人id
     * courseplanId	String		是	排课id
     * courseOrderId	String		是	课程预约id
     * orderStatus	String		是	预约状态:1已预约，2已签到，3爽约，4取消
     * @param courseOrder
     * @param position*/
    private void editMemberSignStatus(CourseOrderListBean.DatasBean.CourseOrderList courseOrder, final int position){

        String employeeId = SPUtils.getUserDataBean(mActivity).getId();
        String courseplanId = courseOrder.getCourseplanId();
        String courseOrderId = courseOrder.getId();

        Log.i(TAG, "更新状态传参--->\temployeeId-->"+employeeId+"\tcourseplanId-->"+courseplanId+"\tcourseOrderId-->"+courseOrderId+"\torderStatus-->"+orderStatus+"\t");
       //orderStatus ="1";
        OperatingFloorRequest.editMemberSignStatus(employeeId, courseplanId, courseOrderId, orderStatus, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        ToastUtils.showToCenters(mActivity,"操作失败,请稍后重试!",1000);
                        return;
                    }
                    courseOrderList.get(position).setOrderStatus(orderStatus);
                    mCommonAdapter.notifyDataSetChanged();

                    Log.i(TAG, "改变状态成功--->"+response.get().toString()+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "改变状态失败--->"+response.toString()+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @OnClick({R.id.head_ivBack,R.id.llCourseSignIn, R.id.llCancelCourse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llCourseSignIn:   //上课签到
                clickedCourseSignIn();
                break;
            case R.id.llCancelCourse:   //取消课程
                clickedCancelCourse();
                break;
        }
    }

    /**
     * 上课签到
     * */
    private void clickedCourseSignIn() {
        String id = mCoursePlanSignInfo.getId();
        String employeeId = "2167d84e-d886-40ae-b290-555d22d63afa";//SPUtils.getUserDataBean(mActivity).getId();//mCoursePlanSignInfo.getEmployeeId();
       Log.i(TAG, "签到传参-->\tid-->"+id+"\temployeeId-->"+employeeId+"\t"+mCoursePlanSignInfo.toString()+"");
        OperatingFloorRequest.courseSign(id, employeeId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        switch (resultBean.getCode()){
                            case "5027":
                                ToastUtils.showToCenters(mActivity,"您没有进行该操作的权限!",1000);
                                break;
                            case "5028":
                                ToastUtils.showToCenters(mActivity,"您该课程的授课老师,不能签到!",1000);
                                break;
                        }
                        return;
                    }
                    ToastUtils.showToCenters(mActivity,"签到成功!",1000);
                    Log.i(TAG, "签到成功--"+response.get().toString()+"");

                } catch (Exception e) {
                    ToastUtils.showToCenters(mActivity,"签到失败,请稍后重试!",1000);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"签到失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 取消课程
     * */
    private void clickedCancelCourse() {
        String employeeId = "2167d84e-d886-40ae-b290-555d22d63afa";
        String courseplanId = mCoursePlanSignInfo.getCourseplanId();
        String coursesignId = mCoursePlanSignInfo.getId();
        Log.i(TAG, "取消课程传参-->\temployeeId-->"+employeeId+"\tcourseplanId-->"+courseplanId+"\tcoursesignId-->"+coursesignId+"\t");
        OperatingFloorRequest.cancelCourse(employeeId, courseplanId, coursesignId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        switch (resultBean.getCode()){
                            case "5027":
                                ToastUtils.showToCenters(mActivity,"您没有进行该操作的权限!",1000);
                                break;
                            case "5028":
                                ToastUtils.showToCenters(mActivity,"您该课程的授课老师,不能签到!",1000);
                                break;
                        }
                        return;
                    }
                    ToastUtils.showToCenters(mActivity,"签到成功!",1000);
                    Log.i(TAG, "签到成功--"+response.get().toString()+"");

                } catch (Exception e) {
                    ToastUtils.showToCenters(mActivity,"签到失败,请稍后重试!",1000);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"签到失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


}
