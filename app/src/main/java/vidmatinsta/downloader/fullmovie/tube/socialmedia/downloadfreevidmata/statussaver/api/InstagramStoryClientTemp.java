package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstagramStoryClientTemp {
    private static Retrofit retrofit = null;
    private static final Map<String, List<Cookie>> cookieStore = new HashMap<>();

    public static Retrofit getClient(String cookie) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("User-Agent", "Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)")
                                .header("Cookie", cookie);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .followRedirects(false)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                })
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.instagram.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
