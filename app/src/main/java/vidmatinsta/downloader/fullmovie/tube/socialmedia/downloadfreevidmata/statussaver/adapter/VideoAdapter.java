package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter;

import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass.IgAudioPathDirectory;
import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass.shareAllVideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.AudioActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.VideoPlayerActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.AudioExtractor;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DebouncedOnClickListener;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.service.VideoLiveWallpaperIGService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DocumentUtils;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    FragmentActivity requireActivity;
    public ArrayList<File> videoFile;
    LinearLayout linearAudio, linearWallpaper, linearShare, linearDelete;
    TextView txtVideoName, txtAudio, txtWallpaper, txtShare, txtDelete;
    ImageView close;
    String pathAudio = IgAudioPathDirectory + "/" + System.currentTimeMillis() + "." + "mp3";
    Uri VideoFiles;
    DeleteData VideoDeleteData;


    public VideoAdapter(FragmentActivity requireActivity, ArrayList<File> videoFile, DeleteData VideoDeleteData) {
        this.requireActivity = requireActivity;
        this.videoFile = videoFile;
        this.VideoDeleteData = VideoDeleteData;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(requireActivity).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MediaScannerConnection.scanFile(requireActivity, new String[]{videoFile.get(position).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                VideoFiles = uri;
            }
        });

        String videoSizeDuration = CommonClass.getStringSizeLengthFile(videoFile.get(position).length());
        String videoSize = videoSizeDuration;
        long duration = 0;
        try {
            duration = CommonClass.getVideoDuration(videoFile.get(position).getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String durationString = CommonClass.convertDurationToString(duration);

        if (!videoFile.isEmpty() && videoFile.get(position).getAbsolutePath() != null) {
            Glide.with(requireActivity).load(videoFile.get(position).getAbsolutePath()).into(holder.imgVideoShow);
        }

        holder.txtVideoName.setSelected(true);
        holder.txtVideoName.setText(videoFile.get(position).getName());
        holder.txtVideoSize.setText(videoSize);
        holder.txtDuration.setText(durationString);

        holder.itemView.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                Intent intent = new Intent(requireActivity, VideoPlayerActivity.class);
                intent.putExtra("from", videoFile.get(position).getAbsolutePath());
                intent.putExtra("lin", "");
                intent.putExtra("name", videoFile.get(position).getName());
                requireActivity.startActivity(intent);
            }
        });

        holder.imgVideoDownloadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(requireActivity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = requireActivity.getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.video_dailog, null);
                alertDialog.setView(view);
                alertDialog.setCanceledOnTouchOutside(false);

                linearAudio = view.findViewById(R.id.linearAudio);
                linearWallpaper = view.findViewById(R.id.linearWallpaper);
                linearShare = view.findViewById(R.id.linearShare);
                linearDelete = view.findViewById(R.id.linearDelete);
                txtVideoName = view.findViewById(R.id.txtVideoName);
                txtAudio = view.findViewById(R.id.txtAudio);
                txtWallpaper = view.findViewById(R.id.txtWallpaper);
                txtShare = view.findViewById(R.id.txtShare);
                txtDelete = view.findViewById(R.id.txtDelete);
                close = view.findViewById(R.id.close);

                txtVideoName.setSelected(true);
                txtAudio.setSelected(true);
                txtWallpaper.setSelected(true);
                txtShare.setSelected(true);
                txtDelete.setSelected(true);

                txtVideoName.setText(videoFile.get(position).getName());
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                linearWallpaper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uris = VideoFiles;
                        if (uris != null) {
                            try {
                                FileOutputStream fos = requireActivity.openFileOutput("video_live_wallpaper_file_path", Context.MODE_PRIVATE);
                                String path = DocumentUtils.getPath(requireActivity, VideoFiles);
                                if (path != null) {
                                    fos.write(path.getBytes());
                                    fos.close();
                                    VideoLiveWallpaperIGService.setToIgWallPaper(requireActivity);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            alertDialog.dismiss();
                        }
                    }
                });

                linearShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareAllVideo(requireActivity, videoFile.get(position).getAbsolutePath());
                        alertDialog.dismiss();
                    }
                });

                linearAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            pathAudio = IgAudioPathDirectory + "/" + System.currentTimeMillis() + "." + "mp3";
                            new AudioExtractor().genVideoUsingMuxer(videoFile.get(position).getAbsolutePath(), pathAudio, -1, -1, true, false);
                            requireActivity.startActivity(new Intent(requireActivity, AudioActivity.class)
                                    .putExtra("path", pathAudio)
                                    .putExtra("name",videoFile.get(position).getName())
                                    .putExtra("positions", position)
                            );
                        } catch (IOException e) {
                            Log.d("TAG", "errorss: " + e.getMessage());
                            throw new RuntimeException(e);
                        }
                        alertDialog.dismiss();
                    }
                });

                linearDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog delete_dialog = new AlertDialog.Builder(requireActivity, R.style.MyTransparentBottomSheetDialogTheme).create();
                        LayoutInflater layoutInflater = requireActivity.getLayoutInflater();
                        View view1 = layoutInflater.inflate(R.layout.delete_layout, null);
                        delete_dialog.setView(view1);
                        delete_dialog.setCanceledOnTouchOutside(false);
                        TextView cancel = view1.findViewById(R.id.delete_cancel);
                        TextView delete_btn = view1.findViewById(R.id.delete_ok);

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete_dialog.dismiss();
                            }
                        });

                        delete_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                VideoDeleteData.VideoDeleteClick(new File(videoFile.get(position).getAbsolutePath()), 0);
                                delete_dialog.dismiss();
                            }
                        });

                        delete_dialog.show();
                        Window window = delete_dialog.getWindow();
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        requireActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int screenWidth = displayMetrics.widthPixels;
                        int dialogWidth = (int) (screenWidth * 0.83);
                        window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                        window.setGravity(Gravity.CENTER);
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
                Window window = alertDialog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                requireActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.83);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            }
        });

        if (position >= videoFile.size() - 1) {
            holder.viewBorder.setVisibility(View.GONE);
        }
    }

    public interface DeleteData {
        void VideoDeleteClick(File str, int i);
    }

    @Override
    public int getItemCount() {
        return videoFile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgVideoShow, imgVideoDownloadMore;
        TextView txtDuration, txtVideoName, txtVideoSize;
        View viewBorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVideoShow = itemView.findViewById(R.id.imgPreview);
            imgVideoDownloadMore = itemView.findViewById(R.id.imgVideoDownloadMore);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtVideoName = itemView.findViewById(R.id.txtVideoName);
            txtVideoSize = itemView.findViewById(R.id.txtVideoSize);
            viewBorder = itemView.findViewById(R.id.viewBorder);
        }
    }
}
