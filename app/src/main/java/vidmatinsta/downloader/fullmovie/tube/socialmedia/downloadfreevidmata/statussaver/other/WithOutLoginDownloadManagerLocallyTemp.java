package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.WithOutLoginListDownloadManagerTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.WithOutLoginListDownloadTemp;

public class WithOutLoginDownloadManagerLocallyTemp {
    DownloadManager downloadManager;
    private Context context;
    public List<WithOutLoginListDownloadTemp> withOutLoginListDownloadTemps = new ArrayList();
    public List<WithOutLoginListDownloadManagerTemp> withOutLoginListDownloadManagerTemps = new ArrayList();

    public WithOutLoginDownloadManagerLocallyTemp(Context context) {
        this.downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.context = context;
    }


    public void downloadVideo(String str, String str2, int i, int i2, AlertDialog alertDialog, String edtpaste, WithOutLoginDownloadCompleteTemp temp, boolean isLast) {
        this.withOutLoginListDownloadManagerTemps.add(new WithOutLoginListDownloadManagerTemp(str, str2, i, i2));
        WithOutLoginCheckAndStart(temp, alertDialog, edtpaste,isLast);
    }

    public void WithOutLoginCheckAndStart(WithOutLoginDownloadCompleteTemp temp, AlertDialog alertDialog, String edtpaste, boolean isLast) {
        if (this.withOutLoginListDownloadManagerTemps.size() == 0) {
            return;
        }
        WithOutLoginRunDownload(this.withOutLoginListDownloadManagerTemps.get(0).getUrl(), this.withOutLoginListDownloadManagerTemps.get(0).getType(), this.withOutLoginListDownloadManagerTemps.get(0).getIndex(), this.withOutLoginListDownloadManagerTemps.get(0).getCount(), temp, alertDialog, edtpaste,isLast);
        this.withOutLoginListDownloadManagerTemps.remove(0);
    }

    public void WithOutLoginRunDownload(String str, String str2, int i, int i2, WithOutLoginDownloadCompleteTemp temp, AlertDialog alertDialog, String edtpaste, boolean isLast) {
        long currentTimeMillis = System.currentTimeMillis();
        if (((Activity) this.context) == null) {
            return;
        }
        if (str2.endsWith(".mp4")) {
            download(str, currentTimeMillis + ".mp4", alertDialog, edtpaste,isLast);
        } else {
            download(str, currentTimeMillis + ".jpeg", alertDialog, edtpaste,isLast);
        }
    }

    @SuppressLint("Range")
    private void download(final String str, final String str2, AlertDialog alertDialog, String edtpaste, boolean isLast) {
        new InstagramDownload(context, str2, alertDialog, edtpaste, str2,isLast).execute(str);
    }

}
