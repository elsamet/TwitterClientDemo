package com.ramadan.twitterclient.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mahmoud Ramadan on 7/25/17.
 */

public class Utils {

    public static void setText(TextView textView, String prefix, String text, boolean hideIfEmpty) {
        textView.setVisibility(View.VISIBLE);

        if (text != null) {
            if (text.isEmpty() && hideIfEmpty)
                textView.setVisibility(View.GONE);
            else
                textView.setText(prefix == null ? text : prefix + text);
        } else
            textView.setText("");
    }

    public static String getUsernameScreenDisplay(String username) {
        return "@" + username;
    }

    public static boolean isOnline(Context context) {
        boolean connected;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return connected;
    }


}
