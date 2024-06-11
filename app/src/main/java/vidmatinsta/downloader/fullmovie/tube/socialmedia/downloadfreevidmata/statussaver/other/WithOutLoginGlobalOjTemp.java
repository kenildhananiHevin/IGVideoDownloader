package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.content.Context;

public class WithOutLoginGlobalOjTemp {
    public static Context context;
    public static WithOutLoginMainApiTemp mainApi;
    public static WithOutLoginDownloadManagerLocallyTemp withOutLoginDownloadManagerLocallyTemp;


    public static void CreateGlobalOj(Context context2) {
        context = context2;
        if (withOutLoginDownloadManagerLocallyTemp == null) {
            withOutLoginDownloadManagerLocallyTemp = new WithOutLoginDownloadManagerLocallyTemp(context2);
        }

        if (mainApi == null) {
            mainApi = new WithOutLoginMainApiTemp(context2);
        }
    }
}
