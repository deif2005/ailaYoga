package com.dodsport.consumer.activity.memberaboutclass;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.consumer.R;
import com.dodsport.consumer.activity.BaseActivity;
import com.dodsport.consumer.adapter.ListFragmentPagerAdapter;
import com.dodsport.consumer.fragment.homefragment.LeagueClassFragment;
import com.dodsport.consumer.fragment.homefragment.PrivateCoachFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员约课
 *
 * @author Administrator
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MemberAboutClassActivity extends BaseActivity {

    @BindView(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @BindView(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.AboutClassViewPager)
    ViewPager mAboutClassViewPager;

    //标题
    private List<String> beanList = new ArrayList<>();
    private ColorTransitionPagerTitleView simplePagerTitleView;
    private ListFragmentPagerAdapter listStripFragmentPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private LeagueClassFragment mLeagueClassFragment;
    private PrivateCoachFragment mPrivateCoachFragment;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_about_class);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText(R.string.member_AboutClass);

        beanList.add("团课预约");
        beanList.add("私教预约");
        mLeagueClassFragment = new LeagueClassFragment();
        mPrivateCoachFragment = new PrivateCoachFragment();
        fragmentList.add(mLeagueClassFragment);
        fragmentList.add(mPrivateCoachFragment);
        listStripFragmentPagerAdapter = new ListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, beanList);
        mAboutClassViewPager.setAdapter(listStripFragmentPagerAdapter);
        focuTable();
    }


    /**
     * 初始化标题
     */
    private void focuTable() {

        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return beanList == null ? 0 : beanList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(beanList.get(index));
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setNormalColor(Color.parseColor("#474747"));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.home_text_selected));

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutClassViewPager.setCurrentItem(index);
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
                indicator.setColors(getResources().getColor(R.color.home_text_selected));
                indicator.setLineHeight(5);
                indicator.setLineWidth(300);
                return indicator;
            }

        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(mActivity, 100);
            }
        });
        ViewPagerHelper.bind(mMagicIndicator, mAboutClassViewPager);
    }

    @OnClick(R.id.head_ivBack)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;

            default:
                break;
        }
    }
}
