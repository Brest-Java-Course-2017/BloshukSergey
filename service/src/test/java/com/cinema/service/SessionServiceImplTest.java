package com.cinema.service;

import com.cinema.configuration.SpringServiceTestConfiguration;
import com.cinema.model.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringServiceTestConfiguration.class)
@Transactional
public class SessionServiceImplTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Autowired
    private SessionService sessionService;

    private static Session EXIST_SESSION;

    private static Session UPDATE_SESSION;

    private static Session NEW_SESSION;

    private static final Integer EXPECTED = 1;

    @Before
    public void setUp() throws Exception {
        EXIST_SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 60);

        UPDATE_SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-4-4"), 20);

        NEW_SESSION = new Session("Lego movie", SIMPLE_DATE_FORMAT.parse("2017-11-10"), 20);
    }

    @Test
    public void add() throws Exception {
        Integer sessionId = sessionService.add(NEW_SESSION);

        assertNotNull("Session id must be not null", sessionId);

        Session session = sessionService.getById(sessionId);

        assertNotNull("Session must be not null", session);
        assertEquals("Session id", sessionId, session.getSessionId());
        assertEquals("Session movie name", NEW_SESSION.getMovieName(), session.getMovieName());
    }

    @Test
    public void delete() throws Exception {
        Integer quantity = sessionService.delete(EXIST_SESSION.getSessionId());

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

    @Test
    public void update() throws Exception {
        Integer quantity = sessionService.update(UPDATE_SESSION);

        assertNotNull("Quantity of updated sessions must be not null", quantity);
        assertEquals("Quantity changed records", EXPECTED, quantity);
    }

    @Test
    public void getById() throws Exception {
        Session session = sessionService.getById(EXIST_SESSION.getSessionId());

        assertNotNull("Session must be not null", session);
        assertEquals("Movie name", EXIST_SESSION, session);
    }

    @Test
    public void getAll() throws Exception {
        List<Session> sessions = sessionService.getAll();

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions must be greater than zero", sessions.size() > 0);
    }

}