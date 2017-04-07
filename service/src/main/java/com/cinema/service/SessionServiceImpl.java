package com.cinema.service;

import com.cinema.aop.annotation.Loggable;
import com.cinema.dao.SessionDao;
import com.cinema.model.Session;
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

    @Autowired
    private SessionDao sessionDao;

    @Override
    @Loggable
    public Integer add(Session session) throws DataAccessException {
        notNull(session, "session must not be null");
        hasText(session.getMovieName(), "session must be had movie name");
        notNull(session.getSessionDate(), "session's movie name must not be null" );

        Integer sessionId = sessionDao.add(session);

        return sessionId;
    }

    @Override
    @Loggable
    public Integer delete(Integer id) throws DataAccessException {
        notNull(id, "sessionId must not be null");
        isTrue(id > 0, "sessionId must be greater than zero");

        Integer quantity = sessionDao.delete(id);

        return quantity;
    }

    @Override
    @Loggable
    public Integer update(Session session) throws DataAccessException {
        notNull(session, "session must not be null");
        hasText(session.getMovieName(), "session must be had movie name");
        notNull(session.getSessionDate(), "session's movie name must not be null" );

        Integer quantity = sessionDao.update(session);

        return quantity;
    }

    @Override
    @Loggable
    public Session getById(Integer id) throws DataAccessException {
        notNull(id, "sessionId must not be null");
        isTrue(id > 0, "sessionId must be greater than 0");

        Session session = sessionDao.getById(id);

        return session;
    }

    @Override
    @Loggable
    public List<Session> getAll() throws DataAccessException {
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
