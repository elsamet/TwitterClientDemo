package com.ramadan.twitterclient.presentation.activity.login;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public interface LoginView {

    void showFollowers(long userId);

    void showNoNetworkMessage();

    void showAuthorizeFailedMessage();
}
