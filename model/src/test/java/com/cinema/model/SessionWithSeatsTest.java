package com.cinema.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionWithSeatsTest {

    private static final Logger LOGGER = LogManager.getLogger(CustomerTest.class);

    public static final Integer SEATS = 20;

    @Test
    public void setSeats() throws Exception {
        LOGGER.debug("test: setSeats()");

        SessionWithSeats sessionWithSeats = new SessionWithSeats();
        sessionWithSeats.setSeats(SEATS);

        assertEquals("Id: ", SEATS, sessionWithSeats.getSeats());
    }

}