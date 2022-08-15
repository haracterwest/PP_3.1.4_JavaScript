package ru.kata.spring.boot_security.demo.exception;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }
}
