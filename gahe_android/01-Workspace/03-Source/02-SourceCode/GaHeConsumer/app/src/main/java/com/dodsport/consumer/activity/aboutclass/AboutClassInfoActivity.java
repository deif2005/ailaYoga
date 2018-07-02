package com.dodsport.consumer.activity.aboutclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.consumer.R;
import com.dodsport.consumer.activity.BaseActivity;
import com.dodsport.consumer.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.consumer.extension.BaseSubscriber;
import com.dodsport.consumer.model.ApiCode;
import com.dodsport.consumer.model.ClientCourseOrderListBean;
import com.dodsport.consumer.model.CourseOrderInfoBean;
import com.dodsport.consumer.model.ResultBean;
import com.dodsport.consumer.net.api.VisApi;
import com.dodsport.consumer.request.UrlInterfaceManager;
import com.dodsport.consumer.util.SPUtils;
import com.dodsport.consumer.util.TimeUtils;
import com.dodsport.consumer.util.ToastUtils;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Administrator
 *         <p>
 *         约课详情信息
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class AboutClassInfoActivity extends BaseActivity {

    @BindView(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @BindView(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @BindView(R.id.tvTeacherName)
    TextView mTvTeacherName;
    @BindView(R.id.tvClassroomName)
    TextView mTvClassroomName;
    @BindView(R.id.tvTimeName)
    TextView mTvTimeName;
    @BindView(R.id.tvCourseName)
    TextView mTvCourseName;
    @BindView(R.id.tvCourseIntroduce)
    TextView mTvCourseIntroduce;
    @BindView(R.id.tvHint)
    TextView mTvHint;
    @BindView(R.id.tvMembershipCard)
    TextView mTvMembershipCard;
    @BindView(R.id.llCancelAboutClass)
    LinearLayout mLlCancelAboutClass;
    @BindView(R.id.tvMemberCard)
    TextView mTvMemberCard;
    @BindView(R.id.tvCancelAboutClass)
    TextView mTvCancelAboutClass;
    @BindView(R.id.llMemberCardNmae)
    LinearLayout mLlMemberCardNmae;
    @BindView(R.id.rvMemberCardNmae)
    RecyclerView mRvMemberCardNmae;

    private Activity mActivity;
    private String courseOrderId = "";   //
    private String memberCradId = "";   //会员卡id
    private int memberCrad = 0;     //选中会员卡下标
    private boolean request = true;
    private boolean request2 = true;

    //课程详情对象
    private ClientCourseOrderListBean.DatasBean.ClientCourseOrderList bean = null;
    private String TAG = "***课程详情-->";
    private CommonAdapter<CourseOrderInfoBean.DatasBean.CourseOrderInfo.MemberCardListBean> mCommonAdapter;
    private String orderStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_class_info);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("约课");
        mRvMemberCardNmae.setLayoutManager(new GridLayoutManager(mActivity,2));
        Intent intent = getIntent();
        bean = (ClientCourseOrderListBean.DatasBean.ClientCourseOrderList) intent.getSerializableExtra("object");
        String date = intent.getStringExtra("date");
        orderStatus = bean.getOrderStatus();
        courseOrderId = bean.getCourseOrderId();

        getCourseOrderInfo();
    }

    /**
     * 获取约课信息
     * <p>
     * storeId	String		是	门店id
     * courseId	String		是	课程id
     * memberId	String		是	会员id
     * businessId	String		是	商家id
     * id	String		是	签到表id
     */
    private void getCourseOrderInfo() {

        String url = UrlInterfaceManager.GETCOURSEORDERINFO;
        Map<String, String> map = new ArrayMap<>();
        map.put("storeId", SPUtils.getUserDataBean(mActivity).getStoreId());
        map.put("businessId", SPUtils.getUserDataBean(mActivity).getBusinessId());
        map.put("memberId", SPUtils.getUserDataBean(mActivity).getId());
        map.put("courseId", bean.getCourseId());
        map.put("id", bean.getId());

        new VisApi().post(url, map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "捕获异常--> " + e.getMessage().toString() + "");
                    }

                    @Override
                    public void onNext(JsonObject body) {
                        try {
                            Log.i(TAG, "成功--> " + body.toString() + "");
                            CourseOrderInfoBean orderInfoBean = JSON.parseObject(body.toString(), CourseOrderInfoBean.class);
                            mTvTeacherName.setText(orderInfoBean.getDatas().getCourseOrderInfo().getEmployeeName());
                            mTvClassroomName.setText(orderInfoBean.getDatas().getCourseOrderInfo().getClassRoomName());
                            mTvCourseIntroduce.setText(orderInfoBean.getDatas().getCourseOrderInfo().getRemark());
                            mTvCourseName.setText(orderInfoBean.getDatas().getCourseOrderInfo().getCourseName());

                            //最少人数
                            String lowPersons = orderInfoBean.getDatas().getCourseOrderInfo().getLowPersons();
                            //已约人数
                            String orderPersons = orderInfoBean.getDatas().getCourseOrderInfo().getOrderPersons();
                            String hint = "* 当前已预约" + orderPersons + "人，课程最低人数" +  lowPersons+ "人，若人数不足，我们将会提前通知您";
                            SpannableStringBuilder span = new SpannableStringBuilder(hint);
                            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_red)), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color)), 2, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_red)), 7, lowPersons.length() + 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color)), lowPersons.length() + 7, (lowPersons.length() + 7 + 8), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_red)), (lowPersons.length() + 7 + 8), (lowPersons.length() + 7 + 8) + orderPersons.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color)), (lowPersons.length() + 7 + 8 + orderPersons.length()), hint.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mTvHint.setText(span);


                            String time = orderInfoBean.getDatas().getCourseOrderInfo().getClassDateTime().substring(0, 10);
                            memberCradId = orderInfoBean.getDatas().getCourseOrderInfo().getCoursesignId();
                            String dateTime = time + " " + bean.getClassDatetime() + "";
                            int i = Integer.parseInt(bean.getDuration());
                            String dateMinut = TimeUtils.addDateMinut(dateTime, i);
                            dateMinut = dateMinut.substring(11, 16);
                            mTvTimeName.setText(time + "     \t" + bean.getClassDatetime() + " - " + dateMinut);
                            if (orderInfoBean.getDatas().getCourseOrderInfo().getMemberCardList().size() == 1 && orderInfoBean.getDatas().getCourseOrderInfo().getMemberCardList().size() !=0) {
                                mLlMemberCardNmae.setVisibility(View.VISIBLE);
                                mRvMemberCardNmae.setVisibility(View.GONE);
                                memberCradId = orderInfoBean.getDatas().getCourseOrderInfo().getMemberCardList().get(0).getId();
                                //会员卡名称
                                if (TextUtils.isEmpty(orderInfoBean.getDatas().getCourseOrderInfo().getMembcardName())) {
                                    mTvMemberCard.setText(orderInfoBean.getDatas().getCourseOrderInfo().getMemberCardList().get(0).getMembcardName());
                                } else {
                                    mTvMemberCard.setText(orderInfoBean.getDatas().getCourseOrderInfo().getMembcardName());
                                }
                            }else {
                                mLlMemberCardNmae.setVisibility(View.GONE);
                                mRvMemberCardNmae.setVisibility(View.VISIBLE);
                                adaper(orderInfoBean.getDatas().getCourseOrderInfo().getMemberCardList());
                            }
                            //用于取消约课用的id
                            courseOrderId = orderInfoBean.getDatas().getCourseOrderInfo().getCourseOrderId();
                            if (TextUtils.isEmpty(orderInfoBean.getDatas().getCourseOrderInfo().getOrderStatus())) {
                                mLlCancelAboutClass.setBackground(getResources().getDrawable(R.drawable.shape_login_style2));
                                mTvCancelAboutClass.setText("预  约");
                            } else {
                                orderStatus = orderInfoBean.getDatas().getCourseOrderInfo().getOrderStatus();
                                switch (orderStatus) {
                                    case "0":
                                    case "4":
                                        mLlCancelAboutClass.setBackground(getResources().getDrawable(R.drawable.shape_login_style2));
                                        mTvCancelAboutClass.setText("预  约");
                                        break;
                                    case "1":
                                        mLlCancelAboutClass.setBackground(getResources().getDrawable(R.drawable.shape_cancel_right_angle));
                                        mTvCancelAboutClass.setText("取消预约");
                                        break;
                                    case "2":
                                        mLlCancelAboutClass.setBackground(getResources().getDrawable(R.drawable.shape_cancel_right_angle));
                                        mTvCancelAboutClass.setText("预  约");
                                        break;
                                    default:
                                        mLlCancelAboutClass.setBackground(getResources().getDrawable(R.drawable.shape_cancel_right_angle));
                                        mTvCancelAboutClass.setText("预  约");
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 会员卡适配器
     *
     * @param memberCardList*/
    private void adaper(List<CourseOrderInfoBean.DatasBean.CourseOrderInfo.MemberCardListBean> memberCardList){

        mCommonAdapter = new CommonAdapter<CourseOrderInfoBean.DatasBean.CourseOrderInfo.MemberCardListBean>(mActivity,R.layout.bespeaktime_item,memberCardList) {
            @Override
            protected void convert(ViewHolder holder, CourseOrderInfoBean.DatasBean.CourseOrderInfo.MemberCardListBean memberCardListBean, int position) {
                LinearLayout llBespeakTime = holder.getView(R.id.llBespeakTime);
                TextView tvBespeakTime = holder.getView(R.id.tvBespeakTime);
                if (memberCrad == position){
                    //会员卡Id
                    memberCradId = memberCardListBean.getId();
                    llBespeakTime.setBackground(getResources().getDrawable(R.drawable.shape_login_style2));
                    tvBespeakTime.setText(memberCardListBean.getMembcardName());
                    tvBespeakTime.setTextColor(getResources().getColor(R.color.white));
                }else {
                    llBespeakTime.setBackground(getResources().getDrawable(R.drawable.radio_whitenull_back2));
                    tvBespeakTime.setText(memberCardListBean.getMembcardName());
                    tvBespeakTime.setTextColor(getResources().getColor(R.color.home_text_selected));
                }
                llBespeakTime.setOnClickListener(view -> {
                    memberCrad = position;
                    mCommonAdapter.notifyDataSetChanged();
                });
            }
        };

        mRvMemberCardNmae.setAdapter(mCommonAdapter);
    }



    @OnClick({R.id.head_ivBack, R.id.llCancelAboutClass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                if (!orderStatus.equals(bean.getOrderStatus())){
                    Intent intent = new Intent();
                    setResult(ApiCode.Request.CONVERT_ERROR,intent);
                }
                finish();
                break;

            case R.id.llCancelAboutClass:
                switch (orderStatus) {
                    case "0":
                    case "4":
                        //预约课程
                        OrderPublicCourse();
                        break;
                    case "1":
                        //取消预约
                        if (!TextUtils.isEmpty(courseOrderId)) {
                            cancelAboutClass();
                        }
                        break;
                        default:break;
                }

                break;
            default:
                break;
        }
    }


    /**
     * 团课预约
     * <p>
     * id	String		是	签到表id
     * memberId	String		是	会员id
     * cardrelationId	String		是	会员卡关系id
     */
    private void OrderPublicCourse() {
        String url = UrlInterfaceManager.ORDERPUBLICCOURSE;
        Map<String, String> map = new ArrayMap<>();
        map.put("id", bean.getId());
        map.put("memberId", SPUtils.getUserDataBean(mActivity).getId());
        //预约卡id
        map.put("cardrelationId", memberCradId);
        map.put("coursePlanId", bean.getCourseplanId());
        new VisApi().post(url, map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToCenters(mActivity, "团课预约失败!", 1000);
                    }


                    @Override
                    public void onNext(JsonObject jsonObject) {
                        ResultBean resultBean = JSON.parseObject(jsonObject.toString(), ResultBean.class);
                        if (!resultBean.getResult().getCode().equals("0")) {
                            switch (resultBean.getResult().getCode()) {
                                case "5018":
                                    ToastUtils.showToCenters(mActivity, "该课程已过预约时间!", 1000);
                                    break;
                                default:
                                    break;
                            }
                            return;
                        }
                        //获取约课详情
                        orderStatus = "1";
                        if (request){
                            request = false;
                            getCourseOrderInfo();
                        }
                        mLlCancelAboutClass.setBackground(getResources().getDrawable(R.drawable.shape_cancel_right_angle));
                        mTvCancelAboutClass.setText("取消预约");

                    }
                });
    }

    /**
     * 取消约课
     */
    private void cancelAboutClass() {
        String url = UrlInterfaceManager.CANCELCOURSEORDER;
        Map<String, String> map = new ArrayMap<>();
        map.put("id",courseOrderId);
        new VisApi().post(url, map)
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToCenters(mActivity, "取消预约失败!", 1000);
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        ResultBean resultBean = JSON.parseObject(jsonObject.toString(), ResultBean.class);
                        if (!resultBean.getResult().getCode().equals("0")) {
                            switch (resultBean.getResult().getCode()) {
                                case "5042":
                                    ToastUtils.showToCenters(mActivity, "该课程不允许取消预约!", 1000);
                                    break;
                                default:
                                    break;
                            }
                            return;
                        }
                        orderStatus = "4";
                        mLlCancelAboutClass.setBackground(getResources().getDrawable(R.drawable.shape_login_style2));
                        mTvCancelAboutClass.setText("预  约");
                    }
                });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {//按键的按下事件
               return false;
            } else if (event.getAction() == KeyEvent.ACTION_UP && event.getRepeatCount() == 0) {//按键的抬起事件
                if (!orderStatus.equals(bean.getOrderStatus())){
                    Intent intent = new Intent();
                    setResult(ApiCode.Request.CONVERT_ERROR,intent);
                }
                finish();
               return false;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
