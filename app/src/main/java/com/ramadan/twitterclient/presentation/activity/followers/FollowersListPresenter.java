package com.ramadan.twitterclient.presentation.activity.followers;

import com.ramadan.twitterclient.presentation.presenter.BasePresenter;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public interface FollowersListPresenter  extends BasePresenter {

    void loadFollowersList(boolean reload);

    void openFollowerDetails(User follower);

    void setActiveUser(String userName);

    void stop();

}
