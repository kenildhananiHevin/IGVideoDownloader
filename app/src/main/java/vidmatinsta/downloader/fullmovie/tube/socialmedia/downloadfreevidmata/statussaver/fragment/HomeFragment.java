package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.fragment;

import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI.a;
import static vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI.stickyNodesList;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.SSLSocketFactory;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.MainInstagramLogin;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.PhotoVideoActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity.VideoDownloadActivity;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.StoryAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.InstagramStoryAPIInterfaceTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.InstagramStoryClientTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.interfaces.StoryUserListInterface;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.InstagramResponseModelTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.WithOutLoginListDownloadTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.WithOutLoginSlideDataTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video.Node;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryModel;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryTray;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.InstagramDownload;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.SharedPref;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.TouchableWebView;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.WithOutLoginApiTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.WithOutLoginGlobalOjTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.WithOutLoginInterfaceFragLinkTemp;

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

    WebView webView;

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
                if (arrayList.isEmpty()) {
                }
                storyAdapter = new StoryAdapter(requireActivity(), arrayList, new StoryUserListInterface() {
                    @Override
                    public void storyUserListClick(int i, StoryTray storyTray) {

                    }
                });
                recycleRVUserList.setAdapter(storyAdapter);
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            SharedPref.getInstance(requireActivity()).mainSharedPutBoolean(requireActivity(), SharedPref.ISINSTALOGIN, false);
            SharedPref.getInstance(requireActivity()).mainSharedPutString(requireActivity(), SharedPref.COOKIES, "");
            SharedPref.getInstance(requireActivity()).mainSharedPutString(requireActivity(), SharedPref.CSRF, "");
            SharedPref.getInstance(requireActivity()).mainSharedPutString(requireActivity(), SharedPref.SESSIONID, "");
            SharedPref.getInstance(requireActivity()).mainSharedPutString(requireActivity(), SharedPref.USERID, "");
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

        setupWebView(view);


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

                if (!CommonClass.IgVideoPathDirectory.exists()) {
                    CommonClass.IgVideoPathDirectory.mkdirs();
                }
                

                if (SharedPref.getInstance(requireActivity()).mainSharedGetBoolean(requireActivity(), SharedPref.ISINSTALOGIN)) {
                    loginDownload(edtPasteLink.getText().toString());
                    create_progress();
                    return;
                } else {
//                    if (edtPasteLink.getText().toString().contains("stories")) {
//                        
//                        Toast.makeText(requireActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                }
                DownloadClick1(edtPasteLink.getText().toString());
                create_progress();
            }
        });


        return view;
    }

    private void setupWebView(View view) {
        webView = view.findViewById(R.id.webview);
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
        webView.addJavascriptInterface(new JavaScriptInterface(), "Android");

    }

    private class JavaScriptInterface {
        @JavascriptInterface
        public void handleData(String datas) {
            
            InstagramResponseModelTemp temp = new Gson().fromJson(datas, InstagramResponseModelTemp.class);
            if (temp != null) {
                if (temp.getData().getShortcode_media().getEdge_sidecar_to_children() != null) {
                    List<InstagramResponseModelTemp.Data.shortcode_media.edge_sidecar_to_children.edges> data = temp.getData().getShortcode_media().getEdge_sidecar_to_children().getEdges();
                    int size = data.size();
                    for (int i = 0; i < size; i++) {
                        stickyNodesList.add(data.get(i).getNode());
                    }
                    alertDialog.dismiss();
                    startActivity(new Intent(requireActivity(), PhotoVideoActivity.class));
                    
                } else {
                    String data = temp.getData().getShortcode_media().getVideo_url();
                    if (data == null) {
                        data = temp.getData().getShortcode_media().getDisplay_url();
                    }
                    if (data.contains(".jpg") || data.contains(".heic") || data.contains(".png") || data.contains(".jpeg") || data.contains(".webp")) {
                        startDownload(data, requireActivity(), System.currentTimeMillis() + ".jpg", alertDialog, edtPasteLink.getText().toString(), "");
                    } else {
                        startDownload(data, requireActivity(), System.currentTimeMillis() + ".mp4", alertDialog, edtPasteLink.getText().toString(), "");
                    }

                }
            }
        }
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
                        String str3 = url_clean(tempUrl).split("\\?")[0];
                        if (!tempUrl.contains("instagram.com/stories/")) {
                            callResult(requireActivity(), getInstaPhoto, getPhotoVideo, str3, "" + SharedPref.sharedGetString(requireActivity(), SharedPref.USERID) + "; sessionid=" + SharedPref.sharedGetString(requireActivity(), SharedPref.SESSIONID));
                        }else {
                            DownloadClick1(tempUrl);
                        }
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
            String replace = str.replace(a("NDCiQCnI3sx1+ahv/1Pbv7g8j+sPKuSETmlxi1RCtkM="), a(" j/lr6TYl4YNfKyXJTehUar8Yvax/ruvNnHF0vZU3HYg="));
            if (replace.startsWith(a(" j/lr6TYl4YNfKyXJTehUar8Yvax/ruvNnHF0vZU3HYg="))) {
                return replace;
            }
            String str2 = a(" j/lr6TYl4YNfKyXJTehUar8Yvax/ruvNnHF0vZU3HYg=") + str.split(a(" j/lr6TYl4YNfKyXJTehUar8Yvax/ruvNnHF0vZU3HYg="))[1];
            return str2.contains(" ") ? str.split(" ")[0] : str2;
        } catch (Exception unused) {
            return str;
        }
    }


    public DisposableObserver<ArrayList<Node>> getInstaPhoto = new DisposableObserver<ArrayList<Node>>() {
        @Override
        public void onNext(ArrayList<Node> rootPhotoVideo) {
            try {
                alertDialog.dismiss();
                requireActivity().startActivity(new Intent(requireActivity(), PhotoVideoActivity.class));
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

    public DisposableObserver<String> getPhotoVideo = new DisposableObserver<String>() {
        @Override
        public void onNext(String rootPhotoVideo) {
            try {
                if (rootPhotoVideo.contains(".jpg") || rootPhotoVideo.contains(".heic") || rootPhotoVideo.contains(".png") || rootPhotoVideo.contains(".jpeg") || rootPhotoVideo.contains(".webp")) {
                    startDownload(rootPhotoVideo, requireActivity(), System.currentTimeMillis() + ".jpg", alertDialog, edtPasteLink.getText().toString(), "");
                } else {
                    startDownload(rootPhotoVideo, requireActivity(), System.currentTimeMillis() + ".mp4", alertDialog, edtPasteLink.getText().toString(), "");
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

    String foundLink = "";

    public void DownloadClick1(String url) {
        String str2 = "";
        if (url.contains("instagram.com")) {
            String str3 = url_clean(url).split("\\?")[0];
            if (!str3.endsWith("/")) {
                str2 = str3 + "/?__a=1&__d=dis";
            } else {
                str2 = str3 + "?__a=1&__d=dis";
            }
            create_progress();
            if (!str2.contains("instagram.com/stories/")) {
                tryWithWebViewApi(str2);
                return;
            }
            new WithOutLoginApiTemp(getActivity(), new WithOutLoginInterfaceFragLinkTemp() {
                @Override
                public void WithOutLoginGetResult(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, String str6, ArrayList<WithOutLoginSlideDataTemp> arrayList, boolean z3) {
                    
                }

                @Override
                public void WithOutLoginGetStoryUserIdMediaId(String str, String str2, String str3) {

                    
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new WithOutLoginApiTemp(getActivity(), new WithOutLoginInterfaceFragLinkTemp() {
                                    @Override
                                    public void WithOutLoginGetResult(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, String str6, ArrayList<WithOutLoginSlideDataTemp> arrayList, boolean z3) {
                                        getResult(str, str2, str3, str4, str5, z, z2, str6, arrayList, z3);
                                    }

                                    @Override
                                    public void WithOutLoginGetStoryUserIdMediaId(String str, String str2, String str3) {
                                        
                                    }

                                    @Override
                                    public void WithOutLoginGetStoryUserIdMediaIdLocal(String str) {
                                        
                                        if (getActivity() != null) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    WithOutLoginApiTemp apiVar = new WithOutLoginApiTemp(getActivity(), new WithOutLoginInterfaceFragLinkTemp() {
                                                        @Override
                                                        public void WithOutLoginGetResult(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, String str6, ArrayList<WithOutLoginSlideDataTemp> arrayList, boolean z3) {
                                                            getResult(str, str2, str3, str4, str5, z, z2, str6, arrayList, z3);
                                                        }

                                                        @Override
                                                        public void WithOutLoginGetStoryUserIdMediaId(String str, String str2, String str3) {

                                                        }

                                                        @Override
                                                        public void WithOutLoginGetStoryUserIdMediaIdLocal(String str) {

                                                        }
                                                    });
                                                    
                                                    apiVar.with_out_login_get_data_story_item_by_media2(str, page);
                                                }
                                            });
                                        }
                                    }
                                }).with_out_login_get_data_story_item_by_media(str, str2, str3, page);
                            }
                        });
                    }
                }

                @Override
                public void WithOutLoginGetStoryUserIdMediaIdLocal(String str) {
                    
                }
            }).with_out_login_get_data_story_data_by_username(str2.split("\\?")[0], page);
        }
    }

    public void tryWithWebViewApi(final String str) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                    new WithOutLoginApiTemp(getActivity(), new WithOutLoginInterfaceFragLinkTemp() {
                        @Override
                        public void WithOutLoginGetResult(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, String str6, ArrayList<WithOutLoginSlideDataTemp> arrayList, boolean z3) {
                            getResult(str, str2, str3, str4, str5, z, z2, str6, arrayList, z3);
                        }

                        @Override
                        public void WithOutLoginGetStoryUserIdMediaId(String str, String str2, String str3) {
                        }

                        @Override
                        public void WithOutLoginGetStoryUserIdMediaIdLocal(String str) {
                        }
                    }).WithOutLoginTryWithWebViewApi(str, page);
                }
            });
        }
    }

    public void getResult(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, String str6, ArrayList<WithOutLoginSlideDataTemp> arrayList, boolean z3) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WithOutLoginGlobalOjTemp.withOutLoginDownloadManagerLocallyTemp.withOutLoginListDownloadTemps.add(new WithOutLoginListDownloadTemp(str, str5, z, z2));
                    int size = WithOutLoginGlobalOjTemp.withOutLoginDownloadManagerLocallyTemp.withOutLoginListDownloadTemps.size() - 1;
                    if (z) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).isIs_video()) {
                                WithOutLoginGlobalOjTemp.withOutLoginDownloadManagerLocallyTemp.downloadVideo(arrayList.get(i).getVideo_url(), ".mp4", size, arrayList.size(), alertDialog, edtPasteLink.getText().toString(), (url, name) -> startActivity(new Intent(requireActivity(), VideoDownloadActivity.class)
                                        .putExtra("Type", "mp4")
                                        .putExtra("url", url)
                                        .putExtra("link", edtPasteLink.getText().toString())
                                        .putExtra("name", name)), i == (arrayList.size() - 1));
                            } else {
                                WithOutLoginGlobalOjTemp.withOutLoginDownloadManagerLocallyTemp.downloadVideo(arrayList.get(i).getDisplay_resources(), ".jpg", size, arrayList.size(), alertDialog, edtPasteLink.getText().toString(), (url, name) -> startActivity(new Intent(requireActivity(), VideoDownloadActivity.class)
                                        .putExtra("Type", "jpg")
                                        .putExtra("url", url)
                                        .putExtra("link", edtPasteLink.getText().toString())
                                        .putExtra("name", name)), i == (arrayList.size() - 1));
                            }
                        }
                        return;
                    }
                    if (z2) {
                        
                        WithOutLoginGlobalOjTemp.withOutLoginDownloadManagerLocallyTemp.downloadVideo(str6, ".mp4", size, 0, alertDialog, edtPasteLink.getText().toString(), (url, name) -> startActivity(new Intent(requireActivity(), VideoDownloadActivity.class)
                                .putExtra("Type", "mp4")
                                .putExtra("url", url)
                                .putExtra("link", edtPasteLink.getText().toString())
                                .putExtra("name", name)), true);
                    } else {
//
                        
                        WithOutLoginGlobalOjTemp.withOutLoginDownloadManagerLocallyTemp.downloadVideo(str5, ".jpg", size, 0, alertDialog, edtPasteLink.getText().toString(), (url, name) -> startActivity(new Intent(requireActivity(), VideoDownloadActivity.class)
                                .putExtra("Type", "jpg")
                                .putExtra("url", url)
                                .putExtra("link", edtPasteLink.getText().toString())
                                .putExtra("name", name)), true);
                    }
                }
            });
        }
    }


    private void create_progress() {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
        }

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

    public static void startDownload(String paths, FragmentActivity instagram, String name, AlertDialog alertDialog, String edtPaste, String nameIns) {
        new InstagramDownload(instagram, name, alertDialog, edtPaste, nameIns, true).execute(paths);
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


    public void callResult(FragmentActivity fragmentActivity, final DisposableObserver disposableObserver, final DisposableObserver disposableObserver2, String str, String str2) {
        String oldUrl = str;
        if (oldUrl.endsWith("/")) {
            oldUrl = oldUrl.substring(0, oldUrl.length() - 1);
        }
        String[] split = oldUrl.split("/");
        String str3 = "graphql/query/?query_hash=b3055c01b4b222b8a47dc12b090e4e64&variables={%22shortcode%22:%22" + split[split.length - 1] + "%22}";

        
        
        InstagramStoryAPIInterfaceTemp apiService = InstagramStoryClientTemp.getClient(str2).create(InstagramStoryAPIInterfaceTemp.class);
        Call<InstagramResponseModelTemp> call = apiService.callResult1(str3, str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"");

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<InstagramResponseModelTemp> call, Response<InstagramResponseModelTemp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        
                        if (response.body().getData().getShortcode_media().getEdge_sidecar_to_children() != null) {
                            List<InstagramResponseModelTemp.Data.shortcode_media.edge_sidecar_to_children.edges> data = response.body().getData().getShortcode_media().getEdge_sidecar_to_children().getEdges();
                            int size = data.size();
                            for (int i = 0; i < size; i++) {
                                stickyNodesList.add(data.get(i).getNode());
                            }
                            disposableObserver.onNext(stickyNodesList);
                        } else {
                            String data = response.body().getData().getShortcode_media().getVideo_url();
                            if (data == null) {
                                data = response.body().getData().getShortcode_media().getDisplay_url();
                            }
                            disposableObserver2.onNext(data);
                        }
                    } else {
                        
                    }
                } else {
                    String newUrl = response.raw().request().url().toString();
                    webView.loadUrl(newUrl);
                }
            }

            @Override
            public void onFailure(Call<InstagramResponseModelTemp> call, Throwable t) {
                
            }
        });
    }
}