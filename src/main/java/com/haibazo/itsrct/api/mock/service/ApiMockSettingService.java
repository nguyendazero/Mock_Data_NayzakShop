package com.haibazo.itsrct.api.mock.service;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.haibazo.itsrct.api.mock.dto.internal.ApiMockSettingMatchDto;
import com.haibazo.itsrct.api.mock.dto.internal.ApiMockSettingDto;

import com.opencsv.CSVReader;

import jakarta.annotation.PostConstruct;

/**
 * Service class responsible for managing API mock settings and configurations.
 * This service handles loading and processing mock API responses based on
 * configuration files.
 */
@Service
public class ApiMockSettingService {

    /**
     * Logger instance for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ApiMockSettingService.class);

    /**
     * Base folder path for storing mock response files.
     * Configured via application properties.
     */
    @Value("${haibazo.api.mock.base-folder-path}")
    private String mockBaseFolder;

    /**
     * File path for the mock settings configuration file.
     * Configured via application properties.
     */
    @Value("${haibazo.api.mock.setting-file-path}")
    private String mockSettingFilePath;

    /**
     * List to store mock settings loaded from the configuration file.
     */
    private List<ApiMockSettingDto> mockSettings = new ArrayList<>();

    /**
     * Initializes the service by loading mock settings from a CSV file.
     * Called automatically after bean construction.
     * The CSV file should have the following columns:
     * URI, FilePath, Charset, HTTP Method, Status Code
     */
    @PostConstruct
    public void initialize() {
        try (CSVReader reader = new CSVReader(new FileReader(getMockFilePath(mockSettingFilePath)))) {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                mockSettings.add(ApiMockSettingDto.builder()
                        .uri(line[0])
                        .filePath(line[1])
                        .charset(line[2])
                        .method(HttpMethod.valueOf(line[3]))
                        .status(HttpStatus.resolve(Integer.parseInt(line[4])))
                        .build());
            }
        } catch (Exception e) {
            logger.error("FAILED_TO_GET_MOCK_SETTING", e);
        }
    }

    /**
     * Finds a matching mock configuration for a given request path and HTTP method.
     * Uses Spring's AntPathMatcher for URI pattern matching.
     * 
     * @param path   The request path to match
     * @param method The HTTP method to match
     * @return ApiMockSettingMatchDto containing the matching configuration, or null
     *         if no match
     */
    public ApiMockSettingMatchDto findMatchingMock(String path, HttpMethod method) {
        return mockSettings.stream()
                .filter(mock -> mock.getMethod() == method)
                .map(mock -> {
                    AntPathMatcher matcher = new AntPathMatcher();
                    if (matcher.match(mock.getUri(), path)) {
                        Map<String, String> variables = matcher.extractUriTemplateVariables(mock.getUri(), path);
                        return new ApiMockSettingMatchDto(mock, mock.getUri(), variables);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    /**
     * Generates the complete mock file path, considering path variables for dynamic
     * responses.
     * Supports fallback mechanism for dynamic mock responses.
     * 
     * @param mockSetting The matched mock setting containing path variables
     * @return The resolved file path for the mock response
     */
    public String getMockFilePathWithFallback(ApiMockSettingMatchDto mockSetting) {
        String filePath = mockSetting.getMockSetting().getFilePath();
        String fileName = filePath.substring(0, filePath.lastIndexOf('.'));
        String extension = filePath.substring(filePath.lastIndexOf('.'));
        Map<String, String> pathVariables = mockSetting.getPathVariables();

        if (pathVariables.isEmpty()) {
            return getMockFilePath(filePath);
        }

        List<String> values = new ArrayList<>(pathVariables.values());

        String[] parts = fileName.split("_");
        List<String> resultParts = new ArrayList<>();

        resultParts.add(parts[0]);

        int valueIndex = 0;
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].matches("\\d+")) {
                String value = valueIndex < values.size() ? values.get(valueIndex) : "0";
                resultParts.add(value);
                valueIndex++;
            } else {
                resultParts.add(parts[i]);
            }
        }

        String primaryPath = getMockFilePath(String.join("_", resultParts) + extension);
        if (Files.exists(Path.of(primaryPath))) {
            return primaryPath;
        }

        resultParts = new ArrayList<>();
        resultParts.add(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].matches("\\d+")) {
                resultParts.add("0");
            } else {
                resultParts.add(parts[i]);
            }
        }

        String fallbackPath = getMockFilePath(String.join("_", resultParts) + extension);
        if (Files.exists(Path.of(fallbackPath))) {
            return fallbackPath;
        }

        return null;
    }

    /**
     * Resolves the absolute file path for a mock file.
     * Combines the base folder path with the relative path and normalizes it.
     * 
     * @param path The relative path to resolve
     * @return The absolute file path as a string
     */
    private String getMockFilePath(String path) {
        Path mockBasePath = Path.of(mockBaseFolder);
        Path mockFilePath = mockBasePath.resolve(path).normalize();

        return mockFilePath.toString();
    }

}
