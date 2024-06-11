package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WithOutLoginAPIClientTemp {
    private static String base = "https://optekasoft.net";

    public static Retrofit getClientLocalAuth(Context context) {
        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        return new Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient.Builder().addInterceptor(new NetworkConnectionInterceptor(context)).retryOnConnectionFailure(true).callTimeout(2L, TimeUnit.MINUTES).connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).cache(new Cache(context.getCacheDir(), 5242880)).build()).build();
    }
}
