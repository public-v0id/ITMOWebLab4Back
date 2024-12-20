package ru.se.ifmo.web.lab4.exceptions;

public class UnknownLoginException extends Exception{
    public UnknownLoginException(String msg) {
        super(msg);
    }
}
