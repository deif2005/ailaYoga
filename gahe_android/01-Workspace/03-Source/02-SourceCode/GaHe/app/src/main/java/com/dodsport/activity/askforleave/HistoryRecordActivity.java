package com.dodsport.activity.askforleave;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.expenses.ExpensesHistoryActivity;
import com.dodsport.activity.personnel.EntryBillsDetailActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.BIllDetailBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.JsonUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 历史记录
 */
public class HistoryRecordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.rlHistoryRecord)
    RecyclerView mRlHistoryRecord;
    @Bind(R.id.wsrlyout)
    WaveSwipeRefreshLayout mWsrlyout;
    @Bind(R.id.follow_loadView)
    LoadingView mFollowLoadView;
    private String TAG = "*****单据历史记录";
    public Handler mHandler;
    private boolean refurbish = false;
    private List<BIllDetailBean.BillsBean> data = new ArrayList<>();
    private CommonAdapter<BIllDetailBean.BillsBean> mCommonAdapter;
    private  Integer page = 1;
    private  Integer billType = 1;
    private  String id;
    private String storeId;
    private String key;


    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!refurbish) {
                        refurbishAdapter();
                    } else {
                        if (mCommonAdapter != null){
                            mCommonAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mRlHistoryRecord.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("历史记录");
        mHeadIvBack.setOnClickListener(this);
        mHandler = new mHandler();
        getReceiveData();

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
                        mWsrlyout.setRefreshing(false);
                        page = 1;
                        if (key.equals("APF")){
                            usetGet();
                        } else if (key.equals("Ha")) {
                            usetGet();
                        }

                    }
                }, 2000);
            }

            @Override
            public void onLoad() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWsrlyout.setLoading(false);
                        page = page+1;
                        if (key.equals("APF")){
                            usetGet();
                        } else if (key.equals("Ha")) {
                            usetGet();
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
    }

    private void getReceiveData(){
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        if (key.equals("APF")){      //审批模块进来
            BIllDetailBean.BillsBean  billsBean = (BIllDetailBean.BillsBean) intent.getSerializableExtra("data");

            String billTypes = billsBean.getBillType();
            if(!TextUtils.isEmpty(billTypes)){
                billType = Integer.parseInt(billTypes);
            }
            id = billsBean.getEmployeeId();
            storeId = billsBean.getStoreId();
            usetGet();
        }else if (key.equals("Ha")){    //填写请假单页面进来
            UserDataBean.DatasBean.ResponseEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
//            Log.i(TAG, "用户信息---->"+userDataBean.toString()+"");
            storeId = userDataBean.getStoreId();
            String type = intent.getStringExtra("type");
            if (!TextUtils.isEmpty(type)){
                billType = Integer.parseInt(type);
            }
            id = userDataBean.getId();
            usetGet();
        }else if (key.equals("Entry")){     //入职单页面进来
            UserDataBean.DatasBean.ResponseEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
            storeId = userDataBean.getStoreId();
            String type = intent.getStringExtra("type");
            if (!TextUtils.isEmpty(type)){
                billType = Integer.parseInt(type);
            }
            id = userDataBean.getId();
            usetGet();
        }
    }


    /**
     * 加载适配器
     */
    private void refurbishAdapter() {
        mCommonAdapter = new CommonAdapter<BIllDetailBean.BillsBean>(HistoryRecordActivity.this, R.layout.approve_item, data) {
            @Override
            protected void convert(ViewHolder holder, BIllDetailBean.BillsBean billsBean, int position) {
                TextView AskForLeaveTitle = holder.getView(R.id.tvAskForLeaveTitle);
                TextView AskForLeaveTime = holder.getView(R.id.tvAskForLeaveTime);
                TextView StartTime = holder.getView(R.id.tvStartTime);
                TextView AskForLeaveQuantity = holder.getView(R.id.tvAskForLeaveQuantity);
                TextView StatusText = holder.getView(R.id.tvStatus);
                LinearLayout Status = holder.getView(R.id.llStatus);
                LinearLayout AskForLeaveType = holder.getView(R.id.llAskForLeaveType);
                TextView tvAskForLeaveType = holder.getView(R.id.tvAskForLeaveType);
                LinearLayout llStartTime = holder.getView(R.id.llStartTime);

                //提交时间
                String createTime = billsBean.getCreateTime();
                String substring = createTime.substring(0, 10);
                AskForLeaveTime.setText(substring);

                if (key.equals("Entry")){    //入职单
//                    AskForLeaveTitle.setText(billsBean.);
                    AskForLeaveType.setVisibility(View.VISIBLE);
                    Status.setVisibility(View.GONE);
                    llStartTime.setVisibility(View.GONE);
                    AskForLeaveTitle.setText("门店名称\t\t\t\t"+"门店A");
                    tvAskForLeaveType.setText("姓  \t\t\t名\t\t\t\t"+billsBean.getEmployeeName());
                    AskForLeaveQuantity.setText("电  \t\t\t话\t\t\t\t"+"15102751000");

                }else { //其他单据
                 Status.setVisibility(View.VISIBLE);
                 AskForLeaveType.setVisibility(View.GONE);
                String vacationType = billsBean.getVacationType();
                int i = 0;
                if (!TextUtils.isEmpty(vacationType)){
                    i = Integer.parseInt(vacationType);
                }
                    AskForLeaveTitle.setText("请假类型\t\t  " + UrlInterfaceManager.type[i]);

                    String substring2 = createTime.substring(0, 16);
                    StartTime.setText("开始时间\t\t\t" + substring2 + "");
                    AskForLeaveQuantity.setText("请假天数\t\t\t" + billsBean.getDuration() + " (天)");
                    String approveStatus = billsBean.getApproveStatus();
                    if (approveStatus.equals("2")) {
                        StatusText.setText("审批同意");
                        StatusText.setTextColor(getResources().getColor(R.color.status_color));
                    } else if (approveStatus.equals("3")) {
                        StatusText.setText("审批拒绝");
                        StatusText.setTextColor(getResources().getColor(R.color.statusno_color));
                    } else if (approveStatus.equals("1")) {
                        StatusText.setTextColor(getResources().getColor(R.color.receive_text));
                        StatusText.setText("待审批");
                    }
                }
            }
        };

        mRlHistoryRecord.setAdapter(mCommonAdapter);

        /**适配器点击事件*/
        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String billType = data.get(position).getBillType();
                if (!TextUtils.isEmpty(billType) && !billType.equals("0")){
                    int i = Integer.parseInt(billType);
                    Intent intent = null;
                    switch (i){
                        case 1:     //请假单
                            intent = new Intent(HistoryRecordActivity.this,BillsDetailActivity.class);
                            intent.putExtra("key","Hi");
                            break;
                        case 2:     //报销单
                            intent= new Intent(HistoryRecordActivity.this, ExpensesHistoryActivity.class);
                            intent.putExtra("ST","ExHi");
                            break;
                        case 3:     //离职单

                            break;
                        case 4:     //调岗单

                            break;
                        case 5:     //转正单
                            break;
                        case 6:     //入职单
                            intent= new Intent(HistoryRecordActivity.this, EntryBillsDetailActivity.class);
                            intent.putExtra("Entry","ExHi");
                            intent.putExtra("Entry","ExHi");
                            break;
                        default:
                            break;
                    }
                    if (intent!=null){
                        intent.putExtra("data",data.get(position));
                        startActivity(intent);
                    }
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**普通员工获取*/
    private void usetGet() {
        Log.i(TAG, "manageGet: 用户请求参数--->"+storeId+"\t"+billType+"\t"+page+"\t"+id+"\t");

        OperatingFloorRequest.getUserListBillInfo(storeId,billType,page,id,"", new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                load(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "onSucceed: 获取数据失败返回 " + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**管理人员获取*/
    private void manageGet(){
        Log.i(TAG, "manageGet: 请求参数--->"+storeId+"\t"+billType+"\t"+page+"\t"+id+"\t");
        OperatingFloorRequest.getListBillInfo(storeId, billType,page,id,"", new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                //Log.i(TAG, "onSucceed: 获取数据返回 " + response.toString() + "");
                load(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "onSucceed: 获取数据失败返回 " + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**加载数据*/
    private void load(Response<String> response){
        try {
            Log.i(TAG, " 获取数据列表返回 " + response.get().toString() + "");
            StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
            ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
            if (!resultBean.getCode().equals("0")) {
                switch (resultBean.getCode()) {
                    case "":

                        break;
                    default:
                        break;
                }
                return;
            }
            JSONObject json = new JSONObject(statusBean.getDatas());
            JSONArray bills = json.getJSONArray("bills");

            List<BIllDetailBean.BillsBean> billsBeen = JsonUtils.fromJSONArrayToList(bills, BIllDetailBean.BillsBean.class);
            if (page == 1){
                data.clear();
            }
            if (billsBeen.size() == 0  || billsBeen == null){
                page = page-1;
                ToastUtils.showToCenters(HistoryRecordActivity.this,"已经没有更多数据了!",1000);
                return;
            }else {
                data.addAll(billsBeen);
                billsBeen.clear();
            }
            mHandler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            default:
                break;

        }
    }
}
