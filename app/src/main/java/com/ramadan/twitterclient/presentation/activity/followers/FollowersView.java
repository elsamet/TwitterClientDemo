package com.ramadan.twitterclient.presentation.activity.followers;

import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public interface FollowersView {

    void setTitle(String username);

    void showFollowersList(List<User> users);

    void openFollowerDetailsUI(User follower);

    void showIndicator();

    void hideIndicator();

    void showNoResultMessage();

    void showApiLimitMessage();

    void showToastMessage(String msg);

    void showNoNetworkMessage();

}
