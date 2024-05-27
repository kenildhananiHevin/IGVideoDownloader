package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoryUser implements Serializable {
    @SerializedName("full_name")
    private String full_name;
    int isFav;
    @SerializedName("pk")
    private long pk;
    @SerializedName("profile_pic_url")
    private String profile_pic_url;




    public long getPk() {
        return this.pk;
    }



    public String getFull_name() {
        return this.full_name;
    }



    public String getProfile_pic_url() {
        return this.profile_pic_url;
    }

}
