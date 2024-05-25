package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.fragment;

import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass.IgVideoPathDirectory;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.PhotoAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;

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
    LinearLayout empty_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        imageFile = new ArrayList<>();

        recyclePhoto = view.findViewById(R.id.recyclePhoto);
        empty_list = view.findViewById(R.id.empty_list);

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
                    CommonClass.deleteFiles(list, CommonClass.REQUEST_PERM_DELETE_PHOTO, requireActivity(), b);
                } else {
                    pos = i;
                    File photofiles = str;
                    photofiles.delete();
                    removeAt(pos);
                    MediaScannerConnection.scanFile(requireActivity(), new String[]{photofiles.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
                }
            }
        });
        recyclePhoto.setAdapter(photoAdapter);

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

        if (imageFile.isEmpty()) {
            empty_list.setVisibility(View.VISIBLE);
        } else {
            empty_list.setVisibility(View.GONE);
        }

        photoAdapter.imageFile = imageFile;
        photoAdapter.notifyDataSetChanged();


    }

    private void removeAt(int pos) {
        if (photoAdapter != null) {
            photoAdapter.imageFile.remove(pos);
            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE_PHOTO && resultCode == -1) {
            removeAt(pos);
        }
    }
}