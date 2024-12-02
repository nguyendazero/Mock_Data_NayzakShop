package com.haibazo.bff.mock.webapi.service;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiMockLocalStorageService {

    /**
     * Base folder path for storing mock response files.
     * Configured via application properties.
     */
    @Value("${haibazo.bff.mock.base-folder-path}")
    private String mockBaseFolder;

    /**
     * Resolves the absolute file path for a mock file.
     * Combines the base folder path with the relative path and normalizes it.
     * 
     * @param path The relative path to resolve
     * @return The absolute file path as a string
     */
    public String getMockFilePath(String path) {
        Path mockBasePath = Path.of(mockBaseFolder);
        Path mockFilePath = mockBasePath.resolve(path).normalize();

        return mockFilePath.toString();
    }

}
