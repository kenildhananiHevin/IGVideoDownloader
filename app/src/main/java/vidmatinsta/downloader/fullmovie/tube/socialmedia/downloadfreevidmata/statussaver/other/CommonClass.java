package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommonClass {

    public static final int REQUEST_PERM_DELETE = 1222;
    public static final int REQUEST_PERM_DELETE_PHOTO = 1223;
    public static final int REQUEST_PERM_DELETE_AUDIO = 1224;

    private static Context context;

    public static File IgVideoPathDirectory = new File(Environment.getExternalStorageDirectory()
            + "/Download/Ig_Downloader/IgVideo");

    public static File IgAudioPathDirectory = new File(Environment.getExternalStorageDirectory()
            + "/Download/Ig_Downloader/IgAudio");

    public CommonClass(Context context2) {
        context = context2;
    }

    public static boolean isNullOrEmpty(String str) {
        return false;
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void OpenApp(Context context2, String str) {
        Intent launchIntentForPackage = context2.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context2.startActivity(launchIntentForPackage);
        } else {
            Toast.makeText(context2, context2.getResources().getString(R.string.app_not_available), Toast.LENGTH_LONG).show();
        }
    }

    public static void shareInstagram(Activity activity, String Path) {
        File file = new File(Path);
        Intent shareVideo = new Intent();
        shareVideo.setAction("android.intent.action.SEND");
        shareVideo.setPackage("com.instagram.android");
        if (Path.startsWith("content:")) {
            shareVideo.putExtra("android.intent.extra.STREAM", Uri.parse(Path));
        } else {
            shareVideo.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(activity, "com.cashloan.myapplication.igvideodownloader.provider", file));
        }
        shareVideo.setType("video/*");
        activity.startActivity(shareVideo);
    }

    public static void shareInstagramImage(Activity activity, String Path) {
        if (activity.getPackageManager().getLaunchIntentForPackage("com.instagram.android") != null) {
            Intent shareImage = new Intent();
            shareImage.setAction("android.intent.action.SEND");
            shareImage.setPackage("com.instagram.android");
            if (!Path.startsWith("content:")) {
                try {
                    shareImage.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), Path, "", "")));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                shareImage.putExtra("android.intent.extra.STREAM", Uri.parse(Path));
            }
            shareImage.setType("image/*");
            activity.startActivity(shareImage);
        }
    }

    public static void shareAllVideo(Activity activity, String path) {
        File f = new File(path);
        Uri uriPath = Uri.parse(f.getPath());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriPath);
        shareIntent.setType("video/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public static void shareAllImage(Activity activity, String str) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), str, "", (String) null)));
            intent.setType("image/*");
            activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R.string.share_image_via)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareAudio(Activity activity, String path) {
        Uri uri = Uri.parse(path);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        activity.startActivity(Intent.createChooser(share, "Share Sound File"));
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void deleteFiles(@Nullable List<File> files, final int requestCode, Activity activity, @Nullable Intent fillInIntent) {
        if (files == null || files.isEmpty()) {
            return;
        }
        List<Uri> uris = files.stream().map(file -> {
            long mediaID = getFilePath(file.getAbsolutePath(), activity);
            String fileType = getFile(file);
            if (fileType.equalsIgnoreCase("video")) {
                return ContentUris.withAppendedId(MediaStore.Video.Media.getContentUri("external"), mediaID);
            } else if (fileType.equalsIgnoreCase("image")) {
                return ContentUris.withAppendedId(MediaStore.Images.Media.getContentUri("external"), mediaID);
            } else {
                return ContentUris.withAppendedId(MediaStore.Audio.Media.getContentUri("external"), mediaID);
            }
        }).collect(Collectors.toList());

        PendingIntent pi = MediaStore.createDeleteRequest(activity.getContentResolver(), uris);
        try {
            IntentSender intentSender = pi.getIntentSender();
            activity.startIntentSenderForResult(intentSender, requestCode, fillInIntent, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private static String getFile(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getAbsolutePath());
        if (mimeType != null) {
            if (mimeType.startsWith("video")) {
                return "video";
            } else if (mimeType.startsWith("image")) {
                return "image";
            } else if (mimeType.startsWith("audio/mp3")) {
                return "audio/mp3";
            }
        }
        return "";
    }

    private static long getFilePath(String songPath, Context context) {
        long id = 0;
        String selection = MediaStore.Audio.Media.DATA + "=?";
        String[] selectionArgs = new String[]{songPath};
        String[] projection = new String[]{MediaStore.Audio.Media._ID};
        try (android.database.Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                id = cursor.getLong(idIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getStringSizeLengthFile(long size) {
        DecimalFormat df = new DecimalFormat("00.00");
        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;
        if (size < sizeMb)
            return df.format(size / sizeKb) + " KB";
        else if (size < sizeGb)
            return df.format(size / sizeMb) + " MB";
        else if (size < sizeTerra)
            return df.format(size / sizeGb) + " GB";
        return "";
    }


    public static String convertDurationToString(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1);

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static long getVideoDuration(String videoPath) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(videoPath);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return Long.parseLong(time);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            retriever.release();
        }
    }
}
