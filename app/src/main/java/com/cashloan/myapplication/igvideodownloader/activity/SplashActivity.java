package com.cashloan.myapplication.igvideodownloader.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.other.LocaleHelper;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleLanguageChange();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("Language", 0);
                boolean prefsString = preferences.getBoolean("language_set", false);
                if (prefsString) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LanguageActivity.class).putExtra("from", true));
                    finish();
                }
            }
        },1500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleLanguageChange();
    }

    protected void handleLanguageChange() {
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String languageCode = preferences.getString("language_code", "en");
        LocaleHelper.setLocale(SplashActivity.this, languageCode);
    }
}