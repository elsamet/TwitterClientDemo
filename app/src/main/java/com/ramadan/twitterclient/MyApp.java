package com.ramadan.twitterclient;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class MyApp extends Application {

    private MyApp instance;

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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        instance = null;
    }

    public MyApp getInstance(){
        return  instance;

    }
}