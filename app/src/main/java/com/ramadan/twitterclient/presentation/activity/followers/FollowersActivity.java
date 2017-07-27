package com.ramadan.twitterclient.presentation.activity.followers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.presentation.activity.BaseActivity;
import com.ramadan.twitterclient.presentation.adapter.FollowersAdapter;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowersActivity extends BaseActivity implements FollowersView{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.errorMessage)
    TextView errorMessage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.switchLang)
    View switchLang;
    @BindView(R.id.switchAccounts)
    View switchAccounts;

    PopupMenu popupAccounts, popupLang;

    private FollowersAdapter rcAdapter;

    boolean isLoading;

    private FollowersListPresenter followersPresenter;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_followers_list;
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, FollowersActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }


    @Override
    public void setTitle(String username) {

    }

    @Override
    public void showFollowersList(List<User> users) {

    }

    @Override
    public void openFollowerDetailsUI(User follower) {

    }

    @Override
    public void showIndicator() {

    }

    @Override
    public void hideIndicator() {

    }

    @Override
    public void showNoResultMessage() {

    }

    @Override
    public void showApiLimitMessage() {

    }

    @Override
    public void showToastMessage(String msg) {

    }

    @Override
    public void showNoNetworkMessage() {

    }



}
