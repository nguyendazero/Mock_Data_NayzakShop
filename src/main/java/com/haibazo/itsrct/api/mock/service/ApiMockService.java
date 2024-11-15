package com.haibazo.itsrct.api.mock.service;

import com.haibazo.itsrct.api.mock.dto.internal.ApiMockSettingMatchDto;
import com.haibazo.itsrct.api.mock.dto.response.ApiResponseBaseDto;
import com.haibazo.itsrct.api.mock.dto.response.ApiResponseDto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Service class responsible for handling API mock responses in a testing
 * environment.
 * This service intercepts API requests and returns predefined mock responses
 * based on
 * configured settings.
 *
 * @author [Your Name]
 * @version 1.0
 */
@Service
public class ApiMockService {

    private static final Logger logger = LoggerFactory.getLogger(ApiMockService.class);

    @Autowired
    private ApiMockSettingService apiMockSettingService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Processes an incoming API request and returns a mock response based on the
     * configured settings.
     * The method matches the request path and HTTP method against stored mock
     * configurations
     * and returns the corresponding mock response.
     *
     * @param request  The HTTP servlet request containing the incoming request
     *                 details
     * @param response The HTTP servlet response object
     * @param service  The service identifier for the mock API
     * @return ResponseEntity containing the mock response wrapped in ApiResponseDto
     *         with appropriate HTTP status code and response body
     */
    public ResponseEntity<ApiResponseBaseDto<Object>> mockItsRctApi(HttpServletRequest request,
            HttpServletResponse response) {
        String path = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        ApiMockSettingMatchDto matchMockSetting = apiMockSettingService.findMatchingMock(path, method);

        if (matchMockSetting != null) {
            try {
                String filePath = apiMockSettingService.getMockFilePathWithFallback(matchMockSetting);
                ApiResponseDto responseDto = ApiResponseDto.status(matchMockSetting.getMockSetting().getStatus());

                if (filePath == null) {
                    return ApiResponseDto.error(HttpStatus.NOT_FOUND, "MOCK_FILE_NOT_FOUND");
                }

                String content = Files.readString(Path.of(filePath),
                        Charset.forName(matchMockSetting.getMockSetting().getCharset()));
                Object parsedContent = objectMapper.readValue(content, Object.class);

                logger.info("RETURNING_MOCK_RESPONSE [{}] {} - {} - [MOCK] {}",
                        matchMockSetting.getMockSetting().getMethod(),
                        matchMockSetting.getMockSetting().getUri(), matchMockSetting.getMockSetting().getStatus(),
                        filePath);

                responseDto.body(parsedContent);

                return responseDto.build();
            } catch (IOException e) {
                logger.error("FAILED_TO_READ_MOCK_FILE", e);
                return ApiResponseDto.error(HttpStatus.NOT_FOUND, "FAILED_TO_READ_MOCK_FILE [MOCK] " +
                        matchMockSetting.getMockSetting().getFilePath());
            }
        }

        return ApiResponseDto.error(HttpStatus.NOT_FOUND, "NO_MOCK_SETTING_FOUND");
    }

}
