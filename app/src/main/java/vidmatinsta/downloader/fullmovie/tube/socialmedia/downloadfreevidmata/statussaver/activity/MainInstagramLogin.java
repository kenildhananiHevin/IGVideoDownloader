package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.SharedPref;


public class MainInstagramLogin extends BaseActivity {
    private MainInstagramLogin thisActivity;
    private String cooci;
    SwipeRefreshLayout mainRefresh;
    WebView mainWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_instagram_login);
        thisActivity = this;
        mainRefresh = findViewById(R.id.mainRefresh);
        mainWebView = findViewById(R.id.mainWebView);

        MainView();

        mainRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainView();
            }
        });

    }

    public void MainView() {
        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.clearCache(true);
        mainWebView.setWebViewClient(new WebviewWebclient());
        CookieSyncManager.createInstance(thisActivity);
        CookieManager.getInstance().removeAllCookie();
        mainWebView.loadUrl("https://www.instagram.com/accounts/login/");
        mainWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                mainRefresh.setRefreshing(newProgress != 100);
            }
        });
    }

    public String mainFindLoginOrNot(String str, String str2) {
        String cookie = CookieManager.getInstance().getCookie(str);
        if (cookie != null && !cookie.isEmpty()) {
            String[] split = cookie.split(";");
            for (String str3 : split) {
                if (str3.contains(str2)) {
                    return str3.split("=")[1];
                }
            }
        }
        return null;
    }

    class WebviewWebclient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            cooci = CookieManager.getInstance().getCookie(str);
            try {
                String mainFindLoginOrNot = mainFindLoginOrNot(str, "sessionid");
                String mainFindLoginOrNot2 = mainFindLoginOrNot(str, "csrftoken");
                String mainFindLoginOrNot3 = mainFindLoginOrNot(str, "ds_user_id");
                if (mainFindLoginOrNot != null && mainFindLoginOrNot2 != null && mainFindLoginOrNot3 != null) {
                    SharedPref.mainSharedPutString(thisActivity, SharedPref.COOKIES, cooci);
                    SharedPref.mainSharedPutString(thisActivity, SharedPref.CSRF, mainFindLoginOrNot2);
                    SharedPref.mainSharedPutString(thisActivity, SharedPref.SESSIONID, mainFindLoginOrNot);
                    SharedPref.mainSharedPutString(thisActivity, SharedPref.USERID, mainFindLoginOrNot3);
                    SharedPref.mainSharedPutBoolean(thisActivity, SharedPref.ISINSTALOGIN, true);
                    webView.destroy();
                    Intent intent = new Intent();
                    intent.putExtra("result", "result");
                    thisActivity.setResult(-1, intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
