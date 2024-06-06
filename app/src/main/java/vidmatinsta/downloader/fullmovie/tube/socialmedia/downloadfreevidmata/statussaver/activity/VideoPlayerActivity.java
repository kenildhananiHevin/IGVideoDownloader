package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.AudioExtractor;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DebouncedOnClickListener;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.service.VideoLiveWallpaperIGService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.io.FileOutputStream;
import java.io.IOException;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DocumentUtils;

public class VideoPlayerActivity extends BaseActivity {

    VideoPlayerActivity activity;
    public static StyledPlayerView player;
    private ExoPlayer players;
    float currentSpeed = 1.0f;
    ImageView imgPic, imgShare, imgWallpaper, imgBack, imgMore, imgRepost, imgTopTrans, imgOpenInst;
    LinearLayout linearItem;
    String pathAudio = CommonClass.IgAudioPathDirectory + "/" + System.currentTimeMillis() + "." + "mp3";
    Uri fileuri;
    TextView txtVideoPlayer, txtImage;
    RelativeLayout relativeToolBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        activity = this;
        String url = getIntent().getStringExtra("from");
        String linkss = getIntent().getStringExtra("lin");
        String allName = getIntent().getStringExtra("name");
        Log.d("TAG", "pathss: " + url);
        Log.d("TAG", "linkss: " + linkss);


        MediaScannerConnection.scanFile(this, new String[]{url}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                fileuri = uri;
            }
        });


        player = findViewById(R.id.video_view);
        imgPic = findViewById(R.id.imgPic);
        imgShare = findViewById(R.id.imgShare);
        imgRepost = findViewById(R.id.imgRepost);
        imgBack = findViewById(R.id.imgBack);
        imgWallpaper = findViewById(R.id.imgWallpaper);
        relativeToolBar = findViewById(R.id.relativeToolBar);
        linearItem = findViewById(R.id.linearItem);
        txtVideoPlayer = findViewById(R.id.txtVideoPlayer);
        imgTopTrans = findViewById(R.id.imgTopTrans);
        imgOpenInst = findViewById(R.id.imgOpenInst);
        txtImage = findViewById(R.id.txtImage);


        players = new ExoPlayer.Builder(activity).build();
        MediaItem mediaItem = MediaItem.fromUri(url);
        players.setMediaItem(mediaItem);
        players.prepare();
        player.setPlayer(players);
        players.play();


        if (url.endsWith(".jpg")|| url.endsWith(".heic") || url.endsWith(".png") || url.endsWith(".jpeg") || url.endsWith(".webp")) {
            player.setVisibility(View.GONE);
            relativeToolBar.setVisibility(View.VISIBLE);
            linearItem.setVisibility(View.VISIBLE);
            imgTopTrans.setVisibility(View.VISIBLE);
            Glide.with(activity).load(fileuri).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgPic);

            txtImage.setText(allName);
            txtImage.setSelected(true);

            imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonClass.shareAllImage(activity, url);
                }
            });

            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            imgRepost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonClass.shareInstagramImage(activity, url);
                }
            });

            if (linkss.isEmpty()) {
                imgOpenInst.setVisibility(View.GONE);
            }

            imgOpenInst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent imagePost = new Intent("android.intent.action.VIEW", Uri.parse(linkss));
                    imagePost.setPackage("com.instagram.android");
                    try {
                        startActivity(imagePost);
                    } catch (ActivityNotFoundException unused) {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(linkss)));
                    }
                }
            });
            imgWallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(activity, WallpaperActivity.class).putExtra("uriImg", fileuri.toString()));
                }
            });
        } else {
            new GuideView.Builder(activity)
                    .setTitle(getString(R.string.extract_audio))
                    .setContentText(getString(R.string.extract_this_audio))
                    .setDismissType(DismissType.anywhere)
                    .setTargetView(findViewById(R.id.imgVideoAudio))
                    .setContentTextSize(12)//optional
                    .setTitleTextSize(14)//optional
                    .build()
                    .show();
        }


        txtVideoPlayer.setText(allName);
        txtVideoPlayer.setSelected(true);


        if (linkss.isEmpty()) {
            findViewById(R.id.imgVideoOpenInst).setVisibility(View.GONE);
        }

        findViewById(R.id.imgVideoOpenInst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "linkss1: " + linkss);
                Intent videoReels = new Intent("android.intent.action.VIEW", Uri.parse(linkss));
                videoReels.setPackage("com.instagram.android");
                try {
                    startActivity(videoReels);
                } catch (ActivityNotFoundException unused) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(linkss)));
                }
            }
        });

        findViewById(R.id.imgVideoRepost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareInstagram(activity, url);
            }
        });

        findViewById(R.id.imgVideoSpeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu(v);
            }
        });

        findViewById(R.id.imgVideoShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareAllVideo(activity, url);
            }
        });

        findViewById(R.id.imgVideoAudio).setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                try {
                    new AudioExtractor().genVideoUsingMuxer(url, pathAudio, -1, -1, true, false);
                    startActivity(new Intent(activity, AudioActivity.class)
                            .putExtra("path", pathAudio)
                            .putExtra("name", allName)
                            .putExtra("positionsss", "do not list"));
                } catch (IOException e) {
                    Log.d("TAG", "errorss: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });

        findViewById(R.id.imgVideoBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.imgVideoWallpaper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = fileuri;
                if (uri != null) {
                    try {
                        FileOutputStream fos = openFileOutput("video_live_wallpaper_file_path", Context.MODE_PRIVATE);
                        String path = DocumentUtils.getPath(activity, fileuri);
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

        DefaultTimeBar timeBar = player.findViewById(com.google.android.exoplayer2.ui.R.id.exo_progress);
        if (timeBar != null) {
            timeBar.setBackgroundColor(ContextCompat.getColor(activity, R.color.transparent));
            timeBar.setScrubberColor(ContextCompat.getColor(activity, R.color.white));
            timeBar.setPlayedColor(ContextCompat.getColor(activity, R.color.app_color));
            timeBar.setUnplayedColor(ContextCompat.getColor(activity, R.color.white));
            timeBar.setBufferedColor(ContextCompat.getColor(activity, R.color.white));
        }
    }

    private void popMenu(View view) {
        PopupWindow popupWindow;
        View popupView;

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.speed_dailog, null);
        popupWindow = new PopupWindow(
                popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        popupWindow.setOutsideTouchable(true);
        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        popupWindow.setFocusable(true);
        /*popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

        FrameLayout overlay = popupView.findViewById(R.id.overlay);
        overlay.setVisibility(View.VISIBLE);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        getWindow().setAttributes(layoutParams);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams layoutParams1 = getWindow().getAttributes();
            layoutParams1.alpha = 1.0f;
            getWindow().setAttributes(layoutParams1);
        });
        int[] values = new int[2];
        int positionOfIcon = values[1];
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels * 2 / 3;
        System.out.println("Height:" + height);
        if (positionOfIcon > height) {
            popupWindow.showAsDropDown(view, 0, -250);
        } else {
            popupWindow.showAsDropDown(view, 0, 0);
        }
        TextView txtSpeedThree = popupView.findViewById(R.id.txtSpeedThree);
        TextView txtSpeedTwo = popupView.findViewById(R.id.txtSpeedTwo);
        TextView txtSpeedOneFive = popupView.findViewById(R.id.txtSpeedOneFive);
        TextView txtSpeedOne = popupView.findViewById(R.id.txtSpeedOne);
        TextView txtSpeedFive = popupView.findViewById(R.id.txtSpeedFive);
        PopupWindow finalPopupWindow = popupWindow;

        txtSpeedThree.setTextColor(getColor(R.color.white));
        txtSpeedTwo.setTextColor(getColor(R.color.white));
        txtSpeedOneFive.setTextColor(getColor(R.color.white));
        txtSpeedOne.setTextColor(getColor(R.color.white));
        txtSpeedFive.setTextColor(getColor(R.color.white));

        if (currentSpeed == 3.0f) {
            txtSpeedThree.setTextColor(getColor(R.color.app_color));
        } else if (currentSpeed == 2.0f) {
            txtSpeedTwo.setTextColor(getColor(R.color.app_color));
        } else if (currentSpeed == 1.5f) {
            txtSpeedOneFive.setTextColor(getColor(R.color.app_color));
        } else if (currentSpeed == 1.0f) {
            txtSpeedOne.setTextColor(getColor(R.color.app_color));
        } else if (currentSpeed == 0.5f) {
            txtSpeedFive.setTextColor(getColor(R.color.app_color));
        }

        txtSpeedThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpeedThree.setTextColor(getColor(R.color.app_color));
                txtSpeedTwo.setTextColor(getColor(R.color.white));
                txtSpeedOneFive.setTextColor(getColor(R.color.white));
                txtSpeedOne.setTextColor(getColor(R.color.white));
                txtSpeedFive.setTextColor(getColor(R.color.white));
                currentSpeed = 3.0f;
                PlaybackParameters playbackParameters = new PlaybackParameters(currentSpeed);
                if (players != null)
                    players.setPlaybackParameters(playbackParameters);
                finalPopupWindow.dismiss();
            }
        });

        txtSpeedTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpeedThree.setTextColor(getColor(R.color.white));
                txtSpeedTwo.setTextColor(getColor(R.color.app_color));
                txtSpeedOneFive.setTextColor(getColor(R.color.white));
                txtSpeedOne.setTextColor(getColor(R.color.white));
                txtSpeedFive.setTextColor(getColor(R.color.white));
                currentSpeed = 2.0f;
                PlaybackParameters playbackParameters = new PlaybackParameters(currentSpeed);
                if (players != null)
                    players.setPlaybackParameters(playbackParameters);
                finalPopupWindow.dismiss();
            }
        });

        txtSpeedOneFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpeedThree.setTextColor(getColor(R.color.white));
                txtSpeedTwo.setTextColor(getColor(R.color.white));
                txtSpeedOneFive.setTextColor(getColor(R.color.app_color));
                txtSpeedOne.setTextColor(getColor(R.color.white));
                txtSpeedFive.setTextColor(getColor(R.color.white));
                currentSpeed = 1.5f;
                PlaybackParameters playbackParameters = new PlaybackParameters(currentSpeed);
                if (players != null)
                    players.setPlaybackParameters(playbackParameters);
                finalPopupWindow.dismiss();
            }
        });

        txtSpeedOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpeedThree.setTextColor(getColor(R.color.white));
                txtSpeedTwo.setTextColor(getColor(R.color.white));
                txtSpeedOneFive.setTextColor(getColor(R.color.white));
                txtSpeedOne.setTextColor(getColor(R.color.white));
                txtSpeedFive.setTextColor(getColor(R.color.white));
                currentSpeed = 1.0f;
                PlaybackParameters playbackParameters = new PlaybackParameters(currentSpeed);
                if (players != null)
                    players.setPlaybackParameters(playbackParameters);
                finalPopupWindow.dismiss();
            }
        });

        txtSpeedFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpeedThree.setTextColor(getColor(R.color.white));
                txtSpeedTwo.setTextColor(getColor(R.color.white));
                txtSpeedOneFive.setTextColor(getColor(R.color.white));
                txtSpeedOne.setTextColor(getColor(R.color.white));
                txtSpeedFive.setTextColor(getColor(R.color.app_color));
                currentSpeed = 0.5f;
                PlaybackParameters playbackParameters = new PlaybackParameters(currentSpeed);
                if (players != null)
                    players.setPlaybackParameters(playbackParameters);
                finalPopupWindow.dismiss();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        players.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        players.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        players.play();
    }
}