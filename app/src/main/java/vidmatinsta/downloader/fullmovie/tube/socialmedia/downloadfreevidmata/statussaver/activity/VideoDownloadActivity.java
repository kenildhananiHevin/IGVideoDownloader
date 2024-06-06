package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileOutputStream;
import java.io.IOException;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.AudioExtractor;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DebouncedOnClickListener;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DocumentUtils;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.service.VideoLiveWallpaperIGService;

public class VideoDownloadActivity extends BaseActivity {

    ImageView imgDownload, imgBack, imgInstagramLogin;
    VideoDownloadActivity activity;
    LinearLayout linearVideoExtractAudio, linearVideoPlayVideo, linearVideoWallapaper, linearVideoShare, linearImageWallapaper, linearImageShow, linearImageShare;
    String pathAudio = CommonClass.IgAudioPathDirectory + "/" + System.currentTimeMillis() + "." + "mp3";
    Uri Videofileuri;
    LinearLayout videoItem, imageItem;
    TextView txtAudioVideo, txtWallpaperVideo, txtPreviewVideo, txtShareVideo, txtWallpaperImage, txtPreviewImage, txtShareImage, txtVideoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_download);

        activity = this;

        String uri = getIntent().getStringExtra("url");
        String links = getIntent().getStringExtra("link");
        String names = getIntent().getStringExtra("name");


        Log.d("TAG", "onCreategj: " + uri);
        Log.d("TAG", "onCreategj1: " + links);

        MediaScannerConnection.scanFile(this, new String[]{uri}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Videofileuri = uri;
            }
        });

        imgDownload = findViewById(R.id.imgDownload);
        linearVideoExtractAudio = findViewById(R.id.linearVideoExtractAudio);
        linearVideoPlayVideo = findViewById(R.id.linearVideoPlayVideo);
        linearVideoWallapaper = findViewById(R.id.linearVideoWallapaper);
        linearVideoShare = findViewById(R.id.linearVideoShare);
        linearImageWallapaper = findViewById(R.id.linearImageWallapaper);
        linearImageShow = findViewById(R.id.linearImageShow);
        linearImageShare = findViewById(R.id.linearImageShare);
        imgBack = findViewById(R.id.imgBack);
        videoItem = findViewById(R.id.videoItem);
        imageItem = findViewById(R.id.imageItem);
        imgInstagramLogin = findViewById(R.id.imgInstagramLogin);
        txtAudioVideo = findViewById(R.id.txtAudioVideo);
        txtWallpaperVideo = findViewById(R.id.txtWallpaperVideo);
        txtPreviewVideo = findViewById(R.id.txtPreviewVideo);
        txtShareVideo = findViewById(R.id.txtShareVideo);
        txtWallpaperImage = findViewById(R.id.txtWallpaperImage);
        txtPreviewImage = findViewById(R.id.txtPreviewImage);
        txtShareImage = findViewById(R.id.txtShareImage);
        txtVideoTitle = findViewById(R.id.txtVideoTitle);

        txtAudioVideo.setSelected(true);
        txtWallpaperVideo.setSelected(true);
        txtPreviewVideo.setSelected(true);
        txtShareVideo.setSelected(true);
        txtWallpaperImage.setSelected(true);
        txtPreviewImage.setSelected(true);
        txtShareImage.setSelected(true);
        txtVideoTitle.setSelected(true);

        imgInstagramLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.OpenApp(activity, "com.instagram.android");
            }
        });


        if (uri.endsWith(".jpg") || uri.endsWith(".heic") || uri.endsWith(".png") || uri.endsWith(".jpeg") || uri.endsWith(".webp")) {
            videoItem.setVisibility(View.GONE);
            imageItem.setVisibility(View.VISIBLE);

            linearImageShow.setOnClickListener(new DebouncedOnClickListener(750) {
                @Override
                public void onDebouncedClick(View v) {
                    Intent intent = new Intent(activity, VideoPlayerActivity.class);
                    intent.putExtra("from", uri);
                    intent.putExtra("lin", links);
                    intent.putExtra("name", names);
                    startActivity(intent);
                }
            });


            linearImageShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonClass.shareAllImage(activity, uri);
                }
            });

            linearImageWallapaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(activity, WallpaperActivity.class).putExtra("uriImg", Videofileuri.toString()));
                }
            });

        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MediaScannerConnection.scanFile(activity, new String[]{uri}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.d("TAG", "onScanCompleted: " + uri);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(activity).load(uri).override(500,500).into(imgDownload);
                    }
                });
            }
        });


        linearVideoPlayVideo.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                Intent intent = new Intent(activity, VideoPlayerActivity.class);
                intent.putExtra("from", uri);
                intent.putExtra("lin", links);
                intent.putExtra("name", names);
                startActivity(intent);
            }
        });

        linearVideoExtractAudio.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                try {
                    new AudioExtractor().genVideoUsingMuxer(uri, pathAudio, -1, -1, true, false);
                    startActivity(new Intent(activity, AudioActivity.class).putExtra("path", pathAudio).putExtra("name", names).putExtra("positionsss", "do not list")

                    );
                } catch (IOException e) {
                    Log.d("TAG", "errorss: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });


        linearVideoWallapaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uris = Videofileuri;
                if (uris != null) {
                    try {
                        FileOutputStream fos = openFileOutput("video_live_wallpaper_file_path", Context.MODE_PRIVATE);
                        String path = DocumentUtils.getPath(activity, Videofileuri);
                        if (path != null) {
                            fos.write(path.getBytes());
                            fos.close();
                            VideoLiveWallpaperIGService.setToIgWallPaper(activity);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        linearVideoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareAllVideo(activity, uri);
            }
        });


        imgDownload.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                Intent intent = new Intent(activity, VideoPlayerActivity.class);
                intent.putExtra("from", uri);
                intent.putExtra("lin", links);
                intent.putExtra("name", names);
                startActivity(intent);
            }
        });


    }
}