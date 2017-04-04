package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Session;
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
public class SessionClientImpl implements SessionClient, InitializingBean {

    @Value("${rest.protocol}://${rest.host}:${rest.port}")
    private String url;

    @Value("${rest.session}")
    private String urlSession;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LogManager.getLogger(SessionClientImpl.class);

    @Override
    public Integer add(Session session) throws ServerDataAccessException {
        LOGGER.debug("add({})", session);

        String httpRequest = new StringBuffer().append(url).append(urlSession).append("/add").toString();
        ResponseEntity responseEntity = restTemplate.postForEntity(httpRequest, session, Integer.class);
        Integer sessionId = (Integer) responseEntity.getBody();

        return sessionId;
    }

    @Override
    public Integer delete(Integer id) throws ServerDataAccessException {
        LOGGER.debug("delete({})", id);

        String httpRequest = new StringBuffer().append(url).append(urlSession).append("/delete?id={id}").toString();
        ResponseEntity<Integer> response= restTemplate.exchange(httpRequest, HttpMethod.DELETE,null, Integer.class, id);
        Integer quantity = response.getBody();

        return quantity;
    }

    @Override
    public Integer update(Session session) throws ServerDataAccessException {
        LOGGER.debug("update({})", session);

        String httpRequest = new StringBuffer().append(url).append(urlSession).append("/update").toString();
        HttpEntity<Session> entity = new HttpEntity<>(session);
        ResponseEntity<Integer> response= restTemplate.exchange(httpRequest, HttpMethod.PUT, entity, Integer.class);
        Integer quantity = response.getBody();

        return quantity;
    }

    @Override
    public Session getById(Integer id) throws ServerDataAccessException {
        LOGGER.debug("getById({})", id);

        String httpRequest = new StringBuffer().append(url).append(urlSession).append("/getById?id={id}").toString();
        ResponseEntity responseEntity = restTemplate.getForEntity(httpRequest, Session.class, id);
        Session sessions = (Session) responseEntity.getBody();

        return sessions;
    }

    @Override
    public List<Session> getAll() throws ServerDataAccessException {
        LOGGER.debug("getAll()");

        String httpRequest = new StringBuffer().append(url).append(urlSession).append("/getAll").toString();
        ResponseEntity responseEntity = restTemplate.getForEntity(httpRequest, List.class);
        List<Session> sessions = (List<Session>) responseEntity.getBody();

        return sessions;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (restTemplate == null) {
            throw new BeanCreationException("restTemplate is null");
        }
    }
}
