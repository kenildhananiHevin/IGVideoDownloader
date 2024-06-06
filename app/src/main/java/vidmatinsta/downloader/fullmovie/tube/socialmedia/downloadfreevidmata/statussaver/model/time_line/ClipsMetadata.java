package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.time_line;

import androidx.annotation.Keep;

import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.AchievementsInfo;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.AdditionalAudioInfo;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.AudioRankingInfo;
import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.BrandedContentTagInfo;
@Keep
public class ClipsMetadata{
    public String audio_type;
    public String clips_creation_entry_point;
    public boolean disable_use_in_clips_client_cache;
    public boolean is_public_chat_welcome_video;
    public String music_canonical_id;
    public int professional_clips_upsell_type;
    public AchievementsInfo achievements_info;
    public AdditionalAudioInfo additional_audio_info;
    public BrandedContentTagInfo branded_content_tag_info;
    public ContentAppreciationInfo content_appreciation_info;
    public ArrayList<Object> cutout_sticker_info;
    public boolean is_shared_to_fb;
    public MashupInfo mashup_info;
    public String reusable_text_attribute_string;
    public ArrayList<ReusableTextInfo> reusable_text_info;
    public boolean show_achievements;
    public AudioRankingInfo audio_ranking_info;
    public MusicInfo music_info;
}