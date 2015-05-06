package com.changxx.practice.http;

public class CustomException extends Exception {

    private static final long serialVersionUID = 2575009263805908068L;

    public CustomException(String msg) {
        super(msg);
    }
}
