package com.dodsport.fragment.operatingfloorfragment.membercardoperate;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dodsport.R;
import com.dodsport.eventBus.ObjectEvent;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.MemberMyCardListBean;
import com.dodsport.model.ResultBean;
import com.dodsport.model.StatusBean;
import com.dodsport.request.OperatingFloorRequest;
import com.dodsport.utils.SPUtils;
import com.dodsport.utils.ToastUtils;
import com.dodsport.weight.pickView.TimePickerView;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dodsport.weight.TimeUtils.parseDate;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 校准模块的Fragment
 */
public class CaliBratIonFragment extends BaseFragment {

    @Bind(R.id.RadioButton5)
    RadioButton mRadioButton5;
    @Bind(R.id.RadioButton6)
    RadioButton mRadioButton6;
    @Bind(R.id.llTermOfValidity)
    LinearLayout mLlTermOfValidity;
    @Bind(R.id.tvNumberText)
    TextView mTvNumberText;
    @Bind(R.id.tvNumber)
    TextView mTvNumber;
    @Bind(R.id.tvDateText)
    TextView mTvDateText;
    @Bind(R.id.tvDate)
    TextView mTvDate;
    @Bind(R.id.tvCaliBratIonText)
    TextView mTvCaliBratIonText;
    @Bind(R.id.tvCaliBratIon)
    TextView mTvCaliBratIon;
    @Bind(R.id.tvReviseText)
    TextView mTvReviseText;
    @Bind(R.id.etNumber)
    EditText mEtNumber;
    @Bind(R.id.llConfirm)
    LinearLayout mLlConfirm;
    @Bind(R.id.layout)
    LinearLayout mLayout;
    @Bind(R.id.llRevise)
    LinearLayout mLlRevise;
    @Bind(R.id.layoutNoActivation)
    LinearLayout mLayoutNoActivation;

    private View mView;
    private EventBus mEventBus;
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean; //选中的会员卡
    private String TAG = "****校准--";
    private Integer validityType = 1;
    private String validityTime = "";
    private FragmentCallBack mFragmentCallBack;
    private Activity mActivity;
    private TimePickerView pvTime;


    public CaliBratIonFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_cali_bration, container, false);
        }
        ButterKnife.bind(this, mView);

        Bundle data = getArguments();//获得从activity中传递过来的值
        mMemberCardBean = (MemberMyCardListBean.DatasBean.MembercardrelationListBean) data.getSerializable("MemberCardBean");
        initView();
        return mView;
    }

    private void initView() {
        if (TextUtils.isEmpty(mMemberCardBean.getActTime().toString())) {
            mLayout.setVisibility(View.GONE);
            mLayoutNoActivation.setVisibility(View.VISIBLE);
        } else {
            mLayout.setVisibility(View.VISIBLE);
            mLayoutNoActivation.setVisibility(View.GONE);
            if (mMemberCardBean.getMembcardType() == 1) {     //次卡
                mLlTermOfValidity.setVisibility(View.VISIBLE);
                mLlRevise.setVisibility(View.VISIBLE);
                if (mMemberCardBean.getValidityType() == 1) {
                    mRadioButton5.setChecked(true);
                    mRadioButton6.setChecked(false);
                    validityType = 1;
                } else {
                    mRadioButton5.setChecked(false);
                    mRadioButton6.setChecked(true);
                    validityType = 2;
                }
                mTvNumberText.setText("剩余次数");
                mTvNumber.setText(mMemberCardBean.getGiveTimes() + " 次");
            } else if (mMemberCardBean.getMembcardType() == 2) {   //期限卡
                mLlTermOfValidity.setVisibility(View.GONE);
                mLlRevise.setVisibility(View.GONE);
                mTvNumberText.setText("剩余天数");
                mTvNumber.setText(mMemberCardBean.getDays() + " 天");
            }

            mTvDate.setText(mMemberCardBean.getActTime().substring(0, 10));
            if (TextUtils.isEmpty(mMemberCardBean.getValidityTime())) {
                mTvCaliBratIon.setText("请选择");
            } else {
                mTvCaliBratIon.setText(mMemberCardBean.getValidityTime().substring(0, 10));
            }

        }

        addTime();
    }


    //主线程接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(ObjectEvent event) {
        //如果多个消息，可在实体类中添加type区分消息
        if (event == null)
            return;
        if (event.getType().equals("CaliBratIon")) {
            mMemberCardBean = event.getMemberCardBean();
            initView();
        }
        Log.i(TAG, "接收到的对象" + mMemberCardBean.toString() + "");
    }


    @Override
    public void onStart() {
        super.onStart();
        mEventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.RadioButton5, R.id.RadioButton6, R.id.llConfirm,R.id.tvCaliBratIon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RadioButton5:
                mRadioButton5.setChecked(true);
                mRadioButton6.setChecked(false);
                validityType = 1;
                break;
            case R.id.RadioButton6:
                mRadioButton5.setChecked(false);
                mRadioButton6.setChecked(true);
                validityType = 2;
                break;
            case R.id.tvCaliBratIon:
                pvTime.show();
                break;
            case R.id.llConfirm:
                get();
                break;
        }
    }

    /**
     * 会员卡 校准
     * <p>
     * Token 用户通行口令
     * relationId	String		是	会员卡关系表id
     * validityType	Integer			是否开启有效期:1是;2否
     * times	Integer			剩余次数
     * validityTime	String			有效期时间
     * creator	String		是
     */
    private void get() {
        Integer times = 0;
        String relationId = mMemberCardBean.getId();
        if (!TextUtils.isEmpty(mEtNumber.getText().toString())){
            times = Integer.parseInt(mEtNumber.getText().toString());
        }
        String creator = SPUtils.getUserDataBean(mActivity).getId();
        validityTime = mTvCaliBratIon.getText().toString();
        //Log.i(TAG, "传参--->"+relationId+"\t"+validityType+"\t"+times+"\t"+validityTime+"\t");
        OperatingFloorRequest.calibrationMembercardrelation(relationId, validityType + "", times, validityTime, creator, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                //Log.i(TAG, "校准成功--->" + response.get().toString() + "");
                StatusBean statusBean = JSON.parseObject(response.get(), StatusBean.class);
                ResultBean resultBean = JSON.parseObject(statusBean.getResult(), ResultBean.class);
                if (resultBean.getCode().equals("0")){
                    Bundle bundle = new Bundle();
                    bundle.putString("Operating","CaliBratIon");
                    mFragmentCallBack.CallBackFun(bundle);
                    ToastUtils.showToCenters(mActivity,"校准成功!",1000);
                }else {
                    ToastUtils.showToCenters(mActivity,"校准失败,请稍后重试!",1000);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtils.showToCenters(mActivity,"校准失败,请稍后重试!",1000);
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    /**
     * 时间选择器
     */
    private void addTime() {
        // 时间选择器
        pvTime = new TimePickerView(mActivity, TimePickerView.Type.YEAR_MONTH_DAY);
        // 控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
                calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                try {
                    String datetime = parseDate(date);
                    mTvCaliBratIon.setText(datetime);
//                    long currentTimeMillis = System.currentTimeMillis();
//
//                    long time = date.getTime();
//
//                    if(time>currentTimeMillis)
//                    {
//                        ToastUtils.showToCenter(mActivity,"不能大于当前时间",800);
//                        return;
//                    }



                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mFragmentCallBack = (FragmentCallBack) activity;
    }

    @Override
    protected void lazyLoad() {

    }


}
