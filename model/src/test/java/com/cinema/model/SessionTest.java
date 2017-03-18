package com.cinema.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class SessionTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final Logger LOGGER = LogManager.getLogger(CustomerTest.class);

    public static final Integer SESSION_ID = 1;

    public static final Integer TOTAL_SEATS = 20;

    public static final String MOVIE_NAME = "Logan";

    public static Date DATE;

    @Before
    public void setUp() throws Exception {
        DATE = SIMPLE_DATE_FORMAT.parse("2017-3-3");
    }

    @Test
    public void setSessionId() throws Exception {
        LOGGER.debug("test: setSessionId()");

        Session session = new Session();
        session.setSessionId(SESSION_ID);

        assertEquals("Id: ", SESSION_ID, session.getSessionId());
    }

    @Test
    public void setMovieName() throws Exception {
        LOGGER.debug("test: setMovieName()");

        Session session = new Session();
        session.setMovieName(MOVIE_NAME);

        assertEquals("Movie name: ", MOVIE_NAME, session.getMovieName());
    }

    @Test
    public void setSessionDate() throws Exception {
        LOGGER.debug("test: setSessionDate()");

        Session session = new Session();
        session.setSessionDate(DATE);

        assertEquals("Date: ", DATE, session.getSessionDate());
    }

    @Test
    public void setTotalSeats() throws Exception {
        LOGGER.debug("test: setTotalSeats()");

        Session session = new Session();
        session.setTotalSeats(TOTAL_SEATS);

        assertEquals("Seats: ", TOTAL_SEATS, session.getTotalSeats());
    }

}