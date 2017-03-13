package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;

import java.time.LocalDate;
import java.util.List;

public interface SessionClient {
    /**
     *
     * @return all sessions.
     */
    List<Session> getAllSessions() throws ServerDataAccessException;

    /**
     *
     * @return all sessions with quantity tickets.
     */
    List<SessionWithQuantityTickets> getAllSessionsWithQuantityTickets() throws ServerDataAccessException;

    /**
     * firstDate < secondDate
     *
     * @param firstDate start date.
     * @param secondDate end date.
     * @return list of SessionWithQuantityTickets between two dates.
     */
    List<SessionWithQuantityTickets> getAllSessionsWithQuantityTicketsDateToDate(LocalDate firstDate, LocalDate secondDate) throws ServerDataAccessException;

    /**
     *
     * @param sessionId session's id.
     * @return session from database.
     */
    Session getSessionById(Integer sessionId) throws ServerDataAccessException;

    /**
     * Add new session
     *
     * @param session new session.
     * @return session's id.
     * @throws ServerDataAccessException
     */
    Integer addSession(Session session) throws ServerDataAccessException;

    /**
     * Update session
     *
     * @param session with new values.
     * @return the number of update rows.
     * @throws ServerDataAccessException
     */
    Integer updateSession(Session session) throws ServerDataAccessException;

    /**
     * Delete session from session.
     *
     * @param sessionId session's id.
     * @return quantity of delete session.
     * @throws ServerDataAccessException
     */
    Integer deleteSession(Integer sessionId) throws ServerDataAccessException;

}
