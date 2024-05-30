package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import android.app.Activity;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.Root;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story.StoryModel;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show.RootStory;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other.CommonClass;

public class CommonClassStoryForAPI {

    private static Activity mactivity;

    private static CommonClassStoryForAPI CommonClassStoryForAPI;

    public static CommonClassStoryForAPI getInstance() {
        CommonClassStoryForAPI = new CommonClassStoryForAPI();
        return CommonClassStoryForAPI;
    }

    public void getStories(final DisposableObserver disposableObserver, String str) {
        if (CommonClass.isNullOrEmpty(str)) {
            str = "";
        }
        InstagramStoryClient.getInstance(mactivity).getService().getStoriesApi(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu42WcpscR/vYfeBAs6heZq3l"), str, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StoryModel>() {
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
        InstagramStoryClient.getInstance(mactivity).getService().getUserStory(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str + "/story", str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<RootStory>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(RootStory userDetailModelGraphqlUser) {
                Log.d("TAG", "onNextghj1: " + new Gson().toJson(userDetailModelGraphqlUser));
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                Log.d("TAG", "onNextghj2: " + th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getFullPost(final DisposableObserver disposableObserver, String str, String str2) {
        InstagramStoryClient.getInstance(mactivity).getService().getUserDetail(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str, str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"", "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(Root userDetailModelGraphqlUser) {
                Log.i("TAG", "onNextghj1: " + new Gson().toJson(userDetailModelGraphqlUser));
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                Log.d("TAG", "onNextghj2: " + th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getFullPostExtra(final DisposableObserver disposableObserver, String str, String str2, String maxId) {
       /* String key = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            key = encrypt("https://i.instagram.com/api/v1/feed/user/");
        }*/
        /*Log.d("TAG", "getFullPostExtra: " + key);*/

        InstagramStoryClient.getInstance(mactivity).getService().getUserDetail(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str, str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"", maxId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(Root userDetailModelGraphqlUser) {
                Log.i("TAG", "onNextghj1: " + new Gson().toJson(userDetailModelGraphqlUser));
                disposableObserver.onNext(userDetailModelGraphqlUser);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
                Log.d("TAG", "onNextghj2: " + th);
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
