package com.dodsport.consumer.fragment.myfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodsport.consumer.R;
import com.dodsport.consumer.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment {


    private View mView;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null){

            mView = inflater.inflate(R.layout.fragment_my, container, false);
        }
        return mView;

    }

    @Override
    protected void lazyLoad() {

    }
}
