package com.haibazo.bff.mock.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haibazo.bff.mock.webapi.dto.response.ApiResponseBaseDto;
import com.haibazo.bff.mock.webapi.service.ApiMockService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller responsible for handling mock API requests.
 * Intercepts all requests matching the configured base path and returns mock
 * responses.
 */
@CrossOrigin(origins = "${haibazo.bff.cors.origins}")
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
    @RequestMapping(value = "${haibazo.bff.itsrct.base-path}/**")
    public ResponseEntity<ApiResponseBaseDto<Object>> mockItsRctApi(HttpServletRequest request,
            HttpServletResponse response) {
        return apiMockService.mockItsRctApi(request, response);
    }

}
