package com.ramadan.twitterclient.data.network;

import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by Mahmoud Ramadan on 7/28/17.
 */

public class FollowersResponse {

    public List<User> users;

    public long next_cursor;
    public long previous_cursor;
    public String next_cursor_str;
    public String previous_cursor_str;
}
