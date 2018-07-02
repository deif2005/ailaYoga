package com.dodsport.activity.teachschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.activity.BaseActivity;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.PrivateCoursePlanListBean;
import com.dodsport.request.UrlInterfaceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看私教排课信息
 */
public class SeePrivateInfoActivity extends BaseActivity {

    @Bind(R.id.head_ivBack)
    ImageView mHeadIvBack;
    @Bind(R.id.head_tvTitle)
    TextView mHeadTvTitle;
    @Bind(R.id.tvEndTime1)
    TextView mTvEndTime1;
    @Bind(R.id.tvEndTime3)
    TextView mTvEndTime3;
    @Bind(R.id.tvEndTime5)
    TextView mTvEndTime5;
    @Bind(R.id.rvCourse2)
    RecyclerView mRvCourse2;
    @Bind(R.id.tvEndTime4)
    TextView mTvEndTime4;
    private CommonAdapter<String> mCommonAdapter2;
    private PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList mPrivateCourse;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_private_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeadIvBack.setVisibility(View.VISIBLE);
        mHeadTvTitle.setVisibility(View.VISIBLE);
        mHeadTvTitle.setText("查看");
        mRvCourse2.setLayoutManager(new GridLayoutManager(this,2));
        Intent intent = getIntent();
        mPrivateCourse = (PrivateCoursePlanListBean.DatasBean.PrivateCoursePlanList) intent.getSerializableExtra("object");
        if (mPrivateCourse==null){
            return;
        }
        mTvEndTime1.setText(mPrivateCourse.getClassDate());
        mTvEndTime3.setText(mPrivateCourse.getEmployeeName());
        mTvEndTime4.setText("("+ UrlInterfaceManager.jobTitle[Integer.parseInt(mPrivateCourse.getJobTitle())]+")");
        mTvEndTime5.setText(mPrivateCourse.getClassroomName());
        LeagueLectureAdapter();

    }

    /**加载适配器*/
    private void LeagueLectureAdapter(){

        // ","隔开的多个 转换成数组
        String[] sArray = mPrivateCourse.getClassDatetime().split(",");
        for (String sTagItme : sArray) {
            data.add(sTagItme);
        }

        mCommonAdapter2 = new CommonAdapter<String>(this, R.layout.course_timelist_item, data) {
            @Override
            protected void convert(ViewHolder holder, String coursePlanList, final int position) {
                ImageView iconDelete = holder.getView(R.id.icon_Delete);
                TextView tvCardTypename = holder.getView(R.id.tvCardTypename);
                tvCardTypename.setText(coursePlanList);
                iconDelete.setVisibility(View.GONE);
            }
        };

        mRvCourse2.setAdapter(mCommonAdapter2);

    }

    @OnClick(R.id.head_ivBack)
    public void onViewClicked() {
        finish();
    }
}
