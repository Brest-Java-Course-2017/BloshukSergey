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

    private static final Logger LOGGER = LogManager.getLogger(CustomerClientImpl.class);


    @Override
    public Integer add(Customer customer) throws ServerDataAccessException {
        LOGGER.debug("add({})", customer);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                url + urlCustomer + "/add",
                customer,
                Integer.class);
        Integer customerId = (Integer) responseEntity.getBody();

        return customerId;
    }

    @Override
    public Integer delete(Integer id) throws ServerDataAccessException {
        LOGGER.debug("delete({})", id);

        ResponseEntity<Integer> response= restTemplate.exchange(
                url + urlCustomer + "/delete?id={id}",
                HttpMethod.DELETE,
                null,
                Integer.class,
                id);
        Integer quantity = response.getBody();

        return quantity;
    }

    @Override
    public Integer update(Customer customer) throws ServerDataAccessException {
        LOGGER.debug("update({})", customer);

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
    public List<Customer> getByName(String name) throws ServerDataAccessException {
        LOGGER.debug("getByName({})", name);

        ResponseEntity responseEntity = restTemplate.getForEntity(
                url + urlCustomer + "/getByName?name=" + name,
                List.class);
        List<Customer> customers = (List<Customer>) responseEntity.getBody();

        return customers;
    }

    @Override
    public Customer getById(Integer id) throws ServerDataAccessException {
        LOGGER.debug("getById({})", id);

        ResponseEntity responseEntity = restTemplate.getForEntity(
                url + urlCustomer + "/getById?id=" + id,
                Customer.class);
        Customer customer = (Customer) responseEntity.getBody();

        return customer;
    }

    @Override
    public List<Customer> getAll() throws ServerDataAccessException {
        LOGGER.debug("getAll()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url + urlCustomer + "/getAll", List.class);
        List<Customer> customers = (List<Customer>) responseEntity.getBody();

        return customers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (restTemplate == null) {
            throw new BeanCreationException("restTemplate is null");
        }
    }
}
