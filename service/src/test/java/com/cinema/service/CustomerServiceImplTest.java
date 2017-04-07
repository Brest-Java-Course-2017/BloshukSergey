package com.cinema.service;

import com.cinema.configuration.SpringServiceTestConfiguration;
import com.cinema.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringServiceTestConfiguration.class)
@Transactional
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    private static final Customer EXIST_CUSTOMER = new Customer(1, "Sergey Bloshuk");

    private static final Customer ADD_CUSTOMER = new Customer(10, "Bob");

    private static final Customer UPDATE_CUSTOMER = new Customer(1, "Bob");

    private static final Integer EXPECTED = 1;


    @Test
    public void add() throws Exception {
        Integer customerId = customerService.add(ADD_CUSTOMER);

        assertNotNull("Customer id must be not null", customerId);

        Customer customer = customerService.getById(customerId);

        assertNotNull("Customer must be not null", customer);
        assertEquals("Name", ADD_CUSTOMER.getName(), customer.getName());
    }

    @Test
    public void delete() throws Exception {
        Integer quantityDeleted = customerService.delete(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Quantity of delete customer must be not null", quantityDeleted);
        assertEquals("Quantity of delete customer", EXPECTED, quantityDeleted);
    }

    @Test
    public void update() throws Exception {
        Integer quantity = customerService.update(UPDATE_CUSTOMER);

        assertNotNull(quantity);
        assertEquals("Quantity changed records", EXPECTED, quantity);
    }

    @Test
    public void getByName() throws Exception {
        List<Customer> customers = customerService.getByName(EXIST_CUSTOMER.getName());

        assertNotNull("Customer must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() > 0);
    }

    @Test
    public void getById() throws Exception {
        Customer customer = customerService.getById(EXIST_CUSTOMER.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("Name", EXIST_CUSTOMER.getName(), customer.getName());
    }

    @Test
    public void getAll() throws Exception {
        List<Customer> customers = customerService.getAll();

        assertNotNull("Customers must be not null", customers);
        assertTrue("Customers must be greater than zero", customers.size() > 0);
    }

}