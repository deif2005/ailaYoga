package com.dodsport.activity.teachschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.BusiEmployeeListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.SPUtils;
import com.dodsport.view.LoadingView;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 教练 列表
 */

public class CoachListInfoActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.rvCoach)
    RecyclerView mRvCoach;
    @Bind(R.id.loading_View)
    LoadingView mLoadingView;

    private Integer page = 1;
    private String TAG = "***教练信息---";
    private List<BusiEmployeeListBean.DatasBean.EvalListBean> employeeList;
    private CommonAdapter<BusiEmployeeListBean.DatasBean.EvalListBean> mCommonAdapter;
    private int REQUEST_CAMERA_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_list_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("选择老师");
        mRvCoach.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLoadingView.showLoadingView();

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
                Log.i(TAG, "获取列表成功---> " + response.get().toString() + "");
                BusiEmployeeListBean busiEmployeeListBean = JSON.parseObject(response.get(), BusiEmployeeListBean.class);
                if (!busiEmployeeListBean.getResult().getCode().equals("0")){

                    return;
                }
                employeeList = busiEmployeeListBean.getDatas().getEvalList();
                mLoadingView.showContentView();
                show();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取列表成功---> " + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    private void show(){
        mCommonAdapter = new CommonAdapter<BusiEmployeeListBean.DatasBean.EvalListBean>(this,R.layout.coach_list_item,employeeList) {
            @Override
            protected void convert(ViewHolder holder, BusiEmployeeListBean.DatasBean.EvalListBean busiEmployeeList, int position) {

                TextView name = holder.getView(R.id.tvName);
                TextView name2 = holder.getView(R.id.tvName2);
                View view = holder.getView(R.id.view_1);

                if (!busiEmployeeList.getIsCoach().equals("2")){
                    return;
                }
                name.setText(busiEmployeeList.getEmployeeName());
                String jobTitle = busiEmployeeList.getJobTitle();
                if (!TextUtils.isEmpty(jobTitle)){
                    int i = Integer.parseInt(jobTitle);
                    name2.setText("("+UrlInterfaceManager.jobTitle[i]+")");
                }

                if (position == (employeeList.size()-1)){
                    view.setVisibility(View.GONE);
                }
            }
        };

        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra("objectKey",employeeList.get(position));
                setResult(REQUEST_CAMERA_CODE,intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mRvCoach.setAdapter(mCommonAdapter);
    }

    @OnClick(R.id.head_ivBack)
    public void onViewClicked() {
        Intent intent = new Intent();

        finish();
    }
}
