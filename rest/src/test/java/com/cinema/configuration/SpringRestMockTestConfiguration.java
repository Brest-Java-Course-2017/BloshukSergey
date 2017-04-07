package com.cinema.configuration;

import com.cinema.service.BookingService;
import com.cinema.service.CustomerService;
import com.cinema.service.SessionService;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cinema.controller.rest", "com.cinema.aop.aspect"})
public class SpringRestMockTestConfiguration {

    @Bean
    public CustomerService customerService() {
        return EasyMock.createMock(CustomerService.class);
    }

    @Bean
    public SessionService sessionService() {
        return EasyMock.createMock(SessionService.class);
    }

    @Bean
    public BookingService bookingService() { return EasyMock.createMock(BookingService.class); }

}
