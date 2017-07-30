package com.ramadan.twitterclient.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mahmoud Ramadan on 7/25/17.
 */

public interface MyTwitterService {

    @GET("/1.1/followers/list.json?count=25")
    Call<FollowersResponse> getFollowers(@Query("user_id") long id, @Query("cursor") long cursor);

}
