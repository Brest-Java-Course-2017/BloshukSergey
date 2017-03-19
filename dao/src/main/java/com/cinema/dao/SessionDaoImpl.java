package com.cinema.dao;

import com.cinema.model.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
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
import java.util.List;

@Repository
public class SessionDaoImpl implements SessionDao, InitializingBean {

    @Value("${sql.session.getAll}")
    String SQL_GET_ALL;

    @Value("${sql.session.add}")
    String SQL_ADD;

    @Value("${sql.session.delete}")
    String SQL_DELETE;

    @Value("${sql.session.update}")
    String SQL_UPDATE;

    @Value("${sql.session.getById}")
    String SQL_GET_BY_ID;

    private static final String SESSION_ID = "sessionId";

    private static final String MOVIE_NAME = "movieName";

    private static final String SESSION_DATE = "sessionDate";

    private static final String TOTAL_SEATS = "totalSeats";

    private static final Logger LOGGER = LogManager.getLogger(SessionDaoImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public SessionDaoImpl (DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (namedParameterJdbcTemplate == null) {
            throw new BeanCreationException("NamedParameterJdbcTemplate is null");
        }
    }

    @Override
    public Integer add(Session session)  throws DataAccessException {
        LOGGER.debug("addSession({})", session);

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(session);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_ADD, parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer delete(Integer id) throws DataAccessException {
        LOGGER.debug("deleteSession({})", id);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, id);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_DELETE, parameterSource);

        return quantity;
    }

    @Override
    public Integer update(Session session) throws DataAccessException {
        LOGGER.debug("update({})", session);

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(session);
        Integer rows = namedParameterJdbcTemplate.update(SQL_UPDATE, parameterSource);

        return rows;
    }

    @Override
    public Session getById(Integer id) throws DataAccessException {
        LOGGER.debug("getById({})", id);

        SqlParameterSource parameterSource = new MapSqlParameterSource(SESSION_ID, id);
        Session session = namedParameterJdbcTemplate.queryForObject(
                SQL_GET_BY_ID,
                parameterSource,
                new SessionRowMapper());

        return session;
    }

    @Override
    public List<Session> getAll() throws DataAccessException {
        LOGGER.debug("test: getAll()");

        List<Session> sessions = namedParameterJdbcTemplate.query(SQL_GET_ALL, new SessionRowMapper());

        return sessions;
    }

    private static final class SessionRowMapper implements RowMapper<Session> {
        @Override
        public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
            Session session = new Session(
                    rs.getInt(SESSION_ID),
                    rs.getString(MOVIE_NAME),
                    rs.getDate(SESSION_DATE),
                    rs.getInt(TOTAL_SEATS)
            );
            return session;
        }
    }

}