package com.ramadan.twitterclient.presentation.activity.login;

import android.app.Activity;
import android.content.Intent;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.net.UnknownHostException;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mView;
    private Activity activity;
    TwitterAuthClient authClient;

    public LoginPresenterImpl(Activity activity, TwitterAuthClient authClient ,LoginView mView) {
        this.mView = mView;
        this.activity = activity;
        this.authClient = authClient;
    }


    @Override
    public void authenticate() {

        authClient.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                mView.showFollowers(result.data.getUserId());
            }

            @Override
            public void failure(TwitterException exception) {

                //if there is no internet connection then display an error msg
                if (exception.getCause() instanceof UnknownHostException) {
                    mView.showNoNetworkMessage();
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            authClient.onActivityResult(requestCode, resultCode, data);

        } catch (TwitterException e) {
            mView.showAuthorizeFailedMessage();
        }
    }

    @Override
    public void stop() {
        authClient.cancelAuthorize();
    }
}
