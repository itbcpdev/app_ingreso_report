package com.itbcp.report.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class WebSetting implements PropertySourceFactory {

    @Override
    public org.springframework.core.env.PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

        InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/configuration/" + resource.getResource().getFilename());

        return new MapPropertySource("json-source", new ObjectMapper().readValue(inputStream, Map.class));
    }
}
