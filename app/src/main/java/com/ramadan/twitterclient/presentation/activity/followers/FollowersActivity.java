package com.ramadan.twitterclient.presentation.activity.followers;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.common.AppLocal;
import com.ramadan.twitterclient.common.Utils;
import com.ramadan.twitterclient.data.FollowerInfo;
import com.ramadan.twitterclient.data.UserManager;
import com.ramadan.twitterclient.presentation.activity.BaseActivity;
import com.ramadan.twitterclient.presentation.activity.follower_info.FollowerInfoActivity;
import com.ramadan.twitterclient.presentation.activity.login.LoginActivity;
import com.ramadan.twitterclient.presentation.adapter.FollowersAdapter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowersActivity extends BaseActivity implements FollowersView,PopupMenu.OnMenuItemClickListener {

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
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        followersPresenter = new FollowersListPresenterImpl(UserManager.getInstance(this), this);
        followersPresenter.loadFollowersList(true);

    }

    //show language options dropdown menu
    @OnClick(R.id.switchLang)
    void onSwitchLang() {
        popupLang = new PopupMenu(this, switchLang, Gravity.END);
        popupLang.getMenu().add(getString(R.string.lang_english));
        popupLang.getMenu().add(getString(R.string.lang_arabic));
        popupLang.setOnMenuItemClickListener(this);
        popupLang.show();
    }

    //show accounts options dropdown menu for switching account and/or adding new one
    @OnClick(R.id.switchAccounts)
    void onSwitchAccounts() {
        popupAccounts = new PopupMenu(this, switchAccounts, Gravity.START);
        popupAccounts.getMenu().add(getString(R.string.acc_add));
        for (TwitterSession s : TwitterCore.getInstance().getSessionManager().getSessionMap().values()) {
            popupAccounts.getMenu().add(Utils.getUsernameScreenDisplay(s.getUserName()));
        }
        popupAccounts.setOnMenuItemClickListener(this);
        popupAccounts.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getTitle().equals(getString(R.string.lang_english)) && !AppLocal.getAppLocal().equals(AppLocal.PREF_LOCAL_ENGLISH)) {
            AppLocal.setAppLocal(this, AppLocal.PREF_LOCAL_ENGLISH);
            recreate();
            return true;
        } else if (item.getTitle().equals(getString(R.string.lang_arabic)) && !AppLocal.getAppLocal().equals(AppLocal.PREF_LOCAL_ARABIC)) {
            AppLocal.setAppLocal(this, AppLocal.PREF_LOCAL_ARABIC);
            recreate();
            return true;
        } else if (item.getTitle().equals(getString(R.string.acc_add))) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getTitle().toString().startsWith("@")) {
            followersPresenter.setActiveUser(item.getTitle().toString().replace("@", ""));
            return true;
        }

        return false;
    }

    @Override
    public void setTitle(String username) {
        toolbarTitle.setText(getString(R.string.followers, Utils.getUsernameScreenDisplay(username)));
    }

    @Override
    public void showFollowersList(List<User> users) {
        rcAdapter.replaceData(users);
    }

    @Override
    public void openFollowerDetailsUI(User follower) {
        Parcelable followerInfo = Parcels.wrap(new FollowerInfo(follower.id, follower.name, follower.screenName, follower.profileImageUrl, follower.profileBackgroundImageUrl));
        FollowerInfoActivity.start(this, followerInfo);
    }

    @Override
    public void showIndicator() {
        isLoading = true;
        recyclerView.setVisibility(android.view.View.GONE);
        errorMessage.setVisibility(android.view.View.GONE);
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideIndicator() {
        isLoading = false;
        progressBar.setVisibility(android.view.View.GONE);
        errorMessage.setVisibility(android.view.View.GONE);
        recyclerView.setVisibility(android.view.View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNoResultMessage() {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.msg_no_followers));
    }

    @Override
    public void showApiLimitMessage() {
        Toast.makeText(this, getString(R.string.msg_api_limit_exceeded), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNetworkMessage() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_network), Snackbar.LENGTH_LONG).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        followersPresenter.stop();
    }

    FollowersAdapter.FollowerItemClickListener followerItemClickListener = new FollowersAdapter.FollowerItemClickListener() {
        @Override
        public void onFollowerClick(User follower) {
            followersPresenter.openFollowerDetails(follower);
        }
    };




    void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        progressBar.getIndeterminateDrawable().
                setColorFilter(getApplication().
                        getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        rcAdapter = new FollowersAdapter(new ArrayList<User>(0), followerItemClickListener);
        recyclerView.setAdapter(rcAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followersPresenter.loadFollowersList(true);
            }
        });


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, 0) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount) {

                isLoading = true;
                followersPresenter.loadFollowersList(false);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }


}
