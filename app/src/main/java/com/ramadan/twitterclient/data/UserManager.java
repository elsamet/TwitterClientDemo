package com.ramadan.twitterclient.data;

import android.content.Context;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mahmoud Ramadan on 7/26/17.
 */

//This class manages current active user and its twitter session and My TwitterApiClient
public class UserManager {

    private static UserManager INSTANCE;
    private Context context;

    private UserManager(Context context) {
        this.context = context;
    }

    public static synchronized UserManager getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new UserManager(context);
        return INSTANCE;
    }


    public TwitterSession getActiveTwitterSession() {
        return TwitterCore.getInstance().getSessionManager().getActiveSession();
    }

    public boolean isLoggedIn() {
        return getActiveTwitterSession() != null;
    }


}
