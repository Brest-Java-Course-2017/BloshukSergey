package com.cinema.service;

import com.cinema.configuration.SpringServiceMockTestConfiguration;
import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;
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
@ContextConfiguration(classes=SpringServiceMockTestConfiguration.class)
public class CustomerServiceImplMockTest {

    private static final Customer CUSTOMER_1 = new Customer(1, "Sergey Bloshuk");

    private static final Customer CUSTOMER_2 = new Customer(2, "Bob");

    private static final Integer EXPECTED = 1;


    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDaoMock;

    @After
    public void clean() {
        verify(customerDaoMock);
    }

    @Before
    public void setUp() {
        reset(customerDaoMock);
    }

    @Test
    public void add() throws Exception {
        expect(customerDaoMock.add(CUSTOMER_1)).andReturn(CUSTOMER_1.getCustomerId());
        replay(customerDaoMock);

        Integer customerId = customerService.add(CUSTOMER_1);

        assertNotNull("Customer id must be not null", customerId);
        assertEquals("Customer id", CUSTOMER_1.getCustomerId(), customerId);
    }

    @Test
    public void delete() throws Exception {
        expect(customerDaoMock.delete(CUSTOMER_1.getCustomerId())).andReturn(EXPECTED);
        replay(customerDaoMock);

        Integer quantityDeleted = customerService.delete(CUSTOMER_1.getCustomerId());

        assertNotNull("Quantity deleted must be not null", quantityDeleted);
        assertEquals("Quantity deleted", EXPECTED, quantityDeleted);
    }

    @Test
    public void update() throws Exception {
        expect(customerDaoMock.update(CUSTOMER_1)).andReturn(EXPECTED);
        replay(customerDaoMock);

        Integer quantity = customerService.update(CUSTOMER_1);

        assertNotNull("Quantity must be not null", quantity);
        assertEquals("Quantity updated", EXPECTED, quantity);
    }

    @Test
    public void getByName() throws Exception {
        List<Customer> expectCustomers = new ArrayList<Customer>();
        expectCustomers.add(CUSTOMER_1);

        expect(customerDaoMock.getByName(CUSTOMER_1.getName())).andReturn(expectCustomers);
        replay(customerDaoMock);

        List<Customer> customers = customerService.getByName(CUSTOMER_1.getName());

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() == expectCustomers.size());
    }

    @Test
    public void getById() throws Exception {
        expect(customerDaoMock.getById(CUSTOMER_1.getCustomerId())).andReturn(CUSTOMER_1);
        replay(customerDaoMock);

        Customer customer = customerService.getById(CUSTOMER_1.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("Customer", CUSTOMER_1, customer);
    }

    @Test
    public void getAll() throws Exception {
        List<Customer> expectCustomers = new ArrayList<Customer>();
        expectCustomers.add(CUSTOMER_1);
        expectCustomers.add(CUSTOMER_2);

        expect(customerDaoMock.getAll()).andReturn(expectCustomers);
        replay(customerDaoMock);

        List<Customer> customers = customerService.getAll();

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() == expectCustomers.size());
    }

}