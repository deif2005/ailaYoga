package com.dodsport.consumer.fragment.homefragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dodsport.consumer.R;
import com.dodsport.consumer.activity.memberaboutclass.MemberAboutClassActivity;
import com.dodsport.consumer.adapter.adapter_recyclerview.CommonAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.MultiItemTypeAdapter;
import com.dodsport.consumer.adapter.adapter_recyclerview.base.ViewHolder;
import com.dodsport.consumer.fragment.BaseFragment;
import com.dodsport.consumer.util.TransformationUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页碎片管理
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.hotspot_vp)
    ViewPager mHotspotVp;
    @BindView(R.id.tv_img_desc)
    TextView mTvImgDesc;
    @BindView(R.id.ll_dot_group)
    LinearLayout mLlDotGroup;
    Unbinder unbinder;
    @BindView(R.id.rlClassify)
    RecyclerView mRlClassify;
    @BindView(R.id.tvDate)
    TextView mTvDate;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvAddress)
    TextView mTvAddress;
    @BindView(R.id.llDial)
    LinearLayout mLlDial;

    private Activity mActivity;
    private View mView;
    private boolean isSwitchPager = false; //默认不切换
    private int previousPosition = 0; //默认为0
    private Handler mHandler;
    private List<String> data = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;
    private String[] member = {"会员约课", "会员请假", "申请体验", "卡券购买"};
    /**
     * 头部广告轮播
     */
    private List<ImageView> vpLists = new ArrayList<ImageView>();
    private ArrayList<String> imageDesc = new ArrayList<>();
    private String TAG = "***首页碎片--";

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        unbinder = ButterKnife.bind(this, mView);
        mActivity = getActivity();
        mHotspotVp.setAdapter(new ViewpagerAdapter());
        initView();
        return mView;
    }


    /**
     * 初始化view
     */
    private void initView() {
        mRlClassify.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mHandler = new mHandler();

        adapter();


        ArrayList<String> imageDesc = new ArrayList<>();
        imageDesc.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        imageDesc.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        imageDesc.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        imageDesc.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        imageDesc.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        imageDesc.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        imageDesc.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");


        initViewPagerData(imageDesc);
        //设置当前viewpager要显示第几个条目
        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % vpLists.size());
        mHotspotVp.setCurrentItem(item);

        //把第一个小圆点设置为白色，显示第一个textview内容
        mLlDotGroup.getChildAt(previousPosition).setEnabled(true);
        //mTvImgDesc.setText(imageDesc.get(previousPosition).getTitle());
        //设置viewpager滑动的监听事件
        mHotspotVp.addOnPageChangeListener(this);
        //实现自动切换的功能
        new Thread() {
            public void run() {
                while (!isSwitchPager) {
                    SystemClock.sleep(2500);
                    //拿着我们创建的handler 发消息
                    mHandler.sendEmptyMessage(1);
                }
            }
        }.start();

        int items = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % vpLists.size());
        mHotspotVp.setCurrentItem(items);

    }


    private void adapter() {
        for (int i = 0; i < 4; i++) {
            data.add(member[i]);
        }

        mCommonAdapter = new CommonAdapter<String>(mActivity, R.layout.home_member_item, data) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                ImageView ivMember = holder.getView(R.id.ivMember);
                TextView tvMember = holder.getView(R.id.tvMember);
                tvMember.setText(s);
                switch (position) {
                    case 1:
                        ivMember.setImageResource(R.drawable.hui_yuan_yue_ke);
                        ivMember.setBackground(getResources().getDrawable(R.drawable.shape_white_point));
                        break;
                    case 2:
                        ivMember.setImageResource(R.drawable.hui_yuan_qing_jia);
                        ivMember.setBackground(getResources().getDrawable(R.drawable.shape_white_point2));
                        break;
                    case 3:
                        ivMember.setImageResource(R.drawable.shen_qing_ti_yan);
                        ivMember.setBackground(getResources().getDrawable(R.drawable.shape_white_point3));
                        break;
                    case 4:
                        ivMember.setImageResource(R.drawable.ka_quan_gou_mai);
                        ivMember.setBackground(getResources().getDrawable(R.drawable.shape_white_point4));
                        break;
                    default:
                        break;
                }

            }
        };

        mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position + 1) {
                    //会员约课
                    case 1:
                        startActivity(new Intent(mActivity, MemberAboutClassActivity.class));
                        break;
                    //会员请假
                    case 2:
                        break;
                    //申请体验
                    case 3:
                        break;
                    //卡券购买
                    case 4:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mRlClassify.setAdapter(mCommonAdapter);
    }

    @OnClick(R.id.llDial)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.llDial:
                String phone = "0755-2890 8652";
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:"+phone));
                startActivity(intent);
                break;
                default:
                    break;
        }
    }


    private class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //更新当前viewpager的 要显示的当前条目
                    try {
                        if (mHotspotVp == null) {
                            mHotspotVp = (ViewPager) mView.findViewById(R.id.hotspot_vp);
                        }
                        mHotspotVp.setCurrentItem(mHotspotVp.getCurrentItem() + 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                        mHandler.sendEmptyMessage(1);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 初始化ViewPager的数据
     *
     * @param mImageDesc
     */
    private void initViewPagerData(ArrayList<String> mImageDesc) {
        ImageView iv;
        View dotView;

        for (int i = 0; i < mImageDesc.size(); i++) {
            iv = new ImageView(getContext());
//            iv.setBackgroundResource(imgIds[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            Picasso.with(getContext().getApplicationContext()).load(mImageDesc.get(i))
                    .resize(400, 400)
                    .config(Bitmap.Config.RGB_565)
                    .error(R.drawable.jia_zai_shi_bai)
                    .placeholder(R.drawable.jia_zai_shi_bai)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .transform(TransformationUtils.zipImage(iv))
                    .into(iv);


            vpLists.add(iv);
            //准备小圆点的数据
            dotView = new View(getContext().getApplicationContext());
            dotView.setBackgroundResource(R.drawable.selector_dot);
            //设置小圆点的宽和高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            //设置每个小圆点之间距离
            if (i != 0) {
                params.leftMargin = 12;
            }
            dotView.setLayoutParams(params);
            //设置小圆点默认状态
            dotView.setEnabled(false);
            //把dotview加入到线性布局中
            mLlDotGroup.addView(dotView);
        }


    }

    /**
     * Viewpager适配器
     */
    private class ViewpagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        //是否复用当前view对象
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //初始化每个条目要显示的内容
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //拿着position位置 % 集合.size
            final int newposition = position % vpLists.size();
            //获取到条目要显示的内容imageview
            ImageView iv = vpLists.get(newposition);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onClickvp(newposition);
                }
            });
            //要把 iv加入到 container 中
            container.addView(iv);
            return iv;
        }

        //销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //移除条目
            container.removeView((View) object);
        }

    }

    /**
     * Viewpageritem点击事件
     */
  /*  public void onClickvp(int position) {
        String webUrl = UrlInterfaceManager.WEBURL;
        String url = webUrl + imageDesc.get(position).getN_ID();
        Intent intent = new Intent(getActivity(), ConWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("essay", imageDesc.get(position));
        getActivity().startActivity(intent);
    }*/

    //当新的页面被选中的时候调用
    @Override
    public void onPageSelected(int position) {
        //拿着position位置 % 集合.size
        int newposition = position % vpLists.size();
        //取出postion位置的小圆点 设置为true
        if (mLlDotGroup == null) {
            mLlDotGroup = (LinearLayout) mView.findViewById(R.id.ll_dot_group);
        }
        mLlDotGroup.getChildAt(newposition).setEnabled(true);
        //把一个小圆点设置为false
        mLlDotGroup.getChildAt(previousPosition).setEnabled(false);
        if (mTvImgDesc == null) {
            mTvImgDesc = (TextView) mView.findViewById(R.id.tv_img_desc);
        }
        //mTvImgDesc.setText(imageDesc.get(newposition).getTitle());
        previousPosition = newposition;
    }


    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //当页面开始滑动
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    //懒加载
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        mHasLoadedOnce = true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
