package com.cinema.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class SessionTest {

    private static final Logger LOGGER = LogManager.getLogger(SessionTest.class);

    public static final Integer SESSION_ID = 1;

    public static final String MOVIE_NAME = "Logan";

    public static final LocalDate DATE = LocalDate.of(2017, 3, 5);

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