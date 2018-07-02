package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.dodsport.R;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.BaseMemberListBean;
import com.dodsport.model.ClientUserBean;
import com.dodsport.model.MemberInfoBean;
import com.dodsport.model.ResultBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.request.UrlInterfaceManager;
import com.dodsport.utils.KeyBoardUtils;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.TimeUtils;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.dodsport.utils.ScreenUtils.setBackgroundAlpha;


/**
 * 会员编辑的popupWindow
 * @author Administrator
 */
public class EditArchivesPopupWindow extends PopupWindow {

    private final TextView tvBirthday;
    private Activity mContext;
    private View parentView;
    private View mViews;
    private BaseMemberListBean.DatasBean.ResponseMemberList baseMember;
    private int sex = 2;
    private List<String> data = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;
    private String mPosition = "0";
    private TimePickerView pvCustomLunar;
    private EditText evName,evPhoneName,evRemarks;
    private int memberTage = 1;
    private LinearLayout llTypeManager,llEditText;

    public EditArchivesPopupWindow() {
        throw new AssertionError("不可实例化");
    }


    /**
     * 分享的popupWindow
     * @param context    当前Context
     * @param parentView 基于哪个布局的位置
     */
    public EditArchivesPopupWindow(Activity context,final View parentView,boolean isAlpha,final BaseMemberListBean.DatasBean.ResponseMemberList baseMember) {

        mContext  = context;
        this.parentView = parentView;
        this.mViews = mViews;
        this.baseMember = baseMember;
        mViews = View.inflate(context, R.layout.popupwindow_archives_edit, null);
        setParams();

        evName = mViews.findViewById(R.id.evName);
        tvBirthday = mViews.findViewById(R.id.tvBirthday);
        evPhoneName = mViews.findViewById(R.id.evPhoneName);
        evRemarks = mViews.findViewById(R.id.evRemarks);
        final RadioButton RadioButton1 = mViews.findViewById(R.id.RadioButton1);
        final RadioButton RadioButton2 = mViews.findViewById(R.id.RadioButton2);
        Button btConservation = mViews.findViewById(R.id.btConservation);   //保存
        Button btCancel = mViews.findViewById(R.id.btCancel);   //取消
        llTypeManager = mViews.findViewById(R.id.llTypeManager);
        llEditText = mViews.findViewById(R.id.llEditText);
        RecyclerView rvTypeManager = mViews.findViewById(R.id.rvTypeManager);
        TextView tvTitle = mViews.findViewById(R.id.tvTitle);
        final LinearLayout llText = mViews.findViewById(R.id.llText);
        LinearLayout llBirthday = mViews.findViewById(R.id.llBirthday);
        rvTypeManager.setLayoutManager(new GridLayoutManager(mContext,3));
        //客户生日
        llBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickDlg(tvBirthday);
                setBackgroundAlpha(mContext,1.0f);
            }
        });
        if (baseMember!=null){
            tvTitle.setText("编辑客户资料");
            evName.setText(baseMember.getNickName());
            tvBirthday.setText(baseMember.getBirthday());
            evPhoneName.setText(baseMember.getPhoneNum());
            evRemarks.setText(baseMember.getRemark());
            if (baseMember.getSex().equals("1")){
                sex = 1;
                RadioButton1.setChecked(true);
                RadioButton2.setChecked(false);
            }else if (baseMember.getSex().equals("2")){
                sex = 2;
                RadioButton1.setChecked(false);
                RadioButton2.setChecked(true);
            }
           mPosition = baseMember.getMemberTags();
        }else {
            tvTitle.setText("添加新客户");
        }

        RadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 1;
                RadioButton1.setChecked(true);
                RadioButton2.setChecked(false);
            }
        });

        RadioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 2;
                RadioButton1.setChecked(false);
                RadioButton2.setChecked(true);
            }
        });

        //继续
        btConservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(evName.getText().toString().trim())){
                    ToastUtils.showToCenters(mContext,"昵称不能为空!",1000);
                    return;
                }else if (TextUtils.isEmpty(tvBirthday.getText().toString().trim()) || tvBirthday.getText().equals("请选择")){
                    ToastUtils.showToCenters(mContext,"生日不能为空!",1000);
                    return;
                } else if (TextUtils.isEmpty(evPhoneName.getText().toString().trim())){
                    ToastUtils.showToCenters(mContext,"手机号不能为空!",1000);
                    return;
                }
                //添加会员
                if (baseMember == null){
                    //添加会员
                    if (llTypeManager.getVisibility() == View.VISIBLE){
                        addMemberInfo();
                    }else {
                        //获取该用户信息
                        getMemberInfo();
                        //获取该用户是否是 平台用户
                        getInfo();
                    }
                    //编辑会员
                    }else {
                    if (llTypeManager.getVisibility() == View.GONE) {
                        showView();
                        //获取该用户是否是 平台用户
                        getInfo();
                    } else {
                        setMemberInfo();
//                    llEditText.setVisibility(View.VISIBLE);
//                    llTypeManager.setVisibility(View.GONE);
//                    // 向右边移出
//                    llTypeManager.setAnimation(AnimationUtils.makeOutAnimation(mContext, true));
//                    // 向右边移入
//                    llEditText.setAnimation(AnimationUtils.makeInAnimation(mContext, true));
                    }
                }

            }
        });
        //手机号

        //取消
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llTypeManager.getVisibility() == View.VISIBLE){
                    llEditText.setVisibility(View.VISIBLE);
                    llTypeManager.setVisibility(View.GONE);
                    // 向右边移出
                    llTypeManager.setAnimation(AnimationUtils.makeOutAnimation(mContext, true));
                    // 向右边移入
                    llEditText.setAnimation(AnimationUtils.makeInAnimation(mContext, true));
                }else {
                    setBackgroundAlpha(mContext,1.0f);
                    dismiss();
                }

            }
        });
        for (int i = 1; i < 6; i++) {
            //添加
            data.add(UrlInterfaceManager.memberTags[i]);
        }

        mCommonAdapter = new CommonAdapter<String>(mContext,R.layout.edit_archives_item,data) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                LinearLayout llType = holder.getView(R.id.llType);
                TextView tvType = holder.getView(R.id.tvType);

                if (baseMember == null){
                    if (memberTage != position + 1){
                        llType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_cancel_style2));
                        tvType.setText(UrlInterfaceManager.memberTags[position+1]);
                        tvType.setTextColor(mContext.getResources().getColor(R.color.eeeeee));
                    }else {
                        llType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_login_style2));
                        tvType.setText(UrlInterfaceManager.memberTags[position+1]);
                        tvType.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                }else {
                    if (!mPosition.equals(position+1+"")){
                        llType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_cancel_style2));
                        tvType.setText(UrlInterfaceManager.memberTags[position+1]);
                        tvType.setTextColor(mContext.getResources().getColor(R.color.eeeeee));
                    }else {
                        llType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_login_style2));
                        tvType.setText(UrlInterfaceManager.memberTags[position+1]);
                        tvType.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                }


                if (baseMember == null && memberTage == 5){
                    llText.setVisibility(View.VISIBLE);
                }else {
                    llText.setVisibility(View.GONE);
                }

                if (baseMember != null && mPosition.equals("5") && baseMember.getStatus().equals("1")){
                    llText.setVisibility(View.VISIBLE);
                }else {
                    llText.setVisibility(View.GONE);
                }

                llType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPosition = position+1+"";
                        memberTage = position+1;
                        mCommonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        rvTypeManager.setAdapter(mCommonAdapter);
    }


    /**
     * 获取会员信息
     */
    public void getMemberInfo() {
        String businessId = SPUtils.getUserDataBean(mContext).getBusinessId();
        String memberPone = evPhoneName.getText().toString().trim();
        OperatingFloorRequest.queryMemberinfoByPhoneNum(memberPone, businessId, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                try {

                    MemberInfoBean memberInfoBean = JSON.parseObject(response.get(), MemberInfoBean.class);
                    Log.i("****", "获取用户信息成功--->" + memberInfoBean.toString() + "");
                    if (!memberInfoBean.getResult().getCode().equals("0")) {
                        switch (memberInfoBean.getResult().getCode()) {
                            case "5002":
                                showView();
                                break;
                            default:
                                ToastUtils.showToCenters(mContext, "已有该会员，不能重复添加!", 1000);
                                break;
                        }
                        return;
                    }

                    if (memberInfoBean.getDatas().getBaseMember() == null){
                        showView();
                    }else {
                        ToastUtils.showToCenters(mContext, "已有该会员，不能重复添加!", 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mContext, "查找失败,请稍后重试!", 1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 编辑会员信息
     * Token 用户通行口令
     * nickName	String			用户昵称(userName 改成nickName)
     * sex	String			性别:1:男;2女;3:未知
     * birthday	string			生日(yyyy-MM-dd)
     * memberTags		String				会员标签1潜在客户;2:意向客户;3:目标客户;4:待签约客户;5:签约客户
     * phoneNum	String			会员电话
     * remark	String			备注
     * memberId	String		是	客户id
     * userId	String		是	客户id(新增)
     * */
    private void setMemberInfo(){
        String nickName = evName.getText().toString().trim();
        String birthday = tvBirthday.getText().toString().trim();
        String memberTags = mPosition;
        String phoneNum = evPhoneName.getText().toString().trim();
        String remark = evRemarks.getText().toString().trim();
        String memberId = baseMember.getId();
        String userId = baseMember.getUserId();
        OperatingFloorRequest.updateMmberInfo(nickName, sex + "", birthday, memberTags, phoneNum, remark, memberId, userId, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                Log.i("****", "编辑成功--->"+response.get().toString()+"");
                dismiss();
                ToastUtils.showToCenters(mContext,"用户信息编辑成功!",1000);
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                ToastUtils.showToCenters(mContext,"用户信息编辑失败!",1000);
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }


    /**
     * 获取平台用户信息
     * */
    private void getInfo(){
        String  phoneNum = evPhoneName.getText().toString().trim();
        OperatingFloorRequest.getInfo(phoneNum, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                Log.i("*****", "获取平台用户信息--->"+response.get().toString()+"");
                ClientUserBean clientUser = JSON.parseObject(response.get(), ClientUserBean.class);
                if (!clientUser.getResult().getCode().equals("0")){
                    return;
                }
                if (clientUser.getDatas() == null){
                    memberTage = 5;
                    mPosition = "1";
                }else {
                    memberTage = 1;
                }
                mCommonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                Log.i("*****", "获取平台用户信息失败--->"+response.toString()+"");
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }

    /**
     * 添加会员信息
     * Token 用户通行口令
     * BusinessId	String		是	商家id
     * storeId	String		是	门店id
     * nickName	string		是	用户昵称(改成昵称)
     * Sex	String		是	性别:1男;2女3未知
     * birthday	String		是	生日(yyyy-MM-dd)
     * phoneNum	string		是	会员电话
     * memberTags	String		是	会员标签1潜在客户;2:意向客户;3:目标客户;4:待签约客户;5:签约客户
     * creator	String		是
     * remark	String		是	备注
     * */
    private void addMemberInfo(){
        String nickName = evName.getText().toString().trim();
        String birthday = tvBirthday.getText().toString().trim();
        String memberTags = mPosition;
        String phoneNum = evPhoneName.getText().toString().trim();
        final String remark = evRemarks.getText().toString().trim();
        String businessId = SPUtils.getUserDataBean(mContext).getBusinessId();
        String storeId = SPUtils.getUserDataBean(mContext).getStoreId();
        String id = SPUtils.getUserDataBean(mContext).getId();
        OperatingFloorRequest.addMemberInfo(businessId, storeId, nickName, sex + "", birthday, phoneNum, memberTags, remark, id, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                ResultBean resultBean = JSON.parseObject(response.get(), ResultBean.class);
                if (resultBean.getCode().equals("0")){
                    ToastUtils.showToCenters(mContext,"添加会员成功!",1000);
                    dismiss();
                }else {
                    ToastUtils.showToCenters(mContext,"添加会员失败,请稍后重试!",1000);
                }
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                ToastUtils.showToCenters(mContext,"添加会员失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int i) {

            }
        });

    }


    /**
     * 显示标签模块
     * */
    private void showView(){
        //关闭键盘
        KeyBoardUtils.closeKeybord(evName,mContext);
        if (llTypeManager.getVisibility()== View.GONE) {
            llEditText.setVisibility(View.GONE);
            llTypeManager.setVisibility(View.VISIBLE);
            // 向左边移入
            llTypeManager.setAnimation(AnimationUtils.makeInAnimation(mContext, false));
            // 向左边移出
            llEditText.setAnimation(AnimationUtils.makeOutAnimation(mContext, false));
        }
    }

    private void setParams() {
        this.setContentView(mViews);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.take_photo_anim);
//        this.setFocusable(false);
//        this.setOutsideTouchable(false);
//        this.setTouchable(false);

        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setTouchable(true);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        this.setBackgroundDrawable(dw);
        if(true)
        {
            setBackgroundAlpha(mContext,1.0f);
        }


        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mViews.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mViews.findViewById(R.id.rl_height).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }

                return false;
            }
        });
        //添加pop窗口关闭事件
        this.setOnDismissListener(new poponDismissListener());
    }


    private class poponDismissListener implements OnDismissListener {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(mContext,1f);
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 选择生日
     * */
    protected void showDatePickDlg(final TextView mEditText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,R.style.ThemeDialog ,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                long currentTimeMillis = System.currentTimeMillis();
                    monthOfYear = monthOfYear+1;
                    if (monthOfYear == 12){
                        year = year+1;
                    }
                    String ofMonth = "";
                    if (dayOfMonth < 10){
                        ofMonth = "0"+dayOfMonth;
                    }else {
                        ofMonth = ""+dayOfMonth;
                    }

                    String monthOf = "";
                    if (monthOfYear < 10){

                        monthOf = "0"+monthOfYear;
                    }else {
                        monthOf = ""+monthOfYear;
                    }
                    Date date = TimeUtils.parseDatetime(year + "-" + monthOfYear + "-" + dayOfMonth+" 23:59:59");
                    long time = date.getTime();
                if (time > currentTimeMillis) {
                    ToastUtils.showToCenter(mContext, "不能大于当前时间", 0);
                } else {
                    mEditText.setText(year + "-" + monthOf + "-" + ofMonth);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                try {
                    String parseDate = TimeUtils.parseDate(date);
                    tvBirthday.setText(parseDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, 0.8f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timepicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timepicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timepicker.getChildCount(); i++) {
                            View childAt = timepicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }


}