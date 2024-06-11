package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.VideoAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;


public class VideoFragment extends Fragment {

    RecyclerView recycleVideo;
    ArrayList<File> videoFile;
    VideoAdapter videoAdapter;
    int pos = -1;
    public static File videofiles;
    String languageCode;
    LinearLayout empty_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        videoFile = new ArrayList<>();
        Log.d("TAG", "videofiles: " + videoFile.size());

        recycleVideo = view.findViewById(R.id.recycleVideo);
        empty_list = view.findViewById(R.id.empty_list);

        Log.d("TAG", "videofiles1: " + videoFile.size());

        recycleVideo.setLayoutManager(new LinearLayoutManager(requireActivity()));
        videoAdapter = new VideoAdapter(requireActivity(), videoFile, new VideoAdapter.DeleteData() {
            @Override
            public void VideoDeleteClick(File str, int i) {
                if (Build.VERSION.SDK_INT >= 30) {
                    pos = i;
                    Intent b = new Intent();
                    b.putExtra("pos", i);
                    b.putExtra("flag", true);
                    List<File> list = new ArrayList<>();
                    list.add(str);
                    videofiles = str;
                    CommonClass.deleteFiles(list, CommonClass.REQUEST_PERM_DELETE, requireActivity(), b);
                } else {
                    pos = i;
                    File videofiles = str;
                    videofiles.delete();
                    removeAt(pos);
                    MediaScannerConnection.scanFile(requireActivity(), new String[]{videofiles.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
                }
            }
        });
        recycleVideo.setAdapter(videoAdapter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        String languageCode = preferences.getString("language_code", "en");
        if (!Objects.equals(this.languageCode, languageCode)) {
            requireActivity().recreate();
        }

        videoFile.clear();

        if (CommonClass.IgVideoPathDirectory.listFiles() != null) {
            for (File videofiles : CommonClass.IgVideoPathDirectory.listFiles()) {
                if (!videofiles.getName().endsWith(".jpg") && !videofiles.getName().endsWith(".jpeg") && !videofiles.getName().endsWith(".heic") && !videofiles.getName().endsWith(".png") && !videofiles.getName().endsWith(".webp")) {
                    videoFile.add(videofiles);
                    Log.d("TAG", "videofiles2: " + videoFile.size());
                }
            }
        }

        if (videoFile.isEmpty()) {
            empty_list.setVisibility(View.VISIBLE);
        } else {
            empty_list.setVisibility(View.GONE);
        }

        videoAdapter.videoFile = videoFile;
        videoAdapter.notifyDataSetChanged();
    }

    private void removeAt(int pos) {
        if (videoAdapter != null) {
            videoAdapter.videoFile.remove(pos);
            videoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE && resultCode == -1) {
            removeAt(pos);
        }
    }
}