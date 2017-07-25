package com.ramadan.twitterclient.presentation.activity;

import android.content.Context;
import android.content.Intent;

import com.ramadan.twitterclient.R;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowersActivity extends BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_followers_list;
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, FollowersActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }
}
