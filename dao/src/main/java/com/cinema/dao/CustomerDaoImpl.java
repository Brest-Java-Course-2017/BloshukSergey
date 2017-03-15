package com.cinema.dao;

import com.cinema.model.Customer;
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
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao, InitializingBean {

    public static final String CUSTOMER_ID = "customerId";

    public static final String SESSION_ID = "sessionId";

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String QUANTITY_TICKETS = "quantityTickets";

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(CustomerDaoImpl.class);

    @Value("${sql.getAllCustomers}")
    String SQL_GET_ALL_CUSTOMERS;

    @Value("${sql.getCustomersBySessionId}")
    String SQL_GET_CUSTOMERS_BY_SESSION_ID;

    @Value("${sql.getCustomerById}")
    String SQL_GET_CUSTOMER_BY_ID;

    @Value("${sql.addCustomer}")
    String SQL_ADD_CUSTOMER;

    @Value("${sql.updateCustomer}")
    String SQL_UPDATE_CUSTOMER;

    @Value("${sql.deleteCustomer}")
    String SQL_DELETE_CUSTOMER;

    @Autowired
    public CustomerDaoImpl(DataSource dataSource) {
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
    public List<Customer> getAllCustomers() {
        LOGGER.debug("getAllCustomers()");

        List<Customer> customers = jdbcTemplate.query(SQL_GET_ALL_CUSTOMERS, new CustomerRowMapper());

        return customers;
    }

    @Override
    public List<Customer> getCustomersBySessionId(Integer sessionId) throws DataAccessException {
        LOGGER.debug("getCustomersBySessionId({})", sessionId);

        List<Customer> customers = jdbcTemplate.query(SQL_GET_CUSTOMERS_BY_SESSION_ID, new CustomerRowMapper(), sessionId);

        return customers;
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        LOGGER.debug("getCustomerById({})", customerId);

        SqlParameterSource parameterSource = new MapSqlParameterSource(CUSTOMER_ID, customerId);
        Customer customer = namedParameterJdbcTemplate.queryForObject(SQL_GET_CUSTOMER_BY_ID, parameterSource, new CustomerRowMapper());

        return customer;
    }

    @Override
    public Integer addCustomer(Customer customer) {
        LOGGER.debug("addCustomer({})", customer);

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_ADD_CUSTOMER, parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateCustomer(Customer customer) {
        LOGGER.debug("updateCustomer({})", customer);

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_UPDATE_CUSTOMER, parameterSource);

        return quantity;
    }

    @Override
    public Integer deleteCustomer(Integer customerId) {
        LOGGER.debug("deleteCustomer({})", customerId);

        SqlParameterSource parameterSource = new MapSqlParameterSource(CUSTOMER_ID, customerId);
        Integer quantity = namedParameterJdbcTemplate.update(SQL_DELETE_CUSTOMER, parameterSource);

        return quantity;
    }

    private static final class CustomerRowMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer(
                    rs.getInt(CUSTOMER_ID),
                    rs.getInt(SESSION_ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getInt(QUANTITY_TICKETS)
            );
            return customer;
        }
    }
}
