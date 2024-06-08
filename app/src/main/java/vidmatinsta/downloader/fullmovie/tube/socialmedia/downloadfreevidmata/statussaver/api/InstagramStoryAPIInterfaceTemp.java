package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.InstagramResponseModelTemp;

public interface InstagramStoryAPIInterfaceTemp {
    @GET
    Call<InstagramResponseModelTemp> callResult1(
            @Url String url,
            @Header("Cookie") String cookie,
            @Header("User-Agent") String userAgent
    );
}
