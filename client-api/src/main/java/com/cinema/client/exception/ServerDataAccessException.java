package com.cinema.client.exception;

public class ServerDataAccessException extends RuntimeException {

    public ServerDataAccessException(String s) {
        super(s);
    }

    public ServerDataAccessException(String s, Throwable throwable) {
        super(s, throwable);
    }
}