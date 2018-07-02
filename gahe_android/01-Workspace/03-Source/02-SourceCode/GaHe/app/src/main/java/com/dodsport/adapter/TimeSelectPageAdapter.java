package com.dodsport.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 * 首页viewPager适配器
 */

public class TimeSelectPageAdapter extends FragmentPagerAdapter {


    private List<String> beanList;


    public TimeSelectPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public TimeSelectPageAdapter(FragmentManager fm, List<String> beanList) {
        super(fm);
        this.beanList = beanList;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
//                fragment = new FocusFragment();
//                fragment = new TimeSelectFragment();       //日期
//                Bundle bundle1 = new Bundle();
//                fragment.setArguments(bundle1);
                break;
            case 1:
//                fragment = new TimeSelectFragment2();          //上下午
                Bundle bundle2 = new Bundle();
                fragment.setArguments(bundle2);
                break;
//            default:
//                fragment = new PlaybackFragment();  //回放
//                Bundle bundle3 = new Bundle();
//                fragment.setArguments(bundle3);
//                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return beanList.get(position);
    }
}
