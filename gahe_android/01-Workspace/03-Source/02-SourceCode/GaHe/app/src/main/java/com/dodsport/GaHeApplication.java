package com.dodsport;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.dodsport.activity.BaseActivity;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/17.
 */

public class GaHeApplication extends Application {

    public static int Width;
    public static int Height;
    public static DisplayMetrics mDisplayMetrics;
    public static List<Integer> weekList = new ArrayList<>();
    private GaHeApplication mGaHeApplication;

    private static Context mContext;    //上下文

    // 保存所有打开的 Activity
    public List<BaseActivity> mActivities;  //用于存放所有启动的Activity的集合
    private GaHeApplication getInstance(){
        return mGaHeApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGaHeApplication = this;
        mContext = this;

        /**Activity集合*/
        mActivities = new ArrayList<>();

        /**获取屏幕宽高*/
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Width = wm.getDefaultDisplay().getWidth();
        Height = wm.getDefaultDisplay().getHeight();
        mDisplayMetrics = getResources().getDisplayMetrics();

        /**初始化nohttp网络框架*/
        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(30 * 1000) // 全局连接超时时间，单位毫秒。
                .setReadTimeout(30 * 1000) // 全局服务器响应超时时间，单位毫秒。
                .setCacheStore(
                        new DBCacheStore(this) // 配置缓存到数据库。
                                .setEnable(false)) // true启用缓存，fasle禁用缓存。
//                .setCacheStore(
//                        new DiskCacheStore(this)) // 配置缓存到SD卡。
                .setCookieStore(
                        new DBCookieStore(this)
                                .setEnable(true)) // true启用自动维护Cookie，fasle禁用自动维护Cookie。
//                .setNetworkExecutor(new URLConnectionNetworkExecutor()) // 使用HttpURLConnection做网络层。
                .setNetworkExecutor(new OkHttpNetworkExecutor())  // 使用OkHttp做网络层。
        );

        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("NoHttpSample"); // 设置NoHttp打印Log的TAG。

        /**配置极光推送*/
        //初始化sdk
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //作为标签
        String EmployeeId = SPUtils.getUserDataBean(this).getEmployeeSerialId();
        //作为别名
        String positionId = SPUtils.getUserDataBean(this).getPositionId();
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        Set<String> set = new HashSet<>();
        set.add(EmployeeId);//名字任意，可多添加几个,能区别就好了
        JPushInterface.setTags(this, set, null);//设置标签
        JPushInterface.setAlias(this,positionId,null);//设置别名，一个用户只能有一个别名
//       String time = "09:00-10:00,10:00-11:00";
    }

    public static Context getmContext() {
        return mContext;
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
