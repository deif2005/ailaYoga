package com.dodsport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.SignListBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.LogUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.TimeUtils;
import com.dodsport.weight.WrapContentLinearLayoutManager;
import com.dodsport.weight.pickView.TimePickerView;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.dodsport.weight.TimeUtils.parseDate;


/**
 * 考勤记录
 * @author Administrator
 */
public class PunchTheClockRecordActivity extends BaseActivity {

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
    @Bind(R.id.rvRecord)
    RecyclerView mRvRecord;
    @Bind(R.id.wsrlyout)
    WaveSwipeRefreshLayout mWsrlyout;
    @Bind(R.id.LoadingView)
    LoadingView mLoadingView;

    private Activity mActivity;
    private TimePickerView pvTime;
    private String datetime = "";   //日期
    private String TAG = "***考勤记录---";
    private CommonAdapter<SignListBean.DatasBean.SignList> mCommonAdapter;
    private static final int REQUEST_CAMERA_CODE = 10;
    private List<SignListBean.DatasBean.SignList> signList = new ArrayList<>();//考勤记录集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_the_clock_record);
        ButterKnife.bind(this);
        mActivity = this;
        mLoadingView.showLoadingView();
        initView();
        ini();
    }

    /**初始化控件*/
    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("考勤记录");
        mHeadTvOK.setText("考勤规则");
        mRvRecord.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        Intent intent = getIntent();
        datetime = intent.getStringExtra("time");
        try {
            String currentDatetime = TimeUtils.currentDatetime();
            datetime = currentDatetime.substring(0, 7);
            mTvDate.setText(currentDatetime.substring(0,4)+"年"+currentDatetime.substring(5,7)+"月");
            querySignListByStoreId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addTime();
    }

    private void adapter(final List<SignListBean.DatasBean.SignList> signList){
        mCommonAdapter = new CommonAdapter<SignListBean.DatasBean.SignList>(mActivity,R.layout.record_item,signList) {
            @Override
            protected void convert(ViewHolder holder, SignListBean.DatasBean.SignList sign, int position) {
                TextView my_set_tvNickName = holder.getView(R.id.my_set_tvNickName);
                TextView tvJobNumber = holder.getView(R.id.tvJobNumber);
                TextView tvBeLate = holder.getView(R.id.tvBeLate);
                TextView tvPosition = holder.getView(R.id.tvPosition);
                TextView tvLeaveEarly = holder.getView(R.id.tvLeaveEarly);
                TextView tvDepartment = holder.getView(R.id.tvDepartment);
                TextView tvMissingCard = holder.getView(R.id.tvMissingCard);

                //名称
                my_set_tvNickName.setText(sign.getEmployeeName());
                //工号
                tvJobNumber.setText("工号："+sign.getEmployeeSerialId()+"");
                //职位
                tvPosition.setText(sign.getPositionName());
                //部门
                tvDepartment.setText(sign.getDepName());
                //迟到
                tvBeLate.setText(sign.getLasttimes()+"次");
                //早退
                tvLeaveEarly.setText(sign.getLeavetimes()+"次");
                //缺卡
                tvMissingCard.setText(sign.getFailurePunch()+"天");



            }
        };
        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                try {
                    Intent intent = new Intent(mActivity,ExceptionRecordActivity.class);
                    intent.putExtra("object",signList.get(position));
                    intent.putExtra("time",datetime);
                    startActivityForResult(intent,REQUEST_CAMERA_CODE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mRvRecord.setAdapter(mCommonAdapter);
        mLoadingView.showContentView();
    }



    @OnClick({R.id.head_ivBack, R.id.head_tvOK, R.id.tvDate, R.id.icon, R.id.llSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            //考勤规则
            case R.id.head_tvOK:
                Intent intent = new Intent(mActivity,PunchTheClockRuleActivity.class);
                startActivity(intent);
                break;
            //选择日期
            case R.id.tvDate:
            case R.id.icon:
                pvTime.show();
                break;
            //搜索
            case R.id.llSearch:

                break;
            default:
                break;
        }
    }

    /**初始化下拉刷新 上滑加载*/
    private void ini() {
        LinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRvRecord.setLayoutManager(linearLayoutManager);

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
                        querySignListByStoreId();
                    }
                }, 2000);
            }

            @Override
            public void onLoad() {
                mWsrlyout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWsrlyout.setLoading(false);
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

        if (mLoadingView != null) {
            mLoadingView.showContentView();
        }
    }


    /**根据日期获取签到记录*/
    private void querySignListByStoreId(){
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();
        Log.i(TAG, "传参****---->"+storeId+"\t"+datetime+"\t");
        int page = 1;
        PunchTheClockRequest.querySignListByStoreId(storeId, datetime,page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    LogUtils.i(TAG, "根据日期获取成功---> "+response.get().toString()+"");
                    SignListBean signListBean = JSON.parseObject(response.get(), SignListBean.class);
                    if (!signListBean.getResult().getCode().equals("0")){
                        ToastUtils.showToCenters(mActivity,"获取考勤记录失败!",1000);
                        mLoadingView.showErrorView(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                querySignListByStoreId();
                            }
                        });
                        return;
                    }
                    signList = signListBean.getDatas().getSignList();
                    adapter(signList);
                } catch (Exception e) {
                    e.printStackTrace();
                    mLoadingView.showErrorView(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            querySignListByStoreId();
                        }
                    });
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mLoadingView.showErrorView(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        querySignListByStoreId();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 时间选择器
     */
    private void addTime() {
        // 时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAYS);
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
                    datetime = parseDate(date);

                    String mYear = datetime.substring(0, 4);
                    String moon = datetime.substring(5, 7);
                    String mMoon = null;
                    String Year = null;

                    if (!TextUtils.isEmpty(moon)) {
                        int i = Integer.parseInt(moon);
                        int s = i + 1;
                        if (s == 13) {
                            s = 1;
                        }
                        if (s == 1) {
                            int year = Integer.parseInt(mYear);
                            Year = (year + 1) + "";
                        } else {
                            Year = mYear;
                        }
                        if (s > 9 || s == 12) {
                            mMoon = s + "";
                        } else {
                            mMoon = s + "";
                            mMoon = "0" + mMoon;
                        }
                    }
                    datetime = Year+"-"+mMoon;
                    mTvDate.setText(Year+"年"+mMoon+"月");
                    querySignListByStoreId();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
