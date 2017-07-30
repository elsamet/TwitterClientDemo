package com.ramadan.twitterclient.data.network;

import android.content.Context;

import com.ramadan.twitterclient.common.MyApp;
import com.ramadan.twitterclient.common.Utils;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Ramadan on 7/25/17.
 */

public class MYTwitterAPIClient extends TwitterApiClient {

    public MYTwitterAPIClient(Context context, TwitterSession session) {
        super(session, MYTwitterAPIClient.createCachedClient(context));
    }

    public MyTwitterService getTwitterService() {
        return getService(MyTwitterService.class);
    }


    private static OkHttpClient createCachedClient(final Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "cache_file");

        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);//20mb
        return new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return getCacheInterceptor(context,chain);
                    }
                })
                .build();
    }

    private static Response getCacheInterceptor(Context context, Interceptor.Chain chain) {
        Request originalRequest = chain.request();
        String cacheHeaderValue = Utils.isOnline(context)
                ? "public, max-age=2419200"
                : "public, only-if-cached, max-stale=2419200";
        Request request = originalRequest.newBuilder().build();
        Response response;
        try {
            response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Interceptor to cache data and maintain it for four weeks.
     *
     * If the device is offline, stale (at most four weeks old)
     * response is fetched from the cache.
     */
    private static class OfflineResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (Utils.isOnline(MyApp.getInstance().getApplicationContext())) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }


    /**
     * Interceptor to cache data and maintain it for a minute.
     *
     * If the same network request is sent within a minute,
     * the response is retrieved from cache.
     */
    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        }
    }

}
