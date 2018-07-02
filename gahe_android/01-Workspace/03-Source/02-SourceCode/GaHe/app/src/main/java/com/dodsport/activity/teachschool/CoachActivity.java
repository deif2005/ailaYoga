package com.dodsport.activity.teachschool;

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
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.BusiEmployeeListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.CircleImageTransformation;
import com.dodsport.utils.SPUtils;
import com.dodsport.view.LoadingView;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 教练
 */

public class CoachActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.tvItem2)
    TextView mTvItem2;
    @Bind(R.id.tvItem3)
    TextView mTvItem3;
    @Bind(R.id.Price_RecyclerView)
    RecyclerView mPriceRecyclerView;
    @Bind(R.id.LoadingView)
    LoadingView mLoadingView;
    @Bind(R.id.llErrorDisplay)
    LinearLayout mLlErrorDisplay;
    @Bind(R.id.layout)
    LinearLayout mLayout;


    private List<String> data = new ArrayList<>();
    private CommonAdapter<BusiEmployeeListBean.DatasBean.EvalListBean> mCommonAdapter;
    private String TAG = "****教练--";
    private int page = 1;
    private List<BusiEmployeeListBean.DatasBean.EvalListBean> employeeList = new ArrayList<>(); //门点教练
    private Activity mActivity;
    private final static int CODE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mPriceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("教练");
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mLoadingView.showLoadingView();
        mTvItem1.setText("教练");
        mTvItem2.setText("职称");
        mTvItem3.setText("关联课程");

        getCoachList();

    }


    /**
     * 获取门店教练列表
     */
    public void getCoachList() {
        String storeId = SPUtils.getUserDataBean(this).getStoreId();
        OperatingFloorRequest.queryCoachListByStoreId(storeId, page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "获取列表成功---> " + response.get().toString() + "");
                    mLoadingView.showContentView();
                    BusiEmployeeListBean busiEmployeeListBean = JSON.parseObject(response.get(), BusiEmployeeListBean.class);
                    if (!busiEmployeeListBean.getResult().getCode().equals("0")) {
                        mLoadingView.showErrorView(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getCoachList();
                            }
                        });
                        return;
                    }
                    employeeList = busiEmployeeListBean.getDatas().getEvalList();
                    if (employeeList.size() == 0 || employeeList == null) {
                        mLlErrorDisplay.setVisibility(View.VISIBLE);
                        mLayout.setVisibility(View.VISIBLE);
                        mPriceRecyclerView.setVisibility(View.GONE);
                        return;
                    } else {
                        mLlErrorDisplay.setVisibility(View.GONE);
                        mLayout.setVisibility(View.GONE);
                        mPriceRecyclerView.setVisibility(View.VISIBLE);
                    }

                    adapter();
                } catch (Exception e) {
                    mLoadingView.showErrorView(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getCoachList();
                        }
                    });
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mLoadingView.showErrorView(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCoachList();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 加载适配器
     */
    private void adapter() {

        mCommonAdapter = new CommonAdapter<BusiEmployeeListBean.DatasBean.EvalListBean>(this, R.layout.coach_item, employeeList) {
            @Override
            protected void convert(ViewHolder holder, final BusiEmployeeListBean.DatasBean.EvalListBean busiEmployeeList, int position) {

                TextView tvName = holder.getView(R.id.tvName);
                TextView tvAcademicTitle = holder.getView(R.id.tvAcademicTitle);
                ImageView ivUserHead = holder.getView(R.id.ivUserHead);
                ImageView ivCorrelation = holder.getView(R.id.ivCorrelation);

                try {
                    //头像
                    if (!TextUtils.isEmpty(busiEmployeeList.getEmpHead())){
                        Picasso.with(mActivity)
                                .load(busiEmployeeList.getEmpHead())
                                .placeholder(R.drawable.publish_add)
                                .error(R.drawable.publish_add)
                                .resize(150, 150)
                                        .transform(new CircleImageTransformation())//展示圆形图片
                                .centerCrop()
                                .into(ivUserHead);
                    }

                    tvName.setText(busiEmployeeList.getEmployeeName());
                    String jobTitle = busiEmployeeList.getJobTitle();
                    if (!TextUtils.isEmpty(jobTitle)){
                        int i = Integer.parseInt(jobTitle);
                        tvAcademicTitle.setText(UrlInterfaceManager.jobTitle[i]);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                /**查看关联课程*/
                ivCorrelation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mActivity,CorrelationCourseActivity.class);
                        intent.putExtra("object",busiEmployeeList);
                        startActivityForResult(intent,CODE);
                    }
                });

            }
        };


        mPriceRecyclerView.setAdapter(mCommonAdapter);
    }


    @OnClick(R.id.head_ivBack)
    public void onViewClicked() {
        finish();
    }
}
