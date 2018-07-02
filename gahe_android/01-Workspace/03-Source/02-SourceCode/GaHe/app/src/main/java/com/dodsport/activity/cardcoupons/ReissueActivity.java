package com.dodsport.activity.cardcoupons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.model.PricesListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 换卡
 * */
public class ReissueActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvCardName1)
    TextView mTvCardName1;
    @Bind(R.id.text1)
    TextView mText1;
    @Bind(R.id.tvText1)
    TextView mTvText1;
    @Bind(R.id.text2)
    TextView mText2;
    @Bind(R.id.tvText2)
    TextView mTvText2;
    @Bind(R.id.text3)
    TextView mText3;
    @Bind(R.id.tvText3)
    TextView mTvText3;
    @Bind(R.id.tvCardName2)
    TextView mTvCardName2;
    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.tvItem2)
    TextView mTvItem2;
    @Bind(R.id.tvItem3)
    TextView mTvItem3;
    @Bind(R.id.Price_RecyclerView)
    RecyclerView mPriceRecyclerView;
    @Bind(R.id.text4)
    TextView mText4;
    @Bind(R.id.tvText4)
    TextView mTvText4;
    @Bind(R.id.text5)
    TextView mText5;
    @Bind(R.id.tvText5)
    TextView mTvText5;
    @Bind(R.id.text6)
    TextView mText6;
    @Bind(R.id.tvText6)
    TextView mTvText6;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;
    @Bind(R.id.etMoney)
    EditText mEtMoney;
    @Bind(R.id.tvMoney)
    TextView mTvMoney;
    @Bind(R.id.rlMoney)
    RelativeLayout mRlMoney;

    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean;
    private CommonAdapter<PricesListBean.DatasBean.ListBean> commonAdapter;
    private String TAG = "***换卡Activity--";
    private int operate = 0;
    private String PriceId = "";//选中的价格ID
    private Intent intent;
    private String price = "";
    private Integer times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reissue);
        ButterKnife.bind(this);
        intent = getIntent();
        mMemberCardBean = (MemberMyCardListBean.DatasBean.MembercardrelationListBean) intent.getSerializableExtra("object");
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("换卡");
        mPriceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTvCardName2.setText(intent.getStringExtra("ReplaceCardName"));
        getNetData();//获取卡价格阶梯

        mTvCardName1.setText(mMemberCardBean.getMembcardName());
        if (mMemberCardBean.getMembcardType() == 1) {        //次卡
            mText1.setText("剩余次数：");
            mTvText1.setText(mMemberCardBean.getTimes() + " 次");
            times = mMemberCardBean.getTimes();
        } else {
            mText1.setText("剩余天数：");
            mTvText1.setText(mMemberCardBean.getDays() + " 天");
            times = mMemberCardBean.getDays();
        }


        mEtMoney.addTextChangedListener(new TextWatcher() {

            int cou = 0;
            int selectionEnd = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cou = before + count;
                cou = mEtMoney.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (cou == 0) {
                        mRlMoney.setVisibility(View.GONE);
                    }
                    if (cou >= 1) {
                        if (!TextUtils.isEmpty(price)) {
                            int i = Integer.parseInt(mEtMoney.getText().toString());
                            int i1 = Integer.parseInt(price.substring(0, price.indexOf(".")));
                            mRlMoney.setVisibility(View.VISIBLE);
                            mTvMoney.setText((i1 - i) + "");//计算差价
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @OnClick({R.id.head_ivBack, R.id.llConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llConfirm:
                if (mRlMoney.getVisibility() == View.VISIBLE) {
                    int i = Integer.parseInt(mEtMoney.getText().toString());
                    int i1 = Integer.parseInt(price.substring(0, price.indexOf(".")));
                    i1 = i1+1;
                    if (i >= i1){
                        ToastUtils.showToCenters(this,"输入的折金额不能大于选中价格!",1000);
                        return;
                    }
                }
                get();
                break;
        }
    }


    /**
     *  * Token 用户通行口令
     * relationId	String		是	会员卡关系表id
     * memberCardId	String		是	会员卡id
     * rechargegradId	String		是	充值梯度id
     * creator	String		是
     * discountPrice	String		是	折算价值(换卡折算)
     * priceSpread	String		是	差价(换卡补差价)
     * times	integer		是	剩余次数/天数
     * */
    private void get() {
        String creator = SPUtils.getUserDataBean(this).getId();
        String id = mMemberCardBean.getId();
        String memberCardId = intent.getStringExtra("ReplaceCardId");
        String discountPrice = mEtMoney.getText().toString();
        if (TextUtils.isEmpty(discountPrice)){
            discountPrice = "0";
        }
        String priceSpread = mTvMoney.getText().toString();
        if (TextUtils.isEmpty(priceSpread)){
            priceSpread = "0";
        }        //Log.i(TAG, "get: 传参--》"+id+"\t"+memberCardId+"\t"+PriceId+"\t"+discountPrice+"\t"+priceSpread+"\t"+times+"");
        OperatingFloorRequest.memberCardChange(id, memberCardId, PriceId, creator,discountPrice,priceSpread,times, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "换卡成功--->" + response.get().toString());
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (resultBean.getCode().equals("0")){
                    ToastUtils.showToCenters(ReissueActivity.this,"更卡成功!",1000);
                    Intent intent = new Intent();
                    setResult(RESULT_FIRST_USER, intent);
                    finish();
                }else {
                    ToastUtils.showToCenters(ReissueActivity.this,"换卡失败!",1000);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(ReissueActivity.this,"换卡失败!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 获取价格阶梯List
     */
    private void getNetData() {
        if (mMemberCardBean == null) {
            return;
        }
        String replaceCardId = intent.getStringExtra("ReplaceCardId");
        //Log.i(TAG, "获取价格列表传参***-->"+mMemberCardId+"");
        OperatingFloorRequest.getRechargegradList(replaceCardId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "成功: " + response.get().toString() + "");
                    PricesListBean pricesListBean = JSON.parseObject(response.get(), PricesListBean.class);
                    if (!pricesListBean.getResult().getCode().equals("0")) {
                        return;
                    }
                    loadOnAdapter(pricesListBean.getDatas().getList());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "失败: " + response.get().toString() + "");
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
            protected void convert(ViewHolder holder, final PricesListBean.DatasBean.ListBean listBean, final int position) {
                TextView tvItem1 = holder.getView(R.id.tvItem1);
                TextView tvItem2 = holder.getView(R.id.tvItem2);
                TextView tvItem3 = holder.getView(R.id.tvItem3);
                RadioButton RadioButton1 = holder.getView(R.id.RadioButton1);
                RadioButton1.setVisibility(View.VISIBLE);
                tvItem3.setVisibility(View.GONE);
                View view = holder.getView(R.id.view);
                if (listBean.getTimes() == 0) {
                    tvItem1.setText(listBean.getMonths() + "");
                    mTvItem1.setText("月数");
                } else {
                    tvItem1.setText(listBean.getTimes() + "");
                    mTvItem1.setText("次数");
                }
                tvItem2.setText(listBean.getNominalAmount() + "");
                if (position == (list.size() - 1)) {
                    view.setVisibility(View.GONE);
                }
                if (position == operate) {
                    PriceId = listBean.getId(); //价格Id
                    price = listBean.getNominalAmount();
                    RadioButton1.setChecked(true);
                } else {
                    RadioButton1.setChecked(false);
                }

                //选中 卡价格阶梯
                RadioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PriceId = listBean.getId(); //价格Id
                        operate = position;
                        price = listBean.getNominalAmount();
                        commonAdapter.notifyDataSetChanged();

                        if (!TextUtils.isEmpty(price) && price.length() > 1) {
                            int i = Integer.parseInt(mEtMoney.getText().toString());
                            int i1 = Integer.parseInt(price.substring(0, price.indexOf(".")));
                            mRlMoney.setVisibility(View.VISIBLE);
                            mTvMoney.setText((i1 - i) + "");//计算差价
                        }
                    }
                });
            }
        };

        mPriceRecyclerView.setAdapter(commonAdapter);
    }
}
