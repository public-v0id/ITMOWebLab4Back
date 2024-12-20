package ru.se.ifmo.web.lab4.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="results")
@Table(name="results")
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "x")
    private double x;
    @Column (name = "y")
    private double y;
    @Column (name = "r")
    private double r;
    @Column (name = "res")
    private boolean res;
    @Column (name = "exTime")
    private double exTime;
    @Column (name = "servTime")
    private String servTime;
    @Column (name = "login")
    private String login;

    public Results() {
        x = 0;
        y = 0;
        r = 0;
        res = false;
        exTime = 0;
        servTime = "";
        login = "";
    }

    public Results(double x, double y, double r, boolean res, double exTime, String servTime, String login) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = res;
        this.exTime = exTime;
        this.servTime = servTime;
        this.login = login;
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

    public void setRes(boolean res) {
        this.res = res;
    }

    public void setExTime(double exTime) {
        this.exTime = exTime;
    }

    public void setServTime(String servTime) {
        this.servTime = servTime;
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

    public boolean getRes() {
        return res;
    }

    public double getExTime() {
        return exTime;
    }

    public String getServTime() {
        return servTime;
    }

    public String getLogin() {
        return login;
    }
}
