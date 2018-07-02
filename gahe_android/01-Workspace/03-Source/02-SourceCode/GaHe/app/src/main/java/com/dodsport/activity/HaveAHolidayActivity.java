package com.dodsport.activity;

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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.askforleave.BillsDetailActivity;
import com.dodsport.activity.askforleave.HistoryRecordActivity;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.dodsport.weight.popupwindow.AskForLeaveTypePopupWindow;
import com.dodsport.weight.popupwindow.TimeSelectPopupWindow;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.compareTwoTime;
import static com.dodsport.weight.TimeUtils.parseDate;


/**
 * 休假Activity
 */
public class HaveAHolidayActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvTime)
    TextView mTvTime;
    @Bind(R.id.tvEndTime)
    TextView mTvEndTime;
    @Bind(R.id.tvTitle)
    TextView mTvTitle;
    @Bind(R.id.evCause)
    EditText mEvCause;
    @Bind(R.id.btHaveSubmitTo)
    Button mBtHaveSubmitTo;
    @Bind(R.id.tvLoadOnTime)
    TextView mTvLoadOnTime;
    @Bind(R.id.tvCause_ets)
    TextView mTvCauseEts;
    @Bind(R.id.btApprove)
    Button mBtApprove;
    @Bind(R.id.llType)
    LinearLayout mLlType;
    @Bind(R.id.tvType)
    TextView mTvType;
    @Bind(R.id.tvTypeSelect)
    TextView mTvTypeSelect;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;

    private TimePickerView pvTime;
    private String TAG = " *****";
    private String Label = "";
    private String startYear = "";
    private String endYear = "";
    private boolean HaveSubmitTo = false;
    private EventBus mEventBus;
    private AskForLeaveTypePopupWindow askpopupWindow;
    private TimeSelectPopupWindow TimePopupWindow;
    private int position = 999;
    private UserDataBean.DatasBean.BusiEmployeeBean mUserDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_a_holiday);
        ButterKnife.bind(this);
        mEventBus.getDefault().register(this); //注册EventBus
        initView();
        mUserDataBean = SPUtils.getUserDataBean(this);
        //调试接口


        //会员卡
//        testMembership();


//
//        /**
//         *  获取调岗单详情数据信息
//         * token 用户唯一标识
//         * id 单据Id
//         * */
//        String id3 = "5660f5dc-f5b7-4d49-8503-2921be6b7209";
//        OperatingFloorRequest.getTransferBillDetail(id3, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "获取调岗单据成功--->"+response.get().toString());
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "获取调岗单据失败--->"+response.toString());
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });

    }



    private void testHttpRequest(){

        //提交转正申请单

//        /**
//         *  提交转正单
//         *
//         * token 用户唯一标识
//         * businessId 商家id
//         * storeId 门点Id
//         * employeeId   员工Id
//         * positionDesc  试用期岗位理解
//         * workDesc  使用期工作总结
//         * suggestion   意见或建议
//         * approver  审批人
//         * */
//
//        String businessId1 = mUserDataBean.getBusinessId();
//        String storeId = mUserDataBean.getStoreId();
//        String employeeId = mUserDataBean.getId();
//        String positionDesc = "测试提交转正单接口";
//        String workDesc = "测试提交转正单接口 总结啥";
//        String suggestion  = "测试提交转正单接口 找意见";
//        String approver = "43b117b0-7c15-4145-9ff9-e8141aea7073";
//        Log.i(TAG, "转正单传参---->  businessId1-->"+businessId1+"\tstoreId-->"+storeId+"\temployeeId-->"+employeeId+"\tpositionDesc-->"+positionDesc+
//                "\tworkDesc-->"+workDesc+"\tsuggestion-->"+suggestion+"\tapprover-->"+approver+"\t");
//
//        OperatingFloorRequest.addRegularEmpInfo(businessId1, storeId, employeeId, positionDesc, workDesc, suggestion, approver, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "提交转正单成功--->"+response.get().toString());
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "提交转正单失败--->"+response.toString());
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });

//
//        //获取转正单详情
//        String id = SPUtils.getUserDataBean(getActivity().getApplicationContext()).getId();
//        OperatingFloorRequest.getRegularBillDetail(id, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "获取转正单详情成功--->"+response.get().toString());
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "获取转正单详情失败--->"+response.toString());
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });
//

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
        UserDataBean.DatasBean.BusiEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
        String businessId2 = userDataBean.getBusinessId();
        String storeId2 = userDataBean.getStoreId();
        String employeeId2 = userDataBean.getId();
        String leaveDate = "2017-08-15";
        String handler = "5660f5dc-f5b7-4d49-8503-2921be6b7209";
        String leaveReason = "测试提交离职单据";
        String handleItem = "查看返回数据";
        String approver2 = "43b117b0-7c15-4145-9ff9-e8141aea7073";

        Log.i(TAG, "提交离职单传参---->  businessId1-->"+businessId2+"\tstoreId-->"+storeId2+"\temployeeId-->"+employeeId2+"\tleaveDate-->"+leaveDate+
                "\thandler-->"+handler+"\tleaveReason-->"+leaveReason+"\thandleItem-->"+handleItem+"\tapprover2-->"+approver2+"\t");

        OperatingFloorRequest.addLeaveEmpInfo(businessId2, storeId2, employeeId2, leaveDate, handler, leaveReason, handleItem, approver2, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "提交离职单据成功--->"+response.get().toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "提交离职单据失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });

//
//        /**
//         *  获取离职单详情数据信息
//         * token 用户唯一标识
//         * id 单据Id
//         * */
//        String id2 = "";
//        OperatingFloorRequest.getLeaveBillDetail(id2, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "获取离职单据成功--->"+response.get().toString());
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "获取离职单据失败--->"+response.toString());
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });


//        /**
//         * 提交调岗单据数据
//         *
//         * businessId 商家ID
//         * storeId 门点ID
//         * employeeId 员工ID
//         * transferReason 调岗原因
//         * transferDate 调岗日期
//         * transferPositionId 调岗职位
//         * transferDepId 调岗部门
//         * approver 审批人ID
//         * */
//        UserDataBean userDataBean = SPUtils.getUserDataBean(this);
//        String businessId3 = userDataBean.getBusinessId();
//        String storeId3 =  userDataBean.getStoreId();
//        String employeeId3 = userDataBean.getId();
//        String transferReason = "测试调岗单提交";
//        String transferDate = "2017-09-01";
//        String transferPositionId = "b9b197c6-d96e-426e-afa8-3693e4199838";
//        String transferDepId = "44278f04-ab13-4c83-a7b8-c68c15122caf";
//        String approver3 = "43b117b0-7c15-4145-9ff9-e8141aea7073";
//
//        Log.i(TAG, "提交调岗单传参---->  businessId1-->"+businessId3+"\tstoreId-->"+storeId3+"\temployeeId-->"+employeeId3+"\ttransferReason-->"+transferReason+
//                "\ttransferDate-->"+transferDate+"\ttransferPositionId-->"+transferPositionId+"\ttransferDepId-->"+transferDepId+"\tapprover3-->"+approver3+"\t");
//
//
//        OperatingFloorRequest.addTransferEmpInfo(businessId3, storeId3, employeeId3, transferReason, transferDate, transferPositionId, transferDepId, approver3, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "提交调岗单据成功--->"+response.get().toString());
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "提交调岗单据失败--->"+response.toString());
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });


        //新增员工
//        String employeeName = "李四";
//        String phoneNum = "15102740760";
//        String positionId = "b9b197c6-d96e-426e-afa8-3693e4199838";
//        Integer empRela = 2;
//        Integer sex = 2;
//        String businessId = mUserDataBean.getBusinessId();
//        String creator = "43b117b0-7c15-4145-9ff9-e8141aea7073";
//
//        OperatingFloorRequest.addEmployeeInfo(employeeName, phoneNum, positionId, empRela, sex, businessId, creator, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "新增员工信息成功--->"+response.toString());
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "新增员工信息失败--->"+response.toString());
//            }
//
//            @Override
//            public void onFinish(int what) {
//            }
//        });



    }


    private void  testMembership(){

//        /**
//         *  添加会员卡类型
//         * membcardTypeName 会员卡类别名称
//         * creator 添加者的ID
//         * */
//        String name = "次卡";
//        String id = "43b117b0-7c15-4145-9ff9-e8141aea7073";
//        String membcardTypeId = "2";
//        Log.i(TAG, "添加会员卡类别请求参数--->"+"\tmembcardTypeName-->"+name+"\tcreator-->"+id+"\t");
//        OperatingFloorRequest.addMemberCardType(name, id,membcardTypeId ,new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "添加会员卡类别成功返回-->"+response.toString()+"");
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "添加会员卡类别失败返回-->"+response.toString()+"");
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });



        /**
         *  获取会员卡类型列表
         * Token 用户通行口令
         * */
//        OperatingFloorRequest.getListMemberCardType(new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "获取会员卡类列表别成功返回-->"+response.toString()+"");
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "获取会员卡类列表别失败返回-->"+response.toString()+"");
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });



//        /**
//         *  获取会员卡类型数据
//         * Token 用户通行口令
//         * Id 会员卡类型ID
//         * */
//        String id = "6840ccda-09c3-48fa-89fb-a17769dc15ae";
//
//        OperatingFloorRequest.getMemberCardType(id, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Log.i(TAG, "获取会员卡类数据别成功返回-->"+response.toString()+"");
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                Log.i(TAG, "获取会员卡类数据别失败返回-->"+response.toString()+"");
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });


        /**
         *  添加会员卡数据
         * Token 用户通行口令
         * membcardName 会员卡名称
         * membcardTypeId 会员卡类型ID
         * remark   简介（卡介绍）
         * creator  添加人ID
         * */
        String membcardName ="次卡";
        String membcardTypeId = "6840ccda-09c3-48fa-89fb-a17769dc15ae";
        String remark = "测试添加会员卡";
        String creator = mUserDataBean.getId();
        OperatingFloorRequest.addMemberCard("",membcardName, membcardTypeId, remark, creator, "",new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "添加会员卡数据成功返回-->"+response.get().toString()+"");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "添加会员卡数据失败返回-->"+response.toString()+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });

        /**获取会员卡列表*/
        OperatingFloorRequest.getListMemberCard("",new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取会员卡列表成功返回-->"+response.get().toString()+"");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取会员卡列表失败返回-->"+response.get().toString()+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 测试接口数据
     * */
    public void getHttp(){

        //新增员工
        String employeeName = "李四";
        String phoneNum = "15102740760";
        String positionId = "1";
        Integer empRela = 1;
        Integer sex = 2;
        String businessId = mUserDataBean.getBusinessId();
        String creator = "";




        //获取用户信息
        OperatingFloorRequest.getEmployeeInfo(mUserDataBean.getPhoneNum(), new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取员工信息成功--->"+response.get().toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取员工信息失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });


        //提交转正申请单

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

        String businessId1 = mUserDataBean.getBusinessId();
        String storeId = mUserDataBean.getStoreId();
        String employeeId = mUserDataBean.getEmployeeSerialId();
        String positionDesc = "";
        String workDesc = "";
        String suggestion  = "";
        String approver = "";

        OperatingFloorRequest.addRegularEmpInfo(businessId1, storeId, employeeId, positionDesc, workDesc, suggestion, approver, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "提交转正单成功--->"+response.get().toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "提交转正单失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });


        //获取转正单详情
        String id = "";
        OperatingFloorRequest.getRegularBillDetail(id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取转正单详情成功--->"+response.get().toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取转正单详情失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });



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
        UserDataBean.DatasBean.BusiEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
        String businessId2 = userDataBean.getBusinessId();
        String storeId2 = userDataBean.getStoreId();
        String employeeId2 = userDataBean.getId();
        String leaveDate = "2017-08-15";
        String handler = "5660f5dc-f5b7-4d49-8503-2921be6b7209";
        String leaveReason = "测试提交离职单据";
        String handleItem = "查看返回数据";
        String approver2 = "43b117b0-7c15-4145-9ff9-e8141aea7073";

        OperatingFloorRequest.addLeaveEmpInfo(businessId2, storeId2, employeeId2, leaveDate, handler, leaveReason, handleItem, approver2, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "提交离职单据成功--->"+response.get().toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "提交离职单据失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });


        /**
         *  获取离职单详情数据信息
         * token 用户唯一标识
         * id 单据Id
         * */
        String id2 = "";
        OperatingFloorRequest.getLeaveBillDetail(id2, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取离职单据成功--->"+response.get().toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取离职单据失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });




        /**
         * 提交调岗单据数据
         *
         * businessId 商家ID
         * storeId 门点ID
         * employeeId 员工ID
         * transferReason 调岗原因
         * transferDate 调岗日期
         * transferPositionId 调岗职位
         * transferDepId 调岗部门
         * approver 审批人ID
         * */

        String businessId3 = "";
        String storeId3 = "";
        String employeeId3 = "";
        String transferReason = "";
        String transferDate = "";
        String transferPositionId = "";
        String transferDepId = "";
        String approver3 = "";
        OperatingFloorRequest.addTransferEmpInfo(businessId3, storeId3, employeeId3, transferReason, transferDate, transferPositionId, transferDepId, approver3, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "提交调岗单据成功--->"+response.get().toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "提交调岗单据失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });






    }







    private void initView() {

        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvOK.setText("历史记录");
        mLlType.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("请假");
        mTvTitle.setText("请假事由");
        mTvType.setText("请假类型");
        mEvCause.setHint("请输入请假事由（必填）");
        mBtApprove.setText(" + ");//审批人
        mBtApprove.setTextColor(getResources().getColor(R.color.fcfcfc));



        //addTime();
        getSignature();
    }


    @OnClick({R.id.head_ivBack, R.id.tvTime, R.id.tvEndTime, R.id.btHaveSubmitTo, R.id.tvTypeSelect,R.id.head_tvOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_tvOK:        //历史记录
                Intent hahIntent = new Intent(this,HistoryRecordActivity.class);
                hahIntent.putExtra("key","Ha");
                hahIntent.putExtra("type","1");
                startActivity(hahIntent);
                break;
            case R.id.tvTime:           //开始时间
                String start = "start";
                if (TimePopupWindow!=null){
                    TimePopupWindow.dismiss();
                }
                TimePopupWindow = new TimeSelectPopupWindow(this, mTvTime,start,mTvEndTime,mTvLoadOnTime);
                TimePopupWindow.showAtLocation(mTvEndTime, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tvEndTime:        //结束时间
                if (mTvTime.getText().toString().equals("请选择(必填)")){
                    ToastUtils.showToCenters(this, "请先选择开始时间!", 1000);
                    return;
                }
                String end = "end";
                if (TimePopupWindow!=null){
                    TimePopupWindow.dismiss();
                }
                TimePopupWindow = new TimeSelectPopupWindow(this, mTvEndTime,end,mTvTime,mTvLoadOnTime);
                TimePopupWindow.showAtLocation(mTvEndTime, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btHaveSubmitTo:   //提交
                try {
                    uploadAskForLeave();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "提交请假单捕获到的异常-->"+e.toString());
                }

                break;
            case R.id.tvTypeSelect:     //选择请假类型
                askpopupWindow = new AskForLeaveTypePopupWindow(this, mTvTypeSelect);
                askpopupWindow.showAtLocation(mTvEndTime, Gravity.TOP, 0, 300);
                break;
        }
    }

    private void uploadAskForLeave() throws ParseException {
        if (position > 9){
            ToastUtils.showToCenters(this,"请选择请假类型！",800);
            return;
        }
        Integer vacationType = position+1;
        String startTime = mTvTime.getText().toString();
        String endTime = mTvEndTime.getText().toString();
        final String durations = mTvLoadOnTime.getText().toString();
        if (mTvTime.getText().equals("请选择(必填)") || mTvEndTime.getText().equals("请选择(必填)") || durations.equals("自动加载时长")){
            ToastUtils.showToCenters(this,"请假时间不能为空！",800);
            return;
        }

        final float duration = Float.parseFloat(durations);
        final String description = mEvCause.getText().toString();
        if (TextUtils.isEmpty(description)){
            ToastUtils.showToCenters(this,"请输入请假事由!",1000);
            return;
        }

        final UserDataBean.DatasBean.BusiEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
        String businessId = userDataBean.getBusinessId();
        String storeId = userDataBean.getStoreId();
        String employeeId = userDataBean.getId();       //员工Id
        String startDay = startTime.substring(11, 13);
        String endTimes = endTime.substring(11, 13);
        startTime = startTime.substring(0, 10);
        endTime = endTime.substring(0, 10);
        String approver = employeeId;

        Log.i(TAG, "提交请假单--->"+employeeId+"");
        OperatingFloorRequest.uploadAskForLeave(businessId, storeId,vacationType, employeeId, startTime, endTime
                ,duration, description, approver,startDay,endTimes,new OnResponseListener<String>() {

                    @Override
                    public void onStart(int what) {

                    }

                    @Override
            public void onSucceed(int what, Response<String> response) {
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                Log.i(TAG, "提交请假单--->"+response.toString()+"");
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")){
                    switch (resultBean.getCode()){
                        case "":
                            break;
                    }
                    return;
                }

                Intent intent = new Intent(HaveAHolidayActivity.this, BillsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("startTime",mTvTime.getText().toString());    //请假开始时间
                bundle.putString("endTime",mTvEndTime.getText().toString());        //请假结束时间
                bundle.putFloat("duration",duration);       //请假天数
                bundle.putString("description",description); //请假事由
                bundle.putString("typeslect",mTvTypeSelect.getText().toString());       //请假类型
                intent.putExtra("have",bundle);
                intent.putExtra("key","Ha");
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "提交请假单 失败返回--->"+response.toString()+"");
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
        if (event.getType().equals("ask")) {
            mTvTypeSelect.setText(event.getMsg() + "");
             position = event.getmPosition();
            if (askpopupWindow != null) {
                askpopupWindow.dismiss();
                askpopupWindow = null;
            }
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

                    if (Label.equals("Start") && !TextUtils.isEmpty(Label) && !TextUtils.isEmpty(datetime)) {
                        startYear = datetime;
                        mTvTime.setText(datetime + "");
                    } else if (Label.equals("End") && !TextUtils.isEmpty(Label) && !TextUtils.isEmpty(datetime)) {
                        endYear = datetime;
                        //String replace = datetime.replace("-", "");
                        // String mYear = replace.substring(4, 8);
                        // endTime = Integer.parseInt(mYear);
                        mTvEndTime.setText(datetime + "");
                    }

                    if (!TextUtils.isEmpty(startYear) && !TextUtils.isEmpty(endYear)) {
                        String time = compareTwoTime(startYear, endYear);
                        //String timeDifference = getTimeDifference(startYear, endYear);//获取相差小时
                        int i = 0;
                        if (!TextUtils.isEmpty(time)) {
                            i = Integer.parseInt(time);
                            if (i > 32) {
                                ToastUtils.showToCenters(HaveAHolidayActivity.this, "休假时长不能超过一个月!", 1000);
                                HaveSubmitTo = false;
                            } else {
                                mTvLoadOnTime.setText(i + " ");
                                HaveSubmitTo = true;
                            }
                        } else {
                            ToastUtils.showToCenters(HaveAHolidayActivity.this, "开始时间不能超过结束时间!", 1000);
                            HaveSubmitTo = false;
                        }


                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }



    /**
     * 编辑休假事由文字
     */
    public void getSignature() {

        mEvCause.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTvCauseEts.setText((200 - s.length()) + "/200");
                if (s.length() >= 200) {
                    ToastUtils.showToCenter(HaveAHolidayActivity.this, "您编辑的文字长度超过200!", 0);
                }

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.getDefault().unregister(this);//反注册EventBus
    }
}
