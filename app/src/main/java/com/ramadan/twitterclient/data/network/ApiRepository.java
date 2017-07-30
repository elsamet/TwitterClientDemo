package com.ramadan.twitterclient.data.network;

import rx.Observable;

/**
 * Created by Mahmoud Ramadan on 7/28/17.
 */


 // Use Cases will use this interface to fetch all tweets and followers from twitter apis

public interface ApiRepository {
    Observable<FollowersResponse> fetchFollowers(long id , long cursor);
}
