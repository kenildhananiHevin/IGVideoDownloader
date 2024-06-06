package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post;

import androidx.annotation.Keep;

import java.util.ArrayList;


@Keep
public class MusicConsumptionInfo {
    public IgArtist ig_artist;
    public String placeholder_profile_pic_url;
    public boolean should_mute_audio;
    public String should_mute_audio_reason;
    public Object should_mute_audio_reason_type;
    public boolean is_bookmarked;
    public int overlap_duration_in_ms;
    public int audio_asset_start_time_in_ms;
    public boolean allow_media_creation_with_music;
    public boolean is_trending_in_clips;
    public Object trend_rank;
    public Object formatted_clips_media_count;
    public Object display_labels;
    public boolean should_allow_music_editing;
    public Object derived_content_id;
    public ArrayList<Object> audio_filter_infos;
    public AudioMutingInfo audio_muting_info;
    public Object contains_lyrics;
    public boolean should_render_soundwave;
}
