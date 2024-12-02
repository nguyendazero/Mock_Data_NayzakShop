package com.haibazo.bff.mock.webapi.dto.internal;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object representing API mock settings configuration.
 * Contains all necessary information to create and serve mock API responses.
 */
@Data
@Builder
public class ApiMockSettingDto {

    /**
     * The URI pattern to match for this mock response
     */
    @NotBlank()
    @Pattern(regexp = "^/.*")
    private String uri;

    /**
     * Path to the mock response file
     */
    @NotBlank()
    private String filePath;

    /**
     * Character encoding for the response
     */
    @Builder.Default
    private String charset = "UTF-8";

    /**
     * HTTP method to match
     */
    @NotNull()
    private HttpMethod method;

    /**
     * HTTP status code to return
     */
    @NotNull()
    @Builder.Default
    private HttpStatus status = HttpStatus.OK;

    /**
     * Delay in milliseconds before sending response
     */
    @Builder.Default
    private Long delayMs = 0L;

    /**
     * Whether to enable request validation
     */
    @Builder.Default
    private Boolean validateRequest = false;

    /**
     * Content type of the response
     */
    @Builder.Default
    private String contentType = "application/json";

}