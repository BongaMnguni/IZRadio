package izradio.payghost.com.Tvshow;

/**
 * Created by 21428 on 2/1/2018.
 */

public class ShowItems {
    private String id;
    private String name;
    private String image;
    private String date;
    private String message;
    private String sender;

    public ShowItems(){}

    public ShowItems(String name, String image, String date, String message, String sender) {
        this.name = name;
        this.image = image;
        this.date = date;
        this.message = message;
        this.sender = sender;
    }

    public ShowItems(String id, String name, String image, String date, String message, String sender) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.date = date;
        this.message = message;
        this.sender = sender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
