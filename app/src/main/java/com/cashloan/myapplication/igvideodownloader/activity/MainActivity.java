package com.cashloan.myapplication.igvideodownloader.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.fragment.HistoryFragment;
import com.cashloan.myapplication.igvideodownloader.fragment.HomeFragment;
import com.cashloan.myapplication.igvideodownloader.fragment.SettingFragment;
import com.cashloan.myapplication.igvideodownloader.model.CustomViewPager;
import com.cashloan.myapplication.igvideodownloader.other.CommonClass;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity {
    TextView txtInSaver;
    TabLayout tabLayout;
    CustomViewPager viewPager;
    MainActivity activity;
    MyAdapter adapter;
    private static final String PREF_NAME = "MyPrefsFile";
    private static final String selected_tab = "selected_tab";
    int savedTabPosition = 0;
    HomeFragment homeFragment = new HomeFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    SettingFragment settingFragment = new SettingFragment();
    String[] permissions;
    ImageView imgInstagramLogin;
    String languageCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        savedTabPosition = getSavedTabPosition();

        SharedPreferences preferences = getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");


        txtInSaver = findViewById(R.id.txtInSaver);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager);
        imgInstagramLogin = findViewById(R.id.imgInstagramLogin);

        txtInSaver.setSelected(true);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ig_history).setText(R.string.history));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ig_home).setText(R.string.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ig_setting).setText(R.string.setting));


        adapter = new MyAdapter(activity, getSupportFragmentManager(), tabLayout.getTabCount());
        adapter.addFragment(historyFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(settingFragment);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                saveTabPosition(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.ig_select_history);
                    imgInstagramLogin.setVisibility(View.VISIBLE);
                } else if (tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.ig_home);
                    imgInstagramLogin.setVisibility(View.GONE);
                } else if (tab.getPosition() == 2) {
                    tab.setIcon(R.drawable.ig_select_setting);
                    imgInstagramLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.ig_history);
                } else if (tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.ig_home);
                } else if (tab.getPosition() == 2) {
                    tab.setIcon(R.drawable.ig_setting);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.selectTab(tabLayout.getTabAt(1));
        tabLayout.getTabAt(1).setIcon(R.drawable.ig_home);


        imgInstagramLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.OpenApp(activity, "com.instagram.android");
            }
        });


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO

            };
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_IMAGES) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_VIDEO)) {
                    showSnackbar(activity, findViewById(R.id.settingA), R.string.please_allow, R.string.allow, new Runnable() {
                        @Override
                        public void run() {
                            openSettingsDialog();
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(activity, permissions, 101);
                }
            }
        } else {
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showSnackbar(activity, findViewById(R.id.settingA), R.string.please_allow, R.string.allow, new Runnable() {
                        @Override
                        public void run() {
                            openSettingsDialog();
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(activity, permissions, 101);
                }
            }
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        List<Fragment> list = new ArrayList<>();
        int tabCount;

        public MyAdapter(MainActivity activity, @NonNull FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        public void addFragment(Fragment fragment) {
            list.add(fragment);
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    private void saveTabPosition(int position) {
        SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
        editor.putInt(selected_tab, position);
        editor.apply();
    }

    private int getSavedTabPosition() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return prefs.getInt(selected_tab, 0);
    }

    public void showSnackbar(Context context, View view, int text, int dismissText, final Runnable onDismiss) {
        Snackbar snackbar = Snackbar.make(
                view,
                context.getString(text),
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setBackgroundTint(context.getColor(R.color.white));
        snackbar.setTextColor(context.getColor(R.color.black));
        snackbar.setActionTextColor(context.getColor(R.color.app_color));
        snackbar.setAction(context.getString(dismissText), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                if (onDismiss != null) {
                    onDismiss.run();
                }
            }
        });
        snackbar.show();
    }

    private void openSettingsDialog() {
        final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
        final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":settings:show_fragment_args";
        final String EXTRA_SYSTEM_ALERT_WINDOW = "permission_settings";

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FRAGMENT_ARG_KEY, EXTRA_SYSTEM_ALERT_WINDOW);

        Uri uri = Uri.fromParts("package", getPackageName(), null);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(uri)
                .putExtra(EXTRA_FRAGMENT_ARG_KEY, EXTRA_SYSTEM_ALERT_WINDOW)
                .putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle);
        startActivityForResult(intent, 102);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE && resultCode == -1) {
            historyFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String languageCode = preferences.getString("language_code", "en");
        if (!Objects.equals(this.languageCode, languageCode)) {
            recreate();
        }
    }
}