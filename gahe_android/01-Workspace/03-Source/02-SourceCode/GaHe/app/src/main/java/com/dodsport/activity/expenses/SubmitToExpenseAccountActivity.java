package com.dodsport.activity.expenses;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.askforleave.HistoryRecordActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.ImageCaptureManager;
import com.dodsport.activity.expenses.expenseaccountmanage.MyGridView;
import com.dodsport.activity.expenses.expenseaccountmanage.PhotoPickerActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.PhotoPreviewActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.SelectModel;
import com.dodsport.activity.expenses.expenseaccountmanage.intent.PhotoPickerIntent;
import com.dodsport.activity.expenses.expenseaccountmanage.intent.PhotoPreviewIntent;
import com.dodsport.model.ExpensesBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.PermissionUtil;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.utils.luban.Luban;
import com.dodsport.utils.luban.OnCompressListener;
import com.dodsport.weight.dialog.LoadingDialog;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 报销单提交
 */
public class SubmitToExpenseAccountActivity extends BaseActivity {


    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.tvSubmitName)
    TextView mTvSubmitName;
    @Bind(R.id.tvSection)
    TextView mTvSection;
    @Bind(R.id.evUse)
    EditText mEvUse;
    @Bind(R.id.evSum)
    EditText mEvSum;
    @Bind(R.id.gvExpenseAccount)
    MyGridView mGvExpenseAccount;
    @Bind(R.id.llExpense)
    LinearLayout mLlExpense;
    @Bind(R.id.llSubmitTo)
    LinearLayout mLlSubmitTo;
    @Bind(R.id.btApprove)
    Button mBtApprove;
    @Bind(R.id.btSubmit)
    Button mBtSubmit;

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> ImBase64;
    private ArrayList<String> imgBse64 = new ArrayList<>();
    private GridAdapter gridAdapter;

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE;
    private int TAKIN_PACTURES_CAMERA;
    public static final int TAKING_PICTURES = 1; // Taking pictures
    private int currentapiVersion;
    private ImageCaptureManager captureManager; // 相机拍照处理类
    private Handler mHandler;
    private String TAG = "****单据--";
    private ArrayList<BasicBinary> binaryList = new ArrayList<>();
    private LoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_to_expense_account);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("报销");
        mHeadTvOK.setText("历史记录");

        UserDataBean.DatasBean.ResponseEmployeeBean userDataBean = SPUtils.getUserDataBean(this);
        mTvSubmitName.setText("报销人\t\t\t\t\t" + userDataBean.getEmployeeName());
        mTvSection.setText("报销部门\t\t\t\t" + userDataBean.getDepName());

        GetAuthority();

    }

    @OnClick({R.id.head_ivBack, R.id.head_tvOK,R.id.btSubmit, R.id.llSubmitTo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:  //返回
                finish();
                break;
            case R.id.head_tvOK:    //历史记录
                Intent intent = new Intent(SubmitToExpenseAccountActivity.this,HistoryRecordActivity.class);
                intent.putExtra("key","Ha");
                intent.putExtra("type","2");
                startActivity(intent);
                break;
            case R.id.btSubmit:
            case R.id.llSubmitTo:   //提交
                mBtApprove.setText("店长");
                if (TextUtils.isEmpty(mEvUse.getText().toString())) {
                    ToastUtils.showToCenters(this, "请输入款项用途!", 800);
                } else if (TextUtils.isEmpty(mEvSum.getText().toString())) {
                    ToastUtils.showToCenters(this, "请输入金额!", 800);
                } else if (mBtApprove.getText().equals("+")) {
                    ToastUtils.showToCenters(this, "请选择审批人!", 800);
                } else {
                    showLoading();
                    mBtSubmit.setEnabled(false);
                    mLlSubmitTo.setEnabled(false);
                    if (imagePaths.size() >= 2){
                        onClicks();
                    }else {
                        mHandler.sendEmptyMessage(1009);
                    }
                }
                break;
        }
    }


    /**
     * 显示加载动画
     */
    private void showLoading() {
        mDialog = new LoadingDialog(SubmitToExpenseAccountActivity.this, 0.7f).builder();
        mDialog.setTitle("正在提交......");
        mDialog.show();

        //  dialog.setCancelable(false);
    }


    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1009:
                    submitTo();
                    break;

            }
        }
    }

    /**
     * 提交数据
     */
    private void submitTo() {
        UserDataBean.DatasBean.ResponseEmployeeBean userDataBean = SPUtils.getUserDataBean(SubmitToExpenseAccountActivity.this);
        String businessId = userDataBean.getBusinessId();//	商家id
        String storeId = userDataBean.getStoreId();  //门店id
        final String employeeId = userDataBean.getId();    //员工id
        final String description = "测试提交报销单";    //款项用途
        final String account = "1000.4";    //金额（保留一位小数）
        String approver = "43b117b0-7c15-4145-9ff9-e8141aea7073";    //审批人id

        OperatingFloorRequest.addExpenseAccountBill(businessId, storeId, employeeId, description, account, approver,
                binaryList, new OnResponseListener<String>() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        try {
                            Log.i(TAG, "提交File文件成功-->" + response.toString() + "");
                            if (mDialog!=null){
                                mDialog.cancel();
                            }
                            if (mBtSubmit!=null){
                                mBtSubmit.setEnabled(true);
                                if (mLlSubmitTo!=null){
                                    mLlSubmitTo.setEnabled(true);
                                }
                            }
                            ExpensesBean expensesBean = JSON.parseObject(response.get(), ExpensesBean.class);
                            if (!expensesBean.getResult().getCode().equals("0")){
                                switch (expensesBean.getResult().getCode()){
                                    case "4001":    //没有权限操作

                                        break;
                                }

                                return;
                            }
                            Intent intent = new Intent(SubmitToExpenseAccountActivity.this,ExpensesHistoryActivity.class);
                            intent.putExtra("ST","st");
                            intent.putExtra("description",description);
                            intent.putExtra("account",account);
                            intent.putExtra("ExpensesBean",expensesBean);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        Log.i(TAG, "提交File文件失败-->" + response.toString() + "");
                        if (mDialog!=null){
                            mDialog.cancel();
                        }
                        if (mBtSubmit!=null){
                            mBtSubmit.setEnabled(true);
                            if (mLlSubmitTo!=null){
                                mLlSubmitTo.setEnabled(true);
                            }
                        }
                        ToastUtils.showToCenters(SubmitToExpenseAccountActivity.this,"提交失败,请稍后重试!",1000);
                    }

                    @Override
                    public void onFinish(int what) {

                    }
                });

    }

    /**
     * Gridview管理方法
     */
    private void ManagementGridview() {
        //根据屏幕分辨率来分配 5列
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 5 ? 5 : cols;
        mGvExpenseAccount.setNumColumns(cols);

        // preview
        mGvExpenseAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);

                if ("000000".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(SubmitToExpenseAccountActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    //在主页面可以查看大图
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(SubmitToExpenseAccountActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    intent.setCharacteristic("PublishDynamicActivity");
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        mGvExpenseAccount.setAdapter(gridAdapter);

    }


    //提交单据
    private void onClicks() {
        if (binaryList.size() != 0) {
            binaryList.clear();
        }

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < imagePaths.size(); i++) {
                    if (imagePaths.get(i) != "000000") {
                        File file = new File(imagePaths.get(i));
                        Luban.get(SubmitToExpenseAccountActivity.this)
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
                                                //Log.i(TAG, "压缩图片成功--->"+binaryList.size()+"");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
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
            }
        }

//        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);

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
            mGvExpenseAccount.setAdapter(gridAdapter);

            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
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
            if (listUrls.size() == 9) {   //图片集合size
                listUrls.remove(listUrls.size() - 1);
            }
            ImBase64 = new ArrayList<>();
            ImBase64 = listUrls;

            inflater = LayoutInflater.from(SubmitToExpenseAccountActivity.this);
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

//
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

                Glide.with(SubmitToExpenseAccountActivity.this)
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

            if (ContextCompat.checkSelfPermission(SubmitToExpenseAccountActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(SubmitToExpenseAccountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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

                if (ContextCompat.checkSelfPermission(SubmitToExpenseAccountActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请照相机权限
                    ActivityCompat.requestPermissions(SubmitToExpenseAccountActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            TAKING_PICTURES);
                    TAKIN_PACTURES_CAMERA = TAKING_PICTURES;
                }

            } else {
                PermissionUtil.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);

            }

        }
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
                    Toast.makeText(SubmitToExpenseAccountActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
