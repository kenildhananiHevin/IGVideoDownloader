package com.cashloan.myapplication.igvideodownloader.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.activity.LanguageActivity;

import java.util.Objects;


public class SettingFragment extends Fragment {

    LinearLayout linearLanguage, linearRate, linearShare, linearPrivacy;
    TextView txtLanguage, txtRate, txtShare, txtPrivacy;
    String languageCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        linearLanguage = view.findViewById(R.id.linearLanguage);
        linearRate = view.findViewById(R.id.linearRate);
        linearShare = view.findViewById(R.id.linearShare);
        linearPrivacy = view.findViewById(R.id.linearPrivacy);
        txtLanguage = view.findViewById(R.id.txtLanguage);
        txtRate = view.findViewById(R.id.txtRate);
        txtShare = view.findViewById(R.id.txtShare);
        txtPrivacy = view.findViewById(R.id.txtPrivacy);

        txtLanguage.setSelected(true);
        txtRate.setSelected(true);
        txtShare.setSelected(true);
        txtPrivacy.setSelected(true);

        click();

        return view;
    }

    public void click() {
        linearLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), LanguageActivity.class));
            }
        });

        linearRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateUs(requireActivity());
            }
        });

        linearPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyPolicy(requireActivity());
            }
        });

        linearShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp(requireActivity());
            }
        });
    }

    //Rate
    public void rateUs(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
    }

    //Share
    public void shareApp(Activity activity) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            String shareMessage = "\nPlease try this application\n\n"
                    + "https://play.google.com/store/apps/details?id="
                    + activity.getPackageName() + "\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception ignored) {
        }
    }

    //Policy
    public void privacyPolicy(Activity activity) {
        try {
            Intent browserIntent;
            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wisdomvalleey.blogspot.com/2023/02/wisdom-valley-privacy.html?m=1"));
            startActivity(browserIntent);
        } catch (Exception ignored) {
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