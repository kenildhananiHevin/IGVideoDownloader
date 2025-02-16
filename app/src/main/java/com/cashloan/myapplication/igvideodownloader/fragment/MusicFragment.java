package com.cashloan.myapplication.igvideodownloader.fragment;

import static com.cashloan.myapplication.igvideodownloader.other.CommonClass.IgAudioPathDirectory;
import static com.cashloan.myapplication.igvideodownloader.other.CommonClass.IgVideoPathDirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.adapter.MusicAdapter;
import com.cashloan.myapplication.igvideodownloader.adapter.PhotoAdapter;
import com.cashloan.myapplication.igvideodownloader.other.CommonClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MusicFragment extends Fragment {
    RecyclerView audioRecycle;
    ArrayList<File> musicFile;
    MusicAdapter musicAdapter;
    int pos = -1;
    public static File musicfiles;
    String languageCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");


        musicFile = new ArrayList<>();

        audioRecycle = view.findViewById(R.id.audioRecycle);

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

        musicFile.clear();

        if (IgAudioPathDirectory.listFiles() != null) {
            for (File file : IgAudioPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    musicFile.add(file);
                }
            }
        }

        audioRecycle.setLayoutManager(new LinearLayoutManager(requireActivity()));
        musicAdapter = new MusicAdapter(requireActivity(), musicFile, new MusicAdapter.DeleteMusic() {
            @Override
            public void MusicDeleteClick(File str, int i) {
                if (Build.VERSION.SDK_INT >= 30) {
                    pos = i;
                    Intent b = new Intent();
                    b.putExtra("pos", i);
                    b.putExtra("flag", true);
                    List<File> list = new ArrayList<>();
                    list.add(str);
                    musicfiles = str;
                    CommonClass.deleteFiles(list, CommonClass.REQUEST_PERM_DELETE, requireActivity(), b);
                } else {
                    File musicfiles = str;
                    musicfiles.delete();
                    removeAt(pos);
                }
            }
        });
        audioRecycle.setAdapter(musicAdapter);
    }

    private void removeAt(int pos) {
        musicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE && resultCode == -1) {
            removeAt(pos);
        }
    }

}