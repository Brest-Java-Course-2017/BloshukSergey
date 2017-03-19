package com.cinema.dao;

import com.cinema.model.Session;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface SessionDao {
    /**
     * @param session new session.
     * @return session's id.
     */
    Integer add(Session session) throws DataAccessException;

    /**
     *
     * @param id session's id.
     * @return quantity of deleted sessions.
     */
    Integer delete(Integer id) throws DataAccessException;

    /**
     *
     * @param session customer for update.
     * @return the number of update rows.
     */
    Integer update(Session session) throws DataAccessException;

    /**
     * @return session by id.
     */
    Session getById(Integer id) throws DataAccessException;

    /**
     * @return all sessions.
     */
    List<Session> getAll() throws DataAccessException;
}
