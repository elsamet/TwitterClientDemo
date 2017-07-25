package com.ramadan.twitterclient.presentation.presenter;

import com.google.gson.Gson;
import com.ramadan.twitterclient.AppConst;
import com.ramadan.twitterclient.MyApp;
import com.ramadan.twitterclient.data.file.Prefs;
import com.ramadan.twitterclient.presentation.activity.LoginView;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mView;

    public LoginPresenterImpl(LoginView mView) {
        this.mView = mView;
    }

    @Override
    public void checkPreviousLogin() {
        Gson gson = new Gson();

        String userJson = Prefs.getInstance(MyApp.getInstance().getApplicationContext()).getStringValue(AppConst.USER);

        if(!userJson.equals("")){
            com.ramadan.twitterclient.presentation.model.User user =
                    gson.fromJson(userJson, com.ramadan.twitterclient.presentation.model.User.class);

            mView.onPreviousLoginExisted(true);
        }else{
            mView.onPreviousLoginExisted(false);
        }

    }
}
