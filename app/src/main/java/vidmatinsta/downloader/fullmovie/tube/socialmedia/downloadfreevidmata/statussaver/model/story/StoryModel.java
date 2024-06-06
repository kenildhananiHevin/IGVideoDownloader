package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Keep
public class StoryModel implements Serializable {
    @SerializedName("tray")
    private ArrayList<StoryTray> tray;

    public ArrayList<StoryTray> getTray() {
        return this.tray;
    }

}
