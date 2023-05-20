package com.z.persistence.exceptions;

/**
 * CustomEtcdException
 *
 * @author Lee
 * @date 2023/5/20
 */
public class CustomEtcdException extends RuntimeException {

    public CustomEtcdException(String message, Throwable cause) {
        super(message, cause);
    }
}
