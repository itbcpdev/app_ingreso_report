package com.itbcp.report.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    @Deprecated
    public void configureContentNegotiation(@NonNull ContentNegotiationConfigurer configure) {
        configure.favorPathExtension(false).favorParameter(true).defaultContentType(MediaType.APPLICATION_JSON);
    }
}