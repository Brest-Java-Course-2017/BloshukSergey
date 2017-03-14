package com.cinema.dao;

import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

/**
 * SessionDao is dao interface
 */
public interface SessionDao {

    /**
     *
     * @return all sessions.
     */
    List<Session> getAllSessions() throws DataAccessException;

    /**
     *
     * @return all sessions with quantity tickets.
     */
    List<SessionWithQuantityTickets> getAllSessionsWithQuantityTickets() throws DataAccessException;

    /**
     *
     * @param firstDate start date.
     * @param secondDate end date.
     * @return list of SessionWithQuantityTickets between two dates.
     */
    List<SessionWithQuantityTickets> getAllSessionsWithQuantityTicketsDateToDate(Date firstDate, Date secondDate) throws DataAccessException;

    /**
     *
     * @param sessionId session's id.
     * @return session from database.
     */
    Session getSessionById(Integer sessionId) throws DataAccessException;

    /**
     * Add new session
     *
     * @param session new session.
     * @return session's id.
     * @throws DataAccessException
     */
    Integer addSession(Session session) throws DataAccessException;

    /**
     * Update session
     *
     * @param session with new values.
     * @return the number of update rows.
     * @throws DataAccessException
     */
    Integer updateSession(Session session) throws DataAccessException;

    /**
     * Delete session from session.
     *
     * @param sessionId session's id.
     * @return quantity of delete session.
     * @throws DataAccessException
     */
    Integer deleteSession(Integer sessionId) throws DataAccessException;


}
