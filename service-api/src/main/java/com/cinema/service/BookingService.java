package com.cinema.service;

import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;

import java.util.Date;
import java.util.List;

public interface BookingService {
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
    List<SessionWithSeats> getSessionWithSeats(Date firstDate, Date secondDate);

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
}
