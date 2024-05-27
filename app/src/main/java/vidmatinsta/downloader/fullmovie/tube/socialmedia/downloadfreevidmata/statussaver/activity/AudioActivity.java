package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.File;
import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;

public class AudioActivity extends BaseActivity {
    PlayerView playerView;
    ExoPlayer exoPlayer;
    AudioActivity activity;
    private ArrayList<MediaItem> mediaItem;
    float currentSpeed = 1.0f;
    TextView txtAudioName, txtAName;
    ArrayList<File> musicFile;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        activity = this;
        String audio = getIntent().getStringExtra("path");
        String allAudioName = getIntent().getStringExtra("name");
        int allAudioPosition = getIntent().getIntExtra("positions", 0);
        String audioList = getIntent().getStringExtra("positionsss");

        musicFile = new ArrayList<>();

        playerView = findViewById(R.id.playerAudioView);
        txtAudioName = findViewById(R.id.txtAudioName);
        txtAName = findViewById(R.id.txtAName);

        txtAudioName.setSelected(true);
        txtAName.setSelected(true);
        txtAudioName.setText(allAudioName);
        txtAName.setText(allAudioName);

        if (audioList != null && audioList.equals("do not list")) {
            exoPlayer = new ExoPlayer.Builder(activity).build();
            mediaItem = new ArrayList<>();
            MediaItem mediaItems = MediaItem.fromUri(audio);
            mediaItem.add(mediaItems);
            exoPlayer.setMediaItem(mediaItems);
            exoPlayer.prepare();
            playerView.setPlayer(exoPlayer);
        } else {
            int i = 0;
            if (CommonClass.IgAudioPathDirectory.listFiles() != null) {
                for (File file : CommonClass.IgAudioPathDirectory.listFiles()) {
                    if (!file.getName().startsWith(".")) {
                        if (audio.equals(file.getAbsolutePath())){
                            allAudioPosition = i;
                        }
                       musicFile.add(file);
                        i++;
                    }

                }

            }

            exoPlayer = new ExoPlayer.Builder(activity).build();
            mediaItem = new ArrayList<>();
            for (File item : musicFile) {
                MediaItem mediaItems = MediaItem.fromUri(Uri.parse(item.getAbsolutePath()));
                mediaItem.add(mediaItems);
            }
            exoPlayer.setMediaItems(mediaItem);
            exoPlayer.seekTo(allAudioPosition, 0);
            exoPlayer.prepare();
            playerView.setPlayer(exoPlayer);
        }


//        musicFile.clear();


        findViewById(R.id.imgAudioBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.imgAudioSpeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu(v);
            }
        });

        findViewById(R.id.imgAudioShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareAudio(activity, audio);
            }
        });

        DefaultTimeBar timeBar = playerView.findViewById(com.google.android.exoplayer2.ui.R.id.exo_progress);
        if (timeBar != null) {
            timeBar.setBackgroundColor(ContextCompat.getColor(activity, R.color.transparent));
            timeBar.setScrubberColor(ContextCompat.getColor(activity, R.color.app_color));
            timeBar.setPlayedColor(ContextCompat.getColor(activity, R.color.app_color));
            timeBar.setUnplayedColor(ContextCompat.getColor(activity, R.color.seek_color));
            timeBar.setBufferedColor(ContextCompat.getColor(activity, R.color.seek_color));
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
            popupWindow.showAsDropDown(view, 0, 0);
        } else {
            popupWindow.showAsDropDown(view, 0, -650);
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
                if (exoPlayer != null)
                    exoPlayer.setPlaybackParameters(playbackParameters);
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
                if (exoPlayer != null)
                    exoPlayer.setPlaybackParameters(playbackParameters);
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
                if (exoPlayer != null)
                    exoPlayer.setPlaybackParameters(playbackParameters);
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
                if (exoPlayer != null)
                    exoPlayer.setPlaybackParameters(playbackParameters);
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
                if (exoPlayer != null)
                    exoPlayer.setPlaybackParameters(playbackParameters);
                finalPopupWindow.dismiss();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        exoPlayer.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        exoPlayer.play();
    }
}