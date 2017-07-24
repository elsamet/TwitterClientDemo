package com.ramadan.twitterclient.data.file;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.ramadan.twitterclient.AppConst;

//this class manages saving and getting of logged in  user name

public class Prefs {
    public static Prefs INSTANCE;

    public Context context;
    public static SharedPreferences sharedPreferences;


    private Prefs(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AppConst.PREFS_DB, 0);
    }

    public synchronized static Prefs getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Prefs(context);
        }
        return INSTANCE;
    }


    public  void setStringValue(String key ,String val) {
        sharedPreferences.edit().
                putString(key, val).
                commit();
    }

    public  String getStringValue(String key){
        return sharedPreferences.getString(key,"");
    }

}