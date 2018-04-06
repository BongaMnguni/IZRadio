package izradio.payghost.com.videos;

import android.graphics.Bitmap;

/**
 * Created by Payghost on 3/26/2018.
 */

public class videoItems {
    private String time;
    private Bitmap videoURL;
    private String url;
    private String id;

    public videoItems(String time, Bitmap videoURL,String url) {
        this.time = time;
        this.videoURL = videoURL;
        this.url = url;
    }

    public videoItems(String time, Bitmap videoURL, String url, String id) {
        this.time = time;
        this.videoURL = videoURL;
        this.url = url;
        this.id = id;
    }


    public Bitmap getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(Bitmap videoURL) {
        this.videoURL = videoURL;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
