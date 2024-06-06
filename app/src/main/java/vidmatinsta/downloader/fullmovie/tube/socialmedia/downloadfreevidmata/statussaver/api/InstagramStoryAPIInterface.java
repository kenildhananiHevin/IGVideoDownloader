package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.InstagramResponseModelTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.Root;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryModel;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show.RootStory;

public interface InstagramStoryAPIInterface {
    @GET
    Observable<StoryModel> getStoriesApi(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @GET
    Observable<Root> getUserDetail(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3, @Query("max_id") String str4);

    @GET
    Observable<RootStory> getUserStory(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @GET
    Observable<JsonObject> getUsersExplore(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @GET
    Observable<JsonObject> getUserTimeExplore(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3, @Query("max_id") String str4);


}
