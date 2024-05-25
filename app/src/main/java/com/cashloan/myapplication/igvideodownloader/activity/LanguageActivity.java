package com.cashloan.myapplication.igvideodownloader.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.adapter.LanguageAdapter;
import com.cashloan.myapplication.igvideodownloader.model.Languages;
import com.cashloan.myapplication.igvideodownloader.other.LocaleHelper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LanguageActivity extends BaseActivity {

    RecyclerView recycleLanguageList;
    public static ImageView back, imgDone;
    LanguageAdapter languageAdapter;
    ArrayList<Languages> languages = new ArrayList<>();
    boolean backClick = false;
    boolean intent;
    boolean prefsStrings;
    ProgressBar progressIg;
    LanguageActivity activity;
    TextView txtLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        activity = this;
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String prefsString = preferences.getString("language_code", "en");
        intent = getIntent().getBooleanExtra("from", false);

        recycleLanguageList = findViewById(R.id.recycleLanguageList);
        back = findViewById(R.id.back);
        imgDone = findViewById(R.id.imgDone);
        progressIg = findViewById(R.id.progressIg);
        txtLanguage = findViewById(R.id.txtLanguage);

        txtLanguage.setSelected(true);

        Handler handler = new Handler(Looper.getMainLooper());
        long delay = 1000L + new Random().nextInt(500);
        handler.postDelayed(() -> {
            progressIg.setVisibility(View.GONE);
            recycleLanguageList.setVisibility(View.VISIBLE);
        }, delay);

        languages.add(new Languages(R.drawable.ig_english, getString(R.string.english), "en", "Default"));
        languages.add(new Languages(R.drawable.ig_hindi, getString(R.string.hindi), "hi", "हिंदी"));
        languages.add(new Languages(R.drawable.ig_spanish, getString(R.string.spanish), "es", "Español"));
        languages.add(new Languages(R.drawable.ig_french, getString(R.string.french), "fr", "Français"));
        languages.add(new Languages(R.drawable.ig_russian, getString(R.string.russian), "ru", "Русский"));
        languages.add(new Languages(R.drawable.ig_portuguese, getString(R.string.portuguese), "pt", "Português"));
        languages.add(new Languages(R.drawable.ig_german, getString(R.string.german), "de", "Deutsch"));
        languages.add(new Languages(R.drawable.ig_indonesian, getString(R.string.indonesian), "in", "Indonesia"));
        languages.add(new Languages(R.drawable.ig_chinese, getString(R.string.chinese), "zh","中國人"));
        languages.add(new Languages(R.drawable.ig_filipino, getString(R.string.filipino), "fil","Filipino"));
        languages.add(new Languages(R.drawable.ig_italian, getString(R.string.italian), "it","Italiana"));
        languages.add(new Languages(R.drawable.ig_afrikaans, getString(R.string.afrikaans), "af","Afrikaans"));
        languages.add(new Languages(R.drawable.ig_japanese, getString(R.string.japanese), "ja","日本語"));
        languages.add(new Languages(R.drawable.ig_korean, getString(R.string.korean), "ko","한국인"));
        languages.add(new Languages(R.drawable.ig_polish, getString(R.string.polish), "pl","Polski"));
        languages.add(new Languages(R.drawable.ig_thai, getString(R.string.thai), "th","แบบไทย"));
        languages.add(new Languages(R.drawable.ig_turkish, getString(R.string.turkish), "tr","Türkçe"));
        languages.add(new Languages(R.drawable.ig_ukrainian, getString(R.string.ukrainian), "uk","українська"));
        languages.add(new Languages(R.drawable.ig_vietnamese, getString(R.string.vietnamese), "vi","Tiếng Việt"));

        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageAdapter.selected != -1) {
                    Languages languages = LanguageActivity.this.languages.get(languageAdapter.selected);
                    LocaleHelper.setLocale(LanguageActivity.this, languages.getLanguageCode());
                    /*LocaleHelper.setLocale(LanguageActivity.this, languages.getLanguageCode());*/

                    SharedPreferences prefsString = getSharedPreferences("Language", 0);
                    SharedPreferences.Editor editor = prefsString.edit();

                    String languageCode = languages.getLanguageCode();
                    editor.putString("language_code", languageCode);
                    editor.putBoolean("language_set", true);
                    editor.apply();
                    if (intent) {
                        startActivity(new Intent(activity, MainActivity.class));
                        finish();
                    } else {
                        if (!prefsString.equals(languages.getLanguageCode())) {
                            finish();
                        } else {
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(activity, R.string.select_language, Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (intent) {
//            imgDone.setVisibility(View.GONE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imgDone.setVisibility(View.VISIBLE);
            }
        }

        recycleLanguageList.setLayoutManager(new LinearLayoutManager(this));
        languageAdapter = new LanguageAdapter(activity, languages);
        recycleLanguageList.setAdapter(languageAdapter);

        SharedPreferences preference = getSharedPreferences("Language", 0);
        prefsStrings = preference.getBoolean("language_set", false);
        if (prefsStrings) {
            for (int i = 0; i < languages.size(); i++) {
                if (Objects.equals(languages.get(i).getLanguageCode(), prefsString)) {
                    languageAdapter.selected = i;
                    languageAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (prefsStrings) {
            finish();
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    backClick = false;
                }
            }, 2000);
            if (backClick) {
                finishAffinity();
            } else {
                backClick = true;
                Toast.makeText(activity, R.string.press, Toast.LENGTH_SHORT).show();
            }
        }
    }
}