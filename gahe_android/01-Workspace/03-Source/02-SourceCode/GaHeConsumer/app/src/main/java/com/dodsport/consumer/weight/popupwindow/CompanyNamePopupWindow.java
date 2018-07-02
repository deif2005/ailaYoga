package com.dodsport.consumer.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dodsport.consumer.R;
import com.dodsport.consumer.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.consumer.rxbus.PopupWindowBus;
import com.dodsport.consumer.rxbus.RxBus;

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
    private PopupWindowBus ask = new PopupWindowBus();
    private String mPosition="";
    private int viewId = 0;


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
//        ImageView ivShut = mMenuView.findViewById(R.id.ivShut);
        setParams();

        //关闭
//        ivShut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });

        if (position.equals("Attendance")){
            viewId = R.layout.typename_item;
        }
//        else if (position.equals("AlbumName")){
//            viewId = R.layout.albumname_item;
//
//        }else {
//            viewId = R.layout.companyname_item;
//        }
        mCommonAdapter  = new CommonAdapter<String>(mActivity,viewId,data) {
            @Override
            protected void convert(ViewHolder holder, String cnLsitBean, int position) {
                TextView tvCompanyName_item = holder.getView(R.id.tvCompanyName_item);
                tvCompanyName_item.setText(data.get(position)+"");
                tvCompanyName_item.setTextColor(activity.getResources().getColor(R.color.home_text_normal));

            }
        };

        adapter();
        mRecyclerView.setAdapter(mCommonAdapter);

    }


    /**相册名字*/
    public CompanyNamePopupWindow(final Activity activity, final TextView mNameView,final List<String> data,String position,int i) {
        super(activity);
        this.mActivity = activity;
        this.data = data;
        this.mNameView = mNameView;
        this.mPosition = position;
        this.viewId = i;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_company_name, null);
        mRecyclerView = mMenuView.findViewById(R.id.rvCompanyName);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        setParams();

//        mCommonAdapter  = new CommonAdapter<String>(mActivity,R.layout.albumname_item,data) {
//            @Override
//            protected void convert(ViewHolder holder, String cnLsitBean, int position) {
//                TextView tvCompanyName_item = holder.getView(R.id.tvCompanyName_item);
//                tvCompanyName_item.setText(data.get(position)+"");
//                tvCompanyName_item.setTextColor(activity.getResources().getColor(R.color.home_text_normal));
//                ImageView imageView = holder.getView(R.id.ImageView);
//                if (viewId == position){
//                    imageView.setVisibility(View.VISIBLE);
//                }else {
//                    imageView.setVisibility(View.GONE);
//                }
//
//            }
//        };
//
//        adapter();
//        mRecyclerView.setAdapter(mCommonAdapter);

    }


    /**加载适配器*/
    private void adapter(){

        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mNameView!=null){
                    mNameView.setText(data.get(position)+"");
                }
                ask.setType(mPosition);
                ask.setmPosition(position);
                ask.setMsg(data.get(position));
                RxBus.getInstance().post(ask);
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
        if (mPosition.equals("Attendance")){
            this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        }else {
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        }

        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        //设置SelectPicPopupWindow弹出窗体动画效果
        if (mPosition.equals("AlbumName")){
            this.setAnimationStyle(R.style.take_photo_anim);
        }else {
            this.setAnimationStyle(R.style.popmenu_animation);
        }

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        this.setBackgroundDrawable(dw);
        if(true)
        {
            this.setBackgroundAlpha(mActivity,0.7f);
        }
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.lllatview).getTop();
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
                setBackgroundAlpha(mActivity,1f);
                if (ivCompanytName!=null){
                    ivCompanytName.setImageResource(R.drawable.xiayibu);
                }
            }
        });
    }

    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明
     */
    public void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }



}
