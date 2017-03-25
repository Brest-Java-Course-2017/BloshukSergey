package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;

import java.util.Date;
import java.util.List;

public interface BookingClient {
    /**
     *
     * @param id session id
     * @return customers who booking seats.
     */
    List<Customer> getCustomersBySessionId(Integer id) throws ServerDataAccessException;

    /**
     *
     * @return session with seats.
     */
    List<SessionWithSeats> getSessionsWithSeats(Date firstDate, Date secondDate) throws ServerDataAccessException;

    /**
     *
     * @return quantity of deleted customers.
     */
    Integer delete(Integer sessionId, Integer customerId) throws ServerDataAccessException;

    /**
     *
     * @return new id.
     */
    Integer add(Integer sessionId, Integer customerId) throws ServerDataAccessException;
}
