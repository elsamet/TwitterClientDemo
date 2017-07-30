package com.ramadan.twitterclient.presentation.activity.followers;

import com.ramadan.twitterclient.data.UserManager;
import com.ramadan.twitterclient.data.network.ApiRepositoryImpl;
import com.ramadan.twitterclient.data.network.FollowersResponse;
import com.ramadan.twitterclient.domain.UCFetchFollowersList;
import com.twitter.sdk.android.core.TwitterApiException;
import com.twitter.sdk.android.core.models.User;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class FollowersListPresenterImpl implements FollowersListPresenter {
    private FollowersView mView;
    private UserManager userManager;

    private Call<FollowersResponse> followersResponseCall;
    private long nextCursor = -1;

    private List<User> followers = new ArrayList<>(0);

    UCFetchFollowersList useCase;

    public FollowersListPresenterImpl(UserManager userManager, FollowersView mView) {
        this.mView = mView;
        this.userManager = userManager;
        useCase = new UCFetchFollowersList(new ApiRepositoryImpl());
    }


    //sets new active user after the user change it from the dropdown menu
    @Override
    public void setActiveUser(String username) {
        userManager.setActiveUser(username);

        //reload followersList after active user has changed
        loadFollowersList(true);
    }

    /**
     * @param reload Pass in true to refresh the data in the.
     */
    @Override
    public void loadFollowersList(boolean reload) {

        this.mView.setTitle(userManager.getActiveTwitterSession().getUserName());

        //if the list will reload reset current cursor to default and clear the list
        if (reload) {
            nextCursor = -1;
            mView.showIndicator();
            followers.clear();
            followers.add(null);
        }

        if (nextCursor == 0) {
            mView.hideIndicator();
            return;
        }


      // useCase.setArgs(userManager.getActiveTwitterSession().getUserId(), nextCursor);
        
        
        
//        Subscription sub = useCase.execute()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<FollowersResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable exception) {
//
//                        mView.hideIndicator();
//
//                        if (exception instanceof TwitterApiException) {
//                            TwitterApiException apiException = (TwitterApiException) exception;
//                            if (apiException.getErrorCode() == 429)
//                                mView.showApiLimitMessage();
//                            else
//                                mView.showToastMessage(apiException.getErrorMessage());
//                        } else if (exception.getCause() instanceof UnknownHostException) {
//                            mView.showNoNetworkMessage();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(FollowersResponse response) {
//                        if (response != null) {
//                            if (response.users != null && response.users.size() > 0) {
//                                nextCursor = response.next_cursor;
//
//                                followers.addAll(followers.size() - 1, response.users);
//
//                                if (nextCursor == 0)
//                                    followers.remove(followers.size() - 1);
//
//                                mView.hideIndicator();
//                                mView.showFollowersList(followers);
//                            } else if (followers.size() == 0) {
//                                mView.hideIndicator();
//                                mView.showNoResultMessage();
//                            }
//                        }
//                    }
//                });

//        Subscription subscription =   Observable.create(new Observable.OnSubscribe<okhttp3.Response>() {
//            OkHttpClient client = new OkHttpClient();
//            @Override
//            public void call(Subscriber<? super okhttp3.Response> subscriber) {
//                try {
//                    okhttp3.Response response = client.newCall(new Request.Builder().url("your url").build()).execute();
//                    if (response.isSuccessful()) {
//                        if(!subscriber.isUnsubscribed()){
//                            subscriber.onNext(response);
//                        }
//                        subscriber.onCompleted();
//                    } else if (!response.isSuccessful() && !subscriber.isUnsubscribed()) {
//                        subscriber.onError(new Exception("error"));
//                    }
//                } catch (IOException e) {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onError(e);
//                    }
//                }
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<okhttp3.Response>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(okhttp3.Response response) {
//
//                    }
//                });


/////////////////////////////////////////////


        followersResponseCall = userManager.getActiveApiClient().getTwitterService().
                getFollowers(userManager.getActiveTwitterSession().getUserId(), nextCursor);


        followersResponseCall.enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(Call<FollowersResponse> call, Response<FollowersResponse> response) {
                if (response != null && response.body()!=null) {
                    if (response.body().users != null && response.body().users.size() > 0) {
                        nextCursor = response.body().next_cursor;

                        followers.addAll(followers.size() - 1, response.body().users);

                        if (nextCursor == 0)
                            followers.remove(followers.size() - 1);

                        mView.hideIndicator();
                        mView.showFollowersList(followers);
                    } else if (followers.size() == 0) {
                        mView.hideIndicator();
                        mView.showNoResultMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowersResponse> call, Throwable exception) {
                exception.printStackTrace();
                if (followersResponseCall.isCanceled()) return;

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

    /**
     * open {@link com.ramadan.twitterclient.presentation.activity.follower_info.FollowerInfoActivity} when user click on in in the list
     */
    @Override
    public void openFollowerDetails(User follower) {
        mView.openFollowerDetailsUI(follower);
    }

    @Override
    public void stop() {
        followersResponseCall.cancel();
    }
}
