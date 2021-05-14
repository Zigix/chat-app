package com.zigix.chatapp.exception;

public class TokenNoExpiredException extends RuntimeException {

    public TokenNoExpiredException(String message) {
        super(message);
    }

}
