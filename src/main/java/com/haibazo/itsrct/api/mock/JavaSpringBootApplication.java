package com.haibazo.itsrct.api.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the API mock service.
 * Initializes and launches the Spring application context with auto-configuration enabled.
 * 
 * @SpringBootApplication enables:
 * - @Configuration: Tags the class as a source of bean definitions
 * - @EnableAutoConfiguration: Adds beans based on classpath settings
 * - @ComponentScan: Scans for Spring components in the package hierarchy
 */
@SpringBootApplication
public class JavaSpringBootApplication {

    /**
     * Application entry point. Bootstraps and launches the Spring Boot application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(JavaSpringBootApplication.class, args);
    }

}