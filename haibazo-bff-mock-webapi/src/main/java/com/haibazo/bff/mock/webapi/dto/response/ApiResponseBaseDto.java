package com.haibazo.bff.mock.webapi.dto.response;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A generic DTO class for API responses.
 *
 * <p>
 * This class serves as a standard structure for API responses,
 * providing information about the HTTP status, response data, message,
 * error details, and pagination metadata.
 * </p>
 *
 * <p>
 * Lombok annotations are used to automatically generate common methods:
 * </p>
 * <ul>
 * <li>{@link Data}: Generates getters, setters, toString, equals, and hashCode
 * methods.</li>
 * <li>{@link AllArgsConstructor}: Generates a constructor with parameters for
 * all fields.</li>
 * <li>{@link NoArgsConstructor}: Generates a no-arguments constructor for
 * creating empty objects.</li>
 * </ul>
 *
 * @param <T> the type of data returned in the response body
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseBaseDto<T> {

    private HttpStatus status;

    private Object data;

    private String message;

    private String code;

    private Pageable meta;

    /**
     * Metadata providing additional context for the response.
     *
     * <p>
     * This typically includes pagination details such as
     * page index, page size, and total items.
     * </p>
     */
    private Object metadata;

    private Exception error = null;

}
