package com.dodsport.activity.personnel;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.activity.expenses.expenseaccountmanage.MyGridView;
import com.dodsport.weight.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryBillsDetailActivity extends BaseActivity {

    @Bind(R.id.tvItem1)
    TextView mTvItem1;
    @Bind(R.id.tvItem2)
    TextView mTvItem2;
    @Bind(R.id.tvItem3)
    TextView mTvItem3;
    @Bind(R.id.tvItem4)
    TextView mTvItem4;
    @Bind(R.id.tvItem5)
    TextView mTvItem5;
    @Bind(R.id.tvItem6)
    TextView mTvItem6;
    @Bind(R.id.tvItem7)
    TextView mTvItem7;
    @Bind(R.id.tvItem8)
    TextView mTvItem8;
    @Bind(R.id.tvItem9)
    TextView mTvItem9;
    @Bind(R.id.tvItem10)
    TextView mTvItem10;
    @Bind(R.id.tvItem11)
    TextView mTvItem11;
    @Bind(R.id.UserHead)
    CircleImageView mUserHead;
    @Bind(R.id.gvTeacherEntry)
    MyGridView mGvTeacherEntry;
    @Bind(R.id.tvItem12)
    TextView mTvItem12;
    @Bind(R.id.TeacherSynopsis)
    TextView mTeacherSynopsis;
    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_bills_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("报销单");
    }

    @OnClick(R.id.head_ivBack)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.head_ivBack:
                finish();
                break;
        }
    }

    /**
     * 获取网络数据
     * */
    private void getNetData(){

    }
}
