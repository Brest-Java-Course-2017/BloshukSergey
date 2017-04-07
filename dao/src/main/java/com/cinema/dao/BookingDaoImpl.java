package com.cinema.dao;

import com.cinema.aop.annotation.Loggable;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class BookingDaoImpl implements BookingDao, InitializingBean {

    public static final String SEATS = "seats";
    public static final String NAME = "name";
    @Value("${sql.booking.getSessionsWithSeats}")
    String SQL_GET_SESSIONS_WITH_SEATS;

    @Value("${sql.booking.getCustomersBySessionId}")
    String SQL_GET_CUSTOMERS_BY_SESSION_ID;

    @Value("${sql.booking.add}")
    String SQL_ADD;

    @Value("${sql.booking.delete}")
    String SQL_DELETE;

    @Value("${sql.booking.getSeatsBySessionId}")
    String SQL_GET_SEATS_BY_SESSION_ID;

    @Value("${sql.booking.getCountByIds}")
    String SQL_GET_COUNT_BY_IDS;

    private static final String SESSION_ID = "sessionId";

    private static final String CUSTOMER_ID = "customerId";

    private static final String MOVIE_NAME = "movieName";

    private static final String SESSION_DATE = "sessionDate";

    private static final String TOTAL_SEATS = "totalSeats";

    private static final String FIRST_DATE = "firstDate";

    private static final String SECOND_DATE = "secondDate";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BookingDaoImpl (DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Loggable
    public void afterPropertiesSet() throws Exception {
        if (namedParameterJdbcTemplate == null) {
            throw new BeanCreationException("NamedParameterJdbcTemplate is null");
        }
    }

    @Override
    @Loggable
    public List<Customer> getCustomersBySessionId(Integer id) throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, id);
        List<Customer> customers = namedParameterJdbcTemplate.query(SQL_GET_CUSTOMERS_BY_SESSION_ID, parameterSource, new CustomerRowMapper());

        return customers;
    }

    @Override
    @Loggable
    public List<SessionWithSeats> getSessionsWithSeats(Date firstDate, Date secondDate) throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(FIRST_DATE, firstDate)
                .addValue(SECOND_DATE, secondDate);
        List<SessionWithSeats> sessions = namedParameterJdbcTemplate.query(
                SQL_GET_SESSIONS_WITH_SEATS,
                parameterSource,
                new SessionWithSeatsRowMapper());

        return sessions;
    }

    @Override
    @Loggable
    public Integer delete(Integer sessionId, Integer customerId)  throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, sessionId)
                .addValue(CUSTOMER_ID, customerId);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_DELETE, parameterSource);

        return quantity;
    }

    @Override
    @Loggable
    public Integer add(Integer sessionId, Integer customerId) throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, sessionId)
                .addValue(CUSTOMER_ID, customerId);
        Integer id = namedParameterJdbcTemplate.update(SQL_ADD, parameterSource);

        return id;
    }

    @Override
    @Loggable
    public Integer getSeatsBySessionId(Integer id) throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, id);
        Integer seats = namedParameterJdbcTemplate.queryForObject(SQL_GET_SEATS_BY_SESSION_ID, parameterSource, Integer.class);

        return seats;
    }

    @Override
    @Loggable
    public Boolean checkUpBooking(Integer sessionId, Integer customerId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, sessionId).addValue(CUSTOMER_ID, customerId);
        Integer count = namedParameterJdbcTemplate.queryForObject(SQL_GET_COUNT_BY_IDS, parameterSource, Integer.class);

        return count > 0 ? new Boolean(true): new Boolean(false);
    }

    private static final class SessionWithSeatsRowMapper implements RowMapper<SessionWithSeats> {
        @Override
        public SessionWithSeats mapRow(ResultSet rs, int rowNum) throws SQLException {
            SessionWithSeats session = new SessionWithSeats(
                    rs.getInt(SESSION_ID),
                    rs.getString(MOVIE_NAME),
                    rs.getDate(SESSION_DATE),
                    rs.getInt(TOTAL_SEATS),
                    rs.getInt(SEATS)
            );
            return session;
        }
    }

    private static final class CustomerRowMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer(
                    rs.getInt(CUSTOMER_ID),
                    rs.getString(NAME)
            );
            return customer;
        }
    }
}
