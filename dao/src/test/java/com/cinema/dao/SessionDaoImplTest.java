package com.cinema.dao;

import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class SessionDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger(SessionDaoImplTest.class);

    private static final LocalDate FIRST_DATE = LocalDate.of(2017, 1, 1);

    private static final LocalDate SECOND_DATE = LocalDate.of(2017, 6, 20);

    private static final Session EXIST_SESSION = new Session(1, "Logan", LocalDate.of(2017, 3, 3));

    private static final Session UPDATE_SESSION = new Session(1, "Logan", LocalDate.of(2017, 4, 4));

    private static final Session NEW_SESSION = new Session("Lego movie", LocalDate.of(2017, 11, 10));

    private static final Integer QUANTITY_SESSION_ONE = 1;

    private static final Integer QUANTITY_SESSION_ZERO = 0;

    private static final Integer BAD_ID = 100;

    @Autowired
    private SessionDao sessionDao;

    @Test
    public void getAllSessions() throws Exception {
        LOGGER.debug("test: getAllSessions()");

        List<Session> sessions = sessionDao.getAllSessions();

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions must be greater than zero", sessions.size() > 0);
    }

    @Test
    public void getAllSessionsWithQuantityTickets() throws Exception {
        LOGGER.debug("test: getAllSessionsWithQuantityTickets()");

        List<SessionWithQuantityTickets> sessions = sessionDao.getAllSessionsWithQuantityTickets();

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions must be greater than zero", sessions.size() > 0);
    }

    @Test
    public void getAllSessionsWithQuantityTicketsDateToDate() throws Exception {
        LOGGER.debug("test: getAllSessionsWithQuantityTicketsDateToDate(from {} to {})", FIRST_DATE, SECOND_DATE);

        List<SessionWithQuantityTickets> sessions = sessionDao.getAllSessionsWithQuantityTicketsDateToDate(FIRST_DATE, SECOND_DATE);

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions must be greater than zero", sessions.size() > 0);
    }

    @Test
    public void getSessionById() throws Exception {
        LOGGER.debug("test: getSessionById({})", EXIST_SESSION.getSessionId());

        Session session = sessionDao.getSessionById(EXIST_SESSION.getSessionId());

        assertNotNull("Session must be not null", session);
        assertEquals("Movie name", EXIST_SESSION.getMovieName(), session.getMovieName());
        assertEquals("Session date", EXIST_SESSION.getSessionDate(), session.getSessionDate());
    }

    @Test(expected = DataAccessException.class)
    public void getSessionWithNotExistId() throws Exception {
        LOGGER.debug("test: getSessionWithNotExistId({})", BAD_ID);

        Session session = sessionDao.getSessionById(BAD_ID);
    }

    @Test
    public void addSession() throws Exception {
        LOGGER.debug("test: addSession({})", NEW_SESSION);

        Integer sessionId = sessionDao.addSession(NEW_SESSION);

        assertNotNull("Session id must be not null", sessionId);

        Session session = sessionDao.getSessionById(sessionId);

        assertNotNull("Session must be not null", session);
        assertEquals("Movie name", NEW_SESSION.getMovieName(), session.getMovieName());
        assertEquals("Session date", NEW_SESSION.getSessionDate(), session.getSessionDate());
    }

    @Test
    public void updateSession() throws Exception {
        LOGGER.debug("test: updateSession(from {} to {})", EXIST_SESSION, UPDATE_SESSION);

        Integer quantity = sessionDao.updateSession(UPDATE_SESSION);

        assertNotNull("Quantity of updated sessions must be not null", quantity);
        assertEquals("Quantity changed records", QUANTITY_SESSION_ONE, quantity);
    }

    @Test
    public void deleteSession() throws Exception {
        LOGGER.debug("test: deleteSession({})", EXIST_SESSION.getSessionId());

        Integer quantityDeleted = sessionDao.deleteSession(EXIST_SESSION.getSessionId());

        assertNotNull("Quantity of delete sessions must be not null", quantityDeleted);
        assertEquals("Quantity changed records", QUANTITY_SESSION_ONE, quantityDeleted);
    }

    @Test
    public void deleteSessionWithNotExistId() throws Exception {
        LOGGER.debug("test: deleteSessionWithNotExistId({})", BAD_ID);

        Integer quantityDeleted = sessionDao.deleteSession(BAD_ID);

        assertNotNull("Quantity of delete sessions must be not null", quantityDeleted);
        assertEquals("Quantity changed records", QUANTITY_SESSION_ZERO, quantityDeleted);
    }
}