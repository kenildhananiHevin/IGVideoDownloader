package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.StoriesTimeAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.time_line.Media;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.SharedPref;

public class ExploreActivity extends BaseActivity {

    RecyclerView recycleExplore;
    ExploreActivity activity;
    private CommonClassStoryForAPI CallInstaApi;
    StoriesTimeAdapter storiesTimeAdapter;
    ProgressBar progressExplore;
    public String explorePostMax;

    public DisposableObserver<JsonObject> exploreMainObserver = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(JsonObject rootTime) {
            try {
                explorePostMax = rootTime.get("next_max_id").getAsString();
                Log.e("====Kenil", "ExploreApis: " + explorePostMax);
                if (rootTime.getAsJsonArray("sectional_items") != null) {
                    ArrayList<Media> media = new ArrayList<>();
                    for (int i = 0; i < rootTime.getAsJsonArray("sectional_items").size(); i++) {
                        JsonObject item = (JsonObject) rootTime.getAsJsonArray("sectional_items").get(i);
                        if (item.get("layout_type").getAsString().equals("one_by_two_right")) {
                               /* for (FillItem fill_item : item.layout_content.fill_items) {
                                    media.add(fill_item.media);
                                }
                                for (Item item1 : item.layout_content.one_by_two_item.clips.items) {
                                    media.add(item1.media);
                                }*/
                            for (JsonElement media1 : item.getAsJsonObject("layout_content").getAsJsonObject("one_by_two_item").getAsJsonObject("clips").getAsJsonArray("items")) {
                                JsonObject myMedia = ((JsonObject) media1).getAsJsonObject("media");
                                String url = ((JsonObject) ((JsonObject) media1).getAsJsonObject("media").getAsJsonObject("image_versions2").getAsJsonArray("candidates").get(0)).get("url").getAsString();
                                Log.e("TAG", "onNext:11 " + url);
                                media.add(new Gson().fromJson(((JsonObject) media1).getAsJsonObject("media"), Media.class));
                            }
                        }
                        if (item.get("layout_type").getAsString().equals("one_by_two_left")) {
                            for (JsonElement media1 : item.getAsJsonObject("layout_content").getAsJsonObject("one_by_two_item").getAsJsonObject("clips").getAsJsonArray("items")) {
                                JsonObject myMedia = ((JsonObject) media1).getAsJsonObject("media");
                                String url = ((JsonObject) ((JsonObject) media1).getAsJsonObject("media").getAsJsonObject("image_versions2").getAsJsonArray("candidates").get(0)).get("url").getAsString();
                                Log.e("TAG", "onNext:22 " + url);
                                media.add(new Gson().fromJson(((JsonObject) media1).getAsJsonObject("media"), Media.class));
                            }
                           /* for (FillItem fill_item : item.layout_content.fill_items) {
                                media.add(fill_item.media);
                            }
                            for (Item item1 : item.layout_content.one_by_two_item.clips.items) {
                                media.add(item1.media);
                            }*/
                        }
                        if (item.get("layout_type").getAsString().equals("media_grid")) {
                            Log.e("TAG", "onNext: " + item.get("layout_type").toString());
                            for (JsonElement media1 : item.getAsJsonObject("layout_content").getAsJsonArray("medias")) {
                                JsonObject myMedia = ((JsonObject) media1).getAsJsonObject("media");
                                String url = ((JsonObject) ((JsonObject) media1).getAsJsonObject("media").getAsJsonObject("image_versions2").getAsJsonArray("candidates").get(0)).get("url").getAsString();
                                Log.e("TAG", "onNext:33 " + url);
                                media.add(new Gson().fromJson(((JsonObject) media1).getAsJsonObject("media"), Media.class));
                            }
                        }
                    }
                    storiesTimeAdapter.sectional_items.addAll(media);
                    storiesTimeAdapter.notifyDataSetChanged();
                    progressExplore.setVisibility(View.GONE);
                } else {
                    storiesTimeAdapter.sectional_items = new ArrayList<>();
                    storiesTimeAdapter.notifyDataSetChanged();
                    progressExplore.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d("TAG", "onNextsssss: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            explorePostMax = null;
            Log.d("TAG", "onErrorssss: " + e.getMessage());
            e.printStackTrace();
            progressExplore.setVisibility(View.GONE);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ExploreDetailApi();
//                }
//            },1000);
            Log.e("====Kenil", "ExploreApis1: ");
        }

        @Override
        public void onComplete() {
            progressExplore.setVisibility(View.GONE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        activity = this;
        CallInstaApi = CommonClassStoryForAPI.getInstance();

        recycleExplore = findViewById(R.id.recycleExplore);
        progressExplore = findViewById(R.id.progressExplore);

        Handler handler = new Handler();
        progressExplore.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ExploreDetailApi();
            }
        }, 1000);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        recycleExplore.setLayoutManager(gridLayoutManager);
        recycleExplore.setNestedScrollingEnabled(false);
        recycleExplore.setHasFixedSize(false);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        storiesTimeAdapter = new StoriesTimeAdapter(activity, new ArrayList<>(), activity);
        recycleExplore.setAdapter(storiesTimeAdapter);
        recycleExplore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (progressExplore.getVisibility() == View.VISIBLE) {
                    return;
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (explorePostMax != null && Integer.parseInt(explorePostMax) < 20) {
                        progressExplore.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ExploreApis(explorePostMax);
                            }
                        }, 5000);
                        Log.e("====Kenil", "ExploreApis4: ");
                    } else {
                        /*if (Integer.parseInt(explorePostMax) > 0) {
                            progressExplore.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ExploreDetailApi();
                                }
                            }, 5000);
                            Log.e("====Kenil", "ExploreApis5: ");
                        }*/
                    }
                }
            }
        });
    }

    private void ExploreDetailApi() {
        explorePostMax = null;
        try {
            if (!new CommonClass(activity).isNetworkAvailable()) {
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (CallInstaApi != null) {
                CommonClassStoryForAPI commonClassStoryForAPI2 = CallInstaApi;
                DisposableObserver<JsonObject> disposableObserver = exploreMainObserver;
                commonClassStoryForAPI2.getUserExplore(disposableObserver, "", "ds_user_id=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExploreApis(String maxIds) {
        try {
            if (!new CommonClass(activity).isNetworkAvailable()) {
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (CallInstaApi != null) {
                CommonClassStoryForAPI commonClassStoryForAPI2 = CallInstaApi;
                DisposableObserver<JsonObject> disposableObservers = exploreMainObserver;
                commonClassStoryForAPI2.getUserExploreExtra(disposableObservers, "", "ds_user_id=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.SESSIONID), maxIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}