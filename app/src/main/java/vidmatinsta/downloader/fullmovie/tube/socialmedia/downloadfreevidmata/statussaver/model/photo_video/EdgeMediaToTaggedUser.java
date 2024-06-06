package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EdgeMediaToTaggedUser {

@SerializedName("edges")
@Expose
private List<Object> edges;

public List<Object> getEdges() {
return edges;
}

public void setEdges(List<Object> edges) {
this.edges = edges;
}

}