package izradio.payghost.com.Gallery;

/**
 * Created by Payghost on 10/19/2017.
 */

public class GalleryItems {
    private String id;
    private String image;
    public GalleryItems(){}

    public GalleryItems(String image) {
        this.image = image;
    }

    public GalleryItems(String image, String id) {
        this.id = id;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
