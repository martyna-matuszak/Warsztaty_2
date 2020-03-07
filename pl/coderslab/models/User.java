package pl.coderslab.models;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.DBUtil;

import java.sql.*;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private int userGroupId;

    public User(String username, String email, String password, int userGroupId){
        this.username = username;
        this.email = email;
        this.hashPassword(password);
        this.userGroupId = userGroupId;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", username: " + this.username + ", email: " + this.email + ", group id: " + this.userGroupId;
    }
}
