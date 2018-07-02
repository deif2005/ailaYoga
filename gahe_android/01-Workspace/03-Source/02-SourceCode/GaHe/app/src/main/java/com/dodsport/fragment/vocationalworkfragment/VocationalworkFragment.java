package com.dodsport.fragment.vocationalworkfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodsport.R;
import com.dodsport.fragment.BaseFragment;

/**
 * 业务模块
 *
 * */
public class VocationalworkFragment extends BaseFragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_vocationalwork, container, false);
        return mView;
    }



    @Override
    protected void lazyLoad() {

    }
}
