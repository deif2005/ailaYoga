package com.dodsport.activity.cardcoupons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
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
import com.dodsport.eventBus.ObjectEvent;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.CaliBratIonFragment;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.FragmentCallBack;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.LargessFragment;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.RechargeFragment;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.ReplaceCardFragment;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.StopOrStartCardFragment;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.TransferOwnershipFragment;
import com.dodsport.model.MemberInfoBean;
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.model.PricesListBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.TransformationUtils;
import com.dodsport.view.LoadingView;
import com.dodsport.weight.CircleImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.R.id.magicIndicator;
import static com.yanzhenjie.nohttp.NoHttp.getContext;

/**
 * 会员卡操作
 */
public class OperateMemberCardActivity extends BaseActivity implements FragmentCallBack {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.cvUserHead)
    CircleImageView mCvUserHead;
    @Bind(R.id.tvUserName)
    TextView mTvUserName;
    @Bind(R.id.tvPhone)
    TextView mTvPhone;
    @Bind(R.id.tvSex)
    TextView mTvSex;
    @Bind(R.id.tvBirthday)
    TextView mTvBirthday;
    @Bind(R.id.llMemberInfo)
    LinearLayout mLlMemberInfo;
    @Bind(magicIndicator)
    MagicIndicator mMagicIndicator;
    @Bind(R.id.rvOperateBt)
    RecyclerView mRvOperateBt;
    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.layout_price)
    LinearLayout mLayoutPrice;
    @Bind(R.id.OperateLoadingView)
    LoadingView mOperateLoadingView;


    private String memberCardId = "";//当前会员卡ID
    private String memberId = "";    //会员ID
    private String memberPone = "";  //会员电话
    private String TAG = "***会员卡操作--";
    private ColorTransitionPagerTitleView simplePagerTitleView;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private CommonNavigator commonNavigator = new CommonNavigator(getContext());
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean; //选中的会员卡
    private CommonAdapter<PricesListBean.DatasBean.ListBean> commonAdapter;
    private int operate = 0;
    private String PriceId = "";//选中的价格ID
    private List<String> functionName = new ArrayList<>();  //功能模块名称
    private CommonAdapter<String> mCommonAdapter;
    private int Clickedposition = 0;
    private int in = 0;     //标记显示那张会员卡的价格阶梯
    private boolean refurbish = true;
    private Fragment currentFragment;
    private static final String RECHARGE = "RECHARGE";//充值
    private static final String LARGESS = "LARGESS";  //赠送
    private static final String CALIBRATION = "CALIBRATION";//校准
    private static final String REPLACECARD = "REPLACECARD";//换卡
    private static final String TRANSFEROWNERSHIP = "TRANSFEROWNERSHIP";//过户
    private static final String STOPCARD = "STOPCARD";//停卡
    private CommonNavigatorAdapter mCommonNavigatorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_operate_member_card);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mOperateLoadingView.showLoadingView();
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("操作");
        //会员卡信息
        memberCardId = getIntent().getStringExtra("MemberCardId");
        memberId = getIntent().getStringExtra("MemberId");
        memberPone = getIntent().getStringExtra("MemberPone");
        functionName.add("充值");
        functionName.add("赠送");
        functionName.add("校准");
        functionName.add("换卡");
        functionName.add("过户");
        functionName.add("停卡");
        mRvOperateBt.setLayoutManager(new GridLayoutManager(this, 3));

        try {
            getMemberInfo();
            getMemberCardList();
            getFunction();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.head_ivBack)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
        }
    }

    /**
     * 获取会员信息
     */
    public void getMemberInfo() {
        String businessId = SPUtils.getUserDataBean(this).getBusinessId();
        OperatingFloorRequest.queryMemberinfoByPhoneNum(memberPone, businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    //Log.i(TAG, "获取用户信息成功--->" + response.get().toString() + "");
                    MemberInfoBean memberInfoBean = JSON.parseObject(response.get(), MemberInfoBean.class);
                    if (!memberInfoBean.getResult().getCode().equals("0")) {
                        switch (memberInfoBean.getResult().getCode()) {
                            case "5002":
                                ToastUtils.showToCenters(OperateMemberCardActivity.this, "没有找到该会员!", 1000);
                                break;
                        }
                        return;
                    }
                    mLlMemberInfo.setVisibility(View.VISIBLE);
                    //会员信息
                    MemberInfoBean.DatasBean.BaseMemberBean baseMember = memberInfoBean.getDatas().getBaseMember();
                    if (!TextUtils.isEmpty(baseMember.getMemberHead())) {
                        Picasso.with(OperateMemberCardActivity.this).load(baseMember.getMemberHead())
                                .resize(400, 400)
                                .config(Bitmap.Config.RGB_565)
                                .error(R.drawable.user_head)
                                .placeholder(R.drawable.user_head)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .centerCrop()
                                .transform(TransformationUtils.zipImage(mCvUserHead)).into(mCvUserHead);
                    }
                    mTvUserName.setText(baseMember.getMemberName());
                    mTvPhone.setText("电话：" + baseMember.getPhoneNum());
                    if (baseMember.getSex() == 1) {
                        mTvSex.setText("性别：男");
                    } else if (baseMember.getSex() == 2) {
                        mTvSex.setText("性别：女");
                    }
                    if (!TextUtils.isEmpty(baseMember.getBirthday())) {
                        mTvBirthday.setText("生日：" + baseMember.getBirthday().substring(0, 10));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
               mOperateLoadingView.showErrorView(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       initView();
                   }
               });
                ToastUtils.showToCenters(OperateMemberCardActivity.this, "查找失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取会员的会员卡列表
     */
    public void getMemberCardList() {
        OperatingFloorRequest.queryMembercardrelationListByMemberId(memberId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "获取会员卡列表成功--->" + response.get().toString() + "");
                MemberMyCardListBean memberMyCardListBean = JSON.parseObject(response.get(), MemberMyCardListBean.class);
                if (!memberMyCardListBean.getResult().getCode().equals("0")) {
                    ToastUtils.showToCenters(OperateMemberCardActivity.this, "获取会员卡列表失败!", 800);
                    return;
                }
                List<String> CardName = new ArrayList<String>();
                if (memberMyCardListBean.getDatas().getMembercardrelationList()==null){
                    return;
                }
                if (memberMyCardListBean.getDatas().getMembercardrelationList().size() != 0) {

                    for (int i = 0; i < memberMyCardListBean.getDatas().getMembercardrelationList().size(); i++) {
                        CardName.add(memberMyCardListBean.getDatas().getMembercardrelationList().get(i).getMembcardName());
                        if (memberCardId.equals(memberMyCardListBean.getDatas().getMembercardrelationList().get(i).getId())) {
                            //mFragmentContainerHelper.handlePageSelected(i, true);
                            in = i;
                            mMemberCardBean = memberMyCardListBean.getDatas().getMembercardrelationList().get(i);
                            if (refurbish) {
                                addInitFragment();  //添加碎片
                            }
                        }
                    }
                    showCardName(CardName, memberMyCardListBean.getDatas().getMembercardrelationList());
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取会员卡列表失败--->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });

        if (mOperateLoadingView!=null){
            mOperateLoadingView.showContentView();
        }
    }

    /**
     * 显示当前会员的卡名称列表
     */
    private void showCardName(final List<String> beanList, final List<MemberMyCardListBean.DatasBean.MembercardrelationListBean> membercardrelationList) {

        mCommonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return beanList == null ? 0 : beanList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(beanList.get(index));
                simplePagerTitleView.setTextSize(12);
                simplePagerTitleView.setNormalColor(Color.parseColor("#474747"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FFFFFF"));

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMemberCardBean = membercardrelationList.get(index);
                        mFragmentContainerHelper.handlePageSelected(index);
                        post(Clickedposition);

                    }
                });

                return simplePagerTitleView;
            }

            /**
             * 设置底部线条
             * @param context
             * @return
             */
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.login_text_margin_top);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = (navigatorHeight - 2 * borderWidth) / 2;

                indicator.setLineHeight(lineHeight);     //背景的高度
                indicator.setRoundRadius(lineHeight / 4);
                indicator.setYOffset(borderWidth + 6);//距离底部
                indicator.setColors(Color.parseColor("#49ACEB"));
                return indicator;
            }

        };

        commonNavigator.setAdapter(mCommonNavigatorAdapter);
        mCommonNavigatorAdapter.notifyDataSetChanged();

        mMagicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(mMagicIndicator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {

            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 10); //边距
            }
        });

        mFragmentContainerHelper.handlePageSelected(in);
        if (!refurbish) {
            mFragmentContainerHelper.handlePageSelected(in, true);
        }

    }


    /**
     * 初始化显示的fragment
     */
    private void addInitFragment() {
        currentFragment = new RechargeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("MemberCardBean", mMemberCardBean);
        currentFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment, currentFragment, RECHARGE);
        fragmentTransaction.commit();
        post(Clickedposition);
    }

    /**
     * 显示功能模块
     */
    public void getFunction() {
        mCommonAdapter = new CommonAdapter<String>(this, R.layout.function_item_view, functionName) {
            @Override
            protected void convert(ViewHolder holder, String text, final int position) {
                LinearLayout llFunctionbg = holder.getView(R.id.llFunctionbg);
                TextView textView = holder.getView(R.id.textView);
                textView.setText(text);
                if (position == Clickedposition) {
                    llFunctionbg.setBackground(getResources().getDrawable(R.drawable.shape_login_style));
                } else {
                    llFunctionbg.setBackground(getResources().getDrawable(R.drawable.shape_cancel_style));
                }

                llFunctionbg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Clickedposition = position;
                        mCommonAdapter.notifyDataSetChanged();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Fragment fragment;
                        Bundle bundle = new Bundle();
                        if (currentFragment != null) {
                            fragmentTransaction.hide(currentFragment);
                        }

                        switch (position) {
                            case 0:     //充值
                                fragment = getSupportFragmentManager().findFragmentByTag(RECHARGE);
                                if (fragment == null) {
                                    fragment = new RechargeFragment();
                                    fragmentTransaction.add(R.id.fragment, fragment, RECHARGE);
                                    bundle.putSerializable("MemberCardBean", mMemberCardBean);
                                } else {
                                    fragmentTransaction.show(fragment);
                                }
                                currentFragment = fragment;
                                break;
                            case 1:     //赠送
                                fragment = getSupportFragmentManager().findFragmentByTag(LARGESS);
                                if (fragment == null) {
                                    fragment = new LargessFragment();
                                    fragmentTransaction.add(R.id.fragment, fragment, LARGESS);
                                    bundle.putSerializable("MemberCardBean", mMemberCardBean);
                                } else {
                                    fragmentTransaction.show(fragment);
                                }
                                currentFragment = fragment;
                                break;
                            case 2:     //校准
                                fragment = getSupportFragmentManager().findFragmentByTag(CALIBRATION);
                                if (fragment == null) {
                                    fragment = new CaliBratIonFragment();
                                    fragmentTransaction.add(R.id.fragment, fragment, CALIBRATION);
                                    bundle.putSerializable("MemberCardBean", mMemberCardBean);
                                } else {
                                    fragmentTransaction.show(fragment);
                                }
                                currentFragment = fragment;
                                break;
                            case 3:     //换卡
                                fragment = getSupportFragmentManager().findFragmentByTag(REPLACECARD);
                                if (fragment == null) {
                                    fragment = new ReplaceCardFragment();
                                    fragmentTransaction.add(R.id.fragment, fragment, REPLACECARD);
                                    bundle.putSerializable("MemberCardBean", mMemberCardBean);
                                } else {
                                    fragmentTransaction.show(fragment);
                                }
                                currentFragment = fragment;
                                break;
                            case 4:     //过户
                                fragment = getSupportFragmentManager().findFragmentByTag(TRANSFEROWNERSHIP);
                                if (fragment == null) {
                                    fragment = new TransferOwnershipFragment();
                                    fragmentTransaction.add(R.id.fragment, fragment, TRANSFEROWNERSHIP);
                                    bundle.putSerializable("MemberCardBean", mMemberCardBean);
                                } else {
                                    fragmentTransaction.show(fragment);
                                }
                                currentFragment = fragment;
                                break;
                            case 5:     //停卡
                                fragment = getSupportFragmentManager().findFragmentByTag(STOPCARD);
                                if (fragment == null) {
                                    fragment = new StopOrStartCardFragment();
                                    fragmentTransaction.add(R.id.fragment, fragment, STOPCARD);
                                    bundle.putSerializable("MemberCardBean", mMemberCardBean);

                                } else {
                                    fragmentTransaction.show(fragment);
                                }
                                currentFragment = fragment;
                                break;
                        }
                        currentFragment.setArguments(bundle);
                        fragmentTransaction.commit();
                        post(Clickedposition);
                    }
                });
            }
        };

        mRvOperateBt.setAdapter(mCommonAdapter);

    }


    /**
     * 传值给对应的Fragment
     */
    private void post(int i) {

        ObjectEvent object = new ObjectEvent();

        switch (i) {
            case 0:
                object.setType("Recharge");
                break;
            case 1:
                object.setType("Largess");
                break;
            case 2:
                object.setType("CaliBratIon");
                break;
            case 3:
                object.setType("Recharge");
                break;
            case 4:
                object.setType("TransferOwnership");
                break;
            case 5:
                object.setType("StopOrStartCard");
                break;
        }
        object.setMemberCardBean(mMemberCardBean);
        EventBus.getDefault().post(object);
    }

    @Override
    public void SwitchFun(Bundle arg) {
        Log.i(TAG, "碎片调用Activity---》SwitchFun");
    }

    @Override
    public void CallBackFun(Bundle arg) {
        String ok = (String) arg.get("Operating");

        //充值成功 || 过户成功
        if (!TextUtils.isEmpty(ok) && ok.equals("Recharge") || ok.equals("TO") || ok.equals("Stop") || ok.equals("Reissue") || ok.equals("CaliBratIon") || ok.equals("Largess")) {
            refurbish = false;
            memberCardId = mMemberCardBean.getId();
            try {
                getMemberCardList();//获取卡列表数据
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ok.equals("Stop")){
                post(Clickedposition);
            }
        }
    }
}
