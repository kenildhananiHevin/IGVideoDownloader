package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.AudioActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DebouncedOnClickListener;

import java.io.File;
import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    FragmentActivity requireActivity;
    public ArrayList<File> musicFile;
    DeleteMusic deleteMusic;

    public MusicAdapter(FragmentActivity requireActivity, ArrayList<File> musicFile, DeleteMusic deleteMusic) {
        this.requireActivity = requireActivity;
        this.musicFile = musicFile;
        this.deleteMusic = deleteMusic;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(requireActivity).inflate(R.layout.audio_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.audioText.setText(musicFile.get(position).getName());

        holder.audioShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareAudio(requireActivity, musicFile.get(position).getAbsolutePath());
            }
        });

        holder.audioDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog MusicDeleteDailog = new AlertDialog.Builder(requireActivity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = requireActivity.getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.delete_layout, null);
                MusicDeleteDailog.setView(view1);
                MusicDeleteDailog.setCanceledOnTouchOutside(false);
                TextView cancel = view1.findViewById(R.id.delete_cancel);
                TextView delete_btn = view1.findViewById(R.id.delete_ok);
                TextView delete = view1.findViewById(R.id.delete);
                TextView delete_txt = view1.findViewById(R.id.delete_txt);

                delete.setText(R.string.delete_audio);
                delete_txt.setText(R.string.audio_d);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MusicDeleteDailog.dismiss();
                    }
                });

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteMusic.MusicDeleteClick(new File(musicFile.get(position).getAbsolutePath()), 0);
                        MusicDeleteDailog.dismiss();
                    }
                });

                MusicDeleteDailog.show();
                Window window = MusicDeleteDailog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                requireActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.88);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            }
        });

        holder.itemView.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                requireActivity.startActivity(new Intent(requireActivity, AudioActivity.class)
                        .putExtra("path", musicFile.get(position).getAbsolutePath())
                        .putExtra("positions", position)
                        .putExtra("name", musicFile.get(position).getName()));
            }
        });

        if (position >= musicFile.size() - 1) {
            holder.viewAudioBorder.setVisibility(View.INVISIBLE);
        }else {
            holder.viewAudioBorder.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return musicFile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView audioShare, audioDelete;
        TextView audioText;
        View viewAudioBorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            audioShare = itemView.findViewById(R.id.audioShare);
            audioDelete = itemView.findViewById(R.id.audioDelete);
            audioText = itemView.findViewById(R.id.audioText);
            viewAudioBorder = itemView.findViewById(R.id.viewAudioBorder);
        }
    }

    public interface DeleteMusic {
        void MusicDeleteClick(File str, int i);
    }

}
