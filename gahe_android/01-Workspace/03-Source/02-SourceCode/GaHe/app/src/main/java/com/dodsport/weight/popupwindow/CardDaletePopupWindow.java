package com.dodsport.weight.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dodsport.R;
import com.dodsport.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.model.CardTypeListBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 删除会员卡种类的popupWindow
 */

public class CardDaletePopupWindow extends PopupWindow{

    private View mMenuView;
    private Activity mContext;
    private int type = 0;
    public List<String> CardTypeName = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;
    private RecyclerView rvCardTypeName;
    public ImageView ivType;
    public TextView tvCardTypeName;
    public LinearLayout llCardType;
    public String CardTypeId = "";
    public RelativeLayout RvCarddType;

    /**
     *
     * @param context
     * @param itemsOnClick   事件
     * @param strings   内容数组
     */
    public CardDaletePopupWindow(Activity context, View.OnClickListener itemsOnClick, String... strings) {
        super(context);
        this.mContext =  context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_carddelete, null);
        Button btConservation = mMenuView.findViewById(R.id.btConservation); //保存
        Button btCancel = mMenuView.findViewById(R.id.btCancel);    //取消
        btConservation.setOnClickListener(itemsOnClick);

        //取消按钮
        btCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //销毁弹出框
                dismiss();
           }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.take_photo_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        this.setBackgroundDrawable(dw);
        if(true)
        {
            setBackgroundAlpha(mContext,0.7f);
        }
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.rl_height).getTop();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        //添加pop窗口关闭事件
        this.setOnDismissListener(new poponDismissListener());
    }

    /**加载卡券类型列表*/
    private void showCardTypeNmae(List<String> cardTypeName, final CardTypeListBean cardTypeListBean){
        mCommonAdapter = new CommonAdapter<String>(mContext,R.layout.companyname_item,cardTypeName) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                final TextView tvCompanyName_item = holder.getView(R.id.tvCompanyName_item);
                final LinearLayout llName = holder.getView(R.id.llName);

                if (type == position){
                    tvCompanyName_item.setTextColor(mContext.getResources().getColor(R.color.white));
                    llName.setBackgroundColor(mContext.getResources().getColor(R.color.home_text_selected));
                }else {
                    tvCompanyName_item.setTextColor(mContext.getResources().getColor(R.color.home_text_normal));
                    llName.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                tvCompanyName_item.setText(s.toString());
                llName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RvCarddType.setEnabled(true);
                        type = position;
                        ivType.setImageResource(R.drawable.xiayibu);
                        tvCardTypeName.setText(CardTypeName.get(position));
                        tvCompanyName_item.setTextColor(mContext.getResources().getColor(R.color.white));
                        llName.setBackgroundColor(mContext.getResources().getColor(R.color.home_text_selected));
                        llCardType.setVisibility(View.GONE);
                        CardTypeId = cardTypeListBean.getDatas().getMemberCardTypeList().get(position).getId();
                    }
                });
            }

        };

        rvCardTypeName.setAdapter(mCommonAdapter);
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


    private class poponDismissListener implements OnDismissListener {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(mContext,1f);
        }
    }
}
