package com.ramadan.twitterclient.common;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ramadan.twitterclient.R;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

//This class manages Twitter Initialization and get global access to our app
public class MyApp extends Application {

    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.TWITTER_KEY),
                        getString(R.string.TWITTER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        AppLocal.setAppLocal(getApplicationContext(), AppLocal.getAppLocal());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        instance = null;
    }

    public static MyApp getInstance(){
        return  instance;

    }

    public SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}
