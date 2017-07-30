package com.ramadan.twitterclient.presentation.activity.follower_info;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.common.AppConst;
import com.ramadan.twitterclient.common.AppLocal;
import com.ramadan.twitterclient.common.Utils;
import com.ramadan.twitterclient.data.FollowerInfo;
import com.ramadan.twitterclient.data.UserManager;
import com.ramadan.twitterclient.presentation.activity.BaseActivity;
import com.ramadan.twitterclient.presentation.adapter.FollowerTweetsAdapter;
import com.twitter.sdk.android.core.models.Tweet;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ramadan.twitterclient.common.AppConst.EXTRA_FOLLOWER;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowerInfoActivity extends BaseActivity implements FollowerInfoView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.profileHandle)
    TextView profileHandle;
    @BindView(R.id.profileImage)
    ImageView profileImage;
    @BindView(R.id.profileBG)
    ImageView profileBG;

    @BindView(R.id.errorMessage)
    TextView errorMessage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private FollowerInfoPresenter followerDetailsPresenter;
    private FollowerTweetsAdapter followerTweetsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_follower_info;
    }

    public static void start(Context context, Parcelable followerInfo) {
        Intent intent = new Intent(context, FollowerInfoActivity.class);
        intent.putExtra(EXTRA_FOLLOWER, followerInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLocal.setAppLocal(getApplicationContext(), AppLocal.getAppLocal());


        initUI();

        FollowerInfo followerInfo = Parcels.unwrap(getIntent().getParcelableExtra(AppConst.EXTRA_FOLLOWER));

        followerDetailsPresenter = new FollowerInfoPresenterImpl(followerInfo, UserManager.getInstance(this), this);
        followerDetailsPresenter.start();
    }

    void initUI() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("");
        }

        progressBar.getIndeterminateDrawable().
                setColorFilter(this.
                        getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        followerTweetsAdapter = new FollowerTweetsAdapter(new ArrayList<Tweet>(0));
        recyclerView.setAdapter(followerTweetsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        followerDetailsPresenter.stop();
    }

    @Override
    public void setFollowerData(FollowerInfo followerInfo) {
        toolbar.setTitle(followerInfo.getName());
        profileHandle.setText(Utils.getUsernameScreenDisplay(followerInfo.getUsername()));


        Glide.with(this)
                .load(followerInfo.getProfileImageUrl())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profileImage);

        Glide.with(this)
                .load(followerInfo.getBackgroundImageUrl())
                .placeholder(R.drawable.bg_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profileBG);



    }

    @Override
    public void showTweetsList(List<Tweet> tweets) {
        followerTweetsAdapter.replaceData(tweets);
        followerTweetsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIndicator() {
        recyclerView.setVisibility(android.view.View.GONE);
        errorMessage.setVisibility(android.view.View.GONE);
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideIndicator() {
        progressBar.setVisibility(android.view.View.GONE);
        errorMessage.setVisibility(android.view.View.GONE);
        recyclerView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showNoResultMessage() {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.msg_no_tweets));
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
}
