package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.eventBus.AskForLeaveTypeEvent;
import com.dodsport.utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 店家名称列表
 */
public class CompanyNamePopupWindow extends PopupWindow{

    private Activity mActivity;
    private View mMenuView;
    private RecyclerView mRecyclerView;
    private ImageView ivCompanytName;
    private List<String> data;
    private CommonAdapter<String> mCommonAdapter;
    private TextView mNameView;
    private AskForLeaveTypeEvent ask = new AskForLeaveTypeEvent();
    private String mPosition="";


    public CompanyNamePopupWindow(final Activity activity, ImageView ivCompanytName, final TextView mNameView, final List<String> data,String position) {
        super(activity);
        this.mActivity = activity;
        this.ivCompanytName = ivCompanytName;
        this.data = data;
        this.mNameView = mNameView;
        this.mPosition = position;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_company_name, null);
        mRecyclerView = mMenuView.findViewById(R.id.rvCompanyName);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        setParams();

        mCommonAdapter  = new CommonAdapter<String>(mActivity,R.layout.companyname_item,data) {
            @Override
            protected void convert(ViewHolder holder, String cnLsitBean, int position) {
                TextView tvCompanyName_item = holder.getView(R.id.tvCompanyName_item);
                tvCompanyName_item.setText(data.get(position)+"");
                tvCompanyName_item.setTextColor(activity.getResources().getColor(R.color.home_text_normal));
            }
        };

        mRecyclerView.setAdapter(mCommonAdapter);
        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mNameView.setText(data.get(position)+"");
//                if (mPosition.equals("BusiPosition")){
//                    ask.setType("PositionName");
//                }else {
//
//                }
                ask.setType(mPosition);
                ask.setmPosition(position);
                EventBus.getDefault().post(ask);
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

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
        this.setAnimationStyle(R.style.popmenu_animation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        this.setBackgroundDrawable(dw);
        ScreenUtils.setBackgroundAlpha(mActivity, 1.0f);
//        setBackgroundAlpha(0.7f);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.relatview).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
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
                ivCompanytName.setImageResource(R.drawable.xiayibu);
            }
        });
    }

}
