package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.fragment.HomeFragment.startDownload;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.ImagePagerAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.AudioExtractor;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DebouncedOnClickListener;

public class StoryVideoPlayerActivity extends BaseActivity {
    StoryVideoPlayerActivity activity;
    public static StyledPlayerView player;
    private ExoPlayer players;
    float currentSpeed = 1.0f;
    ImageView imgShare, imgWallpaper, imgBack, imgMore, imgRepost, imgTopTrans, imgOpenInst, imgDownload;
    LinearLayout linearItem;
    String pathAudio = CommonClass.IgAudioPathDirectory + "/" + System.currentTimeMillis() + "." + "mp3";
    TextView txtVideoPlayer, txtImage;
    RelativeLayout relativeToolBar;
    AlertDialog alertDialog;
    Uri fileuri;
    ArrayList<String> photopath = new ArrayList<>();
    private ImagePagerAdapter adapter;
    ViewPager2 imgPic;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_video_player);

        activity = this;
        String url = getIntent().getStringExtra("from");
        String linkss = getIntent().getStringExtra("lin");
        String allName = getIntent().getStringExtra("name");
        int positions = getIntent().getIntExtra("position", 0);
        Log.d("TAG", "pathss: " + url);
        Log.d("TAG", "linkss: " + linkss);


        try {
            if (url != null) {
                MediaScannerConnection.scanFile(this, new String[]{url}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        fileuri = uri;
                    }
                });
            } else {
                Log.e("TAG", "onCreate: ");
            }
        } catch (Exception e) {
            Log.e("=====Kenil", "onCreate: " + e.getMessage());
        }


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
        imgDownload = findViewById(R.id.imgDownload);


        players = new ExoPlayer.Builder(activity).build();

        if (url != null) {
            MediaItem mediaItem = MediaItem.fromUri(url);
            players.setMediaItem(mediaItem);
            players.prepare();
            player.setPlayer(players);
            players.play();
        }

        if (getIntent().getStringExtra("photopath") != null) {
            String json = getIntent().getStringExtra("photopath");
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            photopath = gson.fromJson(json, listType);
            adapter = new ImagePagerAdapter(this, photopath);
            imgPic.setAdapter(adapter);
            imgPic.setCurrentItem(positions);
            player.setVisibility(View.GONE);
            relativeToolBar.setVisibility(View.VISIBLE);
            linearItem.setVisibility(View.VISIBLE);
            imgTopTrans.setVisibility(View.VISIBLE);
            imgOpenInst.setVisibility(View.GONE);
            imgDownload.setVisibility(View.VISIBLE);
            imgRepost.setVisibility(View.GONE);

        } else if (url.contains(".jpg") || url.contains(".heic") || url.contains(".png") || url.contains(".jpeg") || url.contains(".webp")) {
            player.setVisibility(View.GONE);
            relativeToolBar.setVisibility(View.VISIBLE);
            linearItem.setVisibility(View.VISIBLE);
            imgTopTrans.setVisibility(View.VISIBLE);

            photopath.add(url);
            adapter = new ImagePagerAdapter(this, photopath);
            imgPic.setAdapter(adapter);
            imgPic.setCurrentItem(positions);
            imgOpenInst.setVisibility(View.GONE);
            imgDownload.setVisibility(View.VISIBLE);
            imgRepost.setVisibility(View.GONE);
        } else {
            new GuideView.Builder(activity)
                    .setTitle(getString(R.string.extract_audio))
                    .setContentText(getString(R.string.extract_this_audio))
                    .setDismissType(DismissType.anywhere)
                    .setTargetView(findViewById(R.id.imgVideoAudio))
                    .setContentTextSize(12)
                    .setTitleTextSize(14)
                    .build()
                    .show();
        }

        txtImage.setText(allName);
        txtImage.setSelected(true);

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareAllImage(activity, photopath.get(imgPic.getCurrentItem()));
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgDownload.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                try {
                    createProgress();
                    startDownload(photopath.get(imgPic.getCurrentItem()), activity, System.currentTimeMillis() + ".jpg", alertDialog, "", "");
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });

        imgWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, WallpaperActivity.class).putExtra("uriImg", photopath.get(imgPic.getCurrentItem())));
            }
        });

        txtVideoPlayer.setText(allName);
        txtVideoPlayer.setSelected(true);

        findViewById(R.id.imgVideoDownload).setVisibility(View.VISIBLE);
        findViewById(R.id.imgVideoOpenInst).setVisibility(View.GONE);
        findViewById(R.id.imgVideoWallpaper).setVisibility(View.GONE);
        findViewById(R.id.imgVideoRepost).setVisibility(View.GONE);

        findViewById(R.id.imgVideoDownload).setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                try {
                    createProgress();
                    startDownload(url, activity, System.currentTimeMillis() + ".mp4", alertDialog, "", "");
                } catch (Exception e) {
                    e.getMessage();
                }
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
                            .putExtra("name", url)
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
        DefaultTimeBar timeBar = player.findViewById(com.google.android.exoplayer2.ui.R.id.exo_progress);
        if (timeBar != null) {
            timeBar.setBackgroundColor(ContextCompat.getColor(activity, R.color.transparent));
            timeBar.setScrubberColor(ContextCompat.getColor(activity, R.color.white));
            timeBar.setPlayedColor(ContextCompat.getColor(activity, R.color.app_color));
            timeBar.setUnplayedColor(ContextCompat.getColor(activity, R.color.white));
            timeBar.setBufferedColor(ContextCompat.getColor(activity, R.color.white));
        }
    }

    private void createProgress() {
        alertDialog = new AlertDialog.Builder(activity, R.style.MyTransparentBottomSheetDialogTheme).create();
        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.pogress_dailog, null);
        alertDialog.setView(view1);
        alertDialog.setCanceledOnTouchOutside(false);
        ProgressBar progressBar = view1.findViewById(R.id.progressBar);

        alertDialog.show();
        Window window = alertDialog.getWindow();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int dialogWidth = (int) (screenWidth * 0.88);
        window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
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