package ru.se.ifmo.web.lab4.cores;

import ru.se.ifmo.web.lab4.exceptions.WrongCoordinatesException;

public class Validator {
    public static boolean check(double x, double y, double r) throws WrongCoordinatesException{
        if (x < -3 || x > 5 || r < 1 || r > 5 || y < -5 || y > 3) {
            throw new WrongCoordinatesException("Wrong coordinates!");
        }
        return checkRect(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r);
    }

    private static boolean checkRect(double x, double y, double r) {
        return (y <= 0.5 * r) && (y >= 0) && (x >= 0) && (x <= r);
    }

    private static boolean checkTriangle(double x, double y, double r) {
        return (y >= 0) && (y <= 0.5*r) && (x <= 0) && (x >= -0.5*r) && (y <= 0.5*x+r/2);
    }

    private static boolean checkCircle(double x, double y, double r) {
        return (y <= 0) && (y >= -r/2) && (x <= 0) && (x >= -r/2) && (r*r/4 >= y*y+x*x);
    }
}