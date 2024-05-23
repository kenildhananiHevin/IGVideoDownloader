package com.cashloan.myapplication.igvideodownloader.fragment;

import static com.cashloan.myapplication.igvideodownloader.other.CommonClass.IgVideoPathDirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.adapter.VideoAdapter;
import com.cashloan.myapplication.igvideodownloader.other.CommonClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class VideoFragment extends Fragment {

    RecyclerView recycleVideo;
    ArrayList<File> videoFile;
    VideoAdapter videoAdapter;
    int pos = -1;
    public static File videofiles;
    String languageCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");


        videoFile = new ArrayList<>();

        recycleVideo = view.findViewById(R.id.recycleVideo);


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

        if (IgVideoPathDirectory.listFiles() != null) {
            for (File videofiles : IgVideoPathDirectory.listFiles()) {
                if (!videofiles.getName().endsWith(".jpg")) {
                    videoFile.add(videofiles);
                }
            }
        }

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
                    File videofiles = str;
                    videofiles.delete();
                    removeAt(pos);
                }
            }
        });
        recycleVideo.setAdapter(videoAdapter);
    }

    private void removeAt(int pos) {
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE && resultCode == -1) {
            removeAt(pos);
        }
    }
}