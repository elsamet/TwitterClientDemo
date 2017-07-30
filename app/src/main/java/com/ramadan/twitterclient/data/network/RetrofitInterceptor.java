package com.ramadan.twitterclient.data.network;

import android.util.Base64;
import android.util.Log;

import com.ramadan.twitterclient.R;
import com.ramadan.twitterclient.common.AppConst;
import com.ramadan.twitterclient.common.MyApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Mahmoud Ramadan on 7/28/17.
 */

public class RetrofitInterceptor implements Interceptor {
    private String token = null;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (token == null) {
            ResponseBody body = chain.proceed(getToken()).body();

            try {
                JSONObject jsonObject = new JSONObject(body.string());
                token = "Bearer " + jsonObject.getString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(RetrofitInterceptor.class.getName(), "Error fetching token");
            }
        }

        request = request.newBuilder()
                .addHeader("Authorization", token)
                .build();

        return chain.proceed(request);
    }

    private Request getToken() {
        String bearerToken = MyApp.getInstance().getString(R.string.TWITTER_KEY) +
                ":" + MyApp.getInstance().getString(R.string.TWITTER_KEY);

        String base64BearerToken = "Basic " + Base64.encodeToString(bearerToken.getBytes(), Base64.NO_WRAP);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), "grant_type=client_credentials");

        return new Request.Builder()
                .url(AppConst.AUTH_END_POINT)
                .post(requestBody)
                .header("Host" ,"api.twitter.com")
                .header("User-Agent", " My Twitter App v1.0.23")
                .header("Authorization", base64BearerToken)
                .header("Content-type", "application/x-www-form-urlencoded;charset=UTF-8")
                .header("Content-Length", "29")
                .header("Accept-Encoding", "gzip")
                .header("grant_type", "client_credentials")

                .build();
    }
}
