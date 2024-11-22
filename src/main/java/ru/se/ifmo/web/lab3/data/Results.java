package ru.se.ifmo.web.lab3.data;

import javax.persistence.*;

@Entity
@Table(name = "lab3res")
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "x")
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

    public Results() {
        x = 0;
        y = 0;
        r = 0;
        res = false;
        exTime = 0;
        servTime = "";
    }

    public Results(double x, double y, double r, boolean res, double exTime, String servTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = res;
        this.exTime = exTime;
        this.servTime = servTime;
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
}
