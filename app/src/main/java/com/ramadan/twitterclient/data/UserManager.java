package com.ramadan.twitterclient.data;

import android.content.Context;

import com.ramadan.twitterclient.data.network.MYTwitterAPIClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.Map;

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
    public MYTwitterAPIClient getActiveApiClient() {
        TwitterCore.getInstance().addApiClient(getActiveTwitterSession(), new MYTwitterAPIClient(context, getActiveTwitterSession()));
        return (MYTwitterAPIClient) TwitterCore.getInstance().getApiClient();
    }

    public TwitterSession getActiveTwitterSession() {
        return TwitterCore.getInstance().getSessionManager().getActiveSession();
    }

    public boolean isLoggedIn() {
        return getActiveTwitterSession() != null;
    }

    public void setActiveUser(String username) {
        Map<Long, TwitterSession> sessions = TwitterCore.getInstance().getSessionManager().getSessionMap();
        for (TwitterSession s : sessions.values()) {
            if (username.equals(s.getUserName())) {
                TwitterCore.getInstance().getSessionManager().setActiveSession(s);
                TwitterCore.getInstance().addApiClient(s, new MYTwitterAPIClient(context, s));
                break;
            }
        }
    }

}
