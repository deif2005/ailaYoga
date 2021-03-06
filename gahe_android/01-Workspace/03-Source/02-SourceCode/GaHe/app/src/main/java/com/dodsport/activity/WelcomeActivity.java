package com.dodsport.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;

import com.dodsport.R;
import com.dodsport.activity.config.Config;
import com.dodsport.activity.config.FirstWelcomeActivity;
import com.dodsport.activity.config.SpUtils;
import com.dodsport.service.LocalService;
import com.dodsport.service.RemoteService;
import com.dodsport.utils.FileUtils;
import com.dodsport.utils.SPUtils;

/**
 * Created by Administrator on 2016-10-19.
 * <p>
 * 欢迎页面 判断是否是第一次使用本App
 */

public class WelcomeActivity extends BaseActivity  {

    private static final int TO_WELCOME = 0x1;
    private static final int TO_MAIN = 0x2;
    private static final int TO_SERVICE = 3;


    static final String TAG = "WelcomeActivity";



    private Handler handler;
    private boolean start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_welcome);

        //获取用户Token
//        getToken(true);


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TO_WELCOME: {
                        startActivity(new Intent(WelcomeActivity.this, FirstWelcomeActivity.class));
                        finish();
                        break;
                    }
                    case TO_MAIN: {
                        redirectTo();
                        break;
                    }
                    case TO_SERVICE: {
                        //开启双线程守护
                        WelcomeActivity.this.startService(new Intent(WelcomeActivity.this, LocalService.class));
                        WelcomeActivity.this.startService(new Intent(WelcomeActivity.this, RemoteService.class));
                        break;
                    }
                }
            }
        };


    }


    /**
     * 跳转到 MainActivity
     */
    private void redirectTo() {
        String phoneNum = SPUtils.getUserDataBean(WelcomeActivity.this).getPhoneNum();
        if (TextUtils.isEmpty(phoneNum)) {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return;
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra(MainActivity.EXTRA_USEFUL_STRING, splashLibrarys.usefulString());
            startActivity(intent);
            //设置Activity过渡动画
            overridePendingTransition(R.anim.fade, R.anim.hold);
            finish();
            return;
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        //context为当前Activity上下文
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            handler.sendEmptyMessage(TO_SERVICE);   //开启Service
            pi = pm.getPackageInfo(getPackageName(), 0);
            //如果是第一次启动，进入功能引导页
            if(pi!=null)
            {
                if (pi.versionCode> SpUtils.getInt(this, Config.VERSION_CODE)) {
                    final String dirFile = FileUtils.DEFAULT_FILE_NEW;  //本地文件夹<byte[]数据集合>
                    if (!dirFile.isEmpty()){
                        FileUtils.deleteFolderFile(dirFile, true);
                    }
                    handler.sendEmptyMessage(TO_WELCOME);
                    SpUtils.putInt(WelcomeActivity.this, Config.VERSION_CODE, pi.versionCode);
                } else {
                    start =false;
                    Thread.sleep(800);
                    //进去欢迎页面
                    handler.sendEmptyMessageDelayed(TO_MAIN, 1000);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        //发送消息开启双进程
       /* new Thread(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(TO_SERVICE);
            }
        }).start();*/



    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

//        Toast.makeText(this, "加载完毕", Toast.LENGTH_SHORT).show();
    }






}
