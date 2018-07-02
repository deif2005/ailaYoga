package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.PrivateCoursePlanListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.TimeUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.parseDate;


/**
 * 私教排课
 */
public class PrivateCoachArrangementActivity extends BaseActivity {


    private static final int REQUEST_CAMERA_CODE = 10;
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.tvDate)
    TextView mTvDate;
    @Bind(R.id.icon)
    ImageView mIcon;
    @Bind(R.id.llSearch)
    LinearLayout mLlSearch;
    @Bind(R.id.rvPrivateCoach)
    RecyclerView mRvPrivateCoach;
    @Bind(R.id.etCoachName)
    EditText mEtCoachName;
    @Bind(R.id.rlNullContext)
    RelativeLayout mRlNullContext;

    private ArrayList<PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList> mPlanList = new ArrayList<>();
    private CommonAdapter<PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList> mCommonAdapter;
    private TimePickerView pvTime;
    private Activity mActivity;
    private String TAG = "****私教---";
    private String netTime = ""; //当前日期
    private Handler mHandler;


    private class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (TextUtils.isEmpty(netTime)) {
                        Date now = TimeUtils.now();
                        try {
                            netTime = TimeUtils.parseDate(now);
                            if (!TextUtils.isEmpty(netTime)) {
                                mTvDate.setText(netTime.substring(0, 10));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        mTvDate.setText(netTime.substring(0, 10));
                    }
                    listPrivateCoursePlan();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_coach_arrangement);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("私教排期");
        mHeadTvOK.setText("创建计划");
        mRvPrivateCoach.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mHandler = new mHandler();
        addTime();

        //获取网络时间
        //请求网络资源是耗时操作。放到子线程中进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                getNetTime();
            }
        }).start();

    }


    /**
     * 获取私教排课列表
     */
    private void listPrivateCoursePlan() {
        String storeId = SPUtils.getUserDataBean(this).getStoreId();
        String employeeName = mEtCoachName.getText().toString();
        Integer page = 1;
        netTime = mTvDate.getText().toString();
        //Log.i(TAG, "传参-->" + storeId + "\t" + netTime + "\t" + page + "\t");
        OperatingFloorRequest.listPrivateCoursePlan(storeId, netTime, employeeName, page + "", new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "获取私教排课列表成功---> " + response.get().toString() + "");
                    PrivateCoursePlanListBean privateCoursePlanListBean = JSON.parseObject(response.get(), PrivateCoursePlanListBean.class);
                    if (!privateCoursePlanListBean.getResult().getCode().equals("0")) {

                        ToastUtils.showToCenters(mActivity, "获取列表失败,请稍后重试!", 1000);
                        return;
                    }
                    mPlanList = (ArrayList<PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList>) privateCoursePlanListBean.getDatas().getPrivateCoursePlanList();
                    if (mPlanList.size()==0 ||mPlanList==null){
                        mRvPrivateCoach.setVisibility(View.GONE);
                        mRlNullContext.setVisibility(View.VISIBLE);
                    }else {
                        mRvPrivateCoach.setVisibility(View.VISIBLE);
                        mRlNullContext.setVisibility(View.GONE);
                    }

                    loadonAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (mPlanList.size()==0 ||mPlanList==null){
                    mRvPrivateCoach.setVisibility(View.GONE);
                    mRlNullContext.setVisibility(View.VISIBLE);
                }else {
                    mRvPrivateCoach.setVisibility(View.VISIBLE);
                    mRlNullContext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 加载适配器
     */
    private void loadonAdapter() {
        mCommonAdapter = new CommonAdapter<PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList>(this, R.layout.private_coach_item, mPlanList) {
            @Override
            protected void convert(ViewHolder holder, final PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList planList, final int position) {
                TextView tvItemText1 = holder.getView(R.id.tvItemText1);
                TextView tvItemText2 = holder.getView(R.id.tvItemText2);
                TextView tvItemText3 = holder.getView(R.id.tvItemText3);
                ImageView ivEdit = holder.getView(R.id.ivEdit);
                ImageView ivDelete = holder.getView(R.id.ivDelete);


                tvItemText1.setText(planList.getEmployeeName());
                String jobTitle = planList.getJobTitle();
                tvItemText2.setText(UrlInterfaceManager.jobTitle[Integer.parseInt(jobTitle)]);
                tvItemText3.setText(planList.getClassDate());

                /*编辑私教课程数据*/
                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mActivity, CreateprivateCourseActivity.class);
                        intent.putExtra("key", "edit");
                        intent.putExtra("object", planList);
                        startActivityForResult(intent, REQUEST_CAMERA_CODE);
                    }
                });

                /*删除排课列表条目*/
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OperatingFloorRequest.deletePrivateCoursePlan(planList.getId(), new OnResponseListener<String>() {
                            @Override
                            public void onStart(int what) {

                            }

                            @Override
                            public void onSucceed(int what, Response<String> response) {
                                try {
                                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                                    if (resultBean.getCode().equals("0")){
                                        mCommonAdapter.notifyItemRemoved(position);
                                        mPlanList.remove(position);
                                        mCommonAdapter.notifyItemRangeChanged(position, mPlanList.size());
                                        ToastUtils.showToCenters(mActivity,"删除成功!",1000);
                                    }else {
                                        ToastUtils.showToCenters(mActivity,"删除失败,请稍后重试!",1000);
                                    }
                                } catch (Exception e) {
                                    ToastUtils.showToCenters(mActivity,"删除失败,请稍后重试!",1000);
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailed(int what, Response<String> response) {

                            }

                            @Override
                            public void onFinish(int what) {

                            }
                        });
                    }
                });
            }
        };
        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mActivity, SeePrivateInfoActivity.class);
                intent.putExtra("object", mPlanList.get(position));
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mRvPrivateCoach.setAdapter(mCommonAdapter);
    }

    @OnClick({R.id.head_ivBack, R.id.head_tvOK, R.id.tvDate, R.id.icon, R.id.llSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_tvOK:    //创建计划
                Intent intent = new Intent(mActivity, CreateprivateCourseActivity.class);
                intent.putExtra("key", "add");
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
                break;
            case R.id.tvDate:       //选择日期
            case R.id.icon:
                pvTime.show();
                break;
            case R.id.llSearch:     //搜索教练
                if (TextUtils.isEmpty(mEtCoachName.getText().toString())) {
                    ToastUtils.showToCenters(this, "请输入教练名!", 1000);
                    return;
                }
                listPrivateCoursePlan();
                break;
        }
    }

    /*回调*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CAMERA_CODE) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    listPrivateCoursePlan();
                    break;
            }
        }
    }

    //获取网络时间
    private void getNetTime() {
        URL url = null;//取得资源对象
        try {
            //url = new URL("http://www.baidu.com");
            url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            //url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            netTime = formatter.format(calendar.getTime());
            mHandler.sendEmptyMessage(1);
        } catch (Exception e) {
            mHandler.sendEmptyMessage(1);
            e.printStackTrace();
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
                    mTvDate.setText(datetime);
                    listPrivateCoursePlan();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
