package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post;

import androidx.annotation.Keep;

import java.util.ArrayList;


@Keep
public class MusicAssetInfo {
    public String audio_asset_id;
    public String audio_cluster_id;
    public String id;
    public String title;
    public Object sanitized_title;
    public String subtitle;
    public String display_artist;
    public String artist_id;
    public boolean is_explicit;
    public String cover_artwork_uri;
    public String cover_artwork_thumbnail_uri;
    public String progressive_download_url;
    public Object reactive_audio_download_url;
    public String fast_start_progressive_download_url;
    public Object web_30s_preview_download_url;
    public ArrayList<Integer> highlight_start_times_in_ms;
    public Object dash_manifest;
    public boolean has_lyrics;
    public int duration_in_ms;
    public Object dark_message;
    public boolean allows_saving;
    public String ig_username;
    public boolean is_eligible_for_audio_effects;
}
