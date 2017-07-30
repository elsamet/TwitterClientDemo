package com.ramadan.twitterclient.data.network;

import com.ramadan.twitterclient.common.AppConst;
import com.ramadan.twitterclient.common.MyApp;
import com.ramadan.twitterclient.data.UserManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static android.R.string.ok;

/**
 * Created by Mahmoud Ramadan on 7/28/17.
 */

public class ApiRepositoryImpl implements ApiRepository {
    @Override
    public Observable<FollowersResponse> fetchFollowers(long id, long cursor) {
//         return  UserManager.getInstance(MyApp.getInstance())
//                 .getActiveApiClient()
//                 .getTwitterService()
//                 .getFollowers(id,cursor);
return null;

//      OkHttpClient client = new OkHttpClient.Builder()
//                                            .addInterceptor(new RetrofitInterceptor())
//                                             .build();
//
//
//        return new Retrofit.Builder()
//                .baseUrl(AppConst.BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build()
//                .create(MyTwitterService.class)
//                .getFollowers(id, cursor);
    }
}
