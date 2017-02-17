package com.epam.test.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserDao implementation.
 */

public class UserDaoImpl implements UserDao {
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM app_user;";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM app_user WHERE user_id=:user_id;";
    private static final String SQL_ADD_USER = "INSERT INTO app_user (user_id, login, password, description) VALUES (:user_id, :login, :password, :description);";
    private static final String SQL_UPDATE_USER = "UPDATE app_user SET login=:login, password=:password, description=:description WHERE user_id=:user_id;";
    private static final String SQL_DELETE_USER = "DELETE FROM app_user WHERE user_id=:user_id;";

    private static final String USER_ID = "user_id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String DESCRIPTION = "description";

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class.getName());

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
        LOGGER.debug("getAllUsers({})", userId);

        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource(USER_ID, userId);
            return namedParameterJdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, namedParameters, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser({})", user);

        Map<String, Object> userParameters = getUserParameters(user);
        return namedParameterJdbcTemplate.update(SQL_ADD_USER, userParameters);
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})", user);

        Map<String, Object> userParameters = getUserParameters(user);
        namedParameterJdbcTemplate.update(SQL_UPDATE_USER, userParameters);
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("deleteUser({})", userId);

        SqlParameterSource namedParameters = new MapSqlParameterSource(USER_ID, userId);
        namedParameterJdbcTemplate.update(SQL_DELETE_USER, namedParameters);
    }


    private final static Map<String, Object> getUserParameters(User user) {
        Map<String, Object> userParameters = new HashMap<>();

        userParameters.put(LOGIN, user.getLogin());
        userParameters.put(PASSWORD, user.getPassword());
        userParameters.put(DESCRIPTION, user.getDescription());
        userParameters.put(USER_ID, user.getUserId());

        return userParameters;
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
