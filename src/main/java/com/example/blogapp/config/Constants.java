package com.example.blogapp.config;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final List<String> CORS_ALLOWED_ORIGINS = List.of("http://localhost:3000");
    public static final List<String> CORS_ALLOWED_HEADERS = Arrays.asList(
            "Origin", "Access-Control-Allow-Origin", "Content-Type",
            "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
            "Access-Control-Request-Method", "Access-Control-Request-Headers");
    public static final List<String> CORS_EXPOSED_HEADERS =Arrays.asList(
            "Origin", "Content-Type", "Accept", "Authorization",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
    public static final List<String> CORS_ALLOWED_METHODS =Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
    public static final String CORS_CONFIGURATION_PATTERN = "/**";

}
