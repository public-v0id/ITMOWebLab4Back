package ru.se.ifmo.web.lab2.classes;

import java.util.logging.*;
import ru.se.ifmo.web.lab2.exceptions.*;

public class Parameters {
    private double x;
    private double y;
    private double r;

    public Parameters(double x, double y, double r) throws ParametersException {
        if (y > 5 || y < -3 || r < 1 || r > 3 || x < -5 || x > 3) {
            throw new ParametersException("Invalid Parameters! Couldn't create object");
        }
        this.x = x;
        this.y = y;
        this.r = r;
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
}