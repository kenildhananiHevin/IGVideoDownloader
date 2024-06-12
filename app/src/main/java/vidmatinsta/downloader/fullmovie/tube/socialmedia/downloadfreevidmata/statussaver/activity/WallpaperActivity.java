package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;

import java.io.IOException;

public class WallpaperActivity extends BaseActivity {
    ImageView imgWallPic, imgWallpaperBack, imgLockSelect, imgHomeSelect, imgBothSelect;
    WallpaperActivity activity;
    TextView txtApply, txtCancel, txtOk;
    LinearLayout llLock, llHome, llBoth;
    int position = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        activity = this;

        String imgWall = getIntent().getStringExtra("uriImg");


        

        imgWallPic = findViewById(R.id.imgWallPic);
        txtApply = findViewById(R.id.txtApply);
        imgWallpaperBack = findViewById(R.id.imgWallpaperBack);


        Glide.with(activity).load(imgWall).into(imgWallPic);

        imgWallpaperBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog wallpaperDialog = new AlertDialog.Builder(activity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.wallpaper_dailog, null);
                wallpaperDialog.setView(view);
                wallpaperDialog.setCanceledOnTouchOutside(false);

                txtCancel = view.findViewById(R.id.txtCancel);
                txtOk = view.findViewById(R.id.txtOk);
                llLock = view.findViewById(R.id.llLock);
                llHome = view.findViewById(R.id.llHome);
                llBoth = view.findViewById(R.id.llBoth);
                imgLockSelect = view.findViewById(R.id.imgLockSelect);
                imgHomeSelect = view.findViewById(R.id.imgHomeSelect);
                imgBothSelect = view.findViewById(R.id.imgBothSelect);

                imgBothSelect.setImageResource(R.drawable.ig_select);

                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wallpaperDialog.dismiss();
                    }
                });

                txtOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 1) {
                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);
                            Bitmap bitmap = ((BitmapDrawable) imgWallPic.getDrawable()).getBitmap();
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                                } else {
                                    wallpaperManager.setBitmap(bitmap);
                                }
                                
                                Toast.makeText(activity, getString(R.string.wallpaper_set), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(activity, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else if (position == 2) {
                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);
                            Bitmap bitmap = ((BitmapDrawable) imgWallPic.getDrawable()).getBitmap();
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                                } else {
                                    wallpaperManager.setBitmap(bitmap);
                                }
                                
                                Toast.makeText(activity, getString(R.string.wallpaper_set), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(activity, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else if (position == 3) {
                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(activity);
                            Bitmap bitmap = ((BitmapDrawable) imgWallPic.getDrawable()).getBitmap();
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
                                } else {
                                    wallpaperManager.setBitmap(bitmap);
                                }
                                
                                Toast.makeText(activity, getString(R.string.wallpaper_set), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(activity, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        wallpaperDialog.dismiss();
                    }
                });

                llHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position = 1;
                        imgHomeSelect.setImageResource(R.drawable.ig_select);
                        imgLockSelect.setImageResource(R.drawable.ig_unselect);
                        imgBothSelect.setImageResource(R.drawable.ig_unselect);
                    }
                });

                llLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position = 2;
                        imgLockSelect.setImageResource(R.drawable.ig_select);
                        imgHomeSelect.setImageResource(R.drawable.ig_unselect);
                        imgBothSelect.setImageResource(R.drawable.ig_unselect);
                    }
                });


                llBoth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position = 3;
                        imgLockSelect.setImageResource(R.drawable.ig_unselect);
                        imgHomeSelect.setImageResource(R.drawable.ig_unselect);
                        imgBothSelect.setImageResource(R.drawable.ig_select);
                    }
                });


                wallpaperDialog.show();
                Window window = wallpaperDialog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.83);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            }
        });
    }
}