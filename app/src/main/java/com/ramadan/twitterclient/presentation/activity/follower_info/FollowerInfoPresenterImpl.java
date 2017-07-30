package com.ramadan.twitterclient.presentation.activity.follower_info;

import android.support.annotation.NonNull;

import com.ramadan.twitterclient.data.FollowerInfo;
import com.ramadan.twitterclient.data.UserManager;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowerInfoPresenterImpl implements FollowerInfoPresenter {
    private FollowerInfoView mView;
    private FollowerInfo followerInfo;
    private UserManager userManager;
    private Call<List<Tweet>> followerLast10TweetsCall;

    public FollowerInfoPresenterImpl(@NonNull FollowerInfo followerInfo, @NonNull UserManager userManager,
                                     @NonNull FollowerInfoView mView) {
        this.followerInfo = followerInfo;
        this.userManager = userManager;
        this.mView = mView;
    }

    @Override
    public void start() {
        mView.setFollowerData(followerInfo);

        loadLastTweets();
    }

    @Override
    public void stop() {
        followerLast10TweetsCall.cancel();
    }

    private void loadLastTweets() {
        mView.showIndicator();

        followerLast10TweetsCall = userManager.getActiveApiClient().getStatusesService().userTimeline(
                followerInfo.getId(), null, 10, null, null, null, true, null, null);
        followerLast10TweetsCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                if (result.data != null) {
                    if (result.data.size() > 0) {
                        mView.hideIndicator();
                        mView.showTweetsList(result.data);
                    } else {
                        mView.hideIndicator();
                        mView.showNoResultMessage();
                    }
                }
            }

            @Override
            public void failure(TwitterException exception) {
                exception.printStackTrace();
                if (followerLast10TweetsCall.isCanceled()) return;

                mView.hideIndicator();

                if (exception instanceof TwitterApiException) {
                    TwitterApiException apiException = (TwitterApiException) exception;
                    if (apiException.getErrorCode() == 429)
                        mView.showApiLimitMessage();
                    else
                        mView.showToastMessage(apiException.getErrorMessage());
                } else if (exception.getCause() instanceof UnknownHostException) {
                    mView.showNoNetworkMessage();
                }
            }
        });
    }
}
