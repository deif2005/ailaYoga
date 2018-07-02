package com.dodsport.activity.cardcoupons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.MultiSelectRecyclerViewAdapter;
import com.dodsport.model.MemberCardListBean;
import com.dodsport.model.StoreInfoListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关联门店
 */
public class RelevanceStoreActivity extends BaseActivity{

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.llDeleteStore)
    LinearLayout mLlDeleteStore;
    @Bind(R.id.rvAddStore)
    RecyclerView mRvAddStore;
    @Bind(R.id.llAddStore)
    LinearLayout mLlAddStore;
    @Bind(R.id.rvDeleteStore)
    RecyclerView mRvDeleteStore;
    @Bind(R.id.btConservation)
    Button mBtConservation;
    @Bind(R.id.btCancel)
    Button mBtCancel;

    private String TAG = "***门店关联--";
    private MemberCardListBean.DatasBean.MemberCardList memberCard;
    private ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> addList = new ArrayList<>();
    private ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> addList2 = new ArrayList<>();
    private ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> deleteList = new ArrayList<>();
    private ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> deleteList2 = new ArrayList<>();
    private Activity mContext;
    private MultiSelectRecyclerViewAdapter mAdapter1;
    private MultiSelectRecyclerViewAdapter mAdapter2;
    private List<Integer> selectedItems = new ArrayList<>();
    private int addlistSize =0;
    private List<Integer> selectedItems1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevance_store);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("关联门店");
        mRvAddStore.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvDeleteStore.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        Intent intent = getIntent();
        memberCard = (MemberCardListBean.DatasBean.MemberCardList) intent.getSerializableExtra("CardInfo");
        getNetData();

    }

    //没有进行关联门店的适配器
    private void loadOnAddAdapter(ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> addLists){
        if (addList.size()!=0){
            addList.clear();
        }
        addList.addAll(addLists);
        mAdapter1 = new MultiSelectRecyclerViewAdapter(addList,mContext,new onItemClicked(),R.layout.relevance_store_item);
        mRvAddStore.setAdapter(mAdapter1);
    }

    //已经进行关联门店的适配器
    private void loadOnDeleteAdapter(ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> addLists){
        if (deleteList.size()!=0){
            deleteList.clear();
        }
        deleteList.addAll(addLists);
        mAdapter2 = new MultiSelectRecyclerViewAdapter(deleteList,mContext,new onItemClicked1(),R.layout.relevance_store_item);
        mRvDeleteStore.setAdapter(mAdapter2);
    }


    //没有进行关联门店的点击事件
    private class onItemClicked implements  MultiSelectRecyclerViewAdapter.ViewHolder.ClickListener {

        @Override
        public void onItemClicked(int position) {
            mAdapter1.switchSelectedState(position);
            selectedItems = mAdapter1.getSelectedItems();

        }

        @Override
        public boolean onItemLongClicked(int position) {
            return false;
        }
    }
    //已经进行关联门店的点击事件
    private class onItemClicked1 implements  MultiSelectRecyclerViewAdapter.ViewHolder.ClickListener {

        @Override
        public void onItemClicked(int position) {
            mAdapter2.switchSelectedState(position);
            selectedItems1 = mAdapter2.getSelectedItems();
        }

        @Override
        public boolean onItemLongClicked(int position) {
            return false;
        }
    }

    @OnClick({R.id.head_ivBack, R.id.llDeleteStore, R.id.llAddStore, R.id.btConservation, R.id.btCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llDeleteStore:    //删除关联门店
                ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> data2 = new ArrayList<>();
                if (selectedItems1.size() == 0){
                    ToastUtils.showToCenters(this,"请选择要移除的门店!",800);
                    return;
                }
                for (int i = 0; i < selectedItems1.size(); i++) {
                    data2.add(deleteList.get(selectedItems1.get(i)));//选中门店
                }
                if (deleteList2.size()!=0){
                    deleteList2.clear();
                }
                for (int i = 0; i < data2.size(); i++) {
                    deleteList.remove(data2.get(i));        //在原来已经关联的门店集合中删除掉 选中的门店
                    mAdapter2.notifyItemRemoved(selectedItems1.get(i));
                }
                deleteList2.addAll(deleteList);   //已经关联的门店
                addList2.addAll(data2);           //选中删除已经关联的门店
                addList2.addAll(addList);         // 原来原来关联的门店
                loadOnAddAdapter(addList2);
                mAdapter1.notifyDataSetChanged();
                //已经关联的门店
                loadOnDeleteAdapter(deleteList2);
                mAdapter2.notifyDataSetChanged();
                data2.clear();
                addList2.clear();
                deleteList2.clear();
                selectedItems1.clear();
                break;
            case R.id.llAddStore:       //店家关联门店
                ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> data1 = new ArrayList<>();
                if (selectedItems.size() == 0){
                    ToastUtils.showToCenters(this,"请选择要添加的门店!",800);
                    return;
                }
                for (int i = 0; i < selectedItems.size(); i++) {
                    data1.add(addList.get(selectedItems.get(i)));       //选中的门店
                }
                    if (addList2.size()!=0){
                        addList2.clear();
                    }
                for (int i = 0; i < data1.size(); i++) {
                    addList.remove(data1.get(i));       //要添加关联的门店
                    mAdapter1.notifyItemRemoved(selectedItems.get(i));      //适配器中去掉选中关联的门店
                }
                addList2.addAll(addList);       //没有选中的门店
                deleteList2.addAll(deleteList); //选中门店和 原来已经关联的门店
                deleteList2.addAll(data1);

                loadOnAddAdapter(addList2);//可以关联门店的适配器
                mAdapter1.notifyDataSetChanged();
                //已经关联的门店
                loadOnDeleteAdapter(deleteList2);   //已经关联的门店适配器
               mAdapter2.notifyDataSetChanged();
                data1.clear();
                addList2.clear();
                deleteList2.clear();
                selectedItems.clear();
                break;
            case R.id.btConservation:   //保存
                conservationListStore();
                break;
            case R.id.btCancel:         //取消
                getNetData();
                break;
            default:
                break;
        }
    }


    private void conservationListStore(){
        String membercardId = memberCard.getId();
        String storeIdStr = "";
        for (int i = 0; i < deleteList.size(); i++) {
            storeIdStr = storeIdStr + deleteList.get(i).getId()+",";    //拼接要关联门店的ID
       }
        Log.i(TAG, "要绑定的门店ID--->"+storeIdStr+"");
        OperatingFloorRequest.addMembercardStoreRelationList(storeIdStr, membercardId, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {
                    
                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    Log.i(TAG, "绑定成功--->"+response.toString()+"");
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    Log.i(TAG, "绑定失败--->"+response.toString()+"");
                }

                @Override
                public void onFinish(int what) {

                }
            });

    }


    public void getNetData() {
        String businessId = SPUtils.getUserDataBean(this).getBusinessId();
        String memberCardId = memberCard.getId();
        Log.i(TAG, "传参-->\tbusinessId-->"+businessId+"\tmemberCardId-->"+memberCardId+"");
        OperatingFloorRequest.queryStoreInfoList(businessId, memberCardId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {

                    StoreInfoListBean storeInfoListBean = JSON.parseObject(response.get(), StoreInfoListBean.class);
                    if (!storeInfoListBean.getResult().getCode().equals("0")){
                        return;
                    }
                    ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> storeInfoListlist = (ArrayList<StoreInfoListBean.DatasBean.StoreInfoList>) storeInfoListBean.getDatas().getStoreInfoListlist();
                    ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> store1 = new ArrayList<>();
                    ArrayList<StoreInfoListBean.DatasBean.StoreInfoList> store2 = new ArrayList<>();
                    for (int i = 0; i < storeInfoListlist.size(); i++) {
                        if (TextUtils.isEmpty(storeInfoListlist.get(i).getCid())){   //可以绑定（未绑定）
                            store1.add(storeInfoListlist.get(i));
                        }else {
                            store2.add(storeInfoListlist.get(i));       //已经绑定
                        }
                    }
                    deleteList2.clear();
                    addList2.clear();
                    loadOnAddAdapter(store1);
                    loadOnDeleteAdapter(store2);

                    Log.i(TAG, "获取列表成功-->"+response.toString()+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取列表失败-->"+response.toString()+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
