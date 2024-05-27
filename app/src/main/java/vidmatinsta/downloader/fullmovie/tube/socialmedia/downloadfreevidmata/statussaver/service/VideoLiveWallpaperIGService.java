package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.service;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class VideoLiveWallpaperIGService extends WallpaperService {

    private static final String VIDEO_PARAMS_CONTROL_ACTION = "moe.cyunrei.livewallpaper";
    private static final String KEY_ACTION = "music";
    private static final boolean ACTION_MUSIC_UNMUTE = false;
    private static final boolean ACTION_MUSIC_MUTE = true;

    @Override
    public Engine onCreateEngine() {
        return new VideoEngine();
    }

    private class VideoEngine extends Engine {

        private MediaPlayer mediaPlayer;
        private BroadcastReceiver broadcastReceiver;
        private String videoFilePath;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            try {
                FileInputStream fis = openFileInput("video_live_wallpaper_file_path");
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                StringBuilder stringBuilder = new StringBuilder();
                int character;
                while ((character = inputStreamReader.read()) != -1) {
                    stringBuilder.append((char) character);
                }
                videoFilePath = stringBuilder.toString();
                inputStreamReader.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            IntentFilter intentFilter = new IntentFilter(VIDEO_PARAMS_CONTROL_ACTION);
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean action = intent.getBooleanExtra(KEY_ACTION, false);
                    if (action) {
                        mediaPlayer.setVolume(0f, 0f);
                    } else {
                        mediaPlayer.setVolume(1.0f, 1.0f);
                    }
                }
            };
            registerReceiver(broadcastReceiver, intentFilter);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setSurface(holder.getSurface());
                mediaPlayer.setDataSource(videoFilePath);
                mediaPlayer.setLooping(true);
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mediaPlayer.prepare();
                mediaPlayer.start();
                File file = new File(getFilesDir(), "unmute");
                if (file.exists()) {
                    mediaPlayer.setVolume(1.0f, 1.0f);
                } else {
                    mediaPlayer.setVolume(0f, 0f);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                mediaPlayer.start();
            } else {
                mediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (broadcastReceiver != null) {
                unregisterReceiver(broadcastReceiver);
            }
        }
    }

    public static void setToIgWallPaper(Context context) {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, VideoLiveWallpaperIGService.class));
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LIVE_WALLPAPER)) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Your device does not support video wallpapers.", Toast.LENGTH_SHORT).show();
        }
        try {
            WallpaperManager.getInstance(context).clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
