package com.zhaomsdemo.research.datahub.exception;

public class TokenAuthExpiredException extends Exception{
    public TokenAuthExpiredException() {
        super();
    }

    public TokenAuthExpiredException(String message) {
        super(message);
    }
}
