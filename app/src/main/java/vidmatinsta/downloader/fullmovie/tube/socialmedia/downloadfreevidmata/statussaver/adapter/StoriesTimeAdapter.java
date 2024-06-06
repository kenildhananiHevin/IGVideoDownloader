package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.ExploreActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.StoryVideoPlayerActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.time_line.Media;

public class StoriesTimeAdapter extends RecyclerView.Adapter<StoriesTimeAdapter.ViewHolder> {
    ExploreActivity activity;
   public  ArrayList<Media> sectional_items;
    Context context;

    public StoriesTimeAdapter(ExploreActivity activity, ArrayList<Media> sectional_items, Context context) {
        this.activity = activity;
        this.sectional_items = sectional_items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.explore_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesTimeAdapter.ViewHolder holder, int position) {
        Media itemModel = sectional_items.get(position);
        try {
            if (itemModel.video_versions != null) {
                holder.imgStoryPlay.setVisibility(View.VISIBLE);
            } else {
                holder.imgStoryPlay.setVisibility(View.GONE);
            }
            Glide.with(this.context).load(itemModel.image_versions2.candidates.get(0).url).override(200,200).into(holder.shapeableImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModel.video_versions != null && !itemModel.video_versions.isEmpty()) {
                    VideoShow(itemModel);
                } else {
                    ImageShow(itemModel);
                }
            }
        });
    }

    private void ImageShow(Media itemModel) {
        Intent intent = new Intent(activity, StoryVideoPlayerActivity.class);
        intent.putExtra("from", itemModel.image_versions2.candidates.get(0).url);
        intent.putExtra("lin", "");
        intent.putExtra("name", "");
        activity.startActivity(intent);
    }

    private void VideoShow(Media itemModel) {
        Intent intent = new Intent(activity, StoryVideoPlayerActivity.class);
        intent.putExtra("from", itemModel.video_versions.get(0).url);
        intent.putExtra("lin", "");
        intent.putExtra("name", "");
        activity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return sectional_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView shapeableImage;
        ImageView imgStoryPlay,multiImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImage = itemView.findViewById(R.id.shapeableImage);
            imgStoryPlay = itemView.findViewById(R.id.imgStoryPlay);
            multiImage = itemView.findViewById(R.id.multiImage);
        }
    }
}
