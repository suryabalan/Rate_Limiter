package com.example.ratelimiter.exception;

public class RateLimitException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RateLimitException(String message){
        super(message);
    }
}
