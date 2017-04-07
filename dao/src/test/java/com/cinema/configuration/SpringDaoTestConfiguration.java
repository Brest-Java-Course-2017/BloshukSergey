package com.cinema.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cinema.dao", "com.cinema.aop.aspect"})
@Import({SpringJDBCConfiguration.class})
public class SpringDaoTestConfiguration {

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


}
