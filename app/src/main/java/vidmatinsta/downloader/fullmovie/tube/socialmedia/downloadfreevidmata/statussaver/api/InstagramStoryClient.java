package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import android.app.Activity;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstagramStoryClient {
    private static InstagramStoryClient instagramRestClient = new InstagramStoryClient();
    private static Retrofit retrofit;
    private static Activity mactivity;


    private InstagramStoryClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(2L, TimeUnit.MINUTES)
                .connectTimeout(2L, TimeUnit.MINUTES)
                .writeTimeout(2L, TimeUnit.MINUTES)
                .addInterceptor(chain -> responseFun(chain))
                .addInterceptor(httpLoggingInterceptor);

        OkHttpClient client = httpClientBuilder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.instagram.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
//        if (afterLogin){
//              String base = "https://optekasoft.net";
//            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//            Retrofit build = new Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient.Builder().addInterceptor(new NetworkConnectionInterceptor(mactivity)).addInterceptor(new Interceptor() { // from class: instagram.video.downloader.story.saver.insapp.Apis.APIClient.1
//                @Override // okhttp3.Interceptor
//                public Response intercept(Chain chain) throws IOException {
//                    String compact = Jwts.builder().claim("platform", "android").claim("time", Long.valueOf(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS256, InstagramStoryClient.getEncryptedKey().getBytes()).compact();
//                    Log.e("scdacc", "token = " + compact);
//                    return chain.proceed(chain.request().newBuilder().addHeader("Authorization", "Bearer " + compact).build());
//                }
//            }).retryOnConnectionFailure(true).callTimeout(2L, TimeUnit.MINUTES).connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).cache(new Cache(mactivity.getCacheDir(), 5242880)).build()).build();
//            retrofit = build;
//        }
    }


    private Response responseFun(Interceptor.Chain chain) throws IOException {
        Response response = null;
        try {
            response = chain.proceed(chain.request());
            if (response.code() == 200) {
                try {
                    String jsonObject = new JSONObject(response.body().string()).toString();
                    printMsg(jsonObject);
                    return response.newBuilder()
                            .body(ResponseBody.create(response.body().contentType(), jsonObject))
                            .build();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void printMsg(String str) {
        int length = str.length() / 4050;
        for (int i = 0; i <= length; i++) {
            int nextLength = (i + 1) * 4050;
            if (nextLength >= str.length()) {
                Log.d("Response::", str.substring(i * 4050));
            } else {
                Log.d("Response::", str.substring(i * 4050, nextLength));
            }
        }
    }

    public InstagramStoryAPIInterface getService() {
        return retrofit.create(InstagramStoryAPIInterface.class);
    }



    public static InstagramStoryClient getInstance(Activity activity) {
        mactivity = activity;
        return instagramRestClient;
    }
}
