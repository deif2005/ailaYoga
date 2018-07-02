package com.dodsport.activity.cardcoupons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.MemberCardListBean;
import com.dodsport.model.PricesListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.LoadingView;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPricesActivity extends BaseActivity {

    @Bind(R.id.RecyclerView)
    RecyclerView RecyclerView;
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.evNumber)
    EditText mEvNumber;
    @Bind(R.id.editText)
    EditText mEditText;
    @Bind(R.id.llAdd)
    LinearLayout mLlAdd;
    @Bind(R.id.card_loadView)
    LoadingView mCardLoadView;
    @Bind(R.id.tvTime)
    TextView mTvTime;
    @Bind(R.id.tvItem1)
    TextView mTvItem1;

    private MemberCardListBean.DatasBean.MemberCardList mMemberCard;

    private CommonAdapter<PricesListBean.DatasBean.ListBean> commonAdapter;
    private String TAG = "***会员卡价格梯度--";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_prices);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("设定价格");
        mCardLoadView.showLoadingView();
        //1.如果设置只输入数字
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

//        此时只会弹出数字输入框，符号点击后不会输入到文字框中
//        2.只能输入数字和小数点
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        Intent intent = getIntent();
        mMemberCard = (MemberCardListBean.DatasBean.MemberCardList) intent.getSerializableExtra("CardInfo");
        if (mMemberCard.getMembcardType().equals("1")){
            mTvTime.setText("充值次数");
            mTvItem1.setText("次数");
        }else {
            mTvTime.setText("充值月数");
            mTvItem1.setText("月数");
        }


        getNetData();
        RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    /**
     * 获取价格阶梯List
     */
    private void getNetData() {
        if (mMemberCard == null) {
            return;
        }
        String mMemberCardId = mMemberCard.getId();
        OperatingFloorRequest.getRechargegradList(mMemberCardId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "成功: " + response.get().toString() + "");
                    PricesListBean pricesListBean = JSON.parseObject(response.get(), PricesListBean.class);
                    if (!pricesListBean.getResult().getCode().equals("0")) {
                        CardLoadView();
                        return;
                    }
                    if (mCardLoadView != null) {
                        mCardLoadView.showContentView();
                    }
                    loadOnAdapter(pricesListBean.getDatas().getList());

                } catch (Exception e) {
                    e.printStackTrace();
                    CardLoadView();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "失败: " + response.get().toString() + "");
                CardLoadView();
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    //加载适配器
    private void loadOnAdapter(final List<PricesListBean.DatasBean.ListBean> list) {
        commonAdapter = new CommonAdapter<PricesListBean.DatasBean.ListBean>(this, R.layout.priceslist_item, list) {
            @Override
            protected void convert(ViewHolder holder, PricesListBean.DatasBean.ListBean listBean, int position) {
                TextView tvItem1 = holder.getView(R.id.tvItem1);
                TextView tvItem2 = holder.getView(R.id.tvItem2);
                TextView tvItem3 = holder.getView(R.id.tvItem3);
                View view = holder.getView(R.id.view);
                tvItem1.setText(listBean.getTimes() + "");
                tvItem2.setText(listBean.getNominalAmount() + "");
                if (position == (list.size() - 1)) {
                    view.setVisibility(View.GONE);
                }

                //删除 卡价格阶梯
                tvItem3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };

        RecyclerView.setAdapter(commonAdapter);
    }


    //加载失败
    private void CardLoadView() {
        if (mCardLoadView != null)
            mCardLoadView.showErrorView(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getNetData();
                }
            });
    }

    @OnClick({R.id.head_ivBack, R.id.llAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llAdd:        //添加
                if (TextUtils.isEmpty(mEvNumber.getText().toString()) || mEditText.getText().toString().equals("0")) {
                    ToastUtils.showToCenters(this, "请输入正确的次数", 1000);
                } else if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    ToastUtils.showToCenters(this, "请输入金额", 1000);
                } else {
                    addPrice();
                }
                break;
        }
    }

    /**
     * 添加价格标签
     * *membcardId	String		是	会员卡Id
     * times	integer			充值次数(次卡充值)
     * months	integer			充值月数(期限卡充值)
     * nominalAmount	String		是	面值金额
     * creator	String		提交人ID
     */
    private void addPrice() {
        String Number = mEvNumber.getText().toString();
        int times =0;
        int months = 0;
        //充值的是次卡
        if (mMemberCard.getMembcardType().equals("1")){
            times = Integer.parseInt(Number);
        }else {     //充值的是期限卡
           months = Integer.parseInt(Number);
        }

        String nominalAmount = mEditText.getText().toString();
        String mMemberCardId = mMemberCard.getId();     //会员卡Id
        String creator = SPUtils.getUserDataBean(this).getId();

        OperatingFloorRequest.addRechargegrad(mMemberCardId, times, months, nominalAmount, creator, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "添加价格成功-->" + response.toString());
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")) {
                        ToastUtils.showToCenters(SetPricesActivity.this, "添加失败", 800);
                        return;
                    }
                    getNetData();
                    mEvNumber.setText("");
                    mEditText.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToCenters(SetPricesActivity.this, "添加失败", 800);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "添加价格失败-->" + response.toString());
                ToastUtils.showToCenters(SetPricesActivity.this, "添加失败", 800);
            }

            @Override
            public void onFinish(int what) {

            }
        });
        hintKbTwo();
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
