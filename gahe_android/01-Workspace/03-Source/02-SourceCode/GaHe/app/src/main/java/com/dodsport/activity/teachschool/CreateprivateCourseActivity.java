package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.model.BusiEmployeeListBean;
import com.dodsport.model.ClassroomListBean;
import com.dodsport.model.CoursePlanListBean;
import com.dodsport.model.PrivateCoursePlanListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.TimeUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.dodsport.weight.popupwindow.CompanyNamePopupWindow;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.formatTime;
import static com.dodsport.weight.TimeUtils.parseDate;

/**
 * 创建私教课
 */
public class CreateprivateCourseActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvDate)
    TextView mTvDate;
    @Bind(R.id.icon)
    ImageView mIcon;
    @Bind(R.id.tvPrivateCoach)
    TextView mTvPrivateCoach;
    @Bind(R.id.tvEndTime2)
    TextView mTvEndTime2;
    @Bind(R.id.tvEndTime3)
    TextView mTvEndTime3;
    @Bind(R.id.llPrivateCoach)
    LinearLayout mLlPrivateCoach;
    @Bind(R.id.tvPrivateCoach2)
    TextView mTvPrivateCoach2;
    @Bind(R.id.tvEndTime4)
    TextView mTvEndTime4;
    @Bind(R.id.llPrivateCoach2)
    LinearLayout mLlPrivateCoach2;
    @Bind(R.id.rvCourse)
    RecyclerView mRvCourse;
    @Bind(R.id.icon2)
    ImageView mIcon2;
    @Bind(R.id.llDate)
    LinearLayout mLlDate;
    @Bind(R.id.tvDate3)
    TextView mTvDate3;
    @Bind(R.id.icon3)
    ImageView mIcon3;
    @Bind(R.id.llAdd)
    LinearLayout mLlAdd;
    @Bind(R.id.rvCourse2)
    RecyclerView mRvCourse2;
    @Bind(R.id.btConservation)
    Button mBtConservation;
    @Bind(R.id.btCancel)
    Button mBtCancel;
    @Bind(R.id.icon1)
    ImageView mIcon1;
    @Bind(R.id.tvRemindText)
    TextView mTvRemindText;
    @Bind(R.id.tvClassRoom)
    TextView mTvClassRoom;

    private List<String> data = new ArrayList<>();
    private List<String> dataTime = new ArrayList<>();
    private CommonAdapter<CoursePlanListBean.DatasBean.CoursePlanList> mCommonAdapter;
    private CommonAdapter<String> mCommonAdapter2;
    private Activity mActivity;
    private Intent mIntent;
    private PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList mPrivateCourse;
    private TimePickerView pvTime;
    private TimePickerView pvTimes;
    private String TAG = "****编辑课程信息---";
    private List<CoursePlanListBean.DatasBean.CoursePlanList> coursePlanList = new ArrayList<>();
    private String addTime = "";     //要添加的时间
    private Integer page = 1;
    private PopupWindow mPopupWindow;
    private int position = 0;
    private List<ClassroomListBean.DatasBean.ClassroomList> ClassroomList = new ArrayList<>();
    private String classroomId = "";
    private String key = "";
    private String datetime = ""; //创建计划 日期
    private String storeId = "";//门店ID
    private String id = ""; //老师ID
    private String classDate = "";  //当前日期
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private BusiEmployeeListBean.DatasBean.EvalListBean mEmployeeList; //选中老师信息
    private boolean conservation = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createprivate_course);
        ButterKnife.bind(this);
        mActivity = this;
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("创建计划");
        //本日已排课
        mRvCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //私教已排课时间
        mRvCourse2.setLayoutManager(new GridLayoutManager(this, 2));

        // 设置动画时长
        mRvCourse.getItemAnimator().setChangeDuration(300);
        mRvCourse.getItemAnimator().setMoveDuration(300);
        mIntent = getIntent();
        key = mIntent.getStringExtra("key");
        if (key.equals("add")) {        //创建计划进来

            lAdapter();
        } else {        //编辑按钮进来
            mPrivateCourse = (PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList) mIntent.getSerializableExtra("object");
            mIcon1.setVisibility(View.INVISIBLE);
            mLlPrivateCoach.setEnabled(false);
            storeId = mPrivateCourse.getStoreId();
            id = mPrivateCourse.getEmployeeId();
            classDate = mPrivateCourse.getClassDate();
            getLeagueLecture();
            mTvDate.setText(classDate);
            datetime = mPrivateCourse.getClassDate();
            mTvEndTime2.setText(mPrivateCourse.getEmployeeName());
            String jobTitle = mPrivateCourse.getJobTitle();
            mTvEndTime3.setText("(" + UrlInterfaceManager.jobTitle[Integer.parseInt(jobTitle)] + ")");
            mTvClassRoom.setText(mPrivateCourse.getClassroomName());
            mBtCancel.setVisibility(View.GONE);
            mIcon.setVisibility(View.GONE);
        }
        addTime();
        addTimes();
    }


    /**
     * 获取该老师的一天排课（团课）
     */
    private void getLeagueLecture() {

        long stringToDate = TimeUtils.getStringToDate(classDate);
        String millon = TimeUtils.getMillon(stringToDate);
        OperatingFloorRequest.listCoursePlanByEmployeeId(storeId, id,millon, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {

                    CoursePlanListBean coursePlanListBean = JSON.parseObject(response.get(), CoursePlanListBean.class);
                    if (!coursePlanListBean.getResult().getCode().equals("0")) {

                        return;
                    }
                    coursePlanList = coursePlanListBean.getDatas().getCoursePlanList();
                    LeagueLectureAdapter(coursePlanListBean.getDatas().getCoursePlanList());
                    Log.i(TAG, "获取某一天的课程计划成功---》" + response.get().toString() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取某一天的课程计划失败--->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 加载老师一天的排课计划适配器
     */
    private void LeagueLectureAdapter(final List<CoursePlanListBean.DatasBean.CoursePlanList> coursePlanList) {
        mCommonAdapter = new CommonAdapter<CoursePlanListBean.DatasBean.CoursePlanList>(mActivity, R.layout.course_plan_item, coursePlanList) {
            @Override
            protected void convert(ViewHolder holder, CoursePlanListBean.DatasBean.CoursePlanList course, int position) {
                TextView text1 = holder.getView(R.id.text1);
                TextView text2 = holder.getView(R.id.text2);
                TextView text3 = holder.getView(R.id.text3);

                try {
                    String startTime = course.getClassDatetime().substring(0, 16);
                    int endTime = Integer.parseInt(course.getDuration());
                    String mEndTime = TimeUtils.addDateMinut(startTime, endTime);

                    startTime = startTime.substring(11, 16);
                    mEndTime = mEndTime.substring(11,16);
                    text1.setText(startTime+" - "+mEndTime);
                    text2.setText(course.getClassRoomName());
                    text3.setText(course.getCourseName());
                    dataTime.add(text1.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mRvCourse.setAdapter(mCommonAdapter);

        // ","隔开的多个 转换成 Set
        String[] sArray = mPrivateCourse.getClassDatetime().split(",");
        for (String sTagItme : sArray) {
            data.add(sTagItme);
        }

        lAdapter();

    }

    /**私教计划时间段适配器*/
    private void lAdapter(){
        mCommonAdapter2 = new CommonAdapter<String>(mActivity, R.layout.course_timelist_item, CreateprivateCourseActivity.this.data) {
            @Override
            protected void convert(ViewHolder holder, String coursePlanList, final int position) {
                ImageView iconDelete = holder.getView(R.id.icon_Delete);
                TextView tvCardTypename = holder.getView(R.id.tvCardTypename);
                tvCardTypename.setText(coursePlanList);

                /*删除已经添加的时间*/
                iconDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCommonAdapter2.notifyItemRemoved(position);
                        CreateprivateCourseActivity.this.data.remove(position);
                        mCommonAdapter2.notifyItemRangeChanged(position, CreateprivateCourseActivity.this.data.size());
                    }
                });
            }
        };

        mRvCourse2.setAdapter(mCommonAdapter2);
    }

    @OnClick({R.id.head_ivBack, R.id.tvDate, R.id.icon, R.id.llPrivateCoach, R.id.llPrivateCoach2, R.id.tvEndTime4, R.id.llDate, R.id.tvDate3, R.id.icon3,
            R.id.llAdd, R.id.btConservation, R.id.btCancel})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {
                case R.id.head_ivBack:
                    finish();
                    break;
                case R.id.tvDate:
                case R.id.icon:
                    if (key.equals("add")){
                        pvTimes.show();
                    }
                    break;
                case R.id.llPrivateCoach:   //老师信息
                    if (TextUtils.isEmpty(datetime)){
                        ToastUtils.showToCenters(mActivity,"请选择日期!",1000);
                        return;
                    }
                    Intent coachntent = new Intent(mActivity,CoachListInfoActivity.class);
                    startActivityForResult(coachntent,REQUEST_CAMERA_CODE);
                    break;
                case R.id.tvEndTime4:       //本周排课
                    if (TextUtils.isEmpty(datetime)){
                        ToastUtils.showToCenters(mActivity,"请选择日期!",1000);
                        return;
                    }else if (mEmployeeList == null){
                        ToastUtils.showToCenters(mActivity,"请选择排课老师!",1000);
                        return;
                    }
                    Intent intent = new Intent(mActivity, ThisWeekCourseActivity.class);
                    if (key.equals("add")){
                        intent.putExtra("ClassDate",mTvDate.getText().toString().substring(0,10));
                        intent.putExtra("StoreId",mEmployeeList.getStoreId());
                    }else {
                        intent.putExtra("ClassDate",mPrivateCourse.getClassDate());
                        intent.putExtra("StoreId",mPrivateCourse.getStoreId());
                    }
                    startActivity(intent);
                    break;
                case R.id.llDate:       //获取教室
                    if (TextUtils.isEmpty(datetime)){
                        ToastUtils.showToCenters(mActivity,"请选择日期!",1000);
                        return;
                    }
                    getClassroomList();
                    break;
                case R.id.tvDate3:
                case R.id.icon3:
                    pvTime.show();
                    break;
                case R.id.llAdd: //添加时间
                    if (TextUtils.isEmpty(datetime)){
                        ToastUtils.showToCenters(mActivity,"请选择日期!",1000);
                        return;
                    }
                    int visibility = mTvRemindText.getVisibility();
                    if (visibility == View.GONE) {
                        if (mTvDate3.getText().toString().equals("    请选择    ")) {
                            ToastUtils.showToCenters(mActivity, "请选择开始时间!", 1000);
                            return;
                        }
                        data.add(0, addTime);
                        mCommonAdapter2.notifyDataSetChanged();
                        mTvDate3.setText("    请选择    ");
                    } else {
                        ToastUtils.showToCenters(mActivity, "老师在该时间段以后排课计划，请重选!", 1000);
                    }
                    break;
                case R.id.btConservation:   //保存
                    if (key.equals("add")){
                        if (TextUtils.isEmpty(classroomId)){
                            ToastUtils.showToCenters(mActivity, "请选择教室!", 800);
                            return;
                        }
                        conservation = true;
                        addpust();
                    }else {
                        pust();
                    }
                    break;
                case R.id.btCancel:     //新增排课
                    conservation = false;
                    addpust();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  新增私教排期信息
     * */
    private void addpust(){
        if (TextUtils.isEmpty(addTime)) {
            ToastUtils.showToCenters(mActivity, "请选择开始时间!", 1000);
            return;
        }
        String storeId = SPUtils.getUserDataBean(this).getStoreId();
        String employeeId = mEmployeeList.getId();
        String remark = "";
        String creator = SPUtils.getUserDataBean(this).getId();
        String classDatetime ="";
        for (int i = 0; i < data.size(); i++) {
            if (i == (data.size()-1)){
                classDatetime = classDatetime+data.get(i);
            }else {
                classDatetime = classDatetime+data.get(i)+",";
            }
        }
        OperatingFloorRequest.addPrivateCoursePlan(storeId, classroomId, employeeId, datetime, classDatetime, creator, remark, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "创建课程计划成功--->"+response.get().toString()+"");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")){
                    switch (resultBean.getCode()){
                        case "5030":
                            ToastUtils.showToCenters(mActivity,"该老师已经有排课计划!",1000);
                            break;
                        default:
                            break;
                    }
                    return;
                }

                //如果是保存关闭当前页面，否则 重启当前页面
                if (conservation){
                    Intent intent = new Intent();
                    int REQUEST_CAMERA_CODE = 10;
                    setResult(REQUEST_CAMERA_CODE,intent);
                    finish();
                }else {
                    Intent i  = getIntent();
                    finish();
                    startActivity(i);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"创建课程计划失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     *  编辑私教排课列表
     * */
    private void pust(){
        String id = mPrivateCourse.getId();
        String employeeId = mPrivateCourse.getEmployeeId();
        String classDate = mPrivateCourse.getClassDate();
        String classDatetime ="";
        for (int i = 0; i < data.size(); i++) {
            if (i == (data.size()-1)){
                classDatetime = classDatetime+data.get(i);
            }else {
                classDatetime = classDatetime+data.get(i)+",";
            }
            //Log.i(TAG, "已排时间-->"+data.get(i)+"");

        }
        String remark = mPrivateCourse.getRemark();
        OperatingFloorRequest.editPrivateCoursePlan(id, classroomId, employeeId, classDate, classDatetime, remark, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                //Log.i(TAG, "编辑成功--->"+response.get().toString()+"");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (resultBean.getCode().equals("0")){
                    Intent intent = new Intent();
                    int REQUEST_CAMERA_CODE = 10;
                    setResult(REQUEST_CAMERA_CODE,intent);
                    finish();
                }else {
                    ToastUtils.showToCenters(mActivity,"编辑失败,请稍后重试!",1000);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"编辑失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == REQUEST_CAMERA_CODE){
            switch (requestCode){
                case REQUEST_CAMERA_CODE:       //选择老师后
                    mEmployeeList = (BusiEmployeeListBean.DatasBean.EvalListBean) intent.getSerializableExtra("objectKey");
                    if (mEmployeeList!=null){
                        storeId = mEmployeeList.getStoreId();
                        //切换老师后清空已经添加添加的课程时间段
                        if (!TextUtils.isEmpty(id)){
                            if (!id.equals(mEmployeeList.getId())){
                                data.clear();
                                mCommonAdapter2.notifyDataSetChanged();
                            }
                        }
                        id = mEmployeeList.getId();
                        classDate = mTvDate.getText().toString();
                        getLeagueLecture();
                        mTvEndTime2.setText(mEmployeeList.getEmployeeName()+"");
                        String jobTitle = mEmployeeList.getJobTitle();
                        if (!TextUtils.isEmpty(jobTitle)){
                            int i = Integer.parseInt(jobTitle);
                            mTvEndTime3.setText("("+UrlInterfaceManager.jobTitle[i]+")");
                        }
                    }
                    break;
                default:
                    break;
            }


        }
    }

    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(AskForLeaveTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取用户选中教室
        if (event.getType().equals("ClassRoom")) {
            position = event.getmPosition();
            classroomId = ClassroomList.get(position).getId();
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


    /**
     * 开课时间选择器
     */
    private void addTime() {
        // 时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        // 控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
//                calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                try {

                    String datetimes = formatTime(date);
                    String endtime = datetimes.substring(0, 5);
                    mTvDate3.setText("\t" + endtime + "\t");

                    List<String> timeList = new ArrayList<>();
                    timeList.addAll(dataTime);
                    timeList.addAll(data);
                    boolean inTime = false;

                    /*判断开始时间是否在已拍课程的时间区间内*/
                    for (String time : timeList) {
                        inTime = TimeUtils.isInTime(time, endtime);
                        if (inTime) {
                            mTvRemindText.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    /*判断时间是否在已排课程的时间区间内*/
                    if (!inTime) {
                        mTvRemindText.setVisibility(View.GONE);
                        String c = "";
                        if (!key.equals("add")){
                            c = mPrivateCourse.getClassDate() + " " + endtime + "";
                        }else {
                            c = datetime + " " + endtime + "";
                        }

                        addTime = endtime;
                        for (String time : timeList) {
                            String s = TimeUtils.addDateMinut(c, 59);
                            if (!TextUtils.isEmpty(s)) {
                                endtime = s.substring(11, 16);
                            }
                            //Log.i(TAG, "结束时间--->" + endtime + "\t" + c + "\t" + time + "\t");
                            inTime = TimeUtils.isInTime(time, endtime);
                            if (inTime) {
                                mTvRemindText.setVisibility(View.VISIBLE);
                                return;
                            }

                        }

                        //如果结束时间不在已拍课的时间区间内
                        if (!inTime) {
                            mTvRemindText.setVisibility(View.GONE);
                           // Log.i(TAG, "计算时间区间--->" + inTime + "\t" + "\t");
                            if (!key.equals("add")){
                                c = mPrivateCourse.getClassDate() + " " + endtime + "";
                            }else {
                                c = datetime + " " + endtime + "";
                            }
                            int i = 1;
                            if (timeList.size()==0 || timeList == null){
                                i = 60;
                            }
                            String s = TimeUtils.addDateMinut(c, i);
                            if (!TextUtils.isEmpty(s)) {
                                endtime = s.substring(11, 16);
                            }
                            addTime = addTime + " - " + endtime;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 时间选择器
     */
    private void addTimes() {
        // 时间选择器
        pvTimes = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        // 控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
                calendar.get(Calendar.YEAR));
        pvTimes.setTime(new Date());
        pvTimes.setCyclic(false);
        pvTimes.setCancelable(true);
        // 时间选择后回调
        pvTimes.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                try {
                    datetime = parseDate(date);
                    mTvDate.setText(datetime);
                    //在选择老师后切换日期后 重新获取该老师的当日团课
                    if (key.equals("add")){
                        if (!TextUtils.isEmpty(id)){
                        classDate = datetime;
                        getLeagueLecture();
                    }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 获取门店教室"ee810841-977e-432d-9a8e-ee660563b302"
     */
    public void getClassroomList() {

        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();

        OperatingFloorRequest.listClassroomByStoreId(storeId, page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                ClassroomListBean classroomListBean = JSON.parseObject(response.get(), ClassroomListBean.class);
                if (!classroomListBean.getResult().getCode().equals("0")) {

                    return;
                }
                List<String> datas = new ArrayList<>();
                ClassroomList = classroomListBean.getDatas().getClassroomList();
                for (int i = 0; i < classroomListBean.getDatas().getClassroomList().size(); i++) {
                    datas.add(classroomListBean.getDatas().getClassroomList().get(i).getClassroomName());
                }

                String de = "ClassRoom";
                mPopupWindow = new CompanyNamePopupWindow(mActivity, mIcon, mTvClassRoom, datas, de);
                mPopupWindow.showAtLocation(mTvClassRoom, Gravity.CENTER,0,0);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
