package com.cinema.dao;

import com.cinema.configuration.SpringDaoTestConfiguration;
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
import java.util.List;
import java.util.Locale;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringDaoTestConfiguration.class)
@Transactional
public class BookingDaoImplTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final Logger LOGGER = LogManager.getLogger(SessionDaoImplTest.class);

    private static Session EXIST_SESSION;

    private static Customer EXIST_CUSTOMER = new Customer(1, "Sergey Bloshuk");

    private static final Integer EXPECTED = 1;

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    @Autowired
    private BookingDao bookingDao;

    @Before
    public void setUp() throws Exception {
        EXIST_SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 60);

        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-3");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-5-3");
    }


    @Test
    public void getCustomersBySessionId() throws Exception {
        LOGGER.debug("test: getCustomersBySessionId()");

        List<Customer> customers = bookingDao.getCustomersBySessionId(EXIST_SESSION.getSessionId());

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() > 0);
    }

    @Test
    public void getSessionWithSeats() throws Exception {
        LOGGER.debug("test: getSessionWithSeats()");

        List<SessionWithSeats> sessionWithSeats = bookingDao.getSessionWithSeats(null, null);

        assertNotNull("sessionWithSeats must be not null", sessionWithSeats);
        assertTrue("sessionWithSeats must be greater than zero", sessionWithSeats.size() > 0);
    }

    @Test
    public void getSessionWithSeatsDateToDate() throws Exception {
        LOGGER.debug("test: getSessionWithSeats()");

        List<SessionWithSeats> sessionWithSeats = bookingDao.getSessionWithSeats(FIRST_DATE, SECOND_DATE);

        assertNotNull("sessionWithSeats must be not null", sessionWithSeats);
        assertTrue("sessionWithSeats must be greater than zero", sessionWithSeats.size() > 0);
    }

    @Test
    public void delete() throws Exception {
        LOGGER.debug("test: delete()");

        Integer quantityDeleted = bookingDao.delete(EXIST_SESSION.getSessionId(), EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Quantity of delete must be not null", quantityDeleted);
        assertEquals("Quantity changed records", EXPECTED, quantityDeleted);
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("test: add()");

        Integer id = bookingDao.add(EXIST_SESSION.getSessionId(), EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Session id must be not null", id);
    }

    @Test
    public void getSeatsBySessionId() throws Exception {
        LOGGER.debug("test: getSeatsBySessionId()");

        Integer seats = bookingDao.getSeatsBySessionId(EXIST_SESSION.getSessionId());

        assertNotNull("Seats must be not null", seats);
        assertTrue("Seats must be greater than zero", seats > 0);
    }

}