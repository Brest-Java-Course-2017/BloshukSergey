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

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CustomerDao customerDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> getAllCustomers() throws DataAccessException {
        LOGGER.debug("getAllCustomers()");

        List<Customer> customers = customerDao.getAllCustomers();

        return customers;
    }

    @Override
    public Customer getCustomerById(Integer customerId) throws DataAccessException {
        LOGGER.debug("getCustomerById({})", customerId);

        notNull(customerId, "customerId must not be null");
        isTrue(customerId > 0, "customerId must be greater than 0");

        Customer customer = customerDao.getCustomerById(customerId);

        return customer;
    }

    @Override
    public Integer addCustomer(Customer customer) throws DataAccessException {
        LOGGER.debug("addCustomer({})", customer);

        notNull(customer, "customer must not be null");
        isTrue(customer.getSessionId() > 0, "sessionId must be greater than 0");
        hasText(customer.getFirstName(), "Customer must have first name");
        hasText(customer.getLastName(), "Customer must have second name");
        isTrue(customer.getQuantityTickets() > 0, "quantityTickets must be greater than 0");

        Integer customerId = customerDao.addCustomer(customer);

        return customerId;
    }

    @Override
    public Integer updateCustomer(Customer customer) throws DataAccessException {
        LOGGER.debug("updateCustomer({})", customer);

        notNull(customer, "customer must not be null");
        notNull(customer.getCustomerId(), "customerId must not be null");
        isTrue(customer.getSessionId() > 0, "sessionId must be greater than 0");
        hasText(customer.getFirstName(), "Customer must have first name");
        hasText(customer.getLastName(), "Customer must have second name");
        isTrue(customer.getQuantityTickets() > 0, "quantityTickets must be greater than 0");

        Integer quantity = customerDao.updateCustomer(customer);

        return quantity;
    }

    @Override
    public Integer deleteCustomer(Integer customerId) throws DataAccessException {
        LOGGER.debug("deleteCustomer({})", customerId);

        notNull(customerId, "customerId must not be null");

        Integer quantity = customerDao.deleteCustomer(customerId);

        return quantity;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (customerDao == null) {
            throw new BeanCreationException("customerDao is null");
        }
    }
}
