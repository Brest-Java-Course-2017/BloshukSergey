package com.cinema.dao;

import com.cinema.configuration.SpringDaoTestConfiguration;
import com.cinema.model.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringDaoTestConfiguration.class)
@Transactional
public class SessionDaoImplTest {

    private static Session EXIST_SESSION;

    private static Session UPDATE_SESSION;

    private static Session NEW_SESSION;

    private static final Integer EXPECTED = 1;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final Logger LOGGER = LogManager.getLogger(SessionDaoImplTest.class);

    @Autowired
    private SessionDao sessionDao;

    @Before
    public void setUp() throws Exception {
        EXIST_SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 60);

        UPDATE_SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-4-4"), 20);

        NEW_SESSION = new Session("Lego movie", SIMPLE_DATE_FORMAT.parse("2017-11-10"), 20);
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("test: addSession({})", NEW_SESSION);

        Integer sessionId = sessionDao.add(NEW_SESSION);

        assertNotNull("Session id must be not null", sessionId);

        Session session = sessionDao.getById(sessionId);

        assertNotNull("Session must be not null", session);
        assertEquals("Movie name", NEW_SESSION.getMovieName(), session.getMovieName());
        assertEquals("Session date", NEW_SESSION.getSessionDate(), session.getSessionDate());
    }

    @Test
    public void delete() throws Exception {
        LOGGER.debug("test: delete({})", EXIST_SESSION.getSessionId());

        Integer quantityDeleted = sessionDao.delete(EXIST_SESSION.getSessionId());

        assertNotNull("Quantity of delete sessions must be not null", quantityDeleted);
        assertEquals("Quantity changed records", EXPECTED, quantityDeleted);
    }

    @Test
    public void update() throws Exception {
        LOGGER.debug("test: update(from {} to {})", EXIST_SESSION, UPDATE_SESSION);

        Integer quantity = sessionDao.update(UPDATE_SESSION);

        assertNotNull("Quantity of updated sessions must be not null", quantity);
        assertEquals("Quantity changed records", EXPECTED, quantity);
    }

    @Test
    public void getById() throws Exception {
        LOGGER.debug("test: getById({})", EXIST_SESSION.getSessionId());

        Session session = sessionDao.getById(EXIST_SESSION.getSessionId());

        assertNotNull("Session must be not null", session);
        assertEquals("Movie name", EXIST_SESSION.getMovieName(), session.getMovieName());
        assertEquals("Session date", EXIST_SESSION.getSessionDate(), session.getSessionDate());
    }

    @Test
    public void getAll() throws Exception {
        LOGGER.debug("test: getAllSessions()");

        List<Session> sessions = sessionDao.getAll();

        assertNotNull("Sessions must be not null", sessions);
        assertTrue("Sessions must be greater than zero", sessions.size() > 0);
    }

}