package com.dodsport.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dodsport.R;
import com.dodsport.fragment.informationfragment.InformationFragment;
import com.dodsport.fragment.operatingfloorfragment.OperatingFloorFragment;
import com.dodsport.fragment.vocationalworkfragment.VocationalworkFragment;
import com.dodsport.jpush.ExampleUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


/**
 * 主MainActivity
 *
 * */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.fragment)
    FrameLayout mFragment;
    @Bind(R.id.ivEntertainment)
    ImageView mIvEntertainment;
    @Bind(R.id.tvEntertainment)
    TextView mTvEntertainment;
    @Bind(R.id.llEntertainment)
    LinearLayout mLlEntertainment;
    @Bind(R.id.ivMySelf)
    ImageView mIvMySelf;
    @Bind(R.id.main_ivRedPoint)
    ImageView mMainIvRedPoint;
    @Bind(R.id.tvInformation)
    TextView mTvInformation;
    @Bind(R.id.rlInformation)
    RelativeLayout mRlInformation;
    @Bind(R.id.ivSend)
    ImageView mIvSend;
    @Bind(R.id.llSend)
    LinearLayout mLlSend;
    @Bind(R.id.ivVocational_work)
    ImageView mIvVocationalWork;
    @Bind(R.id.tvVocational_work)
    TextView mTvVocationalWork;
    @Bind(R.id.llVocational_work)
    LinearLayout mLlVocationalWork;
    @Bind(R.id.ivOperatingFloor)
    ImageView mIvOperatingFloor;
    @Bind(R.id.tvOperatingFloor)
    TextView mTvOperatingFloor;
    @Bind(R.id.llOperatingFloor)
    LinearLayout mLlOperatingFloor;
    @Bind(R.id.mainLinear)
    LinearLayout mMainLinear;
    @Bind(R.id.activity_main)
    LinearLayout mActivityMain;


    private static final String ENTERTAINMENT = "ENTERTAINMENT";
    private static final String INFORMATION = "INFORMATION";
    private static final String VOCATIONALWORK = "VOCATIONALWORK";
    private static final String OPERATINGFLOOR = "OPERATINGFLOOR";
    private long exitTime = 0;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取用户Token
//        getToken(true);
        ButterKnife.bind(this);
        iniView();
        registerMessageReceiver();  // used for receive msg
    }

    /**
     * 初始化
     */
    private void iniView() {
//        mLlEntertainment.setOnClickListener(this);      //娱乐
        mRlInformation.setOnClickListener(this);        //消息
        mLlVocationalWork.setOnClickListener(this);     //业务
        mLlOperatingFloor.setOnClickListener(this);     //工作台

        changeSelect(R.id.rlInformation);
        addInitFragment();
        init();
    }


    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init(){
        JPushInterface.init(getApplicationContext());
    }

    /**
     * 改变 TextView、ImageView 选中颜色
     *
     * @param resId
     */
    private void changeSelect(int resId) {
//        mTvEntertainment.setSelected(false);
        mTvInformation.setSelected(false);
        mTvVocationalWork.setSelected(false);
        mTvOperatingFloor.setSelected(false);

//        mIvEntertainment.setImageResource(R.drawable.tab_home_normal);
        mIvMySelf.setImageResource(R.drawable.xiaoxi);
        mIvVocationalWork.setImageResource(R.drawable.lian_meng);
        mIvOperatingFloor.setImageResource(R.drawable.gongzuotai);
        switch (resId) {
//            case R.id.llEntertainment:    //娱乐
//                mTvEntertainment.setSelected(true);
//                mIvEntertainment.setImageResource(R.drawable.tab_home_select);
//                break;
            case R.id.rlInformation:      //消息
                mTvInformation.setSelected(true);
                mIvMySelf.setImageResource(R.drawable.xiaoxi_dianji);
                break;
            case R.id.llVocational_work:   //联盟
                mTvVocationalWork.setSelected(true);
                mIvVocationalWork.setImageResource(R.drawable.lian_meng_dian_ji);
                break;
            case R.id.llOperatingFloor:   //工作台
                mTvOperatingFloor.setSelected(true);
                mIvOperatingFloor.setImageResource(R.drawable.gongzuotai_dianji);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化显示的fragment
     */
    private void addInitFragment() {
        currentFragment = new InformationFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment, currentFragment, ENTERTAINMENT);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        switch (v.getId()) {
//            case R.id.llEntertainment://娱乐
//                fragment = getSupportFragmentManager().findFragmentByTag(ENTERTAINMENT);
//                if (fragment == null) {
//                    fragment = new EntertainmentFragment();
//                    fragmentTransaction.add(R.id.fragment, fragment, ENTERTAINMENT);
//                } else {
//                    fragmentTransaction.show(fragment);
//                }
//                currentFragment = fragment;
//                changeSelect(R.id.llEntertainment);
//                break;
            case R.id.rlInformation:  //消息
                fragment = getSupportFragmentManager().findFragmentByTag(INFORMATION);
                if (fragment == null) {
                    fragment = new InformationFragment();
                    fragmentTransaction.add(R.id.fragment, fragment, INFORMATION);
                } else {
                    fragmentTransaction.show(fragment);
                }
                currentFragment = fragment;
                changeSelect(R.id.rlInformation);
                break;
            case R.id.llVocational_work:   //联盟
                fragment = getSupportFragmentManager().findFragmentByTag(VOCATIONALWORK);
                if (fragment == null) {
                    fragment = new VocationalworkFragment();
                    fragmentTransaction.add(R.id.fragment, fragment, VOCATIONALWORK);
                } else {
                    fragmentTransaction.show(fragment);
                }
                currentFragment = fragment;
                changeSelect(R.id.llVocational_work);
                break;
            case R.id.llOperatingFloor: //工作台
                fragment = getSupportFragmentManager().findFragmentByTag(OPERATINGFLOOR);
                if (fragment == null) {
                    fragment = new OperatingFloorFragment();
                    fragmentTransaction.add(R.id.fragment, fragment, OPERATINGFLOOR);
                } else {
                    fragmentTransaction.show(fragment);
                }
                currentFragment = fragment;
                changeSelect(R.id.llOperatingFloor);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }


    /**
     * 再按一次退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                removeALLActivity();
//                RongManager.getInstance().disconnect();//融云聊天室的管理类
                finish();
                System.exit(0);
                System.gc();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}
