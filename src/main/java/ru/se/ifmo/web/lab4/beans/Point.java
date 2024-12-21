package ru.se.ifmo.web.lab4.beans;

import jakarta.ejb.Stateless;
import java.io.Serializable;
import static java.lang.Math.*;

public class Point implements Serializable {
    private double x;
    private double y;
    private double r;
    private String login;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public String getLogin() {
        return login;
    }
}
