package com.dodsport.activity.expenses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.model.AliPayBean;
import com.dodsport.model.PackageBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.NetUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.alipayUtil.AuthResult;
import com.dodsport.utils.alipayUtil.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 余额
 *
 * @author Administrator
 */
public class PayActivity extends BaseActivity {


    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.llWXPay)
    LinearLayout mLlWXPay;
    @Bind(R.id.llAilPay)
    LinearLayout mLlAilPay;
    private String payTag = "wx";   //支付方式  默认微信
    private float money = 1;
    private UserDataBean userDataBean;

    /**************************微信*****************************************************/
    private IWXAPI wxAPI;
    private PayReq req;
    private Map<String, String> resultunifiedorder;
    private StringBuffer sb;

    /*********
     * 微信支付常量
     ********/
    public static final String WX_APP_ID = "wxc03f87c55f4f27f9";

    /**
     * 微信和支付宝的常量
     */
    public static final String WX_RESULT = "WX_RESULT";

    public static final String ZFB_RRSULT = "ZFB_RESULT";

    private String wx_result = null;
    private String nonce_str;  //随机字符串
    private String prepay_id;   //预支付ID
    private String timestamp;    //时间戳
    private String sign;     //签名
    private String out_trade_no;  // 商户订单号
    private String partnerid;
    private String packAge;

    /**************************支付宝*****************************************************/

    private String AliPayOrderNo;    //支付宝的订单号
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017100609158754";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "";

    public static final String RSA2_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    Log.i("****", "支付返回--->payResult-->"+payResult.toString()+"\tresultInfo-->"+resultInfo+"\tresultStatus-->"+resultStatus+"\t");
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                        int arg1 = msg.arg1;
                        Log.i("", "handleMessage: " + arg1);
                        /*支付宝支付成功*/
                        //WXPayResultRequest(AliPayOrderNo,arg1);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(PayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private int HUBI;  //虎币数量
    private int pId; // 产品ID (VIP)
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        mActivity = this;
        EventBus.getDefault().register(this);
        iniView();
    }

    private void iniView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("余额");
        //注册微信API
        wxAPI = WXAPIFactory.createWXAPI(this, WX_APP_ID);
    }

    @OnClick({R.id.head_ivBack,R.id.llWXPay, R.id.llAilPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llWXPay:
                payTag = "wx";
                pay(payTag, money);
                break;
            case R.id.llAilPay:
                payTag = "ali";
                pay(payTag, money);
                break;
            default:
                break;
        }
    }


    /**
     * 微信支付
     * Token 用户通行口令
     */
    private void getPayInfo() {
        OperatingFloorRequest.wxPay(new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                try {
                    Log.i("*****", "onSucceed:-->" + response.toString() + "");
                    PackageBean packageBean = JSON.parseObject(response.get(), PackageBean.class);
                    PackageBean.DatasBean datas = packageBean.getDatas();
                    nonce_str = datas.getNoncestr();
                    prepay_id = datas.getPrepayid();
                    timestamp = datas.getTimestamp();
                    sign = datas.getSign();
                    partnerid = datas.getPartnerid();
                    packAge = "Sign=WXPay";
                    wxAPI.registerApp(WX_APP_ID);
                    WX();
                    wx_result = "wx";
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i("*****", "onFailed:-->" + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /***
     * 付款
     * @param payTag   付款标示
     * @param money
     */
    private void pay(String payTag, float money) {
        if (payTag.equals("wx")) {
            //选择微信支付
            boolean app = getApp(this, "com.tencent.mm");
            if (app) {
                boolean connected = NetUtils.isConnected(getApplicationContext());
                if (connected) {

                    long l = System.currentTimeMillis();
                    Log.i("", "onClick: " + l);
                    String str = l + "_" + money;
                    //微信请求
                    getPayInfo();
                } else {
                    ToastUtils.showToCenter(PayActivity.this, "当前没有连接网络", 0);
                }
            } else {
                ToastUtils.showToCenter(this, "检测到您的手机未安装微信", Toast.LENGTH_SHORT);
            }


        } else if (payTag.equals("ali")) {

            boolean ali = getApp(this, "com.eg.android.AlipayGphone");
            if (ali) {
                boolean connected = NetUtils.isConnected(getApplicationContext());
                if (connected) {

                     aliPayRequest(money);//支付宝支付请求
                } else {
                    ToastUtils.showToCenter(PayActivity.this, "当前没有连接网络", 0);
                }
            } else {
                ToastUtils.showToCenter(this, "检测到您的手机未安装支付宝", Toast.LENGTH_SHORT);
            }
        }
    }

    private void aliPayRequest(float money) {

        OperatingFloorRequest.aliPay(new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                Log.i("***支付宝", "成功--->"+response.get().toString()+"");
                try {
                    AliPayBean aliPay = JSON.parseObject(response.get(), AliPayBean.class);
                    final String body = aliPay.getDatas();//j.getString("Body");   //订单信息
                    Log.i("****支付过程-->", "转换-->"+body.toString()+"\n"+AliPayOrderNo+"\t");
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(PayActivity.this);
                            Map<String, String> result = alipay.payV2(body, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            msg.arg1 = 2;   //代表充值数量
                            mHandler.sendMessage(msg);
                        }
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                Log.i("***支付宝", "异常--->"+response.toString()+"");
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }

    /**
     * 微信支付
     */
    private void WX() {
        PayReq re = new PayReq();
        re.appId = WX_APP_ID;
        re.timeStamp = timestamp;
        re.packageValue = packAge;
        re.nonceStr = nonce_str;
        re.prepayId = prepay_id;
        re.partnerId = partnerid;
        re.sign = sign;
//        Log.i("****", "WX:调起微信支付--->"+"WX_APP_ID-->"+WX_APP_ID+"\ttimestamp-->"+timestamp+"\tpackAge-->"+packAge+"\tnonce_str-->"+nonce_str+"\tprepay_id-->"+
//                prepay_id+"\tpartnerid-->"+partnerid+"\tsign-->"+sign+"\t");
        wxAPI.sendReq(re);
    }

    //查找指定应用
    private boolean getApp(Activity context, String appName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(appName)) {
                    return true;
                }
            }
        }
        return false;
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(String event) {
        /**
         * 微信支付的第一种情况
         * 支付完成。点击返回  客户端收到了微信回调。
         */
        if (event.equals("WX_RESULT"))   //微信支付的成功结果反馈
        {
            wx_result = "WX_RESULT";
            Log.i("*****收到的消息：", "onUserEvent: ");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
