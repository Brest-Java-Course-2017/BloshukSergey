package com.cinema.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionWithQuantityTicketsTest {
    private static final Logger LOGGER = LogManager.getLogger(SessionTest.class);
    public static final Integer QUANTITY_TICKETS = 1;

    @Test
    public void setQuantityTickets() throws Exception {
        LOGGER.debug("test: setQuantityTickets()");

        SessionWithQuantityTickets session = new SessionWithQuantityTickets();
        session.setQuantityTickets(QUANTITY_TICKETS);

        assertEquals("Quantity of tickets", QUANTITY_TICKETS, session.getQuantityTickets());
    }

}