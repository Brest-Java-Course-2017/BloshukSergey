package com.cinema.service;

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
@ContextConfiguration(locations = {"classpath:test-spring-service.xml"})
@Transactional
public class CustomerServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImplTest.class);

    @Autowired
    private CustomerService customerService;

    private static final Customer EXIST_CUSTOMER = new Customer(1, 1, "Sergey", "Bloshuk", 2);

    private static final Customer ADD_CUSTOMER = new Customer(1, "Bob", "Bloshuk", 3);

    private static final Customer UPDATE_CUSTOMER = new Customer(1, 1, "Bob", "Bloshuk", 3);

    private static final Integer QUANTITY_CUSTOMERS_ONE = 1;

    @Test
    public void getAllCustomers() throws Exception {
        LOGGER.debug("test: getAllCustomers()");

        List<Customer> customers = customerService.getAllCustomers();

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() > 0);
    }

    @Test
    public void getCustomerById() throws Exception {
        LOGGER.debug("test: getCustomerById({})", EXIST_CUSTOMER.getCustomerId());

        Customer customer = customerService.getCustomerById(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("First name", EXIST_CUSTOMER.getFirstName(), customer.getFirstName());
        assertEquals("Last name", EXIST_CUSTOMER.getLastName(), customer.getLastName());
        assertEquals("Quantity tickets", EXIST_CUSTOMER.getQuantityTickets(), customer.getQuantityTickets());
        assertEquals("Session id", EXIST_CUSTOMER.getSessionId(), customer.getSessionId());
    }

    @Test
    public void addCustomer() throws Exception {
        LOGGER.debug("test: addCustomer({})", ADD_CUSTOMER);

        Integer customerId = customerService.addCustomer(ADD_CUSTOMER);

        assertNotNull("Customer id must be not null", customerId);

        Customer customer = customerService.getCustomerById(customerId);

        assertNotNull("Customer must be not null", customer);
        assertEquals("First name", ADD_CUSTOMER.getFirstName(), customer.getFirstName());
        assertEquals("Last name", ADD_CUSTOMER.getLastName(), customer.getLastName());
        assertEquals("Quantity tickets", ADD_CUSTOMER.getQuantityTickets(), customer.getQuantityTickets());
        assertEquals("Session id", ADD_CUSTOMER.getSessionId(), customer.getSessionId());
    }

    @Test
    public void updateCustomer() throws Exception {
        LOGGER.debug("test: updateCustomer(from {} to {})", UPDATE_CUSTOMER, EXIST_CUSTOMER);

        Integer quantity = customerService.updateCustomer(UPDATE_CUSTOMER);

        assertNotNull(quantity);
        assertEquals("Quantity changed records", QUANTITY_CUSTOMERS_ONE, quantity);
    }

    @Test
    public void deleteCustomer() throws Exception {
        LOGGER.debug("test: deleteCustomer({})", EXIST_CUSTOMER);

        Integer quantityDeleted = customerService.deleteCustomer(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Quantity of delete customer must be not null", quantityDeleted);
        assertEquals("Quantity of delete customer", QUANTITY_CUSTOMERS_ONE, quantityDeleted);
    }

}