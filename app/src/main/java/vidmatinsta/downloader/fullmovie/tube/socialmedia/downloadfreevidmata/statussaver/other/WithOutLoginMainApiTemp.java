package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.WithOutLoginSlideDataTemp;

public class WithOutLoginMainApiTemp {
    List<Boolean> is_get = new ArrayList();
    private RequestQueue requestQueue;

    public WithOutLoginMainApiTemp(Context context) {
    }

    public void WithOutLoginGetDataRapidApi(final String str, Context context, final WithOutLoginInterfaceFragLinkTemp withOutLoginInterfaceFragLinkTemp) {
        this.is_get.size();
        this.is_get.add(false);
        String str2 = "https://instagram-post-reels-stories-downloader.p.rapidapi.com/instagram/?url=" + str.split("\\?")[0];
        
        StringRequest stringRequest = new StringRequest(0, str2, new Response.Listener<String>() { // from class: instagram.video.downloader.story.saver.insapp.Apis.MainApi.2
            @Override
            public void onResponse(String str3) {
                try {
                    
                    JsonObject asJsonObject = JsonParser.parseString(str3).getAsJsonObject();
                    if (asJsonObject.get("status").getAsBoolean()) {
                        JsonArray asJsonArray = asJsonObject.get("result").getAsJsonArray();
                        if (asJsonArray.size() > 0) {
                            if (asJsonArray.size() == 1) {
                                JsonObject asJsonObject2 = asJsonArray.get(0).getAsJsonObject();
                                if (asJsonObject2.get("type").getAsString().equals("image/jpeg")) {
                                    withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(str, "", "", asJsonObject2.get("url").getAsString().replace("amp;", "").replace(";amp", ""), asJsonObject2.get("url").getAsString().replace("amp;", "").replace(";amp", ""), false, false, "", null, false);
                                    return;
                                } else {
                                    withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(str, "", "", asJsonObject2.get("url").getAsString().replace("amp;", "").replace(";amp", ""), asJsonObject2.get("url").getAsString().replace("amp;", "").replace(";amp", ""), false, true, asJsonObject2.get("url").getAsString().replace("amp;", "").replace(";amp", ""), null, false);
                                    return;
                                }
                            }
                            ArrayList<WithOutLoginSlideDataTemp> arrayList = new ArrayList<>();
                            for (int i = 0; i < asJsonArray.size(); i++) {
                                JsonObject asJsonObject3 = asJsonArray.get(i).getAsJsonObject();
                                if (asJsonObject3.get("type").getAsString().equals("image/jpeg")) {
                                    arrayList.add(new WithOutLoginSlideDataTemp(false, "", asJsonObject3.get("url").getAsString().replace("amp;", "").replace(";amp", "")));
                                } else {
                                    arrayList.add(new WithOutLoginSlideDataTemp(true, asJsonObject3.get("url").getAsString().replace("amp;", "").replace(";amp", ""), asJsonObject3.get("url").getAsString().replace("amp;", "").replace(";amp", "")));
                                }
                            }
                            withOutLoginInterfaceFragLinkTemp.WithOutLoginGetResult(str, "", "", asJsonArray.get(0).getAsJsonObject().get("url").getAsString().replace("amp;", "").replace(";amp", ""), asJsonArray.get(0).getAsJsonObject().get("url").getAsString().replace("amp;", "").replace(";amp", ""), true, false, "", arrayList, false);
                            return;
                        }
                        
//                        interface_frag_linkVar.login();
                        return;
                    }
//                    interface_frag_linkVar.login();
                } catch (Exception unused) {
//                    interface_frag_linkVar.login();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                WithOutLoginGetDataRapidApi(str, context, withOutLoginInterfaceFragLinkTemp);
//                interface_frag_linkVar.login();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("X-RapidAPI-Key", "7a9a1d8bb8msh27eb7eeaf12d1bfp1f3ff7jsnf669a8c4b858");
                hashMap.put("X-RapidAPI-Host", "instagram-post-reels-stories-downloader.p.rapidapi.com");
                return hashMap;
            }
        };
        if (this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(context);
        }
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
        stringRequest.setShouldCache(false);
        this.requestQueue.add(stringRequest);
    }

}
