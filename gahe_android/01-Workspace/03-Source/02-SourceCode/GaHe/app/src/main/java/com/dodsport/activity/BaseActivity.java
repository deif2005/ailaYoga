package com.dodsport.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dodsport.GaHeApplication;
import com.dodsport.model.StatusBean;
import com.dodsport.model.TokenBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.request.RequestMeansManager;
import com.dodsport.utils.LogUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * 活动基类
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG ="******";
//    ReceiveMsgService receiveMsgService;

//    WeakReference<ReceiveMsgService> weakReference;


    private boolean token = false;
    private InputMethodManager manager;

    /**网络状态的判断*/
    public static String netWortStatus ="";  //网络状态的变量
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


    private GaHeApplication application;
    private BaseActivity oContext;
    private List<Activity> mActivity = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (application == null) {
            // 得到Application对象
            application = (GaHeApplication) getApplication();
        }
        // 把当前的上下文对象赋值给BaseActivity
        oContext = this;
        // 调用添加方法
        addActivity();

        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue(1);

        setHead();

        /**
         * 点击空白地方 键盘关闭
         * */
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


//        bind();
    }


    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    /*public <T> void request2(int what, Request<T> request, HttpListener<T> callback,
                            boolean canCancel, boolean isLoading) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }*/


    /**
     * 发送网络请求
     * */
   /* public <T> void request(int what, Request<T> request, OnResponseListener<T> listener) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);

        com.dodsport.request.CallServer.getInstance().add(what, request, listener);
    }*/





    @Override
    protected void onDestroy()
    {

        unbind();
        mHandler.removeCallbacksAndMessages(null);  //移除消息

        if (RequestMeansManager.mQueue !=null && RequestMeansManager.object !=null){
            // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
            RequestMeansManager.mQueue.cancelBySign(RequestMeansManager.object);
            // 因为回调函数持有了activity，所以退出activity时请停止队列。
            RequestMeansManager.mQueue.stop();
        }

        if (RequestMeansManager.cancelObject!=null){
            // 在组件销毁的时候调用队列的按照sign取消的方法即可取消。
            com.dodsport.request.CallServer.getInstance().cancelBySign(RequestMeansManager.cancelObject);
        }

        removeActivity();
        super.onDestroy();
    }



    /**
     * 沉浸式状态栏
     */
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

    // 添加Activity方法
    public void addActivity() {
        application.addActivity(oContext);// 调用myApplication的添加Activity方法

        //Log.i(TAG, "addActivity: 添加的Activity的个数：  "+application.mActivities);

 }
    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity(oContext);// 调用myApplication的销毁单个Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity();// 调用myApplication的销毁所有Activity方法
    }

   /* private void bind() {
        Intent intent = new Intent(this.getApplicationContext(), ReceiveMsgService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.i("BaseActivity----------", "BindServer: ");

    }*/

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

    protected void showToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
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

        if (execute){
            PunchTheClockRequest.getToKen(new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {
                }
                @Override
                public void onSucceed(int what, Response<String> response) {
//                    Log.i(TAG, "BaseActivity: 请求Token"+response.toString()+"\n");

                    token  = true;
                    try {
                        StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                        TokenBean mTokenBean = JSON.parseObject(statusBean.getDatas(), TokenBean.class);

                        //List<TokenBean> stu =JSON.parseObject(statusBean.getDatas(), new TypeReference<List<TokenBean>>(){});
                        if (!TextUtils.isEmpty(mTokenBean.getToken())){
                            //保存Token
                            SPUtils.saveToken(BaseActivity.this,mTokenBean.getToken());
                        }else {
                            //Toast("请求失败,请稍后重试！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    token = false;
                    Log.i(TAG, "BaseActivity: 请求失败！");
                }

                @Override
                public void onFinish(int what) {
                }
            });
        }
        return token;
    }



    /**
     * 点击空白地方 键盘退出
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

}
