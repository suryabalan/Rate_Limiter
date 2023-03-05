package com.example.ratelimiter.exception;

public enum StatusCode{
    REQUEST_LIMIT_REACHED(100),
    UNABLE_TO_ADD_LIMIT(101),
    LIMIT_ADDED_SUCCESSFULLY(102);

    private int value;

    StatusCode(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}