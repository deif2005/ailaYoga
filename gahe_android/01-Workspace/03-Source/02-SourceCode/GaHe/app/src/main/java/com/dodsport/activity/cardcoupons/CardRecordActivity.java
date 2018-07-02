package com.dodsport.activity.cardcoupons;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.cardcoupons.membercardreplace.DoubleClickListener;
import com.dodsport.activity.cardcoupons.membercardreplace.ListViewPager;
import com.dodsport.activity.cardcoupons.membercardreplace.PagerSlidingTabStrip;
import com.dodsport.activity.cardcoupons.membercardreplace.ScreenUtils;
import com.dodsport.adapter.ListFragmentPagerAdapter;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.CardRecordFragment;
import com.dodsport.fragment.operatingfloorfragment.membercardoperate.FragmentCallBack;
import com.dodsport.model.MemberInfoBean;
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.model.MembercardRecordListBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.R.id.btCardStatus;
import static com.yanzhenjie.nohttp.NoHttp.getContext;

/**
 * 会员卡-记录
 */
public class CardRecordActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener,FragmentCallBack{


    @Bind(R.id.scroll_Viewpager)
    ListViewPager mScrollViewpager;
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
    @Bind(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.tvItem2)
    TextView mTvItem2;
    @Bind(R.id.tvItem3)
    TextView mTvItem3;
    @Bind(R.id.tvItem4)
    TextView mTvItem4;
    @Bind(R.id.tvItem5)
    TextView mTvItem5;
    @Bind(R.id.tvItem6)
    TextView mTvItem6;
    @Bind(R.id.scroll_imageBanner_layout)
    RelativeLayout mScrollImageBannerLayout;
    @Bind(R.id.scroll_toolbar)
    Toolbar mScrollToolbar;
    @Bind(R.id.scroll_pagerSlidingTabStrip)
    PagerSlidingTabStrip mScrollPagerSlidingTabStrip;
    @Bind(R.id.scroll_collapsingToolbarLayout)
    CollapsingToolbarLayout mScrollCollapsingToolbarLayout;
    @Bind(R.id.scroll_appbar)
    AppBarLayout mScrollAppbar;
    @Bind(R.id.scroll_coordinatorLayout)
    CoordinatorLayout mScrollCoordinatorLayout;
    @Bind(R.id.scroll_swipeRefreshLayout)
    SwipeRefreshLayout mScrollSwipeRefreshLayout;
    @Bind(R.id.Layout)
    LinearLayout mLayout;
    @Bind(R.id.include)
    LinearLayout mInclude;
    @Bind(R.id.llayout)
    LinearLayout mLlayout;
    @Bind(R.id.magicIndicator2)
    MagicIndicator mMagicIndicator2;
    @Bind(R.id.llayout2)
    LinearLayout mLlayout2;
    @Bind(R.id.llTitleName)
    LinearLayout mLlTitleName;
    @Bind(R.id.tvMemberCardName)
    TextView mTvMemberCardName;
    @Bind(R.id.tvMemberCardId)
    TextView mTvMemberCardId;
    @Bind(btCardStatus)
    Button mBtCardStatus;
    @Bind(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @Bind(R.id.tvMemberCard)
    TextView mTvMemberCard;
    @Bind(R.id.Card_loadView)
    LoadingView mCardLoadView;
    @Bind(R.id.etSearchPhone)
    EditText mEtSearchPhone;
    @Bind(R.id.llSearch)
    LinearLayout mLlSearch;
    @Bind(R.id.view)
    View mView;


    private Activity mActivity;
    private CardRecordFragment mRecordFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();
    private ListFragmentPagerAdapter listStripFragmentPagerAdapter;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private int devider = 0;
    private int curTab = 0;
    private int tabScroll;
    private int scrollOffset = 4444;
    private int scrollOffsetSquare = 0;
    private int scrollOffsetAttention = 0;

    private String memberCardId = "";//当前会员卡ID
    private String memberId = "";    //会员ID
    private String memberPone = "";  //会员电话
    private String TAG = "***会员卡操作--";
    private ColorTransitionPagerTitleView simplePagerTitleView;
    private ColorTransitionPagerTitleView simplePagerTitleView2;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private CommonNavigator commonNavigator = new CommonNavigator(getContext());
    private FragmentContainerHelper mFragmentContainerHelper2 = new FragmentContainerHelper();
    private CommonNavigator commonNavigator2 = new CommonNavigator(getContext());
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean; //选中的会员卡
    private int in = 0;
    private int Index = 0;
    private boolean refurbish = true;
    private boolean pull = false;
    private int page = 1;
    private int coun = 1;
    private ArrayList<MemberMyCardListBean.DatasBean.MembercardrelationListBean> membercardList = new ArrayList<>();//会员卡集合
    private ArrayList<MembercardRecordListBean.DatasBean.MembercardRecordList> mMembercardRecordLists = new ArrayList<>();
    private boolean Search = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_record);
        ButterKnife.bind(this);

        mActivity = this;
        //会员卡信息
        memberCardId = getIntent().getStringExtra("MemberCardId");
        memberId = getIntent().getStringExtra("MemberId");
        memberPone = getIntent().getStringExtra("MemberPone");

        initView();
    }

    private void initView() {
        mCardLoadView.showLoadingView();
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("记录");
        mLlayout2.setVisibility(View.GONE);
        mInclude.setVisibility(View.VISIBLE);
        mLlMemberInfo.setVisibility(View.VISIBLE);
        mScrollPagerSlidingTabStrip.setVisibility(View.GONE);
        mLlTitleName.setVisibility(View.VISIBLE);

        mScrollCollapsingToolbarLayout.setEnabled(false);
        /**这是tab的高度，用来设置此高度后tab可以停靠*/
//        int height = (int) getResources().getDimension(R.dimen.grid_view_item_height);

//        int height = GaHeApplication.Height / 5;
        int height = 180;
        setViewHeight(mScrollToolbar, ViewGroup.LayoutParams.MATCH_PARENT, height);
        tabScroll = ScreenUtils.getScreenWidth(CardRecordActivity.this) / 5;
        getMemberInfo();
        if (!TextUtils.isEmpty(memberId)) {
            getMemberCardList();
        }

    }


    /**
     * 刷卡记录
     *
     * @param mList
     */
    public void resolveViewPager(List<MemberMyCardListBean.DatasBean.MembercardrelationListBean> mList) {
        if (listStripFragmentPagerAdapter != null) {
            return;
        }
        /**设置了tab的样式*/
        mScrollPagerSlidingTabStrip.setShouldExpand(true);
        mScrollPagerSlidingTabStrip.setDividerColor(Color.WHITE);
        mScrollPagerSlidingTabStrip.setIndicatorColor(Color.TRANSPARENT);
        mScrollPagerSlidingTabStrip.setUnderlineColor(getResources().getColor(R.color.home_text_selected));    //底线颜色
        mScrollPagerSlidingTabStrip.setTabTextSelectColor(getResources().getColor(R.color.home_text_selected));

        for (int i = 0; i < mList.size(); i++) {

            CardRecordFragment mRecordFragment = new CardRecordFragment(); //视频Fragment
            mRecordFragment.setDevider(devider);
            mRecordFragment.setSwipeRefreshLayout(mScrollSwipeRefreshLayout);
            fragmentList.add(mRecordFragment);
            tabList.add(mList.get(i).getMembcardName());
        }
        try {
            mScrollViewpager.setAdapter(fragmentPagerAdapter);
        } catch (Exception e) {
            setListener();
            e.printStackTrace();
        }
        mScrollViewpager.setOffscreenPageLimit(mList.size());
        mScrollViewpager.setSwipeRefreshLayout(mScrollSwipeRefreshLayout);
        listStripFragmentPagerAdapter = new ListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, tabList);
        initFragmentPager();
    }

    public void initFragmentPager() {

        mScrollViewpager.setAdapter(listStripFragmentPagerAdapter);

        mScrollPagerSlidingTabStrip.setViewPager(mScrollViewpager);
        mScrollPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                /**不同的recyclerView的列表对应的位置不同，需要处理对应的顶部banner是否隐藏*/
                curTab = i;
                mScrollAppbar.removeOnOffsetChangedListener(CardRecordActivity.this);
                int scrollHeight = (i == 0) ? scrollOffsetSquare : scrollOffsetAttention;

                if (scrollHeight == -mScrollAppbar.getTotalScrollRange()) {
                    CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)
                            mScrollAppbar.getLayoutParams()).getBehavior();

                    for (int i1 = 0; i1 < fragmentList.size(); i1++) {
                        CardRecordFragment fragment = (CardRecordFragment) fragmentList.get(i1);

                        behavior.onNestedPreScroll(mScrollCoordinatorLayout, mScrollAppbar,

                                fragment.getScrollableView(), 0, mScrollAppbar.getTotalScrollRange(), new int[]{0, 0});
                    }

                    //behavior.onNestedPreScroll(scrollCoordinatorLayout, mScrollAppbar,

//                            fragment2.getScrollableView(), 0, scrollAppbar.getTotalScrollRange(), new int[]{0, 0});

                    onOffsetChanged(mScrollAppbar, mScrollAppbar.getTotalScrollRange());
                }

                mScrollSwipeRefreshLayout.setEnabled(false);
                mScrollAppbar.addOnOffsetChangedListener(CardRecordActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mScrollPagerSlidingTabStrip.setDoubleClickListener(new DoubleClickListener() {
            @Override
            public void onClick(int position) {
                curTab = position;
                resolveToTop();
            }
        });

        mScrollViewpager.setCurrentItem(0);

        mScrollAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                //如果折叠后固定，那么以下判断则是折叠完成时为true
                if (i != scrollOffset) {
                    if (curTab == 0) {
                        scrollOffsetSquare = i;
                    } else {
                        scrollOffsetAttention = i;
                    }
                    scrollOffset = i;
                    mScrollSwipeRefreshLayout.setEnabled(i == 0);
                    mScrollViewpager.setIsTop(i == 0);
                    float alpha = 1 - (float) Math.abs(i) / (float) appBarLayout.getTotalScrollRange() * 1.0f;
                    mScrollImageBannerLayout.setAlpha(alpha);
                    resolveStripTabStyle(alpha, (int) ((1 - alpha) * tabScroll));

                    if (alpha == 1.0) {
                        return;
                    }
                    if (alpha <= 0.59) {
                        mFragmentContainerHelper2.handlePageSelected(Index);
                        mTvMemberCard.setVisibility(View.GONE);
                        mLlayout2.setVisibility(View.VISIBLE);
                        mScrollToolbar.setVisibility(View.VISIBLE);
                        mLlayout.setVisibility(View.INVISIBLE);
                    }
                    //处理共享排行榜的焦点抢占问题
                    if (alpha == 0.0) {
                        mLlTitleName.setVisibility(View.GONE);
                        mView.setVisibility(View.GONE);

                    }
                    if (alpha > 0.98 || alpha == 1.0) {
                        mTvMemberCard.setVisibility(View.VISIBLE);
                    }
                    if (alpha > 0.60) {
                        mLlayout.setVisibility(View.VISIBLE);
                        mScrollToolbar.setVisibility(View.GONE);
                        mLlayout2.setVisibility(View.GONE);
                    }
                    if (alpha > 0.07) {
                        mLlTitleName.setVisibility(View.VISIBLE);
                        mView.setVisibility(View.VISIBLE);
                    }

                    //Log.i(TAG, "测试--2222--》" + alpha + "");
                }

            }
        });

        /**Viewpager左右滑动监听*/
        mScrollViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int index, float v, int i1) {
            }

            @Override
            public void onPageSelected(int index) {
                Index = index;
                mFragmentContainerHelper.handlePageSelected(index);
                mFragmentContainerHelper2.handlePageSelected(index);
                mMemberCardBean = membercardList.get(index);
                showCardInfo();
                CardRecordFragment fragment = (CardRecordFragment) fragmentList.get(index);
                if (fragment.mCommonAdapter == null){
                    page = 1;
                    getSwingCardRecord();
                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }


    private void setListener() {

        mScrollAppbar.addOnOffsetChangedListener(this);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragmentList.get(arg0);
            }
        };

        //刷新
        mScrollSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mScrollSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pull = true;
                        getSwingCardRecord();
                        if (mScrollSwipeRefreshLayout != null)
                            mScrollSwipeRefreshLayout.setRefreshing(false);

                        mScrollSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);


            }
        });


    }

    /**
     * 双击回到顶部
     */
    public void resolveToTop() {
        mRecordFragment.mFragmentrv.scrollToPosition(0);

//        if (curTab==0.0) {
//            refreshToTop1();
//        } else {
//            refreshToTop2();
//        }
    }
//
//    private void refreshToTop2() {
//        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)
//                scrollAppbar.getLayoutParams()).getBehavior();
//        behavior.onNestedPreScroll(scrollCoordinatorLayout, scrollAppbar,
//                fragmentList.get(1).getScrollableView(), 0, scrollAppbar.getTotalScrollRange(), new int[]{0, 0});
//        onOffsetChanged(scrollAppbar, scrollAppbar.getTotalScrollRange());
//        fragmentList.get(1).changeToTop();
//    }
//
//    private void refreshToTop1() {
//        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)
//                scrollAppbar.getLayoutParams()).getBehavior();
//        behavior.onNestedPreScroll(scrollCoordinatorLayout, scrollAppbar,
//                fragmentList.get(0).getScrollableView(), 0, scrollAppbar.getTotalScrollRange(), new int[]{0, 0});
//        onOffsetChanged(scrollAppbar, scrollAppbar.getTotalScrollRange());
//        fragmentList.get(0).changeToTop();
//    }


    //上下滑动监听
    private void resolveStripTabStyle(float alpha, int margin) {

        mScrollPagerSlidingTabStrip.setDividerColor(Color.argb((int) (alpha * 255), 217, 217, 217));
//        mReturnrelative.setBackgroundColor(Color.argb((int) (alpha * 255), 217, 217, 217));

//        顶部导航栏滑动时颜色渐变   236,63,45
//        mLlayout2.setBackgroundColor(Color.argb((int) ((1 - alpha) * 255), 236, 63, 45));

        mScrollPagerSlidingTabStrip.setIndicatorColor(Color.argb((int) ((1 - alpha) * 255), 251, 151, 53));
        LinearLayout linearLayout = mScrollPagerSlidingTabStrip.getTabsContainer();
        linearLayout.setPadding(margin, linearLayout.getPaddingTop(), margin, linearLayout.getPaddingBottom());
        linearLayout.requestLayout();
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
                                ToastUtils.showToCenters(CardRecordActivity.this, "没有找到该会员!", 1000);
                                break;
                        }
                        return;
                    }
                    mLlMemberInfo.setVisibility(View.VISIBLE);
                    //会员信息
                    MemberInfoBean.DatasBean.BaseMemberBean baseMember = memberInfoBean.getDatas().getBaseMember();
                    if (!TextUtils.isEmpty(baseMember.getMemberHead())) {
                        Picasso.with(CardRecordActivity.this).load(baseMember.getMemberHead())
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
                ToastUtils.showToCenters(CardRecordActivity.this, "查找失败,请稍后重试!", 1000);
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
                //Log.i(TAG, "获取会员卡列表成功--->" + response.get().toString() + "");
                MemberMyCardListBean memberMyCardListBean = JSON.parseObject(response.get(), MemberMyCardListBean.class);
                if (!memberMyCardListBean.getResult().getCode().equals("0")) {
                    ToastUtils.showToCenters(CardRecordActivity.this, "获取会员卡列表失败!", 800);
                    return;
                }
                List<String> CardName = new ArrayList<String>();
                if (memberMyCardListBean.getDatas().getMembercardrelationList().size() != 0) {

                    int s =0;
                    for (int i = 0; i < memberMyCardListBean.getDatas().getMembercardrelationList().size(); i++) {
                        CardName.add(memberMyCardListBean.getDatas().getMembercardrelationList().get(i).getMembcardName());
                        if (memberCardId.equals(memberMyCardListBean.getDatas().getMembercardrelationList().get(i).getId())) {
                            //mFragmentContainerHelper.handlePageSelected(i, true);
                            in = i;
                            s = s+1;
                            mMemberCardBean = null;
                            mMemberCardBean = memberMyCardListBean.getDatas().getMembercardrelationList().get(i);
                        }
                    }
                        resolveViewPager(memberMyCardListBean.getDatas().getMembercardrelationList());  //添加碎片
                    if (s == 0){
                        mMemberCardBean = null;
                        mMemberCardBean = memberMyCardListBean.getDatas().getMembercardrelationList().get(0);
                    }

                    showCardName(CardName, memberMyCardListBean.getDatas().getMembercardrelationList());
//                    page = page + 1;
                    getSwingCardRecord();   //提一次获取十条
                    showCardInfo();

                    mFragmentContainerHelper2.handlePageSelected(Index);
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
    }


    /**
     * 获取当前会员卡的刷卡记录
     */
    private void getSwingCardRecord() {
        String id = "fd2fce96-bff3-41a4-a877-cb3da34374c4";
        //Log.i(TAG, "获取记录\t"+page+"\t会员卡ID--->"+id+"");mMemberCardBean.getId()
        OperatingFloorRequest.queryRecordListBycardId(id, page, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "获取记录成功--->" + response.get().toString());
                    MembercardRecordListBean mMemberCardRecord = JSON.parseObject(response.get(), MembercardRecordListBean.class);
                    if (!mMemberCardRecord.getResult().getCode().equals("0")){
                        ToastUtils.showToCenters(mActivity,"查找失败!",800);
                        return;
                    }
                    CardRecordFragment fragment = (CardRecordFragment) fragmentList.get(Index);
                    if (page != 1 && mMemberCardRecord.getDatas().getMembercardRecordList().size() == 0){
                        page = page-1;
                        fragment.showView();
                        return;
                    }
                    if (page!=1 && mMemberCardRecord.getDatas().getMembercardRecordList().size() != 0){
                        mMembercardRecordLists.addAll(mMemberCardRecord.getDatas().getMembercardRecordList());
                    }else if (page == 1){
                        mMembercardRecordLists.clear();
                        mMembercardRecordLists.addAll(mMemberCardRecord.getDatas().getMembercardRecordList());
                    }

                    fragment.setArguments(mMembercardRecordLists);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取记录失败--->" + response.toString());
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    /**
     * 搜索会员信息
     */
    private void getSearchInfo() {
        String phone = mEtSearchPhone.getText().toString();
        String businessId = SPUtils.getUserDataBean(this).getBusinessId();
        OperatingFloorRequest.queryMemberinfoByPhoneNum(phone, businessId, new OnResponseListener<String>() {
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
                                ToastUtils.showToCenters(CardRecordActivity.this, "没有找到该会员!", 1000);
                                break;
                        }
                        return;
                    }
                    //会员信息
                    MemberInfoBean.DatasBean.BaseMemberBean baseMember = memberInfoBean.getDatas().getBaseMember();
                    //重启当前Activity
                    Intent intent = getIntent();
                    intent.putExtra("MemberId",baseMember.getId());
                    intent.putExtra("MemberCardId","null");
                    intent.putExtra("MemberPone",baseMember.getPhoneNum());
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade, R.anim.hold);

//                    if (!TextUtils.isEmpty(baseMember.getMemberHead())) {
//                        Picasso.with(CardRecordActivity.this).load(baseMember.getMemberHead())
//                                .resize(400, 400)
//                                .config(Bitmap.Config.RGB_565)
//                                .error(R.drawable.user_head)
//                                .placeholder(R.drawable.user_head)
//                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                                .centerCrop()
//                                .transform(TransformationUtils.zipImage(mCvUserHead)).into(mCvUserHead);
//                    }
//                    memberId = baseMember.getId();
//                    mTvUserName.setText(baseMember.getMemberName());
//                    mTvPhone.setText("电话：" + baseMember.getPhoneNum());
//                    if (baseMember.getSex() == 1) {
//                        mTvSex.setText("性别：男");
//                    } else if (baseMember.getSex() == 2) {
//                        mTvSex.setText("性别：女");
//                    }
//                    if (!TextUtils.isEmpty(baseMember.getBirthday())) {
//                        mTvBirthday.setText("生日：" + baseMember.getBirthday().substring(0, 10));
//                    }

                    //获取会员的会员卡列表
                    //getMemberCardList();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(CardRecordActivity.this, "查找失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });

        mEtSearchPhone.setText("");
    }


    /**
     * 展示会员卡信息
     */
    private void showCardInfo() {
        mTvMemberCardName.setText(mMemberCardBean.getMembcardName());
        mTvMemberCardId.setText(mMemberCardBean.getOpencardSerialId());

        if (mMemberCardBean.getCardStatus() == 1) {
            mBtCardStatus.setText("使用中");
            mBtCardStatus.setBackground(getDrawable(R.drawable.shape_card_status_green_style));
        } else if (mMemberCardBean.getCardStatus() == 2) {
            mBtCardStatus.setText("已停卡");
            mBtCardStatus.setBackground(getDrawable(R.drawable.shape_card_status_red_style));
        }

        if (mMemberCardBean.getTimes() == 0) {
            mTvItem1.setText(mMemberCardBean.getDays() + "");
            mTvItem4.setText("天数");
        } else {
            mTvItem1.setText(mMemberCardBean.getTimes() + "");
            mTvItem4.setText("次数");
        }

        if (!TextUtils.isEmpty(mMemberCardBean.getCreateTime())) {
            mTvItem2.setText(mMemberCardBean.getCreateTime().substring(0, 10));
        }

        if (!TextUtils.isEmpty(mMemberCardBean.getValidityTime())) {
            mTvItem3.setText(mMemberCardBean.getValidityTime().substring(0, 10));
        } else {
            mTvItem3.setText("无期限");
        }

        if (mCardLoadView != null) {
            mCardLoadView.showContentView();
        }
    }

    /**
     * 显示当前会员的卡名称列表
     */
    private void showCardName(final List<String> beanList, final List<MemberMyCardListBean.DatasBean.MembercardrelationListBean> membercardrelationList) {
       if (membercardList.size()!=0){
           membercardList.clear();
       }
        membercardList.addAll(membercardrelationList);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
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
                        Index = index;
                        mMemberCardBean = membercardrelationList.get(index);
                        mFragmentContainerHelper.handlePageSelected(index);
                        mScrollViewpager.setCurrentItem(index);
                        showCardInfo();
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

        });

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
        mScrollViewpager.setCurrentItem(in);
        Index = in;
//        if (refurbish) {
//            mFragmentContainerHelper.handlePageSelected(in, true);
//        }


        commonNavigator2.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return beanList == null ? 0 : beanList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                simplePagerTitleView2 = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView2.setText(beanList.get(index));
                simplePagerTitleView2.setTextSize(12);
                simplePagerTitleView2.setNormalColor(Color.parseColor("#474747"));
                simplePagerTitleView2.setSelectedColor(Color.parseColor("#FFFFFF"));

                simplePagerTitleView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Index = index;
                        mMemberCardBean = membercardrelationList.get(index);
                        mFragmentContainerHelper2.handlePageSelected(index);
                        mScrollViewpager.setCurrentItem(index);
                        showCardInfo();
                        resolveToTop();
                    }
                });

                return simplePagerTitleView2;
            }

            /**
             * 设置底部线条
             * @param context
             * @return
             */
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator2 = new LinePagerIndicator(context);
                float navigatorHeight2 = context.getResources().getDimension(R.dimen.login_text_margin_top);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = (navigatorHeight2 - 2 * borderWidth) / 2;

                indicator2.setLineHeight(lineHeight);     //背景的高度
                indicator2.setRoundRadius(lineHeight / 4);
                indicator2.setYOffset(borderWidth + 6);//距离底部
                indicator2.setColors(Color.parseColor("#49ACEB"));
                return indicator2;
            }

        });

        mMagicIndicator2.setNavigator(commonNavigator2);
        mFragmentContainerHelper2.attachMagicIndicator(mMagicIndicator2);
        LinearLayout titleContainer2 = commonNavigator2.getTitleContainer(); // must after setNavigator
        titleContainer2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer2.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 10); //边距
            }
        });

        mFragmentContainerHelper2.handlePageSelected(Index);

    }


    private void setViewHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (null == layoutParams)
            return;

        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        //如果折叠后固定，那么以下判断则是折叠完成时为true
        if (i != scrollOffset) {
            if (curTab == 0) {
                scrollOffsetSquare = i;
            } else {
                scrollOffsetAttention = i;
            }
            scrollOffset = i;
            mScrollSwipeRefreshLayout.setEnabled(i == 0);
            mScrollViewpager.setIsTop(i == 0);
            float alpha = 1 - (float) Math.abs(i) / (float) appBarLayout.getTotalScrollRange() * 1.0f;
            mScrollImageBannerLayout.setAlpha(alpha);
            resolveStripTabStyle(alpha, (int) ((1 - alpha) * tabScroll));

            if (alpha == 1.0) {
                return;
            }
            if (alpha <= 0.59) {
                mFragmentContainerHelper2.handlePageSelected(Index);
                mTvMemberCard.setVisibility(View.GONE);
                mLlayout2.setVisibility(View.VISIBLE);
                mScrollToolbar.setVisibility(View.VISIBLE);
                mLlayout.setVisibility(View.INVISIBLE);
            }
            //处理共享排行榜的焦点抢占问题
            if (alpha == 0.0) {
                mLlTitleName.setVisibility(View.GONE);
                mView.setVisibility(View.GONE);

            }
            if (alpha > 0.98 || alpha == 1.0) {
                mTvMemberCard.setVisibility(View.VISIBLE);
            }
            if (alpha > 0.60) {
                mLlayout.setVisibility(View.VISIBLE);
                mScrollToolbar.setVisibility(View.GONE);
                mLlayout2.setVisibility(View.GONE);
            }
            if (alpha > 0.07) {
                mLlTitleName.setVisibility(View.VISIBLE);
                mView.setVisibility(View.VISIBLE);
            }

            //Log.i(TAG, "测试--2222--》" + alpha + "");
        }
    }

    @OnClick({R.id.head_ivBack, R.id.llSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llSearch:
                if (TextUtils.isEmpty(mEtSearchPhone.getText().toString())) {
                    ToastUtils.showToCenters(this, "请输入要搜索的手机号!", 1000);
                    return;
                }
//                if (mEtSearchPhone.getText().toString().equals(mMemberCardBean.getPhoneNum())){
//                    ToastUtils.showToCenters(this, "!", 1000);
//                    return;
//                }
                getSearchInfo();
                break;
        }
    }

    @Override
    public void SwitchFun(Bundle arg) {
        page = 1;
        getSwingCardRecord();
    }

    @Override
    public void CallBackFun(Bundle arg) {
        page = page + 1;
        //碎片上滑加载
        getSwingCardRecord();
    }
}
