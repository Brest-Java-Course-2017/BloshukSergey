package com.cinema.controller.rest;

import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import com.cinema.service.SessionService;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-rest.xml")
public class SessionControllerTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    
    private static Session SESSION_1;

    private static Session SESSION_2;

    private static SessionWithQuantityTickets SESSION_WITH_QUANTITY_TICKETS_1;

    private static SessionWithQuantityTickets SESSION_WITH_QUANTITY_TICKETS_2;

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    private static final Integer EXPECTED = 1;

    private static final String SESSION_GET_BY_ID = "/session/getById?id=1";
    private static final String SESSION_GET_ALL = "/session/getAll";
    private static final String SESSION_GET_ALL_WITH_QUANTITY_TICKETS = "/session/getAllWithTickets";
    private static final String SESSION_GET_ALL_WITH_QUANTITY_TICKETS_DATE_TO_DATE = "/session/getAllWithTicketsDateToDate?firstDate=2017-03-01&secondDate=2017-03-22";
    private static final String SESSION_ADD = "/session/add";
    private static final String SESSION_UPDATE = "/session/update";
    private static final String SESSION_DELETE_ID_1 = "/session/delete?id=1";

    private MockMvc mockMvc;

    @Autowired
    private SessionService sessionServiceMock;

    @Autowired
    private SessionController sessionController;

    private static final Logger LOGGER = LogManager.getLogger(SessionControllerTest.class);

    @After
    public void clean() {
        verify(sessionServiceMock);
    }

    @Before
    public void setUp() throws Exception {

        SESSION_1 = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"));

        SESSION_2 = new Session(2, "Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4"));

        SESSION_WITH_QUANTITY_TICKETS_1 =
                new SessionWithQuantityTickets(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3)"), 2);

        SESSION_WITH_QUANTITY_TICKETS_2 =
                new SessionWithQuantityTickets(2, "Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4)"), 10);

        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-1");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-22");

        mockMvc = standaloneSetup(sessionController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        reset(sessionServiceMock);
    }

    @Test
    public void getAll() throws Exception {
        LOGGER.debug("mock test: getAll()");

        List<Session> sessions = new ArrayList<>();
        sessions.add(SESSION_1);
        sessions.add(SESSION_2);

        expect(sessionServiceMock.getAllSessions()).andReturn(sessions);
        replay(sessionServiceMock);

        mockMvc.perform(get(SESSION_GET_ALL).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void getAllWithQuantityTickets() throws Exception {
        LOGGER.debug("mock test: getAllWithTickets()");

        List<SessionWithQuantityTickets> sessions = new ArrayList<>();
        sessions.add(SESSION_WITH_QUANTITY_TICKETS_1);
        sessions.add(SESSION_WITH_QUANTITY_TICKETS_2);

        expect(sessionServiceMock.getAllSessionsWithQuantityTickets()).andReturn(sessions);
        replay(sessionServiceMock);

        mockMvc.perform(get(SESSION_GET_ALL_WITH_QUANTITY_TICKETS).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void getAllWithQuantityTicketsDateToDate() throws Exception {
        LOGGER.debug("mock test: getAllWithTicketsDateToDate({}, {})", FIRST_DATE, SECOND_DATE);

        List<SessionWithQuantityTickets> sessions = new ArrayList<>();
        sessions.add(SESSION_WITH_QUANTITY_TICKETS_1);

        expect(sessionServiceMock.getAllSessionsWithQuantityTicketsDateToDate(FIRST_DATE, SECOND_DATE)).andReturn(sessions);
        replay(sessionServiceMock);

        mockMvc.perform(get(SESSION_GET_ALL_WITH_QUANTITY_TICKETS_DATE_TO_DATE).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void getById() throws Exception {
        LOGGER.debug("mock test: getById()");

        expect(sessionServiceMock.getSessionById(SESSION_1.getSessionId())).andReturn(SESSION_1);
        replay(sessionServiceMock);

        mockMvc.perform(get(SESSION_GET_BY_ID).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void add() throws Exception {
        LOGGER.debug("mock test: add()");

        expect(sessionServiceMock.addSession(SESSION_1)).andReturn(SESSION_1.getSessionId());
        replay(sessionServiceMock);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String session = mapper.writeValueAsString(SESSION_1);

        mockMvc.perform(
                post(SESSION_ADD).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(session))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(SESSION_1.getSessionId().toString()));
    }

    @Test
    public void update() throws Exception {
        LOGGER.debug("mock test: update()");

        expect(sessionServiceMock.updateSession(SESSION_1)).andReturn(EXPECTED);
        replay(sessionServiceMock);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String session = mapper.writeValueAsString(SESSION_1);

        mockMvc.perform(
                put(SESSION_UPDATE).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(session)
        ).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(EXPECTED.toString()));
    }

    @Test
    public void deleteSession() throws Exception {
        LOGGER.debug("mock test: delete()");

        expect(sessionServiceMock.deleteSession(SESSION_1.getSessionId())).andReturn(EXPECTED);
        replay(sessionServiceMock);

        mockMvc.perform(
                delete(SESSION_DELETE_ID_1)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED.toString()));
    }

}