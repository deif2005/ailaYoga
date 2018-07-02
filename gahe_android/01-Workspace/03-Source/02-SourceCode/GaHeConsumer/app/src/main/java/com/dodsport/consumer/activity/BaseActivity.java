package com.dodsport.consumer.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dodsport.consumer.GaHeConsumerApplication;
import com.dodsport.consumer.extension.BaseSubscriber;
import com.dodsport.consumer.model.TokenBean;
import com.dodsport.consumer.net.api.VisApi;
import com.dodsport.consumer.util.JsonUtils;
import com.dodsport.consumer.util.LogUtils;
import com.dodsport.consumer.util.SPUtils;
import com.dodsport.consumer.util.ToastUtils;
import com.dodsport.consumer.weight.IRefreshView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.indicator.IIndicator;
import okhttp3.ResponseBody;


/**
 * 活动基类
 * @author Administrator
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class BaseActivity extends AppCompatActivity implements IRefreshView{

    private static final String TAG ="******";
//    ReceiveMsgService receiveMsgService;

//    WeakReference<ReceiveMsgService> weakReference;

    public static String token = "";


    private int count = 0;//记住请求token的次数

    /**网络状态的判断*/
    public static String netWortStatus ="";  //网络状态的变量

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public int getStyle() {
        return 0;
    }

    @Override
    public int getCustomHeight() {
        return 0;
    }

    @Override
    public void onFingerUp(SmoothRefreshLayout layout, IIndicator indicator) {

    }

    @Override
    public void onReset(SmoothRefreshLayout layout) {

    }

    @Override
    public void onRefreshPrepare(SmoothRefreshLayout layout) {

    }

    @Override
    public void onRefreshBegin(SmoothRefreshLayout layout, IIndicator indicator) {

    }

    @Override
    public void onRefreshComplete(SmoothRefreshLayout layout, boolean isSuccessful) {

    }

    @Override
    public void onRefreshPositionChanged(SmoothRefreshLayout layout, byte status, IIndicator indicator) {

    }

    @Override
    public void onPureScrollPositionChanged(SmoothRefreshLayout layout, byte status, IIndicator indicator) {

    }
    // 记录当前连接状态，因为广播会接收所有的网络状态改变wifi/3g等等，所以需要一个标志记录当前状态
   /* public  Handler baseHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://
                 //  Log("-----------没有网络");
                    netWortStatus="NO";
                    break;
                case 2://
                    // Log("-----------3G");
                    netWortStatus="3G";
                    break;
                case 3://
                 //   Log("-----------WIFI");
                    netWortStatus="WIFI";
                    break;
                default:
                    break;
            }
        }
    };*/



    /**
     * 使用静态的内部类，不会持有当前对象的引用
     */
    private static class MyHandler extends Handler {
        private final WeakReference<BaseActivity> mActivity;

        public MyHandler(BaseActivity activity) {
            mActivity = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1://
                        //  Log("-----------没有网络");
                        netWortStatus="NO";
                        break;
                    case 2://
                        // Log("-----------3G");
                        netWortStatus="3G";
                        break;
                    case 3://
                        //   Log("-----------WIFI");
                        netWortStatus="WIFI";
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);


    private GaHeConsumerApplication application;
    private BaseActivity oContext;
    private List<Activity> mActivity = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (application == null) {
            // 得到Application对象
            application = (GaHeConsumerApplication)getApplication();
        }
        // 把当前的上下文对象赋值给BaseActivity
        oContext = this;
        // 调用添加方法
        addActivity();


        setHead();

//        bind();
    }


   /* private void bind() {
        Intent intent = new Intent(this.getApplicationContext(), ReceiveMsgService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.i("BaseActivity----------", "BindServer: ");

    }*/


    /**
     * 沉浸式状态栏
     **/
    private void setHead() {
        /**
         * 设置头部头部导航栏颜色
         * */
        //Android4.4及以上版本才能设置此效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Android5.0版本
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }else {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            }
        }
    }

    /** 添加Activity方法*/
    public void addActivity() {
        // 调用myApplication的添加Activity方法
        application.addActivity(oContext);
        //Log.i(TAG, "addActivity: 添加的Activity的个数：  "+application.mActivities);

    }



    public void addtiemActivity(Activity activity){
        mActivity.add(activity);
    }

    public void removetiemActivity(){
        if (mActivity.size()!=0){
            for (int i = 0; i < mActivity.size(); i++) {
                mActivity.get(i).finish();
            }
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            log("BaseActivity----------未收到服务");
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

 /*           try {
                receiveMsgService= ((ReceiveMsgService.MyBinder) service).getService();
                receiveMsgService.setOnGetConnectState(new ReceiveMsgService.GetConnectState() { // 添加接口实例获取连接状态
                    @Override
                    public void GetState(String msg) {

                        log("BaseActivity----------收到服务");
                        if(!TextUtils.isEmpty(msg))
                        {
                            if(msg.equals("3G"))
                            {
                                mHandler.sendEmptyMessage(2);

                            }else if(msg.equals("WIFI")){
                                mHandler.sendEmptyMessage(3);
                            }else
                            {
                                mHandler.sendEmptyMessage(1);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            //  weakReference = new WeakReference<ReceiveMsgService>(receiveMsgService);
        }


    };

    private void unbind() {
       /* if (receiveMsgService != null ) {
            unbindService(serviceConnection);
            Log.i("mylog", "执行unbind()");
        }*/
    }


    /**
     * Toast
     * @param msg
     */
    protected void Toast(String msg)
    {
        ToastUtils.showShort(this,msg);
    }

    /**
     * 日志
     * @param msg
     */
    protected void log(String msg)
    {
        LogUtils.i(msg);
    }

    /**
     *
     * /获取Token
     */

    public boolean getToken(boolean execute){
       new VisApi().getToken()
//                .compose(this.<ResponseBody>bindToLifecycle())
                .subscribe(new BaseSubscriber<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "获取token异常---> " + e.toString() + "");
                        count = count +1;
                        if (count >= 2){
                            getToken(true);
                        }
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            TokenBean tokenBean = JsonUtils.fromStringToJson(body.string(), TokenBean.class);
                            Log.i(TAG, "token-->"+tokenBean.toString()+"");
//                            if (!tokenBean.getResult().getCode().toString().equals("0")){
                            count = count +1;
//                                if (count >= 2){
//                                    getToken(true);
//                                }
//                                return;
//                            }
                            token = tokenBean.getDatas().getToken();
                            SPUtils.saveToken(getApplicationContext(),tokenBean.getDatas().getToken());
                        } catch (Exception e) {
                            Log.i(TAG, "获取数据异常-*****--> " + e.toString() + "");
                            e.printStackTrace();
                        }
                    }

                });

        return execute;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // 点击空白区域 自动隐藏软键盘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


}
