package com.haibazo.bff.mock.webapi.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.haibazo.bff.mock.webapi.dto.response.ApiResponseBaseDto;
import com.haibazo.bff.mock.webapi.dto.response.ApiResponseDto;
import com.haibazo.bff.mock.webapi.dto.response.ApiResponseErrorDto;
import com.haibazo.bff.mock.webapi.exception.BadRequestException;
import com.haibazo.bff.mock.webapi.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles BadRequestException and returns a formatted error response.
     *
     * @param exception the caught BadRequestException
     * @return formatted API response with error details
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiResponseBaseDto<Object>> handleBadRequestException(BadRequestException exception) {
        logger.debug("Handling BadRequestException: {}", exception.getMessage());
        ApiResponseErrorDto response = ApiResponseErrorDto.of(
                exception.getErrorCode(),
                exception.getErrorMessage());
        return ApiResponseDto.badRequest(response);
    }

    /**
     * Handles NotFoundException and returns a formatted error response.
     *
     * @param exception the caught NotFoundException
     * @return formatted API response with error details
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponseBaseDto<Object>> handleNotFoundException(NotFoundException exception) {
        logger.debug("Handling NotFoundException: {}", exception.getMessage());
        ApiResponseErrorDto response = ApiResponseErrorDto.of(
                exception.getErrorCode(),
                exception.getErrorMessage());
        return ApiResponseDto.notFound(response);
    }

    /**
     * Handles NoResourceFoundException and returns a formatted error response.
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponseBaseDto<Object>> handleNoResourceFoundException(
            NoResourceFoundException exception) {
        logger.debug("Handling NoResourceFoundException: {}", exception.getMessage());
        ApiResponseErrorDto response = ApiResponseErrorDto.of(
                "HBZE000001I",
                exception);
        return ApiResponseDto.notFound(response);
    }

    /**
     * Handles validation exceptions from @Valid annotations.
     *
     * @param exception the caught MethodArgumentNotValidException
     * @return formatted API response with validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiResponseBaseDto<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        logger.debug("Handling validation exception: {}", exception.getMessage());

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiResponseErrorDto response = ApiResponseErrorDto.of("HBZE000001I", errors);
        return ApiResponseDto.badRequest(response);
    }

    /**
     * Handles missing required request parameters in controller methods.
     * Creates a map of parameter name to error message and returns a 400 Bad
     * Request response.
     *
     * @param exception the caught MissingServletRequestParameterException
     * @return formatted API response with parameter error details
     * 
     * @example For a missing required 'userId' parameter:
     *          {
     *          "errorCode": "HBZE000001I",
     *          "errors": {
     *          "userId": "Required parameter 'userId' is missing"
     *          }
     *          }
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ApiResponseBaseDto<Object>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getParameterName(), exception.getMessage());

        ApiResponseErrorDto response = ApiResponseErrorDto.of("HBZE000001I", errors);

        return ApiResponseDto.badRequest(response);
    }

}
