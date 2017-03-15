package com.cinema.service;


import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-service-mock.xml"})
public class CustomerServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImplMockTest.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDaoMock;

    private static final Customer CUSTOMER_1 = new Customer(1,1, "Sergey", "Bloshuk", 2);

    private static final Customer CUSTOMER_2 = new Customer(2,2, "Bob", "Bob", 1);

    private static final Integer QUANTITY_1 = 1;

    private static final Integer BAD_ID = -100;

    @After
    public void clean() {
        verify(customerDaoMock);
    }

    @Before
    public void setUp() {
        reset(customerDaoMock);
    }

    @Test
    public void getAllCustomers() throws Exception {
        LOGGER.debug("mock test: getAllCustomers()");

        List<Customer> expectCustomers = new ArrayList<Customer>();
        expectCustomers.add(CUSTOMER_1);
        expectCustomers.add(CUSTOMER_2);

        expect(customerDaoMock.getAllCustomers()).andReturn(expectCustomers);
        replay(customerDaoMock);

        List<Customer> customers = customerService.getAllCustomers();

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() == expectCustomers.size());
    }
    @Test
    public void getCustomersBySessionId() throws Exception {
        LOGGER.debug("mock test: getCustomersBySessionId()");

        List<Customer> expectCustomers = new ArrayList<Customer>();
        expectCustomers.add(CUSTOMER_1);

        expect(customerDaoMock.getCustomersBySessionId(CUSTOMER_1.getSessionId())).andReturn(expectCustomers);
        replay(customerDaoMock);

        List<Customer> customers = customerService.getCustomersBySessionId(CUSTOMER_1.getSessionId());

        assertNotNull("Customers must be not null", customers);
        assertEquals("Customer", expectCustomers.size(), customers.size());
    }

    @Test
    public void getCustomerById() throws Exception {
        LOGGER.debug("mock test: getCustomerById()");

        expect(customerDaoMock.getCustomerById(CUSTOMER_1.getCustomerId())).andReturn(CUSTOMER_1);
        replay(customerDaoMock);

        Customer customer = customerService.getCustomerById(CUSTOMER_1.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("Customer", CUSTOMER_1, customer);
    }

    @Test
    public void addCustomer() throws Exception {
        LOGGER.debug("mock test: addCustomer()");

        expect(customerDaoMock.addCustomer(CUSTOMER_1)).andReturn(CUSTOMER_1.getCustomerId());
        replay(customerDaoMock);

        Integer customerId = customerService.addCustomer(CUSTOMER_1);

        assertNotNull("Customer id must be not null", customerId);
        assertEquals("Customer id", CUSTOMER_1.getCustomerId(), customerId);
    }

    @Test
    public void updateCustomer() throws Exception {
        LOGGER.debug("mock test: updateCustomer()");

        expect(customerDaoMock.updateCustomer(CUSTOMER_1)).andReturn(QUANTITY_1);
        replay(customerDaoMock);

        Integer quantity = customerService.updateCustomer(CUSTOMER_1);

        assertNotNull("Quantity must be not null", quantity);
        assertEquals("Quantity updated", QUANTITY_1, quantity);
    }

    @Test
    public void deleteCustomer() throws Exception {
        LOGGER.debug("mock test: deleteCustomer()");

        expect(customerDaoMock.deleteCustomer(CUSTOMER_1.getCustomerId())).andReturn(QUANTITY_1);
        replay(customerDaoMock);

        Integer quantityDeleted = customerService.deleteCustomer(CUSTOMER_1.getCustomerId());

        assertNotNull("Quantity deleted must be not null", quantityDeleted);
        assertEquals("Quantity deleted", QUANTITY_1, quantityDeleted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomerByBadId() throws Exception {
        LOGGER.debug("mock test: getCustomerByBadId()");

        replay(customerDaoMock);

        customerService.getCustomerById(BAD_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomerByNullId() throws Exception {
        LOGGER.debug("mock test: getCustomerByNullId()");

        replay(customerDaoMock);

        customerService.getCustomerById(null);
    }


}
