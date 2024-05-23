package com.cashloan.myapplication.igvideodownloader.fragment;

import static com.cashloan.myapplication.igvideodownloader.other.CommonClass.IgVideoPathDirectory;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.model.VisitedVideoPage;
import com.cashloan.myapplication.igvideodownloader.other.CommonClass;
import com.cashloan.myapplication.igvideodownloader.other.InstagramDownload;
import com.cashloan.myapplication.igvideodownloader.other.TouchableWebView;
import com.cashloan.myapplication.igvideodownloader.other.Utils;
import com.cashloan.myapplication.igvideodownloader.other.VideoContentSearch;


import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HomeFragment extends Fragment {
    EditText edtPasteLink;
    AlertDialog alertDialog;
    ProgressBar progressBar;
    TextView txtDownload,txtPaste,txtOpenInstagram;
    TouchableWebView page;
    private SSLSocketFactory defaultSSLSF;
    String languageCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences preferences = requireActivity().getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");


        edtPasteLink = view.findViewById(R.id.edtPasteLink);
        page = view.findViewById(R.id.page);
        txtDownload = view.findViewById(R.id.txtDownload);
        txtPaste = view.findViewById(R.id.txtPaste);
        txtOpenInstagram = view.findViewById(R.id.txtOpenInstagram);

        txtOpenInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.OpenApp(requireActivity(), "com.instagram.android");
            }
        });

        txtPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                edtPasteLink.setText(clipboardManager.getText());
            }
        });

        txtDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IgVideoPathDirectory.exists()) {
                    IgVideoPathDirectory.mkdirs();
                }
                Log.d("TAG", "rundfg: " + edtPasteLink.getText().toString());
                DownloadClick(edtPasteLink.getText().toString());
                create_progress();
            }
        });

        return view;
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

    String foundLink = "";


    public void DownloadClick(String url) {
        VideoContentSearch.firstJpegFound = false;
        VideoContentSearch.isVideo = false;
        setupDownloaderSettings(new VideoFoundCallback() {
            @Override
            public void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                Log.d("TAG", "onVideoFoundss: " + type);
                Log.d("TAG", "onVideoFoundss1: " + link);
                Log.d("TAG", "onVideoFoundss2: " + name);
                if (!foundLink.equals(link)) {
                    foundLink = link;
                    startDownload(link, requireActivity(), System.currentTimeMillis() + "." + type, alertDialog, edtPasteLink.getText().toString(),name);
                }
            }
        });
        page.loadUrl(url);
    }


    private void create_progress() {
        alertDialog = new AlertDialog.Builder(requireActivity(), R.style.MyTransparentBottomSheetDialogTheme).create();
        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.pogress_dailog, null);
        alertDialog.setView(view1);
        alertDialog.setCanceledOnTouchOutside(false);
        progressBar = view1.findViewById(R.id.progressBar);

        alertDialog.show();
        Window window = alertDialog.getWindow();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int dialogWidth = (int) (screenWidth * 0.88);
        window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }


    interface VideoFoundCallback {
        void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio);
    }

    private void setupDownloaderSettings(final VideoFoundCallback callback) {
        defaultSSLSF = HttpsURLConnection.getDefaultSSLSocketFactory();
        page.getSettings().setJavaScriptEnabled(true);
        page.getSettings().setDomStorageEnabled(true);
        page.getSettings().setAllowUniversalAccessFromFileURLs(true);
        page.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        page.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(final WebView webview, final String url, Bitmap favicon) {
                super.onPageStarted(webview, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(final WebView view, final String url) {
                final String viewUrl = view.getUrl();
                final String title = view.getTitle();

                new VideoContentSearch(requireActivity(), url, viewUrl, title) {
                    @Override
                    public void onStartInspectingURL() {
                        Utils.disableSSLCertificateChecking();
                    }

                    @Override
                    public void onFinishedInspectingURL(boolean finishedAll) {
                        HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSF);
                    }

                    @Override
                    public void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                        Log.e("TAG", "onVideoFound: " + link);
                        callback.onVideoFound(size, type, link, name, page, chunked, website, audio);
                    }
                }.start();
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("VDDebug", "Ads detected: " + url);
                return super.shouldInterceptRequest(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }


        });
        page.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("TAG", "onVideoFound 1: " + view.getUrl());
                VisitedVideoPage videoVisitPage = new VisitedVideoPage();
                videoVisitPage.title = title;
                videoVisitPage.link = view.getUrl();
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                return Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
            }

        });
    }

    public static void startDownload(String paths, FragmentActivity instagram, String name, AlertDialog alertDialog, String edtPaste, String nameIns) {
        new InstagramDownload(instagram, name, alertDialog, edtPaste,nameIns).execute(paths);
    }

}