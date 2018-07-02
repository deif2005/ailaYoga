package com.dodsport.activity.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


/**
 * 网络状态的广播
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiver";
    private ConnectivityManager mConnectivityManager;

    private NetworkInfo netInfo;
    private String name;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = mConnectivityManager.getActiveNetworkInfo();
            if(netInfo != null && netInfo.isAvailable()) {
                if(netInfo.getType()== ConnectivityManager.TYPE_WIFI){
                    String typeName = netInfo.getTypeName();
                    name = typeName;
                    Log.i(TAG, "onReceive:  "+name);
//                    EventBus.getDefault().post("WIFI");
                }else if(netInfo.getType()== ConnectivityManager.TYPE_ETHERNET){

                }else if(netInfo.getType()== ConnectivityManager.TYPE_MOBILE){
                    name = "3G";
                    Log.i(TAG, "onReceive:  3g");
//                    EventBus.getDefault().post("3G");
                }
            } else {
                    name = "No";
                   Log.i(TAG, "onReceive:  no");
//                EventBus.getDefault().post("NO");
            }

        }
    }
}


