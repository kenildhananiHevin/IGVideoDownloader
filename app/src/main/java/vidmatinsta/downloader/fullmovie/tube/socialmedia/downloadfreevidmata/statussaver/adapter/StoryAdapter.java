package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.interfaces.StoryUserListInterface;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryTray;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    FragmentActivity requireActivity;
    ArrayList<StoryTray> arrayList;
    StoryUserListInterface storyUserListInterface;

    public StoryAdapter(FragmentActivity requireActivity, ArrayList<StoryTray> arrayList, StoryUserListInterface storyUserListInterface) {
        this.requireActivity = requireActivity;
        this.arrayList = arrayList;
        this.storyUserListInterface = storyUserListInterface;
    }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(requireActivity).inflate(R.layout.story_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {
        holder.txtRealName.setText(arrayList.get(position).getUser().getFull_name());
        holder.txtRealName.setSelected(true);
        holder.txtUserName.setSelected(true);
        holder.circleStory.setBorderColor(requireActivity.getColor(R.color.white));
        holder.circleStory.setBorderWidth(5);
        Glide.with(requireActivity).load(arrayList.get(position).getUser().getProfile_pic_url()).thumbnail(0.2f).into( holder.circleStory);
        holder.linearStoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoryAdapter.this.storyUserListInterface.storyUserListClick(position,StoryAdapter.this.arrayList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        ArrayList<StoryTray> arrayList = this.arrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearStoryLayout;
        CircleImageView circleStory;
        TextView txtUserName,txtRealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearStoryLayout = itemView.findViewById(R.id.linearStoryLayout);
            circleStory = itemView.findViewById(R.id.circleStory);
            txtRealName = itemView.findViewById(R.id.txtRealName);
            txtUserName = itemView.findViewById(R.id.txtUserName);
        }
    }
}
