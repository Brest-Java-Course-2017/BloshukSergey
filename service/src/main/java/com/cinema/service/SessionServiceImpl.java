package com.cinema.service;

import com.cinema.dao.SessionDao;
import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.Assert.*;

@Service
@Transactional
public class SessionServiceImpl implements SessionService, InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private SessionDao sessionDao;


    @Override
    public List<Session> getAllSessions() throws DataAccessException {
        LOGGER.debug("getAllSessions()");

        List<Session> sessions = sessionDao.getAllSessions();

        return sessions;
    }

    @Override
    public List<SessionWithQuantityTickets> getAllSessionsWithQuantityTickets() throws DataAccessException {
        LOGGER.debug("getAllSessionsWithQuantityTickets()");

        List<SessionWithQuantityTickets> sessions = sessionDao.getAllSessionsWithQuantityTickets();

        return sessions;
    }

    @Override
    public List<SessionWithQuantityTickets> getAllSessionsWithQuantityTicketsDateToDate(LocalDate firstDate, LocalDate secondDate) throws DataAccessException {
        LOGGER.debug("getAllSessionsWithQuantityTicketsDateToDate({}, {})", firstDate, secondDate);

        notNull(firstDate, "First date must not be null");
        notNull(secondDate, "Second date must not be null");
        isTrue(firstDate.compareTo(secondDate) < 0, "firstDate must be less than secondDate");

        List<SessionWithQuantityTickets> sessions = sessionDao.getAllSessionsWithQuantityTicketsDateToDate(firstDate, secondDate);

        return sessions;
    }

    @Override
    public Session getSessionById(Integer sessionId) throws DataAccessException {
        LOGGER.debug("getSessionById({})", sessionId);

        notNull(sessionId, "sessionId must not be null");
        isTrue(sessionId > 0, "sessionId must be greater than 0");

        Session session = sessionDao.getSessionById(sessionId);

        return session;
    }

    @Override
    public Integer addSession(Session session) throws DataAccessException {
        LOGGER.debug("addSession({})", session);

        notNull(session, "session must not be null");
        hasText(session.getMovieName(), "session must be had movie name");
        notNull(session.getSessionDate(), "session's movie name must not be null" );

        Integer sessionId = sessionDao.addSession(session);

        return sessionId;
    }

    @Override
    public Integer updateSession(Session session) throws DataAccessException {
        LOGGER.debug("updateSession({})", session);

        notNull(session, "session must not be null");
        hasText(session.getMovieName(), "session must be had movie name");
        notNull(session.getSessionDate(), "session's movie name must not be null" );

        Integer quantity = sessionDao.updateSession(session);

        return quantity;
    }

    @Override
    public Integer deleteSession(Integer sessionId) throws DataAccessException {
        LOGGER.debug("deleteSession({})", sessionId);

        notNull(sessionId, "sessionId must not be null");
        isTrue(sessionId > 0, "sessionId must be greater than zero");

        Integer quantity = sessionDao.deleteSession(sessionId);

        return quantity;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sessionDao == null) {
            throw new BeanCreationException("sessionDao is null");
        }
    }
}
