package com.jean.mybatis.generator.exception;

/**
 * @author jinshubao
 */
public class DatabaseNotSupportException extends RuntimeException {

    public DatabaseNotSupportException(String message) {
        super(message);
    }
}
