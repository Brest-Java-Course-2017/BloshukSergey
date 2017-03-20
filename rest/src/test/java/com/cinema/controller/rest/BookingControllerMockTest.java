package com.cinema.controller.rest;

import com.cinema.configuration.SpringRestMockTestConfiguration;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import com.cinema.service.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRestMockTestConfiguration.class)
public class BookingControllerMockTest {

    private static final Customer CUSTOMER_1 = new Customer(1, "Sergey Bloshuk");

    private static SessionWithSeats SESSION_WITH_SEATS_1;

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final Integer EXPECTED = 1;

    private MockMvc mockMvc;

    @Autowired
    private BookingService bookingServiceMock;

    @Autowired
    private BookingController bookingController;

    private static final Logger LOGGER = LogManager.getLogger(SessionControllerMockTest.class);

    @After
    public void clean() {
        verify(bookingServiceMock);
    }

    @Before
    public void setUp() throws Exception {
        SESSION_WITH_SEATS_1 =
                new SessionWithSeats(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3)"), 20,5);

        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-1");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-22");

        mockMvc = standaloneSetup(bookingController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        reset(bookingServiceMock);
    }

    @Test
    public void getCustomersBySessionId() throws Exception {
        LOGGER.debug("mock test: getCustomersBySessionId()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER_1);

        expect(bookingServiceMock.getCustomersBySessionId(SESSION_WITH_SEATS_1.getSessionId())).andReturn(customers);
        replay(bookingServiceMock);

        mockMvc.perform(get("/booking/getCustomersBySessionId?id=1").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void getSessionsWithSeats() throws Exception {
        LOGGER.debug("mock test: getSessionsWithSeats()");

        List<SessionWithSeats> sessions = new ArrayList<>();
        sessions.add(SESSION_WITH_SEATS_1);

        expect(bookingServiceMock.getSessionsWithSeats(FIRST_DATE, SECOND_DATE)).andReturn(sessions);
        replay(bookingServiceMock);

        mockMvc.perform(get("/booking/getSessionsWithSeats?firstDate=2017-03-01&secondDate=2017-03-22").accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void deleteBooking() throws Exception {
        LOGGER.debug("mock test: delete()");

        expect(bookingServiceMock.delete(SESSION_WITH_SEATS_1.getSessionId(), CUSTOMER_1.getCustomerId())).andReturn(EXPECTED);
        replay(bookingServiceMock);

        mockMvc.perform(
                delete("/booking/delete?sessionId=1&customerId=1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED.toString()));
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("mock test: add()");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/booking/add");
        request.accept(MediaType.APPLICATION_JSON);
        request.param("sessionId", SESSION_WITH_SEATS_1.getSessionId().toString());
        request.param("customerId", CUSTOMER_1.getCustomerId().toString());

        expect(bookingServiceMock.add(SESSION_WITH_SEATS_1.getSessionId(), CUSTOMER_1.getCustomerId())).andReturn(EXPECTED);
        replay(bookingServiceMock);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(EXPECTED.toString()));
    }

}