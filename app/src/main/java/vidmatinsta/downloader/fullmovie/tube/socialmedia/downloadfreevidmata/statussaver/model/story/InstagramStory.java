package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InstagramStory implements Serializable {
    @SerializedName("can_reply")
    private boolean can_reply;
    @SerializedName("can_reshare")
    private boolean can_reshare;
    @SerializedName("caption_is_edited")
    private boolean caption_is_edited;
    @SerializedName("caption_position")
    private int caption_position;
    @SerializedName("client_cache_key")
    private String client_cache_key;
    @SerializedName("code")
    private String code;
    @SerializedName("device_timestamp")
    private long device_timestamp;
    @SerializedName("expiring_at")
    private long expiring_at;
    @SerializedName("filter_type")
    private int filter_type;
    @SerializedName("has_audio")
    private boolean has_audio;
    @SerializedName("id")
    private String id;
    @SerializedName("image_versions2")
    private StoryImageVersion image_versions2;
    @SerializedName("is_reel_media")
    private boolean is_reel_media;
    @SerializedName("media_type")
    private int media_type;
    @SerializedName("organic_tracking_token")
    private String organic_tracking_token;
    @SerializedName("original_height")
    private int original_height;
    @SerializedName("original_width")
    private int original_width;
    @SerializedName("photo_of_you")
    private boolean photo_of_you;
    @SerializedName("pk")
    private long pk;
    @SerializedName("taken_at")
    private long taken_at;
    @SerializedName("video_duration")
    private double video_duration;
    @SerializedName("video_versions")
    private ArrayList<StoryVideoVersion> video_versions;

    String pack;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public int getMedia_type() {
        return this.media_type;
    }

    public StoryImageVersion getImage_versions2() {
        return this.image_versions2;
    }

    public ArrayList<StoryVideoVersion> getVideo_versions() {
        return this.video_versions;
    }

}
