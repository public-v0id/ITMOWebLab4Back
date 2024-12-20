package ru.se.ifmo.web.lab4.exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
