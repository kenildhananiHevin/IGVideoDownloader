package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.post;

import androidx.annotation.Keep;

import java.util.ArrayList;

@Keep
public class Root{
    public Object profile_grid_items;
    public Object profile_grid_items_cursor;
    public int num_results;
    public boolean more_available;
    public ArrayList<Item> items;
    public String next_max_id;
    public User user;
    public boolean auto_load_more_enabled;
    public String status;

    public ArrayList<Item> items() {
        return this.items;
    }

}