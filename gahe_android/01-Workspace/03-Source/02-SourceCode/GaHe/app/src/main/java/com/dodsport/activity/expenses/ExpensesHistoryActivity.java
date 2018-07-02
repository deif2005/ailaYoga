package com.dodsport.activity.expenses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.dodsport.GaHeApplication;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.askforleave.SuggestionActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.MyGridView;
import com.dodsport.adapter.CommonAdapter;
import com.dodsport.adapter.ViewHolder;
import com.dodsport.model.BIllDetailBean;
import com.dodsport.model.ExpensesBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.TransformationUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 报销单详情
 */
public class ExpensesHistoryActivity extends BaseActivity {

    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.ivApproveBearFruit)
    ImageView mIvApproveBearFruit;
    @Bind(R.id.Bills_item_gridView)
    MyGridView mBillsItemGridView;
    @Bind(R.id.tvTiemText1)
    TextView mTvTiemText1;
    @Bind(R.id.tvTiem1)
    TextView mTvTiem1;
    @Bind(R.id.tvTiemText2)
    TextView mTvTiemText2;
    @Bind(R.id.tvTiem2)
    TextView mTvTiem2;
    @Bind(R.id.tvTiemText3)
    TextView mTvTiemText3;
    @Bind(R.id.tvTiem3)
    TextView mTvTiem3;
    @Bind(R.id.tvUse)
    TextView mTvUse;
    @Bind(R.id.tvTiemText4)
    TextView mTvTiemText4;
    @Bind(R.id.tvTiem4)
    TextView mTvTiem4;
    @Bind(R.id.llExpense)
    LinearLayout mLlExpense;
    @Bind(R.id.tvHistory)
    TextView mTvHistory;
    @Bind(R.id.llHistory)
    LinearLayout mLlHistory;
    @Bind(R.id.tvSubmitName)
    TextView mTvSubmitName;
    @Bind(R.id.rlApproveOperation)
    RelativeLayout mRlApproveOperation;
    @Bind(R.id.llBillsNumber)
    LinearLayout mLlBillsNumber;
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.btAgree)
    Button mBtAgree;
    @Bind(R.id.btRefuse)
    Button mBtRefuse;
    private CommonAdapter<String> mCommonAdapter;
    private Context mContext;

    private List<String> data = new ArrayList<>();
    private UserDataBean.DatasBean.ResponseEmployeeBean mUserDataBean;
    private String TAG = "****报销单据详情--";
    private String approveStatus = "0"; //审核状态
    private String employeeId = "";     //员工Id
    private String approveDesc = "1";    //审批拒绝理由
    private String id = "";//单据id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_history);
        mContext = this;
        ButterKnife.bind(this);
        mUserDataBean = SPUtils.getUserDataBean(this);
        initView();
    }


    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("报销单详情");
        Intent intent = getIntent();
        String st = intent.getStringExtra("ST");
        if (st.equals("st")) {//提交报销单后进来
            mTvSubmitName.setText(mUserDataBean.getEmployeeName());
            mLlHistory.setVisibility(View.GONE);
            mRlApproveOperation.setVisibility(View.GONE);
            mLlBillsNumber.setVisibility(View.GONE);
            mTvTiem2.setText(mUserDataBean.getEmployeeSerialId());
            mTvTiem3.setText(mUserDataBean.getDepName());
            mTvUse.setText(intent.getStringExtra("description"));
            mTvTiem4.setText(intent.getStringExtra("account"));
            ExpensesBean expensesBean = (ExpensesBean) intent.getSerializableExtra("ExpensesBean");
            if (data.size() != 0) {
                data.clear();
            }
            data.addAll(expensesBean.getDatas().getShowPathList());
            showImage();
            return;
        } else if (st.equals("ExHi")) {   //历史记录进来
            mHeadTvTitle.setText("历史记录详情");

        } else if (st.equals("ExHiAP")) {     //从审批模块进来
            mHeadTvTitle.setText("报销单详情");
            mRlApproveOperation.setVisibility(View.VISIBLE);
        }else {

            mRlApproveOperation.setVisibility(View.GONE);
            mIvApproveBearFruit.setVisibility(View.VISIBLE);
        }
        loginData();
    }



    /**
     * 加载数据
     */
    private void loginData() {
        Intent intent = getIntent();
        BIllDetailBean data = (BIllDetailBean) intent.getSerializableExtra("data");
        Log.i(TAG, "数据---> " + data.toString() + "");


        //审批状态
       /* switch (mBillsInfoBean.getApproveStatus()) {
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
            default:
                break;*/

    }

    /**
     * 显示图片
     */
    private void showImage() {
        if (data.size() == 1) {
            final int width1 = GaHeApplication.Width;
            mBillsItemGridView.setNumColumns(1);
            mCommonAdapter = new CommonAdapter<String>(this, data, R.layout.expense_image) {
                @Override
                public void convert(ViewHolder holder, String s, int position) {
                    ImageView imageView = holder.getView(R.id.imageView);
                    try {
                        Picasso.with(mContext).load(data.get(position))
                                .resize(400, 400)
                                .config(Bitmap.Config.RGB_565)
                                .error(R.drawable.live_continue)
                                .placeholder(R.drawable.live_continue)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .centerCrop()
                                .transform(TransformationUtils.zipImage(imageView)).into(imageView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width1, width1);
                    imageView.setLayoutParams(params);

                }
            };

        } else if (data.size() == 2 || data.size() == 4) {
            final int width = GaHeApplication.Width - 4;
            mBillsItemGridView.setNumColumns(2);

            mCommonAdapter = new CommonAdapter<String>(this, data, R.layout.expense_image) {
                @Override
                public void convert(ViewHolder holder, String s, int position) {
                    ImageView imageView = holder.getView(R.id.imageView);
                    try {
                        Picasso.with(mContext).load(data.get(position))
                                .resize(400, 400)
                                .config(Bitmap.Config.RGB_565)
                                .error(R.drawable.live_continue)
                                .placeholder(R.drawable.live_continue)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                                .transform(new CircleImageTransformation()) //圆形头像
                                .centerCrop()
                                .transform(TransformationUtils.zipImage(imageView)).into(imageView);

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width / 2, width / 2);
                        params.setMargins(2, 0, 0, 3);
                        imageView.setLayoutParams(params);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };


        } else if (data.size() == 3 || data.size() == 6 || data.size() == 9 || data.size() == 5 || data.size() == 7 || data.size() == 8) {
            final int width = GaHeApplication.Width - 9;
            mBillsItemGridView.setNumColumns(3);
            mCommonAdapter = new CommonAdapter<String>(this, data, R.layout.expense_image) {
                @Override
                public void convert(ViewHolder holder, String s, int position) {
                    ImageView imageView = holder.getView(R.id.imageView);
                    try {
                        if (!TextUtils.isEmpty(data.get(position))){
                            Picasso.with(mContext).load(data.get(position))
                                    .resize(400, 400)
                                    .config(Bitmap.Config.RGB_565)
//                                .error(R.drawable.live_continue)
//                                .placeholder(R.drawable.live_continue)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                    .centerCrop()
                                    .transform(TransformationUtils.zipImage(imageView)).into(imageView);
                        }

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width / 3, width / 3);
                        params.setMargins(2, 0, 0, 3);
                        imageView.setLayoutParams(params);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };

        }
        mBillsItemGridView.setAdapter(mCommonAdapter);
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
                startActivityForResult(intent,10);
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
     * */
    private void auditDocument(){
        final String billType = "2";
        String approve = SPUtils.getUserDataBean(this).getId();
        OperatingFloorRequest.auditDocument(id, approveStatus, billType, employeeId, approveDesc, approve, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                try {
                    Log.i(TAG, "审核通过-->"+response.get().toString()+"");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (resultBean.getCode().equals("0")){
                        ToastUtils.showToCenters(ExpensesHistoryActivity.this,"操作成功!",1000);
                        Intent intent = new Intent();
                        setResult(Integer.parseInt(billType),intent);
                        finish();
                    }else {
                        ToastUtils.showToCenters(ExpensesHistoryActivity.this,"操作失败，请稍后重试!",1000);
                    }

                } catch (Exception e) {
                    ToastUtils.showToCenters(ExpensesHistoryActivity.this,"操作失败，请稍后重试!",1000);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                ToastUtils.showToCenters(ExpensesHistoryActivity.this,"操作失败，请稍后重试!",1000);
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10){
            approveDesc = data.getStringExtra("Account");
            auditDocument();
        }
    }
}
