package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.content.Context;

public class SharedPref {
    public static String COOKIES = "mainCookies";
    public static String CSRF = "mainCsrf";
    public static final String SESSIONID = "mainSessionId";
    public static final String USERID = "mainUserId";
    public static final String ISINSTALOGIN = "mainIsInstagramLogin";
    private static final String USER_PREFS = "com.cashloan.myapplication.igvideodownloader";
    public static String PREFERENCE = "AllDownloader";
    private static SharedPref instance;
    private Context ctx;
    private android.content.SharedPreferences sharedPreferences;



    private static android.content.SharedPreferences getPrefEdit(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }


    public static String sharedGetString(Context context, String key) {
        return getPrefEdit(context).getString(key, "");
    }

    public static void mainSharedPutString(Context context, String key, String value) {
        getPrefEdit(context).edit().putString(key, value).apply();
    }

    public static void mainSharedPutBoolean(Context context, String key, boolean value) {
        getPrefEdit(context).edit().putBoolean(key, value).apply();
    }

    public static boolean mainSharedGetBoolean(Context context, String key) {
        return getPrefEdit(context).getBoolean(key, false);
    }


    public SharedPref(Context context) {
        this.ctx = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE, 0);
    }

    public static SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref(context);
        }
        return instance;
    }

}
