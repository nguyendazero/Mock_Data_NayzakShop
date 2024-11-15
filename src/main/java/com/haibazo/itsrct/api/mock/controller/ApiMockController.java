package com.haibazo.itsrct.api.mock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haibazo.itsrct.api.mock.dto.response.ApiResponseBaseDto;
import com.haibazo.itsrct.api.mock.service.ApiMockService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller responsible for handling mock API requests.
 * Intercepts all requests matching the configured base path and returns mock
 * responses.
 */
@RestController
@Validated
public class ApiMockController {
    @Autowired
    private ApiMockService apiMockService;

    /**
     * Handles all incoming requests and returns mock responses.
     * 
     * @param request  The HTTP request
     * @param response The HTTP response
     * @return ResponseEntity containing the mock response
     */
    @RequestMapping(value = "${haibazo.api.itsrct.base-path}/**")
    public ResponseEntity<ApiResponseBaseDto<Object>> mockItsRctApi(HttpServletRequest request,
            HttpServletResponse response) {
        return apiMockService.mockItsRctApi(request, response);
    }

}
