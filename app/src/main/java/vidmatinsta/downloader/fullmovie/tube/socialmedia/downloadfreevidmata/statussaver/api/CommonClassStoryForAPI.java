package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.api;

import android.app.Activity;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

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

public class CommonClassStoryForAPI {

    private static Activity mactivity;

    private String url_for_profile;
    private boolean is_finish = false;

    private static CommonClassStoryForAPI CommonClassStoryForAPI;

    public static CommonClassStoryForAPI getInstance() {
        CommonClassStoryForAPI = new CommonClassStoryForAPI();
        return CommonClassStoryForAPI;
    }



    private void followRedirect(final DisposableObserver disposableObserver, final DisposableObserver disposableObserver2, String url, String str2) {
        InstagramStoryAPIInterfaceTemp apiService = InstagramStoryClientTemp.getClient(str2).create(InstagramStoryAPIInterfaceTemp.class);
        Call<InstagramResponseModelTemp> call = apiService.callResult1(url, str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"");
        call.enqueue(new Callback<InstagramResponseModelTemp>() {
            @Override
            public void onResponse(Call<InstagramResponseModelTemp> call, Response<InstagramResponseModelTemp> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getShortcode_media().getEdge_sidecar_to_children() != null) {
                        List<InstagramResponseModelTemp.Data.shortcode_media.edge_sidecar_to_children.edges> data = response.body().getData().getShortcode_media().getEdge_sidecar_to_children().getEdges();
                        int size = data.size();
                        for (int i = 0; i < size; i++) {
                            stickyNodesList.add(data.get(i).getNode());
                        }
                        disposableObserver.onNext(stickyNodesList);
                        Log.e("=====33", "onResponse: " + size);
                    } else {
                        String data = response.body().getData().getShortcode_media().getVideo_url();
                        if (data == null) {
                            data = response.body().getData().getShortcode_media().getDisplay_url();
                        }
                        disposableObserver2.onNext(data);
                    }

                } else {
                    Log.e("=====347", "onResponse: ");
                    try {
                        String errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<InstagramResponseModelTemp> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public static List<Node> stickyNodesList = new ArrayList<>();

    public void callResult(final DisposableObserver disposableObserver, final DisposableObserver disposableObserver2, String str, String str2) {
        String oldUrl = str;
        if (oldUrl.endsWith("/")) {
            oldUrl = oldUrl.substring(0, oldUrl.length() - 1);
        }
        String[] split = oldUrl.split("/");
        String str3 = "graphql/query/?query_hash=b3055c01b4b222b8a47dc12b090e4e64&variables={%22shortcode%22:%22" + split[split.length - 1] + "%22}";

        Log.e("=====1", "callResult: " + str3);
        Log.e("=====2", "callResult: " + str2);

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
                        Log.e("=====5", "onResponse: ");
                    }
                } else if (response.code() == 302) {
                    Log.e("=====344", "onResponse: ");
                    String newUrl = response.headers().get("Location");
                    if (newUrl != null) {
                        Log.e("=====346", "onResponse: "+newUrl);
                        followRedirect(disposableObserver, disposableObserver2, newUrl, str2);
                    } else {
                        Log.e("=====345", "onResponse: ");
                    }
                } else {
                    Log.e("=====355", "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<InstagramResponseModelTemp> call, Throwable t) {
                Log.e("=====4", "onResponse: " + t.getMessage());
            }
        });
    }


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

    public void getUserExplore(final DisposableObserver disposableObserver, String str, String str2) {
/*        String key = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            key = encrypt("https://www.instagram.com/explore/grid/?module=explore_popular");
        }
        Log.d("TAG", "getFullPostExtra: " + key);*/

        InstagramStoryClient.getInstance(mactivity).getService().getUsersExplore(a("j/lr6TYl4YNfKyXJTehUaiggxkQBERodO20Naug4Lkp18M49Hf0pL0XzhRShEklQnOgUtCN9LKnDrwxvlRrF0w=="), str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(JsonObject userDetailModelGraphqlUser) {
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

    public void getUserExploreExtra(final DisposableObserver disposableObserver, String str, String str2, String exploreMaxId) {
      /*  String key = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            key = encrypt("https://www.instagram.com/explore/grid/?module=explore_popular");
        }
        Log.d("TAG", "getFullPostExtra1: " + key);*/
        InstagramStoryClient.getInstance(mactivity).getService().getUserTimeExplore(a("j/lr6TYl4YNfKyXJTehUaiggxkQBERodO20Naug4Lkp18M49Hf0pL0XzhRShEklQnOgUtCN9LKnDrwxvlRrF0w=="), str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"", exploreMaxId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(JsonObject userDetailModelGraphqlUser) {
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
        InstagramStoryClient.getInstance(mactivity).getService().getUserDetail(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str, str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"", "0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
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

        InstagramStoryClient.getInstance(mactivity).getService().getUserDetail(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu41wf8jum1CnTSKx9TuFywM3") + str, str2, "\"Instagram 146.0.0.27.125 Android (28/9; 420dpi; 1080x2131; samsung; SM-A505FN; a50; exynos9610; fi_FI; 221134032)\"", maxId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Root>() {
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
