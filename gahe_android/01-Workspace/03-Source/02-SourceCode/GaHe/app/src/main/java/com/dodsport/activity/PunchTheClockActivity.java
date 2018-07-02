package com.dodsport.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.dodsport.R;
import com.dodsport.model.ResultBean;
import com.dodsport.model.SignScopeBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.AuthorityApplyForUtils;
import com.dodsport.utils.MacAddressUtils;
import com.dodsport.utils.NetUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.view.Const;
import com.dodsport.weight.TimeUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 考勤管理类
 */
public class PunchTheClockActivity extends BaseActivity implements View.OnClickListener, AMap.OnMapClickListener, LocationSource, AMapLocationListener, GeoFenceListener, GeocodeSearch.OnGeocodeSearchListener {

    private static final String TAG = "*******";
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.mapview)
    MapView mMapview;
    @Bind(R.id.btpunchtheclock)
    TextView mBtpunchtheclock;
    @Bind(R.id.tvtime)
    TextView mTvtime;
    @Bind(R.id.ivPclock)
    ImageView mIvPclock;
    @Bind(R.id.tvPclock)
    TextView mTvPclock;
    @Bind(R.id.llPclock)
    LinearLayout mLlPclock;
    @Bind(R.id.ivPretreat)
    ImageView mIvPretreat;
    @Bind(R.id.tvPretreat)
    TextView mTvPretreat;
    @Bind(R.id.llPretreat)
    LinearLayout mLlPretreat;
    @Bind(R.id.tvtimeYTD)
    TextView mTvtimeYTD;
    @Bind(R.id.tvaddress)
    TextView mTvaddress;
    @Bind(R.id.llpunchtheclock)
    LinearLayout mLlpunchtheclock;


    /**
     * 用于显示当前的位置
     * <p>
     * 示例中是为了显示当前的位置，在实际使用中，单独的地理围栏可以不使用定位接口
     * </p>
     */
    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private AMap mAMap;
    // 地理围栏的广播action
    private static final String GEOFENCE_BROADCAST_ACTION = "com.example.geofence.multiple";
    // 地理围栏客户端
    private GeoFenceClient fenceClient = null;
    // 要创建的围栏半径
    private float fenceRadius = 0.0F;
    // 中心点marker
    private Marker centerMarker;
    private MarkerOptions markerOption = null;
    private BitmapDescriptor ICON_YELLOW = BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_RED);
    private List<Marker> markerList = new ArrayList<Marker>();
    // 触发地理围栏的行为，默认为进入提醒
    private int activatesAction = GeoFenceClient.GEOFENCE_IN;
    // 当前的坐标点集合，主要用于进行地图的可视区域的缩放
    private LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
    // 记录已经添加成功的围栏
    private HashMap<String, GeoFence> fenceMap = new HashMap<String, GeoFence>();
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private GeocodeSearch mGeocodeSearch = null;
    //表示是否满足签到条件
    private boolean signIn = false;
    //当前时间
    private String time = "";
    private boolean str = false;
    private int sign = 0;       //签到请求网络次数
    private String address = "";    //定位地址
    private String signType = "1";  //1签到、2签退
    private String connectedWifiMacAddress = "";//WIFI地址
    private Activity mActivity;
    /**定位的经纬度*/
    private double lng = 0;
    private double lat = 0;
    /**商家的经纬度*/
    private double bissLng = 0;
    private double bissLat = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_the_clock);
        ButterKnife.bind(this);
        mActivity = this;
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapview.onCreate(savedInstanceState);
        //获取签到规则
        querySignTimeByStoreId();
        initview();
    }

    private void initview() {
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadIvOK.setVisibility(View.VISIBLE);
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("考勤打卡");
        mHeadTvOK.setText("考勤记录");
        mHeadIvBack.setOnClickListener(this);
        mHeadIvOK.setOnClickListener(this);
        mHeadTvOK.setOnClickListener(this);

        mIvPclock.setOnClickListener(this);
        mTvPclock.setOnClickListener(this);
        mIvPretreat.setOnClickListener(this);
        mTvPretreat.setOnClickListener(this);
        mBtpunchtheclock.setOnClickListener(this);
        mLlpunchtheclock.setOnClickListener(this);
        str = true;//

        /**
         * 获取网络时间
         * */

      new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    while (str) {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        //获取当前时间
                        time = formatter.format(curDate);
                        handler.sendEmptyMessage(4);
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
            //针对6.0做权限申请
            boolean mAuthority = AuthorityApplyForUtils.getAAF(this);
            if (mAuthority) {
                init();
            } else {
                init();
            }


    }



    /**初始化地图控制器对象*/
    void init() {
//        markerOption = new MarkerOptions().draggable(true);
        // 初始化地理围栏
//        fenceClient = new GeoFenceClient(getApplicationContext());
//        mGeocodeSearch = new GeocodeSearch(this);
//        mGeocodeSearch.setOnGeocodeSearchListener(this);
        if (mAMap == null) {
            mAMap = mMapview.getMap();
            //mAMap.getUiSettings().setRotateGesturesEnabled(false);
            //mAMap.moveCamera(CameraUpdateFactory.zoomBy(6));
            setUpMap();
        }
        /**
         * 创建pendingIntent
         */
//        fenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
//        fenceClient.setGeoFenceListener(this);
//        /**
//         * 设置地理围栏的触发行为,默认为进入
//         */
//        fenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN);
//
//        if (true) {
//            activatesAction |= GeoFenceClient.GEOFENCE_IN;
//        } else {
//            activatesAction = activatesAction
//                    & (GeoFenceClient.GEOFENCE_OUT
//                    | GeoFenceClient.GEOFENCE_STAYED);
//        }
//        if (null != fenceClient) {
//            fenceClient.setActivateAction(activatesAction);
//        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        //可点击选中
        mAMap.setOnMapClickListener(this);
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
//        myLocationStyle.myLocationIcon(
//                BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        //连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;

//        //连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);

        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(8000);

        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:  //返回
                finish();
                break;
            case R.id.llpunchtheclock:  //签到
                //网络判断
                boolean connected = NetUtils.isConnected(this);
                if (connected) {
                    boolean wifi = NetUtils.isWifi(this);
                    if (wifi) {
                        /**获取当前连接WIFI的路由器MAC地址*/
                        connectedWifiMacAddress = MacAddressUtils.getConnectedWifiMacAddress(mActivity);
                        wifiSign(); //WIFI签到
                    }else {
                        //定位签到
                        PunchTheClock();
                    }
                }else {
                    ToastUtils.showToCenters(mActivity,"请连接网络!",1000);
                    return;
                }
                break;
            case R.id.head_tvOK:    //考勤记录
                Intent intent = new Intent(this,PunchTheClockRecordActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                break;
            case R.id.ivPclock:     //签到页面
            case R.id.tvPclock:
                mBtpunchtheclock.setText("签到");
                signType ="1";
                mLlPclock.setBackgroundColor(getResources().getColor(R.color.home_text_selected));
                mIvPclock.setImageResource(R.drawable.qiandao_dianji);
                mTvPclock.setTextColor(getResources().getColor(R.color.white));

                mLlPretreat.setBackgroundColor(getResources().getColor(R.color.textincmeColor));
                mIvPretreat.setImageResource(R.drawable.qiantui);
                mTvPretreat.setTextColor(getResources().getColor(R.color.home_text_normal));
                break;
            case R.id.ivPretreat:   //签退页面
            case R.id.tvPretreat:
                mBtpunchtheclock.setText("签退");
                signType = "2";
                mLlPclock.setBackgroundColor(getResources().getColor(R.color.textincmeColor));
                mIvPclock.setImageResource(R.drawable.qiaodao);
                mTvPclock.setTextColor(getResources().getColor(R.color.home_text_normal));

                mLlPretreat.setBackgroundColor(getResources().getColor(R.color.home_text_selected));
                mIvPretreat.setImageResource(R.drawable.qiantui_dianji);
                mTvPretreat.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }
    }

    /**
     * 定位签到
     */
    private void PunchTheClock() {
        String id = SPUtils.getUserDataBean(this).getId();
        String storeId = SPUtils.getUserDataBean(this).getStoreId();
        //Log.i(TAG, "k----->"+id+"\t"+signType+"\t"+storeId+"\t"+address+"\t");

        PunchTheClockRequest.userPunchTheClock(id, signType, storeId,address, lng+"",lat+"",new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "定位签到成功---> "+response.get().toString()+"");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        switch (resultBean.getCode()){
                            case "5038":
                                ToastUtils.showToCenters(mActivity,"已经签到过了!",1000);
                                break;
                            case "5039":
                                ToastUtils.showToCenters(mActivity,"已经签退过了!",1000);
                                break;
                            case "5014":
                                ToastUtils.showToCenters(mActivity,"不在打卡时间范围,请去请假页面，填写请假单!",1500);
                                break;
                            case "5002":
                                ToastUtils.showToCenters(mActivity,"请先设置考勤规则!",1000);
                                break;
                            default:
                                sign = sign +1;
                                if (sign >= 2){
                                    return;
                                }
                                //定位签到
                                PunchTheClock();
                                break;
                        }

                        return;
                    }else {
                        if (signType.equals("1")){
                            ToastUtils.showToCenters(mActivity,"签到成功!",1000);
                        }else {
                            ToastUtils.showToCenters(mActivity,"签退成功!",1000);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sign = sign +1;
                    if (sign >= 2){
                        return;
                    }
                    //定位签到
                    PunchTheClock();

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                sign = sign +1;
                if (sign >= 2){
                    ToastUtils.showToCenters(mActivity,"签到失败!",1000);
                    return;
                }
                //定位签到
                PunchTheClock();
            }

            @Override
            public void onFinish(int what) {
            }
        });

        if (signIn) {
            mBtpunchtheclock.setText("成功");
            signIn = false;
        }
    }

    /**
     * WIFI签到
     * */
    private void wifiSign(){
        String id = SPUtils.getUserDataBean(this).getId();
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();
        //Log.i(TAG, "WIFI签到传参-->"+id+"\t"+signType+"\t"+connectedWifiMacAddress+"\t-->"+storeId+"\t");
        PunchTheClockRequest.wifiSign(storeId,id, signType, connectedWifiMacAddress, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i(TAG, "WIFI签到成功---> "+response.get().toString()+"");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")){
                        switch (resultBean.getCode()){
                            case "5038":
                                ToastUtils.showToCenters(mActivity,"已经签到过了!",1000);
                                break;
                            case "5039":
                                ToastUtils.showToCenters(mActivity,"已经签退过了!",1000);
                                break;
                            case "5014":
                                ToastUtils.showToCenters(mActivity,"不在打卡时间范围,请去请假页面，填写请假单!",1500);
                                break;
                            default:
                                //定位签到
                                PunchTheClock();
                                break;
                        }

                        return;
                    }else {
                        if (signType.equals("1")){
                            ToastUtils.showToCenters(mActivity,"签到成功!",1000);
                        }else {
                            ToastUtils.showToCenters(mActivity,"签退成功!",1000);
                        }
                    }
                } catch (Exception e) {
                    //定位签到
                    PunchTheClock();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                //定位签到
                PunchTheClock();
            }

            @Override
            public void onFinish(int what) {

            }
        });

        if (signIn) {
            mBtpunchtheclock.setText("成功");
            signIn = false;
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapview.onSaveInstanceState(outState);
    }


    /**
     * 地图点击选点
     */
    @Override
    public void onMapClick(LatLng latLng) {

    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置true为单次定位
            mLocationOption.setOnceLocation(false);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                //根据地址获取经纬度
                //getLatlon(amapLocation.getAddress());

                //签到地址
                address = amapLocation.getAddress();
                //签到经纬度
                lng = amapLocation.getLongitude();
                lat = amapLocation.getLatitude();
                //计算两个点的距离
//                float distance = AMapUtils.calculateLineDistance(userLatIng, companyLatIng);
                //面积计算
//                float area = AMapUtils.calculateArea(latLng,userLatIng);
                //当员工所在位置和商家的地址间距小于300就停止定位
//                if (distance < 1500) {
//                    mlocationClient.stopLocation();
//                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": "
                        + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 获取考勤规则
     * */
    private void querySignTimeByStoreId(){
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();
        PunchTheClockRequest.querySignTimeByStoreId(storeId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    Log.i("*****", "获取签到规则成功---> "+response.get().toString()+"");
                    SignScopeBean signScopeBean = JSON.parseObject(response.get(), SignScopeBean.class);
                    if (!signScopeBean.getResult().getCode().equals("0")){
                        ToastUtils.showToCenters(mActivity,"获取签到规则失败!",1000);
                        return;
                    }
                    try {
                        if (TextUtils.isEmpty(signScopeBean.getDatas().getSignScope().toString())){
                            mTvaddress.setText(SPUtils.getUserDataBean(mActivity).getBusinessAddress());
                        }else {
                            mTvaddress.setText(signScopeBean.getDatas().getSignScope().getSignAdd() + "");
                        }

                        if (TextUtils.isEmpty(signScopeBean.getDatas().getSignScope().getLng().toString()) || TextUtils.isEmpty(signScopeBean.getDatas().getSignScope().getLat().toString())){
                            //根据商家地址获取经纬度
                            if (TextUtils.isEmpty(signScopeBean.getDatas().getSignScope().getSignAdd())){
                                getLatlon(SPUtils.getUserDataBean(mActivity).getBusinessAddress());
                            }else {
                                getLatlon(signScopeBean.getDatas().getSignScope().getSignAdd());
                            }

                        }else {
                            bissLng =  Double.valueOf(signScopeBean.getDatas().getSignScope().getLng().toString());
                            bissLat =  Double.valueOf(signScopeBean.getDatas().getSignScope().getLat().toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        String datetime = TimeUtils.currentDatetime();
                        if (!TextUtils.isEmpty(datetime)){
                            String s = datetime.substring(0,4);
                            String a = datetime.substring(5,7);
                            String b = datetime.substring(8,10);
                            mTvtimeYTD.setText(s+"年"+a+"月"+b+"日");
                        }
                        mTvaddress.setText(SPUtils.getUserDataBean(mActivity).getBusinessAddress());
                        getLatlon(SPUtils.getUserDataBean(mActivity).getBusinessAddress());
                    }
                    //商家经纬度
                    LatLng companyLatIng = new LatLng(bissLat, bissLng);
                    addCenterMarker(companyLatIng);
//                    addRoundFence(companyLatIng);
                } catch (Exception e) {


                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"获取签到规则失败!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void drawFence(GeoFence fence) {
        switch (fence.getType()) {
            case GeoFence.TYPE_ROUND:
            case GeoFence.TYPE_AMAPPOI:
                drawCircle(fence);
                break;
            case GeoFence.TYPE_POLYGON:
            case GeoFence.TYPE_DISTRICT:
                drawPolygon(fence);
                break;
            default:
                break;
        }

        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = boundsBuilder.build();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        removeMarkers();
    }

    //圆形绘制围栏
    private void drawCircle(GeoFence fence) {
        LatLng center = new LatLng(fence.getCenter().getLatitude(),
                fence.getCenter().getLongitude());
        // 绘制一个圆形
        mAMap.addCircle(new CircleOptions().center(center)
                //.radius(fence.getRadius()).strokeColor(Const.STROKE_COLOR)
                //.fillColor(Const.FILL_COLOR)
                .strokeWidth(Const.STROKE_WIDTH));
        boundsBuilder.include(center);
    }

    private void drawPolygon(GeoFence fence) {
        final List<List<DPoint>> pointList = fence.getPointList();
        if (null == pointList || pointList.isEmpty()) {
            return;
        }
        for (List<DPoint> subList : pointList) {
            List<LatLng> lst = new ArrayList<LatLng>();

            PolygonOptions polygonOption = new PolygonOptions();
            for (DPoint point : subList) {
                lst.add(new LatLng(point.getLatitude(), point.getLongitude()));
                boundsBuilder.include(
                        new LatLng(point.getLatitude(), point.getLongitude()));
            }
            polygonOption.addAll(lst);

            polygonOption.strokeColor(Const.STROKE_COLOR)
                    .fillColor(Const.FILL_COLOR).strokeWidth(Const.STROKE_WIDTH);
            mAMap.addPolygon(polygonOption);
        }
    }

    /**
     * 添加圆形围栏
     *
     * @param companyLatIng
     * @author hongming.wang
     * @since 3.2.0
     */
    private void addRoundFence(LatLng companyLatIng) {
        String customId = "考勤";
        String radiusStr = "1100";
//        if (null == companyLatIng || TextUtils.isEmpty(radiusStr)) {
//            Toast.makeText(getApplicationContext(), "参数不全", Toast.LENGTH_SHORT)
//                    .show();
////            setRadioGroupAble(true);
//            return;
//        }
        DPoint centerPoint = new DPoint(companyLatIng.latitude,
                companyLatIng.longitude);
        fenceRadius = Float.parseFloat(radiusStr);
        fenceClient.addGeoFence(centerPoint, fenceRadius, customId);
    }


    private void addCenterMarker(LatLng latlng) {
        //设置经度、纬度
        if (null == centerMarker) {
            String storeName = SPUtils.getUserDataBean(mActivity).getStoreName();
            markerOption = new MarkerOptions()
                    .position(latlng)
                    .title(storeName)
                    .icon(ICON_YELLOW)
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point))
                        .perspective(true)
//                        .visible(false)
                    .draggable(true);// 设置远小近大效果,2.1.0版本新增
            centerMarker = mAMap.addMarker(markerOption);

            centerMarker.showInfoWindow();
        }
        centerMarker.setPosition(latlng);
        //显示平面
        centerMarker.setVisible(true);
        markerList.add(centerMarker);
    }

    private void removeMarkers() {
        if (null != centerMarker) {
            centerMarker.remove();
            centerMarker = null;
        }
        if (null != markerList && markerList.size() > 0) {
            for (Marker marker : markerList) {
                marker.remove();
            }
            markerList.clear();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    Object lock = new Object();

    void drawFence2Map() {
        new Runnable(){
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        if (null == fenceList || fenceList.isEmpty()) {
                            return;
                        }
                        for (GeoFence fence : fenceList) {
                            if (fenceMap.containsKey(fence.getFenceId())) {
                                continue;
                            }
                            drawFence(fence);
                            fenceMap.put(fence.getFenceId(), fence);
                        }
                    }
                } catch (Throwable e) {

                }
            }
        };
    }

    /**
     * handler
     * 接收添加围栏 是否成功的消息
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    StringBuffer sb = new StringBuffer();
                    //sb.append("添加围栏成功");
                    String customId = (String) msg.obj;
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append("customId: ").append(customId);
                    }
//                    Toast.makeText(getApplicationContext(), sb.toString(),
//                            Toast.LENGTH_SHORT).show();
                    signIn = true; //添加围栏成功
                    //drawFence2Map();
                    break;
                case 1:
                    int errorCode = msg.arg1;
                    //Toast.makeText(getApplicationContext(),
                    //        "添加围栏失败 " + errorCode, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String statusStr = (String) msg.obj;
                   /* tvResult.setVisibility(View.VISIBLE);
                    tvResult.append(statusStr + "\n");*/
                    break;
                case 4:     //当前时间
                    if (!TextUtils.isEmpty(time)) {
                        mTvtime.setText(time + "");

                    }

                    break;
                default:
                    break;
            }
//            setRadioGroupAble(true);
        }
    };
    /**
     * 接收触发围栏后的广播,当添加围栏成功之后，会立即对所有围栏状态进行一次侦测，如果当前状态与用户设置的触发行为相符将会立即触发一次围栏广播；
     * 只有当触发围栏之后才会收到广播,对于同一触发行为只会发送一次广播不会重复发送，除非位置和围栏的关系再次发生了改变。
     */
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                String customId = bundle
                        .getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
                //status标识的是当前的围栏状态，不是围栏行为
                int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                StringBuffer sb = new StringBuffer();
                switch (status) {
                    case GeoFence.STATUS_LOCFAIL:
                        sb.append("定位失败");
                        break;
                    case GeoFence.STATUS_IN:
                        sb.append("进入围栏 ");
                        ToastUtils.showToCenters(PunchTheClockActivity.this, "进入围栏可以签到", 1000);
                        break;
                    case GeoFence.STATUS_OUT:
                        sb.append("离开围栏 ");
                        break;
                    case GeoFence.STATUS_STAYED:
                        sb.append("停留在围栏内 ");
                        break;
                    default:
                        break;
                }
                if (status != GeoFence.STATUS_LOCFAIL) {
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append(" customId: " + customId);
                    }
                    sb.append(" fenceId: " + fenceId);
                }
                String str = sb.toString();
                Message msg = Message.obtain();
                msg.obj = str;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }
    };




    /**
     * 画围栏的回调
     */
    List<GeoFence> fenceList = new ArrayList<GeoFence>();

    @Override
    public void onGeoFenceCreateFinished(final List<GeoFence> geoFenceList,
                                         int errorCode, String customId) {
        Message msg = Message.obtain();
        if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {
            fenceList = geoFenceList;
            msg.obj = customId;
            msg.what = 0;
        } else {
            msg.arg1 = errorCode;
            msg.what = 1;
        }
        handler.sendMessage(msg);
    }



    /**
     * 根据商家的详细地址获取经纬度
     */
    private void getLatlon(String cityName) {

        //cityName = "广东省深圳市龙岗区万科天誉A座2单元";
        final Context context = this;
        LatLng latLng = null;

        GeocodeSearch geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                            geocodeResult.getGeocodeAddressList().size() > 0) {

                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode = geocodeAddress.getAdcode();//区域编码

                        //绘制公司位置
                        LatLng latLng = new LatLng(latitude, longititude);
                        addCenterMarker(latLng);
                        Log.e("地理编码", geocodeAddress.getAdcode() + "");
                        Log.e("纬度latitude", latitude + "");//22.722589
                        Log.e("经度longititude", longititude + ""); //114.24575


                    } else {
                        ToastUtils.show(context, "地址名出错");
                    }
                }
            }
        });

        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName.trim(), "29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        str = false;
        mMapview.onDestroy();
        try {
            unregisterReceiver(mGeoFenceReceiver);
        } catch (Throwable e) {
        }

        if (null != fenceClient) {
            fenceClient.removeGeoFence();
        }
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapview.onPause();
    }


}
