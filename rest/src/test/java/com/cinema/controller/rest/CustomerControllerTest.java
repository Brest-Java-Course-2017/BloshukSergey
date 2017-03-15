package com.cinema.controller.rest;

import com.cinema.model.Customer;
import com.cinema.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-rest.xml")
public class CustomerControllerTest {

    private static final Customer CUSTOMER_1 = new Customer(1, 2, "Sergey", "Bloshuk", 2);

    private static final Customer CUSTOMER_2 = new Customer(2, 1, "Bob", "Bob", 1);

    private static final Integer EXPECTED = 1;

    private static final String CUSTOMER_GET_ALL = "/customer/getAll";
    private static final String CUSTOMER_GET_BY_ID_ID_1 = "/customer/getById?id=1";
    private static final String CUSTOMER_ADD = "/customer/add";
    private static final String CUSTOMER_UPDATE = "/customer/update";
    private static final String CUSTOMER_DELETE_ID_1 = "/customer/delete?id=1";
    private static final String CUSTOMER_GET_ALL_BY_SESSION_ID = "/customer/getAllBySessionId?id=2";

    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerServiceMock;

    @Autowired
    private CustomerController customerController;

    private static final Logger LOGGER = LogManager.getLogger(CustomerControllerTest.class);

    @After
    public void clean() {
        verify(customerServiceMock);
    }

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(customerController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        reset(customerServiceMock);
    }

    @Test
    public void getAll() throws Exception {
        LOGGER.debug("mock test: getAll()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER_1);
        customers.add(CUSTOMER_2);

        expect(customerServiceMock.getAllCustomers()).andReturn(customers);
        replay(customerServiceMock);

        mockMvc.perform(get(CUSTOMER_GET_ALL).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void getAllBySessionId() throws Exception {
        LOGGER.debug("mock test: getAllBySessionId()");

        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER_1);

        expect(customerServiceMock.getCustomersBySessionId(CUSTOMER_1.getSessionId())).andReturn(customers);
        replay(customerServiceMock);

        mockMvc.perform(get(CUSTOMER_GET_ALL_BY_SESSION_ID).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @Test
    public void getById() throws Exception {
        LOGGER.debug("mock test: getById()");

        expect(customerServiceMock.getCustomerById(CUSTOMER_1.getCustomerId())).andReturn(CUSTOMER_1);
        replay(customerServiceMock);

        mockMvc.perform(get(CUSTOMER_GET_BY_ID_ID_1).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("mock test: add()");

        expect(customerServiceMock.addCustomer(CUSTOMER_1)).andReturn(CUSTOMER_1.getCustomerId());
        replay(customerServiceMock);

        String customer = new ObjectMapper().writeValueAsString(CUSTOMER_1);

        mockMvc.perform(
                post(CUSTOMER_ADD).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customer)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(CUSTOMER_1.getCustomerId().toString()));
    }

    @Test
    public void update() throws Exception {
        LOGGER.debug("mock test: update()");

        expect(customerServiceMock.updateCustomer(CUSTOMER_1)).andReturn(EXPECTED);
        replay(customerServiceMock);

        String customer = new ObjectMapper().writeValueAsString(CUSTOMER_1);

        mockMvc.perform(
                put(CUSTOMER_UPDATE).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customer)
        ).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(EXPECTED.toString()));
    }

    @Test
    public void deleteCustomer() throws Exception {
        LOGGER.debug("mock test: delete()");

        expect(customerServiceMock.deleteCustomer(CUSTOMER_1.getCustomerId())).andReturn(EXPECTED);
        replay(customerServiceMock);

        mockMvc.perform(
                delete(CUSTOMER_DELETE_ID_1)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED.toString()));
    }

}