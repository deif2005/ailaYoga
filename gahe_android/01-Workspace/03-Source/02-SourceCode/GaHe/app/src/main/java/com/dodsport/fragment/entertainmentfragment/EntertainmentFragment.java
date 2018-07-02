package com.dodsport.fragment.entertainmentfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodsport.R;
import com.dodsport.fragment.BaseFragment;


/**
 * 娱乐模块
 * */
public class EntertainmentFragment extends BaseFragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_entertainment, container, false);
        return mView;
    }



    @Override
    protected void lazyLoad() {

    }
}
