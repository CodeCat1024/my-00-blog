package com.example.demo;

public class UserNameException extends RuntimeException{
    public UserNameException() {
        super();
    }
    public UserNameException(String s) {
        super(s);
    }
}