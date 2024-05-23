package com.cashloan.myapplication.igvideodownloader.other;

import android.content.Context;

public class SharedPreferences {
    private static final String USER_PREFS = "com.cashloan.myapplication.igvideodownloader";
    public static String PREFERENCE = "AllDownloader";
    private static SharedPreferences instance;
    private Context ctx;
    private android.content.SharedPreferences sharedPreferences;



    private static android.content.SharedPreferences getPrefEdit(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }


    public static String sharedGetString(Context context, String key) {
        return getPrefEdit(context).getString(key, "");
    }



    public SharedPreferences(Context context) {
        this.ctx = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE, 0);
    }

    public static SharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferences(context);
        }
        return instance;
    }
}
