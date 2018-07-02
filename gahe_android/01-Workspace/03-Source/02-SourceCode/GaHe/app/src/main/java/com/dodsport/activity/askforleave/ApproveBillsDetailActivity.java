package com.dodsport.activity.askforleave;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.dodsport.model.BIllDetailBean;
import com.dodsport.model.BillsInfoBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.JsonUtils;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.R.id.tvCondition;

/**
 * 请假模块中的 请假单详情（审批模块中可见）
 */
public class ApproveBillsDetailActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvDepartment)
    TextView mTvDepartment;
    @Bind(R.id.tvEndTime)
    TextView mTvEndTime;
    @Bind(R.id.llSerialNumber)
    LinearLayout mLlSerialNumber;
    @Bind(R.id.llSerialNumber_view)
    View mLlSerialNumberView;
    @Bind(R.id.llHistory)
    LinearLayout mLlHistory;
    @Bind(R.id.llHistory_view)
    View mLlHistoryView;
    @Bind(R.id.rlApproveOperation)
    RelativeLayout mRlApproveOperation;
    @Bind(R.id.tvUserName)
    TextView mTvUserName;
    @Bind(tvCondition)
    TextView mTvCondition;
    @Bind(R.id.SerialNumber)
    TextView mSerialNumber;
    @Bind(R.id.tvSerialNumber)
    TextView mTvSerialNumber;
    @Bind(R.id.tvType)
    TextView mTvType;
    @Bind(R.id.tvStartTime)
    TextView mTvStartTime;
    @Bind(R.id.tvLoadOnTime)
    TextView mTvLoadOnTime;
    @Bind(R.id.tvReason)
    TextView mTvReason;
    @Bind(R.id.ivStatusbg)
    ImageView mIvStatusbg;
    @Bind(R.id.tvUserJod)
    TextView mTvUserJod;
    @Bind(R.id.llUserJod)
    LinearLayout mLlUserJod;
    @Bind(R.id.llEndTime)
    LinearLayout mLlEndTime;
    @Bind(R.id.tvHistory)
    TextView mTvHistory;
    @Bind(R.id.btAgree)
    Button mBtAgree;
    @Bind(R.id.btRefuse)
    Button mBtRefuse;

    private String employeeName;
    private String depName;
    private String employeeId;
    private String TAG = "****单据详情";
    private BillsInfoBean mBillsInfoBean;
    private Handler mHandler;
    private  BIllDetailBean.BillsBean billsBean;
    String bill = "null";




    private class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:     //加载 从历史记录页面进来的网络数据
                    try {
                        LoadOn();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_detail);
        ButterKnife.bind(this);
//        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);

        mLlSerialNumberView.setVisibility(View.GONE);
        mLlHistory.setVisibility(View.GONE);
        mLlHistoryView.setVisibility(View.GONE);
        mRlApproveOperation.setVisibility(View.GONE);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        employeeName = SPUtils.getUserDataBean(this).getEmployeeName();
        depName = SPUtils.getUserDataBean(this).getDepName();
        employeeId = SPUtils.getUserDataBean(this).getEmployeeSerialId();
         if (key.equals("AP")) {  //从审核模块进来
            String stringExtra = intent.getStringExtra("ap");//表示从审核模块的 待审核页面进来，还是已审核页面进来
            if(stringExtra.equals("ApproveFragment")){    //待审核
                mLlHistory.setVisibility(View.VISIBLE);
                mLlHistoryView.setVisibility(View.VISIBLE);
                mLlSerialNumberView.setVisibility(View.VISIBLE);
                mRlApproveOperation.setVisibility(View.VISIBLE);
            }else if (stringExtra.equals("ApproveOKFragment")){   //已审核
                mLlHistory.setVisibility(View.GONE);
                mLlHistoryView.setVisibility(View.GONE);
                mLlSerialNumberView.setVisibility(View.GONE);
                mRlApproveOperation.setVisibility(View.GONE);
            }
            getNetData();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();

    }


    @OnClick({R.id.tvHistory, R.id.btAgree, R.id.btRefuse, R.id.head_ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.tvHistory:    //查看单据历史记录
                bill = "bill";
                Intent intent1 = new Intent(this,HistoryRecordActivity.class);
                intent1.putExtra("key","APF");
                intent1.putExtra("bullkey",bill);
                intent1.putExtra("data",billsBean);
                startActivity(intent1);
                break;
            case R.id.btAgree:      //审批同意

                break;
            case R.id.btRefuse:     //审批拒绝

                break;
        }
    }


    /**
     * 加载网络数据
     */
    private void LoadOn() {
        mTvUserName.setText(mBillsInfoBean.getEmployeeName());
        //部门
        mTvDepartment.setText(mBillsInfoBean.getDepName() + "");
        //单据编号
        mTvSerialNumber.setText(mBillsInfoBean.getBillId());
        //开始时间
        String substring = mBillsInfoBean.getStartTime().substring(0, 10);
        substring = substring + mBillsInfoBean.getStartDay();
        mTvStartTime.setText(substring + "");
        //工号
        mTvUserJod.setText(mBillsInfoBean.getEmployeeSerialId());
        //请假时长
        mTvLoadOnTime.setText(mBillsInfoBean.getDuration() + "");
        //请假类型
        String billType = mBillsInfoBean.getVacationType();
        if (!TextUtils.isEmpty(billType)) {
            int i = Integer.parseInt(billType);
            mTvType.setText(UrlInterfaceManager.type[i]);
        }
        //请假事由
        mTvReason.setText(mBillsInfoBean.getDescription());
        //审批状态
        switch (mBillsInfoBean.getApproveStatus()) {
            case "1":
                mIvStatusbg.setVisibility(View.GONE);
                mTvCondition.setVisibility(View.VISIBLE);
                break;
            case "2":
                mIvStatusbg.setVisibility(View.VISIBLE);
                mIvStatusbg.setImageResource(R.drawable.shen_pi_tong_guo);
                mTvCondition.setVisibility(View.GONE);
                break;
            case "3":
                mIvStatusbg.setVisibility(View.VISIBLE);
                mIvStatusbg.setImageResource(R.drawable.shen_pi_ju_jue);
                mTvCondition.setVisibility(View.GONE);
                break;
        }

        //页面复用 把不是当前页面的控件隐藏
        mLlSerialNumber.setVisibility(View.VISIBLE);
        mLlUserJod.setVisibility(View.VISIBLE);
        mLlEndTime.setVisibility(View.GONE);
    }

    /**
     * 获取网络数据
     */
    public void getNetData() {
        Intent intent = getIntent();
        billsBean  = (BIllDetailBean.BillsBean) intent.getSerializableExtra("data");
        if (billsBean==null){
            return;
        }


        String id = billsBean.getId();
        String type = billsBean.getBillType();
        if (!TextUtils.isEmpty(type) && !type.equals("0")) {
            int i = Integer.parseInt(type);
            mHeadTvTitle.setText(UrlInterfaceManager.type[i] + "单");
        }
        mHandler = new mHandler();
        OperatingFloorRequest.getBillsDetail(id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "--成功 " + response.get().toString() + "");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")) {
                        switch (resultBean.getCode()) {
                            case "":

                                break;
                        }
                        return;
                    }
                    JSONObject json = new JSONObject(statusBean.getDatas());
                    JSONObject vacationBills = json.getJSONObject("VacationBills");
                    mBillsInfoBean = JsonUtils.fromJsonObjectToObject(vacationBills, BillsInfoBean.class);
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "--失败 " + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }
}
