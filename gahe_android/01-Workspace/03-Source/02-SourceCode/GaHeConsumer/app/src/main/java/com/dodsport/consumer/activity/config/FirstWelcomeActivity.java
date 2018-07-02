package com.dodsport.consumer.activity.config;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.consumer.R;
import com.dodsport.consumer.activity.BaseActivity;
import com.dodsport.consumer.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/19 0019.
 * <p>
 * 第一次使用App展示的欢迎页 Activity
 */
public class FirstWelcomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;
    @BindView(R.id.imgeview1)
    ImageView mImgeview1;
    @BindView(R.id.imgeview2)
    ImageView mImgeview2;
    @BindView(R.id.imgeview3)
    ImageView mImgeview3;
    @BindView(R.id.mLinearLyout)
    LinearLayout mMLinearLyout;
    @BindView(R.id.vp_tvCancel)
    TextView mVpTvCancel;//关闭


    private List<WelcomeFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_firstwelcome);
        ButterKnife.bind(this);

        if (mVpTvCancel != null)
            mVpTvCancel.setOnClickListener(this);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(WelcomeFragment.getInstance(R.drawable.welcome));
        mFragmentList.add(WelcomeFragment.getInstance(R.drawable.welcome));
        mFragmentList.add(WelcomeFragment.getInstance(R.drawable.welcome, true, LoginActivity.class));
        mFragmentList.get(2).setOnButtonClickListener(v -> finish());

        mVpGuide.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
        mVpGuide.setOffscreenPageLimit(1);

        //viewpager滑动的监听方法
        mVpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //小圆点监听
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mImgeview1.setImageResource(R.drawable.welcomebacktrue);
                        mImgeview2.setImageResource(R.drawable.welcomeback);
                        mImgeview3.setImageResource(R.drawable.welcomeback);
                        break;
                    case 1:
                        mImgeview2.setImageResource(R.drawable.welcomebacktrue);
                        mImgeview1.setImageResource(R.drawable.welcomeback);
                        mImgeview3.setImageResource(R.drawable.welcomeback);
                        break;
                    case 2:
                        mImgeview1.setImageResource(R.drawable.welcomeback);
                        mImgeview2.setImageResource(R.drawable.welcomeback);
                        mImgeview3.setImageResource(R.drawable.welcomebacktrue);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
