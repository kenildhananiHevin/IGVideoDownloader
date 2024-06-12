package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import android.app.Activity;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.InstagramResponseModelTemp;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video.Node;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.Root;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryModel;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show.RootStory;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.SharedPref;

public class CommonClassStoryForAPI {

    private static Activity mactivity;

    private String url_for_profile;
    private boolean is_finish = false;

    private static CommonClassStoryForAPI CommonClassStoryForAPI;

    public static CommonClassStoryForAPI getInstance() {
        CommonClassStoryForAPI = new CommonClassStoryForAPI();
        return CommonClassStoryForAPI;
    }



    public static List<Node> stickyNodesList = new ArrayList<>();




    public void getStories(final DisposableObserver disposableObserver, String str) {
        if (CommonClass.isNullOrEmpty(str)) {
            str = "";
        }
        InstagramStoryClient.getInstance(mactivity).getService().getStoriesApi(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu42WcpscR/vYfeBAs6heZq3l"), str, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StoryModel>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(StoryModel model_story) {
                disposableObserver.onNext(model_story);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getFullStory(final DisposableObserver disposableObserver, String str, String str2) {
        InstagramStoryClient.getInstance(mactivity).getService().getUserStory(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str + "/story", str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<RootStory>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(RootStory userDetailModelGraphqlUser) {
//                
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getUserExplore(final DisposableObserver disposableObserver, String str, String str2) {
        InstagramStoryClient.getInstance(mactivity).getService().getUsersExplore(a("j/lr6TYl4YNfKyXJTehUaiggxkQBERodO20Naug4Lkp18M49Hf0pL0XzhRShEklQnOgUtCN9LKnDrwxvlRrF0w=="), str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(JsonObject userDetailModelGraphqlUser) {
//                
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getUserExploreExtra(final DisposableObserver disposableObserver, String str, String str2, String exploreMaxId) {
        InstagramStoryClient.getInstance(mactivity).getService().getUserTimeExplore(a("j/lr6TYl4YNfKyXJTehUaiggxkQBERodO20Naug4Lkp18M49Hf0pL0XzhRShEklQnOgUtCN9LKnDrwxvlRrF0w=="), str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"", exploreMaxId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            public void onSubscribe(Disposable disposable) {


            }

            @Override
            public void onNext(JsonObject userDetailModelGraphqlUser) {
//                
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getFullPost(final DisposableObserver disposableObserver, String str, String str2) {
        InstagramStoryClient.getInstance(mactivity).getService().getUserDetail(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str, str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"", "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Root userDetailModelGraphqlUser) {
//                
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getProfilePic(final DisposableObserver disposableObserver, String str, String str2) {
        InstagramStoryClient.getInstance(mactivity).getService().getUserDetail(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str, str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"", "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Root userDetailModelGraphqlUser) {
//                
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }


    public void getFullPostExtra(final DisposableObserver disposableObserver, String str, String str2, String maxId) {
        InstagramStoryClient.getInstance(mactivity).getService().getUserDetail(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str, str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"", maxId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(Root userDetailModelGraphqlUser) {
//                
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public static String f11252a = "aesEncryptionKey";

    public static String f11253b = "encryptionIntVec";

    public static String a(String str) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(f11253b.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(f11252a.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return new String(cipher.doFinal(Base64.decode(str, 0)));
        } catch (Exception e10) {
            e10.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String str) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(f11253b.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(f11252a.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
            return java.util.Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
