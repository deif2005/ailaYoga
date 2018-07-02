package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dodsport.GaHeApplication;
import com.dodsport.R;
import com.dodsport.activity.askforleave.PickerView;
import com.dodsport.utils.ScreenUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.silkcal.DatePickerController;
import com.dodsport.weight.silkcal.DayPickerView;
import com.dodsport.weight.silkcal.SimpleMonthAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dodsport.weight.TimeUtils.differentDays;
import static com.dodsport.weight.TimeUtils.parseDate;


/**
 * 请假休假单 时间选择器
 */

public class TimeSelectPopupWindow extends PopupWindow implements DatePickerController {

    private TextView mTvDate,mTimeInterval,tvEndTime,loadOnTime,StartTimeView;
    private Activity mActivity;
    private View mMenuView;
    private DayPickerView mDayPickerView;
    private Button mBtpopOK;
    private LinearLayout mlyout;
    private View mViewDate,mViewInterval;
    public String Time,TimeHH;
    private String label;
    private String indexOf;
    private String StartTime;
    private boolean start = false;
    private String  status = "请选择(必填)";


    public TimeSelectPopupWindow(Activity activity, final TextView tvEndTime, final String label, final TextView StartTimeView, final TextView loadOnTime) {
        super(activity);
        this.mActivity = activity;
        this.tvEndTime = tvEndTime;
        this.label = label;
        this.StartTime = StartTimeView.getText().toString();
        this.loadOnTime = loadOnTime;
        this.StartTimeView = StartTimeView;


        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_timeselect, null);

        mTvDate = mMenuView.findViewById(R.id.tvDate);
        mBtpopOK = mMenuView.findViewById(R.id.btpopOK);
        mlyout = mMenuView.findViewById(R.id.llpopuplayout);
        PickerView mPickerView = mMenuView.findViewById(R.id.wheelView);
        mViewInterval = mMenuView.findViewById(R.id.viewInterval);
        mViewDate = mMenuView.findViewById(R.id.viewDate);
        mTimeInterval = mMenuView.findViewById(R.id.tvTimeInterval);
        mDayPickerView = mMenuView.findViewById(R.id.calendar_view);
        mDayPickerView.setController(this);

        setParams();

        SimpleDateFormat formatters = new SimpleDateFormat("yyyy年MM月dd日-HH");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        final String mTime = formatters.format(curDate);
        //获取指定字符串前面的数据
        String sysTime = mTime.substring(0, mTime.indexOf("-"));
        String s = sysTime.replace("年","-");
        s = s.replace("月","-");
        s = s.replace("日","");
        this.Time = s;
        indexOf = s;
        //获取指定字符串后面的数据
        TimeHH = mTime.substring(mTime.indexOf("-")+1);
        mTvDate.setText(sysTime+ "");
        final List<Integer> weekList = GaHeApplication.weekList;

        for (int i = 0; i < weekList.size(); i++) {

        }

        /**时间段*/
        mTimeInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlyout.setVisibility(View.VISIBLE);
                mDayPickerView.setVisibility(View.GONE);
                mViewInterval.setVisibility(View.VISIBLE);
                mViewDate.setVisibility(View.GONE);

                mViewInterval.setAnimation(AnimationUtils.makeInAnimation(mActivity, false));

                mViewDate.setAnimation(AnimationUtils.makeOutAnimation(mActivity, false));
                // 向左边移入
                mlyout.setAnimation(AnimationUtils.makeInAnimation(mActivity, false));

                // 向左边移出
                mDayPickerView.setAnimation(AnimationUtils.makeOutAnimation(mActivity, false));

            }
        });

        /**日期*/
        mTvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlyout.setVisibility(View.GONE);
                mDayPickerView.setVisibility(View.VISIBLE);
                mViewInterval.setVisibility(View.GONE);
                mViewDate.setVisibility(View.VISIBLE);

                mViewInterval.setAnimation(AnimationUtils.makeOutAnimation(mActivity, true));

                mViewDate.setAnimation(AnimationUtils.makeInAnimation(mActivity, true));
                // 向右边移出
                mlyout.setAnimation(AnimationUtils.makeOutAnimation(mActivity, true));

                // 向右边移入
                mDayPickerView.setAnimation(AnimationUtils.makeInAnimation(mActivity, true));

            }
        });

        /**确定*/
        mBtpopOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String time = mTimeInterval.getText().toString();
                    String substring = time.substring(2, 4);
                    if (label.equals("start")){

                        Date date = parseDate(Time);
                        Date date1 = parseDate(indexOf);
                        int ti = differentDays(date, date1);
                        int i = Integer.parseInt(TimeHH);
                        if (ti > 0) {
                            ToastUtils.showToCenters(mActivity, "开始时间不能大于当前时间!", 1000);
                            Time = status;
                            loadOnTime.setHint("自动加载时长");
                            StartTimeView.setText(status);
                        }else if (ti ==0){
                            if ((i >= 12 && substring.equals("上午")) || (i < 12 && substring.equals("下午")) || i > 23){
                                ToastUtils.showToCenters(mActivity, "开始时间不能大于当前时间!", 1000);
                                Time = status;
                                loadOnTime.setHint("自动加载时长");
                                StartTimeView.setText(status);
                            }
                        }

                        if (!StartTime.equals(status)){
                            countTime(substring);
                        }

                    }else if (label.equals("end")) {
                        countTime(substring);
                    }

                    start = true;
                    if (Time.equals(status)){
                        tvEndTime.setText(Time);
                        loadOnTime.setText("自动加载时长");
                    }else {
                        tvEndTime.setText(Time+"."+substring+"");
                    }


                    ScreenUtils.setBackgroundAlpha(mActivity, 1.0f);
                    dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        List<String> seleck = new ArrayList<>();
        if (!TextUtils.isEmpty(TimeHH)){
            int i = Integer.parseInt(TimeHH);
            if (i <= 12){
                mTimeInterval.setText("  上午  ");
                seleck.add("下午");
                seleck.add("上午");

            }else {
                mTimeInterval.setText("  下午  ");
                seleck.add("上午");
                seleck.add("下午");
            }
        }else {
            seleck.add("下午");
            seleck.add("上午");
        }



       mPickerView.setData(seleck);
        mPickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                mTimeInterval.setText("  "+text+"  ");
            }
        });


    }

    /**
     * 计算请假时长
     *
     * @param substring*/
    private void countTime(String substring){
        if (!TextUtils.isEmpty(StartTime)){
            String s = StartTime.substring(0, 10);
            try {
                int i = 10000;
                Date date = parseDate(s);
                Date date1 = parseDate(Time);
                i = differentDays(date, date1);
                //开始时间
                String mtime = StartTime.substring(11, 13);
                if (i==0 || i<0){
                    if (mtime.equals(substring)){
                        ToastUtils.showToCenters(mActivity, "开始时间不能大于当前时间!", 1000);
                        Time = status;
                    }
                }

                if (mtime.equals("上午") && substring.equals("下午")){
                    if(i == 0){
                        loadOnTime.setText("0.5");
                    }else if (i ==2){
                        i =i-1;
                        loadOnTime.setText(i+".5");
                    }else {
                        loadOnTime.setText(i+".5");
                    }
                }else if (mtime.equals("下午") && substring.equals("上午")){
                    if(i == 0 ||i == 1){
                        loadOnTime.setText("0.5");
                    } else if (i==2) {
                        i = i-1;
                        loadOnTime.setText(i + ".5");
                    } else {
                        loadOnTime.setText(i + ".5");
                    }
                }else if (mtime.equals("上午") && substring.equals("上午")){
                    if (i==0){
                        i=1;
                    }
                    loadOnTime.setText(i+"");
                }else if (mtime.equals("下午") && substring.equals("下午")){
                    if (i==0){
                        i=1;
                    }
                    loadOnTime.setText(i+"");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 设置基本参数
     */
    private void setParams() {

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.take_photo_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        this.setBackgroundDrawable(dw);
        ScreenUtils.setBackgroundAlpha(mActivity, 0.7f);
//        setBackgroundAlpha(0.7f);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.lyout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        if (start){
                            String time = mTimeInterval.getText().toString();
                            String substring = time.substring(2, 4);
                            if (Time.equals(status)){
                                tvEndTime.setText(Time);
                                loadOnTime.setText("自动加载时长");
                            }else {
                                tvEndTime.setText(Time+"."+substring+"");
                            }

                        }

                        ScreenUtils.setBackgroundAlpha(mActivity, 1.0f);
                        dismiss();
                    }
                }
                return true;

            }
        });
        //添加pop窗口关闭事件
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (start){
                    String time = mTimeInterval.getText().toString();
                    String substring = time.substring(2, 4);
                    if (Time.equals(status)){
                        tvEndTime.setText(Time);
                        loadOnTime.setText("自动加载时长");
                    }else {
                        tvEndTime.setText(Time+"."+substring+"");
                    }

                }
                ScreenUtils.setBackgroundAlpha(mActivity, 1.0f);
            }
        });
    }


    @Override
    public void onDraw(Canvas canvas) {

    }

    @Override
    public int getMaxYear() {
        return 0;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        String mMonth = "";
        String mDay = "";

        month = month + 1;
        if (month<=9){
            mMonth = "0"+month+"";
        }else {
            mMonth = month+"";
        }
        if (day <= 9){
            mDay = "0"+day+"";
        }else {
            mDay = day+"";
        }
        String Time = year + "-" + mMonth + "-" + mDay + "";
        mTvDate.setText(Time + "");
        this.Time = Time;
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }
}
