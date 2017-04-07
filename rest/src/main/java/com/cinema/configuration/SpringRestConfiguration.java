package com.cinema.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebMvc
@EnableAspectJAutoProxy
@Configuration
@Import({SpringJDBCConfiguration.class})
@ComponentScan({
        "com.cinema.dao",
        "com.cinema.service",
        "com.cinema.controller.rest",
        "com.cinema.aop.aspect"})
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

        converter.setObjectMapper(new ObjectMapper().findAndRegisterModules());

        converter.setSupportedMediaTypes(Arrays.asList(MediaType.valueOf("application/json")));
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
