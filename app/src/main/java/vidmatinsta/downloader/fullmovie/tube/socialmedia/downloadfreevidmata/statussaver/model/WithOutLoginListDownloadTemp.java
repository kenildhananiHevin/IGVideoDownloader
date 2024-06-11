package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model;

import androidx.annotation.Keep;

@Keep
public class WithOutLoginListDownloadTemp {
    private String display_resources;
    private boolean is_slide;
    private boolean is_video;
    private int last_progress;
    private String link;
    private int progress;
    private String storage_path;
    private int type;

    public void setStorage_path(String str) {
        this.storage_path = str;
    }

    public String getStorage_path() {
        return this.storage_path;
    }

    public int getLast_progress() {
        return this.last_progress;
    }

    public int getType() {
        return this.type;
    }

    public void setLast_progress(int i) {
        this.last_progress = i;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int i) {
        this.progress = i;
    }

    public WithOutLoginListDownloadTemp(String str, String str2, boolean z, boolean z2) {
        this.progress = 0;
        this.type = 2;
        this.storage_path = "";
        this.link = str;
        this.display_resources = str2;
        this.is_slide = z;
        this.is_video = z2;
    }

    public WithOutLoginListDownloadTemp(String str, String str2, boolean z, boolean z2, int i) {
        this.progress = 0;
        this.storage_path = "";
        this.link = str;
        this.display_resources = str2;
        this.is_slide = z;
        this.is_video = z2;
        this.type = i;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public String getDisplay_resources() {
        return this.display_resources;
    }

    public boolean getIs_slide() {
        return this.is_slide;
    }

    public boolean getIs_video() {
        return this.is_video;
    }
}
