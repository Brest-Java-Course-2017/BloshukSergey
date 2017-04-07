package com.cinema.service;

import com.cinema.configuration.SpringServiceMockTestConfiguration;
import com.cinema.dao.SessionDao;
import com.cinema.model.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringServiceMockTestConfiguration.class)
public class SessionServiceImplMockTest {

    private static Session SESSION_1;

    private static Session SESSION_2;

    private static final Integer EXPECTED = 1;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionDao sessionDaoMock;

    @After
    public void clean() {
        verify(sessionDaoMock);
    }

    @Before
    public void setUp() throws Exception {
        SESSION_1 = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 20);

        SESSION_2 = new Session(2, "Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4"), 60);

        reset(sessionDaoMock);
    }

    @Test
    public void add() throws Exception {
        expect(sessionDaoMock.add(SESSION_1)).andReturn(SESSION_1.getSessionId());
        replay(sessionDaoMock);

        Integer sessionId = sessionService.add(SESSION_1);

        assertNotNull("Session id must be not null", sessionId);
        assertEquals("Session id", SESSION_1.getSessionId(), sessionId);
    }

    @Test
    public void delete() throws Exception {
        expect(sessionDaoMock.delete(SESSION_1.getSessionId())).andReturn(EXPECTED);
        replay(sessionDaoMock);

        Integer quantity = sessionService.delete(SESSION_1.getSessionId());

        assertNotNull("Quantity delete must be not null", quantity);
        assertEquals("Delete", EXPECTED, quantity);
    }

    @Test
    public void update() throws Exception {
        expect(sessionDaoMock.update(SESSION_1)).andReturn(EXPECTED);
        replay(sessionDaoMock);

        Integer quantity = sessionService.update(SESSION_1);

        assertNotNull("Quantity updated must be not null", quantity);
        assertEquals("Update", EXPECTED, quantity);
    }

    @Test
    public void getById() throws Exception {
        expect(sessionDaoMock.getById(SESSION_1.getSessionId())).andReturn(SESSION_1);
        replay(sessionDaoMock);

        Session session = sessionService.getById(SESSION_1.getSessionId());

        assertNotNull("Session must be not null", session);
        assertEquals("Session", SESSION_1, session);
    }

    @Test
    public void getAll() throws Exception {
        List<Session> expectSessions = new ArrayList<Session>();
        expectSessions.add(SESSION_1);
        expectSessions.add(SESSION_2);

        expect(sessionDaoMock.getAll()).andReturn(expectSessions);
        replay(sessionDaoMock);

        List<Session> sessions = sessionService.getAll();

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions must be greater than zero", sessions.size() == expectSessions.size());
    }

}