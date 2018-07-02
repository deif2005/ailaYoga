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
import com.dodsport.model.LeaveBillsBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提交离职单
 *
 * @author Administrator
 */
public class DimissionBillsDetailActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvSubmitName)
    TextView mTvSubmitName;
    @Bind(R.id.tvItemText1)
    TextView mTvItemText1;
    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.tvItemText2)
    TextView mTvItemText2;
    @Bind(R.id.tvItem2)
    TextView mTvItem2;
    @Bind(R.id.tvItemText3)
    TextView mTvItemText3;
    @Bind(R.id.tvItem3)
    TextView mTvItem3;
    @Bind(R.id.tvItemText4)
    TextView mTvItemText4;
    @Bind(R.id.tvItem4)
    TextView mTvItem4;
    @Bind(R.id.tvItemText5)
    TextView mTvItemText5;
    @Bind(R.id.tvItem5)
    TextView mTvItem5;
    @Bind(R.id.tvItemText6)
    TextView mTvItemText6;
    @Bind(R.id.tvItem6)
    TextView mTvItem6;
    @Bind(R.id.tvItemText7)
    TextView mTvItemText7;
    @Bind(R.id.tvItem7)
    TextView mTvItem7;
    @Bind(R.id.btAgree)
    Button mBtAgree;
    @Bind(R.id.btRefuse)
    Button mBtRefuse;
    @Bind(R.id.rlApproveOperation)
    RelativeLayout mRlApproveOperation;
    @Bind(R.id.ivStatusbg)
    ImageView mIvStatusbg;
    @Bind(R.id.tvCondition)
    TextView mTvCondition;
    @Bind(R.id.tvItem0)
    TextView mTvItem0;
    @Bind(R.id.llItem0)
    LinearLayout mLlItem0;

    private UserDataBean.DatasBean.ResponseEmployeeBean mUserDataBean;
    private String TAG = "***离职单--";
    private String approveStatus = "0"; //审核状态
    private String employeeId = "";     //员工Id
    private String approveDesc = "1";    //审批拒绝理由
    private String id = "";//单据id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimission_bills_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mUserDataBean = SPUtils.getUserDataBean(this);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("离职单");
        Intent intent = getIntent();
        String dikey = intent.getStringExtra("Dikey");
        if (dikey.equals("Dimission")) {     //提交离职单页面进来
            mRlApproveOperation.setVisibility(View.GONE);
            mTvSubmitName.setText(mUserDataBean.getEmployeeName());
            mTvItem1.setText(mUserDataBean.getEmployeeSerialId());
            if (!TextUtils.isEmpty(mUserDataBean.getEntryDate())) {
                String EntryDate = mUserDataBean.getEntryDate().substring(0, 10);
                mTvItem2.setText(EntryDate);
            }
            mTvItem3.setText(intent.getStringExtra("DimissionDate").substring(0, 10));
            mTvItem4.setText(mUserDataBean.getDepName());
            mTvItem5.setText(intent.getStringExtra("ConnectName"));
            mTvItem6.setText(intent.getStringExtra("leaveReason"));
            mTvItem7.setText(intent.getStringExtra("handleItem"));

            return;
            //待审核页面进来
        } else if (dikey.equals("DimissionAP")) {
            mRlApproveOperation.setVisibility(View.VISIBLE);
            //已审核页面进来
        } else if ((dikey.equals("DimissionAPOK"))) {
            Log.i(TAG, "进来了--->");
            mIvStatusbg.setVisibility(View.VISIBLE);
            mRlApproveOperation.setVisibility(View.GONE);
        }

        getNetData(intent);
    }

    /**
     * 获取网络数据
     *
     * @param intent
     */
    private void getNetData(Intent intent) {
        BIllDetailBean.BillsBean data = (BIllDetailBean.BillsBean) intent.getSerializableExtra("data");
        /**
         *  获取离职单详情数据信息
         * token 用户唯一标识
         * id 单据Id
         * */
        String id = data.getId();
        Log.i(TAG, "单据ID" + id + "");
        OperatingFloorRequest.getLeaveBillDetail(id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "获取离职单据成功--->" + response.get().toString());
                    LeaveBillsBean leaveBillsBean = JSON.parseObject(response.get(), LeaveBillsBean.class);
                    if (!leaveBillsBean.getResult().getCode().equals("0")) {
                        switch (leaveBillsBean.getResult().getCode()) {
                            case "4003":
                                ToastUtils.showToCenters(DimissionBillsDetailActivity.this, "获取单据失败,请检查网络或稍后重试!", 1000);
                                break;

                            default:
                                break;
                        }
                        return;
                    }

                    setNetData(leaveBillsBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(DimissionBillsDetailActivity.this, "获取单据失败,请检查网络或稍后重试!", 1000);
                Log.i(TAG, "获取离职单据失败--->" + response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    private void setNetData(LeaveBillsBean leaveBillsBean) {
        LeaveBillsBean.DatasBean.LeaveBillBean leaveBill = leaveBillsBean.getDatas().getLeaveBill();
//        mTvSubmitName.setText(leaveBill.get());
        mTvItem1.setText(leaveBill.getEmpId());
        if (!TextUtils.isEmpty(leaveBill.getStartTime())) {
            String StartTime = leaveBill.getStartTime().substring(0, 10);
            mTvItem2.setText(StartTime);
        }
        if (!TextUtils.isEmpty(leaveBill.getEndTime())) {
            String EndTime = leaveBill.getEndTime().substring(0, 10);
            mTvItem3.setText(EndTime);
        }
        mTvItem4.setText(leaveBill.getPositionName());
        mTvItem5.setText(leaveBill.getHandler());
        mTvItem6.setText(leaveBill.getLeaveReason());
        mTvItem7.setText(leaveBill.getHandleItem());

        //审批状态
        switch (leaveBill.getApproveStatus()) {
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
                mTvItem0.setText(leaveBill.getBillId());
                break;
            case "3":
                mIvStatusbg.setVisibility(View.VISIBLE);
                mIvStatusbg.setImageResource(R.drawable.shen_pi_ju_jue);
                mTvCondition.setVisibility(View.GONE);
                mLlItem0.setVisibility(View.VISIBLE);
                mTvItem0.setText(leaveBill.getBillId());
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.head_ivBack, R.id.btAgree, R.id.btRefuse})
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
        final String billType = "3";
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
                        ToastUtils.showToCenters(DimissionBillsDetailActivity.this, "操作成功!", 1000);
                        Intent intent = new Intent();
                        setResult(Integer.parseInt(billType), intent);
                        finish();
                    } else {
                        ToastUtils.showToCenters(DimissionBillsDetailActivity.this, "操作失败，请稍后重试!", 1000);
                    }

                } catch (Exception e) {
                    ToastUtils.showToCenters(DimissionBillsDetailActivity.this, "操作失败，请稍后重试!", 1000);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                ToastUtils.showToCenters(DimissionBillsDetailActivity.this, "操作失败，请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int i) {

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
