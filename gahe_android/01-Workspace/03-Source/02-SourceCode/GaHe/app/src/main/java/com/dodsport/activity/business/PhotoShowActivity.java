package com.dodsport.activity.business;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.business.imageUtil.MNImageBrowser;
import com.dodsport.activity.expenses.expenseaccountmanage.Image;
import com.dodsport.activity.expenses.expenseaccountmanage.PhotoPickerActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.PhotoPreviewActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.SelectModel;
import com.dodsport.activity.expenses.expenseaccountmanage.intent.PhotoPickerIntent;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.utils.TransformationUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoShowActivity extends BaseActivity {


    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.rvPhoto)
    RecyclerView mRvPhoto;
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.AllPhoto)
    Button mAllPhoto;
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.llDelete)
    LinearLayout mLlDelete;
    @Bind(R.id.rlBottom)
    RelativeLayout mRlBottom;
    @Bind(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @Bind(R.id.ivDeleteImage)
    ImageView mIvDeleteImage;
    private ImageView imageView;
    private Context mContext;
    public View mask;
    public ImageView indicator;

    private ArrayList<String> sourceImageList = new ArrayList<>();
    private ArrayList<String> ImageList = new ArrayList<>();
    private ArrayList<String> dataImage = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;
    private boolean edit = false;
    private boolean edit2 = false;
    private List<Image> mImages = new ArrayList<>();
    private ArrayList<Image> mSelectedImages = new ArrayList<>();
    private int mItemSize;
    private boolean showSelectIndicator = true;
    private String TAG = "****相册--";
    private ArrayList<String> imagePaths = new ArrayList<>();
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_show);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        mRvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvOK.setText("编辑");
        Intent intent = getIntent();
        String photoName = intent.getStringExtra("photoName");
        mHeadTvTitle.setText(photoName);
        getImage();

        Adapter();
    }

    private void Adapter() {

        mCommonAdapter = new CommonAdapter<String>(this, R.layout.photo_item_view, ImageList) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                try {
                    imageView = holder.getView(R.id.imageView);
                    mask = holder.getView(R.id.mask);
                    indicator = holder.getView(R.id.checkmark);
                    if (edit) {
                        indicator.setVisibility(View.VISIBLE);
                    } else {
                        indicator.setVisibility(View.GONE);
                    }
                    // Picasso.with(PhotoShowActivity.this).load(sourceImageList.get(position)).into(imageView);
                    if (position <= (imagePaths.size() - 1) && edit2) {
                        Log.i(TAG, "显示图片进来--->" + position + "");
                        Glide.with(PhotoShowActivity.this)
                                .load(ImageList.get(position))
                                .placeholder(R.drawable.live_continue)
                                .error(R.drawable.live_continue)
                                .centerCrop()
                                .crossFade()
                                .into(imageView);
                    } else {
                        Picasso.with(PhotoShowActivity.this).load(ImageList.get(position))
                                .resize(400, 400)
                                .config(Bitmap.Config.RGB_565)
                                .error(R.drawable.live_continue)
                                .placeholder(R.drawable.live_continue)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .centerCrop()
                                .transform(TransformationUtils.zipImage(imageView)).into(imageView);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (edit && indicator.getVisibility() == View.VISIBLE) {
                    Log.i(TAG, "点击:-->" + ImageList.get(position) + "");
//                    bindData(mSelectedImages.get(position));
                } else {
                    MNImageBrowser.showImageBrowser(mContext, holder.itemView, position, ImageList);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mRvPhoto.setAdapter(mCommonAdapter);

    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        mSelectedImages.clear();
        for (String path : resultList) {
            Image image = getImageByPath(path);
            if (image != null) {
                mSelectedImages.add(image);
            }
        }
    }

    private Image getImageByPath(String path) {
        if (mImages != null && mImages.size() > 0) {
            for (Image image : mImages) {
                if (image.path.equalsIgnoreCase(path)) {
                    return image;
                }
            }
        }
        return null;
    }


    private void bindData(final Image data) {
//        if(data == null) return;
        // 处理单选和多选状态
        if (showSelectIndicator) {
            indicator.setVisibility(View.VISIBLE);
            if (mSelectedImages.contains(data)) {
                // 设置选中状态
                indicator.setImageResource(R.mipmap.btn_selected);
                mask.setVisibility(View.VISIBLE);
            } else {
                // 未选择
                indicator.setImageResource(R.mipmap.btn_unselected);
                mask.setVisibility(View.GONE);
            }
            select(data);
//            mCommonAdapter.notifyItemChanged(position);
//            mCommonAdapter.notifyItemRemoved(position);
        } else {
            indicator.setVisibility(View.GONE);
        }
        File imageFile = new File(data.path);

        Picasso.with(PhotoShowActivity.this).load(imageFile)
                .resize(400, 400)
                .config(Bitmap.Config.RGB_565)
                .error(R.drawable.live_continue)
                .placeholder(R.drawable.live_continue)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .centerCrop()
                .transform(TransformationUtils.zipImage(imageView)).into(imageView);

//        if(mItemSize > 0) {
//            // 显示图片
//            Glide.with(mContext)
//                    .load(sourceImageList.get(position))
//                    .placeholder(R.mipmap.default_error)
//                    .error(R.mipmap.default_error)
//                    .override(mItemSize, mItemSize)
//                    .centerCrop()
//                    .into(imageView);
//        }
    }

    /**
     * 选择某个图片，改变选择状态
     *
     * @param image
     */
    public void select(Image image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
        } else {
            mSelectedImages.add(image);
        }
        mCommonAdapter.notifyDataSetChanged();
    }


    /**
     * 获取图片
     */
    private void getImage() {
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-12-18380140_455327614813449_854681840315793408_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-10-18382517_1955528334668679_3605707761767153664_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-09-18443931_429618670743803_5734501112254300160_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-08-18252341_289400908178710_9137908350942445568_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-05-18251898_1013302395468665_8734429858911748096_n.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/61e74233ly1feuogwvg27j20p00zkqe7.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-11-17881546_248332202297978_2420944671002853376_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-12-17662441_1675934806042139_7236493360834281472_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-13-17882785_926451654163513_7725522121023029248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-14-17881962_1329090457138411_8289893708619317248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-11-17881546_248332202297978_2420944671002853376_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-12-17662441_1675934806042139_7236493360834281472_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-13-17882785_926451654163513_7725522121023029248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-14-17881962_1329090457138411_8289893708619317248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-12-18380140_455327614813449_854681840315793408_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-10-18382517_1955528334668679_3605707761767153664_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-09-18443931_429618670743803_5734501112254300160_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-08-18252341_289400908178710_9137908350942445568_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-05-18251898_1013302395468665_8734429858911748096_n.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/61e74233ly1feuogwvg27j20p00zkqe7.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-11-17881546_248332202297978_2420944671002853376_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-12-17662441_1675934806042139_7236493360834281472_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-13-17882785_926451654163513_7725522121023029248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-14-17881962_1329090457138411_8289893708619317248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-11-17881546_248332202297978_2420944671002853376_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-12-17662441_1675934806042139_7236493360834281472_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-13-17882785_926451654163513_7725522121023029248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-14-17881962_1329090457138411_8289893708619317248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
        ImageList.addAll(sourceImageList);
        setDefaultSelected(sourceImageList);
    }


    @OnClick({R.id.head_ivBack, R.id.head_tvOK, R.id.head_ivOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.head_tvOK:
                edit = true;
                mRlBottom.setVisibility(View.VISIBLE);
                mCommonAdapter.notifyDataSetChanged();
                mHeadTvOK.setVisibility(View.GONE);
                mHeadIvOK.setVisibility(View.VISIBLE);
                mHeadIvOK.setImageResource(R.drawable.tian_jia_an_niu);
                break;
            case R.id.head_ivOK:        //添加图片
                PhotoPickerIntent intent = new PhotoPickerIntent(PhotoShowActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(20); // 最多选择照片数量，默认为20
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent, REQUEST_CAMERA_CODE);    //选择图片
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    //Log.d(TAG, "list: " + "list = [" + list.size());
                    Log.i(TAG, "选中图片集合-->" + list.toString() + "");
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    //Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }

    }


    private void loadAdpater(ArrayList<String> paths) {
        try {
            if (imagePaths != null && imagePaths.size() > 0) {
                imagePaths.clear();
            }
            if (paths.contains("000000")) {
                paths.remove("000000");
            }
            imagePaths.addAll(paths);
            Log.i(TAG, "选中后的图片--->" + imagePaths.toString() + "");
            ImageList.clear();
            ImageList.addAll(imagePaths);
            ImageList.addAll(sourceImageList);
            edit = false;
            edit2 = true;
            mHeadTvOK.setVisibility(View.VISIBLE);
            mHeadIvOK.setVisibility(View.GONE);
            mRlBottom.setVisibility(View.GONE);
            mCommonAdapter.notifyDataSetChanged();

            //JSONArray obj = new JSONArray(imagePaths);
            //Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
