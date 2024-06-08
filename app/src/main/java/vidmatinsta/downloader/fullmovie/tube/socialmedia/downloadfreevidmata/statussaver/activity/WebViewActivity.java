package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Inject JavaScript to capture data
                webView.evaluateJavascript(
                        "(function() { " +
                                "    var pre = document.querySelector('pre'); " +
                                "    var json = pre ? pre.innerText : ''; " +
                                "    Android.handleData(json); " +
                                "})()",
                        null
                );
            }
        });
        // Adding a JavaScript interface to capture the data from the web page
        webView.addJavascriptInterface(new JavaScriptInterface(), "Android");

        webView.loadUrl("https://www.instagram.com/graphql/query/?query_hash=b3055c01b4b222b8a47dc12b090e4e64&variables=%7B%22shortcode%22:%22C7uKYmDu13M%22%7D");
    }

    private class JavaScriptInterface {
        @JavascriptInterface
        public void handleData(String data) {
            // This method will be called from JavaScript with the data from the web page
            Log.e("===Hits", "handleData: "+data );
            System.out.println("Received data from web page: " + data);
            // You can also update your UI or pass the data to other parts of your app
        }


    }
}
