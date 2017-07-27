package com.ramadan.twitterclient.presentation.presenter;

import com.ramadan.twitterclient.presentation.activity.follower_info.FollowerInfoView;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowerInfoPresenterImpl implements FollowerInfoPresenter {
    private FollowerInfoView mView;

    public FollowerInfoPresenterImpl(FollowerInfoView mView) {
        this.mView = mView;
    }

}
