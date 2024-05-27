package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StoryTray implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("user")
    private StoryUser user;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public StoryUser getUser() {
        return this.user;
    }

}
