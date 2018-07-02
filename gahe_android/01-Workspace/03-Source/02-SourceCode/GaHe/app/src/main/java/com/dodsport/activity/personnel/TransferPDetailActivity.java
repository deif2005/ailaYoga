package com.dodsport.activity.personnel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.askforleave.SuggestionActivity;
import com.dodsport.model.BIllDetailBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.TransferBillsBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.parseDate;

/**
 * 调岗单详情
 *
 * @author Administrator
 */
public class TransferPDetailActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvSubmitName)
    TextView mTvSubmitName;
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
    @Bind(R.id.tvLabel8)
    TextView mTvLabel8;
    @Bind(R.id.tvLabel9)
    TextView mTvLabel9;
    @Bind(R.id.tvLabel10)
    TextView mTvLabel10;
    @Bind(R.id.tvLabel11)
    TextView mTvLabel11;
    @Bind(R.id.btAgree)
    Button mBtAgree;
    @Bind(R.id.btRefuse)
    Button mBtRefuse;
    @Bind(R.id.btRevise)
    Button mBtRevise;
    @Bind(R.id.tvLabelName9)
    TextView mTvLabelName9;
    @Bind(R.id.tvLabelName10)
    TextView mTvLabelName10;
    @Bind(R.id.llAdjustDate)
    LinearLayout mLlAdjustDate;
    @Bind(R.id.rlApproveOperation)
    RelativeLayout mRlApproveOperation;
    @Bind(R.id.tvCondition)
    TextView mTvCondition;
    @Bind(R.id.tvItem0)
    TextView mTvItem0;
    @Bind(R.id.llItem0)
    LinearLayout mLlItem0;
    @Bind(R.id.ivStatusbg)
    ImageView mIvStatusbg;


    private UserDataBean.DatasBean.ResponseEmployeeBean mUserDataBean;
    private TimePickerView pvTime;
    private String approveStatus = "0"; //审核状态
    private String employeeId = "";     //员工Id
    private String approveDesc = "1";    //审批拒绝理由
    private String TAG = "***调岗单--";
    private String id = "";//单据id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_p_detail);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        mUserDataBean = SPUtils.getUserDataBean(this);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("调岗单");
        mTvLabelName9.setText("调岗后部\n" + "门");
        mTvLabelName10.setText("调岗后职\n" + "位");
        Intent intent = getIntent();
        String transfer = intent.getStringExtra("keys");

        if (transfer.equals("Transfer")) {   //从提交调岗单页面进来
            mBtRevise.setVisibility(View.GONE);
            mRlApproveOperation.setVisibility(View.GONE);

            Bundle bundle = intent.getBundleExtra("bundle");
            mTvSubmitName.setText(mUserDataBean.getEmployeeName());
            mTvLabel1.setText(mUserDataBean.getStoreName());
            mTvLabel2.setText(mUserDataBean.getDepName());
            mTvLabel3.setText(mUserDataBean.getEmployeeSerialId());
            mTvLabel4.setText(mUserDataBean.getIdCard());
            mTvLabel5.setText(mUserDataBean.getPositionName());
//            String EntryTime = TimeUtils.getDateToString2(mUserDataBean.getEntryTime());
            String EntryTime = mUserDataBean.getEntryDate().substring(0, 10);   //入职日期
            mTvLabel6.setText(EntryTime);
            mTvLabel7.setText(mUserDataBean.getWorkDuration());
            mTvLabel8.setText(bundle.getString("text"));
            mTvLabel9.setText(bundle.getString("Department"));
            mTvLabel10.setText(bundle.getString("Place"));
            mTvLabel11.setText(bundle.getString("AdjustDate"));

            return;
            //从审批模块进来
        } else if (transfer.equals("TransferAPOK")) {
            //已审批
            mRlApproveOperation.setVisibility(View.GONE);
            mBtRevise.setVisibility(View.GONE);
        } else {
            //未审批
            mRlApproveOperation.setVisibility(View.VISIBLE);
            addTime();
        }
        getTransferPositionExpenses(intent);

    }

    /**
     * 获取调岗单数据
     */
    private void getTransferPositionExpenses(Intent intent) {
        BIllDetailBean.BillsBean data = (BIllDetailBean.BillsBean) intent.getSerializableExtra("data");
        String id = data.getId();
        Log.i(TAG, "单据ID-->" + id + "");
        OperatingFloorRequest.getTransferBillDetail(id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "返回成功--> " + response.toString() + "");
                TransferBillsBean transferBillsBean = JSON.parseObject(response.get(), TransferBillsBean.class);
                if (!transferBillsBean.getResult().getCode().equals("0")) {
                    switch (transferBillsBean.getResult().getCode()) {
                        case "4003":
                            ToastUtils.showToCenters(TransferPDetailActivity.this, "加载失败,请稍后重试!", 1000);
                            break;
                        default:
                            break;
                    }
                    return;
                }
                showContext(transferBillsBean);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "返回失败--> " + response.toString() + "");
                ToastUtils.showToCenters(TransferPDetailActivity.this, "加载失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void showContext(TransferBillsBean transferBillsBean) {
        id = transferBillsBean.getDatas().getTransferBill().getId();
        TransferBillsBean.DatasBean.TransferBills transferBills = transferBillsBean.getDatas().getTransferBill();
        mBtRevise.setVisibility(View.VISIBLE);
        mRlApproveOperation.setVisibility(View.VISIBLE);

        mTvSubmitName.setText(transferBills.getEmployeeName());
        mTvLabel1.setText(transferBills.getStoreName());  //门店
        mTvLabel2.setText(transferBills.getDepName());
        mTvLabel3.setText(transferBills.getEmpId());   //工号
        mTvLabel3.setVisibility(View.GONE);
        mTvLabel4.setText(transferBills.getIdCard());
        mTvLabel5.setText(transferBills.getPositionName());
//        String EntryTime = TimeUtils.getDateToString2(transferBills.getEntryDate());
        String entryDate = transferBills.getEntryDate();
        if (!TextUtils.isEmpty(entryDate)) {
            String substring = entryDate.substring(0, 10);
            mTvLabel6.setText(substring);
        }

        mTvLabel7.setText(transferBills.getWorkDuration());
        mTvLabel8.setText(transferBills.getTransferReason());     //离职原因
        mTvLabel9.setText(transferBills.getTransferDepName());
        mTvLabel10.setText(transferBills.getTransferPositionName());
        String transferDate = transferBills.getTransferDate();
        if (!TextUtils.isEmpty(transferDate)) {
            String substring = transferDate.substring(0, 10);
            mTvLabel11.setText(substring);
        }


        //审批状态
        switch (transferBills.getApproveStatus()) {
            case "1":
                mIvStatusbg.setVisibility(View.GONE);
                mTvCondition.setVisibility(View.VISIBLE);
                mLlItem0.setVisibility(View.GONE);
                break;
            case "2":
                mIvStatusbg.setVisibility(View.VISIBLE);
                mIvStatusbg.setImageResource(R.drawable.shen_pi_tong_guo);
                mTvCondition.setVisibility(View.GONE);
                mLlItem0.setVisibility(View.VISIBLE);
                mTvItem0.setText(transferBills.getBillId());
                break;
            case "3":
                mIvStatusbg.setVisibility(View.VISIBLE);
                mIvStatusbg.setImageResource(R.drawable.shen_pi_ju_jue);
                mTvCondition.setVisibility(View.GONE);
                mLlItem0.setVisibility(View.VISIBLE);
                mTvItem0.setText(transferBills.getBillId());
                break;
            default:
                break;
        }

    }


    @OnClick({R.id.head_ivBack, R.id.btAgree, R.id.btRefuse, R.id.btRevise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.btAgree:  //同意
                approveStatus = "2";
                auditDocument();
                break;
            case R.id.btRefuse: //拒绝
                Intent intent = new Intent(this, SuggestionActivity.class);
                startActivityForResult(intent, 10);
                approveStatus = "3";

                break;
            case R.id.btRevise: //修改日期
                pvTime.show();
                break;
            default:
                break;
        }
    }


    /**
     * 单据审核
     * Id	String		是
     * approveStatus	String		是	审批状态 1、是同意  2、是审批
     * billType	String		是	单据类型:0 全部，1请假单，2,入职单，3,离职单，4调岗单，5转正单
     * employeeId	String		是	员工Id
     * approveDesc	String		是	审批意见
     * approve	String		是	审批人
     */
    private void auditDocument() {
        String billType = "4";
        String approve = SPUtils.getUserDataBean(this).getId();
        OperatingFloorRequest.auditDocument(id, approveStatus, billType, employeeId, approveDesc, approve, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                try {
                    Log.i(TAG, "审核通过-->" + response.get().toString() + "");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (resultBean.getCode().equals("0")) {
                        ToastUtils.showToCenters(TransferPDetailActivity.this, "操作成功!", 1000);
                        Intent intent = new Intent();
                        setResult(4, intent);
                        finish();
                    } else {
                        ToastUtils.showToCenters(TransferPDetailActivity.this, "操作失败，请稍后重试!", 1000);
                    }

                } catch (Exception e) {
                    ToastUtils.showToCenters(TransferPDetailActivity.this, "操作失败，请稍后重试!", 1000);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                ToastUtils.showToCenters(TransferPDetailActivity.this, "操作失败，请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int i) {

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
                    mTvLabel11.setText(datetime);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            approveDesc = data.getStringExtra("Account");
            auditDocument();
        }
    }

}
