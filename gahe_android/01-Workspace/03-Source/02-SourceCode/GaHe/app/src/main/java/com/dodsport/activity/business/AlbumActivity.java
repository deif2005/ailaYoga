package com.dodsport.activity.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.dialog.ClearCacheDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 相册
 */


public class AlbumActivity extends BaseActivity {


    @Bind(R.id.head_ivBack)
    ImageView headIvBack;
    @Bind(R.id.head_tvTitle)
    TextView headTvTitle;
    @Bind(R.id.rvAlbum)
    RecyclerView rvAlbum;

    private List<String> data = new ArrayList<>();
    private CommonAdapter<String> commonAdapter;
    private String TAG = "****相册--";
    private ClearCacheDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        //设置为网格布局,2列
        rvAlbum.setLayoutManager(new GridLayoutManager(this,2));
        initView();
    }

    private void initView() {
        showDialog();
        headIvBack.setVisibility(View.VISIBLE);
        headTvTitle.setVisibility(View.VISIBLE);
        headTvTitle.setText("相册");


        for (int i = 0; i < 5; i++) {
            data.add("相册"+i+"");
        }

        commonAdapter = new CommonAdapter<String>(this,R.layout.album_item_view,data) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView AlbumName = holder.getView(R.id.tvAlbumName);
                ImageView AlbumImage = holder.getView(R.id.ivAlbum);
                AlbumName.setText(data.get(position));

            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //添加相册
                if (data.size() == (position +1)){
                    Intent intent = new Intent(AlbumActivity.this,AddAlbumActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(AlbumActivity.this,PhotoShowActivity.class);
                    intent.putExtra("photoName",data.get(position));
                    startActivity(intent);
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                //ToastUtils.showToCenters(this,"");
                mDialog.setMsg("确认是否删除《"+data.get(position)+"》该相册\n\n\t删除后不可恢复!");
                mDialog.show();

                return false;
            }
        });

        rvAlbum.setAdapter(commonAdapter);

    }

    @OnClick(R.id.head_ivBack)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.head_ivBack:
                finish();
                break;
        }
    }
    private void showDialog(){
        mDialog = new ClearCacheDialog(this,0.7f);
        mDialog.setCancelable(true);
        mDialog.setTitle("温馨提示");
        mDialog.setPositiveButton("取消",new DialogClick());
        mDialog.setNegativeButton("确定", new DialogClick());

    }

    private class DialogClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.dialog_cancel:    //确定
                    ToastUtils.showToCenters(AlbumActivity.this,"删除该相册!",800);
                    if (mDialog!=null){
                        mDialog.cancel();
                    }
                    break;
                case R.id.dialog_enter:     //取消
                    if (mDialog!=null){
                        mDialog.cancel();
                    }
                    break;
            }

        }
    }
}
