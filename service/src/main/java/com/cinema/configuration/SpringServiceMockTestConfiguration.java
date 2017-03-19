package com.cinema.configuration;

import com.cinema.dao.BookingDao;
import com.cinema.dao.CustomerDao;
import com.cinema.dao.SessionDao;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.cinema.service")
public class SpringServiceMockTestConfiguration {

    @Bean
    public CustomerDao customerDao() {
        return EasyMock.createMock(CustomerDao.class);
    }

    @Bean
    public SessionDao sessionDao() {
        return EasyMock.createMock(SessionDao.class);
    }

    @Bean
    public BookingDao bookingDao() {
        return EasyMock.createMock(BookingDao.class);
    }

}
