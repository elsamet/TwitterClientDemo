package com.ramadan.twitterclient.presentation.presenter;

import android.view.View;

import com.ramadan.twitterclient.presentation.activity.FollowerInfoView;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowerInfoPresenterImpl implements FollowerInfoPresenter {
    private FollowerInfoView mView;

    public FollowerInfoPresenterImpl(FollowerInfoView mView) {
        this.mView = mView;
    }

}
