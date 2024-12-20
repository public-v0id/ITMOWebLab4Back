package ru.se.ifmo.web.lab4.exceptions;

public class WrongCoordinatesException extends Exception{
    public WrongCoordinatesException(String msg) {
        super(msg);
    }
}