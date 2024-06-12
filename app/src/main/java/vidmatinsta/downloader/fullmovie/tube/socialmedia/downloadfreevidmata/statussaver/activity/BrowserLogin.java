package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI.a;
import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI.encrypt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


public class BrowserLogin extends BaseActivity {
    private BrowserLogin thisActivity;
    private String cooci;
    SwipeRefreshLayout mainRefresh;
    WebView mainWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_instagram_login);
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
        if (SharedPref.getInstance(thisActivity).mainSharedGetBoolean(thisActivity, SharedPref.ISINSTALOGIN)){
            
        }else {
            CookieManager.getInstance().removeAllCookie();
            mainWebView.setWebViewClient(new WebviewWebclient());
            
        }
        mainWebView.loadUrl(a(" j/lr6TYl4YNfKyXJTehUar8Yvax/ruvNnHF0vZU3HYg="));
        CookieSyncManager.getInstance().sync();
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
            return false;
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
                    mainWebView.setWebViewClient(null);
                    mainWebView.loadUrl(a(" j/lr6TYl4YNfKyXJTehUar8Yvax/ruvNnHF0vZU3HYg="));
                    recreate();
                    CookieSyncManager.getInstance().sync();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
