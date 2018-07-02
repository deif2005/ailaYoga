package com.dodsport.activity.archives;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.BaseMemberListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.LogUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.popupwindow.EditArchivesPopupWindow;
import com.dodsport.weight.waveswiperefreshlayout.WaveSwipeRefreshLayout;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 档案管理
 *
 * @author Administrator
 */

public class ArchivesActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.archivesRecyclerView)
    RecyclerView mArchivesRecyclerView;
    @Bind(R.id.wsrlyout)
    WaveSwipeRefreshLayout mWsrlyout;
    @Bind(R.id.LoadingView)
    LoadingView mLoadingView;
    private int page = 1;
    private Activity mActivity;


    private CommonAdapter<BaseMemberListBean.DatasBean.ResponseMemberList> mCommonAdapter;
    private String TAG = "****会员管理---";
    private PopupWindow editArchivesPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archives);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mLoadingView.showLoadingView();
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvOK.setText("添加客户");
        mHeadTvTitle.setText("档案管理");
        mArchivesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        queryMemberList();
        ini();
    }

    private void ini() {
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
                        if (mWsrlyout!=null){
                            mWsrlyout.setRefreshing(false);

                        }
                        queryMemberList();
                        if (mActivity!=null){
                            //activity.pullUpdate();
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


    /**
     * 获取会员列表
     */
    private void queryMemberList() {
        String storeId = SPUtils.getUserDataBean(this).getStoreId();
        String pageSize = "8";
        OperatingFloorRequest.queryMemberListByStoreId(storeId, page + "", pageSize, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    LogUtils.i(TAG, "获取列表成功--->" + response.get().toString() + "");
                    BaseMemberListBean baseMemberListBean = JSON.parseObject(response.get(), BaseMemberListBean.class);
                    if (!baseMemberListBean.getResult().getCode().equals("0")) {

                        return;
                    }
                    adapter(baseMemberListBean.getDatas().getResponseMemberList());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                LogUtils.i(TAG, "获取列表失败--->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 加载适配器
     *
     * @param baseMemberList
     */
    private void adapter(List<BaseMemberListBean.DatasBean.ResponseMemberList> baseMemberList) {

        mCommonAdapter = new CommonAdapter<BaseMemberListBean.DatasBean.ResponseMemberList>(this, R.layout.archives_item, baseMemberList) {
            @Override
            protected void convert(ViewHolder holder, final BaseMemberListBean.DatasBean.ResponseMemberList baseMember, int position) {
                LinearLayout llType = holder.getView(R.id.llType);  //状态
                TextView tvItem1 = holder.getView(R.id.tvItem1);
                TextView tvItem2 = holder.getView(R.id.tvItem2);
                TextView tvItem3 = holder.getView(R.id.tvItem3);
                TextView tvItem4 = holder.getView(R.id.tvItem4);    //编辑
                TextView tvItem5 = holder.getView(R.id.tvItem5);    //删除
                tvItem1.setText(baseMember.getNickName());
                String birthday = baseMember.getBirthday();
                if (!TextUtils.isEmpty(birthday)) {
                    tvItem2.setText(baseMember.getBirthday().substring(0, 10) + "\t\n" + baseMember.getPhoneNum());
                }
                //标签
                if (!TextUtils.isEmpty(baseMember.getMemberTags()) && !baseMember.getMemberTags().equals("null")) {
                    if (baseMember.getMemberTags().equals("0")) {
                        llType.setVisibility(View.GONE);
                    } else {
                        llType.setVisibility(View.VISIBLE);
                        int integer = Integer.parseInt(baseMember.getMemberTags());
                        tvItem3.setText(UrlInterfaceManager.memberTags[integer]);
                    }
                }

//                Log.i(TAG, "数据详情--->"+baseMember.toString()+"");
                //编辑
                tvItem4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editArchivesPopupWindow = new EditArchivesPopupWindow(ArchivesActivity.this, mHeadIvBack, true, baseMember);
                    }
                });

                //删除
                tvItem5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };
        mArchivesRecyclerView.setAdapter(mCommonAdapter);

        mLoadingView.showContentView();
    }


    @OnClick({R.id.head_ivBack, R.id.head_tvOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_tvOK:        //添加新用户
                editArchivesPopupWindow = new EditArchivesPopupWindow(ArchivesActivity.this, mHeadIvBack, true, null);
                break;
            default:
                break;
        }
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(String event) {
        /**
         * 添加会员 编辑会员
         */
        if (event.equals("dismiss"))   //微信支付的成功结果反馈
        {

        }
    }


    /**
     * Set whether this window is touch modal or if outside touches will be sent
     * to
     * other windows behind it.
     * <p>
     * 让popupWindowView窗口以外的点击事件 传递给下一层布局
     */
    public static void setPopupWindowTouchModal(PopupWindow popupWindow,
                                                boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {

            method = PopupWindow.class.getDeclaredMethod("setTouchModal",
                    boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
