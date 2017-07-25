package com.ramadan.twitterclient.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public abstract class BaseFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(getLayoutResId(),container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    abstract int getLayoutResId();

}
