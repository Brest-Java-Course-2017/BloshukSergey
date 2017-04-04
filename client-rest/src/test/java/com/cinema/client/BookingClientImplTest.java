package com.cinema.client;

import com.cinema.client.configuration.SpringClientRestConfiguration;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringClientRestConfiguration.class)
@PropertySource("classpath:url.properties")
public class BookingClientImplTest {

    @Value("${rest.protocol}://${rest.host}:${rest.port}")
    private String url;

    @Value("${rest.booking}")
    private String urlBooking;

    @Autowired
    private RestTemplate mockRestTemplate;

    @Autowired
    private BookingClient bookingClient;

    private static final Logger LOGGER = LogManager.getLogger(BookingClientImplTest.class);

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static SessionWithSeats SESSION_WITH_SEATS;

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    private static final Customer CUSTOMER = new Customer(1,"Sergey Bloshuk");

    private static final Integer EXPECTED = 1;

    @After
    public void clean() {
        verify(mockRestTemplate);
    }

    @Before
    public void setUp() throws Exception {
        SESSION_WITH_SEATS = new SessionWithSeats(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 20, 4);

        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-1");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-22");

        reset(mockRestTemplate);
    }

    @Test
    public void getCustomersBySessionId() throws Exception {
        LOGGER.debug("mock test: getCustomersBySessionId()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER);

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/getCustomersBySessionId?id={id}").toString();

        Map data = new HashMap<String, Date>();
        data.put("id", SESSION_WITH_SEATS.getSessionId());

        expect(mockRestTemplate.getForEntity(httpRequest, List.class, data))
                .andReturn(new ResponseEntity<List>(customers, HttpStatus.OK));
        replay(mockRestTemplate);

        List<Customer> result = bookingClient.getCustomersBySessionId(SESSION_WITH_SEATS.getSessionId());

        assertNotNull("Customer must be not null", result);
        assertEquals("List size", customers.size(), result.size());
    }

    @Test
    public void getSessionsWithSeatsDateToDate() throws Exception {
        LOGGER.debug("mock test: getSessionsWithSeatsDateToDate()");

        List<SessionWithSeats> sessions = new ArrayList<>();
        sessions.add(SESSION_WITH_SEATS);

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/getSessionsWithSeats")
                .append("?firstDate=").append(SIMPLE_DATE_FORMAT.format(FIRST_DATE))
                .append("&secondDate=").append(SIMPLE_DATE_FORMAT.format(SECOND_DATE))
                .toString();

        expect(mockRestTemplate.getForEntity(httpRequest, List.class))
                .andReturn(new ResponseEntity<List>(sessions, HttpStatus.OK));

        replay(mockRestTemplate);

        List<SessionWithSeats> result = bookingClient.getSessionsWithSeats(FIRST_DATE, SECOND_DATE);

        assertNotNull("Result must be not null", result);
        assertEquals("Lists size", sessions.size(), result.size());
    }

    @Test
    public void getSessionsWithSeats() throws Exception {
        LOGGER.debug("mock test: getSessionsWithSeats()");

        List<SessionWithSeats> sessions = new ArrayList<>();
        sessions.add(SESSION_WITH_SEATS);

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/getSessionsWithSeats").toString();

        expect(mockRestTemplate.getForEntity(httpRequest, List.class))
                .andReturn(new ResponseEntity<List>(sessions, HttpStatus.OK));
        replay(mockRestTemplate);

        List<SessionWithSeats> result = bookingClient.getSessionsWithSeats(null, null);

        assertNotNull("Result must be not null", result);
        assertEquals("Lists size", sessions.size(), result.size());
    }

    @Test
    public void delete() throws Exception {
        LOGGER.debug("mock test: delete()");

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/delete?sessionId={sessionId}&customerId={customerId}").toString();

        Map data = new HashMap<String, Integer>();
        data.put("sessionId", SESSION_WITH_SEATS.getSessionId());
        data.put("customerId", CUSTOMER.getCustomerId());

        expect(mockRestTemplate.exchange(httpRequest, HttpMethod.DELETE, null, Integer.class, data))
                .andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.ACCEPTED));
        replay(mockRestTemplate);

        Integer quantity = bookingClient.delete(SESSION_WITH_SEATS.getSessionId(), CUSTOMER.getCustomerId());

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("mock test: add()");

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/add?sessionId={sessionId}&customerId={customerId}").toString();

        Map data = new HashMap<String, Integer>();
        data.put("sessionId", SESSION_WITH_SEATS.getSessionId());
        data.put("customerId", CUSTOMER.getCustomerId());

        expect(mockRestTemplate.postForEntity(httpRequest, HttpMethod.POST, Integer.class, data))
                .andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.CREATED));
        replay(mockRestTemplate);

        Integer sessionId = bookingClient.add(SESSION_WITH_SEATS.getSessionId(), CUSTOMER.getCustomerId());

        assertNotNull("CustomerId must be not null", sessionId);
        assertEquals("Customer", EXPECTED, sessionId);
    }

}