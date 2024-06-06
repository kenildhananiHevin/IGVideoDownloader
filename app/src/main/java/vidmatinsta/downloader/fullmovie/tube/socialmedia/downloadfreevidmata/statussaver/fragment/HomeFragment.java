package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
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

import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import io.reactivex.observers.DisposableObserver;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.MainInstagramLogin;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.PhotoVideoActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.StoryAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.interfaces.StoryUserListInterface;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.VisitedVideoPage;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video.Node;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryModel;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryTray;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.InstagramDownload;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.SharedPref;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.TouchableWebView;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.Utils;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.VideoContentSearch;

public class HomeFragment extends Fragment {
    public static EditText edtPasteLink;
    AlertDialog alertDialog;
    ProgressBar progressBar;
    TextView txtDownload, txtPaste, txtOpenInstagram;
    TouchableWebView page;
    private SSLSocketFactory defaultSSLSF;
    String languageCode;
    String[] permissions;
    RecyclerView recycleRVUserList;
    StoryAdapter storyAdapter;
    ProgressBar progressStory;
    private CommonClassStoryForAPI CallInstaApi;
    private String mMediaTypeImage = null;
    private String mMediaTypeVideo = null;
    private String tempUrl = "";

    private DisposableObserver<StoryModel> storyObserver = new DisposableObserver<StoryModel>() {
        @Override
        public void onNext(StoryModel instagramStory) {
            recycleRVUserList.setVisibility(View.VISIBLE);
            progressStory.setVisibility(View.VISIBLE);
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
            e.printStackTrace();
            progressStory.setVisibility(View.GONE);
        }

        @Override
        public void onComplete() {
            progressStory.setVisibility(View.GONE);
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
        progressStory = view.findViewById(R.id.progressStory);

        CallInstaApi = CommonClassStoryForAPI.getInstance();


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
                    permissions = new String[]{Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO
                    };
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
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
                if (!edtPasteLink.getText().toString().contains("instagram.com")) {
                    Toast.makeText(requireActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtPasteLink.getText().toString().contains("stories")) {
                    Log.d("TAG", "edtPasteLinks1: " + edtPasteLink.getText().toString());
                    Toast.makeText(requireActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CommonClass.IgVideoPathDirectory.exists()) {
                    CommonClass.IgVideoPathDirectory.mkdirs();
                }
                Log.d("TAG", "rundfg: " + edtPasteLink.getText().toString());

                if (SharedPref.getInstance(requireActivity()).mainSharedGetBoolean(requireActivity(), SharedPref.ISINSTALOGIN)) {
                    loginDownload(edtPasteLink.getText().toString());
                    create_progress();
                    return;
                }
                DownloadClick(edtPasteLink.getText().toString());
                create_progress();
            }
        });


        return view;
    }

    private void loginDownload(String loginDownloads) {
        String str2;
        if (loginDownloads.equals("")) {
            Toast.makeText(requireActivity(), "Enter Url", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.WEB_URL.matcher(loginDownloads).matches()) {
            Toast.makeText(requireActivity(), "Enter Valid Url", Toast.LENGTH_SHORT).show();
        } else {
            if (SharedPref.mainSharedGetBoolean(requireActivity(), SharedPref.ISINSTALOGIN)) {
                try {
                    if (!CommonClass.IgVideoPathDirectory.exists()) {
                        CommonClass.IgVideoPathDirectory.mkdirs();
                    }
                    if (new URL(edtPasteLink.getText().toString()).getHost().equals("www.instagram.com")) {
                        tempUrl = edtPasteLink.getText().toString();
//                        String str2 = getUrlInstaWithoutParameters(tempUrl) + "?__a=1&__d=dis";
                        String str3 = url_clean(tempUrl).split("\\?")[0];
//                        if (!str3.endsWith("/")) {
//                            str2 = str3;
//                        } else {
//                            str2 = str3;
//                        }
                        CallInstaApi.callResult(getInstaPhoto,getPhotoVideo, str3, "" + SharedPref.sharedGetString(requireActivity(), SharedPref.USERID) + "; sessionid=" + SharedPref.sharedGetString(requireActivity(), SharedPref.SESSIONID));
                    } else {
                        Toast.makeText(requireActivity(), "Enter Valid Url", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                startActivity(new Intent(requireActivity(), MainInstagramLogin.class));
            }
        }
    }

    private String url_clean(String str) {
        try {
            String replace = str.replace("https://instagram.com", "https://www.instagram.com");
            if (replace.startsWith("https://www.instagram.com")) {
                return replace;
            }
            String str2 = "https://www.instagram.com" + str.split("https://www.instagram.com")[1];
            return str2.contains(" ") ? str.split(" ")[0] : str2;
        } catch (Exception unused) {
            return str;
        }
    }

//    public static JsonObject rootPhotoVideoJson;

    public DisposableObserver<ArrayList<Node>> getInstaPhoto = new DisposableObserver<ArrayList<Node>>() {
        @Override
        public void onNext(ArrayList<Node> rootPhotoVideo) {
            Log.e("=====Kenil", "onError1: " + new Gson().toJson(rootPhotoVideo));
            try {
//                Gson gson = new Gson();
//                rootPhotoVideoJson = rootPhotoVideo;
                alertDialog.dismiss();
                requireActivity().startActivity(new Intent(requireActivity(), PhotoVideoActivity.class));
            } catch (Exception e) {
                Log.e("=====Kenil", "onNext: "+e.getMessage());
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e("=====Kenil", "onError: " + e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.e("=====Kenil", "onComplete: ");
        }
    };

    public DisposableObserver<String> getPhotoVideo = new DisposableObserver<String>() {
        @Override
        public void onNext(String rootPhotoVideo) {
            try {
                if (rootPhotoVideo.contains(".jpg") || rootPhotoVideo.contains(".heic") || rootPhotoVideo.contains(".png") || rootPhotoVideo.contains(".jpeg") || rootPhotoVideo.contains(".webp")){
                    startDownload(rootPhotoVideo, requireActivity(), System.currentTimeMillis() + ".jpg", alertDialog, edtPasteLink.getText().toString(),"");
                }else {
                    startDownload(rootPhotoVideo, requireActivity(), System.currentTimeMillis() + ".mp4", alertDialog, edtPasteLink.getText().toString(),"");
                }

            } catch (Exception e) {
            }
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {
        }
    };

/*


    private String getUrlInstaWithoutParameters(String tempUrl) {
        try {
            URI uri = new URI(tempUrl);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString();
        } catch (Exception e) {
            Toast.makeText(requireActivity(), "Enter Valid Url", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    private DisposableObserver<JsonObject> getInstaUserName = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(JsonObject jsonObject) {
            int i;
            int mediaType = 0;
            try {
                list.clear();
                JSONObject json = new JSONObject(jsonObject.toString());
                JSONArray itemsArray = json.getJSONArray("items");
                String userNametemp = "";
                for (int i2 = 0; i2 < itemsArray.length(); i2++) {
                    JSONObject itemObject = itemsArray.getJSONObject(i2);
                    mediaType = itemObject.getInt("media_type");
                    userNametemp = itemObject.getJSONObject("user").getString("username");
                    String str = "url";
                    if (mediaType == 1) {
                        JSONArray candidatesArray = itemObject.getJSONObject("image_versions2").getJSONArray("candidates");
                        for (int i4 = 0; i4 < candidatesArray.length(); i4++) {
                            JSONObject candidateObject = candidatesArray.getJSONObject(i4);
                            InstagramDetailModel instagramDetailModel = new InstagramDetailModel();
                            instagramDetailModel.width = candidateObject.getInt("width");
                            instagramDetailModel.height = candidateObject.getInt("height");
                            instagramDetailModel.url = candidateObject.getString(str);
                            instagramDetailModel.isFlag = (i4 == 0);
                            list.add(instagramDetailModel);
                        }
                        i = i2;
                    } else {
                        i = i2;
                        if (mediaType == 2) {
                            JSONArray videoVersionsArray = itemObject.getJSONArray("video_versions");
                            for (int i7 = 0; i7 < videoVersionsArray.length(); i7++) {
                                JSONObject videoObject = videoVersionsArray.getJSONObject(i7);
                                InstagramDetailModel instagramDetailModel2 = new InstagramDetailModel();
                                instagramDetailModel2.width = videoObject.getInt("width");
                                instagramDetailModel2.height = videoObject.getInt("height");
                                instagramDetailModel2.url = videoObject.getString(str);
                                instagramDetailModel2.isFlag = (i7 == 0);
                                list.add(instagramDetailModel2);
                            }
                        } else {
                            Toast.makeText(requireActivity(), R.string.somethingWentWrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                    i2 = i + 1;
                }
                *//*dialogInstagramDownload(userNametemp, list.get(0).url);*//*
                if (list.size() == 0) {
                    Toast.makeText(requireActivity(), R.string.somethingWentWrong, Toast.LENGTH_SHORT).show();
                }

                if (mediaType == 1 || mediaType == 8) {
                    startActivity(new Intent(requireActivity(), ImageListActivity.class).putExtra("url", "").putExtra("link", edtPasteLink.getText().toString()).putExtra("name", userNametemp).putExtra("list", new Gson().toJson(list)));

//                    startDownload(list.getUrl(), requireActivity(), getImageFilenameFromURL(list.get(0).getUrl()), alertDialog, edtPasteLink.getText().toString(), userNametemp);
                } else {
                    startDownload(list.get(0).getUrl(), requireActivity(), getVideoFilenameFromURL(list.get(0).getUrl()), alertDialog, edtPasteLink.getText().toString(), userNametemp);
                }
//                startDownload(list.get(0).getUrl(), requireActivity(), getVideoFilenameFromURL(list.get(0).getUrl()));
            } catch (Exception e) {
                e.printStackTrace();
                String str2;
                String str3 = url_clean(tempUrl).split("\\?")[0];
                if (!str3.endsWith("/")) {
                    str2 = str3;
                } else {
                    str2 = str3;
                }
                try {
//                    CallInstaApi.try_with_webview_api(str2,page);
                    CallInstaApi.callResult(instagramObserver, str2, "" + SharedPref.sharedGetString(requireActivity(), SharedPref.USERID) + "; " + "sessionid=" + SharedPref.sharedGetString(requireActivity(), SharedPref.SESSIONID));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override
        public void onError(Throwable th) {
            th.printStackTrace();
            String str2;
            String str3 = url_clean(tempUrl).split("\\?")[0];
            if (!str3.endsWith("/")) {
                str2 = str3;
            } else {
                str2 = str3;
            }
            try {
//                CallInstaApi.try_with_webview_api(str2,page);
                CallInstaApi.callResult(instagramObserver, str2, "" + SharedPref.sharedGetString(requireActivity(), SharedPref.USERID) + "; " + "sessionid=" + SharedPref.sharedGetString(requireActivity(), SharedPref.SESSIONID));
            } catch (Exception e) {
                Toast.makeText(requireActivity(), R.string.somethingWentWrong, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onComplete() {
        }
    };

    private DisposableObserver<JsonObject> instagramObserver = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(JsonObject jsonObject) {
            try {
                InstagramResponseModelTemp temp = new Gson().fromJson(jsonObject.toString(), new TypeToken<InstagramResponseModelTemp>() {
                }.getType());
                Log.e("====Hits", "onNext: " + temp);

                InstagramResponseModel responseModel = new Gson().fromJson(jsonObject.toString(), new TypeToken<InstagramResponseModel>() {
                }.getType());
                InstagramResponseModel.ApplyChildView edgeSidecarChildren = responseModel.getGraphql().getShortcodeMedia().getEdgeSidecarToChildren();
                if (edgeSidecarChildren != null) {
                    List<InstagramResponseModel.ApplyChildView.Edge> edges = edgeSidecarChildren.getEdges();
                    for (int i = 0; i < edges.size(); i++) {
                        if (edges.get(i).getNode().isVideo()) {
                            mMediaTypeVideo = edges.get(i).getNode().getVideoUrl();
                            String str = mMediaTypeVideo;
                            startDownload(str, requireActivity(), getVideoFilenameFromURL(mMediaTypeVideo), alertDialog, edtPasteLink.getText().toString(), "");
                            edtPasteLink.setText("");
                            mMediaTypeVideo = "";
                        } else {
                            mMediaTypeImage = edges.get(i).getNode().getDisplayResources().get(edges.get(i).getNode().getDisplayResources().size() - 1).getSrc();
                            String str3 = mMediaTypeImage;
                            startDownload(str3, requireActivity(), getImageFilenameFromURL(mMediaTypeImage), alertDialog, edtPasteLink.getText().toString(), "");
                            mMediaTypeImage = "";
                            edtPasteLink.setText("");
                        }
                    }
                } else if (responseModel.getGraphql().getShortcodeMedia().isVideo()) {
                    mMediaTypeVideo = responseModel.getGraphql().getShortcodeMedia().getVideoUrl();
                    String str5 = mMediaTypeVideo;
                    startDownload(str5, requireActivity(), getVideoFilenameFromURL(mMediaTypeVideo), alertDialog, edtPasteLink.getText().toString(), "");
                    mMediaTypeVideo = "";
                    edtPasteLink.setText("");
                } else {
                    mMediaTypeImage = responseModel.getGraphql().getShortcodeMedia().getDisplayResources().get(responseModel.getGraphql().getShortcodeMedia().getDisplayResources().size() - 1).getSrc();
                    String str7 = mMediaTypeImage;
                    startDownload(str7, requireActivity(), getImageFilenameFromURL(mMediaTypeImage), alertDialog, edtPasteLink.getText().toString(), "");
                    mMediaTypeImage = "";
                    edtPasteLink.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireActivity(), R.string.logout_for_instagram, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Throwable th) {
            Toast.makeText(requireActivity(), R.string.logout_for_instagram, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {
        }
    };

    public static String getVideoFilenameFromURL(String str) {
        try {
            return new File(new URL(str).getPath()).getName();
        } catch (MalformedURLException e) {
            return System.currentTimeMillis() + ".mp4";
        }
    }

    public static String getImageFilenameFromURL(String str) {
        try {
            return new File(new URL(str).getPath()).getName();
        } catch (MalformedURLException e) {
            return System.currentTimeMillis() + ".jpg";
        }
    }*/

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
        }, url);
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
                Log.d("TAG", "onVideoFoundss11: " + view);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(final WebView webview, final String url, Bitmap favicon) {
                Log.d("TAG", "onVideoFoundss10: " + url);
                super.onPageStarted(webview, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("TAG", "onVideoFoundss9: " + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(final WebView view, final String url) {
                Log.d("TAG", "onVideoFoundss8: " + url);
                final String viewUrl = view.getUrl();
                final String title = view.getTitle();
                try {
                    new VideoContentSearch(requireActivity(), url, viewUrl, title, mainUrl) {
                        @Override
                        public void onStartInspectingURL() {
                            Log.d("TAG", "onVideoFoundss6: " + viewUrl);
                            Utils.disableSSLCertificateChecking();
                        }

                        @Override
                        public void onFinishedInspectingURL(boolean finishedAll) {
                            Log.d("TAG", "onVideoFoundss5: " + viewUrl);
                            HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSF);
                        }

                        @Override
                        public void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                            Log.e("TAG", "onVideoFoundss7: " + link);
                            callback.onVideoFound(size, type, link, name, page, chunked, website, audio);
                        }
                    }.start();
                } catch (Exception e) {
                    Log.d("TAG", "onVideoFoundss4: " + e.getMessage());
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
                Log.d("TAG", "onProgressChanged: " + newProgress);
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

    private void callStoriesApi() {
        try {
            if (!new CommonClass(requireActivity()).isNetworkAvailable()) {
                Toast.makeText(requireActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (CallInstaApi != null) {
                progressStory.setVisibility(View.VISIBLE);
                CommonClassStoryForAPI commonClassStoryForAPI2 = CallInstaApi;
                DisposableObserver<StoryModel> disposableObserver = storyObserver;
                commonClassStoryForAPI2.getStories(disposableObserver, "ds_user_id=" + SharedPref.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPref.SESSIONID));
            }
        } catch (Exception e) {
            Log.d("TAG", "callStoriesApi: " + e.getMessage());
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