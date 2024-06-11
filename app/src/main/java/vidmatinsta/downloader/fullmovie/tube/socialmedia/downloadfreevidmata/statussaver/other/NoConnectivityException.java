package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No Internet Connection";
    }
}
