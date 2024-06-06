package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI.stickyNodesList;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.PhotoVideoAdapter;

public class PhotoVideoActivity extends BaseActivity {

    RecyclerView recyclePhotoVideo;
    PhotoVideoActivity activity;
    PhotoVideoAdapter photoVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_video);

        activity = this;

        recyclePhotoVideo = findViewById(R.id.recyclePhotoVideo);

//        stickyNodesList.clear();

//        Log.d("=====Kenil", "onCreate1: " + rootPhotoVideoJson);
        if (stickyNodesList != null) {
            if (stickyNodesList.size() > 0) {
                recyclePhotoVideo.setLayoutManager(new GridLayoutManager(activity, 3));
                photoVideoAdapter = new PhotoVideoAdapter(activity, stickyNodesList);
                recyclePhotoVideo.setAdapter(photoVideoAdapter);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        stickyNodesList.clear();
    }
}