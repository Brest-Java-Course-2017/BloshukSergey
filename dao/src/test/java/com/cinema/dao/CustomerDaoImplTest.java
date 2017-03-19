package com.cinema.dao;

import com.cinema.configuration.SpringDaoTestConfiguration;
import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringDaoTestConfiguration.class)
@Transactional
public class CustomerDaoImplTest {

    private static final Customer EXIST_CUSTOMER = new Customer(1, "Sergey Bloshuk");

    private static final Customer ADD_CUSTOMER = new Customer("Bob");

    private static final Customer UPDATE_CUSTOMER = new Customer(1, "Bob Bloshuk");

    private static final Integer EXPECTED = 1;

    private static final Logger LOGGER = LogManager.getLogger(CustomerDaoImplTest.class);

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void add() throws Exception {
        LOGGER.debug("test: addCustomer({})", ADD_CUSTOMER);

        Integer customerId = customerDao.add(ADD_CUSTOMER);

        assertNotNull("Customer id must be not null", customerId);

        Customer customer = customerDao.getById(customerId);

        assertNotNull("Customer must be not null", customer);
        assertEquals("Name", ADD_CUSTOMER.getName(), customer.getName());
    }

    @Test
    public void delete() throws Exception {
        LOGGER.debug("test: delete({})", EXIST_CUSTOMER);

        Integer quantity = customerDao.delete(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Quantity of delete customers must be not null", quantity);
        assertEquals("Quantity of delete customers", EXPECTED, quantity);
    }

    @Test
    public void update() throws Exception {
        LOGGER.debug("test: updateCustomer(from {} to {})", UPDATE_CUSTOMER, EXIST_CUSTOMER);

        Integer quantity = customerDao.update(UPDATE_CUSTOMER);

        assertNotNull(quantity);
        assertEquals("Quantity changed records", EXPECTED, quantity);
    }

    @Test
    public void getByName() throws Exception {
        LOGGER.debug("test: getByName({})", EXIST_CUSTOMER.getName());

        List<Customer> customers = customerDao.getByName(EXIST_CUSTOMER.getName());

        assertNotNull("Customer must be not null", customers);
        assertEquals("name", EXPECTED.intValue(), customers.size());
    }

    @Test
    public void getById() throws Exception {
        LOGGER.debug("test: getById({})", EXIST_CUSTOMER.getCustomerId());

        Customer customer = customerDao.getById(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("name", EXIST_CUSTOMER.getName(), customer.getName());
    }

    @Test
    public void getAll() throws Exception {
        LOGGER.debug("test: getAll()");

        List<Customer> customers = customerDao.getAll();

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() > 0);
    }

}