package com.dodsport.consumer;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.dodsport.consumer.activity.BaseActivity;
import com.dodsport.consumer.activity.MainActivity;
import com.dodsport.consumer.extension.BaseNetProvider;
import com.dodsport.consumer.retrofit.NetMgr;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/10/27
 *
 */

public class GaHeConsumerApplication extends Application {

    public static int Width;
    public static int Height;
    public static DisplayMetrics mDisplayMetrics;
    public static List<Integer> weekList = new ArrayList<>();
    private static Context mContext;    //上下文
    private GaHeConsumerApplication mApplication;

    // 保存所有打开的 Activity 用于存放所有启动的Activity的集合
    public List<BaseActivity> mActivities;
    public GaHeConsumerApplication getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mContext = this;

        /**Activity集合*/
        mActivities = new ArrayList<>();

        /**获取屏幕宽高*/
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Width = wm.getDefaultDisplay().getWidth();
        Height = wm.getDefaultDisplay().getHeight();
        mDisplayMetrics = getResources().getDisplayMetrics();

        // 程序崩溃时触发线程  以下用来捕获程序崩溃异常
//        Thread.setDefaultUncaughtExceptionHandler(restartHandler);

        /**初始化Register网络框架*/
        NetMgr.getInstance().registerProvider(new BaseNetProvider());


        /**配置极光推送*/
      /*  //初始化sdk
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //作为标签
        //String EmployeeId = SPUtils.getUserDataBean(this).getEmployeeSerialId();
        //作为别名
        //String positionId = SPUtils.getUserDataBean(this).getPositionId();
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了game, old_page, women

        Set<String> set = new HashSet<>();
        set.add("game");//名字任意，可多添加几个,能区别就好了
        set.add("oldpage");
        set.add("women");
        JPushInterface.setTags(this, set, null);//设置标签

        JPushInterface.setAlias(this,"00",null);//设置别名，一个用户只能有一个别名
*/

//        UMShareAPI.get(mContext);
//        Config.DEBUG = true;
        //PlatformConfig.setWeixin(getString(R.string.wei_chat_app_id), getString(R.string.wei_chat_app_secret));
        //PlatformConfig.setQQZone(getString(R.string.qq_app_id), getString(R.string.qq_app_key));

    }



    public static Context getmContext() {
        return mContext;
    }



    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();//发生崩溃异常时,重启应用
        }
    };

    /**
     * 重启 MainActivity
     */
    public void restartApp() {
        Intent intent = new Intent(mApplication, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mApplication.startActivity(intent);
        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        android.os.Process.killProcess(android.os.Process.myPid());
    }



    /**
     * 添加Activity
     */
    public void addActivity(BaseActivity activity) {
        // 判断当前集合中不存在该Activity
        if (!mActivities.contains(activity)) {
            mActivities.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity(BaseActivity activity) {
        //判断当前集合中存在该Activity
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity() {
        //通过循环，把集合中的所有Activity销毁
        for (BaseActivity activity : mActivities) {
            activity.finish();
        }
    }
}
