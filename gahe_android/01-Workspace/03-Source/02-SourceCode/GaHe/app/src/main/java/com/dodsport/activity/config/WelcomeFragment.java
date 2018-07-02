package com.dodsport.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dodsport.GaHeApplication;
import com.dodsport.R;

/**
 * 展示第一次登陆的欢迎页（碎片类）
 */
public class WelcomeFragment extends Fragment {

    private static final String IMAGE = "image";
    private static final String BUTTON = "button";
    private static final String CLASS = "class";
    private OnButtonClickListener mOnButtonClickListener;
    private static int conen = 1;
    private static int conen2 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        conen = conen * 2;
        View view = inflater.inflate(R.layout.fragment_firswelcome_1, container, false);
        ImageView imageView = view.findViewById(R.id.image);
        try {
            imageView.setImageBitmap(BitmapTool.getBitmap(getActivity().getResources(),getArguments().getInt(IMAGE),
                    GaHeApplication.mDisplayMetrics.widthPixels, GaHeApplication.mDisplayMetrics.heightPixels));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getArguments().getBoolean(BUTTON, false)) {
            TextView button = (TextView) view.findViewById(R.id.start_btn);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), (Class<?>) getArguments().getSerializable(CLASS)));
                    if (mOnButtonClickListener != null) {
                        mOnButtonClickListener.onClick(v);
                    }
                }
            });
        }
        return view;
    }

    public static WelcomeFragment getInstance(int imageId, boolean showBtn, Class<?> jumpClass) {
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMAGE, imageId);
        bundle.putBoolean(BUTTON, showBtn);
        bundle.putSerializable(CLASS, jumpClass);
        welcomeFragment.setArguments(bundle);
        return welcomeFragment;
    }

    public static WelcomeFragment getInstance(int imageId) {
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMAGE, imageId);
        welcomeFragment.setArguments(bundle);
        return welcomeFragment;
    }

    public interface OnButtonClickListener {
        void onClick(View v);

    }

    public void setOnButtonClickListener(OnButtonClickListener mOnButtonClickListener) {
        this.mOnButtonClickListener = mOnButtonClickListener;

    }

}
