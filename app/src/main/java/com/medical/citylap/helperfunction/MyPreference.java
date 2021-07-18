package com.medical.citylap.helperfunction;

import android.content.Context;
import android.content.SharedPreferences;

import com.medical.citylap.modles.Data;

public class MyPreference {
    public static final String SHARED_USER_TOKEN = "tokenuser";


    static private Context appContext;

    public static void init(Context context) {
        appContext = context;
    }

    private static SharedPreferences getSharedPreference() {
        return appContext.getSharedPreferences("shared_file", Context.MODE_PRIVATE);
    }

    public static void saveUser(Data user) {
        getSharedPreference().edit().putString(SHARED_USER_TOKEN, user.getToken()).apply();
    }

    public static String getSharedString(String key) {

        return getSharedPreference().getString(key, "");
    }

}
