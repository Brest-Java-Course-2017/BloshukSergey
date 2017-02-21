package com.epam.test.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * UserDao implementation.
 */

public class UserDaoImpl implements UserDao, InitializingBean {

    @Value("${sql.getAllUsers}")
    private String SQL_GET_ALL_USERS;

    @Value("${sql.getUserById}")
    private String SQL_GET_USER_BY_ID;

    @Value("${sql.addUser}")
    private String SQL_ADD_USER;

    @Value("${sql.updateUser}")
    private String SQL_UPDATE_USER;

    @Value("${sql.deleteUser}")
    private String SQL_DELETE_USER;

    @Value("${sql.getUserByLogin}")
    private String SQL_GET_USER_BY_LOGIN;

    private static final String USER_ID = "user_id";

    private static final String LOGIN = "login";

    private static final String PASSWORD = "password";

    private static final String DESCRIPTION = "description";

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("getAllUsers()");

        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        LOGGER.debug("getUserById({})", userId);

        SqlParameterSource namedParameters = new MapSqlParameterSource(USER_ID, userId);
        User user = namedParameterJdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, namedParameters, new UserRowMapper());

        return user;
    }

    @Override
    public User getUserByLogin(String userLogin) {
        LOGGER.debug("getUserByLogin({})", userLogin);

        SqlParameterSource namedParameters = new MapSqlParameterSource(LOGIN, userLogin);
        User user = namedParameterJdbcTemplate.queryForObject(SQL_GET_USER_BY_LOGIN, namedParameters, new UserRowMapper());

        return user;
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser({})", user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = getUserParameters(user);
        namedParameterJdbcTemplate.update(SQL_ADD_USER, parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateUser(User user) {
        LOGGER.debug("updateUser({})", user);

        MapSqlParameterSource parameterSource = getUserParameters(user);
        Integer id = namedParameterJdbcTemplate.update(SQL_UPDATE_USER, parameterSource);

        return id;
    }

    @Override
    public Integer deleteUser(Integer userId) {
        LOGGER.debug("deleteUser({})", userId);

        SqlParameterSource namedParameters = new MapSqlParameterSource(USER_ID, userId);
        Integer id = namedParameterJdbcTemplate.update(SQL_DELETE_USER, namedParameters);

        return id;
    }

    private final static MapSqlParameterSource getUserParameters(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, user.getUserId());
        parameterSource.addValue(LOGIN, user.getLogin());
        parameterSource.addValue(PASSWORD, user.getPassword());
        parameterSource.addValue(DESCRIPTION, user.getDescription());

        return parameterSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(jdbcTemplate == null) {
            throw new BeanCreationException("JdbcTemplate is null");
        }

        if(namedParameterJdbcTemplate == null) {
            throw new BeanCreationException("NamedParameterJdbcTemplate is null");
        }
    }

    /**
     * User RowMapper class
     */
    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getInt(USER_ID),
                    resultSet.getString(LOGIN),
                    resultSet.getString(PASSWORD),
                    resultSet.getString(DESCRIPTION));
            return user;
        }
    }
}
