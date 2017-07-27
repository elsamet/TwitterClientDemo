package com.ramadan.twitterclient.data.network;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Mahmoud Ramadan on 7/25/17.
 */

public class MYTwitterAPIClient extends TwitterApiClient {

   public MYTwitterAPIClient(TwitterSession session){
        super(session);
    }

    public MyTwitterService getTwitterService(){
        return  getService(MyTwitterService.class);
    }
}
