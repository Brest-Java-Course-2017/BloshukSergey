package com.cinema.client;

import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-client-rest.xml"})
@PropertySource("classpath:url.properties")
public class CustomerClientImplTest {

    private static final Customer CUSTOMER_1 = new Customer(1, 2, "Sergey", "Bloshuk", 2);

    private static final Customer CUSTOMER_2 = new Customer(2, 1, "Bob", "Bob", 1);

    private static final Integer EXPECTED = 1;

    private static final Logger LOGGER = LogManager.getLogger(CustomerClientImplTest.class);

    @Value("${rest.protocol}://${rest.host}:${rest.port}")
    private String url;

    @Value("${rest.customer}")
    private String urlCustomer;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private RestTemplate mockRestTemplate;

    @After
    public void clean() {
        verify(mockRestTemplate);
    }

    @Before
    public void setUp() {
        reset(mockRestTemplate);
    }

    @Test
    public void getAllCustomers() throws Exception {
        LOGGER.debug("mock test: getAllCustomers()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER_1);
        customers.add(CUSTOMER_2);

        expect(mockRestTemplate.getForEntity(url + urlCustomer + "/getAll", List.class))
                .andReturn(new ResponseEntity<List>(customers, HttpStatus.OK));
        replay(mockRestTemplate);

        List<Customer> result = customerClient.getAllCustomers();

        assertNotNull("Result must be not null", result);
        assertEquals("Lists size", customers.size(), result.size());
    }

    @Test
    public void getCustomerById() throws Exception {
        LOGGER.debug("mock test: getCustomerById()");

        expect(mockRestTemplate.getForEntity(url + urlCustomer + "/getById?id=" + CUSTOMER_1.getCustomerId(), Customer.class))
                .andReturn(new ResponseEntity<Customer>(CUSTOMER_1, HttpStatus.FOUND));
        replay(mockRestTemplate);

        Customer customer = customerClient.getCustomerById(CUSTOMER_1.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("Customer", CUSTOMER_1, customer);
    }

    @Test
    public void addCustomer() throws Exception {
        LOGGER.debug("mock test: addCustomer()");

        expect(mockRestTemplate.postForEntity(url + urlCustomer + "/add", CUSTOMER_1, Integer.class))
                .andReturn(new ResponseEntity<Integer>(CUSTOMER_1.getCustomerId(), HttpStatus.CREATED));
        replay(mockRestTemplate);

        Integer customerId = customerClient.addCustomer(CUSTOMER_1);

        assertNotNull("CustomerId must be not null", customerId);
        assertEquals("Customer", CUSTOMER_1.getCustomerId(), customerId);
    }

    @Test
    public void updateCustomer() throws Exception {
        LOGGER.debug("mock test: updateCustomer()");

        HttpEntity<Customer> entity = new HttpEntity<>(CUSTOMER_1);
        expect(mockRestTemplate.exchange(url + urlCustomer + "/update",
                HttpMethod.PUT, entity, Integer.class))
                .andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.ACCEPTED));
        replay(mockRestTemplate);

        Integer quantity = customerClient.updateCustomer(CUSTOMER_1);

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

    @Test
    public void deleteCustomer() throws Exception {
        LOGGER.debug("mock test: deleteCustomer()");

        expect(mockRestTemplate.exchange(
                    url + urlCustomer + "/delete?id={id}",
                    HttpMethod.DELETE,
                    null,
                    Integer.class,
                    CUSTOMER_1.getCustomerId()
                )
        ).andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.ACCEPTED));
        replay(mockRestTemplate);

        Integer quantity = customerClient.deleteCustomer(CUSTOMER_1.getCustomerId());

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

}
