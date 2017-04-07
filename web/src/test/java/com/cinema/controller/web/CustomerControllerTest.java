package com.cinema.controller.web;

import com.cinema.client.CustomerClient;
import com.cinema.configuration.SpringWebMockTestConfiguration;
import com.cinema.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringWebMockTestConfiguration.class)
public class CustomerControllerTest {

    private static final Customer CUSTOMER = new Customer(1, "Sergey Bloshuk");

    private static final Integer EXPECTED = 1;

    private static final Integer SESSION_ID = 1;

    public static final String REDIRECT_CUSTOMER = "redirect:/customer";
    public static final String CUSTOMER_VIEW_PAGE = "customerViewPage";
    public static final String SEARCH_CUSTOMERS = "searchCustomers";
    public static final String CUSTOMERS = "customers";

    private MockMvc mockMvc;

    @Autowired
    private CustomerClient customerClientMock;

    @Autowired
    private CustomerController customerController;

    @After
    public void clean() {
        verify(customerClientMock);
    }

    @Before
    public void setUp() throws Exception {

        mockMvc = standaloneSetup(customerController).build();
        reset(customerClientMock);
    }

    @Test
    public void getCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER);

        expect(customerClientMock.getAll()).andReturn(customers);
        replay(customerClientMock);

        mockMvc.perform(get("/customer/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name(CUSTOMERS))
                .andExpect(model().attribute("customerList", hasSize(EXPECTED)));
    }

    @Test
    public void searchCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(CUSTOMER);

        expect(customerClientMock.getByName(CUSTOMER.getName() + "%")).andReturn(customers);
        replay(customerClientMock);

        mockMvc.perform(get("/customer/search")
                    .param("name", CUSTOMER.getName())
                    .param("sessionId", SESSION_ID.toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name(SEARCH_CUSTOMERS))
                .andExpect(model().attribute("customerList", hasSize(EXPECTED)));
    }

    @Test
    public void customerView() throws Exception {
        expect(customerClientMock.getById(CUSTOMER.getCustomerId())).andReturn(CUSTOMER);
        replay(customerClientMock);

        mockMvc.perform(get("/customer/customerView").param("id", CUSTOMER.getCustomerId().toString()))
                .andDo(print())
                .andExpect(view().name(CUSTOMER_VIEW_PAGE))
                .andExpect(model().attribute("item", is(CUSTOMER)));
    }

    @Test
    public void deleteCustomer() throws Exception {
        expect(customerClientMock.delete(CUSTOMER.getCustomerId())).andReturn(EXPECTED);
        replay(customerClientMock);

        mockMvc.perform(delete("/customer/delete").param("id", CUSTOMER.getCustomerId().toString()))
                .andDo(print())
                .andExpect(view().name(REDIRECT_CUSTOMER));
    }

    @Test
    public void add() throws Exception {
        expect(customerClientMock.add(CUSTOMER)).andReturn(EXPECTED);
        replay(customerClientMock);

        String customer = new ObjectMapper().writeValueAsString(CUSTOMER);

        mockMvc.perform(post("/customer/add").contentType(MediaType.APPLICATION_JSON).content(customer))
                .andDo(print())
                .andExpect(view().name(REDIRECT_CUSTOMER));
    }

    @Test
    public void update() throws Exception {
        expect(customerClientMock.update(CUSTOMER)).andReturn(EXPECTED);
        replay(customerClientMock);

        String customer = new ObjectMapper().writeValueAsString(CUSTOMER);

        mockMvc.perform(put("/customer/update").contentType(MediaType.APPLICATION_JSON).content(customer))
                .andDo(print())
                .andExpect(view().name(REDIRECT_CUSTOMER));
    }

}