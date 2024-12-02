package com.haibazo.bff.mock.webapi.exception;

/**
 * Base exception class for server-side errors.
 * Provides error code and message handling functionality.
 */
public abstract class ServerErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final String errorMessage;

    /**
     * Constructs a new exception with the specified error code.
     *
     * @param errorCode the error code
     * @throws IllegalArgumentException if errorCode is null
     */
    public ServerErrorException(String errorCode) {
        super(errorCode);
        validateErrorCode(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = null;
    }

    /**
     * Constructs a new exception with the specified error code and message.
     *
     * @param errorCode the error code
     * @param message   detailed error message
     * @throws IllegalArgumentException if errorCode is null
     */
    public ServerErrorException(String errorCode, String message) {
        super(message);
        validateErrorCode(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * Constructs a new exception with the specified error code and cause.
     *
     * @param errorCode the error code
     * @param cause     the cause of this exception
     * @throws IllegalArgumentException if errorCode is null
     */
    public ServerErrorException(String errorCode, Throwable cause) {
        super(cause);
        validateErrorCode(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = cause.getMessage();
    }

    /**
     * Constructs a new exception with error code, message and cause.
     *
     * @param errorCode the error code
     * @param message   detailed error message
     * @param cause     the cause of this exception
     * @throws IllegalArgumentException if errorCode is null
     */
    public ServerErrorException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        validateErrorCode(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * Validates the error code parameter.
     * 
     * @param errorCode the error code to validate
     * @throws IllegalArgumentException if errorCode is null
     */
    private void validateErrorCode(String errorCode) {
        if (errorCode == null) {
            throw new IllegalArgumentException("Error code cannot be null");
        }
    }

    /**
     * Returns the error code associated with this exception.
     * 
     * @return the error code string
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the detailed error message associated with this exception.
     * 
     * @return the error message string, may be null
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}