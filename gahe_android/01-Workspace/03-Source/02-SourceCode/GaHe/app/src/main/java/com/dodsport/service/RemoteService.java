package com.dodsport.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dodsport.GaHeApplication;
import com.dodsport.GaheService;
import com.dodsport.model.StatusBean;
import com.dodsport.model.TokenBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Administrator on 2017/1/15.
 *
 * 远程进程
 */

public class RemoteService extends Service {

    private MyBinber binber;
    private MyConn conn;
    private Handler mHandler;
    private boolean token = true;
    private String TAG ="***RemoteService-->";

    @Override
    public IBinder onBind(Intent intent) {
        return binber;
    }

    @Override
    public void onCreate() {
        super.onCreate();
            binber = new MyBinber();
        if (conn == null) {
            conn = new MyConn();
        }

            mHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what){
                        case 1:
                            getToken();
                            break;
                        case 2:
                            getToken();
                            Log.i(TAG, "Service进来获取token");
                            break;
                    }
                    return false;
                }
            });


    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //如果启动了该服务，立即去绑定LocalService服务
        RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class),conn, Context.BIND_IMPORTANT);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while (token){
                        Thread.sleep(7100000);
                        mHandler.sendEmptyMessage(2);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    /*获取token*/
    public void getToken() {
        PunchTheClockRequest.getToKen(new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }
            @Override
            public void onSucceed(int what, Response<String> response) {
//                    Log.i(TAG, "BaseActivity: 请求Token"+response.toString()+"\n");
                try {
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    TokenBean mTokenBean = JSON.parseObject(statusBean.getDatas(), TokenBean.class);
                    if (!TextUtils.isEmpty(mTokenBean.getToken())){
                        //保存Token
                        SPUtils.saveToken(GaHeApplication.getmContext(),mTokenBean.getToken());
                    }else {
                        //Toast("请求失败,请稍后重试！");
                        mHandler.sendEmptyMessage(1);
                    }
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(1);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "BaseActivity: 请求失败！");
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }



    class MyBinber extends GaheService.Stub{

        @Override
        public String getGaheServiceName() throws RemoteException {
            return "RemoteService";
        }
    }

    class MyConn implements ServiceConnection{


        //连接LocalService成功后调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mHandler.sendEmptyMessage(1);       //获取token
        }



        //连接出现异常后调用
        @Override
        public void onServiceDisconnected(ComponentName name) {

//            //重启app代码
//            Intent intent = getBaseContext().getPackageManager()
//                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
            //启动本地服务
            RemoteService.this.startService(new Intent(RemoteService.this,LocalService.class));
            //绑定本地服务 并声明为重要级别
            RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class),conn,Context.BIND_IMPORTANT);



        }
    }
}
