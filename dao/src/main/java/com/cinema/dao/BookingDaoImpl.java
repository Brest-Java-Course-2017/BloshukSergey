package com.cinema.dao;

import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final String SESSION_ID = "sessionId";

    private static final String CUSTOMER_ID = "customerId";

    private static final String MOVIE_NAME = "movieName";

    private static final String SESSION_DATE = "sessionDate";

    private static final String TOTAL_SEATS = "totalSeats";

    private static final String FIRST_DATE = "firstDate";

    private static final String SECOND_DATE = "secondDate";

    private static final Logger LOGGER = LogManager.getLogger(SessionDaoImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BookingDaoImpl (DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (namedParameterJdbcTemplate == null) {
            throw new BeanCreationException("NamedParameterJdbcTemplate is null");
        }
    }

    @Override
    public List<Customer> getCustomersBySessionId(Integer id) throws DataAccessException {
        LOGGER.debug("getCustomersBySessionId({})", id);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, id);
        List<Customer> customers = namedParameterJdbcTemplate.query(SQL_GET_CUSTOMERS_BY_SESSION_ID, parameterSource, new CustomerRowMapper());

        return customers;
    }

    @Override
    public List<SessionWithSeats> getSessionsWithSeats(Date firstDate, Date secondDate) throws DataAccessException {
        LOGGER.debug("test: getSessionWithSeats()");

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
    public Integer delete(Integer sessionId, Integer customerId)  throws DataAccessException {
        LOGGER.debug("delete({}, {})", sessionId, customerId);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, sessionId)
                .addValue(CUSTOMER_ID, customerId);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_DELETE, parameterSource);

        return quantity;
    }

    @Override
    public Integer add(Integer sessionId, Integer customerId) throws DataAccessException {
        LOGGER.debug("add({}, {})", sessionId, customerId);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, sessionId)
                .addValue(CUSTOMER_ID, customerId);
        Integer id = namedParameterJdbcTemplate.update(SQL_ADD, parameterSource);

        return id;
    }

    @Override
    public Integer getSeatsBySessionId(Integer id) throws DataAccessException {
        LOGGER.debug("getSeatsBySessionId({})", id);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, id);
        Integer seats = namedParameterJdbcTemplate.queryForObject(SQL_GET_SEATS_BY_SESSION_ID, parameterSource, Integer.class);

        return seats;
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
