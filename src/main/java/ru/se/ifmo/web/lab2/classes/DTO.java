package ru.se.ifmo.web.lab2.classes;

public class DTO {
    private String x;
    private String y;
    private String r;
    private String res;
    private String exTime;
    private String servTime;

    public DTO(String x, String y, String r, String res, String exTime, String servTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = res;
        this.exTime = exTime;
        this.servTime = servTime;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getR() {
        return r;
    }

    public String getRes() {
        return res;
    }

    public String getExTime() {
        return exTime;
    }

    public String getServTime() {
        return servTime;
    }
}
