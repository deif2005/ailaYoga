package com.dodsport.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dodsport.R;
import com.dodsport.activity.teachschool.lcrecyclerview.adapter.BaseHeaderAdapter;
import com.dodsport.activity.teachschool.lcrecyclerview.entitiy.PinnedHeaderEntity;
import com.dodsport.model.AbnormalAttendanceBean;
import com.dodsport.model.CoursePlanListBean;
import com.dodsport.model.SignListBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.ImageUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.TransformationUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.TimeUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.oushangfeng.pinnedsectionitemdecoration.callback.OnHeaderClickListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
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
 * 签到 异常记录
 *
 * @author Administrator
 */
public class ExceptionRecordActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvUserName)
    TextView mTvUserName;
    @Bind(R.id.tvJobNumber)
    TextView mTvJobNumber;
    @Bind(R.id.tvDate)
    TextView mTvDate;
    @Bind(R.id.icon)
    ImageView mIcon;
    @Bind(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @Bind(R.id.rvException)
    RecyclerView mRvException;
    @Bind(R.id.LoadingView)
    LoadingView mLoadingView;
    @Bind(R.id.ivUserHead)
    ImageView mIvUserHead;

    private Activity mActivity;
    //适配器
    private BaseHeaderAdapter<PinnedHeaderEntity<String>> mAdapter;
    private boolean adapter = true;
    private ArrayList<CoursePlanListBean.DatasBean.CoursePlanList> coursePlanList = new ArrayList<>();
    private TimePickerView pvTime;
    private String datetime = "";
    private Intent mIntent;
    private SignListBean.DatasBean.SignList mSignList;      //考勤规则对象
    private boolean fa = false;
    private AbnormalAttendanceBean.DatasBean.AbnormalAttendance attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_record);
        ButterKnife.bind(this);
        mActivity = this;
        initView();

        getEmployeeSignById();
    }


    private void getEmployeeSignById(){
        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();
        String empId = mSignList.getEmpId();

        PunchTheClockRequest.getEmployeeSignById(businessId, storeId, datetime, empId, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                Log.i("****", "获取考勤异常记录--->"+response.get().toString()+"");
                AbnormalAttendanceBean abnormalAttendanceBean = JSON.parseObject(response.get(), AbnormalAttendanceBean.class);
                if (!abnormalAttendanceBean.getResult().getCode().equals("0")){

                    ToastUtils.showToCenters(mActivity,"获取数据失败,请稍后重试!",1000);
                    return;
                }
               attendance = abnormalAttendanceBean.getDatas().getAbnormalAttendance();
                initAdapter();
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                Log.i("****", "获取考勤异常记录失败--->"+response.toString()+"");
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }

    private void initView() {
        mRvException.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("异常记录");
        try {
            mIntent = getIntent();
            mSignList = (SignListBean.DatasBean.SignList) mIntent.getSerializableExtra("object");
            datetime = mIntent.getStringExtra("time");
            String currentDatetime = TimeUtils.currentDatetime();
            datetime = currentDatetime.substring(0, 7);
            mTvDate.setText(currentDatetime.substring(0, 4) + "年" + currentDatetime.substring(5, 7) + "月");

            if (mSignList != null) {
                mTvJobNumber.setText("工号：" + mSignList.getEmployeeSerialId() + "");
                mTvUserName.setText(mSignList.getEmployeeName());
                //头像
                if (!TextUtils.isEmpty(mSignList.getEmpHead())) {
                    Picasso.with(mActivity).load(mSignList.getEmpHead())
                            .resize(400, 400)
                            .config(Bitmap.Config.RGB_565)
                            .error(R.drawable.mo_ren_tou_xiang_xxhdpi)
                            .placeholder(R.drawable.mo_ren_tou_xiang_xxhdpi)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .centerCrop()
                            .transform(TransformationUtils.zipImage(mIvUserHead)).into(mIvUserHead);
                }
            }
            addTime();
            for (int i = 0; i < 20; i++) {
                CoursePlanListBean.DatasBean.CoursePlanList m = new CoursePlanListBean.DatasBean.CoursePlanList();
                coursePlanList.add(m);
            }

            initAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载适配器
     */
    private void initAdapter() {

        final List<PinnedHeaderEntity<String>> data = new ArrayList<>();
        final List<String> lateTime = attendance.getLateTime();
        final List<String> earlyTime = attendance.getEarlyTime();
        final List<String> notSign = attendance.getNotSign();


        if (lateTime.size() != 0) {
            String substring = "迟到";
            data.add(new PinnedHeaderEntity<>(lateTime.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < lateTime.size(); i++) {
                data.add(new PinnedHeaderEntity<>(lateTime.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }
        if (earlyTime.size() != 0) {
            String substring = "早退";
            data.add(new PinnedHeaderEntity<>(earlyTime.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < earlyTime.size(); i++) {
                data.add(new PinnedHeaderEntity<>(earlyTime.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }

        if (notSign.size() != 0) {
            String substring = "缺卡";
            data.add(new PinnedHeaderEntity<>(notSign.get(0), BaseHeaderAdapter.TYPE_HEADER, substring));
            for (int i = 0; i < notSign.size(); i++) {
                data.add(new PinnedHeaderEntity<>(notSign.get(i), BaseHeaderAdapter.TYPE_DATA, substring));
            }
        }
        if (mAdapter != null) {
            mAdapter = null;
        }

        mAdapter = new BaseHeaderAdapter<PinnedHeaderEntity<String>>((ArrayList<PinnedHeaderEntity<String>>) data) {
            @Override
            protected void convert(BaseViewHolder holder, PinnedHeaderEntity<String> coursePlanList) {
                try {
                    switch (holder.getItemViewType()) {
                        case BaseHeaderAdapter.TYPE_HEADER:
                            holder.setText(R.id.my_set_tvNickName, coursePlanList.getPinnedHeaderName());
                            //holder.setBackgroundColor(R.id.layout,getResources().getColor(R.color.white));

                            TextView tvNumber = holder.getView(R.id.tvNumbers);
                            if (coursePlanList.getPinnedHeaderName().trim().equals("迟到")) {
                                tvNumber.setText(lateTime.size()+"次");
                            } else if (coursePlanList.getPinnedHeaderName().trim().equals("早退")) {
                                tvNumber.setText(earlyTime.size()+"次");
                            } else if (coursePlanList.getPinnedHeaderName().trim().equals("缺卡")) {
                                tvNumber.setText(notSign.size()+"次");
                            }

                            break;
                        case BaseHeaderAdapter.TYPE_DATA:
                            final int position = holder.getLayoutPosition();
                            if (mRvException.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                                // 瀑布流布局记录随机高度，就不会导致Item由于高度变化乱跑，导致画分隔线出现问题
                                // 随机高度, 模拟瀑布效果.
                                if (mRandomHeights == null) {
                                    mRandomHeights = new SparseIntArray(getItemCount());
                                }
                                if (mRandomHeights.get(position) == 0) {
                                    mRandomHeights.put(position, ImageUtils.dip2px(mActivity, (int) (100 + Math.random() * 100)));
                                }
                                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                                lp.height = mRandomHeights.get(position);
                                holder.itemView.setLayoutParams(lp);
                            }

                            LinearLayout llSignTheCard = holder.getView(R.id.llSignTheCard);
                            if (coursePlanList.getPinnedHeaderName().trim().equals("迟到")) {
                                llSignTheCard.setVisibility(View.GONE);
                            } else if (coursePlanList.getPinnedHeaderName().trim().equals("早退")) {
                                llSignTheCard.setVisibility(View.GONE);
                            } else if (coursePlanList.getPinnedHeaderName().trim().equals("缺卡")) {
                                llSignTheCard.setVisibility(View.VISIBLE);
                            }

                            TextView tvContext = holder.getView(R.id.my_set_tvNickName1);
                            tvContext.setText(coursePlanList.getData());
                            /**签卡点击*/
                            llSignTheCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (view.getId()) {
                                        case R.id.llSignTheCard:
                                            Log.i("****", "点击签卡---->");
                                            break;
                                        default:
                                            break;
                                    }

                                }
                            });


//                            String classDatetime = coursePlanList.getData().getClassDatetime();
//                            String duration = coursePlanList.getData().getDuration();
//                            int EndTime = Integer.parseInt(duration);
//                            /*计算课程的开始时间和结束时间*/
//                            String endTime = TimeUtils.addDateMinut(classDatetime, EndTime);
//                            String substring = endTime.substring(11, 16);
//                            String startTime = coursePlanList.getData().getClassDatetime().substring(11, 16);
//                            tvTime.setText(startTime + " -- " + substring);
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            private SparseIntArray mRandomHeights;

            @Override
            protected void addItemTypes() {
                addItemType(BaseHeaderAdapter.TYPE_HEADER, R.layout.exception_type_item);
                addItemType(BaseHeaderAdapter.TYPE_DATA, R.layout.exception_recor_item);
            }

        };
        mAdapter.onAttachedToRecyclerView(mRvException);
        if (adapter) {
            adapter = false;
            mRvException.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    switch (mAdapter.getItemViewType(i)) {
                        case BaseHeaderAdapter.TYPE_DATA:   //列表点击
                            break;
                        case BaseHeaderAdapter.TYPE_HEADER: //标题点击
                            break;
                        default:
                            break;
                    }
                }
            });

            /**标签的点击事件*/
            OnHeaderClickListener headerClickListener = new OnHeaderClickListener() {
                @Override
                public void onHeaderClick(View view, int id, int position) {
                }

                @Override
                public void onHeaderLongClick(View view, int id, int position) {
                }

                @Override
                public void onHeaderDoubleClick(View view, int id, int position) {
                }
            };

            mRvException.addItemDecoration(new PinnedHeaderItemDecoration
                    .Builder(BaseHeaderAdapter.TYPE_HEADER)
//                    .setDividerId(R.drawable.divider)
                    .enableDivider(false)
                    .disableHeaderClick(true)
                    .setHeaderClickListener(headerClickListener).create());
        }


        mRvException.setAdapter(mAdapter);

    }


    @OnClick({R.id.head_ivBack, R.id.tvDate, R.id.icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.tvDate:       //选择月份
            case R.id.icon:
                pvTime.show();
                break;
            default:
                break;
        }
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
                    datetime = Year + "-" + mMoon;
                    mTvDate.setText(Year + "年" + mMoon + "月");


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
