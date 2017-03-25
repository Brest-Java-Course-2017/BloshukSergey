package com.cinema.client;

import com.cinema.client.configuration.SpringClientRestConfiguration;
import com.cinema.model.Session;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringClientRestConfiguration.class)
@PropertySource("classpath:url.properties")
public class SessionClientImplTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static Session SESSION_1;

    private static Session SESSION_2;

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    private static final Integer EXPECTED = 1;

    private static final Logger LOGGER = LogManager.getLogger(SessionClientImplTest.class);

    @Value("${rest.protocol}://${rest.host}:${rest.port}")
    private String url;

    @Value("${rest.session}")
    private String urlSession;

    @Autowired
    private SessionClient sessionClient;

    @Autowired
    private RestTemplate mockRestTemplate;

    @After
    public void clean() {
        verify(mockRestTemplate);
    }

    @Before
    public void setUp() throws Exception {
        SESSION_1 = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 20);

        SESSION_2 = new Session(2, "Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4"), 20);

        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-1");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-22");

        reset(mockRestTemplate);
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("mock test: add()");

        expect(mockRestTemplate.postForEntity(url + urlSession + "/add", SESSION_1, Integer.class))
                .andReturn(new ResponseEntity<Integer>(SESSION_1.getSessionId(), HttpStatus.CREATED));
        replay(mockRestTemplate);

        Integer sessionId = sessionClient.add(SESSION_1);

        assertNotNull("CustomerId must be not null", sessionId);
        assertEquals("Customer", SESSION_1.getSessionId(), sessionId);
    }

    @Test
    public void delete() throws Exception {
        LOGGER.debug("mock test: deleteSession()");

        expect(mockRestTemplate.exchange(url + urlSession + "/delete?id={id}",
                HttpMethod.DELETE, null, Integer.class, SESSION_1.getSessionId()))
                .andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.ACCEPTED));
        replay(mockRestTemplate);

        Integer quantity = sessionClient.delete(SESSION_1.getSessionId());

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

    @Test
    public void update() throws Exception {
        LOGGER.debug("mock test: update()");

        HttpEntity<Session> entity = new HttpEntity<>(SESSION_1);
        expect(mockRestTemplate.exchange(url + urlSession + "/update",
                HttpMethod.PUT,
                entity,
                Integer.class))
                .andReturn(new ResponseEntity<Integer>(EXPECTED, HttpStatus.ACCEPTED));
        replay(mockRestTemplate);

        Integer quantity = sessionClient.update(SESSION_1);

        assertNotNull("quantity must be not null", quantity);
        assertEquals("quantity", EXPECTED, quantity);
    }

    @Test
    public void getById() throws Exception {
        LOGGER.debug("mock test: getById()");

        String tempurl = url + urlSession + "/getById?id=" + SESSION_1.getSessionId();
        expect(mockRestTemplate.getForEntity(tempurl, Session.class))
                .andReturn(new ResponseEntity<Session>(SESSION_1, HttpStatus.FOUND));
        replay(mockRestTemplate);

        Session session = sessionClient.getById(SESSION_1.getSessionId());

        assertNotNull("Session must be not null", session);
        assertEquals("Session", SESSION_1, session);
    }

    @Test
    public void getAll() throws Exception {
        LOGGER.debug("mock test: getAll()");

        List<Session> sessions = new ArrayList<>();
        sessions.add(SESSION_1);
        sessions.add(SESSION_2);

        expect(mockRestTemplate.getForEntity(url + urlSession + "/getAll", List.class))
                .andReturn(new ResponseEntity<List>(sessions, HttpStatus.OK));
        replay(mockRestTemplate);

        List<Session> result = sessionClient.getAll();

        assertNotNull("Result must be not null", result);
        assertEquals("Lists size", sessions.size(), result.size());
    }

}