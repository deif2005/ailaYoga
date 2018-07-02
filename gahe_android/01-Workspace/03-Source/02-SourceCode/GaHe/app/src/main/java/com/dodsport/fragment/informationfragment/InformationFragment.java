package com.dodsport.fragment.informationfragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.GaHeApplication;
import com.dodsport.R;
import com.dodsport.adapter.ListFragmentPagerAdapter;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.UserDataBean;
import com.dodsport.utils.SPUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息模块
 * @author Administrator
 */
public class InformationFragment extends BaseFragment {

    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @Bind(R.id.ViewPager)
    ViewPager mViewPager;

    private Activity mActivity;
    private View mView;
    private String TAG = "****消息模块+";
    private UserDataBean.DatasBean.ResponseEmployeeBean mUserDataBean;
    private List<String> beanList = new ArrayList<>();
    private ListFragmentPagerAdapter listStripFragmentPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private SystemNotifyFragment mSystemNotifyFragment; //系统通知
    private LeagueNotifyFragment mLeagueNotifyFragment; //联盟通知
    private InteriorNotifyFragment mInteriorNotifyFragment; //内部通知
    private ColorTransitionPagerTitleView simplePagerTitleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_information, container, false);
            mActivity = getActivity();
        }
        ButterKnife.bind(this, mView);
        mUserDataBean = SPUtils.getUserDataBean(getContext());
        initView();

        return mView;
    }

    /**初始化*/
    private void initView() {
        beanList.add("系统通知");
        ///beanList.add("联盟通知");
        beanList.add("内部通知");
        mSystemNotifyFragment = new SystemNotifyFragment();
        mLeagueNotifyFragment = new LeagueNotifyFragment();
        //mInteriorNotifyFragment = new InteriorNotifyFragment();
        fragmentList.add(mSystemNotifyFragment);
        fragmentList.add(mLeagueNotifyFragment);
        //fragmentList.add(mInteriorNotifyFragment);

        listStripFragmentPagerAdapter = new ListFragmentPagerAdapter(getFragmentManager(), fragmentList, beanList);
        mViewPager.setAdapter(listStripFragmentPagerAdapter);
        focuTable();
    }

    /**
     * 初始化标题
     */
    private void focuTable() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return beanList == null ? 0 : beanList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                View customLayout = LayoutInflater.from(context).inflate(R.layout.infofragment_type, null);
                final TextView title = (TextView) customLayout.findViewById(R.id.tvTitle);
                title.setText(beanList.get(index));

                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        //页面停留
                        //titleText.setTextColor(getResources().getColor(R.color.home_text_selected));
                        title.setTextColor(getResources().getColor(R.color.home_text_selected));

                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        //titleText.setTextColor(getResources().getColor(R.color.text_color));
                        title.setTextColor(getResources().getColor(R.color.home_text_normal));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
//                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
//                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
//                        titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
//                        titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                    }
                });


                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;

//                simplePagerTitleView = new ColorTransitionPagerTitleView(context);
//                simplePagerTitleView.setText(beanList.get(index));
//                simplePagerTitleView.setTextSize(17);
//                simplePagerTitleView.setNormalColor(Color.parseColor("#474747"));
//                simplePagerTitleView.setSelectedColor(Color.parseColor("#49ACEB"));
//                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mViewPager.setCurrentItem(index);
//                    }
//                });
//                return simplePagerTitleView;
            }

            /**
             * 设置底部线条
             * @param context
             * @return
             */
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#49ACEB"));
                indicator.setLineHeight(5);
                indicator.setLineWidth(GaHeApplication.Width/2);
                return indicator;
            }

        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {

            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(),60);
            }
        });
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
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