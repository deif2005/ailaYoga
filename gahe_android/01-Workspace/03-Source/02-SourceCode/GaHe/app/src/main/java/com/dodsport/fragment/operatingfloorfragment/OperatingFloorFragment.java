package com.dodsport.fragment.operatingfloorfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.CompanyListActivity;
import com.dodsport.activity.HaveAHolidayActivity;
import com.dodsport.activity.PunchTheClockActivity;
import com.dodsport.activity.approve.ApproveActivity;
import com.dodsport.activity.business.AlbumActivity;
import com.dodsport.activity.cardcoupons.CardCouponsIntercalateActivity;
import com.dodsport.activity.cardcoupons.MembershipCardActivity;
import com.dodsport.activity.expenses.ExpensesActivity;
import com.dodsport.activity.expenses.SubmitToExpenseAccountActivity;
import com.dodsport.activity.financial.IncomeDetailActivity;
import com.dodsport.activity.personnel.BecomeARegularWorkerBillsActivity;
import com.dodsport.activity.personnel.DimissionBillsActivity;
import com.dodsport.activity.personnel.EntryBillsActivity;
import com.dodsport.activity.personnel.TransferPositionActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.fragment.BaseFragment;
import com.dodsport.model.OperatingFloorBean;
import com.dodsport.model.UserDataBean;
import com.dodsport.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工作台模块
 */
public class OperatingFloorFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "******";

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.head_ivOK)
    ImageView mHeadIvOK;
    @Bind(R.id.head_tvOK)
    TextView mHeadTvOK;
    @Bind(R.id.rlopRecyclerView)
    RecyclerView mRlopRecyclerView;
    private View mView;
    private String token = "";
    private UserDataBean.DatasBean.BusiEmployeeBean mUserDataBean;

    //适配器
    private CommonAdapter<Object> mCommonAdapter;
    private  OperatingFloorBean mOperatingFloorBean;
    private  final List<OperatingFloorBean> mData = new ArrayList<>();
    private  List<Integer> mDrawable = new ArrayList<>();
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_operatingfloor, container, false);
        }

        ButterKnife.bind(this, mView);
        mUserDataBean = SPUtils.getUserDataBean(getActivity().getApplicationContext());

        isPrepared = true;
        initView();
        lazyLoad();

        return mView;
    }

    //初始化
    private void initView(){
        mRlopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mHeadTvOK.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        if (mUserDataBean!=null){
            mHeadTvOK.setText(mUserDataBean.getBusinessName()+"");
        }
        mHeadTvOK.setOnClickListener(this);
        mHeadTvTitle.setText("工作台");
        new Thread(){
            @Override
            public void run() {
                super.run();
                inITiaLise();

            }
        }.start();
    }

    @Override
    protected void lazyLoad() {
    }

    private void inITiaLise() {
      final List<Object> data = new ArrayList<>();

        String[] g = {"","行政管理","人事管理","财务管理","卡券管理","会员管理","教学管理","店铺管理"};
        String[] vicon1 ={"出勤打卡","请假","审批"};
        String[] vicon2 ={"入职","转正","离职","调岗"};
        String[] vicon3 ={"收入","支出","报销"};
        String[] vicon4 ={"卡券操作","卡券设置","卡券记录"};
        String[] vicon5 ={"信息管理","会员评价","推送消息"};
        String[] vicon6 ={"排课","课程设置"};
        String[] vicon7 ={"店铺信息"};

        List<String[]> mVicon = new ArrayList<>();
        mVicon.add(vicon1);
        mVicon.add(vicon2);
        mVicon.add(vicon3);
        mVicon.add(vicon4);
        mVicon.add(vicon5);
        mVicon.add(vicon6);
        mVicon.add(vicon7);

        for (int i = 0; i != 8;i++){
            if (i==1)
            {
                mOperatingFloorBean = new OperatingFloorBean();
                List<String> a = new ArrayList<>();
                a.add("出勤打卡");
                mOperatingFloorBean.setData(a);
                a.add("请假");
                mOperatingFloorBean.setData(a);
                a.add("审批");
                mOperatingFloorBean.setData(a);

                mData.add(mOperatingFloorBean);
            }else if (i == 2 ){
                mOperatingFloorBean = new OperatingFloorBean();
                List<String> a = new ArrayList<>();
                a.add("入职");
                mOperatingFloorBean.setData(a);
                a.add("转正");
                mOperatingFloorBean.setData(a);
                a.add("离职");
                mOperatingFloorBean.setData(a);
                a.add("调岗");
                mOperatingFloorBean.setData(a);

                mData.add(mOperatingFloorBean);
            }else if (i ==3){

                mOperatingFloorBean = new OperatingFloorBean();
                List<String> a = new ArrayList<>();
                a.add("收入");
                mOperatingFloorBean.setData(a);
                a.add("支出");
                mOperatingFloorBean.setData(a);
                a.add("报销");
                mOperatingFloorBean.setData(a);

                mData.add(mOperatingFloorBean);
            } else if (i == 4) {
                mOperatingFloorBean = new OperatingFloorBean();
                List<String> a = new ArrayList<>();
                a.add("会员卡");
                mOperatingFloorBean.setData(a);
                a.add("卡券设置");
                mOperatingFloorBean.setData(a);

                mData.add(mOperatingFloorBean);
            } else if (i ==5) {
                mOperatingFloorBean = new OperatingFloorBean();
                List<String> a = new ArrayList<>();
                a.add("信息管理");
                mOperatingFloorBean.setData(a);
                a.add("会员评价");
                mOperatingFloorBean.setData(a);

                mData.add(mOperatingFloorBean);
            } else if (i ==6) {     //教学管理
                mOperatingFloorBean = new OperatingFloorBean();
                List<String> a = new ArrayList<>();
                a.add("排课");
                mOperatingFloorBean.setData(a);
                a.add("课程设置");
                mOperatingFloorBean.setData(a);
                a.add("课程签到");
                mOperatingFloorBean.setData(a);
                a.add("教练");
                mOperatingFloorBean.setData(a);

                mData.add(mOperatingFloorBean);
            } else {
                mOperatingFloorBean = new OperatingFloorBean();
                List<String> a = new ArrayList<>();
                a.add("商家信息");
                mOperatingFloorBean.setData(a);
                a.add("相册");
                mOperatingFloorBean.setData(a);
                mData.add(mOperatingFloorBean);
            }
            mData.get(i).setGrouping(g[i]);
            data.add(mData);
        }


        mCommonAdapter = new CommonAdapter<Object>(getActivity().getApplicationContext(),R.layout.operatingfloor_itme,data) {
            @Override
            protected void convert(ViewHolder holder, Object s, final int position) {
                View view = holder.getView(R.id.inmydataview);
                View mView = holder.getView(R.id.view);
                TextView mGrouping = holder.getView(R.id.tvgrouping);
                //用户信息
                Button btuserhaed = holder.getView(R.id.btuserhaed);    //用户头像
                TextView tvposition = holder.getView(R.id.tvposition);  //用户职位
                TextView tvdepartment = holder.getView(R.id.tvdepartment);//用户部门
                TextView tvcompany = holder.getView(R.id.tvcompany);      //用户公司

                btuserhaed.setText(mUserDataBean.getEmployeeName());
                tvposition.setText("职位： "+mUserDataBean.getPositionName());
                tvdepartment.setText("部门： "+mUserDataBean.getDepName());
                tvcompany.setText("  "+mUserDataBean.getBusinessName());

                //分类模块
                LinearLayout mLlgrouping1 = holder.getView(R.id.llgrouping1);
                LinearLayout mLlgrouping2 = holder.getView(R.id.llgrouping2);
                LinearLayout mLlgrouping3 = holder.getView(R.id.llgrouping3);
                LinearLayout mLlgrouping4 = holder.getView(R.id.llgrouping4);

                mLlgrouping1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Click(position,ONE);
                        Log.i(TAG, "onClick模块" +position+"\t"+"第一个小模块");
                    }
                });

                mLlgrouping2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Click(position,TWO);
                        Log.i(TAG, "onClick模块" +position+"\t"+"第二个小模块");
                    }
                });
                mLlgrouping3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Click(position,THREE);
                        Log.i(TAG, "onClick模块" +position+"\t"+"第三个小模块");
                    }
                });
                mLlgrouping4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Click(position,FOUR);
                        Log.i(TAG, "onClick模块" +position+"\t"+"第四个小模块");
                    }
                });
                mLlgrouping1.setVisibility(View.GONE);
                mLlgrouping2.setVisibility(View.GONE);
                mLlgrouping3.setVisibility(View.GONE);
                mLlgrouping4.setVisibility(View.GONE);
                mGrouping.setVisibility(View.GONE);
                mGrouping.setText(mData.get(position).getGrouping()+"");

                //图标
                ImageView mIvicon1 = holder.getView(R.id.ivicon1);
                ImageView mIvicon2 = holder.getView(R.id.ivicon2);
                ImageView mIvicon3 = holder.getView(R.id.ivicon3);
                ImageView mIvicon4 = holder.getView(R.id.ivicon4);

                //描述文字
                TextView mTvcue1 = holder.getView(R.id.tvcue1);
                TextView mTvcue2 = holder.getView(R.id.tvcue2);
                TextView mTvcue3 = holder.getView(R.id.tvcue3);
                TextView mTvcue4 = holder.getView(R.id.tvcue4);

                //显示个人信息
                if (position == 0){
                    view.setVisibility(View.VISIBLE);
                }else {
                    if (data.size() == (position+1)){
                        mView.setVisibility(View.GONE);
                    }
                    //显示个人信息
                    view.setVisibility(View.GONE);
                    List<String> list = mData.get(position).getData();
                    //分类模块管理
                    switch (mData.get(position).getData().size()){
                        case 1:
                            mLlgrouping1.setVisibility(View.VISIBLE);
                            mLlgrouping2.setVisibility(View.INVISIBLE);
                            mLlgrouping3.setVisibility(View.INVISIBLE);
                            mLlgrouping4.setVisibility(View.INVISIBLE);
                            mGrouping.setVisibility(View.VISIBLE);
                            mTvcue1.setText(list.get(0));
                            List<Integer> integers1 = showGroupingView(position);
                            mIvicon1.setImageResource(integers1.get(0));
                            break;
                        case 2:
                            mLlgrouping1.setVisibility(View.VISIBLE);
                            mLlgrouping2.setVisibility(View.VISIBLE);
                            mLlgrouping3.setVisibility(View.INVISIBLE);
                            mLlgrouping4.setVisibility(View.INVISIBLE);
                            mGrouping.setVisibility(View.VISIBLE);

                            //第一个模块
                            mTvcue1.setText(list.get(0));
                            List<Integer> integers2 = showGroupingView(position);
                            mIvicon1.setImageResource(integers2.get(0));

                            //第二个模块
                            mTvcue2.setText(list.get(1));
                            mIvicon2.setImageResource(integers2.get(1));
                            break;
                        case 3:
                            mLlgrouping1.setVisibility(View.VISIBLE);
                            mLlgrouping2.setVisibility(View.VISIBLE);
                            mLlgrouping3.setVisibility(View.VISIBLE);
                            mLlgrouping4.setVisibility(View.INVISIBLE);
                            mGrouping.setVisibility(View.VISIBLE);

                            //第一个模块
                            mTvcue1.setText(list.get(0));
                            List<Integer> integers3 = showGroupingView(position);
                            mIvicon1.setImageResource(integers3.get(0));

                            //第二个模块
                            mTvcue2.setText(list.get(1));
                            mIvicon2.setImageResource(integers3.get(1));

                            //第三个模块
                            mTvcue3.setText(list.get(2));
                            mIvicon3.setImageResource(integers3.get(2));

                            break;
                        case 4:
                            mLlgrouping1.setVisibility(View.VISIBLE);
                            mLlgrouping2.setVisibility(View.VISIBLE);
                            mLlgrouping3.setVisibility(View.VISIBLE);
                            mLlgrouping4.setVisibility(View.VISIBLE);
                            mGrouping.setVisibility(View.VISIBLE);

                            //第一个模块
                            mTvcue1.setText(list.get(0));
                            List<Integer> integers4 = showGroupingView(position);
                            mIvicon1.setImageResource(integers4.get(0));

                            //第二个模块
                            mTvcue2.setText(list.get(1));
                            mIvicon2.setImageResource(integers4.get(1));

                            //第三个模块
                            mTvcue3.setText(list.get(2));
                            mIvicon3.setImageResource(integers4.get(2));

                            //第四个模块
                            mTvcue4.setText(list.get(3));
                            mIvicon4.setImageResource(integers4.get(3));
                            break;
                    }
                }
            }
        };
        //加载适配器
        mRlopRecyclerView.setAdapter(mCommonAdapter);
    }

    /**点击事件*/
    private void Click(int position, int itme) {
        switch (position){
            case 1:             //行政管理
                switch (itme){
                    case 1:     //签到
                        startActivity(new Intent(getContext(),PunchTheClockActivity.class));
                        break;
                    case 2:     //请假
                        Intent mIntent = new Intent(getActivity().getApplicationContext(), HaveAHolidayActivity.class);
                        mIntent.putExtra("ask","AskForLeave");
                        startActivity(mIntent);
                        break;
                    case 3:     //审批
                        Intent mIntents = new Intent(getActivity().getApplicationContext(), ApproveActivity.class);
                        startActivity(mIntents);
                        break;
                    case 4:
                        break;
                }
                break;
            case 2:             //人事管理
                switch (itme){
                    case 1:     //入职
                        startActivity(new Intent(getActivity().getApplicationContext(), EntryBillsActivity.class));
                        break;
                    case 2:     //转正
                        startActivity(new Intent(getActivity().getApplicationContext(), BecomeARegularWorkerBillsActivity.class));
                        break;
                    case 3:     //离职
                        startActivity(new Intent(getActivity().getApplicationContext(), DimissionBillsActivity.class));
                        break;
                    case 4:     //调岗
                        startActivity(new Intent(getActivity().getApplicationContext(), TransferPositionActivity.class));
                        break;
                }
                break;
            case 3:             //财务管理
                if (itme == 1){
                    //收入
                    startActivity(new Intent(getActivity().getApplicationContext(), IncomeDetailActivity.class));
                }else if (itme == 2){
                    //支出
                    startActivity(new Intent(getActivity().getApplicationContext(), ExpensesActivity.class));
                }else if (itme ==3){
                    //报销
                    startActivity(new Intent(getActivity().getApplicationContext(), SubmitToExpenseAccountActivity.class));
                }
                break;
            case 4:             //卡券管理
                if (itme==1){
                    //会员卡
                    Intent albumIntent = new Intent(getActivity().getApplicationContext(), MembershipCardActivity.class);
                    startActivity(albumIntent);
                }else if (itme == 2){
                    //卡券设置
                    startActivity(new Intent(getActivity().getApplicationContext(), CardCouponsIntercalateActivity.class));
                }
                break;
            case 5:             //会员管理
                if (itme == 1){

                }else if (itme == 2){

                }
                break;
            case 6:             //教学管理
                if (itme == 1){

                }else if (itme == 2){

                }
                break;
            case 7:             //店铺管理
                if (itme==1){

                }else if (itme==2){
                    //相册
                    Intent albumIntent = new Intent(getActivity().getApplicationContext(), AlbumActivity.class);

                    startActivity(albumIntent);
                }
                break;
        }
    }

    private List<Integer> showGroupingView(int position){
        if (mDrawable.size()!=0)
        mDrawable.clear();
        switch (position){
            case 1:
                mDrawable.add(R.drawable.chuqindaka);
                mDrawable.add(R.drawable.qing_jia);
//                mDrawable.add(R.drawable.xiu_jia);
                mDrawable.add(R.drawable.shen_pi);
                break;
            case 2:
                mDrawable.add(R.drawable.ruzhi);
                mDrawable.add(R.drawable.zhuanzheng);
                mDrawable.add(R.drawable.lizhi);
                mDrawable.add(R.drawable.diaogang);
                break;

            case 3:
                mDrawable.add(R.drawable.shouru);
                mDrawable.add(R.drawable.zhichu);
                mDrawable.add(R.drawable.icon_bao_xiao);
                break;

            case 4:
                mDrawable.add(R.drawable.kaquancaozuo);
                mDrawable.add(R.drawable.kaquanshezhi);
                break;

            case 5:
                mDrawable.add(R.drawable.huiyuanxinxi);
                mDrawable.add(R.drawable.huiyuanpingjia);
                //mDrawable.add(R.drawable.icon_tui_song_xiao_xi);
                break;
            case 6:
                mDrawable.add(R.drawable.paike);
                mDrawable.add(R.drawable.kechengshezhi);
                mDrawable.add(R.drawable.ke_cheng_qian_dao);
                mDrawable.add(R.drawable.jiao_lian);
                break;
            case 7:
                mDrawable.add(R.drawable.dianpuxinxi);
                mDrawable.add(R.drawable.xiang_ce);
                break;
        }
        return mDrawable;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.head_tvOK:        //获取商家列表
                Intent intent = new Intent(getActivity().getApplicationContext(),CompanyListActivity.class);
                intent.putExtra("company",mUserDataBean.getBusinessName());
                intent.putExtra("position","OperatingFloorFragment");
                startActivity(intent);
                break;
        }
    }
}
