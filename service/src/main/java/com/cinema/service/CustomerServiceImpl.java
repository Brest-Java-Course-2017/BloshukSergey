package com.cinema.service;

import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.util.Assert.*;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService, InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerDao customerDao;


    @Override
    public Integer add(Customer customer) throws DataAccessException {
        LOGGER.debug("add({})", customer);

        notNull(customer, "customer must not be null");
        hasText(customer.getName(), "Customer's must have name");

        Integer customerId = customerDao.add(customer);

        return customerId;
    }

    @Override
    public Integer delete(Integer id) throws DataAccessException {
        LOGGER.debug("delete({})", id);

        notNull(id, "customerId must not be null");

        Integer quantity = customerDao.delete(id);

        return quantity;
    }

    @Override
    public Integer update(Customer customer) throws DataAccessException {
        LOGGER.debug("update({})", customer);

        notNull(customer, "customer must not be null");
        notNull(customer.getCustomerId(), "customerId must not be null");
        hasText(customer.getName(), "Customer must have name");

        Integer quantity = customerDao.update(customer);

        return quantity;
    }

    @Override
    public List<Customer> getByName(String name) throws DataAccessException {
        LOGGER.debug("getByName({})", name);

        notNull(name, "name must not be null");
        hasText(name, "name must be");

        List<Customer> customers = customerDao.getByName(name);

        return customers;
    }

    @Override
    public Customer getById(Integer id) throws DataAccessException {
        LOGGER.debug("getById({})", id);

        notNull(id, "id must not be null");
        isTrue(id > 0, "id must be greater than 0");

        Customer customer = customerDao.getById(id);

        return customer;
    }

    @Override
    public List<Customer> getAll() throws DataAccessException {
        LOGGER.debug("getAll()");

        List<Customer> customers = customerDao.getAll();

        return customers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (customerDao == null) {
            throw new BeanCreationException("customerDao is null");
        }
    }
}
