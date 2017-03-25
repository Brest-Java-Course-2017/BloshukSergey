package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:url.properties")
public class BookingClientImpl implements BookingClient, InitializingBean {

    @Value("${rest.protocol}://${rest.host}:${rest.port}")
    private String url;

    @Value("${rest.customer}")
    private String urlBooking;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LogManager.getLogger(BookingClientImpl.class);

    @Override
    public List<Customer> getCustomersBySessionId(Integer id) throws ServerDataAccessException {
        LOGGER.debug("getCustomersBySessionId({})", id);

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/getCustomersBySessionId").toString();

        Map data = new HashMap<String, Integer>();
        data.put("id", id);

        ResponseEntity responseEntity = restTemplate.getForEntity(httpRequest, List.class, data);
        List<Customer> customers = (List<Customer>) responseEntity.getBody();

        return customers;
    }

    @Override
    public List<SessionWithSeats> getSessionsWithSeats(Date firstDate, Date secondDate) throws ServerDataAccessException {
        LOGGER.debug("getSessionsWithSeats({}, {})", firstDate, secondDate);

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/getSessionsWithSeats").toString();

        Map data = new HashMap<String, Date>();
        data.put("firstDate", firstDate);
        data.put("secondDate", secondDate);

        ResponseEntity responseEntity = restTemplate.getForEntity(httpRequest, List.class, data);
        List<SessionWithSeats> sessions = (List<SessionWithSeats>) responseEntity.getBody();

        return sessions;
    }

    @Override
    public Integer delete(Integer sessionId, Integer customerId) throws ServerDataAccessException {
        LOGGER.debug("delete({}, {})", sessionId, customerId);

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/delete").toString();

        Map data = new HashMap<String, Integer>();
        data.put("sessionId", sessionId);
        data.put("customerId", customerId);

        ResponseEntity<Integer> response = restTemplate.exchange(httpRequest, HttpMethod.DELETE, null, Integer.class, data);
        Integer quantity = response.getBody();

        return quantity;
    }

    @Override
    public Integer add(Integer sessionId, Integer customerId) throws ServerDataAccessException {
        LOGGER.debug("addCustomer({}, {})", sessionId, customerId);

        String httpRequest = new StringBuffer()
                .append(url)
                .append(urlBooking)
                .append("/add").toString();

        Map data = new HashMap<String, Integer>();
        data.put("sessionId", sessionId);
        data.put("customerId", customerId);

        ResponseEntity responseEntity = restTemplate.postForEntity(httpRequest, HttpMethod.POST, Integer.class, data);
        Integer id = (Integer) responseEntity.getBody();

        return id;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (restTemplate == null) {
            throw new BeanCreationException("restTemplate is null");
        }
    }
}
