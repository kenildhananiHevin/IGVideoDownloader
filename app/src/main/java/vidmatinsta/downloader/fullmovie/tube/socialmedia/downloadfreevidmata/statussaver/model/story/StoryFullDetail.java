package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StoryFullDetail implements Serializable {

    @SerializedName("reels_media")
    private ArrayList<StoryReelFeed> reel_feed;

    public ArrayList<StoryReelFeed> getReel_feed() {
        return this.reel_feed;
    }


}