//package com.wcd.fcmservice.fcmservice.config;
//
//import com.google.common.net.HttpHeaders;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
//
//    @Override
//    public void addCorsMappings(final CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("https://wcd.kro.kr", "http://wcd.kro.kr", "http://localhost:3000", "https://localhost:3000", "http://localhost:8080", "10.1.1.66")
//                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
//                .exposedHeaders(HttpHeaders.LOCATION);
//    }
//}