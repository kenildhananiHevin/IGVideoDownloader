package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StoryReelFeed implements Serializable {
    @SerializedName("id")
    private long id;
    @SerializedName("items")
    private ArrayList<InstagramStory> items;


    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }
    public ArrayList<InstagramStory> getItems() {
        return this.items;
    }

}
