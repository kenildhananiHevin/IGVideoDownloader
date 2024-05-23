package com.cashloan.myapplication.igvideodownloader.fragment;

import static com.cashloan.myapplication.igvideodownloader.other.CommonClass.IgVideoPathDirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.adapter.PhotoAdapter;
import com.cashloan.myapplication.igvideodownloader.adapter.VideoAdapter;
import com.cashloan.myapplication.igvideodownloader.other.CommonClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhotoFragment extends Fragment {

    RecyclerView recyclePhoto;
    ArrayList<File> imageFile;
    PhotoAdapter photoAdapter;
    int pos = -1;
    public static File photofiles;
    String languageCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_photo, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        imageFile = new ArrayList<>();

        recyclePhoto = view.findViewById(R.id.recyclePhoto);

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

        imageFile.clear();

        if (IgVideoPathDirectory.listFiles() != null) {
            for (File photofiles : IgVideoPathDirectory.listFiles()) {
                if (!photofiles.getName().endsWith(".mp4")) {
                    imageFile.add(photofiles);
                }
            }
        }


        recyclePhoto.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        photoAdapter = new PhotoAdapter(requireActivity(), imageFile, new PhotoAdapter.DeleteData() {
            @Override
            public void ImageDeleteClick(File str, int i) {
                if (Build.VERSION.SDK_INT >= 30) {
                    pos = i;
                    Intent b = new Intent();
                    b.putExtra("pos", i);
                    b.putExtra("flag", true);
                    List<File> list = new ArrayList<>();
                    list.add(str);
                    photofiles = str;
                    CommonClass.deleteFiles(list, CommonClass.REQUEST_PERM_DELETE, requireActivity(), b);
                } else {
                    File photofiles = str;
                    photofiles.delete();
                    removeAt(pos);
                }
            }
        });
        recyclePhoto.setAdapter(photoAdapter);
    }
    private void removeAt(int pos) {
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE && resultCode == -1) {
            removeAt(pos);
        }
    }
}