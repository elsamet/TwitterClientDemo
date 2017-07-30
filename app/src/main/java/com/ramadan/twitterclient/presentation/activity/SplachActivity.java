package com.ramadan.twitterclient.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ramadan.twitterclient.common.AppConst;
import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.data.UserManager;
import com.ramadan.twitterclient.presentation.activity.followers.FollowersActivity;
import com.ramadan.twitterclient.presentation.activity.login.LoginActivity;

public class SplachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        getSupportActionBar().hide();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (UserManager.getInstance(getApplicationContext()).isLoggedIn()) {
                    Intent intent = new Intent(getApplicationContext(), FollowersActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, AppConst.SPLACH_DURATION);


    }
}
