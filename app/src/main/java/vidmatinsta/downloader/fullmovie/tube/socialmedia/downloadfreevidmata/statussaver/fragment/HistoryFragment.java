package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.CustomViewPager;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HistoryFragment extends Fragment {

    TabLayout HistoryTabLayout;
    CustomViewPager HistoryViewPager;
    HistoryAdapter historyAdapter;
    VideoFragment videoFragment = new VideoFragment();
    PhotoFragment photoFragment = new PhotoFragment();
    MusicFragment musicFragment = new MusicFragment();
    private static final String PREF_NAME = "MyPrefsFile";
    private static final String selected_tab = "selected_tab";
    String languageCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");


        HistoryTabLayout = view.findViewById(R.id.HistoryTabLayout);
        HistoryViewPager = view.findViewById(R.id.HistoryViewPager);

        HistoryTabLayout.addTab(HistoryTabLayout.newTab().setText(R.string.video));
        HistoryTabLayout.addTab(HistoryTabLayout.newTab().setText(R.string.photo));
        HistoryTabLayout.addTab(HistoryTabLayout.newTab().setText(R.string.music));

        historyAdapter = new HistoryAdapter(requireActivity(), getChildFragmentManager(), HistoryTabLayout.getTabCount());
        historyAdapter.addFragment(videoFragment);
        historyAdapter.addFragment(photoFragment);
        historyAdapter.addFragment(musicFragment);
        HistoryViewPager.setAdapter(historyAdapter);
        HistoryViewPager.setOffscreenPageLimit(3);
        HistoryViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(HistoryTabLayout));

        HistoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                saveTabPosition(tab.getPosition());
                HistoryViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {

                } else if (tab.getPosition() == 1) {

                } else if (tab.getPosition() == 2) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        HistoryTabLayout.selectTab(HistoryTabLayout.getTabAt(0));

        return view;
    }

    public static class HistoryAdapter extends FragmentPagerAdapter {
        List<Fragment> list = new ArrayList<>();
        int tabCount;

        public HistoryAdapter(FragmentActivity activity, @NonNull FragmentManager fm, int tabCount) {
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
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
        editor.putInt(selected_tab, position);
        editor.apply();
    }

    private int getSavedTabPosition() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return prefs.getInt(selected_tab, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonClass.REQUEST_PERM_DELETE && resultCode == -1) {
            videoFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == CommonClass.REQUEST_PERM_DELETE_PHOTO && resultCode == -1) {
            photoFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == CommonClass.REQUEST_PERM_DELETE_AUDIO && resultCode == -1) {
            musicFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        String languageCode = preferences.getString("language_code", "en");
        if (!Objects.equals(this.languageCode, languageCode)) {
            requireActivity().recreate();
        }
    }
}