package com.cashloan.myapplication.igvideodownloader.adapter;

import static com.cashloan.myapplication.igvideodownloader.other.CommonClass.shareAllImage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.activity.VideoPlayerActivity;
import com.cashloan.myapplication.igvideodownloader.activity.WallpaperActivity;

import java.io.File;
import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    FragmentActivity requireActivity;
    ArrayList<File> imageFile;
    TextView txtImageWallpaper,txtImageSpeedShare,txtImageDelete;
    DeleteData ImageDeleteData;

    public PhotoAdapter(FragmentActivity requireActivity, ArrayList<File> imageFile,DeleteData ImageDeleteData) {
        this.requireActivity = requireActivity;
        this.imageFile = imageFile;
        this.ImageDeleteData = ImageDeleteData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(requireActivity).inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (!imageFile.isEmpty() && imageFile.get(position).getAbsolutePath() != null) {
            Glide.with(requireActivity).load(imageFile.get(position).getAbsolutePath()).into(holder.imgPhotoShow);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity, VideoPlayerActivity.class);
                intent.putExtra("from", imageFile.get(position).getAbsolutePath());
                intent.putExtra("lin", "");
                intent.putExtra("name",imageFile.get(position).getName());
                requireActivity.startActivity(intent);
            }
        });

       /* holder.imgDownloadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(requireActivity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = requireActivity.getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.photo_dailog, null);
                alertDialog.setView(view);

                txtImageWallpaper = view.findViewById(R.id.txtImageWallpaper);
                txtImageSpeedShare = view.findViewById(R.id.txtImageSpeedShare);
                txtImageDelete = view.findViewById(R.id.txtImageDelete);

                txtImageWallpaper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requireActivity.startActivity(new Intent(requireActivity, WallpaperActivity.class)
                                .putExtra("uriImg",imageFile.get(position).getAbsolutePath()));
                        alertDialog.dismiss();
                    }
                });

                txtImageSpeedShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareAllImage(requireActivity, imageFile.get(position).getAbsolutePath());
                    }
                });

                txtImageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog delete_dialog = new AlertDialog.Builder(requireActivity, R.style.MyTransparentBottomSheetDialogTheme).create();
                        LayoutInflater layoutInflater = requireActivity.getLayoutInflater();
                        View view1 = layoutInflater.inflate(R.layout.delete_layout, null);
                        delete_dialog.setView(view1);
                        delete_dialog.setCanceledOnTouchOutside(false);
                        TextView cancel = view1.findViewById(R.id.delete_cancel);
                        TextView delete_btn = view1.findViewById(R.id.delete_ok);

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete_dialog.dismiss();
                            }
                        });

                        delete_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageDeleteData.ImageDeleteClick(new File(imageFile.get(position).getAbsolutePath()), 0);
                                delete_dialog.dismiss();
                            }
                        });

                        delete_dialog.show();
                        Window window = delete_dialog.getWindow();
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        requireActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int screenWidth = displayMetrics.widthPixels;
                        int dialogWidth = (int) (screenWidth * 0.88);
                        window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                        window.setGravity(Gravity.CENTER);
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
                Window window = alertDialog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                requireActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.88);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            }
        });*/
    }

    public interface DeleteData {
        void ImageDeleteClick(File str, int i);
    }


    @Override
    public int getItemCount() {
        return imageFile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhotoShow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhotoShow = itemView.findViewById(R.id.imgPhotoShow);
        }
    }
}
