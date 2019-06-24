package com.doyd.core.exceptions;

/**
 * @author zhouzq
 */
public class RestStatusException extends RuntimeException {

    private static final long serialVersionUID = -1568408074951438212L;

    public RestStatusException(String message) {
        super(message);
    }

    public RestStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestStatusException(Throwable cause) {
        super(cause);
    }

    protected RestStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
