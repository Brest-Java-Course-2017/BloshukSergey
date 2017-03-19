package com.cinema.service;

import com.cinema.dao.SessionDao;
import com.cinema.model.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.util.Assert.*;

@Service
@Transactional
public class SessionServiceImpl implements SessionService, InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(SessionServiceImpl.class);

    @Autowired
    private SessionDao sessionDao;

    @Override
    public Integer add(Session session) throws DataAccessException {
        LOGGER.debug("addSession({})", session);

        notNull(session, "session must not be null");
        hasText(session.getMovieName(), "session must be had movie name");
        notNull(session.getSessionDate(), "session's movie name must not be null" );

        Integer sessionId = sessionDao.add(session);

        return sessionId;
    }

    @Override
    public Integer delete(Integer id) throws DataAccessException {
        LOGGER.debug("deleteSession({})", id);

        notNull(id, "sessionId must not be null");
        isTrue(id > 0, "sessionId must be greater than zero");

        Integer quantity = sessionDao.delete(id);

        return quantity;
    }

    @Override
    public Integer update(Session session) throws DataAccessException {
        LOGGER.debug("updateSession({})", session);

        notNull(session, "session must not be null");
        hasText(session.getMovieName(), "session must be had movie name");
        notNull(session.getSessionDate(), "session's movie name must not be null" );

        Integer quantity = sessionDao.update(session);

        return quantity;
    }

    @Override
    public Session getById(Integer id) throws DataAccessException {
        LOGGER.debug("getSessionById({})", id);

        notNull(id, "sessionId must not be null");
        isTrue(id > 0, "sessionId must be greater than 0");

        Session session = sessionDao.getById(id);

        return session;
    }

    @Override
    public List<Session> getAll() throws DataAccessException {
        LOGGER.debug("getAll()");

        List<Session> sessions = sessionDao.getAll();

        return sessions;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sessionDao == null) {
            throw new BeanCreationException("sessionDao is null");
        }
    }

}
