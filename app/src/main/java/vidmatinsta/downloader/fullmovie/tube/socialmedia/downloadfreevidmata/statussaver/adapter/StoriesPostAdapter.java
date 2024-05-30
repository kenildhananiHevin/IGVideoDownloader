package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.StoryVideoPlayerActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.Item;

public class StoriesPostAdapter extends RecyclerView.Adapter<StoriesPostAdapter.ViewHolder> {
    Context context;
    public ArrayList<Item> items;
    FragmentActivity requireActivity;

    public StoriesPostAdapter(Context context, ArrayList<Item> items, FragmentActivity requireActivity) {
        this.context = context;
        this.items = items;
        this.requireActivity = requireActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(requireActivity).inflate(R.layout.story_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesPostAdapter.ViewHolder holder, int position) {
        Item itemModel = items.get(position);
        try {
            if (itemModel.video_versions != null) {
                holder.imgStoryPlay.setVisibility(View.VISIBLE);
            } else {
                holder.imgStoryPlay.setVisibility(View.GONE);
            }
            Glide.with(this.context).load(itemModel.image_versions2.candidates.get(0).url).into(holder.shapeableImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModel.video_versions != null) {
                    Intent intent = new Intent(requireActivity, StoryVideoPlayerActivity.class);
                    intent.putExtra("from", itemModel.video_versions.get(0).url);
                    intent.putExtra("lin", "");
                    intent.putExtra("name", "");
                    requireActivity.startActivity(intent);
                } else {
                    Intent intent = new Intent(requireActivity, StoryVideoPlayerActivity.class);
                    intent.putExtra("from", itemModel.image_versions2.candidates.get(0).url);
                    intent.putExtra("lin", "");
                    intent.putExtra("name", "");
                    requireActivity.startActivity(intent);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView shapeableImage;
        ImageView imgStoryPlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImage = itemView.findViewById(R.id.shapeableImage);
            imgStoryPlay = itemView.findViewById(R.id.imgStoryPlay);
        }
    }
}
