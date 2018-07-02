package com.dodsport.fragment.operatingfloorfragment.Approve;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.approve.ApproveActivity;
import com.dodsport.activity.askforleave.ApproveBillsDetailActivity;
import com.dodsport.activity.expenses.ExpensesHistoryActivity;
import com.dodsport.activity.personnel.BecomeARegularWorkerBillsDetailActivity;
import com.dodsport.activity.personnel.DimissionBillsDetailActivity;
import com.dodsport.activity.personnel.TransferPDetailActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.BIllDetailBean;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.WrapContentLinearLayoutManager;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 已审批 页面碎片
 * @author Administrator
 */
public class ApproveOKFragment extends BaseFragment {

    @Bind(R.id.approveRecyclerView)
    RecyclerView mApproveRecyclerView;
    @Bind(R.id.follow_loadView)
    LoadingView mFollowLoadView;
    @Bind(R.id.wsrlyout)
    WaveSwipeRefreshLayout mWsrlyout;
    private View mView;
    private ApproveActivity activity;
    private int count = 0;

    private List<BIllDetailBean.BillsBean> data = new ArrayList<>();
    private CommonAdapter<BIllDetailBean.BillsBean> mCommonAdapter;
    private String TAG = "******";
    private boolean refurbish = false;
    private int CODE = 1;   //跳转页面返回码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_approve_ok, container, false);
        }
        ButterKnife.bind(this, mView);
        isPrepared = true;
        lazyLoad();
        return mView;
    }


    //接收数据
    public void addData(ApproveActivity activity, List<BIllDetailBean.BillsBean> data) {
        this.data = data;
        this.activity = activity;
        if (!refurbish) {
            initView();
        } else {
            if (mCommonAdapter != null)
                mCommonAdapter.notifyDataSetChanged();
        }

    }

    public void addThis(ApproveActivity activity){
        this.activity = activity;
    }

    //初始化
    private void initView() {
        refurbish = true;
        mCommonAdapter = new CommonAdapter<BIllDetailBean.BillsBean>(getActivity().getApplicationContext(), R.layout.approve_item, data) {
            @Override
            protected void convert(ViewHolder holder, BIllDetailBean.BillsBean billsBean, int position) {

                TextView AskForLeaveTitle = holder.getView(R.id.tvAskForLeaveTitle);
                TextView AskForLeaveTime = holder.getView(R.id.tvAskForLeaveTime);
                TextView tvAskForLeaveType = holder.getView(R.id.tvAskForLeaveType);
                TextView StartTime = holder.getView(R.id.tvStartTime);
                TextView AskForLeaveQuantity = holder.getView(R.id.tvAskForLeaveQuantity);
                LinearLayout llAskForLeaveQuantity = holder.getView(R.id.llAskForLeaveQuantity);
                TextView StatusText = holder.getView(R.id.tvStatus);
                LinearLayout Status = holder.getView(R.id.llStatus);
                LinearLayout AskForLeaveType = holder.getView(R.id.llAskForLeaveType);
                Status.setVisibility(View.VISIBLE);
                AskForLeaveType.setVisibility(View.VISIBLE);
                llAskForLeaveQuantity.setVisibility(View.VISIBLE);


                /**单据类型展示*/
                String billType = billsBean.getBillType();
                int i = 0;
                if (!TextUtils.isEmpty(billType)) {
                    i = Integer.parseInt(billType);
                }

                switch (i) {
                    case 1:     //请假单
                        String vacationType = billsBean.getVacationType();
                        int v = 0;
                        if (!TextUtils.isEmpty(vacationType)) {
                            v = Integer.parseInt(vacationType);
                        }
                        AskForLeaveTitle.setText(billsBean.getEmployeeName() + "的" + UrlInterfaceManager.billType[i]);
                        tvAskForLeaveType.setText("请假类型\t\t  " + UrlInterfaceManager.type[v]);
                        String createTime = billsBean.getCreateTime();
                        String substring = createTime.substring(0, 10);
                        AskForLeaveTime.setText(substring);
                        String substring2 = createTime.substring(0, 10);
                        if (!TextUtils.isEmpty(billsBean.getStartDay())) {
                            StartTime.setText("开始时间\t\t\t" + substring2 + "、" + billsBean.getStartDay() + "");
                        } else {
                            StartTime.setText("开始时间\t\t\t" + substring2 + " " + billsBean.getStartDay() + "");
                        }
                        String sb = billsBean.getDuration().substring(billsBean.getDuration().lastIndexOf(".") + 1);
                        if (sb.equals("0")){
                            String s = billsBean.getDuration().substring(0, billsBean.getDuration().indexOf("."));
                            AskForLeaveQuantity.setText("请假天数\t\t\t" + s + " (天)");
                        }else {
                            AskForLeaveQuantity.setText("请假天数\t\t\t" + billsBean.getDuration() + " (天)");

                        }

                        break;
                    case 2:     //报销单
                        AskForLeaveTitle.setText(billsBean.getEmployeeName() + "的" + UrlInterfaceManager.billType[i]);
                        tvAskForLeaveType.setText("金\t\t\t\t额\t\t\t" + billsBean.getAccount()+"(元)");
                        String createTime2 = billsBean.getCreateTime();
                        if (!TextUtils.isEmpty(createTime2)){
                            String sbcreateTime2 = createTime2.substring(0, 10);
                            AskForLeaveTime.setText(sbcreateTime2);  //提交时间
                        }
                        StartTime.setText("报销部门\t\t\t" + billsBean.getDepName()+ "");
                        llAskForLeaveQuantity.setVisibility(View.GONE);

                        break;
                    case 3:     //离职单
                        AskForLeaveTitle.setText(billsBean.getEmployeeName() + "的" + UrlInterfaceManager.billType[i]);
                        tvAskForLeaveType.setText("部\t\t\t\t门\t\t\t" + billsBean.getDepName()+ "");
                        String createTime3 = billsBean.getCreateTime();
                        String substring3 = createTime3.substring(0, 10);
                        AskForLeaveTime.setText(substring3);

                        String startTime = billsBean.getStartTime();
                        String endTime = billsBean.getCreateTime();
                        String startTimeText = startTime.substring(0, 10);
                        String endTimeText = endTime.substring(0, 10);
                        StartTime.setText("入职时间\t\t\t" + startTimeText + "");
                        AskForLeaveQuantity.setText("离职时间\t\t\t" + endTimeText+ "");

                        break;
                    case 4:     //调岗单
                        AskForLeaveTitle.setText(billsBean.getEmployeeName() + "的" + UrlInterfaceManager.billType[i]);
                        tvAskForLeaveType.setText("部\t\t\t\t门\t\t\t" + billsBean.getDepName()+ "");
                        String createTime4 = billsBean.getCreateTime();
                        String substring4 = createTime4.substring(0, 10);
                        AskForLeaveTime.setText(substring4);

                        StartTime.setText("岗\t\t\t\t位\t\t\t" + billsBean.getEntryPositionName() + "");
                        AskForLeaveQuantity.setText("调整后岗\t\t\t" + billsBean.getTransferPositionName()+ "\n位");
                        break;
                    case 5:     //转正单
                        AskForLeaveTitle.setText(billsBean.getEmployeeName() + "的" + UrlInterfaceManager.billType[i]);
                        tvAskForLeaveType.setText("入职日期\t\t\t" + billsBean.getStartTime()+ "");
                        String createTime5 = billsBean.getCreateTime();
                        String substring5 = createTime5.substring(0, 10);
                        AskForLeaveTime.setText(substring5);

                        StartTime.setText("试用期岗\t\t\t" + billsBean.getEntryPositionName() + "\n位");
                        llAskForLeaveQuantity.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }

                String approveStatus = billsBean.getApproveStatus();
                if (approveStatus.equals("2")) {
                    StatusText.setText("审批同意");
                    StatusText.setTextColor(getResources().getColor(R.color.status_color));
                } else if (approveStatus.equals("3")) {
                    StatusText.setText("审批拒绝");
                    StatusText.setTextColor(getResources().getColor(R.color.statusno_color));
                }

                if (data.size() == (position + 1)) {
                    View view = holder.getView(R.id.view);
                    view.setVisibility(View.GONE);
                }
            }
        };
        mApproveRecyclerView.setAdapter(mCommonAdapter);

        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //已审批页面跳转处理
                String type = data.get(position).getBillType();
                Intent intent = null;
                    switch (type){
                        case "1":     //请假单
                            intent = new Intent(getActivity().getApplicationContext(), ApproveBillsDetailActivity.class);
                            intent.putExtra("key", "AP");
                            intent.putExtra("ap","ApproveOKFragment");
                            CODE = 1;
                            break;
                        case "2":     //报销单
                            intent = new Intent(getActivity().getApplicationContext(), ExpensesHistoryActivity.class);
                            intent.putExtra("ST", "ExHiAPOK");
                            CODE = 2;
                            break;
                        case "3":     //离职单
                            intent = new Intent(getActivity().getApplicationContext(), DimissionBillsDetailActivity.class);
                            intent.putExtra("Dikey","DimissionAPOK");
                            CODE = 3;
                            break;
                        case "4":     //调岗单
                            intent = new Intent(getActivity().getApplicationContext(), TransferPDetailActivity.class);
                            intent.putExtra("keys","TransferAPOK");
                            CODE = 4;
                            break;
                        case "5":     //转正单
                            intent = new Intent(getActivity().getApplicationContext(), BecomeARegularWorkerBillsDetailActivity.class);
                            intent.putExtra("Become","BecomeAPOK");
                            CODE = 5;
                            break;
                        case "6":     //入职单

                            break;
                        default:
                            break;
                    }

                if (data.size()==0 || data == null || intent == null){
                    return;
                }
                intent.putExtra("data",data.get(position));
                startActivityForResult(intent,CODE);
//
//
//                Intent intent = new Intent(getActivity().getApplicationContext(), ApproveBillsDetailActivity.class);
//                intent.putExtra("key", "AP");
//                intent.putExtra("ap","ApproveOKFragment");
//                intent.putExtra("data",data.get(position));
//                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }



    private void init(){
        //设置RecyclerView的显示方式
//        mApproveRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()
//                ,LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager=new WrapContentLinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mApproveRecyclerView.setLayoutManager(linearLayoutManager);

        //下拉刷新 上拉加载
        int homepage_refresh_spacing = 40;
        mWsrlyout.setProgressViewOffset(false, -homepage_refresh_spacing * 2, homepage_refresh_spacing);
        mWsrlyout.setColorSchemeResources(R.color.colorAccent);
        mWsrlyout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mWsrlyout !=null){
                            mWsrlyout.setRefreshing(false);
                            activity.pullUpdate();
                        }

                    }
                }, 2000);
            }

            @Override
            public void onLoad() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mWsrlyout!=null){
                            mWsrlyout.setLoading(false);
                            activity.pullLoading();
                        }

                    }
                }, 2000);
            }

            @Override
            public boolean canLoadMore() {
                return true;
            }

            @Override
            public boolean canRefresh() {
                return true;
            }


        });

        if (mFollowLoadView!=null){
            mFollowLoadView.showContentView();
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        mHasLoadedOnce = true;
        init();
    }

    /**Fragment可见时*/
    @Override
    protected void onVisible() {
        super.onVisible();
        if (activity!=null){
            activity.approveStatus = "2";
            if (count == 0){
                count = count + 1;
                activity.pullUpdate();
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
