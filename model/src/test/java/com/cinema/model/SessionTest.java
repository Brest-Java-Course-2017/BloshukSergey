package com.cinema.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class SessionTest {

    private static final Logger LOGGER = LogManager.getLogger(SessionTest.class);

    public static final Integer SESSION_ID = 1;

    public static final String MOVIE_NAME = "Logan";

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public static Date DATE;

    @Before
    public void setUp() throws Exception {
        DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2017-3-5");
    }

    @Test
    public void setSessionId() throws Exception {
        LOGGER.debug("test: setSessionId()");

        Session session = new Session();
        session.setSessionId(SESSION_ID);

        assertEquals("Session id", SESSION_ID, session.getSessionId());
    }

    @Test
    public void setMovieName() throws Exception {
        LOGGER.debug("test: setMovieName()");

        Session session = new Session();
        session.setMovieName(MOVIE_NAME);

        assertEquals("Movie name", MOVIE_NAME, session.getMovieName());
    }

    @Test
    public void setDate() throws Exception {
        LOGGER.debug("test: setSessionDate()");

        Session session = new Session();
        session.setSessionDate(DATE);

        assertEquals("Date", DATE, session.getSessionDate());
    }

}