package com.cinema.controller.web;

import com.cinema.client.BookingClient;
import com.cinema.configuration.SpringWebMockTestConfiguration;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringWebMockTestConfiguration.class)
public class BookingControllerTest {

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    private static SessionWithSeats SESSION_WITH_SEATS;

    private static final Customer CUSTOMER = new Customer(1, "Sergey Bloshuk");

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final String BOOKING_URL= "/booking";

    private static final Integer EXPECTED = 1;

    public static final String SESSIONS = "sessions";
    public static final String SESSION_CUSTOMERS = "sessionCustomers";
    public static final String REDIRECT_BOOKING_ID = "redirect:/booking?id=";

    private MockMvc mockMvc;

    private static final Logger LOGGER = LogManager.getLogger(BookingControllerTest.class);

    @Autowired
    private BookingClient bookingClientMock;

    @Autowired
    private BookingController bookingController;

    @After
    public void clean() {
        verify(bookingClientMock);
    }

    @Before
    public void setUp() throws Exception {

        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-1");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-22");

        SESSION_WITH_SEATS =
                new SessionWithSeats(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3)"), 20,5);

        mockMvc = standaloneSetup(bookingController).build();
        reset(bookingClientMock);
    }

    @Test
    public void getSessionsWithSeats() throws Exception {
        LOGGER.debug("test: getSessionsWithSeats()");

        List<SessionWithSeats> sessions = new ArrayList<>();
        sessions.add(SESSION_WITH_SEATS);

        expect(bookingClientMock.getSessionsWithSeats(FIRST_DATE, SECOND_DATE)).andReturn(sessions);
        replay(bookingClientMock);

        String httpRequest = new StringBuffer().append(BOOKING_URL).append("/getSessionsWithSeats").toString();

        mockMvc.perform(get(httpRequest)
                .param("firstDate", SIMPLE_DATE_FORMAT.format(FIRST_DATE))
                .param("secondDate", SIMPLE_DATE_FORMAT.format(SECOND_DATE)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name(SESSIONS))
                .andExpect(model().attribute("sessionList", hasSize(EXPECTED)));
    }

    @Test
    public void getCustomersBySessionId() throws Exception {
        LOGGER.debug("test: getCustomersBySessionId()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER);

        expect(bookingClientMock.getCustomersBySessionId(SESSION_WITH_SEATS.getSessionId())).andReturn(customers);
        replay(bookingClientMock);

        mockMvc.perform(get(BOOKING_URL)
                .param("id", SESSION_WITH_SEATS.getSessionId().toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name(SESSION_CUSTOMERS))
                .andExpect(model().attribute("customerList", hasSize(EXPECTED)))
                .andExpect(model().attribute("sessionId", is(SESSION_WITH_SEATS.getSessionId())));
    }

    @Test
    public void deleteBooking() throws Exception {
        LOGGER.debug("test: delete()");

        expect(bookingClientMock.delete(SESSION_WITH_SEATS.getSessionId(), CUSTOMER.getCustomerId())).andReturn(EXPECTED);
        replay(bookingClientMock);

        String httpRequest = new StringBuffer().append(BOOKING_URL).append("/delete").toString();

        mockMvc.perform(delete(httpRequest)
                .param("sessionId", SESSION_WITH_SEATS.getSessionId().toString())
                .param("customerId", CUSTOMER.getCustomerId().toString()))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(view().name(REDIRECT_BOOKING_ID + SESSION_WITH_SEATS.getSessionId()));
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("test: add()");

        expect(bookingClientMock.add(SESSION_WITH_SEATS.getSessionId(), CUSTOMER.getCustomerId())).andReturn(EXPECTED);
        replay(bookingClientMock);

        String httpRequest = new StringBuffer().append(BOOKING_URL).append("/add").toString();

        mockMvc.perform(post(httpRequest)
                .param("sessionId", SESSION_WITH_SEATS.getSessionId().toString())
                .param("customerId", CUSTOMER.getCustomerId().toString()))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(view().name(REDIRECT_BOOKING_ID + SESSION_WITH_SEATS.getSessionId()));
    }

}