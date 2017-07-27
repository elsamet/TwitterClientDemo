package com.ramadan.twitterclient.presentation.adapter;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.Utils;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_NORMAL = 1000;
    public static final int VIEW_LOADING = 2000;

    private List<User> itemList;
    private FollowerItemClickListener followerItemClickListener;

    FollowersAdapter(List<User> itemList, FollowerItemClickListener followerItemClickListener) {
        this.itemList = itemList;
        this.followerItemClickListener = followerItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.view.View layoutView;
        if (viewType == VIEW_NORMAL) {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower, parent, false);
            return new FollowerViewHolder(layoutView, followerItemClickListener);
        } else {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(layoutView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FollowerViewHolder)
            ((FollowerViewHolder) holder).onBindView(this, position);
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_NORMAL : VIEW_LOADING;
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void replaceData(List<User> users) {
        setList(users);
        notifyDataSetChanged();
    }

    private void setList(List<User> users) {
        itemList = users;
    }

    static class FollowerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profileImage)
        ImageView profileImage;
        @BindView(R.id.profileFullName)
        TextView profileFullName;
        @BindView(R.id.profileHandle)
        TextView profileHandle;
        @BindView(R.id.profileBio)
        TextView profileBio;

        private FollowerItemClickListener followerItemClickListener;

        FollowerViewHolder(android.view.View itemView, FollowerItemClickListener followerItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.followerItemClickListener = followerItemClickListener;
        }

        void onBindView(FollowersAdapter adapter, int position) {
           final  User follower = adapter.itemList.get(position);

            Glide.with(itemView.getContext())
                    .load(follower.profileImageUrlHttps)
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(profileImage);


            profileFullName.setText(follower.name);
            profileHandle.setText(Utils.getUsernameScreenDisplay(follower.screenName));
            Utils.setText(profileBio, itemView.getContext().getString(R.string.profile_bio), follower.description, true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    followerItemClickListener.onFollowerClick(follower);
                }
            });

        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            progressBar.getIndeterminateDrawable().
                        setColorFilter(itemView.getContext().
                         getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }

    interface FollowerItemClickListener {
        void onFollowerClick(User follower);
    }
}

