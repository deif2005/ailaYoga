package com.dodsport.fragment.operatingfloorfragment.membercardoperate;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.adapter.CardRecordXRecyclerAdapter;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.MembercardRecordListBean;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 会员卡记录 Fragment
 */
public class CardRecordFragment extends BaseFragment {

    @Bind(R.id.tishi)
    LinearLayout mTishi;
    @Bind(R.id.Fragmentrv)
    public RecyclerView mFragmentrv;
    @Bind(R.id.WaveSwipeRefresh_Layout)
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;


    private View mView;
    protected int devider;
    protected SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;
    private FragmentCallBack mFragmentCallBack;
    public CardRecordXRecyclerAdapter mCommonAdapter;
    private Activity mActivity;

    protected boolean isRefreshing;
    private List<String> data = new ArrayList<>();
    private String TAG = "****记录碎片";
    private ArrayList<MembercardRecordListBean.DatasBean.MembercardRecordList> membercardRecordList = new ArrayList<>();
    private CommonAdapter<MembercardRecordListBean.DatasBean.MembercardRecordList> mListCommonAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_card_record, container, false);
        }
        ButterKnife.bind(this, mView);
        ini();
        return mView;
    }

    private void ini() {
        mFragmentrv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));



        mFragmentrv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                //Log.i(TAG, "滑动监听--->"+totalItemCount+"");
                //Log.i(TAG, "滑动监听2222--->"+lastVisibleItem+"");

//                if (!loading && totalItemCount < (lastVisibleItem + Constant.VISIBLE_THRESHOLD)) {
//                    new ArticleTask(mActivity).execute(mAdapter.getBottomArticleId());
//                    loading = true;
//                }
            }
        });


        //下拉刷新 上拉加载
        int homepage_refresh_spacing = 40;
        mWaveSwipeRefreshLayout.setProgressViewOffset(false, -homepage_refresh_spacing * 2, homepage_refresh_spacing);
        mWaveSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWaveSwipeRefreshLayout.setRefreshing(false);
//                mWaveSwipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mFragmentCallBack.SwitchFun(null);
//                        if (mWaveSwipeRefreshLayout!=null){
//                            mWaveSwipeRefreshLayout.setRefreshing(false);
//                        }
//
//
//                    }
//                }, 2000);
            }

            @Override
            public void onLoad() {
                mWaveSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFragmentCallBack.CallBackFun(null);
                        if (mWaveSwipeRefreshLayout!=null){
                            mWaveSwipeRefreshLayout.setLoading(false);
                        }



                    }
                },0);
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

    private void initView(ArrayList<MembercardRecordListBean.DatasBean.MembercardRecordList> list) {

        mListCommonAdapter = new CommonAdapter<MembercardRecordListBean.DatasBean.MembercardRecordList>(mActivity, R.layout.swingcard_record_item, list) {
            @Override
            protected void convert(ViewHolder holder, MembercardRecordListBean.DatasBean.MembercardRecordList membercardRecordList, int position) {
                TextView t1 = holder.getView(R.id.TextItem1);
                TextView t2 = holder.getView(R.id.TextItem2);
                TextView t3 = holder.getView(R.id.TextItem3);


                if (!TextUtils.isEmpty(membercardRecordList.getContentStr().toString())) {
                    t1.setText(membercardRecordList.getContentStr() + "");
                }
                if (!TextUtils.isEmpty(membercardRecordList.getCreaterTime())) {
                    String itme1 = membercardRecordList.getCreaterTime().substring(0, 10);
                    String itme2 = membercardRecordList.getCreaterTime().substring(10, 19);
                    t2.setText(itme1 + "\n" + itme2 + "");
                }
                if (!TextUtils.isEmpty(membercardRecordList.getEmpName())) {
                    t3.setText(membercardRecordList.getEmpName() + "");
                }

            }
        };

        mFragmentrv.setAdapter(mListCommonAdapter);

    }

    //接收刷卡记录Activity 传过来的值
    public void setArguments(ArrayList<MembercardRecordListBean.DatasBean.MembercardRecordList> List) {
        if (membercardRecordList.size() != 0 && List.size() != 0) {
            membercardRecordList.clear();
        }
        this.membercardRecordList.addAll(List);
        initView(List);

        //Log.i(TAG, "碎片接收到的值---》"+membercardRecordList.toString()+"");
    }

    public void showView() {
        if (mTishi != null) {
            mTishi.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentCallBack = (FragmentCallBack) activity;
        mActivity = activity;
    }

    public int getDevider() {
        return devider;
    }

    public void setDevider(int devider) {
        this.devider = devider;
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public RecyclerView getScrollableView() {
        return mFragmentrv;
    }

    /**
     * 回到顶部
     */
    public void changeToTop() {
        mFragmentrv.stopNestedScroll();
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.scrollToPosition(0);
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
