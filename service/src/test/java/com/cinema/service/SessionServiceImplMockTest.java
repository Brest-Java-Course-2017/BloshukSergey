package com.cinema.service;

import com.cinema.dao.SessionDao;
import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-service-mock.xml"})
public class SessionServiceImplMockTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final Logger LOGGER = LogManager.getLogger(SessionServiceImplMockTest.class);

    private static Session SESSION_1;

    private static Session SESSION_2;

    private static SessionWithQuantityTickets SESSION_WITH_QUANTITY_TICKETS_1;

    private static SessionWithQuantityTickets SESSION_WITH_QUANTITY_TICKETS_2;

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    private static final Integer QUANTITY_UPDATED = 1;

    private static final Integer QUANTITY_DELETED = 1;

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
        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-1");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-6-22");

        SESSION_1 = new Session(1,"Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"));

        SESSION_2 = new Session(2,"Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4"));

        SESSION_WITH_QUANTITY_TICKETS_1 = new SessionWithQuantityTickets(1,"Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 2);

        SESSION_WITH_QUANTITY_TICKETS_2 = new SessionWithQuantityTickets(2,"Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4"), 10);

        reset(sessionDaoMock);
    }

    @Test
    public void getAllSessions() throws Exception {
        LOGGER.debug("mock test: getAllSessions()");

        List<Session> expectSessions = new ArrayList<Session>();
        expectSessions.add(SESSION_1);
        expectSessions.add(SESSION_2);

        expect(sessionDaoMock.getAllSessions()).andReturn(expectSessions);
        replay(sessionDaoMock);

        List<Session> sessions = sessionService.getAllSessions();

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions must be greater than zero", sessions.size() == expectSessions.size());
    }

    @Test
    public void getAllSessionsWithQuantityTickets() throws Exception {
        LOGGER.debug("mock test: getAllSessionsWithQuantityTickets()");

        List<SessionWithQuantityTickets> expectSessions = new ArrayList<SessionWithQuantityTickets>();
        expectSessions.add(SESSION_WITH_QUANTITY_TICKETS_1);
        expectSessions.add(SESSION_WITH_QUANTITY_TICKETS_2);

        expect(sessionDaoMock.getAllSessionsWithQuantityTickets()).andReturn(expectSessions);
        replay(sessionDaoMock);

        List<SessionWithQuantityTickets> sessions = sessionService.getAllSessionsWithQuantityTickets();

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions", sessions.size() == expectSessions.size());
    }

    @Test
    public void getAllSessionsWithQuantityTicketsDateToDate() throws Exception {
        LOGGER.debug("mock test: getAllSessionsWithQuantityTicketsDateToDate({} to {})", FIRST_DATE, SECOND_DATE);

        List<SessionWithQuantityTickets> expectSessions = new ArrayList<SessionWithQuantityTickets>();
        expectSessions.add(SESSION_WITH_QUANTITY_TICKETS_1);

        expect(sessionDaoMock.getAllSessionsWithQuantityTicketsDateToDate(FIRST_DATE, SECOND_DATE)).andReturn(expectSessions);
        replay(sessionDaoMock);

        List<SessionWithQuantityTickets> sessions = sessionService.getAllSessionsWithQuantityTicketsDateToDate(FIRST_DATE, SECOND_DATE);

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions", sessions.size() == expectSessions.size());
    }

    @Test
    public void getSessionById() throws Exception {
        LOGGER.debug("mock test: getSessionById()");

        expect(sessionDaoMock.getSessionById(SESSION_1.getSessionId())).andReturn(SESSION_1);
        replay(sessionDaoMock);

        Session session = sessionService.getSessionById(SESSION_1.getSessionId());

        assertNotNull("Session must be not null", session);
        assertEquals("Session", SESSION_1, session);
    }

    @Test
    public void addSession() throws Exception {
        LOGGER.debug("mock test: addSession()");

        expect(sessionDaoMock.addSession(SESSION_1)).andReturn(SESSION_1.getSessionId());
        replay(sessionDaoMock);

        Integer sessionId = sessionService.addSession(SESSION_1);

        assertNotNull("Session id must be not null", sessionId);
        assertEquals("Session id", SESSION_1.getSessionId(), sessionId);
    }

    @Test
    public void updateSession() throws Exception {
        LOGGER.debug("mock test: updateSession()");

        expect(sessionDaoMock.updateSession(SESSION_1)).andReturn(QUANTITY_UPDATED);
        replay(sessionDaoMock);

        Integer quantity = sessionService.updateSession(SESSION_1);

        assertNotNull("Quantity updated must be not null", quantity);
        assertEquals("Update", QUANTITY_UPDATED, quantity);
    }

    @Test
    public void deleteSession() throws Exception {
        LOGGER.debug("mock test: deleteSession()");

        expect(sessionDaoMock.deleteSession(SESSION_1.getSessionId())).andReturn(QUANTITY_DELETED);
        replay(sessionDaoMock);

        Integer quantity = sessionService.deleteSession(SESSION_1.getSessionId());

        assertNotNull("Quantity delete must be not null", quantity);
        assertEquals("Delete", QUANTITY_DELETED, quantity);
    }
}
