package com.dodsport.consumer.fragment.homefragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodsport.consumer.R;
import com.dodsport.consumer.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * @author Administrator
 */
public class PrivateCoachFragment extends BaseFragment {


    public PrivateCoachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_private_coach, container, false);
    }

    @Override
    protected void lazyLoad() {

    }
}
