package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Session;

import java.util.List;

public interface SessionClient {
    /**
     * @param session new session.
     * @return session's id.
     */
    Integer add(Session session) throws ServerDataAccessException;

    /**
     *
     * @param id session's id.
     * @return quantity of deleted sessions.
     */
    Integer delete(Integer id) throws ServerDataAccessException;

    /**
     *
     * @param session customer for update.
     * @return the number of update rows.
     */
    Integer update(Session session) throws ServerDataAccessException;

    /**
     * @return session by id.
     */
    Session getById(Integer id) throws ServerDataAccessException;

    /**
     * @return all sessions.
     */
    List<Session> getAll() throws ServerDataAccessException;
}
