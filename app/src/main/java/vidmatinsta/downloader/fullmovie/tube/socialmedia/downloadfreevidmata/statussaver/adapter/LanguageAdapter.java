package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.LanguageActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.Languages;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    LanguageActivity activity;
    ArrayList<Languages> languages;
    public int selected = -1;

    public LanguageAdapter(LanguageActivity activity, ArrayList<Languages> languages) {
        this.activity = activity;
        this.languages = languages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.language_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.ViewHolder holder, int position) {
        Languages languages1 = languages.get(position);
        holder.imgFlag.setImageResource(languages1.getImage());
        holder.txtLanguageName.setText(languages1.getLanguageName());
        holder.txtLanguageAll.setText("(" + languages1.getAllName() + ")");

        if (selected == position) {
            holder.imgLanguageSelect.setImageResource(R.drawable.ig_select);
        } else {
            holder.imgLanguageSelect.setImageResource(R.drawable.ig_unselect);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = position;
                LanguageActivity.imgDone.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFlag, imgLanguageSelect;
        TextView txtLanguageName, txtLanguageAll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.imgFlag);
            imgLanguageSelect = itemView.findViewById(R.id.imgLanguageSelect);
            txtLanguageName = itemView.findViewById(R.id.txtLanguageName);
            txtLanguageAll = itemView.findViewById(R.id.txtLanguageAll);

        }
    }
}
