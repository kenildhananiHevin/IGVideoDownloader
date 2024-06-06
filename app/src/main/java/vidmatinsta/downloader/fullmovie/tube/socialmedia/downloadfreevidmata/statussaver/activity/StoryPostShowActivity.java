package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.StoriesPostAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.adapter.StoriesViewAdapter;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api.CommonClassStoryForAPI;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.Root;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show.RootStory;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.DebouncedOnClickListener;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.SharedPref;

public class StoryPostShowActivity extends BaseActivity {
    RecyclerView recycleRVStories, recycleRVPost;
    StoriesPostAdapter storiesPostAdapter;
    StoriesViewAdapter storiesViewAdapter;
    TextView txtPost, txtStory;
    LinearLayout linearPost;
    StoryPostShowActivity activity;
    private String storyUserOld;
    private CommonClassStoryForAPI CallInstaApi;
    public long storyUser;
    ProgressBar progressStory,progressStoryShow;

    public DisposableObserver<RootStory> storyDetailsMainObserver = new DisposableObserver<RootStory>() {
        @Override
        public void onNext(RootStory story) {
            try {
                if (story.reel != null) {
                    storiesViewAdapter = new StoriesViewAdapter(activity, story.reel.items, activity);
                    recycleRVStories.setAdapter(storiesViewAdapter);
                    storiesViewAdapter.notifyDataSetChanged();
                } else {
                    storiesViewAdapter = new StoriesViewAdapter(activity, new ArrayList<>(), activity);
                    recycleRVStories.setAdapter(storiesViewAdapter);
                    storiesViewAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
        }
    };


    public String postMax;
    DisposableObserver<Root> postStoryDetailsMainObserver = new DisposableObserver<Root>() {
        @Override
        public void onNext(Root root) {
            Log.d("TAG", "onNext232: ");
            try {
                postMax = root.next_max_id;
                if (root.items() != null) {
                    if (root.user.pk_id.equals(storyUserOld)) {
                        storiesPostAdapter.items.addAll(root.items);
                    } else {
                        storiesPostAdapter.items = (root.items);
                    }
                    storiesPostAdapter.notifyDataSetChanged();
                    progressStoryShow.setVisibility(View.GONE);
                } else {
                    storiesPostAdapter.items = new ArrayList();
                    storiesPostAdapter.notifyDataSetChanged();
                    progressStoryShow.setVisibility(View.GONE);
                }
                storyUserOld = root.user.pk_id;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            progressStory.setVisibility(View.GONE);
            progressStoryShow.setVisibility(View.GONE);
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            progressStory.setVisibility(View.GONE);
            progressStoryShow.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_post_show);

        activity = this;
        CallInstaApi = CommonClassStoryForAPI.getInstance();

        long storyUsers = getIntent().getLongExtra("userId",0);

        recycleRVStories = findViewById(R.id.recycleRVStories);
        recycleRVPost = findViewById(R.id.recycleRVPost);
        linearPost = findViewById(R.id.linearPost);
        txtPost = findViewById(R.id.txtPost);
        txtStory = findViewById(R.id.txtStory);
        progressStory = findViewById(R.id.progressStory);
        progressStoryShow = findViewById(R.id.progressStoryShow);

        storyUser = storyUsers;
        Handler handler = new Handler();
        progressStoryShow.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callStoriesDetailApi(String.valueOf(storyUser));
            }
        },2500);

        txtStory.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                txtStory.setBackground(getDrawable(R.drawable.select_bg_post));
                txtPost.setBackground(getDrawable(R.drawable.dialog_bg));
                recycleRVStories.setVisibility(View.VISIBLE);
                recycleRVPost.setVisibility(View.GONE);
            }
        });

        txtPost.setOnClickListener(new DebouncedOnClickListener(750) {
            @Override
            public void onDebouncedClick(View v) {
                txtPost.setBackground(getDrawable(R.drawable.select_bg_post));
                txtStory.setBackground(getDrawable(R.drawable.dialog_bg));
                recycleRVStories.setVisibility(View.GONE);
                recycleRVPost.setVisibility(View.VISIBLE);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        recycleRVStories.setLayoutManager(gridLayoutManager);
        recycleRVStories.setNestedScrollingEnabled(false);
        recycleRVStories.setHasFixedSize(false);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        GridLayoutManager gridLayoutManagers = new GridLayoutManager(activity, 3);
        recycleRVPost.setLayoutManager(gridLayoutManagers);
        recycleRVPost.setNestedScrollingEnabled(false);
        recycleRVPost.setHasFixedSize(false);
        gridLayoutManagers.setOrientation(RecyclerView.VERTICAL);

        storiesPostAdapter = new StoriesPostAdapter(activity, new ArrayList<>(), activity);
        recycleRVPost.setAdapter(storiesPostAdapter);
        recycleRVPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (postMax != null) {
                        progressStory.setVisibility(View.VISIBLE);
                        callStoriesDetailApis(String.valueOf(storyUser), postMax);
                    }
                }
            }
        });
    }

    private void callStoriesDetailApis(String str, String maxIds) {
        try {
            if (!new CommonClass(activity).isNetworkAvailable()) {
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (CallInstaApi != null) {
                CommonClassStoryForAPI commonClassStoryForAPI2 = CallInstaApi;
                DisposableObserver<Root> disposableObservers = postStoryDetailsMainObserver;
                commonClassStoryForAPI2.getFullPostExtra(disposableObservers, str, "ds_user_id=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.SESSIONID), maxIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void callStoriesDetailApi(String str) {
        try {
            if (!new CommonClass(activity).isNetworkAvailable()) {
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (CallInstaApi != null) {
                CommonClassStoryForAPI commonClassStoryForAPI2 = CallInstaApi;
                DisposableObserver<RootStory> disposableObserver = storyDetailsMainObserver;
                DisposableObserver<Root> disposableObservers = postStoryDetailsMainObserver;
                Log.d("TAG", "callStoriesDetailApighj: "+str);
                commonClassStoryForAPI2.getFullPost(disposableObservers, str, "ds_user_id=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.SESSIONID));
                commonClassStoryForAPI2.getFullStory(disposableObserver, str, "ds_user_id=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.USERID) + "; sessionid=" + SharedPref.getInstance(activity).sharedGetString(activity, SharedPref.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}