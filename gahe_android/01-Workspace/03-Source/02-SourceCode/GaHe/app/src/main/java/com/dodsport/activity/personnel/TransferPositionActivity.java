package com.dodsport.activity.personnel;

import android.content.Intent;
import android.os.Bundle;
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
import com.dodsport.activity.EmployeeManageActivity;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.model.DepartmentsBean;
import com.dodsport.model.PositionBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.JsonUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.dodsport.weight.popupwindow.CompanyNamePopupWindow;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.parseDate;

/**
 * 调岗单 --提交
 * @author Administrator
 */
public class TransferPositionActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvLabel1)
    TextView mTvLabel1;
    @Bind(R.id.tvLabel2)
    TextView mTvLabel2;
    @Bind(R.id.tvLabel3)
    TextView mTvLabel3;
    @Bind(R.id.tvLabel4)
    TextView mTvLabel4;
    @Bind(R.id.tvLabel5)
    TextView mTvLabel5;
    @Bind(R.id.tvLabel6)
    TextView mTvLabel6;
    @Bind(R.id.tvLabel7)
    TextView mTvLabel7;
    @Bind(R.id.tvUnderstand1)
    TextView mTvUnderstand1;
    @Bind(R.id.evUnderstand1)
    EditText mEvUnderstand1;
    @Bind(R.id.tvDepartmentText)
    TextView mTvDepartmentText;
    @Bind(R.id.tvDepartment)
    TextView mTvDepartment;
    @Bind(R.id.image1)
    ImageView mImage1;
    @Bind(R.id.tvPlaceText)
    TextView mTvPlaceText;
    @Bind(R.id.tvPlace)
    TextView mTvPlace;
    @Bind(R.id.image2)
    ImageView mImage2;
    @Bind(R.id.tvAdjustDate)
    TextView mTvAdjustDate;
    @Bind(R.id.image3)
    ImageView mImage3;
    @Bind(R.id.btApprove)
    Button mBtApprove;
    @Bind(R.id.llApprove)
    LinearLayout mLlApprove;
    @Bind(R.id.btSubmit)
    Button mBtSubmit;
    @Bind(R.id.llSubmit)
    LinearLayout mLlSubmit;
    private String businessId;
    private CompanyNamePopupWindow mPopupWindow;
    private List<DepartmentsBean> departmentsBeen;
    private String TAG = "***提交调岗单--";
    private List<PositionBean.DatasBean.PositionListBean> positionList;
    private TimePickerView pvTime;
    private int position = 0;
    private int mDepartment = 0;
    UserDataBean.DatasBean.ResponseEmployeeBean userDataBean;
    private static final int REQUEST_CAMERA_CODE = 10;
    private String id = ""; //审批人Id
    private String transferDepId = "";//调岗后的部门Id
    private String transferPositionId = "";//调岗后的职位Id



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_position);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        userDataBean = SPUtils.getUserDataBean(this);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("调岗单");
        mTvDepartmentText.setText("调整后部\n"+"门");
        mTvPlaceText.setText("调整后岗\n"+"位");

        businessId = userDataBean.getBusinessId();
        mTvLabel1.setText("门店名称\t\t\t\t"+userDataBean.getStoreName());
        mTvLabel2.setText("部 \t\t\t门\t\t\t\t"+userDataBean.getDepName());
        mTvLabel3.setText("工 \t\t\t号\t\t\t\t"+userDataBean.getEmployeeSerialId());
        mTvLabel4.setText("岗 \t\t\t位\t\t\t\t"+userDataBean.getPositionName());
        mTvLabel5.setText("证   件 号\t\t\t\t"+userDataBean.getIdCard());
//        String dateToString2 = TimeUtils.getDateToString2(userDataBean.getEntryTime());
        String EntryDate = userDataBean.getEntryDate().substring(0, 10);
        mTvLabel6.setText("入职日期\t\t\t\t"+EntryDate);
        mTvLabel7.setText("工 \t\t\t龄\t\t\t\t"+userDataBean.getWorkDuration());

        addTime();
    }

    /**
     * 获取调岗单数据
     */
    private void getTransferPositionExpenses() {

        OperatingFloorRequest.getTransferBillDetail("", new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    @OnClick({R.id.head_ivBack, R.id.tvDepartment, R.id.tvPlace, R.id.tvAdjustDate, R.id.btApprove, R.id.btSubmit, R.id.llSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:      //返回
                finish();
                break;
            case R.id.tvDepartment:     //调整后部门
                getDepartment();
                break;
            case R.id.tvPlace:        //调整后岗位
                getBusiPosition();
                break;
            case R.id.tvAdjustDate:  //调整日期
                pvTime.show();
                break;
            case R.id.btApprove:    //审批人
                Intent intent = new Intent(this, EmployeeManageActivity.class);
                intent.putExtra("key","Have");
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
                break;
            case R.id.btSubmit:     //提交
            case R.id.llSubmit:
                if (mEvUnderstand1.getText().toString().equals("请输入")|| TextUtils.isEmpty(mEvUnderstand1.getText().toString())){
                    ToastUtils.showToCenters(this,"请输入正确的调岗原因！",800);
                }else if (mTvDepartment.getText().toString().equals("请选择(必填)")){
                    ToastUtils.showToCenters(this,"请选择部门！",800);
                }else if (mTvPlace.getText().toString().equals("请选择(必填)")){
                    ToastUtils.showToCenters(this,"请选择职位！",800);
                }else if(mTvAdjustDate.getText().toString().equals("请选择(必填)")){
                    ToastUtils.showToCenters(this,"请选择时间！",800);
                }else {
                    addTransferPositionExpenses();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CAMERA_CODE){
            switch (requestCode){
                case REQUEST_CAMERA_CODE:
                    String name = data.getStringExtra("name");
                    if (!TextUtils.isEmpty(name)){
                        mBtApprove.setText(name);//审批人
                        id = data.getStringExtra("id");
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * token	string		是	用户通行口令
     businessId	String		是	商家id
     storeId	String		是	门店id
     employeeId	String		是	员工id
     transferReason	String		是	调岗原因
     transferDate	String		是	调岗日期
     transferPositionId	String		是	调岗职位
     transferDepId	String		是	调岗部门
     approver	String		是	审批人id
     String businessId, String storeId, String employeeId, String transferReason, String transferDate
     ,String transferPositionId,String transferDepId, String approver

     * */
    private void addTransferPositionExpenses(){

        String businessId = userDataBean.getBusinessId();
        String storeId = userDataBean.getStoreId();
        String employeeId = userDataBean.getId();
        final String text = mEvUnderstand1.getText().toString();
        final String AdjustDate = mTvAdjustDate.getText().toString();
        String approver = id;

        Log.i(TAG, "请求参数-->"+"\tbusinessId-->"+businessId+"\tstoreId-->"+storeId+"\temployeeId-->"+"\ttext-->"+"\tDepartment-->"+transferDepId+"\tPlace-->"+transferPositionId+"\tAdjustDate-->"+AdjustDate+"\t");
        OperatingFloorRequest.addTransferEmpInfo(businessId, storeId, employeeId, text, AdjustDate, transferPositionId, transferDepId, approver, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "返回成功-->"+response.toString()+"");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        switch (resultBean.getCode()){
                            case "4003":
                                break;
                            default:
                                break;

                        }
                        ToastUtils.showToCenters(TransferPositionActivity.this,"单据提交失败，请稍后重试!",1000);
                        return;
                    }
                    ToastUtils.showToCenters(TransferPositionActivity.this,"单据提交成功!",1000);
                    Intent intent = new Intent(TransferPositionActivity.this,TransferPDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("text",text);
                    bundle.putString("Department",mTvDepartment.getText().toString());
                    bundle.putString("Place",mTvPlace.getText().toString());
                    bundle.putString("AdjustDate",AdjustDate);
                    intent.putExtra("keys","Transfer");
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(TransferPositionActivity.this,"提交失败，请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
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
                try {
                    Log.i(TAG, "获取部门--->"+response.get().toString()+"");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")) {
                    switch (resultBean.getCode()) {
                        case "4001":
                            break;
                    }
                    return;
                }
                    JSONObject json = new JSONObject(statusBean.getDatas());
                    JSONArray departments = json.getJSONArray("departments");
                    departmentsBeen = JsonUtils.fromJSONArrayToList(departments, DepartmentsBean.class);
                    mImage1.setImageResource(R.drawable.xiang_xia);
                    String de = "DepartmentName";
                    List<String> data = new ArrayList<String>();
                    for (int i = 0; i < departmentsBeen.size(); i++) {
                        data.add(departmentsBeen.get(i).getDepName());
                    }
                    mPopupWindow = new CompanyNamePopupWindow(TransferPositionActivity.this, mImage1, mTvDepartment, data,de);
                    mPopupWindow.showAsDropDown(mTvDepartment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(TransferPositionActivity.this,"获取部门失败，请稍后重试!",1000);
                Log.i(TAG, "获取商家部门失败--->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * -获取职位
     */
    private void getBusiPosition() {
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
                    mImage2.setImageResource(R.drawable.xiang_xia);
                    mPopupWindow = new CompanyNamePopupWindow(TransferPositionActivity.this, mImage2, mTvPlace, data,BusiPosition);
                    mPopupWindow.showAsDropDown(mTvPlace);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(TransferPositionActivity.this,"获取职位失败，请稍后重试!",1000);
                Log.i(TAG, "职位数据失败-->" + response.toString() + "");
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
            transferPositionId = positionList.get(position).getId();
            Log.i(TAG, "id---->"+transferPositionId.toString()+"");


        }else if(event.getType().equals("DepartmentName")){
            mDepartment = event.getmPosition();
            transferDepId = departmentsBeen.get(mDepartment).getId();
            Log.i(TAG, "部门id---->"+ transferDepId.toString()+"");
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
                    String datetime = parseDate(date);
                    mTvAdjustDate.setText(datetime);
//                    long currentTimeMillis = System.currentTimeMillis();
//
//                    long time = date.getTime();
//
//                    if(time>currentTimeMillis)
//                    {
//                        ToastUtils.showToCenter(TransferPositionActivity.this,"不能大于当前时间",0);
//                        return;
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
