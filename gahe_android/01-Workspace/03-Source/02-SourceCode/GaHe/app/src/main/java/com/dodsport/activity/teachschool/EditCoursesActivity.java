package com.dodsport.activity.teachschool;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.CourseBean;
import com.dodsport.model.CourseTypeListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.FileUtils;
import com.dodsport.utils.ImageUtils;
import com.dodsport.utils.PermissionUtil;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.luban.Luban;
import com.dodsport.utils.luban.OnCompressListener;
import com.dodsport.weight.dialog.LoadingDialog;
import com.dodsport.weight.popupwindow.SelectPicPopupWindow;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑课程信息
 */
public class EditCoursesActivity extends BaseActivity {


    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.ettext1)
    EditText mEttext1;
    @Bind(R.id.ettext2)
    EditText mEttext2;
    @Bind(R.id.RadioButton5)
    RadioButton mRadioButton5;
    @Bind(R.id.RadioButton6)
    RadioButton mRadioButton6;
    @Bind(R.id.RadioButton7)
    RadioButton mRadioButton7;
    @Bind(R.id.RadioButton9)
    RadioButton mRadioButton9;
    @Bind(R.id.RadioButton10)
    RadioButton mRadioButton10;
    @Bind(R.id.textView8)
    TextView mTextView8;
    @Bind(R.id.RadioButton13)
    RadioButton mRadioButton13;
    @Bind(R.id.RadioButton14)
    RadioButton mRadioButton14;
    @Bind(R.id.ettext3)
    EditText mEttext3;
    @Bind(R.id.ivCourse)
    ImageView mIvCourse;
    @Bind(R.id.rvCourseType)
    RecyclerView mRvCourseType;
    @Bind(R.id.btConservation)
    Button mBtConservation;
    @Bind(R.id.btCancel)
    Button mBtCancel;
    @Bind(R.id.icon_Delete)
    ImageView mIconDelete;


    private final static int SKIP_CODE = 10;
    private Intent mIntent;
    private String TAG = "***编辑--";
    private String courseMeans = "2";   //授课形式
    private String courseStatus = "2";  //课程开启状态
    private String isExperience = "2";  //课程是否支持体验
    private Activity mActivity;
    private String courseId = "";       //课程ID
    private CommonAdapter<CourseTypeListBean.DatasBean.CourseTypeList> mCommonAdapter;
    private int CourseType = 0;     //标记选中的是哪个课程种类
    private String CourseTypeId = "";//选中课程种类的ID
    private int duration = 0;
    private SelectPicPopupWindow window;//拍照
    public static final int LOCAL_PHOTO_ALBUM = 0; // Local photo album
    public static final int TAKING_PICTURES = 1; // Taking pictures
    public static final int CUT_OUT_THE_PICTURE = 2; // Cut out the picture
    private String mPicPath = "";
    /*获取当前系统的android版本号*/
    private int currentapiVersion = 0;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE;
    private static final String[] STRINGS = {"拍照", "相册获取"};
    private int mOutputX = 300, mOutputY = 300;
    private boolean isCutPicture = true;
    private ArrayList<BasicBinary> binaryList = new ArrayList<>();//图片
    private String isUpdateIcon = "1"; //标识有没有操作图片
    private LoadingDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_courses);
        ButterKnife.bind(this);
        mActivity = this;
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mRvCourseType.setLayoutManager(new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false));
        mIntent = getIntent();
        courseId = mIntent.getStringExtra("CourseId");

        if (courseId.equals("add")) {       //创建课程进来
            mHeadTvTitle.setText("创建课程");
            getListCourseType();    //获取课程种类
        } else {     //编辑页面进来
            mHeadTvTitle.setText("编辑课程");
            getCourseData();

        }


    }

    /**
     * 获取课程类型列表
     */
    private void getListCourseType() {
        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        OperatingFloorRequest.listCourseType(businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                CourseTypeListBean courseTypeListBean = JSON.parseObject(response.get(), CourseTypeListBean.class);
                if (!courseTypeListBean.getResult().getCode().equals("0")) {
                    ToastUtils.showToCenters(mActivity, "获取课程类型失败!", 1000);
                    return;
                }
                List<CourseTypeListBean.DatasBean.CourseTypeList> courseTypeList = courseTypeListBean.getDatas().getCourseTypeList();
                CourseTypeAdapter(courseTypeList);

                //Log.i(TAG, "获取课程列表成功!--->" + response.get().toString() + "");
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity, "获取课程类型失败!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 加载课程类型列表 适配器
     */
    private void CourseTypeAdapter(List<CourseTypeListBean.DatasBean.CourseTypeList> courseTypeList) {
        mCommonAdapter = new CommonAdapter<CourseTypeListBean.DatasBean.CourseTypeList>(mActivity, R.layout.course_type_item, courseTypeList) {
            @Override
            protected void convert(ViewHolder holder, final CourseTypeListBean.DatasBean.CourseTypeList courseTypeList, final int position) {

                final RadioButton rbCourseType = holder.getView(R.id.rbCourseType);
                if (position == CourseType) {
                    CourseTypeId = courseTypeList.getId();
                    rbCourseType.setChecked(true);
                } else {
                    rbCourseType.setChecked(false);
                }
                rbCourseType.setText(courseTypeList.getCourseTypeName());

                //课程类型点击事件
                rbCourseType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CourseType = position;
                        //课程种类ID
                        CourseTypeId = courseTypeList.getId();
                        rbCourseType.setChecked(true);
                        mCommonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        mRvCourseType.setAdapter(mCommonAdapter);
    }

    @OnClick({R.id.head_ivBack, R.id.RadioButton5, R.id.RadioButton6, R.id.RadioButton7,R.id.icon_Delete,
            R.id.RadioButton9, R.id.RadioButton10, R.id.RadioButton13, R.id.RadioButton14, R.id.ivCourse, R.id.btConservation, R.id.btCancel})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {
                case R.id.head_ivBack:
                case R.id.btCancel:     //取消
                    finish();
                    break;
                case R.id.RadioButton5:
                    courseMeans("1");
                    break;
                case R.id.RadioButton6:
                    courseMeans("2");
                    break;
                case R.id.RadioButton7:
                    courseMeans("3");
                    break;
                case R.id.RadioButton9:
                    courseStatus("1");
                    break;
                case R.id.RadioButton10:
                    courseStatus("2");
                    break;
                case R.id.RadioButton13:
                    isExperience("1");
                    break;
                case R.id.RadioButton14:
                    isExperience("2");
                    break;
                case R.id.ivCourse:     //添加图片
                    getCourseIcon();
                    break;
                case R.id.btConservation:   //保存
                    if (TextUtils.isEmpty(mEttext1.getText().toString())) {
                        ToastUtils.showToCenters(mActivity, "请输入课程名称!", 1000);
                        return;
                    } else if (TextUtils.isEmpty(mEttext2.getText().toString())) {
                        ToastUtils.showToCenters(mActivity, "请输入课程时长!", 1000);
                        return;
                    } else {
                        showLoading();
                        if (courseId.equals("add")) {    //创建
                            addCourseData();
                        } else {     //编辑
                            editCourseInfo();
                        }
                    }
                    if (mDialog != null)
                        mDialog.cancel();
                    break;
                case R.id.icon_Delete:      //删除选中相片
                    isUpdateIcon = "2";
                    binaryList.clear();
                    Picasso.with(mActivity)
                            .load(R.drawable.publish_add)
                            .placeholder(R.drawable.publish_add)
                            .error(R.drawable.publish_add)
                            .resize(150, 150)
//                                    .transform(new CircleImageTransformation())//展示圆形图片
                            .centerCrop()
                            .into(mIvCourse);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑课程数据
     */
    private void editCourseInfo() {
        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        String courseName = mEttext1.getText().toString();
        courseName = courseName.trim();
        String remark = mEttext3.getText().toString();
        String id = SPUtils.getUserDataBean(mActivity).getId();

        int courseMeans2 = Integer.parseInt(courseMeans);
        int courseStatus2 = Integer.parseInt(courseStatus);
        int isExperience2 = Integer.parseInt(isExperience);
        OperatingFloorRequest.editCourseInfo(courseId, businessId, courseName, duration, CourseTypeId, courseMeans2, courseStatus2, isExperience2, remark, id, binaryList, isUpdateIcon, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    if (mDialog != null)
                        mDialog.cancel();
                    //Log.i(TAG, "编辑成功-->" + response.get().toString() + "");
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "编辑课程失败,请稍后重试!", 1000);
                        return;
                    }
                    ToastUtils.showToCenters(mActivity, "编辑课程成功!", 1000);
                    Intent intent = new Intent();
                    setResult(SKIP_CODE, intent);
                    finish();

                } catch (Exception e) {
                    if (mDialog != null)
                        mDialog.cancel();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (mDialog != null)
                    mDialog.cancel();
                ToastUtils.showToCenters(mActivity, "编辑课程失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 添加课程数据
     */
    private void addCourseData() {
        String businessId = SPUtils.getUserDataBean(mActivity).getBusinessId();
        String courseName = mEttext1.getText().toString();
        courseName = courseName.trim();
        int duration = Integer.parseInt(mEttext2.getText().toString());
        String remark = mEttext3.getText().toString();
        String id = SPUtils.getUserDataBean(mActivity).getId();
        ArrayList<BasicBinary> binaryList = new ArrayList<>();
        int courseMeans2 = Integer.parseInt(courseMeans);
        int courseStatus2 = Integer.parseInt(courseStatus);
        int isExperience2 = Integer.parseInt(isExperience);
        OperatingFloorRequest.addCourse(businessId, courseName, duration, CourseTypeId, courseMeans2, courseStatus2, isExperience2, remark, id, binaryList, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    if (mDialog != null)
                        mDialog.cancel();
                    StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                    ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                    if (!resultBean.getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "创建课程失败,请稍后重试!", 1000);
                        return;
                    }
                    ToastUtils.showToCenters(mActivity, "创建课程成功!", 1000);
                    Intent intent = new Intent();
                    setResult(SKIP_CODE, intent);
                    finish();

                    //Log.i(TAG, "创建成功-->" + response.get().toString() + "");
                } catch (Exception e) {
                    if (mDialog != null)
                        mDialog.cancel();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (mDialog != null)
                    mDialog.cancel();
                ToastUtils.showToCenters(mActivity, "创建课程失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取课程数据
     */
    private void getCourseData() {
        OperatingFloorRequest.getCourseInfo(courseId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                try {
                    Log.i(TAG, "获取数据成功---> " + response.get().toString() + "");

                    CourseBean courseBean = JSON.parseObject(response.get(), CourseBean.class);
                    if (!courseBean.getResult().getCode().equals("0")) {
                        ToastUtils.showToCenters(mActivity, "获取数据失败!", 1000);
                        return;
                    }

                    CourseBean.DatasBean.Course course = courseBean.getDatas().getCourse();

                    mEttext1.setText(course.getCourseName());
                    mEttext1.setSelection(mEttext1.length());
                    mEttext2.setText(course.getDuration() + "");
                    mEttext2.setSelection(mEttext2.length());
                    mEttext3.setText(course.getRemark());
                    mEttext3.setSelection(mEttext3.length());

                    //课程时长
                    duration = Integer.parseInt(course.getDuration());

                    //课程类型
                    CourseType = Integer.parseInt(course.getCourseMeans());
                    getListCourseType();
                    //授课形式
                    courseMeans(course.getCourseMeans());
                    //课程状态
                    courseStatus(course.getCourseStatus());
                    //课程是否可体验
                    isExperience(course.getIsExperience());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Log.i(TAG, "获取数据失败---> " + response.toString() + "");
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 授课形式
     */
    private void courseMeans(String i) {

        switch (i) {
            case "1":
                mRadioButton5.setChecked(true);
                mRadioButton6.setChecked(false);
                mRadioButton7.setChecked(false);
                courseMeans = i;
                break;
            case "2":
                mRadioButton5.setChecked(false);
                mRadioButton6.setChecked(true);
                mRadioButton7.setChecked(false);
                courseMeans = i;
                break;
            case "3":
                mRadioButton5.setChecked(false);
                mRadioButton6.setChecked(false);
                mRadioButton7.setChecked(true);
                courseMeans = i;
                break;

        }
    }

    /**
     * 课程是否开启
     */
    private void courseStatus(String i) {
        switch (i) {
            case "1":
                mRadioButton9.setChecked(true);
                mRadioButton10.setChecked(false);
                courseStatus = i;
                break;
            case "2":
                mRadioButton9.setChecked(false);
                mRadioButton10.setChecked(true);
                courseStatus = i;
                break;
        }
    }

    /**
     * 课程是否体验
     */
    private void isExperience(String i) {
        switch (i) {
            case "1":
                mRadioButton13.setChecked(true);
                mRadioButton14.setChecked(false);
                isExperience = i;
                break;
            case "2":
                mRadioButton13.setChecked(false);
                mRadioButton14.setChecked(true);
                isExperience = i;
                break;

        }
    }


    /**
     * 获得头像File 进行压缩并上传到服务器
     */
    private void setHeadPortrait(File file) {
        Luban.get(this)
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtils.showToCenters(mActivity, "加载图片...", 3000);
                        mBtConservation.setEnabled(false);
                    }

                    @Override
                    public void onSuccess(final File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        try {
                            isUpdateIcon = "2";
                            BasicBinary binary = new FileBinary(file);
                            binaryList.add(binary);
                            Picasso.with(mActivity)
                                    .load(file)
                                    .placeholder(R.drawable.publish_add)
                                    .error(R.drawable.publish_add)
                                    .resize(150, 150)
//                                    .transform(new CircleImageTransformation())//展示圆形图片
                                    .centerCrop()
                                    .into(mIvCourse);
                            mBtConservation.setEnabled(true);
                        } catch (Exception e) {
                            mBtConservation.setEnabled(true);
                            e.printStackTrace();
                            return;
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                        mBtConservation.setEnabled(true);
                    }
                }).launch();        //开始压缩

    }

    private String getPictureName() {
        String fileRootDir = FileUtils.DEFAULT_FILE;
        if (!FileUtils.isExist(fileRootDir)) {
            FileUtils.createDir(fileRootDir);
        }
        return fileRootDir + UUID.randomUUID().toString() + ".jpg";
    }

    /**
     * 获取课程封面图
     */
    private void getCourseIcon() {
        window = new SelectPicPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.photograph:   //拍照

                        //*获取当前系统的android版本号*//
                        currentapiVersion = Build.VERSION.SDK_INT;
                        if (currentapiVersion >= 23) {
                            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
        window.showAtLocation(mIvCourse, Gravity.BOTTOM, 0, 0);

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
                    Toast.makeText(mActivity, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();

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


    //图片截取
    private void cutPicture(final Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        List<ResolveInfo> list = this.getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", mOutputX);
        intent.putExtra("outputY", mOutputY);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_OUT_THE_PICTURE);
    }


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        try {

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == LOCAL_PHOTO_ALBUM) {
                    if (isCutPicture) {     //从相册中获取图片进来了
                        Uri originalUri = data.getData();
                        String realFilePath = ImageUtils.getRealFilePath(mActivity, originalUri);
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
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 显示加载动画
     */
    private void showLoading() {
        mDialog = new LoadingDialog(mActivity, 0.7f).builder();
        mDialog.setTitle("正在提交......");
        mDialog.show();

        //  dialog.setCancelable(false);
    }
}
