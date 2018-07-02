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
import com.dodsport.model.PositionBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
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

import static com.dodsport.weight.TimeUtils.parseDate;

/**
 * 转正单  --填写
 */
public class BecomeARegularWorkerBillsActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvEntryDate)
    TextView mTvEntryDate;
    @Bind(R.id.image1)
    ImageView mImage1;
    @Bind(R.id.tvPlaceText)
    TextView mTvPlaceText;
    @Bind(R.id.tvPlace)
    TextView mTvPlace;
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
    @Bind(R.id.tvUnderstand3)
    TextView mTvUnderstand3;
    @Bind(R.id.evUnderstand3)
    EditText mEvUnderstand3;
    @Bind(R.id.btApprove)
    Button mBtApprove;
    @Bind(R.id.llApprove)
    LinearLayout mLlApprove;
    @Bind(R.id.btSubmit)
    Button mBtSubmit;
    @Bind(R.id.llSubmit)
    LinearLayout mLlSubmit;

    private CompanyNamePopupWindow mPopupWindow;
    private TimePickerView pvTime;
    private List<PositionBean.DatasBean.PositionListBean> positionList;
    private String TAG = "****转正单--";
    private int position = 0;
    private static final int REQUEST_CAMERA_CODE = 10;
    private String id = "";//审批人ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_aregular_worker_bills);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//注册
        initView();

    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("转正单");
        mTvPlaceText.setText("试用期岗\n位");
        addTime();

    }

    @OnClick({R.id.head_ivBack, R.id.tvEntryDate, R.id.tvPlace, R.id.btApprove, R.id.btSubmit,R.id.llSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.tvEntryDate:  //入职日期
                pvTime.show();
                break;
            case R.id.tvPlace:  //试用期岗位
                getBusiPosition();
                break;
            case R.id.btApprove:    //选择审核人
                Intent intent = new Intent(this, EmployeeManageActivity.class);
                intent.putExtra("key","Have");
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
                break;
            case R.id.llSubmit:     //提交
            case R.id.btSubmit:
                getSubmit();
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
     * 提交转正单
     * */
    private void getSubmit(){
        /**
         *  提交转正单
         *
         * token 用户唯一标识
         * businessId 商家id
         * storeId 门点Id
         * employeeId   员工Id
         * positionDesc  试用期岗位理解
         * workDesc  使用期工作总结
         * suggestion   意见或建议
         * approver  审批人
         * */
        UserDataBean.DatasBean.ResponseEmployeeBean mUserDataBean = SPUtils.getUserDataBean(this);
        final String businessId1 = mUserDataBean.getBusinessId();
        final String storeId = mUserDataBean.getStoreId();
        String employeeId = mUserDataBean.getId();
        final String positionDesc = mEvUnderstand1.getText().toString();
        final String workDesc = mEvUnderstand2.getText().toString();
        final String suggestion  = mEvUnderstand3.getText().toString();
        String approver = id;

        OperatingFloorRequest.addRegularEmpInfo(businessId1, storeId, employeeId, positionDesc, workDesc, suggestion, approver, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "提交转正单成功--->"+response.toString());
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        switch (resultBean.getCode()){
                            case "4001":
                                break;
                            case "5047 ":
                                ToastUtils.showToCenters(BecomeARegularWorkerBillsActivity.this, "该单据已经存在，不过重复提交!!", 1000);
                                break;
                            default:
                                break;
                        }
                        return;
                    }
                    //
                    Intent intent = new Intent(BecomeARegularWorkerBillsActivity.this,BecomeARegularWorkerBillsDetailActivity.class);
                    intent.putExtra("Become","Become");
                    intent.putExtra("positionDesc",positionDesc);
                    intent.putExtra("workDesc",workDesc);
                    intent.putExtra("suggestion",suggestion);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "提交转正单失败--->"+response.toString());
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
//        Log.i(TAG, "商家ID" + businessId + "");
        String businessId = SPUtils.getUserDataBean(this).getBusinessId();
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
                            default:
                                break;
                        }
                        return;
                    }
                    positionList = positionBean.getDatas().getPositionList();
                    //Log.i(TAG, "职位集合" + positionList.toString() + "");
                    List<String> data = new ArrayList<String>();
                    for (int i = 0; i < positionList.size(); i++) {
                        data.add(positionList.get(i).getPositionName());
                    }
                    String BusiPosition = "worker";
                    mImage2.setImageResource(R.drawable.xiang_xia);
                    mPopupWindow = new CompanyNamePopupWindow(BecomeARegularWorkerBillsActivity.this, mImage2, mTvPlace, data,BusiPosition);
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


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(AskForLeaveTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        //获取用户选中请假类型
        if (event.getType().equals("worker")) {
            position = event.getmPosition();
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

                    long currentTimeMillis = System.currentTimeMillis();

                    long time = date.getTime();

                    if(time>currentTimeMillis)
                    {
                        ToastUtils.showToCenter(BecomeARegularWorkerBillsActivity.this,"不能大于当前时间",0);
                        return;
                    }
                    mTvEntryDate.setText(datetime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }
}
