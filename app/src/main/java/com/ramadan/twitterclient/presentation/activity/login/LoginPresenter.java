package com.ramadan.twitterclient.presentation.activity.login;

import android.content.Intent;

import com.ramadan.twitterclient.presentation.presenter.BasePresenter;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public interface LoginPresenter extends BasePresenter {

    void authenticate();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void stop();

}
