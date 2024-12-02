package com.haibazo.bff.mock.webapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the request is malformed or invalid.
 * Maps to HTTP 404 Not Found responses.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ServerErrorException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BadRequestException with the specified error code.
     *
     * @param errorCode the error code identifying the type of error
     */
    public NotFoundException(String errorCode) {
        super(errorCode);
    }

    /**
     * Constructs a new BadRequestException with an error code and cause.
     *
     * @param errorCode the error code identifying the type of error
     * @param cause     the cause of this exception
     */
    public NotFoundException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}