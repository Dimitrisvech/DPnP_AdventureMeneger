package Data;

/**
 * Created by Dimas on 18-May-16.
 */
public class User {
    private int uid;
    private String username;
    private String password;
    private String mail;

    public User(int uid, String username, String password, String mail) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
