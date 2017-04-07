package com.cinema.client.configuration;

import com.cinema.client.CinemaResponseErrorHandler;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cinema.client", "com.cinema.aop.aspect"})
public class SpringClientRestConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = EasyMock.createMock(RestTemplate.class);
        restTemplate.setErrorHandler(new CinemaResponseErrorHandler());

        return restTemplate;
    }
}
