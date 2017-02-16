package com.epam.test.dao;

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

    public static final String SQL_GET_ALL_USERS = "SELECT * FROM app_user;";
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM app_user WHERE user_id=:userId;";
    public static final String SQL_ADD_USER = "INSERT INTO app_user (user_id, login, password, description) VALUES (:userId, :userLogin, :userPassword, :userDescription);";
    public static final String SQL_UPDATE_USER = "UPDATE app_user SET login=:userLogin, password=:userPassword, description=:userDescription WHERE user_id=:userId;";
    public static final String SQL_DELETE_USER = "DELETE FROM app_user WHERE user_id=:userId;";



    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);
            return namedParameterJdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, namedParameters, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer addUser(User user) {
        Map<String, Object> userParameters = getUserParameters(user);
        return namedParameterJdbcTemplate.update(SQL_ADD_USER, userParameters);
    }

    @Override
    public void updateUser(User user) {
        Map<String, Object> userParameters = getUserParameters(user);
        namedParameterJdbcTemplate.update(SQL_UPDATE_USER, userParameters);
    }

    @Override
    public void deleteUser(Integer userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);
        namedParameterJdbcTemplate.update(SQL_DELETE_USER, namedParameters);
    }


    private final static Map<String, Object> getUserParameters(User user) {
        Map<String, Object> userParameters = new HashMap<>();

        userParameters.put("userLogin", user.getLogin());
        userParameters.put("userPassword", user.getPassword());
        userParameters.put("userDescription", user.getDescription());
        userParameters.put("userId", user.getUserId());

        return userParameters;
    }

    /**
     * User RowMapper class
     */
    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getInt("user_Id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("description"));
            return user;
        }
    }
}
