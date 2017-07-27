package com.ramadan.twitterclient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;

/**
 * Created by Mahmoud Ramadan on 7/25/17.
 */

public class Utils {

    public static boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) MyApp.getInstance().getApplicationContext().
                        getSystemService(Context.CONNECTIVITY_SERVICE);

      return   cm.getActiveNetworkInfo()!=null
              && cm.getActiveNetworkInfo().isConnected()&&
              cm.getActiveNetworkInfo().isAvailable();

    }



}
