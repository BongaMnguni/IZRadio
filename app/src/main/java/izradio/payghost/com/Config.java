package izradio.payghost.com;

/**
 * Created by 21428 on 10/18/2017.
 */

public class Config {

    //Gallary
    public static final String URL_GET_GALLERY = "http://mydm.co.za/IZRadio/Galleryretrieve.php";
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_IMAGE ="image";

    // delete
    public static final String TAG_ID = "id";
    public static final String URL_DELETE = "http://mydm.co.za/IZRadio/delete.php";

    //Documents Retrival
    public static final String URL_GET_ALL_FILESs = "http://mydm.co.za/IZRadio/RetrieveFiles.php";
    public static final String TAG_FILE_NAME = "category";
    public static final String TAG_FILE_URL = "url";

    //Post
    public static final String URL_GET_ALL_POST = "http://mydm.co.za/IZRadio/postretrieve.php";
    public static final String TAG_POST_NAME = "Name";
    public static final String TAG_POST_SENDER = "Sender";
    public static final String TAG_POST_MESSAGE = "Message";
    public static final String TAG_POST_DATE = "Date";
    public static final String TAG_POST_IMAGE = "Image";

    public static final String URL_GET_VIDEOS = "http://mydm.co.za/IZRadio/Videoretrieve.php";
    public static final String TAG_VIDEO = "video";

    //Sign up
    public static final String URL_INSERT_MEMBERS = "http://mydm.co.za/IZRadio/InsertSignup.php";
    public static final String URL_GET_MEMBERS = "http://mydm.co.za/IZRadio/RetrieveSignup.php";
    public static final String TAG_POST_NAMES = "name";
    public static final String TAG_POST_SCHOOL_NAME = "school";
    public static final String TAG_POST_NUMBER = "number";



}
