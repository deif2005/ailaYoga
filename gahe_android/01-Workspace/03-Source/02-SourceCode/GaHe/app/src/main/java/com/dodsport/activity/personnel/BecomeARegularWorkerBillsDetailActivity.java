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
import com.dodsport.model.RegularBillsBean;
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
 * 转正单详情
 *
 * @author Administrator
 */
public class BecomeARegularWorkerBillsDetailActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvSubmitName)
    TextView mTvSubmitName;
    @Bind(R.id.btAgree)
    Button mBtAgree;
    @Bind(R.id.btRefuse)
    Button mBtRefuse;
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
    @Bind(R.id.rlLayout)
    RelativeLayout mRlLayout;
    @Bind(R.id.llItem1)
    LinearLayout mLlItem1;
    @Bind(R.id.ivStatusbg)
    ImageView mIvStatusbg;
    @Bind(R.id.tvCondition)
    TextView mTvCondition;
    @Bind(R.id.tvItem0)
    TextView mTvItem0;
    @Bind(R.id.llItem0)
    LinearLayout mLlItem0;

    private String TAG = "****转正单--";
    private String id = ""; //单据id
    private String approveStatus = "0"; //审核状态
    private String employeeId = "";     //员工Id
    private String approveDesc = "";    //审批拒绝理由


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_aregular_worker_bills_detail);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("转正单");
        Intent intent = getIntent();
        String become = intent.getStringExtra("Become");//标识从哪个页面进来
        UserDataBean.DatasBean.ResponseEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
        if (become.equals("Become")) {      //提交转正单页面进来
            mTvSubmitName.setText(userDataBean.getEmployeeName());
            mTvItem1.setText(userDataBean.getEmployeeSerialId());
            String EntryDate = userDataBean.getEntryDate().substring(0, 10);
            mTvItem2.setText(EntryDate);
            mTvItemText3.setText("试用期岗\n位");
            mTvItem3.setText(userDataBean.getPositionName());
            mTvLabel2.setText(intent.getStringExtra("workDesc"));
            mTvLabel4.setText(intent.getStringExtra("positionDesc"));
            mTvLabel6.setText(intent.getStringExtra("suggestion"));
            employeeId = userDataBean.getId();
            mRlLayout.setVisibility(View.GONE);

            return;
        } else if (become.equals("BecomeAP")) {
            mRlLayout.setVisibility(View.VISIBLE);
            mIvStatusbg.setVisibility(View.GONE);
        } else {
            mIvStatusbg.setVisibility(View.VISIBLE);
            mRlLayout.setVisibility(View.GONE);
        }
        getNetData(intent);
    }


    /**
     * 获取网络数据
     */
    private void getNetData(Intent intent) {
        BIllDetailBean.BillsBean data = (BIllDetailBean.BillsBean) intent.getSerializableExtra("data");

        //获取转正单详情
        id = data.getId();
        Log.i(TAG, "单据ID-->" + data.toString() + "");
        OperatingFloorRequest.getRegularBillDetail(id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "获取详情成功--->" + response.get().toString());
                    RegularBillsBean regularBillsBean = JSON.parseObject(response.get(), RegularBillsBean.class);
                    if (!regularBillsBean.getResult().getCode().equals("0")) {
                        switch (regularBillsBean.getResult().getCode()) {
                            case "4003":
                                ToastUtils.showToCenters(BecomeARegularWorkerBillsDetailActivity.this, "获取是单据失败,请稍后重试!", 1000);
                                break;
                            default:
                                break;
                        }
                        return;
                    }

                    setBillsDeta(regularBillsBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取详情失败--->" + response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    public void setBillsDeta(RegularBillsBean billsDeta) {
        RegularBillsBean.DatasBean.RegularBills regularBills = billsDeta.getDatas().getRegularBills();
        mTvSubmitName.setText(billsDeta.getDatas().getRegularBills().getEmployeeName());
        mTvSubmitName.setText(regularBills.getEmployeeName());
        if (TextUtils.isEmpty(regularBills.getEmployeeSerialId())){
            mLlItem1.setVisibility(View.GONE);
        }else {
            mTvItem1.setText(regularBills.getEmployeeSerialId());//工号
            mLlItem1.setVisibility(View.VISIBLE);
        }
        String EntryDate = regularBills.getEntryDate().substring(0, 10);
        mTvItem2.setText(EntryDate);
        mTvItemText3.setText("试用期岗\n位");
        mTvItem3.setText(regularBills.getPositionName());
        mTvLabel2.setText(regularBills.getWorkDesc());//intent.getStringExtra("workDesc"));
        mTvLabel4.setText(regularBills.getPositionDesc());//intent.getStringExtra("positionDesc"));
        mTvLabel6.setText(regularBills.getSuggestion());//intent.getStringExtra("suggestion"));
        employeeId = billsDeta.getDatas().getRegularBills().getEmployeeId();

        //审批状态
        switch (regularBills.getApproveStatus()) {
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
                mTvItem0.setText(regularBills.getBillId());
                break;
            case "3":
                mIvStatusbg.setVisibility(View.VISIBLE);
                mIvStatusbg.setImageResource(R.drawable.shen_pi_ju_jue);
                mTvCondition.setVisibility(View.GONE);
                mLlItem0.setVisibility(View.VISIBLE);
                mTvItem0.setText(regularBills.getBillId());
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
            case R.id.btAgree:      //同意，审批意见1、未审批  2、同意  3、拒接
                approveStatus = "2";
                auditDocument();
                break;
            case R.id.btRefuse:     //拒绝
                Intent intent = new Intent(this, SuggestionActivity.class);
                startActivityForResult(intent, 10);
                approveStatus = "3";
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            approveDesc = data.getStringExtra("Account");
            auditDocument();
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
        final String billType = "5";
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
                        ToastUtils.showToCenters(BecomeARegularWorkerBillsDetailActivity.this, "操作成功!", 1000);
                        Intent intent = new Intent();
                        setResult(Integer.parseInt(billType), intent);
                        finish();
                    } else {
                        ToastUtils.showToCenters(BecomeARegularWorkerBillsDetailActivity.this, "操作失败，请稍后重试!", 1000);
                    }

                } catch (Exception e) {
                    ToastUtils.showToCenters(BecomeARegularWorkerBillsDetailActivity.this, "操作失败，请稍后重试!", 1000);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                ToastUtils.showToCenters(BecomeARegularWorkerBillsDetailActivity.this, "操作失败，请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }
}
