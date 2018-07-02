package com.dodsport.activity.expenses.expenseaccountmanage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dodsport.R;
import com.dodsport.activity.expenses.expenseaccountmanage.widget.ViewPagerFixed;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片预览
 *
 */
public class PhotoPreviewActivity extends AppCompatActivity implements PhotoPagerAdapter.PhotoViewClickListener {

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";
    public static final String CLASS_CHARACTERISTIC = "characteristic_item";

    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "preview_result";

    /**
     * 预览请求状态码
     */
    public static final int REQUEST_PREVIEW = 99;
    @Bind(R.id.phpoto_linear)
    LinearLayout mPhpotoLinear;

    private ArrayList<String> paths;
    private ViewPagerFixed mViewPager;
    private PhotoPagerAdapter mPagerAdapter;
    private int currentItem = 0;
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        ButterKnife.bind(this);

        paths = new ArrayList<>();
        ArrayList<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);

        if (pathArr != null) {
            paths.addAll(pathArr);
        }
        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);

        initViews();
        //根据图片标识来移除掉预加载图
        for (String path : paths) {
            if (path.equals("000000")){
                paths.remove(path);
            }
        }
        mPagerAdapter = new PhotoPagerAdapter(this, paths);
        mPagerAdapter.setPhotoViewClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateActionBarTitle();
            }

            @Override
            public void onPageSelected(int position) {
                String stringExtra = getIntent().getStringExtra(CLASS_CHARACTERISTIC);

                if (stringExtra.equals("MyIssueDynamicActivity")&& paths.size()>1){
                    //ViewPage滑动底部的标识点
                    for (int i=0;i<imageViews.length;i++){
                        if (i==position){
                            imageViews[i].setImageResource(R.drawable.shape_white_point);
                        }else {
                            imageViews[i].setImageResource(R.drawable.shape_gary_point_five);
                        }
                    }
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateActionBarTitle();
    }

    private void initViews() {

        String stringExtra = getIntent().getStringExtra(CLASS_CHARACTERISTIC);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.pickerToolbar);
        mViewPager = (ViewPagerFixed) findViewById(R.id.vp_photos);

        if (stringExtra.equals("MyIssueDynamicActivity")) {

            mPhpotoLinear.setBackgroundColor(getResources().getColor(R.color.zero));

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Context mContext = this;
            ImageView imageView;
            LinearLayout llPoint = (LinearLayout) findViewById(R.id.viewGroup);  //圆形的父布局;
            if (paths.size()>1){
                llPoint.setVisibility(View.VISIBLE);
                imageViews = new ImageView[paths.size()];
                for (int i = 0; i < paths.size(); i++) {

                    imageView = new ImageView(mContext);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                    layoutParams.setMargins(10, 0, 10, 0);
                    imageView.setLayoutParams(layoutParams);
                    imageViews[i] = imageView;
                    if (i != currentItem) {
                        imageViews[i].setImageResource(R.drawable.shape_gary_point_five);
                    } else if (currentItem == i){
                        imageViews[i].setImageResource(R.drawable.shape_white_point);
                    }
                    llPoint.addView(imageViews[i]);
                }
            }



            mToolbar.setVisibility(View.GONE);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        onBackPressed();
        overridePendingTransition(R.anim.zoom_enter,
                R.anim.zoom_exit);

//      overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    public void updateActionBarTitle() {
        getSupportActionBar().setTitle(
                getString(R.string.image_index, mViewPager.getCurrentItem() + 1, paths.size()));

    }

    @Override
    public void onBackPressed() {
        String stringExtra = getIntent().getStringExtra(CLASS_CHARACTERISTIC);

        if (!stringExtra.equals("MyIssueDynamicActivity")){
            Intent intent = new Intent();
            intent.putExtra(EXTRA_RESULT, paths);
            setResult(RESULT_OK, intent);
            finish();
        }else {paths.clear();}

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        // 删除当前照片
        if (item.getItemId() == R.id.action_discard) {
            final int index = mViewPager.getCurrentItem();
            final String deletedPath = paths.get(index);
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.deleted_a_photo,
                    Snackbar.LENGTH_LONG);
            if (paths.size() <= 1) {
                // 最后一张照片弹出删除提示
                // show confirm dialog
                new AlertDialog.Builder(this)
                        .setTitle(R.string.confirm_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                paths.remove(index);
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            } else {
                snackbar.show();
                paths.remove(index);
                mPagerAdapter.notifyDataSetChanged();
            }

            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (paths.size() > 0) {
                        paths.add(index, deletedPath);
                    } else {
                        paths.add(deletedPath);
                    }
                    mViewPager.setCurrentItem(index, true);
                    try {
                        mPagerAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
