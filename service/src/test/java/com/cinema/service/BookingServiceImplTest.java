package com.cinema.service;

import com.cinema.configuration.SpringServiceTestConfiguration;
import com.cinema.model.Customer;
import com.cinema.model.Session;
import com.cinema.model.SessionWithSeats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringServiceTestConfiguration.class)
@Transactional
public class BookingServiceImplTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static Session EXIST_SESSION;

    private static Customer EXIST_CUSTOMER = new Customer(1, "Sergey Bloshuk");

    private static Customer EXIST_CUSTOMER_2 = new Customer(4, "Denis Nalivko");

    private static final Integer EXPECTED = 1;

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    @Autowired
    private BookingService bookingService;

    @Before
    public void setUp() throws Exception {
        EXIST_SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 60);

        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-3");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-5-3");
    }


    @Test
    public void getCustomersBySessionId() throws Exception {
        List<Customer> customers = bookingService.getCustomersBySessionId(EXIST_SESSION.getSessionId());

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() > 0);
    }

    @Test
    public void getSessionsWithSeats() throws Exception {
        List<SessionWithSeats> sessionWithSeats = bookingService.getSessionsWithSeats(null, null);

        assertNotNull("sessionWithSeats must be not null", sessionWithSeats);
        assertTrue("sessionWithSeats must be greater than zero", sessionWithSeats.size() > 0);
    }

    @Test
    public void delete() throws Exception {
        Integer quantityDeleted = bookingService.delete(EXIST_SESSION.getSessionId(), EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Quantity of delete must be not null", quantityDeleted);
        assertEquals("Quantity changed records", EXPECTED, quantityDeleted);
    }

    @Test
    public void add() throws Exception {
        Integer id = bookingService.add(EXIST_SESSION.getSessionId(), EXIST_CUSTOMER_2.getCustomerId());

        assertNotNull("Session id must be not null", id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNotExist() throws Exception {
        bookingService.add(EXIST_SESSION.getSessionId(), EXIST_CUSTOMER.getCustomerId());
    }
}