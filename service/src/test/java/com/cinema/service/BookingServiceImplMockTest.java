package com.cinema.service;

import com.cinema.configuration.SpringServiceMockTestConfiguration;
import com.cinema.dao.BookingDao;
import com.cinema.dao.SessionDao;
import com.cinema.model.Customer;
import com.cinema.model.Session;
import com.cinema.model.SessionWithSeats;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringServiceMockTestConfiguration.class)
public class BookingServiceImplMockTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final Customer CUSTOMER_1 = new Customer(1, "Sergey Bloshuk");

    private static final Customer CUSTOMER_2 = new Customer(2, "Bob");
    public static final Boolean EXPECTED_FALSE = new Boolean(false);

    private static Session SESSION;

    private static SessionWithSeats SESSION_WITH_SEATS_1;

    private static SessionWithSeats SESSION_WITH_SEATS_2;

    private static final Integer EXPECTED = 1;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private SessionDao sessionDaoMock;

    @Autowired
    private BookingDao bookingDaoMock;

    @After
    public void clean() {
        verify(sessionDaoMock);
        verify(bookingDaoMock);
    }

    @Before
    public void setUp() throws Exception {
        SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 20);

        SESSION_WITH_SEATS_1 = new SessionWithSeats(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 20, 5);

        SESSION_WITH_SEATS_2 = new SessionWithSeats(2, "Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4"), 10, 4);

        reset(sessionDaoMock);
        reset(bookingDaoMock);
    }

    @Test
    public void getCustomersBySessionId() throws Exception {
        List<Customer> expectCustomers = new ArrayList<Customer>();
        expectCustomers.add(CUSTOMER_1);
        expectCustomers.add(CUSTOMER_2);

        expect(bookingDaoMock.getCustomersBySessionId(SESSION.getSessionId())).andReturn(expectCustomers);
        replay(bookingDaoMock);
        replay(sessionDaoMock);

        List<Customer> customers = bookingService.getCustomersBySessionId(SESSION.getSessionId());

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() == expectCustomers.size());
    }

    @Test
    public void getSessionsWithSeats() throws Exception {
        List<SessionWithSeats> expectCustomers = new ArrayList<SessionWithSeats>();
        expectCustomers.add(SESSION_WITH_SEATS_1);
        expectCustomers.add(SESSION_WITH_SEATS_2);

        expect(bookingDaoMock.getSessionsWithSeats(null, null)).andReturn(expectCustomers);
        replay(bookingDaoMock);
        replay(sessionDaoMock);

        List<SessionWithSeats> customers = bookingService.getSessionsWithSeats(null, null);

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() == expectCustomers.size());
    }

    @Test
    public void delete() throws Exception {
        expect(bookingDaoMock.delete(SESSION.getSessionId(), CUSTOMER_1.getCustomerId())).andReturn(EXPECTED);
        replay(bookingDaoMock);
        replay(sessionDaoMock);

        Integer quantity = bookingService.delete(SESSION.getSessionId(), CUSTOMER_1.getCustomerId());

        assertNotNull("Quantity delete must be not null", quantity);
        assertEquals("Delete", EXPECTED, quantity);
    }

    @Test
    public void add() throws Exception {
        expect(sessionDaoMock.getById(SESSION.getSessionId())).andReturn(SESSION);
        replay(sessionDaoMock);

        expect(bookingDaoMock.getSeatsBySessionId(SESSION.getSessionId())).andReturn(EXPECTED);
        expect(bookingDaoMock.checkUpBooking(SESSION.getSessionId(), CUSTOMER_1.getCustomerId())).andReturn(EXPECTED_FALSE);
        expect(bookingDaoMock.add(SESSION.getSessionId(), CUSTOMER_1.getCustomerId())).andReturn(EXPECTED);
        replay(bookingDaoMock);

        Integer quantity = bookingService.add(SESSION.getSessionId(), CUSTOMER_1.getCustomerId());

        assertNotNull("Quantity delete must be not null", quantity);
        assertEquals("Delete", EXPECTED, quantity);
    }

}