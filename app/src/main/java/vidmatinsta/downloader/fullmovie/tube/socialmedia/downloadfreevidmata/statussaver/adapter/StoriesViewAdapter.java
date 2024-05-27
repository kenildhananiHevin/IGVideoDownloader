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
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.VideoPlayerActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.InstagramStory;

public class StoriesViewAdapter extends RecyclerView.Adapter<StoriesViewAdapter.ViewHolder> {
    Context context;
    ArrayList<InstagramStory> items;
    FragmentActivity requireActivity;

    public StoriesViewAdapter(Context context, ArrayList<InstagramStory> items, FragmentActivity requireActivity) {
        this.context = context;
        this.items = items;
        this.requireActivity = requireActivity;
    }

    @NonNull
    @Override
    public StoriesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.story_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesViewAdapter.ViewHolder holder, int position) {
        InstagramStory itemModel = items.get(position);
        try {
            if (itemModel.getMedia_type() == 2) {
                holder.imgStoryPlay.setVisibility(View.VISIBLE);
            } else {
                holder.imgStoryPlay.setVisibility(View.GONE);
            }
            Glide.with(this.context).load(itemModel.getImage_versions2().getCandidates().get(0).getUrl()).into(holder.shapeableImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModel.getMedia_type() == 2) {
                    VideoShow(itemModel);
                } else {
                    ImageShow(itemModel);
                }
            }
        });
    }

    private void ImageShow(InstagramStory itemModel) {
        Intent intent = new Intent(requireActivity, StoryVideoPlayerActivity.class);
        Log.d("TAG", "ImageShow: "+itemModel.getImage_versions2().getCandidates().get(0).getUrl());
        intent.putExtra("from", itemModel.getImage_versions2().getCandidates().get(0).getUrl());
        intent.putExtra("lin", "");
        intent.putExtra("name", "");
        requireActivity.startActivity(intent);
    }

    private void VideoShow(InstagramStory itemModel) {
        Intent intent = new Intent(requireActivity, StoryVideoPlayerActivity.class);
        intent.putExtra("from", itemModel.getVideo_versions().get(0).getUrl());
        intent.putExtra("lin", "");
        intent.putExtra("name", "");
        requireActivity.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        ArrayList<InstagramStory> arrayList = items;
        if (arrayList == null) {
            return 0;
        }
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
