package com.cinema.dao;

import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;

import java.util.List;
import java.util.Date;

public interface BookingDao {

    /**
     *
     * @param id session id
     * @return customers who booking seats.
     */
    List<Customer> getCustomersBySessionId(Integer id);

    /**
     *
     * @return session with seats.
     */
    List<SessionWithSeats> getSessionsWithSeats(Date firstDate, Date secondDate);

    /**
     *
     * @return quantity of deleted customers.
     */
    Integer delete(Integer sessionId, Integer customerId);

    /**
     *
     * @return new id.
     */
    Integer add(Integer sessionId, Integer customerId);

    /**
     *
     * @param id session id
     * @return seats
     */
    Integer getSeatsBySessionId(Integer id);
}
