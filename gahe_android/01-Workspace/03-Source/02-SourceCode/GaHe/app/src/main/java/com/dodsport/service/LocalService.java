package com.dodsport.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dodsport.GaHeApplication;
import com.dodsport.GaheService;
import com.dodsport.activity.BaseActivity;
import com.dodsport.model.StatusBean;
import com.dodsport.model.TokenBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import static com.dodsport.activity.expenses.expenseaccountmanage.PhotoPickerActivity.TAG;


/**
 * Created by Administrator on 2017/1/15.
 * 本地进程
 */

public class LocalService extends Service {

    private MyBinber binber;
    private MyConn conn;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binber;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binber = new MyBinber();
        if (conn == null){
            conn = new MyConn();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //如果启动了该服务，立即去绑定RemoteService服务，并声明为重要级别
        LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),conn, Context.BIND_IMPORTANT);

    }


    public class MyBinber extends GaheService.Stub {


        @Override
        public String getGaheServiceName() throws RemoteException {
            return "LocalService";
        }
    }



    class MyConn implements ServiceConnection{

        //连接RemoteService 服务成功后调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        //连接出现异常后调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //启动远程服务
            LocalService.this.startService(new Intent(LocalService.this,RemoteService.class));
            //绑定远程服务
            LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),conn,Context.BIND_IMPORTANT);

        }
    }
}
