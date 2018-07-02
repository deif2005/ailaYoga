package com.dodsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.adapter.ExpandableAdapter;
import com.dodsport.model.CompanyListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.JsonUtils;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyListActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.elCompanyList)
    ExpandableListView mElCompanyList;
    @Bind(R.id.tvSelectCompany)
    TextView mTvSelectCompany;
    private String TAG = "****";
    private boolean loadon = false;
    private  ExpandableAdapter mExpandableAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String company = getIntent().getStringExtra("company");
        mTvSelectCompany.setText("当前选择："+company);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadIvBack.setOnClickListener(this);
        mHeadTvTitle.setText("选择门店");

        getCompanyList();

    }

    private void getCompanyList() {
        UserDataBean.DatasBean.BusiEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
        String phoneNum = userDataBean.getPhoneNum();
        OperatingFloorRequest.getCompanyList(phoneNum, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }
            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取商家列表----->" + response.toString() + "");
                try {
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")){
                    switch (resultBean.getCode()){
                        case "5002":

                            break;
                    }
                    return;
                }

                    //获取商家列表
                    JSONObject json = new JSONObject(statusBean.getDatas());
                    JSONObject busiEmployee = json.getJSONObject("busiEmployee");
                    CompanyListBean companyListBean = JsonUtils.fromJsonObjectToObject(busiEmployee, CompanyListBean.class);
                    if (!loadon){   //第一次加载适配器
                        LoadOnData(companyListBean);

                    }else { //已经加载适配器
                        if (mExpandableAdapter!=null)
                            mExpandableAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
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

    /**加载网络数据*/
    private void LoadOnData(CompanyListBean companyListBean) {

        List<CompanyListBean.BusinessListBean> groupArray = new ArrayList<>();
        List<CompanyListBean.BusinessListBean.StoreInfoListBean> groupArray2 = new ArrayList<>();
        List<List<CompanyListBean.BusinessListBean.StoreInfoListBean>> childArray = new ArrayList<>();
        //一级条目
       groupArray.addAll(companyListBean.getBusinessList());
        for (int i = 0; i < groupArray.size(); i++) {
            for(int s=0; s < companyListBean.getBusinessList().get(i).getStoreInfoList().size();s++){
                //二级内容
                groupArray2.add(companyListBean.getBusinessList().get(i).getStoreInfoList().get(s));
            }
            //有多少个一级条目
            childArray.add(groupArray2);
        }

        mElCompanyList.setGroupIndicator(null);
        mExpandableAdapter = new ExpandableAdapter(this, groupArray, childArray);
        mElCompanyList.setAdapter(mExpandableAdapter);
        loadon = true;
    }

    /**用户切换商家*/
    public void Blocked_Out(CompanyListBean.BusinessListBean mBusinessListBean){
        if (mBusinessListBean ==null){
            return;
        }
        Log.i(TAG, "Blocked_Out: "+mBusinessListBean.toString()+"");
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        if (position.equals("EntryBills")){
            intent.putExtra("Company",mBusinessListBean.getBusinessName());
            intent.putExtra("businessId",mBusinessListBean.getId());
            intent.putExtra("storeSerialId",mBusinessListBean.getBusinessSerialId());
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        String businessid = mBusinessListBean.getId();
        String storeid = mBusinessListBean.getBusinessSerialId();
        getNetData(businessid,storeid);

    }

    /**用户切换门店接口*/
    public void initV(CompanyListBean.BusinessListBean.StoreInfoListBean mStoreInfoListBean){
        if (mStoreInfoListBean ==null){
            return;
        }
        Log.i(TAG, "initV: "+mStoreInfoListBean.toString()+"");
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        if (position.equals("EntryBills")){
            intent.putExtra("Company",mStoreInfoListBean.getStoreName());
            intent.putExtra("businessId",mStoreInfoListBean.getBusinessId());
            intent.putExtra("storeSerialId",mStoreInfoListBean.getId());
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        String businessid = mStoreInfoListBean.getBusinessId();
        String storeid = mStoreInfoListBean.getId();
        getNetData(businessid,storeid);

    }

    private void getNetData( String businessid, String storeid){
        String phoneNum = SPUtils.getUserDataBean(this).getPhoneNum();
        Log.i(TAG, "用户切换门店请求参数--->\tphoneNum--->"+phoneNum+"\tbusinessid--->"+businessid+"\tstoreid--->"+storeid+"\t");
        OperatingFloorRequest.getEmployeeInfo2(phoneNum, businessid, storeid, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "用户切换门店成功--->"+response.toString());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "用户切换门店失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
        }
    }


}
