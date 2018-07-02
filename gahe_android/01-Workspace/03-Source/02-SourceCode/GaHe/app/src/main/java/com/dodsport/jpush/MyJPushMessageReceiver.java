package com.dodsport.jpush;

/**
 * 自定义JPush message 接收器,包括操作tag/alias的结果返回(仅仅包含tag/alias新接口部分)
 * */
//public class MyJPushMessageReceiver extends cn.jpush.android.service.JPushMessageReceiver {
//
//    @Override
//    public void onTagOperatorResult(Context context,JPushMessage jPushMessage) {
//        Log.i("*****", "接收消息 1--->"+context.toString()+"\t"+jPushMessage.toString()+"");
//        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
//        super.onTagOperatorResult(context, jPushMessage);
//    }
//    @Override
//    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
//        Log.i("*****", "接收消息 2--->"+context.toString()+"\t"+jPushMessage.toString()+"");
//        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
//        super.onCheckTagOperatorResult(context, jPushMessage);
//    }
//    @Override
//    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
//        Log.i("*****", "接收消息 3--->"+context.toString()+"\t"+jPushMessage.toString()+"");
//        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
//        super.onAliasOperatorResult(context, jPushMessage);
//    }
//}
