package com.ramadan.twitterclient.presentation.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.presentation.activity.BaseActivity;
import com.ramadan.twitterclient.presentation.activity.followers.FollowersActivity;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private LoginPresenter loginPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init the auth client from Twitter sdk
        TwitterAuthClient authClient = new TwitterAuthClient();

        loginPresenter = new LoginPresenterImpl(this, authClient, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //delegates auth client onActivityResult to the presenter to take actions upon
        loginPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.login_button)
    void onLoginButtonClick(View v) {
        loginPresenter.authenticate();
    }

    /**
     * opens {@link FollowersActivity} to list this user's followers
     */
    @Override
    public void showFollowers(long userId) {
        FollowersActivity.start(getApplicationContext());
    }

    @Override
    public void showNoNetworkMessage() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_network), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showAuthorizeFailedMessage() {
        Toast.makeText(this, getString(R.string.error_authorization), Toast.LENGTH_SHORT).show();
    }
}
