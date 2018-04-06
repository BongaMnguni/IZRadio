package izradio.payghost.com.signup;

/**
 * Created by Payghost on 3/14/2018.
 */

public class SignupItems {

    private String id;
    private String name;
    private String number;
    private String school;

    public SignupItems(String name, String number, String school) {
        this.name = name;
        this.number = number;
        this.school = school;
    }

    public SignupItems(String id, String name, String number, String school) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
