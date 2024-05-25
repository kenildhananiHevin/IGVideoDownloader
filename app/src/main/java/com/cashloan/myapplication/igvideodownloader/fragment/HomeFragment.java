package com.cashloan.myapplication.igvideodownloader.fragment;

import static com.cashloan.myapplication.igvideodownloader.other.CommonClass.IgVideoPathDirectory;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.igvideodownloader.R;
import com.cashloan.myapplication.igvideodownloader.adapter.StoriesViewAdapter;
import com.cashloan.myapplication.igvideodownloader.adapter.StoryAdapter;
import com.cashloan.myapplication.igvideodownloader.api.CommonClassForAPI;
import com.cashloan.myapplication.igvideodownloader.interfaces.StoryUserListInterface;
import com.cashloan.myapplication.igvideodownloader.model.story.InstagramStory;
import com.cashloan.myapplication.igvideodownloader.model.story.StoryFullDetail;
import com.cashloan.myapplication.igvideodownloader.model.story.StoryTray;
import com.cashloan.myapplication.igvideodownloader.model.VisitedVideoPage;
import com.cashloan.myapplication.igvideodownloader.other.CommonClass;
import com.cashloan.myapplication.igvideodownloader.other.InstagramDownload;
import com.cashloan.myapplication.igvideodownloader.other.SharedPref;
import com.cashloan.myapplication.igvideodownloader.other.TouchableWebView;
import com.cashloan.myapplication.igvideodownloader.other.Utils;
import com.cashloan.myapplication.igvideodownloader.other.VideoContentSearch;

import java.util.ArrayList;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import io.reactivex.observers.DisposableObserver;

public class HomeFragment extends Fragment {
    EditText edtPasteLink;
    AlertDialog alertDialog;
    ProgressBar progressBar;
    TextView txtDownload, txtPaste, txtOpenInstagram;
    TouchableWebView page;
    private SSLSocketFactory defaultSSLSF;
    String languageCode;
    String[] permissions;
    RecyclerView recycleRVUserList,recycleRVStories;
    StoryAdapter storyAdapter;
    StoriesViewAdapter storiesViewAdapter;
    ProgressBar progressLoading;
    private CommonClassForAPI CallInstaApi;

    DisposableObserver<StoryFullDetail> storyDetailsMainObserver = new DisposableObserver<StoryFullDetail>() {
        @Override
        public void onNext(StoryFullDetail fullDetailModel) {
            recycleRVUserList.setVisibility(View.VISIBLE);
            progressLoading.setVisibility(View.GONE);
            try {
                if (fullDetailModel.getReel_feed() != null) {
                    storiesViewAdapter = new StoriesViewAdapter(requireActivity(), fullDetailModel.getReel_feed().get(0).getItems(), requireActivity());
                    recycleRVStories.setAdapter(storiesViewAdapter);
                    storiesViewAdapter.notifyDataSetChanged();
                } else {
                    storiesViewAdapter = new StoriesViewAdapter(requireActivity(), new ArrayList<>(), requireActivity());
                    recycleRVStories.setAdapter(storiesViewAdapter);
                    storiesViewAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.d("TAG", "onNextsssss: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            progressLoading.setVisibility(View.GONE);
            Log.d("TAG", "onErrorssss: " + e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            progressLoading.setVisibility(View.GONE);
        }
    };

    private DisposableObserver<InstagramStory> storyObserver = new DisposableObserver<InstagramStory>() {
        @Override
        public void onNext(InstagramStory instagramStory) {
            recycleRVUserList.setVisibility(View.VISIBLE);
            progressLoading.setVisibility(View.GONE);
            try {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < instagramStory.getTray().size(); i++) {
                    try {
                        if (instagramStory.getTray().get(i).getUser().getFull_name() != null) {
                            arrayList.add(instagramStory.getTray().get(i));
                        }
                    } catch (Exception unused) {
                    }
                }
                storyAdapter = new StoryAdapter(requireActivity(), arrayList, new StoryUserListInterface() {
                    @Override
                    public void storyUserListClick(int i, StoryTray storyTray) {
                        callStoriesDetailApi(String.valueOf(storyTray.getUser().getPk()));
                    }
                });
                recycleRVUserList.setAdapter(storyAdapter);
            } catch (Exception e) {
                Log.d("TAG", "onNext: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            progressLoading.setVisibility(View.GONE);
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            progressLoading.setVisibility(View.GONE);
        }
    };

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
        recycleRVUserList = view.findViewById(R.id.recycleRVUserList);
        progressLoading = view.findViewById(R.id.progressLoading);
       recycleRVStories = view.findViewById(R.id.recycleRVStories);

        CallInstaApi = CommonClassForAPI.getInstance();


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

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    permissions = new String[]{Manifest.permission.POST_NOTIFICATIONS,
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO

                    };
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                       return;
                    }
                } else {
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }

                if (edtPasteLink.getText().toString().isEmpty()) {
                    Toast.makeText(requireActivity(), getString(R.string.paste_link), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edtPasteLink.getText().toString().contains("instagram.com")){
                    Toast.makeText(requireActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtPasteLink.getText().toString().contains("stories")){
                    Log.d("TAG", "edtPasteLinks1: "+edtPasteLink.getText().toString());
                    Toast.makeText(requireActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!IgVideoPathDirectory.exists()) {
                    IgVideoPathDirectory.mkdirs();
                }
                Log.d("TAG", "rundfg: " + edtPasteLink.getText().toString());
                DownloadClick(edtPasteLink.getText().toString());
                create_progress();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 2);
        recycleRVStories.setLayoutManager(gridLayoutManager);
        recycleRVStories.setNestedScrollingEnabled(false);
        recycleRVStories.setHasFixedSize(false);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        
        return view;
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
                        startDownload(link, requireActivity(), System.currentTimeMillis() + "." + type, alertDialog, edtPasteLink.getText().toString(), name);
                }
            }
        },url);
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

    private void setupDownloaderSettings(final VideoFoundCallback callback, String mainUrl) {
        defaultSSLSF = HttpsURLConnection.getDefaultSSLSocketFactory();
        page.getSettings().setJavaScriptEnabled(true);
        page.getSettings().setDomStorageEnabled(true);
        page.getSettings().setAllowUniversalAccessFromFileURLs(true);
        page.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        page.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("TAG", "onVideoFoundss11: "+view);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(final WebView webview, final String url, Bitmap favicon) {
                Log.d("TAG", "onVideoFoundss10: "+url);
                super.onPageStarted(webview, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("TAG", "onVideoFoundss9: "+url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(final WebView view, final String url) {
                Log.d("TAG", "onVideoFoundss8: "+url);
                final String viewUrl = view.getUrl();
                final String title = view.getTitle();

                try {
                    new VideoContentSearch(requireActivity(), url, viewUrl, title,mainUrl) {
                        @Override
                        public void onStartInspectingURL() {
                            Log.d("TAG", "onVideoFoundss6: "+viewUrl);
                            Utils.disableSSLCertificateChecking();
                        }

                        @Override
                        public void onFinishedInspectingURL(boolean finishedAll) {
                            Log.d("TAG", "onVideoFoundss5: "+viewUrl);
                            HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSF);
                        }

                        @Override
                        public void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                            Log.e("TAG", "onVideoFoundss7: " + link);
                            callback.onVideoFound(size, type, link, name, page, chunked, website, audio);
                        }
                    }.start();
                }catch (Exception e){
                    Log.d("TAG", "onVideoFoundss4: "+e.getMessage());
                }
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
                Log.d("TAG", "onProgressChanged: "+newProgress);
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
        new InstagramDownload(instagram, name, alertDialog, edtPaste, nameIns).execute(paths);
    }


    private void callStoriesDetailApi(String str) {
        try {
            if (!new CommonClass(requireActivity()).isNetworkAvailable()) {
                Toast.makeText(requireActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (CallInstaApi != null) {
                progressLoading.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = CallInstaApi;
                DisposableObserver<StoryFullDetail> disposableObserver = storyDetailsMainObserver;
                commonClassForAPI2.getFullDetailFeed(disposableObserver, str, "ds_user_id=" + SharedPref.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPref.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callStoriesApi() {
        try {
            if (!new CommonClass(requireActivity()).isNetworkAvailable()) {
                Toast.makeText(requireActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (CallInstaApi != null) {
                progressLoading.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = CallInstaApi;
                DisposableObserver<InstagramStory> disposableObserver = storyObserver;
                commonClassForAPI2.getStories(disposableObserver, "ds_user_id=" + SharedPref.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPref.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        GridLayoutManager userGridLayoutManager = new GridLayoutManager(requireActivity(), 1);
        recycleRVUserList.setLayoutManager(userGridLayoutManager);
        recycleRVUserList.setNestedScrollingEnabled(false);
        userGridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        if (SharedPref.getInstance(requireActivity()).mainSharedGetBoolean(requireActivity(), SharedPref.ISINSTALOGIN)) {
            callStoriesApi();
        }
    }

}