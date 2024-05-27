    package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StoryImageVersion implements Serializable {
    @SerializedName("candidates")
    private ArrayList<StoryCandidates> candidates;

    public ArrayList<StoryCandidates> getCandidates() {
        return this.candidates;
    }

}
