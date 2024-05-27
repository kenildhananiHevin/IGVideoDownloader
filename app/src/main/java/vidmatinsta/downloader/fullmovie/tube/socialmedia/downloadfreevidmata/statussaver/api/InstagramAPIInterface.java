package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.InstagramStory;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryFullDetail;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryModel;

public interface InstagramAPIInterface {
    @GET
    Observable<JsonObject> callResult(
            @Url String url,
            @Header("Cookie") String cookie,
            @Header("User-Agent") String userAgent
    );
    @GET
    Observable<StoryModel> getStoriesApi(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @GET
    Observable<StoryFullDetail> getFullDetailInfoApi(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);
}
