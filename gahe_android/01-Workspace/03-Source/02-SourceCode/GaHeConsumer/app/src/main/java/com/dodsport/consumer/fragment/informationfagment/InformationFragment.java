package com.dodsport.consumer.fragment.informationfagment;


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
public class InformationFragment extends BaseFragment {


    private View mView;
    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_information, container, false);
        }
        return mView;
    }

    @Override
    protected void lazyLoad() {

    }
}
