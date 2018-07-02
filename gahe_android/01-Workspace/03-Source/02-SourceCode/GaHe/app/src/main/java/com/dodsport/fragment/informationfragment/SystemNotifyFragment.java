package com.dodsport.fragment.informationfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodsport.R;
import com.dodsport.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 * 系统通知
 */
public class SystemNotifyFragment extends BaseFragment {


    public SystemNotifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system_notify, container, false);
    }

    @Override
    protected void lazyLoad() {

    }
}
