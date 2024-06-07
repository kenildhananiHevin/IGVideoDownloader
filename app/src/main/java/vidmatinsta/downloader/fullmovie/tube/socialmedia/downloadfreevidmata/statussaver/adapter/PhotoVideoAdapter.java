package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.PhotoVideoActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.StoryVideoPlayerActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video.Node;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DebouncedOnClickListener;

public class PhotoVideoAdapter extends RecyclerView.Adapter<PhotoVideoAdapter.ViewHolder> {

    PhotoVideoActivity activity;
    List<Node> stickyNodesList;

    public PhotoVideoAdapter(PhotoVideoActivity activity, List<Node> stickyNodesList) {
        this.activity = activity;
        this.stickyNodesList = stickyNodesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.photo_video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoVideoAdapter.ViewHolder holder, int position) {

        Glide.with(activity).load(stickyNodesList.get(position).getDisplayUrl()).override(200,200).into(holder.shapeablePhotoVideo);

        holder.itemView.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                Intent intent = new Intent(activity, StoryVideoPlayerActivity.class);
                ArrayList<String> photoPath = new ArrayList<>();
                for (int i = 0; i < stickyNodesList.size(); i++) {
                    photoPath.add(stickyNodesList.get(i).getDisplayUrl());
                }
                intent.putExtra("photopath", new Gson().toJson(photoPath));
                intent.putExtra("lin", "");
                intent.putExtra("name", "");
                intent.putExtra("position", position);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stickyNodesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shapeablePhotoVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shapeablePhotoVideo = itemView.findViewById(R.id.imgPhotoShow);
        }
    }
}
