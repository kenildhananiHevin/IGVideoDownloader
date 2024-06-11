package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;


import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass.IgVideoPathDirectory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.VideoDownloadActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class InstagramDownload extends AsyncTask<String, Integer, String> {
    private final Context mContext;
    private final String mediaName;
    private final AlertDialog alertDialog;
    private final String edtPaste;
    private final  String nameIns;

    public InstagramDownload(Context context, String mediaName, AlertDialog alertDialog, String edtPaste, String nameIns) {
        this.mContext = context;
        this.mediaName = mediaName;
        this.alertDialog = alertDialog;
        this.edtPaste = edtPaste;
        this.nameIns = nameIns;
    }

    @Override
    protected String doInBackground(String... strArr) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            File directory = new File(IgVideoPathDirectory.getAbsolutePath());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String name = IgVideoPathDirectory + "/" + mediaName;
            fileOutputStream = new FileOutputStream(name);
            int count;
            try {
                URL url = new URL(strArr[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lenghtOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(name);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return name;
        } catch (Exception e) {
            Log.d("TAG", "linkss4: " + e.getMessage());
            return e.toString();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String str) {
        alertDialog.dismiss();
        Log.d("TAG", "linkss2: "+edtPaste);
        Log.d("TAG", "linkss3: "+str);
        mContext.startActivity(new Intent(mContext, VideoDownloadActivity.class)
                .putExtra("url",str)
                .putExtra("link",edtPaste)
                .putExtra("name",nameIns));
    }

}
