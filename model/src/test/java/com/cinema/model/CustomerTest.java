package com.cinema.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final Logger LOGGER = LogManager.getLogger(CustomerTest.class);

    public static final Integer CUSTOMER_ID = 1;

    public static final String NAME = "Sergey Bloshuk";


    @Test
    public void setCustomerId() throws Exception {
        LOGGER.debug("test: setCustomerId()");

        Customer customer = new Customer();
        customer.setCustomerId(CUSTOMER_ID);

        assertEquals("Id: ", CUSTOMER_ID, customer.getCustomerId());
    }

    @Test
    public void setName() throws Exception {
        LOGGER.debug("test: setName()");

        Customer customer = new Customer();
        customer.setName(NAME);

        assertEquals("Name: ", NAME, customer.getName());
    }

}