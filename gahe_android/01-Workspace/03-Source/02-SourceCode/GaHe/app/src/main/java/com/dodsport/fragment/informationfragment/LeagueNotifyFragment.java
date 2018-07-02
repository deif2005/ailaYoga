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
 * 联盟通知
 */
public class LeagueNotifyFragment extends BaseFragment {


    private View mView;

    public LeagueNotifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView==null){
            mView = inflater.inflate(R.layout.fragment_league_notify, container, false);
        }
        return mView;
    }

    @Override
    protected void lazyLoad() {

    }
}
