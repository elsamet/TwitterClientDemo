package com.ramadan.twitterclient.presentation.activity.follower_info;

import com.ramadan.twitterclient.data.FollowerInfo;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public interface FollowerInfoView {
    void setFollowerData(FollowerInfo followerInfo);

    void showTweetsList(List<Tweet> tweets);

    void showIndicator();

    void hideIndicator();

    void showNoResultMessage();

    void showApiLimitMessage();

    void showToastMessage(String msg);

    void showNoNetworkMessage();
}
