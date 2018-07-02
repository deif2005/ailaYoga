package com.dodsport.activity.personnel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.CompanyListActivity;
import com.dodsport.activity.askforleave.HistoryRecordActivity;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.model.DepartmentsBean;
import com.dodsport.model.PositionBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.nohttp.HttpListener;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.IDCardUtil.IDCardUtil;
import com.dodsport.utils.IDCardUtil.Result;
import com.dodsport.utils.JsonUtils;
import com.dodsport.utils.PhoneFormatCheckUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.dodsport.weight.popupwindow.CompanyNamePopupWindow;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.parseDate;


/**
 * 入职单--填写
 */
public class EntryBillsActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.RadioButton1)
    RadioButton mRadioButton1;
    @Bind(R.id.RadioButton2)
    RadioButton mRadioButton2;
    @Bind(R.id.RadioButton3)
    RadioButton mRadioButton3;
    @Bind(R.id.RadioButton4)
    RadioButton mRadioButton4;
    @Bind(R.id.RadioButton5)
    RadioButton mRadioButton5;
    @Bind(R.id.RadioButton6)
    RadioButton mRadioButton6;
    @Bind(R.id.btConfirm)
    Button mBtConfirm;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;
    @Bind(R.id.tvType)
    TextView mTvType;
    @Bind(R.id.tvTypeSelect)
    TextView mTvTypeSelect;
    @Bind(R.id.evName)
    EditText mEvName;
    @Bind(R.id.evPhone)
    EditText mEvPhone;
    @Bind(R.id.evIDNumber)
    EditText mEvIDNumber;
    @Bind(R.id.tvEntryDate)
    TextView mTvEntryDate;
    @Bind(R.id.tvDepartment)
    TextView mTvDepartment;
    @Bind(R.id.tvPlace)
    TextView mTvPlace;
    @Bind(R.id.image1)
    ImageView mImage1;
    @Bind(R.id.image3)
    ImageView mImage3;
    @Bind(R.id.image4)
    ImageView mImage4;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.RadioButton7)
    RadioButton mRadioButton7;
    @Bind(R.id.RadioButton8)
    RadioButton mRadioButton8;
    @Bind(R.id.RadioButton9)
    RadioButton mRadioButton9;
    @Bind(R.id.llAcademicTitle)
    LinearLayout mLlAcademicTitle;
    @Bind(R.id.evTeacherText)
    EditText mEvTeacherText;
    @Bind(R.id.llTeacherSynopsis)
    LinearLayout mLlTeacherSynopsis;
    @Bind(R.id.TeacherText_ets)
    TextView mTeacherTextEts;

    private CompanyNamePopupWindow mPopupWindow;
    // 定义请求码，不同界面的跳转请求码要求不同，为了识别是哪个控件传递数据
    private final static int RETURN = 1;
    private String TAG = "****提交入职单--";
    private String businessId = "";
    private String storeSerialId = "";
    private TimePickerView pvTime;
    private String TypeSelectText = "";
    private String sex = "1";
    private String FullTime = "1";
    private Integer coachExists = 2;
    private EventBus mEventBus;
    private int position = 0;   //岗位id
    private int mDepartment = 0;//部门
    private List<PositionBean.DatasBean.PositionListBean> positionList; //职位
    private List<DepartmentsBean> departmentsBeen;  //部门
    private Integer jobTitle = 1;   //职称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_bills);
        ButterKnife.bind(this);
        mEventBus.getDefault().register(this);
        initView();

//        ini();//测试接口

    }


    private void ini() {

//         Request<JSONObject>  request = new FastJsonRequest(UrlInterfaceManager.ADDCOURSEANDCARD, RequestMethod.POST);
//        CourseAndCard CourseAndCard = new CourseAndCard();
//        CourseAndCard.setId("001");
//        CourseAndCard.setEnable("测试");
//        String s = new Gson().toJson(CourseAndCard);
//        request.add("token",SPUtils.getToken(this));
//        request.add("courseAndCard",s);
//        request.setDefineRequestBodyForJson(s);
//        RequestMeansManager.request2(this,0, request, objectListener, true, true);
////
//
////         这里用的是自定义的JavaBeanRequest对象对请求，里面用fastjson解析服务器的数据。
//        Request<CourseAndCard> requests = new JavaBeanRequest<>(UrlInterfaceManager.ADDCOURSEANDCARD, CourseAndCard.class);
//        CourseAndCard Co = new CourseAndCard();
//        Co.setId("001");
//        Co.setEnable("测试");
//        String ss = new Gson().toJson(Co);
//        requests.add("token",SPUtils.getToken(this));
////        requests.add("CourseAndCard",ss);
//        requests.add("id","002");
//        requests.add("enable","00000");
////        requests.setDefineRequestBodyForJson(ss);
//        RequestMeansManager.request(0, requests, onResponseListenerss);


        Request<String> request3 = NoHttp.createStringRequest(UrlInterfaceManager.ADDCOURSEANDCARD, RequestMethod.POST);
        ArrayList<CourseAndCardBean.CourseAndCard> CourseAndCard = new ArrayList<>();

        request3.add("token", SPUtils.getToken(this));

        String Css = "";
        for (int i = 0; i < 5; i++) {
            CourseAndCardBean.CourseAndCard C = new CourseAndCardBean.CourseAndCard();
            C.setId("001");
            C.setEnable("测试");
            C.setCourseId("000000");
            C.setMembcardId("0000000");
            C.setMembcardName("55555");
            CourseAndCard.add(C);

        }
        CourseAndCardBean s = new CourseAndCardBean();
        s.setCourseAndCard(CourseAndCard);

        Css = new Gson().toJson(s.CourseAndCard);
        request3.add("dataJson", Css);
//        request3.setDefineRequestBody(Css,"CourseAndCard");
//        request3.setDefineRequestBodyForJson(JSONObject jsonBody);
        request3.setDefineRequestBodyForJson(Css);
//        RequestMeansManager.request2(this,0, request3, onResponseListener,true,true);
    }

    private HttpListener<String> onResponseListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            Log.i(TAG, "测试提交对象成功--33->" + response.toString() + "");
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            Log.i(TAG, "测试提交对象失败--33->" + response.toString() + "");
        }
    };

    public static class CourseAndCardBean implements Serializable {
        ArrayList<CourseAndCard> CourseAndCard;

        public ArrayList<CourseAndCard> getCourseAndCards() {
            return CourseAndCard;
        }


        public void setCourseAndCard(ArrayList<CourseAndCard> courseAndCard) {
            CourseAndCard = courseAndCard;
        }

        public CourseAndCardBean() {
        }

        @Override
        public String toString() {
            return "CourseAndCards{" +
                    "CourseAndCard=" + CourseAndCard +
                    '}';
        }

        public static class CourseAndCard implements Serializable {
            String id;
            String courseId;    //课程id
            String membcardId;  //会员卡id
            String membcardName; //会员卡名称
            String enable;       //是否启用1未启用，2启用


            public CourseAndCard() {
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getMembcardId() {
                return membcardId;
            }

            public void setMembcardId(String membcardId) {
                this.membcardId = membcardId;
            }

            public String getMembcardName() {
                return membcardName;
            }

            public void setMembcardName(String membcardName) {
                this.membcardName = membcardName;
            }

            public String getEnable() {
                return enable;
            }

            public void setEnable(String enable) {
                this.enable = enable;
            }

            @Override
            public String toString() {
                return "CourseAndCard{" +
                        "id='" + id + '\'' +
                        ", courseId='" + courseId + '\'' +
                        ", membcardId='" + membcardId + '\'' +
                        ", membcardName='" + membcardName + '\'' +
                        ", enable='" + enable + '\'' +
                        '}';
            }
        }
    }


    //初始化
    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("入职单");
        mHeadTvOK.setText("历史记录");
        addTime();
    }


    @OnClick({R.id.RadioButton1, R.id.RadioButton2, R.id.RadioButton3, R.id.RadioButton4, R.id.RadioButton5,
            R.id.RadioButton6, R.id.btConfirm, R.id.llConfirm, R.id.head_ivBack, R.id.head_tvOK,
            R.id.tvTypeSelect, R.id.tvEntryDate, R.id.tvDepartment, R.id.tvPlace, R.id.RadioButton7, R.id.RadioButton8, R.id.RadioButton9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:      //返回
                finish();
                break;
            case R.id.head_tvOK:        //历史记录
                Intent intents = new Intent(this, HistoryRecordActivity.class);
                intents.putExtra("key", "Entry");
                intents.putExtra("type", "6");
                startActivity(intents);
                break;
            case R.id.tvTypeSelect:     //门店名称
                Intent intent = new Intent(this, CompanyListActivity.class);
                intent.putExtra("company", SPUtils.getUserDataBean(this).getBusinessName());
                intent.putExtra("position", "EntryBills");
                startActivityForResult(intent, RETURN);
                break;
            case R.id.tvEntryDate:      //入职日期
                pvTime.show();
                break;
            case R.id.tvDepartment:     //部门
                if (!TypeSelectText.equals("")) {
                    getDepartment();
                } else {
                    ToastUtils.showToCenters(this, "请选择门店!", 800);
                }
                break;
            case R.id.tvPlace:          //岗位
                getBusiPosition();
                break;
            case R.id.RadioButton1:     //就职状态 全职
                mRadioButton1.setChecked(true);
                mRadioButton2.setChecked(false);
                FullTime = "1";
                break;
            case R.id.RadioButton2:     //就职状态 兼职
                mRadioButton2.setChecked(true);
                mRadioButton1.setChecked(false);
                FullTime = "2";
                break;
            case R.id.RadioButton3:     //性别 男
                mRadioButton3.setChecked(true);
                mRadioButton4.setChecked(false);
                sex = "1";
                break;
            case R.id.RadioButton4:     //性别 女
                mRadioButton4.setChecked(true);
                mRadioButton3.setChecked(false);
                sex = "2";
                break;
            case R.id.RadioButton5:     //是否教练  是
                mRadioButton5.setChecked(true);
                mRadioButton6.setChecked(false);
                coachExists = 2;
                mLlAcademicTitle.setVisibility(View.VISIBLE);
                mLlTeacherSynopsis.setVisibility(View.VISIBLE);
                break;
            case R.id.RadioButton6:     //是否教练 否
                mRadioButton6.setChecked(true);
                mRadioButton5.setChecked(false);
                coachExists = 1;
                mLlAcademicTitle.setVisibility(View.GONE);
                mLlTeacherSynopsis.setVisibility(View.GONE);
                break;
            case R.id.RadioButton7:     //高级教练
                mRadioButton7.setChecked(true);
                mRadioButton8.setChecked(false);
                mRadioButton9.setChecked(false);
                jobTitle = 3;
                break;
            case R.id.RadioButton8:     //中级教练
                mRadioButton7.setChecked(false);
                mRadioButton8.setChecked(true);
                mRadioButton9.setChecked(false);
                jobTitle = 2;
                break;
            case R.id.RadioButton9:     //初级教练
                mRadioButton7.setChecked(false);
                mRadioButton8.setChecked(false);
                mRadioButton9.setChecked(true);
                jobTitle = 1;
                break;
            case R.id.btConfirm:    //确定
            case R.id.llConfirm:
                boolean phoneLegal = PhoneFormatCheckUtils.isPhoneLegal(mEvPhone.getText().toString());
                String name = mEvName.getText().toString();
                String regex = "[\u4E00-\u9FA5]+";
                if (name.length() <= 1 || !name.matches(regex)) {
                    ToastUtils.showToCenters(this, "请输入正确的名称!", 1000);
                } else if (TextUtils.isEmpty(mEvPhone.getText().toString()) || !phoneLegal) {
                    ToastUtils.showToCenters(this, "请输入正确的手机号码!", 1000);
                } else if (TextUtils.isEmpty(mEvIDNumber.getText().toString())) {
                    ToastUtils.showToCenters(this, "请输入正确的身份证号码!", 1000);
                } else if (TextUtils.isEmpty(mTvEntryDate.getText().toString())) {
                    ToastUtils.showToCenters(this, "请选择入职日期!", 1000);
                } else if (TextUtils.isEmpty(mTvDepartment.getText().toString())) {
                    ToastUtils.showToCenters(this, "请选择部门!", 1000);
                } else if (TextUtils.isEmpty(mTvPlace.getText().toString())) {
                    ToastUtils.showToCenters(this, "请选择岗位!", 1000);
                } else {
                    Result result = IDCardUtil.validateIDNum(mEvIDNumber.getText().toString());
                    boolean show = result.show(this);
                    if (!show) {
                        addEmployeeInfo();
                    }
                }


                break;
        }
    }


    /**
     * 获取部门
     */
    private void getDepartment() {
        OperatingFloorRequest.getListBusiDepartmentInfo(businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")) {
                    switch (resultBean.getCode()) {
                        case "4001":
                            break;
                    }
                    return;
                }

                try {
                    JSONObject json = new JSONObject(statusBean.getDatas());
                    JSONArray departments = json.getJSONArray("departments");
                    departmentsBeen = JsonUtils.fromJSONArrayToList(departments, DepartmentsBean.class);
                    mImage3.setImageResource(R.drawable.xiang_xia);
                    String de = "DepartmentName";
                    List<String> data = new ArrayList<String>();
                    for (int i = 0; i < departmentsBeen.size(); i++) {
                        data.add(departmentsBeen.get(i).getDepName());
                    }
                    mPopupWindow = new CompanyNamePopupWindow(EntryBillsActivity.this, mImage3, mTvDepartment, data, de);
                    mPopupWindow.showAsDropDown(mTvDepartment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取商家部门失败--->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取职位
     */
    private void getBusiPosition() {
        Log.i(TAG, "商家ID" + businessId + "");
        OperatingFloorRequest.getListBusiPositionInfo(businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    PositionBean positionBean = JSON.parseObject(response.get(), PositionBean.class);
                    if (!positionBean.getResult().getCode().equals("0")) {
                        switch (positionBean.getResult().getCode()) {
                            case "":
                                break;
                        }
                        return;
                    }
                    positionList = positionBean.getDatas().getPositionList();
                    Log.i(TAG, "职位集合" + positionList.toString() + "");
                    List<String> data = new ArrayList<String>();
                    for (int i = 0; i < positionList.size(); i++) {
                        data.add(positionList.get(i).getPositionName());
                    }
                    String BusiPosition = "PositionName";
                    mImage4.setImageResource(R.drawable.xiang_xia);
                    mPopupWindow = new CompanyNamePopupWindow(EntryBillsActivity.this, mImage4, mTvPlace, data, BusiPosition);
                    mPopupWindow.showAsDropDown(mTvPlace);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "职位数据失败-->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 提交员工数据
     * <p>
     * employeeName 员工姓名
     * phoneNum 员工电话
     * positionId   员工职位Id
     * empRela  员工角色(全职、兼职)
     * sex  员工性别
     * businessId   商家ID
     * storeId 门店Id
     * creator  创建人 用户ID
     * idCard 员工证件号
     * depId    是否 教练
     */
    private void addEmployeeInfo() {

        //新增员工
        String employeeName = mEvName.getText().toString();
        String phoneNum = mEvPhone.getText().toString();
        String positionId = positionList.get(position).getId();//职位ID
        int full = Integer.parseInt(FullTime);  //就职状态
        Integer empRela = full;
        int i = Integer.parseInt(sex);  //性别
        Integer sex = i;
        //int instrutcor = Integer.parseInt(jobTitle);
        String depId = departmentsBeen.get(mDepartment).getId();    //部门ID
        String businessId = positionList.get(position).getBusinessId(); //商家ID
        String creator = SPUtils.getUserDataBean(this).getId();
        String idCard = mEvIDNumber.getText().toString();      //员工身份证号
        String storeId = storeSerialId;  //门店ID
        Log.i(TAG, "新增员工信息成功--->" + storeSerialId + "");
        String text = mEvTeacherText.getText().toString();

        /*if (Instructor.equals("1")) {    //如果是教练 跳转到填写教练资料页面
            Bundle bundle = new Bundle();
            bundle.putString("employeeName", employeeName);
            bundle.putString("phoneNum", phoneNum);
            bundle.putString("positionId", positionId);
            bundle.putInt("empRela", empRela);
            bundle.putInt("sex", sex);
            bundle.putInt("jobTitle", jobTitle);
            bundle.putString("depId", depId);
            bundle.putString("businessId", businessId);
            bundle.putString("creator", creator);
            bundle.putString("idCard", idCard);
            bundle.putString("storeId", storeId);

            Intent TeacherIntent = new Intent(this, TeacherEntryBillsActivity.class);
            TeacherIntent.putExtra("bundle", bundle);
            startActivity(TeacherIntent);
            return;
        }*/

        Log.i(TAG, "新增员工信息传参--->" + employeeName + "\t" + phoneNum + "\t" + idCard + "\t" + positionId + "\t" + empRela + "\t" + sex + "\t" + businessId + "\t" + depId + "\t" + creator + "\t" + jobTitle + "\t");
        OperatingFloorRequest.addEmployeeInfo1(employeeName, phoneNum, idCard, positionId, empRela, sex, businessId, depId, creator, storeId,jobTitle,coachExists,text, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "新增员工信息成功--->" + response.toString());

                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")) {
                    switch (resultBean.getCode()) {
                        case "4003":
                            ToastUtils.showToCenters(EntryBillsActivity.this, "提交失败!", 800);
                            break;
                        case "5005":
                            ToastUtils.showToCenters(EntryBillsActivity.this, "该员工已存在!", 800);
                            break;
                    }
                    return;
                }
                ToastUtils.showToCenters(EntryBillsActivity.this, "该员工入职成功!", 1000);
                finish();

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "新增员工信息失败--->" + response.toString());
                ToastUtils.showToCenters(EntryBillsActivity.this, "提交失败!", 800);
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(AskForLeaveTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取用户选中请假类型
        if (event.getType().equals("PositionName")) {
            position = event.getmPosition();
        } else if (event.getType().equals("DepartmentName")) {
            mDepartment = event.getmPosition();
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


    /**
     * 时间选择器
     */
    private void addTime() {
        // 时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
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
                    String datetime = parseDate(date);
                    mTvEntryDate.setText(datetime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 如果请求码为 sendUser 返回码 为 RESULT_OK RESULT_OK为系统自定义的int值为 -1
        if (requestCode == RETURN && resultCode == RESULT_OK) {
            // 在TextView中设置返回信息
            TypeSelectText = data.getStringExtra("Company");
            mTvTypeSelect.setText(TypeSelectText);
            businessId = data.getStringExtra("businessId");
            storeSerialId = data.getStringExtra("storeSerialId");
        }
    }


    /**
     * 编辑教练简介文字
     */
    public void getSignature() {

        mEvTeacherText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTeacherTextEts.setText((150 - s.length()) + "/150");
                if (s.length() >= 150) {
                    ToastUtils.showToCenter(EntryBillsActivity.this, "您编辑的文字长度超过150!", 0);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.getDefault().unregister(this);
    }
}
