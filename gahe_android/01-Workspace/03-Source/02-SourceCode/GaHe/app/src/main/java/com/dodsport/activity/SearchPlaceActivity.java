package com.dodsport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.dodsport.R;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.POIBean;
import com.dodsport.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 定位搜索
 */
public class SearchPlaceActivity extends BaseActivity {


    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.dialog_serach_btn_search)
    ImageButton mDialogSerachBtnSearch;
    @Bind(R.id.dialog_search_et)
    EditText mDialogSearchEt;
    @Bind(R.id.dialog_search_recyclerview)
    RecyclerView mDialogSearchRecyclerview;
    private Activity mActivity;
    private String TAG = "****搜索定位--";
    private PoiSearch poiSearch;
    private CommonAdapter<POIBean> mCommonAdapter;
    private static final int REQUEST_CAMERA_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_place);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {

        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("修改位置");
        mDialogSearchRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        getSearch();
    }


    @OnClick({R.id.head_ivBack, R.id.dialog_serach_btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.dialog_serach_btn_search:     //搜索
                if(!TextUtils.isEmpty(mDialogSearchEt.getText().toString().trim())){
                    getSearch();
                }else {
                    ToastUtils.showToCenters(mActivity,"请输入关键字!",1000);
                    return;
                }
                break;
        }
    }

    public void getSearch() {
        mDialogSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String content = mDialogSearchEt.getText().toString().trim();
                //判断内容不为空
                if (null != content && !content.isEmpty()) {
                    String newText = mDialogSearchEt.getText().toString().trim();
                    //通过Query设置搜索条件,第一个参数为搜索内容,第二个参数为搜索类型，第三个参数为搜索范围（空字符串代表全国）。
                    PoiSearch.Query query = new PoiSearch.Query(newText, "", "");
                    poiSearch = new PoiSearch(mActivity, query);
                    poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                        @Override
                        public void onPoiSearched(PoiResult poiResult, int errcode) {

                            ArrayList<POIBean> mPOIBeanList = new ArrayList<>();
                            //判断搜索成功
                            if (errcode == 1000) {
                                if (null != poiResult && poiResult.getPois().size() > 0) {
                                    for (int i = 0; i < poiResult.getPois().size(); i++) {
                                        POIBean mPOIBean = new POIBean();
                                        mPOIBean.setAdName(poiResult.getPois().get(i).getAdCode()+","+poiResult.getPois().get(i).getAdName());
                                        mPOIBean.setBusinessArea(poiResult.getPois().get(i).getBusinessArea());
                                        mPOIBean.setCityName(poiResult.getPois().get(i).getCityCode()+","+poiResult.getPois().get(i).getCityName());
                                        mPOIBean.setLatitude(poiResult.getPois().get(i).getLatLonPoint().getLatitude());
                                        mPOIBean.setLongitude(poiResult.getPois().get(i).getLatLonPoint().getLongitude());
                                        mPOIBean.setSnippet(poiResult.getPois().get(i).getSnippet());
                                        mPOIBean.setItle(poiResult.getPois().get(i).getTitle());
                                        mPOIBean.setLocation(poiResult.getPois().get(i).getCityName() + "" + poiResult.getPois().get(i).getAdName() + "" + poiResult.getPois().get(i).getSnippet() + "");
                                        mPOIBeanList.add(mPOIBean);
                                        //Log.i(" ", "POI 的行政区划代码和名称=" + poiResult.getPois().get(i).getAdCode()+","+poiResult.getPois().get(i).getAdName());
                                        //Log.i("TAG_MAIN", "POI的所在商圈=" + poiResult.getPois().get(i).getBusinessArea());
                                        //Log.i("TAG_MAIN", "POI的城市编码与名称=" + poiResult.getPois().get(i).getCityCode()+","+poiResult.getPois().get(i).getCityName());
                                        //Log.i("TAG_MAIN", "POI 的经纬度=" + poiResult.getPois().get(i).getLatLonPoint());
                                        //Log.i("TAG_MAIN", "POI的地址=" + poiResult.getPois().get(i).getSnippet());
                                        //Log.i("TAG_MAIN", "POI的名称=" + poiResult.getPois().get(i).getTitle());
                                        //Log.i("TAG_MAIN", "获取到的数据-->"+"--->\t"+poiResult.getPois().get(i).getCityName()+""+poiResult.getPois().get(i).getAdName()+""+poiResult.getPois().get(i).getSnippet()+"");

                                    }
                                }
                            }
                            //加载数据
                            adapter(mPOIBeanList);
                        }

                        @Override
                        public void onPoiItemSearched(PoiItem poiItem, int i) {

                        }
                    });
                    poiSearch.searchPOIAsyn();
                }
            }
        });
    }

    //加载适配器
    private void adapter(final ArrayList<POIBean> mPOIBeanList) {
        mCommonAdapter = new CommonAdapter<POIBean>(mActivity,R.layout.search_location_item,mPOIBeanList) {
            @Override
            protected void convert(ViewHolder holder, POIBean poiBean, int position) {
                TextView poiName = holder.getView(R.id.poiName);
                TextView poiLocation = holder.getView(R.id.poiLocation);

                poiName.setText(poiBean.getItle());
                poiLocation.setText(poiBean.getSnippet());
            }
        };

        //点击事件
        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra("object",mPOIBeanList.get(position));
                    setResult(REQUEST_CAMERA_CODE,intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mDialogSearchRecyclerview.setAdapter(mCommonAdapter);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        poiSearch = null;
    }
}
