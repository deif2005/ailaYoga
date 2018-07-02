package com.dodsport.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.dodsport.GaHeApplication;
import com.dodsport.R;
import com.dodsport.eventBus.CardTypeEvent;
import com.dodsport.model.POIBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.SignScopeBean;
import com.dodsport.model.StatusBean;
import com.dodsport.model.WiFiMACBean;
import com.dodsport.request.PunchTheClockRequest;
import com.dodsport.utils.AuthorityApplyForUtils;
import com.dodsport.utils.MacAddressUtils;
import com.dodsport.utils.NetUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.popupwindow.OnDutyTimePopupWindow;
import com.dodsport.weight.popupwindow.RangePopupWindow;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 考勤规则
 * @author Administrator
 */
public class PunchTheClockRuleActivity extends BaseActivity implements AMap.OnMapClickListener, LocationSource, AMapLocationListener, GeoFenceListener, GeocodeSearch.OnGeocodeSearchListener, AMap.InfoWindowAdapter {


    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.MapView)
    MapView mMapView;
    @Bind(R.id.my_set_tvScopeName)
    TextView mMySetTvScopeName;
    @Bind(R.id.my_set_llScope)
    LinearLayout mMySetLlScope;
    @Bind(R.id.my_set_tvWifiName)
    TextView mMySetTvWifiName;
    @Bind(R.id.my_set_tvAddWifi)
    TextView mMySetTvAddWifi;
    @Bind(R.id.tvForeShiftStartTime)
    TextView mTvForeShiftStartTime;
    @Bind(R.id.tvForeShiftEndTime)
    TextView mTvForeShiftEndTime;
    @Bind(R.id.tvNightShiftStartTime)
    TextView mTvNightShiftStartTime;
    @Bind(R.id.tvNightShiftEndTime)
    TextView mTvNightShiftEndTime;
    @Bind(R.id.etFurloughFatalism)
    EditText mEtFurloughFatalism;
    @Bind(R.id.etTWorkingFatalism)
    EditText mEtTWorkingFatalism;
    @Bind(R.id.llSaveSettings)
    LinearLayout mLlSaveSettings;



    private AMap aMap = null;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Activity mActivity;
    private View infoWindow = null;
    private String address = "";    //定位的地址
    private double companyLatitude = 0;
    private double companyLongitude = 0;
    private static final int REQUEST_CAMERA_CODE = 10;
    private PopupWindow mWindow;
    private CameraUpdate cameraUpdate;
    private  Marker BOLIAN;
    //考勤范围
    private int po = 0;
    private int mWIFI = 0;  //wifi
    private List<String> data = new ArrayList<>();
    private List<String> wifiData = new ArrayList<>();
    private List<WiFiMACBean> wiFiMAC = new ArrayList<>();
    private String wifiName = "";
    //wifiMAC地址
    private String connectedWifiMacAddress = "";
    //考勤范围
    private String radius = "100";
    private List<String> wifiList = new ArrayList<>();
    //wifi对象
    private WiFiMACBean wiFiMACBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_the_clock_rule);
        ButterKnife.bind(this);
        mActivity = this;
        EventBus.getDefault().register(this);
        //初始化地图控制器对象
        //针对6.0做权限申请
        boolean mAuthority = AuthorityApplyForUtils.getAAF(this);
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        initView();
        // 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        mMapView.onCreate(savedInstanceState);

    }


    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("考勤规则");

        aMap.setInfoWindowAdapter(this);//AMap类中

    }

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
                        mlocationClient.startLocation();//启动定位
                        return;
                    }
                    setText(signScopeBean.getDatas());
                } catch (Exception e) {
                    mlocationClient.startLocation();//启动定位
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mlocationClient.startLocation();//启动定位
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**初始化规则设置**/
    private void setText(SignScopeBean.DatasBean datas){
        List<SignScopeBean.DatasBean.SignTimeListBean> signTimeList = datas.getSignTimeList();
        for (int i = 0; i < signTimeList.size(); i++) {
            //1、表示 早班
            if (signTimeList.get(i).getSchedulingType()==1){
                if (!TextUtils.isEmpty(signTimeList.get(i).getFirstTime()) && !signTimeList.get(i).getFirstTime().trim().equals("请选择")){
                    mTvForeShiftStartTime.setText(signTimeList.get(i).getFirstTime().substring(0,5));
                }else {
                    mTvForeShiftStartTime.setText("请选择");
                }
                if (!TextUtils.isEmpty(signTimeList.get(i).getLastTime()) && !signTimeList.get(i).getLastTime().trim().equals("请选择")){
                    mTvForeShiftEndTime.setText(signTimeList.get(i).getLastTime().substring(0,5));
                }else {
                    mTvForeShiftEndTime.setText("请选择");
                }

                //2、表示 晚班
            }else if (signTimeList.get(i).getSchedulingType()==2){
                if (!TextUtils.isEmpty(signTimeList.get(i).getFirstTime()) && !signTimeList.get(i).getFirstTime().trim().equals("请选择")){
                    mTvNightShiftStartTime.setText(signTimeList.get(i).getFirstTime().substring(0,5));
                }else {
                    mTvNightShiftStartTime.setText("请选择");
                }
                if (!TextUtils.isEmpty(signTimeList.get(i).getLastTime()) && !signTimeList.get(i).getLastTime().trim().equals("请选择")){
                    mTvNightShiftEndTime.setText(signTimeList.get(i).getLastTime().substring(0,5));
                }else {
                    mTvNightShiftEndTime.setText("请选择");
                }
            }
        }

        mEtFurloughFatalism.setText(datas.getSignScope().getVacationDays()+"");
        mEtTWorkingFatalism.setText(datas.getSignScope().getReissueTimes()+"");
        radius = datas.getSignScope().getRadius();
        mMySetTvScopeName.setText(datas.getSignScope().getRadius()+"米");
        //办公WIFI
        WiFiMACBean wiFiMACBean = SPUtils.getWiFiMACBean(mActivity);
        if (!TextUtils.isEmpty(wiFiMACBean.getSSID())){
            mMySetTvWifiName.setText("办公WIFI\t"+"\t"+wiFiMACBean.getSSID());
            connectedWifiMacAddress = wiFiMACBean.getBSSID();
            mMySetTvAddWifi.setText("当前链接WIFI");
        }else {
            mMySetTvWifiName.setText("办公WIFI\t"+"\t");
            mMySetTvAddWifi.setText("添加WIFI");
        }

        if (!TextUtils.isEmpty(datas.getSignScope().getSignAdd()) && !TextUtils.isEmpty(datas.getSignScope().getLat())  && !TextUtils.isEmpty(datas.getSignScope().getLat())){
            double Lng = Double.parseDouble(datas.getSignScope().getLng());
            double Lat = Double.parseDouble(datas.getSignScope().getLat());
            /**显示商家位置*/
            showPOI(Lng,Lat,datas.getSignScope().getSignAdd());
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        //可点击选中
        aMap.setOnMapClickListener(this);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));    //设置地图缩放级别
        aMap.setPointToCenter(GaHeApplication.Width / 2, GaHeApplication.Height * 5 / 7);//设置中心点
        // 自定义系统定位蓝点
         myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        //连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;

//        //连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);

        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        //设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
//        myLocationStyle.showMyLocation(false);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }


    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(this).inflate(
                    R.layout.custom_info_window, null);
        }
        render(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        //如果想修改自定义Infow中内容，请通过view找到它并修改
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        LinearLayout llRevise = view.findViewById(R.id.llRevise);
        if (!TextUtils.isEmpty(address)) {
            tvAddress.setText(address);
        }
        //修改定位位置
        llRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, SearchPlaceActivity.class);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }
        });


    }

    /**显示地图标签*/
    private void showPOI(double Longitude,double Latitude, String Location){
        try {
            clearMarkers();
            aMap = null;
            BOLIAN = null;
            mMapView.onResume();
            infoWindow =null;
            if (aMap == null) {
                aMap = mMapView.getMap();
                myLocationStyle.showMyLocation(false);
                // 将自定义的 myLocationStyle 对象添加到地图上
                aMap.setMyLocationStyle(myLocationStyle);
                // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
                aMap.setMyLocationEnabled(true);
                // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            }
            UiSettings uiSettings = aMap.getUiSettings();
            // 通过UISettings.setZoomControlsEnabled(boolean)来设置缩放按钮是否能显示
            uiSettings.setZoomControlsEnabled(false);
            companyLatitude = Latitude;
            companyLongitude = Longitude;
            address = Location;
            //可视化区域，将指定位置指定到屏幕中心位置
            cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(new CameraPosition(new LatLng(companyLatitude,companyLongitude), 17, 0, 0));
            aMap.moveCamera(cameraUpdate);
            aMap.setOnMapClickListener(this);
            drawMarkers(new LatLng(companyLatitude,companyLongitude));//绘制小蓝气泡
            getInfoWindow(BOLIAN);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CAMERA_CODE){
            switch (requestCode){
                case REQUEST_CAMERA_CODE:
                POIBean mPOIBean = (POIBean) data.getSerializableExtra("object");
                    showPOI(companyLongitude = mPOIBean.getLongitude()
                            ,companyLatitude = mPOIBean.getLatitude()
                            ,address = mPOIBean.getLocation());
                    break;
                default:
                    break;

            }
        }

    }

    private void drawMarkers(LatLng latLng) {
        BOLIAN = aMap.addMarker(new MarkerOptions()
                .position(latLng)
//                .title("诺姆瑜伽")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point))
//                        .perspective(true)
//                        .visible(false)
                .draggable(true));// 设置远小近大效果,2.1.0版本新增


        BOLIAN.showInfoWindow();
    }

    //删除指定Marker
    private void clearMarkers() {
        //获取地图上所有Marker
        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
            marker.remove();//移除当前Marker
//            if (marker.getObject() instanceof ) {
//            }
        }
        mMapView.invalidate();//刷新地图
    }



    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(CardTypeEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null){
            return;
        }
        //获取用户选中教室
        if (event.getType().equals("start")) {          //早班时间
            mTvForeShiftStartTime.setText(event.getPositions()+":"+event.getCardType());
            mTvForeShiftEndTime.setText(event.getMsg()+":"+event.getName());
        }else if (event.getType().equals("end")){       //晚班时间
            mTvNightShiftStartTime.setText(event.getPositions()+":"+event.getCardType());
            mTvNightShiftEndTime.setText(event.getMsg()+":"+event.getName());
        }else if (event.getType().equals("range")){     //考勤范围
            po = event.getPosition();
            radius = (po*100)+"";
            mMySetTvScopeName.setText(data.get(po));
        }else if (event.getType().equals("wifi")){      //wifi
            if (!wifiList.get(event.getPosition()).equals(connectedWifiMacAddress)){
                ToastUtils.showToCenters(mActivity,"请连接选择的WIFI后重试!",1000);
                return;
            }
            mWIFI = event.getPosition();
            mMySetTvWifiName.setText("办公WIFI\t"+"\t"+wifiData.get(mWIFI));
            mMySetTvAddWifi.setText("当前链接WIFI");
            wifiName = wiFiMAC.get(event.getPosition()).getBSSID();
            wiFiMACBean =new WiFiMACBean();
            wiFiMACBean.setSSID(wifiData.get(mWIFI));
            wiFiMACBean.setBSSID(wifiName);
        }
        if (mWindow != null) {
            mWindow.dismiss();
            mWindow = null;
        }
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设定间隔时间
            mLocationOption.setInterval(600000);
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);

            //获取签到规则
            querySignTimeByStoreId();


            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            //mlocationClient.startLocation();//启动定位
        }
    }


    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                address = amapLocation.getAddress();
                companyLatitude = amapLocation.getLatitude();
                companyLongitude = amapLocation.getLongitude();
                drawMarkers(new LatLng(companyLatitude,companyLongitude));//绘制小蓝气泡
                mlocationClient.stopLocation();
            } else {
                ToastUtils.showToCenters(mActivity,"获取位置失败,请检查网络或者稍后重试!",1000);
            }
        }
    }


    @Override
    public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    @OnClick({R.id.head_ivBack,R.id.my_set_tvScopeName, R.id.my_set_tvAddWifi, R.id.tvForeShiftStartTime, R.id.tvForeShiftEndTime,
            R.id.tvNightShiftStartTime, R.id.tvNightShiftEndTime,
            R.id.llSaveSettings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.my_set_tvScopeName:   //考勤范围
                String range = "range";
                    data.clear();
                int integer = Integer.parseInt(radius);
                for (int i = 1; i < 11; i++) {
                    if (integer == i*100){
                        if (po == 0){
                            po = i - 1;
                        }else {
                            po = i ;
                        }
                    }
                    data.add(i*100+"米");
                }
                mWindow = new RangePopupWindow(mActivity,true,range,data,po,"");
                mWindow.showAtLocation(mTvForeShiftEndTime, Gravity.CENTER,0,0);
                break;
            case R.id.my_set_tvAddWifi:     //连接Wifi

                //网络判断
                boolean connected = NetUtils.isConnected(this);
                if (connected) {
                    boolean wifi = NetUtils.isWifi(this);
                    if (wifi) {
                        wifiData.clear();
                        wiFiMAC.clear();
                        wifiList.clear();
                        /**获取当前连接WIFI的路由器MAC地址*/
                        //本机连接路由器MAC地址
                        connectedWifiMacAddress = MacAddressUtils.getConnectedWifiMacAddress(mActivity);
                        wiFiMAC = MacAddressUtils.getWiFiMAC(mActivity);
                        for (int i = 0; i < wiFiMAC.size(); i++) {
                            if (!TextUtils.isEmpty(wiFiMAC.get(i).getSSID())){
                                wifiData.add(wiFiMAC.get(i).getSSID());
                                wifiList.add(wiFiMAC.get(i).getBSSID());

                            }
                        }
                        for (int i = 0; i < wifiData.size(); i++) {
                            if (wifiData.get(i).equals(connectedWifiMacAddress)){
                                mWIFI = i;
                            }
                        }
                        //String mIp = getLocalIpAddress();   //IP地址
                        //String mMAC = getLocalMacAddress(mActivity); //手机的MAC地址
                    }else {
                        ToastUtils.showToCenters(mActivity,"请连接WIFI!",1000);
                        return;
                    }
                }else {
                    ToastUtils.showToCenters(mActivity,"请连接网络!",1000);
                    return;
                }
                String Wifi = "wifi";
                mWindow = new RangePopupWindow(mActivity,true,Wifi,wifiData,mWIFI,"");
                mWindow.showAtLocation(mTvForeShiftEndTime, Gravity.CENTER,0,0);
                break;
            case R.id.tvForeShiftStartTime:     //早班
            case R.id.tvForeShiftEndTime:
                String startTime = "start";
                mWindow = new OnDutyTimePopupWindow(mActivity,false,startTime,"");
                mWindow.showAtLocation(mTvForeShiftEndTime, Gravity.BOTTOM,0,0);
                break;
            case R.id.tvNightShiftStartTime:    //晚班
            case R.id.tvNightShiftEndTime:
                String endTime = "end";
                mWindow = new OnDutyTimePopupWindow(mActivity,false,endTime,"");
                mWindow.showAtLocation(mTvForeShiftEndTime, Gravity.BOTTOM,0,0);
                break;
            case R.id.llSaveSettings:       //保存设置
                if (TextUtils.isEmpty(mEtFurloughFatalism.getText().toString())){
                    ToastUtils.showToCenters(mActivity,"请输入可请假天数!",1000);
                    return;

                }else if (TextUtils.isEmpty(mEtTWorkingFatalism.getText().toString())){
                    ToastUtils.showToCenters(mActivity,"请输入可补卡次数!",1000);
                    return;
                }
                saveSettings();
                break;
            default:
                break;
        }
    }

    /**提交设置*/

    /**
     * 签到规则
     *
     * token	string		是	口令
     * radius	String		是	签到范围半径;单位米
     * storeId	String		是	分店id
     * morningShift	String			早班时间
     * nightShift	String			晚班时间
     * wifiStr	String			Wifi地址字符串(以逗号拼接)
     * creatorId	String		是	创建人id
     * vacationDays	String		是	可休假天数
     * reissueTimes	String		是	可补卡次数
     * */
    private void saveSettings() {
        String nightShift = "";
        String morningShift = "";
        String trim = mMySetTvScopeName.getText().toString().trim();
        final String radius = trim.substring(0, (trim.length() - 1));
        String storeId = SPUtils.getUserDataBean(mActivity).getStoreId();
        //早班时间
        if (!mTvForeShiftStartTime.getText().toString().trim().equals("请选择") && !mTvForeShiftEndTime.getText().toString().trim().equals("请选择")){
            String startTime = mTvForeShiftStartTime.getText().toString();
            String endTime = mTvForeShiftEndTime.getText().toString().trim();
            morningShift = startTime+":00"+","+endTime+":00";
        }
        //晚班时间
        if (!mTvNightShiftStartTime.getText().toString().trim().equals("请选择") && !mTvNightShiftEndTime.getText().toString().trim().equals("请选择")){
            String trim1 = mTvNightShiftStartTime.getText().toString().trim();
            String trim2 = mTvNightShiftEndTime.getText().toString().trim();
            nightShift = trim1+":00"+","+ trim2+":00";
        }

        String creatorId = SPUtils.getUserDataBean(mActivity).getId();
        String vacationDays = mEtFurloughFatalism.getText().toString().trim();
        String reissueTimes = mEtTWorkingFatalism.getText().toString().trim();
        //Log.i("*****", "传参--->wifiName-->"+wifiName+"\tcompanyLongitude-->"+companyLongitude+"\tcompanyLatitude-->"+companyLatitude+"\t"+address+"\t");
       PunchTheClockRequest.signSet(radius, storeId, morningShift, nightShift, wifiName, creatorId, vacationDays, reissueTimes,
              companyLongitude+"",companyLatitude+"",address,new OnResponseListener<String>() {
           @Override
           public void onStart(int what) {

           }

           @Override
           public void onSucceed(int what, Response<String> response) {
               try {
                   StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                   ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                   if (!resultBean.getCode().equals("0")){
                       ToastUtils.showToCenters(mActivity,"规则设置失败,请稍后重试!",1000);
                       return;
                   }
                   ToastUtils.showToCenters(mActivity,"规则设置成功!",1000);
                   if (wiFiMACBean != null){
                       SPUtils.setWiFiMACBean(mActivity,wiFiMACBean);
                   }

               } catch (Exception e) {
                   ToastUtils.showToCenters(mActivity,"规则设置失败,请稍后重试!",1000);
                   e.printStackTrace();
               }

           }

           @Override
           public void onFailed(int what, Response<String> response) {
               ToastUtils.showToCenters(mActivity,"规则设置失败,请稍后重试!",1000);
           }

           @Override
           public void onFinish(int what) {

           }
       });
    }


}
