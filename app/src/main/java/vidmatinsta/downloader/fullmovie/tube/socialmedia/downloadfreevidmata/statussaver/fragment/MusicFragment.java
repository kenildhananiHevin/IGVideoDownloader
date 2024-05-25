package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.fragment;

import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass.IgAudioPathDirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.MusicAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;

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
    LinearLayout empty_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");


        musicFile = new ArrayList<>();

        audioRecycle = view.findViewById(R.id.audioRecycle);
        empty_list = view.findViewById(R.id.empty_list);


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
                    CommonClass.deleteFiles(list, CommonClass.REQUEST_PERM_DELETE_AUDIO, requireActivity(), b);
                } else {
                    pos = i;
                    File musicfiles = str;
                    musicfiles.delete();
                    removeAt(pos);
                    MediaScannerConnection.scanFile(requireActivity(), new String[]{musicfiles.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
                }
            }
        });
        audioRecycle.setAdapter(musicAdapter);

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

        if (musicFile.isEmpty()) {
            empty_list.setVisibility(View.VISIBLE);
        } else {
            empty_list.setVisibility(View.GONE);
        }

        musicAdapter.musicFile = musicFile;
        musicAdapter.notifyDataSetChanged();


    }

    private void removeAt(int pos) {
        if (musicAdapter != null) {
            musicAdapter.musicFile.remove(pos);
            musicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE_AUDIO && resultCode == -1) {
            removeAt(pos);
        }
    }

}