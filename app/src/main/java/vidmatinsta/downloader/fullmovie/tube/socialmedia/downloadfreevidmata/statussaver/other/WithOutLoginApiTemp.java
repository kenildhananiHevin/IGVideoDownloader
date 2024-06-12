package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.WithOutLoginSlideDataTemp;

public class WithOutLoginApiTemp {
    private Context context;
    private WithOutLoginInterfaceFragLinkTemp withOutLoginInterfaceFragLinkTemp;
    private boolean WithOutLoginIsFinish = false;
    private String WithOutLoginUrlForProfile;

    public WithOutLoginApiTemp(Context context, WithOutLoginInterfaceFragLinkTemp withOutLoginInterfaceFragLinkTemp) {
        this.context = context;
        this.withOutLoginInterfaceFragLinkTemp = withOutLoginInterfaceFragLinkTemp;
    }

    getHtmlFrom getHtmlFrom = new getHtmlFrom() {
        @Override
        @JavascriptInterface
        public void getWithOutLoginHtml(String str, String str2) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("data")) {

                    WithOutLoginGraphQL(jSONObject, str2);
                } else {

                    WithOutLoginItems(jSONObject, str2);
                }
            } catch (Exception unused) {

            }
        }

        @Override
        @JavascriptInterface
        public void with_out_login_get_html_story_data_by_user_id_and_story_id(String str, String str2, String str3) {
            try {
                withOutLoginInterfaceFragLinkTemp.WithOutLoginGetStoryUserIdMediaId(new JSONObject(str).getJSONObject("data").getJSONObject("user").getString("id"), str2, str3);
            } catch (Exception unused) {
                withOutLoginInterfaceFragLinkTemp.WithOutLoginGetStoryUserIdMediaIdLocal("https://www.instagram.com/stories/" + str3 + "/" + str2 + "/");
            }
        }

        @Override
        @JavascriptInterface
        public void with_out_login_get_html_story_items_one(String str, String str2, String str3, String str4) {
            String str5;
            String str6;
            JSONArray jSONArray;
            char c;
            String str7 = str2;
            String str8 = "_";
            String str9 = "id";
            try {
                JSONObject jSONObject = new JSONObject(str);

                JSONArray jSONArray2 = jSONObject.getJSONObject("reels").getJSONObject(str3).getJSONArray("items");
                char c2 = 0;
                int i = 0;
                while (i < jSONArray2.length()) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                    if (jSONObject2.getString(str9).split(str8)[c2].startsWith(str7)) {
                        String str10 = "https://www.instagram.com/stories/" + str4 + "/" + jSONObject2.getString(str9).split(str8)[c2];
                        str5 = str8;
                        str6 = str9;
                        jSONArray = jSONArray2;
                        if (jSONObject2.has("video_versions")) {
                            withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(str10, "", "", "", jSONObject2.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url").replace("amp;", "").replace(";amp", ""), false, true, jSONObject2.getJSONArray("video_versions").getJSONObject(0).getString("url").replace("amp;", "").replace(";amp", ""), null, false);
                            c = 0;
                        } else {
                            c = 0;
                            withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(str10, "", "", "", jSONObject2.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url").replace("amp;", "").replace(";amp", ""), false, false, "", null, false);
                        }
                    } else {
                        str5 = str8;
                        str6 = str9;
                        jSONArray = jSONArray2;
                        c = c2;
                    }
                    i++;
                    str7 = str2;
                    jSONArray2 = jSONArray;
                    c2 = c;
                    str8 = str5;
                    str9 = str6;
                }
            } catch (Exception e) {
                withOutLoginInterfaceFragLinkTemp.WithOutLoginGetStoryUserIdMediaIdLocal("https://www.instagram.com/stories/" + str4 + "/" + str2 + "/");
            }
        }

        @Override
        @JavascriptInterface
        public void with_out_login_get_html_story_items_one_local_api(String str, String str2, String str3) {
            try {
                JSONObject jSONObject = new JSONObject(str);

                JSONObject jSONObject2 = jSONObject.getJSONArray("result").getJSONObject(0);

                String str4 = "https://www.instagram.com/stories/" + str3 + "/" + str2;
                if (jSONObject2.has("video_versions")) {
                    withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(str4, "", "", "", jSONObject2.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url").replace("amp;", "").replace(";amp", ""), false, true, jSONObject2.getJSONArray("video_versions").getJSONObject(0).getString("url").replace("amp;", "").replace(";amp", ""), null, false);
                } else {
                    withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(str4, "", "", "", jSONObject2.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url").replace("amp;", "").replace(";amp", ""), false, false, "", null, false);
                }
            } catch (Exception e) {
                WithOutLoginGlobalOjTemp.mainApi.WithOutLoginGetDataRapidApi("https://www.instagram.com/stories/" + str3 + "/" + str2, context, withOutLoginInterfaceFragLinkTemp);
            }
        }

        @JavascriptInterface
        public void with_out_login_get_story_userId_mediaId_Local(String str) {
            withOutLoginInterfaceFragLinkTemp.WithOutLoginGetStoryUserIdMediaIdLocal(str);
        }
    };

    @SuppressLint("JavascriptInterface")
    public void WithOutLoginTryWithWebViewApi(final String str, final WebView webView) {
        String str2 = str.split("\\?")[0];
        if (str2.endsWith("/")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        String[] split = str2.split("/");
        String str3 = "https://www.instagram.com/graphql/query/?query_hash=b3055c01b4b222b8a47dc12b090e4e64&variables={%22shortcode%22:%22" + split[split.length - 1] + "%22}";
        WithOutLoginUrlForProfile = str3;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 12_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 Instagram 105.0.0.11.118 (iPhone11,8; iOS 12_3_1; en_US; en-US; scale=2.00; 828x1792; 165586599);");
        WithOutLoginIsFinish = false;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView2, String str4) {
                super.onPageFinished(webView2, str4);
                if (!WithOutLoginIsFinish) {

                    WithOutLoginIsFinish = true;
                    webView.loadUrl("javascript:var a = document.getElementsByTagName('pre');if(a.length>0){window.java_sc.getWithOutLoginHtml(a[0].innerHTML,'" + str.split("\\?")[0] + "');}else{window.java_sc.try_with_rapid_api('" + str.split("\\?")[0] + "');}");
                }
            }

            @Override
            public void onPageStarted(WebView webView2, String str4, Bitmap bitmap) {
                super.onPageStarted(webView2, str4, bitmap);
            }
        });
        webView.addJavascriptInterface(getHtmlFrom, "java_sc");
        webView.loadUrl(str3);
    }

    public void with_out_login_get_data_story_data_by_username(String str, final WebView webView) {

        final String str2 = str.split("stories/")[1].split("/")[0];
        final String replace = str.split("stories/")[1].split("/")[1].split("\\?")[0].replace("/", "");
        WithOutLoginUrlForProfile = "https://i.instagram.com/api/v1/users/web_profile_info/?username=" + str2;

        webView.getSettings().setJavaScriptEnabled(true);
        WithOutLoginIsFinish = false;
        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 12_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 Instagram 105.0.0.11.118 (iPhone11,8; iOS 12_3_1; en_US; en-US; scale=2.00; 828x1792; 165586599);");
        WithOutLoginIsFinish = false;
        webView.setWebChromeClient(new WebChromeClient() { // from class: instagram.video.downloader.story.saver.insapp.api.4
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }
        });
        webView.setWebViewClient(new WebViewClient() { // from class: instagram.video.downloader.story.saver.insapp.api.5
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str3) {
                super.onPageFinished(webView2, str3);
                if (!WithOutLoginIsFinish) {
                    WithOutLoginIsFinish = true;
                    if (!WithOutLoginUrlForProfile.replace("www.", "").equals(str3.replace("www.", ""))) {

                        webView.loadUrl("javascript:window.java_sc.with_out_login_get_story_userId_mediaId_Local('https://www.instagram.com/stories/" + str2 + "/" + replace + "/');");
                        return;
                    }
                    webView.loadUrl("javascript:console.log('aaaaa');var a = document.getElementsByTagName('pre');if(a.length>0){window.java_sc.with_out_login_get_html_story_data_by_user_id_and_story_id(a[0].innerHTML,'" + replace + "','" + str2 + "');}else{window.java_sc.with_out_login_get_story_userId_mediaId_Local('https://www.instagram.com/stories/" + str2 + "/" + replace + "/');}");
                }
            }

            @Override
            public void onPageStarted(WebView webView2, String str3, Bitmap bitmap) {
                super.onPageStarted(webView2, str3, bitmap);
            }
        });
        webView.addJavascriptInterface(getHtmlFrom, "java_sc");
        webView.loadUrl(WithOutLoginUrlForProfile);
    }

    public void with_out_login_get_data_story_item_by_media(final String str, final String str2, final String str3, final WebView webView) {
        WithOutLoginUrlForProfile = "https://i.instagram.com/api/v1/feed/reels_media/?reel_ids=" + str;

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        WithOutLoginIsFinish = false;
        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 12_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 Instagram 105.0.0.11.118 (iPhone11,8; iOS 12_3_1; en_US; en-US; scale=2.00; 828x1792; 165586599);");
        WithOutLoginIsFinish = false;
        webView.setWebChromeClient(new WebChromeClient() { // from class: instagram.video.downloader.story.saver.insapp.api.6
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

                return super.onConsoleMessage(consoleMessage);
            }
        });
        webView.setWebViewClient(new WebViewClient() { // from class: instagram.video.downloader.story.saver.insapp.api.7
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str4) {
                super.onPageFinished(webView2, str4);
                if (!WithOutLoginIsFinish) {
                    WithOutLoginIsFinish = true;

                    if (!WithOutLoginUrlForProfile.replace("www.", "").equals(str4.replace("www.", ""))) {
                        webView.loadUrl("javascript:window.java_sc.with_out_login_get_story_userId_mediaId_Local('https://www.instagram.com/stories/" + str3 + "/" + str2 + "/');");

                        return;
                    }

                    webView.loadUrl("javascript:var a = document.getElementsByTagName('pre');if(a.length>0){window.java_sc.with_out_login_get_html_story_items_one(a[0].innerHTML,'" + str2 + "','" + str + "','" + str3 + "');}else{window.java_sc.with_out_login_get_story_userId_mediaId_Local('https://www.instagram.com/stories/" + str3 + "/" + str2 + "/');}");
                }
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str4, Bitmap bitmap) {
                super.onPageStarted(webView2, str4, bitmap);
            }
        });
        webView.addJavascriptInterface(getHtmlFrom, "java_sc");
        webView.loadUrl(WithOutLoginUrlForProfile);
    }

    public void with_out_login_get_data_story_item_by_media2(String str, final WebView webView) {
        WithOutLoginUrlForProfile = str;

        final String str2 = str.split("stories/")[1].split("/")[0];
        final String replace = str.split("stories/")[1].split("/")[1].split("\\?")[0].replace("/", "");
        webView.getSettings().setJavaScriptEnabled(true);
        WithOutLoginIsFinish = false;
        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 12_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 Instagram 105.0.0.11.118 (iPhone11,8; iOS 12_3_1; en_US; en-US; scale=2.00; 828x1792; 165586599);");
        WithOutLoginIsFinish = false;
        webView.setWebChromeClient(new WebChromeClient() { // from class: instagram.video.downloader.story.saver.insapp.api.8
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

                return super.onConsoleMessage(consoleMessage);
            }
        });
        webView.setWebViewClient(new WebViewClient() { // from class: instagram.video.downloader.story.saver.insapp.api.9
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str3) {
                super.onPageFinished(webView2, str3);
                if (!WithOutLoginIsFinish) {
                    WithOutLoginIsFinish = true;

                    String str4 = "https://storiesig.info/api/ig/story?url=" + WithOutLoginUrlForProfile;

                    if (!str4.replace("www.", "").equals(str3.replace("www.", ""))) {
                        webView.loadUrl("javascript:window.java_sc.download_by_server('" + WithOutLoginUrlForProfile + "');");

                        return;
                    }
                    webView.loadUrl("javascript:try{var a = document.getElementsByTagName('pre');if(a.length>0){window.java_sc.with_out_login_get_html_story_items_one_local_api(a[0].innerHTML,'" + replace + "','" + str2 + "');}else{window.java_sc.download_by_server('" + WithOutLoginUrlForProfile + "');}}catch(e){window.java_sc.download_by_server('" + WithOutLoginUrlForProfile + "');}");
                }
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str3, Bitmap bitmap) {
                super.onPageStarted(webView2, str3, bitmap);
            }
        });
        webView.addJavascriptInterface(getHtmlFrom, "java_sc");
        webView.loadUrl("https://storiesig.info/api/ig/story?url=" + WithOutLoginUrlForProfile);
    }

    interface getHtmlFrom {

        void getWithOutLoginHtml(String str, String str2);

        void with_out_login_get_html_story_data_by_user_id_and_story_id(String str, String str2, String str3);

        void with_out_login_get_html_story_items_one(String str, String str2, String str3, String str4);

        void with_out_login_get_html_story_items_one_local_api(String str, String str2, String str3);

    }

    private void WithOutLoginGraphQL(JSONObject jSONObject, String str) {
        String str2;
        String str3;
        ArrayList arrayList;
        String str4;
        String str5;
        String str6 = "is_video";
        String str7 = "display_url";
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("shortcode_media");
            String replace = jSONObject2.getJSONObject("owner").getString("username").replace("'", "''");
            try {
                str2 = jSONObject2.getJSONObject("edge_media_to_caption").getJSONArray("edges").getJSONObject(0).getJSONObject("node").getString("text").replace("'", "''");
            } catch (Exception unused) {
                str2 = "";
            }
            String string = jSONObject2.getJSONObject("owner").getString("profile_pic_url");
            boolean has = jSONObject2.has("edge_sidecar_to_children");
            String string2 = jSONObject2.getString(str7);
            boolean z = jSONObject2.getBoolean(str6);
            if (has) {
                ArrayList arrayList2 = new ArrayList();
                JSONArray jSONArray = jSONObject2.getJSONObject("edge_sidecar_to_children").getJSONArray("edges");
                int i = 0;
                while (i < jSONArray.length()) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i).getJSONObject("node");
                    JSONArray jSONArray2 = jSONArray;
                    String string3 = jSONObject3.getString(str7);
                    String str8 = str7;
                    boolean z2 = jSONObject3.getBoolean(str6);
                    if (z2) {
                        str5 = jSONObject3.getString("video_url");
                        str4 = str6;
                    } else {
                        str4 = str6;
                        str5 = "";
                    }
                    arrayList2.add(new WithOutLoginSlideDataTemp(z2, str5.replace("amp;", "").replace(";amp", ""), string3.replace("amp;", "").replace(";amp", "")));
                    i++;
                    jSONArray = jSONArray2;
                    str7 = str8;
                    str6 = str4;
                }
                str3 = "";
                arrayList = arrayList2;
            } else if (z) {
                str3 = jSONObject2.getString("video_url");
                arrayList = null;
            } else {
                arrayList = null;
                str3 = "";
            }
            String str9 = str.replace("amp;", "").replace(";amp", "").split("\\?")[0];
            withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(!str9.endsWith("/") ? str9 + "/" : str9, replace, str2, string.replace("amp;", "").replace(";amp", ""), string2.replace("amp;", "").replace(";amp", ""), has, z, str3.replace("amp;", ""), arrayList, false);
        } catch (Exception e) {

        }
    }

    private void WithOutLoginItems(JSONObject jSONObject, String str) {
        try {
            JSONObject item = jSONObject.getJSONArray("items").getJSONObject(0);
            String username = item.getJSONObject("user").getString("username").replace("'", "''");
            String profilePicUrl = item.getJSONObject("user").getString("profile_pic_url").replace("amp;", "").replace(";amp", "");
            String imageUrl = item.has("image_versions2") ? item.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url") : "";
            boolean hasCarouselMedia = item.has("carousel_media");
            boolean hasVideoVersions = item.has("video_versions");

            String caption = "";
            try {
                caption = item.getJSONObject("caption").getString("text").replace("'", "''");
            } catch (Exception ignored) {
            }

            String videoUrl = "";
            ArrayList<WithOutLoginSlideDataTemp> withOutLoginSlideDataTemps = null;

            if (hasCarouselMedia) {
                if (hasVideoVersions) {
                    videoUrl = item.getJSONArray("video_versions").getJSONObject(0).getString("url");
                } else {
                    videoUrl = "";
                }
                imageUrl = imageUrl;

            } else {
                withOutLoginSlideDataTemps = new ArrayList<>();
                JSONArray carouselArray = item.getJSONArray("carousel_media");
                imageUrl = carouselArray.getJSONObject(0).getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url");
                for (int i = 0; i < carouselArray.length(); i++) {
                    JSONObject carouselItem = carouselArray.getJSONObject(i);
                    boolean hasVideo = carouselItem.has("video_versions");
                    String carouselImageUrl = carouselItem.getJSONObject("image_versions2").getJSONArray("candidates").getJSONObject(0).getString("url");
                    String carouselVideoUrl = hasVideo ? carouselItem.getJSONArray("video_versions").getJSONObject(0).getString("url") : "";
                    withOutLoginSlideDataTemps.add(new WithOutLoginSlideDataTemp(hasVideo, carouselVideoUrl.replace("amp;", "").replace(";amp", ""), carouselImageUrl.replace("amp;", "").replace(";amp", "")));
                }
            }

            String profileUrl = WithOutLoginUrlForProfile.replace("amp;", "").replace(";amp", "").split("\\?")[0];
            withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(profileUrl.endsWith("/") ? profileUrl + "/" : profileUrl, username, caption, profilePicUrl, imageUrl, hasCarouselMedia, hasVideoVersions, videoUrl.replace("amp;", ""), withOutLoginSlideDataTemps, false);


        } catch (Exception e) {


            WithOutLoginGlobalOjTemp.mainApi.WithOutLoginGetDataRapidApi(str, context, withOutLoginInterfaceFragLinkTemp);
        }
    }
}
