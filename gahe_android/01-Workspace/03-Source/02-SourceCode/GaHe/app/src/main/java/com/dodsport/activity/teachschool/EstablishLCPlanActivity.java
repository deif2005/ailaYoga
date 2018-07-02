package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.model.BusiEmployeeListBean;
import com.dodsport.model.ClassroomListBean;
import com.dodsport.model.CourseListBean;
import com.dodsport.model.CoursePlanListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.LogUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
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

import static com.dodsport.weight.TimeUtils.formatDatetime;


/**
 * 创建团课计划
 */
public class EstablishLCPlanActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvCourse)
    TextView mTvCourse;
    @Bind(R.id.llCourse)
    LinearLayout mLlCourse;
    @Bind(R.id.tvClassRoom)
    TextView mTvClassRoom;
    @Bind(R.id.llClassRoom)
    LinearLayout mLlClassRoom;
    @Bind(R.id.tvTeacher)
    TextView mTvTeacher;
    @Bind(R.id.llTeacher)
    LinearLayout mLlTeacher;
    @Bind(R.id.evLeast)
    EditText mEvLeast;
    @Bind(R.id.evMaximum)
    EditText mEvMaximum;
    @Bind(R.id.tvDate)
    TextView mTvDate;
    @Bind(R.id.rlStartTime)
    RelativeLayout mRlStartTime;
    @Bind(R.id.RadioButton1)
    RadioButton mRadioButton1;
    @Bind(R.id.RadioButton2)
    RadioButton mRadioButton2;
    @Bind(R.id.RadioButton3)
    RadioButton mRadioButton3;
    @Bind(R.id.RadioButton4)
    RadioButton mRadioButton4;
    @Bind(R.id.evRemarks)
    EditText mEvRemarks;
    @Bind(R.id.btConservation)
    Button mBtConservation;
    @Bind(R.id.btCancel)
    Button mBtCancel;
    @Bind(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @Bind(R.id.btDelete)
    Button mBtDelete;
    @Bind(R.id.tvDuration)
    TextView mTvDuration;
    @Bind(R.id.tvRemarksText_ets)
    TextView mTvRemarksTextEts;

    private Activity mActivity;
    private TimePickerView pvTime;
    private String TAG = "***团课创建计划--->";
    private PopupWindow mPopupWindow;
    private Integer page = 1;       //获取门店教室 分页标签
    private List<ClassroomListBean.DatasBean.ClassroomList> ClassroomList;      //门店教室
    private List<CourseListBean.DatasBean.CourseList> courseList;       //课程
    private List<BusiEmployeeListBean.DatasBean.EvalListBean> employeeList;     //教练
    private String datetime = "";       //课程开始时间
    private String status = "1";     //状态
    private String taste = "1";     //体验课
    private String courseId = "";   //课程ID
    private String classroomId = "";//教室ID
    private String employeeId = ""; //老师ID
    private String duration = "";//课程时长
    private static final int REQUEST_CAMERA_CODE = 10;
    private CoursePlanListBean.DatasBean.CoursePlanList mCoursePlanList;    //团课数据
    private String key = "";     //用来标识是进来创建计划 还是编辑计划

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establish_lcplan);
        ButterKnife.bind(this);
        mActivity = this;
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        if (key.equals("add")) {     //创建页面进来
            mHeadTvTitle.setText("创建计划");
        } else {     //编辑页面进来
            mHeadTvTitle.setText("排课编辑");
            mBtDelete.setVisibility(View.VISIBLE);
            mCoursePlanList = (CoursePlanListBean.DatasBean.CoursePlanList) intent.getSerializableExtra("Object");
            mTvCourse.setText(mCoursePlanList.getCourseName());
            mTvClassRoom.setText(mCoursePlanList.getClassRoomName());
            mTvTeacher.setText(mCoursePlanList.getEmployeeName());
            mEvLeast.setText(mCoursePlanList.getLowPersons() + "");
            mEvMaximum.setText(mCoursePlanList.getUpperPersons() + "");
            mTvDate.setText(mCoursePlanList.getClassDatetime().substring(0, 16));
            if (mCoursePlanList.getEnable().equals("1")) {   //1、是开启
                mRadioButton1.setChecked(true);
                mRadioButton2.setChecked(false);
                status = "1";
            } else {
                mRadioButton2.setChecked(true);
                mRadioButton1.setChecked(false);
                status = "2";
            }
            if (mCoursePlanList.getIsExperience().equals("1")) {     //1、是可以体验
                mRadioButton3.setChecked(true);
                mRadioButton4.setChecked(false);
                taste = "1";
            } else {
                mRadioButton4.setChecked(true);
                mRadioButton3.setChecked(false);
                taste = "2";
            }
            mEvRemarks.setText(mCoursePlanList.getRemark());
            employeeId = mCoursePlanList.getEmployeeId();
            courseId = mCoursePlanList.getCourseId();
            classroomId = mCoursePlanList.getClassroomId();
            datetime = mCoursePlanList.getClassDatetime();     //开课时间
            duration = mCoursePlanList.getDuration();
            mTvDuration.setText("开课日期和时间" + "(时长：" + duration + "分钟)");


        }
        addTime();
        getSignature();

    }

    @OnClick({R.id.head_ivBack, R.id.llCourse, R.id.btDelete, R.id.llClassRoom, R.id.llTeacher, R.id.rlStartTime, R.id.RadioButton1, R.id.RadioButton2, R.id.RadioButton3, R.id.RadioButton4, R.id.btConservation, R.id.btCancel})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {
                case R.id.btCancel:     //取消
                case R.id.head_ivBack:
                    finish();
                    break;
                case R.id.llCourse:     //课程
                    getCourseList();
                    break;
                case R.id.llClassRoom:  //教室
                    getClassRoomList();
                    break;
                case R.id.llTeacher:    //老师
                    getTeacherList();
                    break;
                case R.id.rlStartTime:  //开始时间
                    pvTime.show();
                    break;
                case R.id.RadioButton1: //状态:启用
                    mRadioButton1.setChecked(true);
                    mRadioButton2.setChecked(false);
                    status = "1";
                    break;
                case R.id.RadioButton2: //状态：禁用
                    mRadioButton1.setChecked(false);
                    mRadioButton2.setChecked(true);
                    status = "2";
                    break;
                case R.id.RadioButton3: //体验课：是
                    mRadioButton3.setChecked(true);
                    mRadioButton4.setChecked(false);
                    taste = "1";
                    break;
                case R.id.RadioButton4: //体验课：否
                    mRadioButton3.setChecked(false);
                    mRadioButton4.setChecked(true);
                    taste = "2";
                    break;
                case R.id.btConservation:   //保存
                    if (TextUtils.isEmpty(courseId)) {
                        ToastUtils.showToCenters(mActivity, "请选择课程!", 1000);
                    } else if (TextUtils.isEmpty(classroomId)) {
                        ToastUtils.showToCenters(mActivity, "请选择教室!", 1000);
                    } else if (TextUtils.isEmpty(employeeId)) {
                        ToastUtils.showToCenters(mActivity, "请选择老师!", 1000);
                    } else if (TextUtils.isEmpty(datetime)) {
                        ToastUtils.showToCenters(mActivity, "请选择开课日期/时间!", 1000);
                    } else if (TextUtils.isEmpty(mEvLeast.getText().toString())) {
                        ToastUtils.showToCenters(mActivity, "请输入最少人数!", 1000);
                    } else if (TextUtils.isEmpty(mEvMaximum.getText().toString())) {
                        ToastUtils.showToCenters(mActivity, "请输入最多人数!", 1000);
                    } else {
                        if (key.equals("add")) {     //创建计划
                            addCoursePlan();

                        } else {     //编辑计划
                            editCoursePlan();
                        }
                    }
                    break;
                case R.id.btDelete:        //删除
                    deleteCoursePlan();
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(AskForLeaveTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;

        if (event.getType().equals("CourseName")) {     //课程名称
            courseId = courseList.get(event.getmPosition()).getId();
            //课程时长
            duration = courseList.get(event.getmPosition()).getDuration();
            mTvDuration.setText("开课日期和时间" + "(时长：" + duration + "分钟)");
        } else if (event.getType().equals("ClassRoomName")) {   //教室名称
            classroomId = ClassroomList.get(event.getmPosition()).getId();
        } else if (event.getType().equals("TeacherName")) {       //老师名称
            employeeId = employeeList.get(event.getmPosition()).getId();
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


    /**
     * 删除团课计划
     */

    private void deleteCoursePlan() {
        if (mCoursePlanList == null)
            return;
        String id = mCoursePlanList.getId();
        OperatingFloorRequest.deleteCoursePlan(id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (resultBean.getCode().equals("0")) {
                    ToastUtils.showToCenters(mActivity, "删除成功!", 1000);
                    Intent intent = new Intent();
                    setResult(REQUEST_CAMERA_CODE, intent);
                    finish();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "删除失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取门店课程列表
     */
    private void getCourseList() {

        String businessId = SPUtils.getUserDataBean(this).getBusinessId();
        OperatingFloorRequest.getListCourseInfo(businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                Log.i(TAG, "获取列表成功-->" + response.get().toString() + "");

                CourseListBean courseListBean = JSON.parseObject(response.get(), CourseListBean.class);
                if (!courseListBean.getResult().getCode().equals("0")) {

                    ToastUtils.showToCenters(mActivity, "获取课程列表失败,请稍后重试!", 1000);
                    return;
                }
                courseList = courseListBean.getDatas().getCourseList();
                //加载适配器
                List<String> data = new ArrayList<String>();
                for (int i = 0; i < courseList.size(); i++) {
                    data.add(courseList.get(i).getCourseName());
                }
                String BusiPosition = "CourseName";
                mPopupWindow = new CompanyNamePopupWindow(mActivity, mHeadIvOK, mTvCourse, data, BusiPosition);
                mPopupWindow.showAtLocation(mTvCourse, Gravity.CENTER, 0, 0);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取列表失败-->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 获取门店教室
     */
    public void getClassRoomList() {
//        ee810841-977e-432d-9a8e-ee660563b302
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();

        OperatingFloorRequest.listClassroomByStoreId(storeId, page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                try {
                    ClassroomListBean classroomListBean = JSON.parseObject(response.get(), ClassroomListBean.class);
                    if (!classroomListBean.getResult().getCode().equals("0")) {

                        return;
                    }
                    List<String> datas = new ArrayList<>();
                    ClassroomList = classroomListBean.getDatas().getClassroomList();
                    for (int i = 0; i < ClassroomList.size(); i++) {
                        datas.add(ClassroomList.get(i).getClassroomName());
                    }

                    String de = "ClassRoomName";
                    mPopupWindow = new CompanyNamePopupWindow(mActivity, mHeadIvOK, mTvClassRoom, datas, de);
                    mPopupWindow.showAtLocation(mTvClassRoom, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取教室列表失败--->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 获取老师列表
     */
    private void getTeacherList() {
        String storeId = SPUtils.getUserDataBean(this).getStoreId();
        OperatingFloorRequest.queryCoachListByStoreId(storeId, page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    LogUtils.i(TAG, "获取列表老师成功---> " + response.get().toString() + "");
                    BusiEmployeeListBean busiEmployeeListBean = JSON.parseObject(response.get(), BusiEmployeeListBean.class);
                    if (!busiEmployeeListBean.getResult().getCode().equals("0")) {

                        return;
                    }
                    employeeList = busiEmployeeListBean.getDatas().getEvalList();
                    List<String> datas = new ArrayList<>();
                    for (int i = 0; i < employeeList.size(); i++) {
                        datas.add(employeeList.get(i).getEmployeeName());
                    }

                    String de = "TeacherName";
                    mPopupWindow = new CompanyNamePopupWindow(mActivity, mHeadIvOK, mTvTeacher, datas, de);
                    mPopupWindow.showAtLocation(mTvTeacher, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取列表成功---> " + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 提交数据
     * */
    private void addCoursePlan() {
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();
        String lowPersons = mEvLeast.getText().toString();
        String upperPersons = mEvMaximum.getText().toString();
        String duration = "60";
        final String remark = mEvRemarks.getText().toString();
        String creator = SPUtils.getUserDataBean(mActivity).getId();
        OperatingFloorRequest.addCoursePlan(storeId, employeeId, courseId, classroomId, lowPersons, upperPersons, datetime, duration, status, taste, creator, remark, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (resultBean.getCode().equals("0")) {
                    ToastUtils.showToCenters(mActivity, "创建成功!", 1000);
                    Intent intent = new Intent();
                    setResult(REQUEST_CAMERA_CODE, intent);
                    finish();
                }

                Log.i(TAG, "提交成功--->" + response.get().toString() + "");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "创建失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 编辑团课计划
     */
    private void editCoursePlan() {
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();
        String lowPersons = mEvLeast.getText().toString();
        String upperPersons = mEvMaximum.getText().toString();
        final String remark = mEvRemarks.getText().toString();
        String creator = SPUtils.getUserDataBean(mActivity).getId();
        String id = mCoursePlanList.getId();
//        Log.i(TAG, "编辑传参-->\temployeeId"+employeeId+"\tcourseId-->"+courseId+"\tclassroomId-->"+classroomId+"\tlowPersons-->"+lowPersons+"\tupperPersons-->"+upperPersons+"\t" +
//                "datetime-->"+datetime+"\tduration-->"+duration+"\tstatus-->"+status+"\ttaste-->"+taste+"\t");
        OperatingFloorRequest.editCoursePlan(id, storeId, employeeId, courseId, classroomId, lowPersons, upperPersons, datetime, duration, status, taste, creator, remark, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "提交成功--->" + response.toString() + "");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (resultBean.getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "创建成功!", 1000);
                        Intent intent = new Intent();
                        setResult(REQUEST_CAMERA_CODE, intent);
                        finish();
                    }else {
                        switch (resultBean.getCode()){
                            case "5016":
                                ToastUtils.showToCenters(mActivity, "该教师该时段已排课,请重选开始时间!", 1000);
                                break;
                            default:
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "创建失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 时间选择器
     */
    private void addTime() {
        // 时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        // 控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
                calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                try {
                    datetime = formatDatetime(date);
                    if (!TextUtils.isEmpty(datetime)) {
                        mTvDate.setText(datetime.substring(0, 16));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 编辑教练简介文字
     */
    public void getSignature() {

        mEvRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTvRemarksTextEts.setText((150 - s.length()) + "/150");
                if (s.length() >= 150) {
                    ToastUtils.showToCenter(mActivity, "您编辑的文字长度超过150!", 0);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
