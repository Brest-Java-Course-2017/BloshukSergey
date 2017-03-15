package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@PropertySource("classpath:url.properties")
public class CustomerClientImpl implements CustomerClient, InitializingBean {

    @Value("${rest.protocol}://${rest.host}:${rest.port}")
    private String url;

    @Value("${rest.customer}")
    private String urlCustomer;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Customer> getAllCustomers() throws ServerDataAccessException {
        LOGGER.debug("getAllCustomers()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url + urlCustomer + "/getAll", List.class);
        List<Customer> customers = (List<Customer>) responseEntity.getBody();

        return customers;
    }

    @Override
    public List<Customer> getCustomersBySessionId(Integer sessionId) throws ServerDataAccessException {
        LOGGER.debug("getCustomersBySessionId()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url + urlCustomer + "/getAllBySessionId?id=" + sessionId, List.class);
        List<Customer> customers = (List<Customer>) responseEntity.getBody();

        return customers;
    }

    @Override
    public Customer getCustomerById(Integer customerId) throws ServerDataAccessException {
        LOGGER.debug("getCustomerById({})", customerId);

        ResponseEntity responseEntity = restTemplate.getForEntity(
                url + urlCustomer + "/getById?id=" + customerId,
                Customer.class);
        Customer customer = (Customer) responseEntity.getBody();

        return customer;
    }

    @Override
    public Integer addCustomer(Customer customer) throws ServerDataAccessException {
        LOGGER.debug("addCustomer({})", customer);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                url + urlCustomer + "/add",
                customer,
                Integer.class);
        Integer customerId = (Integer) responseEntity.getBody();

        return customerId;
    }

    @Override
    public Integer updateCustomer(Customer customer) throws ServerDataAccessException {
        LOGGER.debug("updateCustomer({})", customer);

        HttpEntity<Customer> entity = new HttpEntity<>(customer);
        ResponseEntity<Integer> response= restTemplate.exchange(
                url + urlCustomer + "/update",
                HttpMethod.PUT,
                entity,
                Integer.class);
        Integer quantity = response.getBody();

        return quantity;
    }

    @Override
    public Integer deleteCustomer(Integer customerId) throws ServerDataAccessException {
        LOGGER.debug("deleteCustomer({})", customerId);

        ResponseEntity<Integer> response= restTemplate.exchange(
                url + urlCustomer + "/delete?id={id}",
                HttpMethod.DELETE,
                null,
                Integer.class,
                customerId);
        Integer quantity = response.getBody();

        return quantity;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (restTemplate == null) {
            throw new BeanCreationException("restTemplate is null");
        }
    }
}
