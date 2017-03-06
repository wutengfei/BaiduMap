package cn.busmap.model;

/**
 * Created by dell on 2016/9/18.
 */
public class User {
    private int id;
    private String username;
    private String password;


    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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


}
