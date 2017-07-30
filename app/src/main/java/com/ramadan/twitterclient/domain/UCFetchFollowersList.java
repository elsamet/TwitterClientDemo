package com.ramadan.twitterclient.domain;

import com.ramadan.twitterclient.data.network.ApiRepository;
import com.ramadan.twitterclient.data.network.FollowersResponse;

import rx.Observable;


/**
 * Created by Mahmoud Ramadan on 7/25/17.
 */

public class UCFetchFollowersList implements UseCase<FollowersResponse> {

    public ApiRepository apiRepository;
    public long id, cursor;

    public UCFetchFollowersList(ApiRepository apiRepository) {

        this.apiRepository = apiRepository;
    }

    public void setArgs(long id, long cursor) {
        this.id = id;
        this.cursor = cursor;
    }

    @Override
    public Observable<FollowersResponse> execute() {
        return apiRepository.fetchFollowers(id, cursor);
    }
}
