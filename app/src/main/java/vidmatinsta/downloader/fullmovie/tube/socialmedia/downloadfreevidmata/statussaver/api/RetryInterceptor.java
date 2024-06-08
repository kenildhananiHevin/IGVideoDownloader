package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
    private static final int MAX_RETRIES = 3;
    private static final int INITIAL_BACKOFF = 1000; // 1 second

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int tryCount = 0;
        int backoff = INITIAL_BACKOFF;

        while (!response.isSuccessful() && tryCount < MAX_RETRIES) {
            tryCount++;
            try {
                Thread.sleep(backoff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            backoff *= 2; // Exponential backoff
            response = chain.proceed(request);
        }
        return response;
    }
}