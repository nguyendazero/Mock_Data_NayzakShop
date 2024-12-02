package com.haibazo.bff.mock.webapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.haibazo.bff.mock.webapi.dto.request.PageableRequestDto;
import com.haibazo.bff.mock.webapi.dto.response.ApiResponseBaseDto;
import com.haibazo.bff.mock.webapi.dto.response.ApiResponseDto;
import com.haibazo.bff.mock.webapi.exception.BadRequestException;

import jakarta.validation.Valid;

/**
 * REST controller for handling greeting-related endpoints.
 * Provides endpoints for basic greeting functionality with pagination support.
 */
@RestController
@RequestMapping("/greetings")
public class GreetingController {

    /**
     * Returns a greeting message with pagination information.
     *
     * @param message            The greeting message to be returned
     * @param pageableRequestDto Pagination parameters
     * @return ResponseEntity containing the greeting message and pagination info
     * @throws BadRequestException if message is null or empty
     */
    @GetMapping()
    public ResponseEntity<ApiResponseBaseDto<Object>> sayHi(@RequestParam(name = "message") String message,
            @Valid PageableRequestDto pageableRequestDto) {
        if (message == null) {
            throw new BadRequestException("HBZE000002M");
        }

        return ApiResponseDto.ok(new ObjectMapper().createObjectNode()
                .put("message", message).set("pageable", new ObjectMapper().valueToTree(pageableRequestDto)));
    }

}
