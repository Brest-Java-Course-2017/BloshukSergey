package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
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

import java.time.LocalDate;
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

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Session> getAllSessions() throws ServerDataAccessException {
        LOGGER.debug("getAllSessions()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url + urlSession + "/getAll", List.class);
        List<Session> sessions = (List<Session>) responseEntity.getBody();

        return sessions;
    }

    @Override
    public List<SessionWithQuantityTickets> getAllSessionsWithQuantityTickets() throws ServerDataAccessException {
        LOGGER.debug("getAllSessionsWithQuantityTickets()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url + urlSession + "/getAllWithTickets", List.class);
        List<SessionWithQuantityTickets> sessions = (List<SessionWithQuantityTickets>) responseEntity.getBody();

        return sessions;
    }

    @Override
    public List<SessionWithQuantityTickets> getAllSessionsWithQuantityTicketsDateToDate(LocalDate firstDate, LocalDate secondDate) throws ServerDataAccessException {
        LOGGER.debug("getAllSessionsWithQuantityTicketsDateToDate()");

        ResponseEntity responseEntity = restTemplate.getForEntity(
                url + urlSession + "/getAllWithTicketsDateToDate?firstDate=" + firstDate + "&secondDate=" + secondDate,
                List.class);
        List<SessionWithQuantityTickets> sessions = (List<SessionWithQuantityTickets>) responseEntity.getBody();

        return sessions;
    }

    @Override
    public Session getSessionById(Integer sessionId) throws ServerDataAccessException {
        LOGGER.debug("getSessionById({})", sessionId);

        ResponseEntity responseEntity = restTemplate.getForEntity(url + urlSession + "/getById?id=" + sessionId, Session.class);
        Session sessions = (Session) responseEntity.getBody();

        return sessions;
    }

    @Override
    public Integer addSession(Session session) throws ServerDataAccessException {
        LOGGER.debug("addSession({})", session);

        ResponseEntity responseEntity = restTemplate.postForEntity(
                url + urlSession + "/add",
                session,
                Integer.class);
        Integer sessionId = (Integer) responseEntity.getBody();

        return sessionId;
    }

    @Override
    public Integer updateSession(Session session) throws ServerDataAccessException {
        LOGGER.debug("updateSession({})", session);

        HttpEntity<Session> entity = new HttpEntity<>(session);
        ResponseEntity<Integer> response= restTemplate.exchange(
                url + urlSession + "/update",
                HttpMethod.PUT,
                entity,
                Integer.class);
        Integer quantity = response.getBody();

        return quantity;
    }

    @Override
    public Integer deleteSession(Integer sessionId) throws ServerDataAccessException {
        LOGGER.debug("deleteSession({})", sessionId);

        ResponseEntity<Integer> response= restTemplate.exchange(
                url + urlSession + "/delete?id={id}",
                HttpMethod.DELETE,
                null,
                Integer.class,
                sessionId);
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
