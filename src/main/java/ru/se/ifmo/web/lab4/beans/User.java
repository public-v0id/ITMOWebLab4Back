package ru.se.ifmo.web.lab4.beans;

import jakarta.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Random;

@Entity(name="user")
@Table(name="users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="login")
    private String login;

    private String password;
    @Column(name="encodedPassword")
    private String encodedPassword;
    @Column(name="salt")
    private String salt;

    public User() {
        login = "";
        password = "";
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setEncodedPassword(String password) {
        try {
            this.encodedPassword = new String(MessageDigest.getInstance("MD5").digest((salt+password+"GRAFF").getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            this.encodedPassword = null;
        }
    }
    public String getEncodedPassword() {
        return encodedPassword;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }
}
