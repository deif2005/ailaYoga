package com.dodsport.activity.teachschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * 排课
 *
 * */
public class CourseArrangementActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.llLeagueClass)
    LinearLayout mLlLeagueClass;
    @Bind(R.id.llPrivateCoach)
    LinearLayout mLlPrivateCoach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_arrangement);
        ButterKnife.bind(this);
        initVIew();
    }

    private void initVIew() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("排课");


    }

    @OnClick({R.id.head_ivBack, R.id.llLeagueClass, R.id.llPrivateCoach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_ivBack:
                finish();
                break;
            case R.id.llLeagueClass:        //团课
                startActivity(new Intent(this,LeagueClassActivity.class));
                break;
            case R.id.llPrivateCoach:       //私教
                startActivity(new Intent(this,PrivateCoachArrangementActivity.class));
                break;
        }
    }
}
