package ru.se.ifmo.web.lab4.exceptions;

public class UserExistsException extends Exception{
    public UserExistsException(String msg) {
        super(msg);
    }
}
