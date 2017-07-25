package com.ramadan.twitterclient.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.ramadan.twitterclient.AppConst;
import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.data.file.Prefs;
import com.ramadan.twitterclient.presentation.presenter.LoginPresenter;
import com.ramadan.twitterclient.presentation.presenter.LoginPresenterImpl;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import butterknife.BindView;

import static com.ramadan.twitterclient.data.file.Prefs.getInstance;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.login_button)
    TwitterLoginButton loginButton;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPresenter = new LoginPresenterImpl(this);

        loginPresenter.checkPreviousLogin();



        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                com.ramadan.twitterclient.presentation.model.User user =
                        new com.ramadan.twitterclient.presentation.model.User(result.data.getUserName(),
                                result.data.getUserId());


                Gson gson = new Gson();
                String json = gson.toJson(user);
                Prefs.getInstance(getApplicationContext()).setStringValue(AppConst.USER, json);

                //open followers list activity
                FollowersActivity.start(getApplicationContext());

            }

            @Override
            public void failure(TwitterException exception) {

                Log.d("t",exception.getMessage());

            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPreviousLoginExisted(boolean flag) {
        if (flag)
            FollowersActivity.start(getApplicationContext());


    }
}
