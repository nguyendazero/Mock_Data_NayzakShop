package com.haibazo.itsrct.api.mock.dto.response;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Builder class for constructing API responses.
 * Provides a fluent interface for creating standardized API responses.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ApiResponseDto {

    /**
     * Creates a new builder instance with the specified HTTP status
     * 
     * @param status HTTP status for the response
     * @return new builder instance
     */
    public static ApiResponseDto status(HttpStatus status) {
        Assert.notNull(status, "HttpStatus must not be null");
        ApiResponseDto builder = new ApiResponseDto();
        builder.status = status;
        return builder;
    }

    /**
     * Utility method for creating successful (200 OK) responses without body.
     * 
     * @return ResponseEntity wrapped ApiResponseDto with OK status
     * @see HttpStatus#OK
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> ok() {
        return status(HttpStatus.OK).build();
    }

    /**
     * Creates a successful (200 OK) response with a custom body.
     * 
     * @param body the response payload to include
     * @return ResponseEntity wrapped ApiResponseDto with OK status and body
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> ok(Object body) {
        return status(HttpStatus.OK).body(body).build();
    }

    /**
     * Utility method for creating error (500) responses
     * 
     * @return builder instance
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> error() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Creates an error response with a custom HTTP status code.
     * Use this method when you need to return a specific error status.
     * 
     * @param status the HTTP status code for the error response
     * @return ResponseEntity wrapped ApiResponseDto with the specified error status
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> error(HttpStatus status) {
        return status(status).build();
    }

    /**
     * Creates an error response with a custom HTTP status and message.
     * 
     * @param status  the HTTP status code for the error response
     * @param message the error message to include in the response
     * @return ResponseEntity wrapped ApiResponseDto with the specified status and
     *         message
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> error(HttpStatus status, String message) {
        return status(status).message(message).build();
    }

    /**
     * Creates an error response with a custom HTTP status and response body.
     * 
     * @param status the HTTP status code for the error response
     * @param body   the response payload to include in the error response
     * @return ResponseEntity wrapped ApiResponseDto with the specified status and
     *         body
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> error(HttpStatus status, Object body) {
        return status(status).body(body).build();
    }

    /**
     * Creates a Bad Request (400) response without body.
     * 
     * @return ResponseEntity wrapped ApiResponseDto with Bad Request status
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> badRequest() {
        return status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Creates a Bad Request (400) response with a custom message.
     * 
     * @param message the error message to include in the response
     * @return ResponseEntity wrapped ApiResponseDto with Bad Request status and
     *         message
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> badRequest(String message) {
        return status(HttpStatus.BAD_REQUEST).message(message).build();
    }

    /**
     * Creates a Bad Request (400) response with a custom body.
     * 
     * @param body the response payload to include
     * @return ResponseEntity wrapped ApiResponseDto with Bad Request status and
     *         body
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> badRequest(Object body) {
        return status(HttpStatus.BAD_REQUEST).body(body).build();
    }

    /**
     * Creates a Bad Request (400) response with a body parsed from an error DTO.
     * 
     * @param errorDto the error details to include in the response
     * @return ResponseEntity wrapped ApiResponseDto with Bad Request status and
     *         error details
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> badRequest(ApiResponseErrorDto errorDto) {
        return status(HttpStatus.NOT_FOUND).code(errorDto.getErrorCode()).message(errorDto.getErrorMessage()).build();
    }

    /**
     * Creates a Not Found (404) response without body.
     * 
     * @return ResponseEntity wrapped ApiResponseDto with Not Found status
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> notFound() {
        return status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Creates a Not Found (404) response with a custom body.
     * 
     * @param body the response payload to include
     * @return ResponseEntity wrapped ApiResponseDto with Not Found status and
     *         body
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> notFound(Object body) {
        return status(HttpStatus.NOT_FOUND).body(body).build();
    }

    /**
     * Creates a Not Found (404) response with a body parsed from an error DTO.
     * 
     * @param errorDto the error details to include in the response
     * @return ResponseEntity wrapped ApiResponseDto with Not Found status and
     *         error details
     */
    public static ResponseEntity<ApiResponseBaseDto<Object>> notFound(ApiResponseErrorDto errorDto) {
        ApiResponseDto response = ApiResponseDto.status(HttpStatus.NOT_FOUND).code(errorDto.getErrorCode())
                .message(errorDto.getErrorMessage());

        response.message = errorDto.getException().getMessage();
        response.error = errorDto.getException();

        return response.build();
    }

    private HttpStatus status;

    private Object data;

    private String message;

    private String code;

    private MetaDataDto meta;

    private Exception error;

    /**
     * Sets the response body
     * 
     * @param body response payload
     * @return builder instance
     */
    public ApiResponseDto body(Object body) {
        this.data = body;
        return this;
    }

    /**
     * Sets a custom message
     * 
     * @param message custom message
     * @return builder instance
     */
    public ApiResponseDto message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Sets a meta object
     * 
     * @param meta Pageable object
     * @return builder instance
     */

    public ApiResponseDto meta(MetaDataDto meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Sets a response code
     * 
     * @param code response code
     * @return builder instance
     */
    public ApiResponseDto code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Builds the final response entity
     * 
     * @param <T> type of response body
     * @return ResponseEntity with ApiResponseDto
     */
    @SuppressWarnings("unchecked")
    public <T> ResponseEntity<ApiResponseBaseDto<T>> build() {
        Assert.notNull(status, "Status must not be null");

        MetaDataDto metaDataDto = null;
        if (data instanceof List<?>) {
            List<?> dataList = (List<?>) data;
            int totalItems = dataList.size();
            metaDataDto = new MetaDataDto(1, 1, totalItems);
        }

        ApiResponseBaseDto<T> response = new ApiResponseBaseDto<>(
                status,
                (T) data,
                message,
                code,
                metaDataDto,
                error);

        return ResponseEntity
                .status(status)
                .body(response);
    }

}