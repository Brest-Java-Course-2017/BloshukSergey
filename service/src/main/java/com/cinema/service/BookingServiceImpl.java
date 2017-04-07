package com.cinema.service;

import com.cinema.aop.annotation.Loggable;
import com.cinema.dao.BookingDao;
import com.cinema.dao.SessionDao;
import com.cinema.model.Customer;
import com.cinema.model.Session;
import com.cinema.model.SessionWithSeats;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Service
@Transactional
public class BookingServiceImpl implements BookingService, InitializingBean {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    @Loggable
    public List<Customer> getCustomersBySessionId(Integer id) throws DataAccessException {
        notNull(id, "Id must not be null");
        isTrue(id > 0, "Id must be greater than 0");

        List<Customer> customers = bookingDao.getCustomersBySessionId(id);

        return customers;
    }

    @Override
    @Loggable
    public List<SessionWithSeats> getSessionsWithSeats(Date firstDate, Date secondDate) throws DataAccessException {
        if(firstDate != null && secondDate != null)
            isTrue(firstDate.compareTo(secondDate) < 0, "First date must be less than second date");

        List<SessionWithSeats> sessions = bookingDao.getSessionsWithSeats(firstDate, secondDate);

        return sessions;
    }

    @Override
    @Loggable
    public Integer delete(Integer sessionId, Integer customerId) throws DataAccessException {
        notNull(sessionId, "sessionId must not be null");
        notNull(customerId, "customerId must not be null");

        Integer quantity = bookingDao.delete(sessionId, customerId);

        return quantity;
    }

    @Override
    @Loggable
    public Integer add(Integer sessionId, Integer customerId) throws DataAccessException {
        notNull(sessionId, "sessionId must not be null");
        notNull(customerId, "customerId must not be null");

        Boolean check = bookingDao.checkUpBooking(sessionId, customerId);
        if(check) {
            throw new IllegalArgumentException("Booking with sessionId = " + sessionId + " and customerId = " + customerId + " exist already.");
        }

        Session session = sessionDao.getById(sessionId);
        Integer totalSeats = session.getTotalSeats();
        Integer seats = bookingDao.getSeatsBySessionId(sessionId);

        if(seats >= totalSeats) {
            throw new IllegalArgumentException("Session " + session.getMovieName() + " is full already.");
        }
        Integer quantity = bookingDao.add(sessionId, customerId);

        return quantity;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (bookingDao == null) {
            throw new BeanCreationException("customerDao is null");
        }
    }
}
