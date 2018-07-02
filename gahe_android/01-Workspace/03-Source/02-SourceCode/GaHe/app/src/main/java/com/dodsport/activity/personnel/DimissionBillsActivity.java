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
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.parseDate;


/**
 * 离职单 --填写
 */
public class DimissionBillsActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvEntryDate)
    TextView mTvEntryDate;
    @Bind(R.id.tvDimissionDate)
    TextView mTvDimissionDate;
    @Bind(R.id.image1)
    ImageView mImage1;
    @Bind(R.id.tvQuarters)
    TextView mTvQuarters;
    @Bind(R.id.tvConnectName)
    TextView mTvConnectName;
    @Bind(R.id.image2)
    ImageView mImage2;
    @Bind(R.id.tvUnderstand1)
    TextView mTvUnderstand1;
    @Bind(R.id.evUnderstand1)
    EditText mEvUnderstand1;
    @Bind(R.id.tvUnderstand2)
    TextView mTvUnderstand2;
    @Bind(R.id.evUnderstand2)
    EditText mEvUnderstand2;
    @Bind(R.id.btApprove)
    Button mBtApprove;
    @Bind(R.id.llApprove)
    LinearLayout mLlApprove;
    @Bind(R.id.btSubmit)
    Button mBtSubmit;
    @Bind(R.id.llSubmit)
    LinearLayout mLlSubmit;

    private TimePickerView pvTime;
    private String TAG = "***离职单--";
    private UserDataBean.DatasBean.ResponseEmployeeBean userDataBean;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int CODE = 20;
    private String id = ""; //审批人ID
    private String handlerId = "";  //交接人id
    private String leaveDate = "";   //离职日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimission_bills);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        userDataBean = SPUtils.getUserDataBean(this);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("离职单");
        String substring = userDataBean.getEntryDate().substring(0, 10);
        mTvEntryDate.setText(substring);
        addTime();
    }

    @OnClick({R.id.head_ivBack,R.id.tvDimissionDate, R.id.tvConnectName, R.id.btApprove, R.id.btSubmit, R.id.llSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.tvDimissionDate:  //离职日期
                pvTime.show();
                break;
            case R.id.tvConnectName:    //交接人员
                Intent in = new Intent(this, EmployeeManageActivity.class);
                in.putExtra("key","connect");
                startActivityForResult(in, CODE);
                break;
            case R.id.btApprove:    //审批人
                Intent intent = new Intent(this, EmployeeManageActivity.class);
                intent.putExtra("key","Have");
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
                break;
            case R.id.btSubmit:     //提交
            case R.id.llSubmit:
                if (mTvDimissionDate.getText().toString().equals("请选择(必填)")){
                    ToastUtils.showToCenters(this,"请选择离职日期!",800);
                }else if (mTvConnectName.getText().toString().equals("请选择")){
                    ToastUtils.showToCenters(this,"请选择交接人!",800);
                } else if (mBtApprove.getText().toString().equals("+") || TextUtils.isEmpty(id)) {
                    ToastUtils.showToCenters(this,"请选择审批人!",800);
                }else {
                    addDimissionBills();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                case REQUEST_CAMERA_CODE:
                    String name = data.getStringExtra("name");
                    if (!TextUtils.isEmpty(name)){
                        mBtApprove.setText(name);//审批人
                        id = data.getStringExtra("id");
                        Log.i(TAG, "o-审批人-->"+id+"");
                    }
                    break;
                case CODE:
                    String userName = data.getStringExtra("name");
                    if (!TextUtils.isEmpty(userName)){
                        mTvConnectName.setText(userName);//交接人
                        handlerId = data.getStringExtra("id");
                        Log.i(TAG, "o-交接人-->"+handlerId+"");
                    }
                    break;
                default:
                    break;
        }
    }


    /**提交离职单*/
    private void addDimissionBills(){

        /**
         * 提交离职单据数据
         *
         * businessId 商家ID
         * storeId 门点ID
         * employeeId 员工ID
         * leaveDate 离职日期
         * handler 交接人ID
         * leaveReason 离职原因
         * handleItem 所需交接事项
         * approver 审批人ID
         * */

        String businessId = userDataBean.getBusinessId();
        String storeId = userDataBean.getStoreId();
        String employeeId = userDataBean.getId();
        final String leaveReason = mEvUnderstand1.getText().toString();
        final String handleItem = mEvUnderstand2.getText().toString();
        if (TextUtils.isEmpty(leaveReason)){
            ToastUtils.showToCenters(this,"请填写离职原因!",800);
            return;
        }else if (TextUtils.isEmpty(handleItem)){
            ToastUtils.showToCenters(this,"请填写所交接事项!",800);
            return;
        }
        String approver = id;

        Log.i(TAG, "提交离职单传参---->  businessId1-->"+businessId+"\tstoreId-->"+storeId+"\temployeeId-->"+employeeId+"\tleaveDate-->"+leaveDate+
                "\thandler-->"+handlerId+"\tleaveReason-->"+leaveReason+"\thandleItem-->"+handleItem+"\tapprover2-->"+approver+"\t");

        OperatingFloorRequest.addLeaveEmpInfo(businessId, storeId, employeeId, leaveDate, handlerId, leaveReason, handleItem, approver, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "提交成功--->"+response.toString());

                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")){
                    switch (resultBean.getCode()){
                        case "4003":
                            ToastUtils.showToCenters(DimissionBillsActivity.this,"提交失败,请稍后重试!",1000);
                            break;
                        case "5047 ":
                            ToastUtils.showToCenters(DimissionBillsActivity.this, "该单据已经存在，不过重复提交!!", 1000);
                            break;
                        default:
                            break;
                    }
                    return;
                }

                Intent intent = new Intent(DimissionBillsActivity.this,DimissionBillsDetailActivity.class);
                intent.putExtra("Dikey","Dimission");
                intent.putExtra("leaveDate",leaveDate);
                intent.putExtra("handler",handlerId);
                intent.putExtra("leaveReason",leaveReason);
                intent.putExtra("handleItem",handleItem);
                intent.putExtra("ConnectName",mTvConnectName.getText().toString().trim());
                intent.putExtra("DimissionDate",mTvDimissionDate.getText().toString().trim());
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "提交失败--->"+response.toString());
                ToastUtils.showToCenters(DimissionBillsActivity.this,"提交失败,请检查网络或稍后重试!",1000);
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
                    mTvDimissionDate.setText(datetime);
                    leaveDate = datetime;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
