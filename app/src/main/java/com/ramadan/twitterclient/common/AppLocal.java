package com.ramadan.twitterclient.common;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;


import java.util.Locale;



/**
 * Created by Mahmoud Ramadan on 7/29/17.
 */

public class AppLocal {

    private static final String PREF_LOCAL = "PREF_LOCAL";
    public static final String PREF_LOCAL_ENGLISH = "en";
    public static final String PREF_LOCAL_ARABIC = "ar";

    private static String mAppLocal;

    public static String getAppLocal() {
        if (mAppLocal == null)
            mAppLocal = getAppLocalFromPref();

        return mAppLocal;
    }

    public static void setAppLocal(Context context, String appLocal) {
        Locale locale;

        mAppLocal = appLocal;
        setAppLocalToPref(appLocal);

        if (appLocal.equals(AppLocal.PREF_LOCAL_ARABIC)) {
            locale = new Locale(AppLocal.PREF_LOCAL_ARABIC);
        } else {
            locale = new Locale(AppLocal.PREF_LOCAL_ENGLISH);
        }

        Locale.setDefault(locale);

        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale);
        else
            config.locale = locale;

        context.getApplicationContext().getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    private static void setAppLocalToPref(String appLocal) {
        MyApp.getInstance().getDefaultSharedPreferences().edit().putString(PREF_LOCAL, appLocal).apply();
    }

    private static String getAppLocalFromPref() {
        String local = MyApp.getInstance().getDefaultSharedPreferences().getString(PREF_LOCAL, PREF_LOCAL_ENGLISH);
        if (local.equals(PREF_LOCAL_ARABIC))
            return AppLocal.PREF_LOCAL_ARABIC;
        else
            return AppLocal.PREF_LOCAL_ENGLISH;
    }
}