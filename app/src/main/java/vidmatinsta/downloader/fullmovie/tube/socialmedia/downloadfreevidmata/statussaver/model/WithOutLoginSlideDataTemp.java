package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model;

import androidx.annotation.Keep;

@Keep
public class WithOutLoginSlideDataTemp {
    private String display_resources;
    private boolean is_video;
    private String video_url;

    public WithOutLoginSlideDataTemp(boolean z, String str, String str2) {
        this.is_video = z;
        this.video_url = str;
        this.display_resources = str2;
    }

    public boolean isIs_video() {
        return this.is_video;
    }

    public void setIs_video(boolean z) {
        this.is_video = z;
    }

    public String getVideo_url() {
        return this.video_url;
    }

    public void setVideo_url(String str) {
        this.video_url = str;
    }

    public String getDisplay_resources() {
        return this.display_resources;
    }

    public void setDisplay_resources(String str) {
        this.display_resources = str;
    }
}
