package com.cinema.dao;

import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class CustomerDaoImplTest {

    private static final Customer EXIST_CUSTOMER = new Customer(1, 1, "Sergey", "Bloshuk", 2);

    private static final Customer ADD_CUSTOMER = new Customer(1, "Bob", "Bloshuk", 3);

    private static final Customer UPDATE_CUSTOMER = new Customer(1, 1, "Bob", "Bloshuk", 3);

    private static final Integer QUANTITY_CUSTOMERS_ZERO = 0;

    private static final Integer QUANTITY_CUSTOMERS_ONE = 1;

    private static final Integer BAD_ID = 100;

    private static final Customer BAD_CUSTOMER = new Customer(null, null, null, null);

    private static final Customer BAD_CUSTOMER_WITH_BAD_ID = new Customer(100, 100, "Bob", "Bob", 100);

    private static final Logger LOGGER = LogManager.getLogger(CustomerDaoImplTest.class);

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void getAllCustomers() throws Exception {
        LOGGER.debug("test: getAllCustomers()");

        List<Customer> customers = customerDao.getAllCustomers();

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() > 0);
    }

    @Test
    public void getCustomerById() throws Exception {
        LOGGER.debug("test: getCustomerById({})", EXIST_CUSTOMER.getCustomerId());

        Customer customer = customerDao.getCustomerById(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("First name", EXIST_CUSTOMER.getFirstName(), customer.getFirstName());
        assertEquals("Last name", EXIST_CUSTOMER.getLastName(), customer.getLastName());
    }

    @Test(expected = DataAccessException.class)
    public void getCustomerWithNotExistId() throws Exception {
        LOGGER.debug("test: getCustomerWithNotExistId({})", BAD_ID);

        Customer customer = customerDao.getCustomerById(BAD_ID);
    }

    @Test
    public void addCustomer() throws Exception {
        LOGGER.debug("test: addCustomer({})", ADD_CUSTOMER);

        Integer customerId = customerDao.addCustomer(ADD_CUSTOMER);

        assertNotNull("Customer id must be not null", customerId);

        Customer customer = customerDao.getCustomerById(customerId);

        assertNotNull("Customer must be not null", customer);
        assertEquals("First name", ADD_CUSTOMER.getFirstName(), customer.getFirstName());
        assertEquals("Last name", ADD_CUSTOMER.getLastName(), customer.getLastName());
    }

    @Test(expected = DataAccessException.class)
    public void addCustomerWithNullValues() throws Exception {
        LOGGER.debug("test: addUserWithNullValues({})", BAD_CUSTOMER);

        Integer customerId = customerDao.addCustomer(BAD_CUSTOMER);
    }

    @Test
    public void updateCustomer() throws Exception {
        LOGGER.debug("test: updateCustomer(from {} to {})", UPDATE_CUSTOMER, EXIST_CUSTOMER);

        Integer quantity = customerDao.updateCustomer(UPDATE_CUSTOMER);

        assertNotNull(quantity);
        assertEquals("Quantity changed records", QUANTITY_CUSTOMERS_ONE, quantity);
    }

    @Test
    public void updateCustomerWithNotExistId() throws Exception {
        LOGGER.debug("test: updateCustomerByNotExistId({})", BAD_CUSTOMER_WITH_BAD_ID);

        Integer quantity = customerDao.updateCustomer(BAD_CUSTOMER_WITH_BAD_ID);

        assertNotNull(quantity);
        assertEquals("Quantity changed records", QUANTITY_CUSTOMERS_ZERO, quantity);
    }

    @Test
    public void deleteCustomer() throws Exception {
        LOGGER.debug("test: deleteCustomer({})", EXIST_CUSTOMER);

        Integer quantityDeleted = customerDao.deleteCustomer(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Quantity of delete customer must be not null", quantityDeleted);
        assertEquals("Quantity of delete customer", QUANTITY_CUSTOMERS_ONE, quantityDeleted);
    }

    @Test
    public void deleteCustomerWithNotExistId() throws Exception {
        LOGGER.debug("test: deleteCustomerWithNotExistId({})", BAD_ID);

        Integer quantityDeleted = customerDao.deleteCustomer(BAD_ID);

        assertNotNull("Quantity of delete customer must be not null", quantityDeleted);
        assertEquals("Quantity of delete customer", QUANTITY_CUSTOMERS_ZERO, quantityDeleted);
    }
}