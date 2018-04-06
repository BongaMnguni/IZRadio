package izradio.payghost.com.CRUD;

class Hero {
    private int id;
    private String title;
    private String image;
    private String description;
    private String branch;
    private String price;

    public Hero(int id, String title, String image, String description, String branch, String price) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.branch = branch;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getBranch() {
        return branch;
    }

    public String getPrice() {
        return price;
    }
}
