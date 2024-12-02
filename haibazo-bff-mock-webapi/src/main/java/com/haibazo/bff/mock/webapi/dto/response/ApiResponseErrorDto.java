package com.haibazo.bff.mock.webapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Value;

import java.util.Collections;
import java.util.Map;

/**
 * DTO for standardized error responses across the API.
 * Contains error code, message and optional field-level errors.
 */
@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseErrorDto {

    /**
     * Creates an error response with code and message
     * 
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public static ApiResponseErrorDto of(String errorCode, String errorMessage) {
        return ApiResponseErrorDto.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }

    /**
     * Creates an error response with code and field errors
     * 
     * @param errorCode
     * @param errors
     * @return
     */
    public static ApiResponseErrorDto of(String errorCode, Map<String, String> errors) {
        return ApiResponseErrorDto.builder()
                .errorCode(errorCode)
                .errors(Collections.unmodifiableMap(errors))
                .build();
    }

    /**
     * Creates an error response with code, message and field errors
     * 
     * @param errorCode
     * @param errorMessage
     * @param errors
     * @return
     */
    public static ApiResponseErrorDto of(String errorCode, String errorMessage, Map<String, String> errors) {
        return ApiResponseErrorDto.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .errors(Collections.unmodifiableMap(errors))
                .build();
    }

    /**
     * Creates an error response with code and exception
     * 
     * @param errorCode
     * @param exception
     * @return
     */
    public static ApiResponseErrorDto of(String errorCode, Exception exception) {
        return ApiResponseErrorDto.builder()
                .errorCode(errorCode)
                .exception(exception)
                .build();
    }

    @NotBlank(message = "Error code is required")
    String errorCode;

    String errorMessage;

    @Builder.Default
    Map<String, String> errors = Collections.emptyMap();

    Exception exception;

    /**
     * Returns an immutable view of the errors map
     * 
     * @return
     */
    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

}