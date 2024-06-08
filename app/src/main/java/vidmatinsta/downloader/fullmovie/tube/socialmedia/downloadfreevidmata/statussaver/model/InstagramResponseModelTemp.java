package vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import vidmatinsta.downloader.fullmovie.tube.socialmedia.downloadfreevidmata.statussaver.model.photo_video.Node;

@Keep
public class InstagramResponseModelTemp implements Serializable {
    @SerializedName("data")
    Data data;

    @Keep
    public class Data implements Serializable {
        @SerializedName("shortcode_media")
        shortcode_media shortcode_media;

        @Keep
        public class shortcode_media implements Serializable {
            @SerializedName("edge_sidecar_to_children")
            edge_sidecar_to_children edge_sidecar_to_children;
            @SerializedName("display_url")
            String display_url;
            @SerializedName("video_url")
            String video_url;

            public String getDisplay_url() {
                return display_url;
            }

            public void setDisplay_url(String display_url) {
                this.display_url = display_url;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            @Keep
            public class edge_sidecar_to_children implements Serializable {
                @SerializedName("edges")
                List<edges> edges;

                public class edges implements Serializable {
                    @SerializedName("node")
                    Node node;

                    public Node getNode() {
                        return node;
                    }

                    public void setNode(Node node) {
                        this.node = node;
                    }
                }

                public List<edges> getEdges() {
                    return edges;
                }

                public void setEdges(List<edges> edges) {
                    this.edges = edges;
                }
            }

            public Data.shortcode_media.edge_sidecar_to_children getEdge_sidecar_to_children() {
                return edge_sidecar_to_children;
            }

            public void setEdge_sidecar_to_children(Data.shortcode_media.edge_sidecar_to_children edge_sidecar_to_children) {
                this.edge_sidecar_to_children = edge_sidecar_to_children;
            }
        }

        public Data.shortcode_media getShortcode_media() {
            return shortcode_media;
        }

        public void setShortcode_media(Data.shortcode_media shortcode_media) {
            this.shortcode_media = shortcode_media;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
