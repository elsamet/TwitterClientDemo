package com.ramadan.twitterclient.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ramadan.twitterclient.R;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud Ramadan on 7/29/17.
 */

public class FollowerTweetsAdapter extends RecyclerView.Adapter<FollowerTweetsAdapter.TweetViewHolder> {

    private List<Tweet> itemList;

    public FollowerTweetsAdapter(List<Tweet> itemList) {
        this.itemList = itemList;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.view.View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false);
        return new TweetViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.onBindView(this, position);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void replaceData(List<Tweet> users) {
        setList(users);
        notifyDataSetChanged();
    }

    private void setList(List<Tweet> users) {
        itemList = users;
    }

    static class TweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tweetContainer)
        ViewGroup tweetContainer;

        TweetViewHolder(android.view.View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBindView(FollowerTweetsAdapter adapter, int position) {
            Tweet tweet = adapter.itemList.get(position);

            tweetContainer.addView(new TweetView(itemView.getContext(), tweet));
        }
    }
}
