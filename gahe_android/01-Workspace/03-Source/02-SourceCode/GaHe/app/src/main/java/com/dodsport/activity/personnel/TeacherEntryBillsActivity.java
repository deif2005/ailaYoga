package com.dodsport.activity.personnel;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.MyGridView;
import com.dodsport.activity.expenses.expenseaccountmanage.PhotoPickerActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.PhotoPreviewActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.SelectModel;
import com.dodsport.activity.expenses.expenseaccountmanage.intent.PhotoPickerIntent;
import com.dodsport.activity.expenses.expenseaccountmanage.intent.PhotoPreviewIntent;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.FileUtils;
import com.dodsport.utils.PermissionUtil;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.TransformationUtils;
import com.dodsport.utils.luban.Luban;
import com.dodsport.utils.luban.OnCompressListener;
import com.dodsport.weight.CircleImageView;
import com.dodsport.weight.popupwindow.SelectPicPopupWindow;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 填写教练入职单
 */
public class TeacherEntryBillsActivity extends BaseActivity {


    private final String TAG = "****教练资料填写--";
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvTeacherName)
    TextView mTvTeacherName;
    @Bind(R.id.UserHead)
    CircleImageView mUserHead;
    @Bind(R.id.gvTeacherEntry)
    MyGridView mGvTeacherEntry;
    @Bind(R.id.RadioButton1)
    RadioButton mRadioButton1;
    @Bind(R.id.RadioButton2)
    RadioButton mRadioButton2;
    @Bind(R.id.RadioButton3)
    RadioButton mRadioButton3;
    @Bind(R.id.btConservation)
    Button mBtConservation;
    @Bind(R.id.llConservation)
    LinearLayout mLlConservation;
    @Bind(R.id.evTeacherText)
    EditText mEvTeacherText;
    @Bind(R.id.TeacherText_ets)
    TextView mTeacherTextEts;

    public static final int LOCAL_PHOTO_ALBUM = 0; // Local photo album
    public static final int TAKING_PICTURES = 1; // Taking pictures
    private String mPicPath = "";
    /*获取当前系统的android版本号*/
    private int currentapiVersion = 0;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE;
    private int TAKIN_PACTURES_CAMERA;
    private SelectPicPopupWindow window;
    private static final String[] STRINGS = {"拍照", "相册获取"};
    private ArrayList<String> imagePaths = new ArrayList<>();
    private GridAdapter gridAdapter;
    private boolean isCutPicture = true;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private InputMethodManager manager;
    private ArrayList<BasicBinary> binaryList = new ArrayList<>();
    private ArrayList<BasicBinary> binaryHead = new ArrayList<>();
    private Handler mHandler;
    private int jobTitle = 1;
    private Intent intent;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_entry_bills);
        /**
         * 点击空白地方 键盘关闭
         * */
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ButterKnife.bind(this);
        try {
            initView();
            ManagementGridview();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void initView() {
        mHandler = new mHandler();
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("入职");
        GetAuthority();
        getSignature();
        intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        mTvTeacherName.setText(bundle.getString("employeeName"));

    }

    /**
     * Gridview管理方法
     */
    private void ManagementGridview() {
        //根据屏幕分辨率来分配 5列
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 5 ? 5 : cols;
        mGvTeacherEntry.setNumColumns(cols);

        // preview
        mGvTeacherEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);

                if ("000000".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(TeacherEntryBillsActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {

                    //在主页面可以查看大图
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(TeacherEntryBillsActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    intent.setCharacteristic("PublishDynamicActivity");
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        mGvTeacherEntry.setAdapter(gridAdapter);


    }

    @OnClick({R.id.head_ivBack, R.id.UserHead, R.id.RadioButton1, R.id.RadioButton2, R.id.RadioButton3,
             R.id.btConservation, R.id.llConservation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.UserHead:     //头像
                selectPhoto();
                break;
            case R.id.RadioButton1:
                mRadioButton1.setChecked(true);
                mRadioButton2.setChecked(false);
                mRadioButton3.setChecked(false);
                jobTitle = 3;
                break;
            case R.id.RadioButton2:
                mRadioButton2.setChecked(true);
                mRadioButton3.setChecked(false);
                mRadioButton1.setChecked(false);
                jobTitle = 2;
                break;
            case R.id.RadioButton3:
                mRadioButton3.setChecked(true);
                mRadioButton2.setChecked(false);
                mRadioButton1.setChecked(false);
                jobTitle = 1;
                break;
            case R.id.btConservation:   //保存
            case R.id.llConservation:
                getFile();
                break;
        }
    }


    private class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1009:
                    addEmployeeInfo();
                    break;

            }
        }
    }

    private void getFile(){

        if (binaryList.size()!=0){
            binaryList.clear();
        }
        new  Thread(){
            @Override
            public void run() {
                super.run();

        for (int i = 0; i < imagePaths.size(); i++) {
            if (imagePaths.get(i) != "000000") {
                File file = new File(imagePaths.get(i));
                Luban.get(TeacherEntryBillsActivity.this)
                        .load(file)
                        .putGear(Luban.THIRD_GEAR)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                try {
                                    BasicBinary binary1 = new FileBinary(file);
                                    binaryList.add(binary1);
                                    if (binaryList.size() == (imagePaths.size() - 1)) {
                                        Log.i(TAG, "压缩图片成功--->"+binaryList.size()+"");
                                        mHandler.sendEmptyMessage(1009);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过去出现问题时调用
                            }
                        }).launch();// TODO 开始压缩
            }
        }
            }
        }.start();

    }


    /**
     * 提交员工数据
     *
     * employeeName 员工姓名
     * phoneNum 员工电话
     * positionId   员工职位Id
     * empRela  员工角色(全职、兼职)
     * sex  员工性别
     * businessId   商家ID
     * storeId 门店Id
     * creator  创建人 用户ID
     * idCard 员工证件号
     * depId    是否 教练
     * */
    private void addEmployeeInfo(){

        //新增员工
        String employeeName = bundle.getString("employeeName");
        String phoneNum = bundle.getString("phoneNum");
        String positionId = bundle.getString("positionId");
         //就职状态
        Integer empRela = bundle.getInt("empRela");
        //性别
        Integer sex = bundle.getInt("sex");
         //职称标记
        Integer jobTitle = bundle.getInt("jobTitle");

        String depId = bundle.getString("depId");    //部门ID
        String businessId = bundle.getString("businessId"); //商家ID
        String creator = bundle.getString("creator");
        String idCard = bundle.getString("idCard");     //员工身份证号
        String storeId= bundle.getString("storeId");  //门店ID
        if (!TextUtils.isEmpty(mEvTeacherText.getText().toString())){

        }
        String text = mEvTeacherText.getText().toString();

        Log.i(TAG, "新增员工信息传参--->"+employeeName+"\t"+phoneNum+"\t"+idCard+"\tpositionId-->"+positionId+"\tempRela-->"+empRela+"\tsex-->"+sex+"\tbusinessId-->"+businessId+"\tdepId-->"+depId+"\tcreator-->"+creator+"\tjobTitle-->"+jobTitle+"\t");
        OperatingFloorRequest.addEmployeeInfo(employeeName, phoneNum, idCard,positionId, empRela, sex, businessId,depId, creator,storeId ,jobTitle,binaryList,binaryHead,text,new OnResponseListener<String>() {

            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                Log.i(TAG, "新增员工信息成功--->"+response.toString());
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (!resultBean.getCode().equals("0")) {
                    switch (resultBean.getCode()) {
                        case "4003":
                            ToastUtils.showToCenters(TeacherEntryBillsActivity.this,"提交失败!",800);
                            break;
                        case "5005":
                            ToastUtils.showToCenters(TeacherEntryBillsActivity.this,"该员工已存在!",800);
                            break;
                    }
                    return;
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(TeacherEntryBillsActivity.this,"提交失败!",800);
                Log.i(TAG, "新增员工信息失败--->"+response.toString());
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }


    private String getPictureName() {
        String fileRootDir = FileUtils.DEFAULT_FILE;
        if (!FileUtils.isExist(fileRootDir)) {
            FileUtils.createDir(fileRootDir);
        }
        return fileRootDir + UUID.randomUUID().toString() + ".jpg";
    }

    /**
     * 选择相册和拍照
     */
    private void selectPhoto() {
        window = new SelectPicPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.photograph:   //拍照

                        //*获取当前系统的android版本号*//
                        currentapiVersion = Build.VERSION.SDK_INT;
                        if (currentapiVersion >= 23) {
                            if (ContextCompat.checkSelfPermission(TeacherEntryBillsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(TeacherEntryBillsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                            }
                            GetCamera();

                        } else {
                            GetCamera();

                        }
                        break;
                    case R.id.albums:    // 从相册获取
                        try {
                            window.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(intent, LOCAL_PHOTO_ALBUM);
                            //Uri geturi = FileUtils.geturi(intent, MySettingActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        }, false, true, STRINGS);
        window.showAtLocation(mHeadIvBack, Gravity.BOTTOM, 0, 0);

    }


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    // 选择照片
                    case REQUEST_CAMERA_CODE:
                        ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                        Log.d(TAG, "list: " + "list = [" + list.size());
                        loadAdpater(list);
                        break;
                    // 预览
                    case REQUEST_PREVIEW_CODE:
                        ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                        Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                        loadAdpater(ListExtra);
                        break;
                    case LOCAL_PHOTO_ALBUM:
                        if (isCutPicture) {     //从相册中获取图片进来了
                            Uri originalUri = data.getData();
                            String realFilePath = getRealFilePath(TeacherEntryBillsActivity.this, originalUri);
                            File temp = new File(realFilePath);
                            setHeadPortrait(temp);
                        } else {                //相册获取
                            Uri uri = Uri.parse(mPicPath);
                            File temp = new File(mPicPath);
                            setHeadPortrait(temp);
                        }
                        break;
                    case TAKING_PICTURES:
                        if (isCutPicture) {
                            File temp = new File(mPicPath);
                            setHeadPortrait(temp);
                            //截取图片
//                        cutPicture(Uri.fromFile(temp));
                        }
                        break;
                }

/*                if (requestCode == LOCAL_PHOTO_ALBUM) {
                    if (isCutPicture) {     //从相册中获取图片进来了
                        Uri originalUri = data.getData();
                        String realFilePath = getRealFilePath(TeacherEntryBillsActivity.this, originalUri);
                        File temp = new File(realFilePath);
                        setHeadPortrait(temp);
                    } else {                //相册获取
                        Uri uri = Uri.parse(mPicPath);
                        File temp = new File(mPicPath);
                        setHeadPortrait(temp);
                    }
                } else if (requestCode == TAKING_PICTURES) {
                    if (isCutPicture) {

                        File temp = new File(mPicPath);
                        setHeadPortrait(temp);
                        //截取图片
//                        cutPicture(Uri.fromFile(temp));
                    }
                }*/
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    private void loadAdpater(ArrayList<String> paths) {
        try {
            if (imagePaths != null && imagePaths.size() > 0) {
                imagePaths.clear();
            }
            if (paths.contains("000000")) {
                paths.remove("000000");
            }
            paths.add("000000");
            imagePaths.addAll(paths);
            gridAdapter = new GridAdapter(imagePaths);
            mGvTeacherEntry.setAdapter(gridAdapter);

            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 编辑教练简介文字
     */
    public void getSignature() {

        mEvTeacherText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTeacherTextEts.setText((150 - s.length()) + "/150");
                if (s.length() >= 150) {
                    ToastUtils.showToCenter(TeacherEntryBillsActivity.this, "您编辑的文字长度超过150!", 0);
                }
            }
        });

    }


    /**
     * 把图片地址URL转换成String
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 获得头像File 进行压缩并上传到服务器
     */
    private void setHeadPortrait(File file) {
        try {
                    Picasso.with(TeacherEntryBillsActivity.this).load(file)
                            .resize(400,400)
                            .config(Bitmap.Config.RGB_565)
                            .error(R.mipmap.default_error)
                            .placeholder(R.mipmap.default_error)
                            .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                            .centerCrop()
                            .transform(TransformationUtils.zipImage(mUserHead)).into(mUserHead);

                } catch (Exception e) {
                    e.printStackTrace();
                }


        Luban.get(this)
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(final File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        try {
                            if (!file.equals("")) {

                                if (binaryHead.size()!=0){
                                    binaryHead.clear();
                                }
                                BasicBinary binary = new FileBinary(file);
                                binaryHead.add(binary);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                    }
                }).launch();        //开始压缩

    }

    /**
     * 如果用户拒绝获得权限，弹出提示框提示
     */
    private PermissionUtil.PermissionGrant mPermissionGrant = new PermissionUtil.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtil.CODE_CAMERA:
                case PermissionUtil.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(TeacherEntryBillsActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();

                    GetCamera();

                    break;
            }
        }
    };

    /**
     * 启动照相机，获取照片
     */
    private void GetCamera() {
        try {

            window.dismiss();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mPicPath = getPictureName();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPicPath)));
            startActivityForResult(intent, TAKING_PICTURES);

        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    /**
     * GridView适配器
     */
    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;

        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if (listUrls.size() == 4) {   //图片集合size
                listUrls.remove(listUrls.size() - 1);
            }
            inflater = LayoutInflater.from(TeacherEntryBillsActivity.this);
            //Log.i(TAG, "图片集合--> " + listUrls.toString() + "");
        }

        public int getCount() {
            return listUrls.size();
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String path = listUrls.get(position);
            if (path.equals("000000")) {
                holder.image.setImageResource(R.drawable.publish_add);
            } else {

//                try {
//                    Picasso.with(PublishDynamicActivity.this).load(path)
//                            .resize(400,400)
//                            .config(Bitmap.Config.RGB_565)
//                            .error(R.mipmap.default_error)
//                            .placeholder(R.mipmap.default_error)
//                            .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//                            .centerCrop()
//                            .transform(TransformationUtils.zipImage(holder.image)).into(holder.image);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                Glide.with(TeacherEntryBillsActivity.this)
                        .load(path)
//                        .placeholder(R.drawable.video_bg)
//                        .error(R.drawable.video_bg)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }


    //权限申请
    private void GetAuthority() {

           /*获取当前系统的android版本号*/
        currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= 23) {

            if (ContextCompat.checkSelfPermission(TeacherEntryBillsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(TeacherEntryBillsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }

        }

    }


    /**
     * 访问SD卡、相机权限的回调方法
     * 无论用户拒绝还是允许 都会回调这个方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults, permissions);
        if (requestCode == TAKIN_PACTURES_CAMERA) {

        }
    }

    /**
     * 可在此方法进行相对应的操作
     */
    private void doNext(int requestCode, int[] grantResults, String[] permissions) {

        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(TeacherEntryBillsActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请照相机权限
                    ActivityCompat.requestPermissions(TeacherEntryBillsActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            TAKING_PICTURES);
                    TAKIN_PACTURES_CAMERA = TAKING_PICTURES;
                }

            } else {
                PermissionUtil.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);

            }

        }
    }


}
