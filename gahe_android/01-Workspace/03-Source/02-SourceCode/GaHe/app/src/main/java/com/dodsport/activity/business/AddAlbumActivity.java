package com.dodsport.activity.business;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAlbumActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.evAlbumName)
    EditText mEvAlbumName;
    @Bind(R.id.evAlbumDescribe)
    EditText mEvAlbumDescribe;
    @Bind(R.id.btOKAdd)
    Button mBtOKAdd;
    @Bind(R.id.llOKAdd)
    LinearLayout mLlOKAdd;
    public String TAG ="****添加相册--";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("添加相册");
    }

    @OnClick({R.id.head_ivBack, R.id.btOKAdd, R.id.llOKAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.btOKAdd:  //提交
            case R.id.llOKAdd:
                addAlbum();
                break;
        }
    }

    /**添加相册*/
    private void addAlbum(){
        UserDataBean.DatasBean.BusiEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
        String storeId = userDataBean.getStoreId();
        String describes = mEvAlbumDescribe.getText().toString();
        String albumName = mEvAlbumName.getText().toString();
        String creator = userDataBean.getId();
        OperatingFloorRequest.addAlbumInfo(storeId, describes, albumName, creator, new OnResponseListener<String>() {


            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "成功-->"+response.toString()+"");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "失败-->"+response.get().toString()+"");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
