package com.dodsport.consumer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dodsport.consumer.R;
import com.dodsport.consumer.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.consumer.fragment.homefragment.HomeFragment;
import com.dodsport.consumer.fragment.informationfagment.InformationFragment;
import com.dodsport.consumer.fragment.myfragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.header.ClassicHeader;


/**
 * 首页Activity 管理
 *
 * @author Administrator
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.fragment)
    FrameLayout mFragment;
    @BindView(R.id.ivHome)
    ImageView mIvHome;
    @BindView(R.id.main_ivRedPoint)
    ImageView mMainIvRedPoint;
    @BindView(R.id.tvHome)
    TextView mTvHome;
    @BindView(R.id.rlHome)
    RelativeLayout mRlHome;
    @BindView(R.id.ivSend)
    ImageView mIvSend;
    @BindView(R.id.llSend)
    LinearLayout mLlSend;
    @BindView(R.id.ivInformation)
    ImageView mIvInformation;
    @BindView(R.id.tvInformation)
    TextView mTvInformation;
    @BindView(R.id.llInformation)
    LinearLayout mLlInformation;
    @BindView(R.id.ivMy)
    ImageView mIvMy;
    @BindView(R.id.tvMy)
    TextView mTvMy;
    @BindView(R.id.llMy)
    LinearLayout mLlMy;
    @BindView(R.id.mainLinear)
    LinearLayout mMainLinear;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.SmoothRefresh)
    SmoothRefreshLayout mSmoothRefresh;
    @BindView(R.id.AppBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rvBusiness)
    RecyclerView mRvBusiness;

    //首页碎片
    private Fragment currentFragment;
    private static final String HOME = "HOME";
    private static final String INFORMATION = "INFORMATION";
    private static final String MY = "MY";
    private String TAG = "****首页--->";
    private Activity mActivity;
    private List<String> data = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mActivity = this;


        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //首页
        mRlHome.setOnClickListener(this);
        //消息
        mLlInformation.setOnClickListener(this);
        //我的
        mLlMy.setOnClickListener(this);

        changeSelect(R.id.rlHome);
        addInitFragment();

        //创建返回键，并实现打开关/闭监听
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        View headerView = mNavView.getHeaderView(0);
        TextView mBusinessName = headerView.findViewById(R.id.tvBusinessName);
        mRvBusiness.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBusinessName.setText("当前：诺姆瑜伽联盟");

        init();
        for (int i = 0; i < 20; i++) {
            data.add("测试");
        }

        mCommonAdapter = new CommonAdapter<String>(mActivity, R.layout.business_item, data) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        mRvBusiness.setAdapter(mCommonAdapter);

    }

    private void init() {
        mSmoothRefresh.setHeaderView(new ClassicHeader(this));

        mSmoothRefresh.setOnRefreshListener(new RefreshingListenerAdapter() {
            @Override
            public void onRefreshBegin(boolean isRefresh) {

                mFragment.postDelayed(() -> mSmoothRefresh.refreshComplete(), 4000);
            }
        });
    }

    /**
     * 改变 TextView、ImageView 选中颜色
     *
     * @param resId
     */
    private void changeSelect(int resId) {
        mTvHome.setSelected(false);
        mTvInformation.setSelected(false);
        mTvMy.setSelected(false);

        mIvHome.setImageResource(R.drawable.shou_ye);
        mIvInformation.setImageResource(R.drawable.xiao_xi);
        mIvMy.setImageResource(R.drawable.wo_de);
        switch (resId) {
            //首页
            case R.id.rlHome:
                mTvHome.setSelected(true);
                mIvHome.setImageResource(R.drawable.shou_ye_xuan_ze);
                break;
            //消息
            case R.id.llInformation:
                mTvInformation.setSelected(true);
                mIvInformation.setImageResource(R.drawable.xiao_xi_xuan_ze);
                break;
            //我的
            case R.id.llMy:
                mTvMy.setSelected(true);
                mIvMy.setImageResource(R.drawable.wo_de_xuan_ze);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化显示的fragment
     */
    private void addInitFragment() {
        currentFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment, currentFragment, HOME);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        switch (view.getId()) {
            //首页
            case R.id.rlHome:
                fragment = getSupportFragmentManager().findFragmentByTag(HOME);
                if (fragment == null) {
                    fragment = new HomeFragment();
                    fragmentTransaction.add(R.id.fragment, fragment, HOME);
                } else {
                    fragmentTransaction.show(fragment);
                }
                currentFragment = fragment;
                changeSelect(R.id.rlHome);
                //启动滑动
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            //消息
            case R.id.llInformation:
                fragment = getSupportFragmentManager().findFragmentByTag(INFORMATION);
                if (fragment == null) {
                    fragment = new InformationFragment();
                    fragmentTransaction.add(R.id.fragment, fragment, INFORMATION);
                } else {
                    fragmentTransaction.show(fragment);
                }
                currentFragment = fragment;
                changeSelect(R.id.llInformation);
                //禁止滑动
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            //我的
            case R.id.llMy:
                fragment = getSupportFragmentManager().findFragmentByTag(MY);
                if (fragment == null) {
                    fragment = new MyFragment();
                    fragmentTransaction.add(R.id.fragment, fragment, MY);
                } else {
                    fragmentTransaction.show(fragment);
                }
                currentFragment = fragment;
                changeSelect(R.id.llMy);
                //禁止滑动
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        super.onPrepareOptionsMenu(menu);
        menu.clear();

        TextView textView = findViewById(R.id.tvBusinessName);
        menu.add(0, 0, 0, "龙岗分馆").setIcon(R.drawable.shang_jia);
//
//        cb=(CheckBox)findViewById(R.id.my_mms_item_checkbox);
//        menu.add(0, MENU_01, 0, "新建短信").setIcon(R.drawable.menu_new_mms);
//        Log.d(TAG,"cb.getVisibility():"+cb.getVisibility());
//        if(cb.getVisibility()==View.GONE)
//            menu.add(0, MENU_02, 0, "多选").setIcon(R.drawable.mms_multiple);
//        else
//        {
//            menu.add(0, MENU_03, 0, "取消多选").setIcon(R.drawable.mms_multiple);
//            menu.add(0, MENU_04, 0, "选择全部").setIcon(R.drawable.mms_multiple);
//        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }



        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
