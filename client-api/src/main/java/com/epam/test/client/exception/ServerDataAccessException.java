package com.epam.test.client.exception;

public class ServerDataAccessException extends RuntimeException {

    public ServerDataAccessException(String message) { super(message);}

    public ServerDataAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
