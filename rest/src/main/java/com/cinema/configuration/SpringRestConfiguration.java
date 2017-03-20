package com.cinema.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan({
        "com.cinema.dao",
        "com.cinema.service",
        "com.cinema.controller.rest"})
@Import({SpringJDBCConfiguration.class})
public class SpringRestConfiguration {
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        ClassPathResource[] resources = new ClassPathResource[] {
                new ClassPathResource( "database.properties" ),
                new ClassPathResource( "queries/customer.properties" ),
                new ClassPathResource("queries/session.properties"),
                new ClassPathResource("queries/booking.properties")
        };
        propertyPlaceholderConfigurer.setLocations(resources);
        return propertyPlaceholderConfigurer;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setPrettyPrint(true);

        return converter;
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(mappingJackson2HttpMessageConverter());
        requestMappingHandlerAdapter.setMessageConverters(messageConverters);

        return requestMappingHandlerAdapter;
    }
}
