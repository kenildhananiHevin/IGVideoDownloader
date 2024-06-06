package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show;

import androidx.annotation.Keep;

import java.util.ArrayList;
@Keep
public class Reel{
    public long id;
    public String strong_id__;
    public int latest_reel_media;
    public int seen;
    public boolean can_reply;
    public boolean can_gif_quick_reply;
    public boolean can_reshare;
    public boolean can_react_with_avatar;
    public String reel_type;
    public int expiring_at;
    public User user;
    public ArrayList<Item> items;
    public boolean is_nux;
    public int prefetch_count;
    public boolean has_besties_media;
    public int media_count;
    public ArrayList<Object> media_ids;
    public boolean has_fan_club_media;
    public boolean show_fan_club_stories_teaser;
    public ArrayList<String> disabled_reply_types;
}