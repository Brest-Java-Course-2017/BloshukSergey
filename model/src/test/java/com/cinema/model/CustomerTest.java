package com.cinema.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final Logger LOGGER = LogManager.getLogger(CustomerTest.class);

    public static final Integer CUSTOMER_ID = 1;

    public static final Integer SESSION_ID = 2;

    public static final String FIRST_NAME = "Sergey";

    public static final String LAST_NAME = "Bloshuk";

    public static final Integer QUANTITY_TICKETS = 3;

    @Test
    public void setCustomerId() throws Exception {
        LOGGER.debug("test: setCustomerId()");

        Customer customer = new Customer();
        customer.setCustomerId(CUSTOMER_ID);

        assertEquals("Customer id", CUSTOMER_ID, customer.getCustomerId());
    }

    @Test
    public void setSessionId() throws Exception {
        LOGGER.debug("test: setSessionId()");

        Customer customer = new Customer();
        customer.setSessionId(SESSION_ID);

        assertEquals("Session id", SESSION_ID, customer.getSessionId());
    }

    @Test
    public void setFirstName() throws Exception {
        LOGGER.debug("test: setFirstName()");

        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);

        assertEquals("First name", FIRST_NAME, customer.getFirstName());
    }

    @Test
    public void setLastName() throws Exception {
        LOGGER.debug("test: setLastName()");

        Customer customer = new Customer();
        customer.setLastName(LAST_NAME);

        assertEquals("Last name", LAST_NAME, customer.getLastName());
    }

    @Test
    public void setQuantityTickets() throws Exception {
        LOGGER.debug("test: setQuantityTickets()");

        Customer customer = new Customer();
        customer.setQuantityTickets(QUANTITY_TICKETS);

        assertEquals("Quantity tickets", QUANTITY_TICKETS, customer.getQuantityTickets());
    }
}