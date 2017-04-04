package com.cinema.client;

import com.cinema.client.configuration.SpringClientRestConfiguration;
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
@ContextConfiguration(classes = SpringClientRestConfiguration.class)
@PropertySource("classpath:url.properties")
public class CustomerClientImplTest {

    private static final Customer CUSTOMER_1 = new Customer(1,"Sergey Bloshuk");

    private static final Customer CUSTOMER_2 = new Customer(2, "Bob");

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
    public void add() throws Exception {
        LOGGER.debug("mock test: add()");

        String httpRequest = new StringBuffer().append(url).append(urlCustomer).append("/add").toString();

        expect(mockRestTemplate.postForEntity(httpRequest, CUSTOMER_1, Integer.class))
                .andReturn(new ResponseEntity<Integer>(CUSTOMER_1.getCustomerId(), HttpStatus.CREATED));
        replay(mockRestTemplate);

        Integer customerId = customerClient.add(CUSTOMER_1);

        assertNotNull("CustomerId must be not null", customerId);
        assertEquals("Customer", CUSTOMER_1.getCustomerId(), customerId);
    }

    @Test
    public void delete() throws Exception {
        LOGGER.debug("mock test: delete()");

        String httpRequest = new StringBuffer().append(url).append(urlCustomer).append("/delete?id={id}").toString();

        expect(mockRestTemplate.exchange(httpRequest, HttpMethod.DELETE,null, Integer.class, CUSTOMER_1.getCustomerId()))
                .andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.ACCEPTED));
        replay(mockRestTemplate);

        Integer quantity = customerClient.delete(CUSTOMER_1.getCustomerId());

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

    @Test
    public void update() throws Exception {
        LOGGER.debug("mock test: update()");

        String httpRequest = new StringBuffer().append(url).append(urlCustomer).append("/update").toString();

        HttpEntity<Customer> entity = new HttpEntity<>(CUSTOMER_1);
        expect(mockRestTemplate.exchange(httpRequest, HttpMethod.PUT, entity, Integer.class))
                .andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.ACCEPTED));
        replay(mockRestTemplate);

        Integer quantity = customerClient.update(CUSTOMER_1);

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

    @Test
    public void getByName() throws Exception {
        LOGGER.debug("mock test: getByName()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER_1);

        String httpRequest = new StringBuffer().append(url).append(urlCustomer).append("/getByName?name={name}").toString();

        expect(mockRestTemplate.getForEntity(httpRequest, List.class, CUSTOMER_1.getName()))
                .andReturn(new ResponseEntity<List>(customers, HttpStatus.FOUND));
        replay(mockRestTemplate);

        List<Customer> result = customerClient.getByName(CUSTOMER_1.getName());

        assertNotNull("Customer must be not null", result);
        assertEquals("Lists size", customers.size(), result.size());
    }

    @Test
    public void getById() throws Exception {
        LOGGER.debug("mock test: getById()");

        String httpRequest = new StringBuffer().append(url).append(urlCustomer).append("/getById?id={id}").toString();

        expect(mockRestTemplate.getForEntity(httpRequest, Customer.class, CUSTOMER_1.getCustomerId()))
                .andReturn(new ResponseEntity<Customer>(CUSTOMER_1, HttpStatus.FOUND));
        replay(mockRestTemplate);

        Customer customer = customerClient.getById(CUSTOMER_1.getCustomerId());

        assertNotNull("Customer must be not null", customer);
        assertEquals("Customer", CUSTOMER_1, customer);
    }

    @Test
    public void getAll() throws Exception {
        LOGGER.debug("mock test: getAll()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER_1);
        customers.add(CUSTOMER_2);

        String httpRequest = new StringBuffer().append(url).append(urlCustomer).append("/getAll").toString();

        expect(mockRestTemplate.getForEntity(url + urlCustomer + "/getAll", List.class))
                .andReturn(new ResponseEntity<List>(customers, HttpStatus.OK));
        replay(mockRestTemplate);

        List<Customer> result = customerClient.getAll();

        assertNotNull("Result must be not null", result);
        assertEquals("Lists size", customers.size(), result.size());
    }

}