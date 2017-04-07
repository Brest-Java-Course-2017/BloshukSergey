package com.cinema.dao;

import com.cinema.aop.annotation.Loggable;
import com.cinema.model.Customer;
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
public class CustomerDaoImpl implements CustomerDao, InitializingBean {

    public static final String CUSTOMER_ID = "customerId";

    public static final String NAME = "name";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${sql.customer.add}")
    String SQL_ADD;

    @Value("${sql.customer.delete}")
    String SQL_DELETE;

    @Value("${sql.customer.update}")
    String SQL_UPDATE;

    @Value("${sql.customer.getById}")
    String SQL_GET_BY_ID;

    @Value("${sql.customer.getByName}")
    String SQL_GET_BY_NAME;

    @Value("${sql.customer.getAll}")
    String SQL_GET_ALL;

    @Autowired
    public CustomerDaoImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (namedParameterJdbcTemplate == null) {
            throw new BeanCreationException("NamedParameterJdbcTemplate is null");
        }
    }

    @Override
    @Loggable
    public Integer add(Customer customer)  throws DataAccessException {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_ADD, parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    @Loggable
    public Integer delete(Integer id) throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource(CUSTOMER_ID, id);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_DELETE, parameterSource);

        return quantity;
    }

    @Override
    @Loggable
    public Integer update(Customer customer) throws DataAccessException {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_UPDATE, parameterSource);

        return quantity;
    }

    @Override
    @Loggable
    public List<Customer> getByName(String name) throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource(NAME, name);
        List<Customer> customer = namedParameterJdbcTemplate.query(SQL_GET_BY_NAME, parameterSource, new CustomerRowMapper());

        return customer;
    }

    @Override
    @Loggable
    public Customer getById(Integer id) throws DataAccessException {
        SqlParameterSource parameterSource = new MapSqlParameterSource(CUSTOMER_ID, id);
        Customer customers = namedParameterJdbcTemplate.queryForObject(SQL_GET_BY_ID, parameterSource, new CustomerRowMapper());

        return customers;
    }

    @Override
    @Loggable
    public List<Customer> getAll() throws DataAccessException {
        List<Customer> customers = namedParameterJdbcTemplate.query(SQL_GET_ALL, new CustomerRowMapper());

        return customers;
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
