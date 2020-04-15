package com.server.config;

/**
 * @Author gg.rao
 * @Date 2019/4/17 19:13
 */
public class UnableToAquireLockException extends RuntimeException{

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
