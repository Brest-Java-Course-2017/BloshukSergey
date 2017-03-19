package com.cinema.service;

import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

public interface BookingService {
    /**
     *
     * @param id session id
     * @return customers who booking seats.
     */
    List<Customer> getCustomersBySessionId(Integer id) throws DataAccessException;

    /**
     *
     * @return session with seats.
     */
    List<SessionWithSeats> getSessionsWithSeats(Date firstDate, Date secondDate) throws DataAccessException;

    /**
     *
     * @return quantity of deleted customers.
     */
    Integer delete(Integer sessionId, Integer customerId) throws DataAccessException;

    /**
     *
     * @return new id.
     */
    Integer add(Integer sessionId, Integer customerId) throws DataAccessException;
}
