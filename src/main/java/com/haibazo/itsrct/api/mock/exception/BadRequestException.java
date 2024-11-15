package com.haibazo.itsrct.api.mock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the request is malformed or invalid.
 * Maps to HTTP 400 Bad Request responses.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ServerErrorException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BadRequestException with the specified error code.
     *
     * @param errorCode the error code identifying the type of error
     */
    public BadRequestException(String errorCode) {
        super(errorCode);
    }

    /**
     * Constructs a new BadRequestException with an error code and cause.
     *
     * @param errorCode the error code identifying the type of error
     * @param cause     the cause of this exception
     */
    public BadRequestException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}