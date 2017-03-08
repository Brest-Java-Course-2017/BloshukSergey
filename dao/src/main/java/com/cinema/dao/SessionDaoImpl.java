package com.cinema.dao;

import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SessionDaoImpl implements SessionDao, InitializingBean {

    private static final String SESSION_ID = "sessionId";

    private static final String MOVIE_NAME = "movieName";

    private static final String SESSION_DATE = "sessionDate";

    private static final String QUANTITY_TICKETS = "quantityTickets";

    private static final String FIRST_DATE = "firstDate";

    private static final String SECOND_DATE = "secondDate";

    @Value("${sql.getAllSessions}")
    String SQL_GET_ALL_SESSIONS;

    @Value("${sql.getAllSessionsWithQuantityTickets}")
    String SQL_GET_ALL_SESSIONS_WITH_QUANTITY_TICKETS;

    @Value("${sql.getAllSessionsWithQuantityTicketsDateToDate}")
    String SQL_GET_ALL_SESSIONS_WITH_QUANTITY_TICKETS_DATE_TO_DATE;

    @Value("${sql.getSessionById}")
    String SQL_GET_SESSION_BY_ID;

    @Value("${sql.addSession}")
    String SQL_ADD_SESSION;

    @Value("${sql.updateSession}")
    String SQL_UPDATE_SESSION;

    @Value("${sql.deleteSession}")
    String SQL_DELETE_SESSION;

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(SessionDaoImpl.class);

    @Autowired
    public SessionDaoImpl (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null) {
            throw new BeanCreationException("JdbcTemplate is null");
        }

        if (namedParameterJdbcTemplate == null) {
            throw new BeanCreationException("NamedParameterJdbcTemplate is null");
        }
    }


    @Override
    public List<Session> getAllSessions() throws DataAccessException {
        LOGGER.debug("test: getAllSessions()");

        List<Session> sessions = jdbcTemplate.query(SQL_GET_ALL_SESSIONS, new SessionRowMapper());

        return sessions;
    }

    @Override
    public List<SessionWithQuantityTickets> getAllSessionsWithQuantityTickets() throws DataAccessException {
        LOGGER.debug("test: getAllSessionsWithQuantityTickets()");

        List<SessionWithQuantityTickets> sessions = jdbcTemplate.query(
                SQL_GET_ALL_SESSIONS_WITH_QUANTITY_TICKETS,
                new SessionWithQuantityTicketsRowMapper());

        return sessions;
    }

    @Override
    public List<SessionWithQuantityTickets> getAllSessionsWithQuantityTicketsDateToDate(LocalDate firstDate, LocalDate secondDate) throws DataAccessException {
        LOGGER.debug("getAllSessionsWithQuantityTicketsDateToDate(from {} to {})", firstDate, secondDate);

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(FIRST_DATE, firstDate)
                .addValue(SECOND_DATE, secondDate);
        List<SessionWithQuantityTickets> sessions = namedParameterJdbcTemplate.query(
                SQL_GET_ALL_SESSIONS_WITH_QUANTITY_TICKETS_DATE_TO_DATE,
                parameterSource,
                new SessionWithQuantityTicketsRowMapper());

        return sessions;
    }

    @Override
    public Session getSessionById(Integer sessionId) throws DataAccessException {
        LOGGER.debug("getSessionById({})", sessionId);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, sessionId);
        Session session = namedParameterJdbcTemplate.queryForObject(
                SQL_GET_SESSION_BY_ID,
                parameterSource,
                new SessionRowMapper());

        return session;
    }

    @Override
    public Integer addSession(Session session) throws DataAccessException {
        LOGGER.debug("addSession({})", session);

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(session);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_ADD_SESSION, parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateSession(Session session) throws DataAccessException {
        LOGGER.debug("updateSession({})", session);

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(session);
        Integer rows = namedParameterJdbcTemplate.update(SQL_UPDATE_SESSION, parameterSource);

        return rows;
    }

    @Override
    public Integer deleteSession(Integer sessionId) throws DataAccessException {
        LOGGER.debug("deleteSession({})", sessionId);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, sessionId);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_DELETE_SESSION, parameterSource);

        return quantity;
    }

    private static final class SessionRowMapper implements RowMapper<Session> {
        @Override
        public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
            Session session = new Session(
                    rs.getInt(SESSION_ID),
                    rs.getString(MOVIE_NAME),
                    rs.getDate(SESSION_DATE).toLocalDate()
            );
            return session;
        }
    }

    private static final class SessionWithQuantityTicketsRowMapper implements RowMapper<SessionWithQuantityTickets> {
        @Override
        public SessionWithQuantityTickets mapRow(ResultSet rs, int rowNum) throws SQLException {
            SessionWithQuantityTickets session = new SessionWithQuantityTickets(
                    rs.getInt(SESSION_ID),
                    rs.getString(MOVIE_NAME),
                    rs.getDate(SESSION_DATE).toLocalDate(),
                    rs.getInt(QUANTITY_TICKETS)
            );
            return session;
        }
    }
}
