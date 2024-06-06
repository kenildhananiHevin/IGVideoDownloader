package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.story_show;

import androidx.annotation.Keep;

import java.util.ArrayList;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post.FanClubInfo;

@Keep
public class User{
    public long pk;
    public String pk_id;
    public String username;
    public String full_name;
    public boolean is_private;
    public String strong_id__;
    public boolean is_verified;
    public String profile_pic_id;
    public String profile_pic_url;
    public FriendshipStatus friendship_status;
    public long interop_messaging_user_fbid;
    public Object fbid_v2;
    public boolean feed_post_reshare_disabled;
    public String id;
    public boolean is_unpublished;
    public boolean show_account_transparency_details;
    public int third_party_downloads_enabled;
    public ArrayList<Object> account_badges;
    public FanClubInfo fan_club_info;
    public boolean has_anonymous_profile_picture;
    public boolean is_favorite;
    public boolean transparency_product_enabled;
}
