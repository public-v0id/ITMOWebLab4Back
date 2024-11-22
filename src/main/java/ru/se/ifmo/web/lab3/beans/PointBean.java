package ru.se.ifmo.web.lab3.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "Point", eager = true)
@SessionScoped
public class PointBean implements Serializable {
    private double x;
    private double y;
    private double r;
    @PostConstruct
    public void init() {
        x = 0;
        y = 0;
        r = 3;
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
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setR(double r) {
        this.r = r;
    }
        @Override
    public String toString() {
        return "Point " + Double.toString(x) + " " + Double.toString(y) + " with radius " + Double.toString(r);
    }
}
