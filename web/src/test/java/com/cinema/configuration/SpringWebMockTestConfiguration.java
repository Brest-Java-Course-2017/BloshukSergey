package com.cinema.configuration;

import com.cinema.client.BookingClient;
import com.cinema.client.CustomerClient;
import com.cinema.client.SessionClient;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cinema.controller.web", "com.cinema.aop.aspect"})
public class SpringWebMockTestConfiguration {

    @Bean
    public BookingClient bookingClient() {
        return EasyMock.createMock(BookingClient.class);
    }

    @Bean
    public CustomerClient customerClient() {
        return EasyMock.createMock(CustomerClient.class);
    }

    @Bean
    public SessionClient sessionClient() {
        return EasyMock.createMock(SessionClient.class);
    }
}
