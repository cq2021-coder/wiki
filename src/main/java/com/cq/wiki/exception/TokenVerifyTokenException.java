package com.cq.wiki.exception;

public class TokenVerifyTokenException extends RuntimeException{
    public TokenVerifyTokenException(String message) {
        this(message, (Throwable)null);
    }

    public TokenVerifyTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
