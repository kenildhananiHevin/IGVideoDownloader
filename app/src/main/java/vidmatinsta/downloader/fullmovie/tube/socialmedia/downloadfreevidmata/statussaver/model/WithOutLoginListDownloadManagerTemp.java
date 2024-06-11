package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model;

import androidx.annotation.Keep;

@Keep
public class WithOutLoginListDownloadManagerTemp {
    private int count;
    private int index;
    private String type;
    private String url;

    public WithOutLoginListDownloadManagerTemp(String str, String str2, int i, int i2) {
        this.url = str;
        this.type = str2;
        this.index = i;
        this.count = i2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }
}
