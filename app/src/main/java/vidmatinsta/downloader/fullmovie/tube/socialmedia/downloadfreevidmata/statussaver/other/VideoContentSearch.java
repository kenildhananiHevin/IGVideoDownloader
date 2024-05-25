/*
 * Copyright (c) 2021.  Hurricane Development Studios
 */

package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.other;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public abstract class VideoContentSearch extends Thread {

    private static final String VIDEO = "video";
    private static final String AUDIO = "audio";
    private static final String LENGTH = "content-length";
    private static final String TWITTER = "twitter.com";
    private static final String METACAFE = "metacafe.com";
    private static final String MYSPACE = "myspace.com";
    private Context context;
    private String url;
    private String mainUrl;
    private String page;
    private String title;
    private int numLinksInspected;


    public abstract void onStartInspectingURL();

    public abstract void onFinishedInspectingURL(boolean finishedAll);

    public abstract void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio);

    public VideoContentSearch(Context context, String url, String page, String title, String mainUrl) {
        this.context = context;
        this.url = url;
        this.mainUrl = mainUrl;
        this.page = page;
        this.title = title;
        numLinksInspected = 0;

    }

    @Override
    public void run() {
        String urlLowerCase = url.toLowerCase();
        String[] filters = context.getResources().getStringArray(R.array.videourl_filters);
        boolean urlMightBeVideo = false;
        for (String filter : filters) {
            if (urlLowerCase.contains(filter)) {
                urlMightBeVideo = true;
                break;
            }
        }
        if (true) {
            numLinksInspected++;
            onStartInspectingURL();

            URLConnection uCon = null;
            try {
                uCon = new URL(url).openConnection();
                uCon.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (uCon != null) {
                String contentType = uCon.getHeaderField("content-type");
                if (url.contains("reel")) {
                    isVideo = true;
                }
                if (contentType != null) {
                    contentType = contentType.toLowerCase();
                    if (contentType.contains(VIDEO) && isVideo) {
                        addVideoToList(uCon, page, title, contentType);
                    } else if (contentType.equals("application/x-mpegurl") || contentType.equals("application/vnd.apple.mpegurl")) {
                        addVideosToListFromM3U8(uCon, page, title);
                    } else if (contentType.contains("image/jpeg") && !isVideo) {
                        addVideoToList(uCon, page, title, contentType);
                    }
                }
            }

            numLinksInspected--;
            boolean finishedAll = false;
            if (numLinksInspected <= 0) {
                finishedAll = true;
            }
            onFinishedInspectingURL(finishedAll);
        }
    }


    private void addVideoToList(URLConnection uCon, String page, String title, String contentType) {
        try {
            String size = uCon.getHeaderField(LENGTH);
            String link = uCon.getHeaderField("Location");

            if (link == null) {
                link = uCon.getURL().toString();
            }
            Log.e("TAGL", "addVideoToList: " + link);
            String host = new URL(page).getHost();
            String website = null;
            boolean chunked = false;
            String type;
            boolean audio = false;

            if (host.contains(TWITTER) && contentType.equals("video/mp2t")) {
                return;
            }

            String name = VIDEO;
            if (title != null) {
                if (contentType.contains(AUDIO)) {
                    name = "[AUDIO ONLY]" + title;
                } else {
                    name = title;
                }
            } else if (contentType.contains(AUDIO)) {
                name = AUDIO;
            }

            if (host.contains("instagram.com")) {
                try {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(link, new HashMap<String, String>());
                    retriever.release();
                    audio = false;
                } catch (RuntimeException ex) {
                    audio = true;
                }
            }

            switch (contentType) {
                case "image/jpeg":
                    type = "jpg";
                    break;
                case "video/mp4":
                    type = "mp4";
                    break;
                case "video/webm":
                    type = "webm";
                    break;
                case "video/mp2t":
                    type = "ts";
                    break;
                case "audio/webm":
                    type = "webm";
                    break;
                default:
                    type = "mp4";
                    break;
            }
            Log.d("TAG", "addVideoToList: " + type);

           /* if (type.equals("jpg") && (link.contains("p1080") || link.contains("p750") || link.contains("p512"))) {
                if ((link.contains("p1080") || link.contains("p750") || link.contains("p512")) && !firstJpegFound) {
                    Log.d("TAGjkl", "addVideoToList: first found");
                    firstJpegFound = true;
                    onVideoFound(size, type, link, name, page, chunked, website, audio);
                }
            }*/
            if (type.equals("jpg") && (!link.contains("s150"))) {
                if ( (!link.contains("s150")) && !firstJpegFound) {
                    Log.d("TAGjkl", "addVideoToList: first found");
                    firstJpegFound = true;
                    onVideoFound(size, type, link, name, page, chunked, website, audio);
                }
            } else {
                if (!firstJpegFound) {
                    if (mainUrl.contains("reel")) {
                        onVideoFound(size, type, link, name, page, chunked, website, audio);
                    }
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static boolean firstJpegFound = false;
    public static boolean isVideo = false;

    private void addVideosToListFromM3U8(URLConnection uCon, String page, String title) {
        try {
            String host;
            Boolean audio = false;
            host = new URL(page).getHost();
            if (host.contains(TWITTER) || host.contains(METACAFE) || host.contains(MYSPACE)) {
                InputStream in = uCon.getInputStream();
                InputStreamReader inReader = new InputStreamReader(in);
                BufferedReader buffReader = new BufferedReader(inReader);
                String line;
                String prefix = null;
                String type = null;
                String name = VIDEO;
                String website = null;
                if (title != null) {
                    name = title;
                }
                if (host.contains(TWITTER)) {
                    prefix = "https://video.twimg.com";
                    type = "ts";
                    website = TWITTER;
                } else if (host.contains(METACAFE)) {
                    String link = uCon.getURL().toString();
                    prefix = link.substring(0, link.lastIndexOf("/") + 1);
                    website = METACAFE;
                    type = "mp4";
                } else if (host.contains(MYSPACE)) {
                    String link = uCon.getURL().toString();
                    website = MYSPACE;
                    type = "ts";
                    onVideoFound(null, type, link, name, page, true, website, audio);
                    return;
                }
                while ((line = buffReader.readLine()) != null) {
                    if (line.endsWith(".m3u8")) {
                        String link = prefix + line;
                        onVideoFound(null, type, link, name, page, true, website, audio);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
