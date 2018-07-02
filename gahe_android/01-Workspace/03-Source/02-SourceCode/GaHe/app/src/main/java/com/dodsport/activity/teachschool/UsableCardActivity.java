package com.dodsport.activity.teachschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.CourseAndCardListBean;
import com.dodsport.model.CourseListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
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
 * 可用卡券
 */
public class UsableCardActivity extends BaseActivity {

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
    @Bind(R.id.btConservation)
    Button mBtConservation;
    @Bind(R.id.btCancel)
    Button mBtCancel;
    @Bind(R.id.tvCourseName)
    TextView mTvCourseName;
    @Bind(R.id.llErrorDisplay)
    LinearLayout mLlErrorDisplay;
    @Bind(R.id.cancel)
    RelativeLayout mCancel;



    private CommonAdapter<CourseAndCardListBean.DatasBean.CourseAndCardList> mCommonAdapter;
    private List<String> data = new ArrayList<>();
    private Activity mActivity;
    private CourseListBean.DatasBean.CourseList courseList; //课程对象
    private String TAG = "***课程关联卡--";
    private List<CourseAndCardListBean.DatasBean.CourseAndCardList> andCardList = new ArrayList<>(); //卡用卡券集合
    private List<CourseAndCardListBean.DatasBean.CourseAndCardList> andCardList2 = new ArrayList<>(); //卡用卡券集合


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usable_card);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("可用卡券");
        mTvItem1.setText("卡名");
        mTvItem2.setText("类型");
        mTvItem3.setText("启用");
        mPriceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        courseList = (CourseListBean.DatasBean.CourseList) intent.getSerializableExtra("object");
        mTvCourseName.setText(courseList.getCourseName());

        getListCourseAndCard();

    }


    /**
     * 获取关联卡列表
     */
    private void getListCourseAndCard() {
        if (courseList == null) {
            return;
        }
        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        String id = courseList.getId();
        OperatingFloorRequest.getListCourseAndCard(businessId, id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    CourseAndCardListBean courseAndCardBean = JSON.parseObject(response.get(), CourseAndCardListBean.class);
                    if (!courseAndCardBean.getResult().getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "获取失败,请稍后重试!", 1000);
                        return;
                    }
                    andCardList = courseAndCardBean.getDatas().getCourseAndCardList();
                    if (andCardList.size() != 0 || andCardList != null) {
                        andCardList2.clear();
                        mPriceRecyclerView.setVisibility(View.VISIBLE);
                        mLlErrorDisplay.setVisibility(View.GONE);
                        mCancel.setVisibility(View.VISIBLE);
                    } else {
                        mCancel.setVisibility(View.GONE);
                        mPriceRecyclerView.setVisibility(View.GONE);
                        mLlErrorDisplay.setVisibility(View.VISIBLE);
                        return;
                    }
                    adapter();

                    //Log.i(TAG, "获取列表成功--->" + response.get().toString() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "获取失败,请稍后重试!", 1000);
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
        mCommonAdapter = new CommonAdapter<CourseAndCardListBean.DatasBean.CourseAndCardList>(this, R.layout.priceslist_item, andCardList) {
            @Override
            protected void convert(ViewHolder holder, final CourseAndCardListBean.DatasBean.CourseAndCardList courseAndCard, final int position) {

                TextView tvItem1 = holder.getView(R.id.tvItem1);
                TextView tvItem2 = holder.getView(R.id.tvItem2);
                TextView tvItem3 = holder.getView(R.id.tvItem3);
                tvItem3.setVisibility(View.GONE);
                final RadioButton mRadioButton2 = holder.getView(R.id.RadioButton2);
                mRadioButton2.setVisibility(View.VISIBLE);

                tvItem1.setText(courseAndCard.getMembcardName());
                if (courseAndCard.getMembcardType() == 1) {
                    tvItem2.setText(UrlInterfaceManager.cardType[1]);
                } else {
                    tvItem2.setText(UrlInterfaceManager.cardType[2]);
                }

                //一是未启用  二是启用
                if (TextUtils.isEmpty(courseAndCard.getCourseId()) && TextUtils.isEmpty(andCardList.get(position).getStatus())) {
                    andCardList.get(position).setStatus("1");
                } else if (!TextUtils.isEmpty(courseAndCard.getCourseId()) && TextUtils.isEmpty(andCardList.get(position).getStatus())) {
                    andCardList.get(position).setStatus("2");
                }
                if (andCardList.get(position).getStatus().equals("1")) {
                    mRadioButton2.setChecked(false);
                } else {
                    data.add(courseAndCard.getMembcardId());
                    mRadioButton2.setChecked(true);
                }

                mRadioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (andCardList.get(position).getStatus().equals("1")) {
                            andCardList.get(position).setStatus("2");
                        } else {
                            andCardList.get(position).setStatus("1");
                        }
                        if (data.size() != 0) {
                            data.clear();
                        }
                        mCommonAdapter.notifyDataSetChanged();

                    }
                });
            }
        };

        mPriceRecyclerView.setAdapter(mCommonAdapter);
    }


    @OnClick({R.id.head_ivBack, R.id.btConservation, R.id.btCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.btConservation:   //保存
                Conservation();
                break;
            case R.id.btCancel:         //取消
                andCardList.clear();
                data.clear();
                andCardList.addAll(andCardList2);
                mCommonAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 提交操作
     */
    private void Conservation() {
        String cardIdStr = "";
        for (int i = 0; i < data.size(); i++) {
            if (i == (data.size() - 1)) {
                cardIdStr = cardIdStr + data.get(i);
            } else {
                cardIdStr = cardIdStr + data.get(i) + ",";
            }
        }
        String id = courseList.getId();
        //Log.i(TAG, "提交参数--->" + cardIdStr + "\tid-->" + id + "");
        OperatingFloorRequest.addCourseAndCard(cardIdStr, id, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "保存成功!", 1000);

                //Log.i(TAG, "提交成功--->" + response.get().toString() + "");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "保存失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
