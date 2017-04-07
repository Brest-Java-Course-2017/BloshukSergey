package com.cinema.controller.rest;

import com.cinema.configuration.SpringRestMockTestConfiguration;
import com.cinema.model.Session;
import com.cinema.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRestMockTestConfiguration.class)
public class SessionControllerMockTest {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static Session SESSION_1;

    private static Session SESSION_2;

    private static final Integer EXPECTED = 1;

    private static final String SESSION_GET_BY_ID = "/session/getById?id=1";

    private static final String SESSION_GET_ALL = "/session/getAll";

    private static final String SESSION_ADD = "/session/add";

    private static final String SESSION_UPDATE = "/session/update";

    private static final String SESSION_DELETE_ID_1 = "/session/delete?id=1";

    private MockMvc mockMvc;

    @Autowired
    private SessionService sessionServiceMock;

    @Autowired
    private SessionController sessionController;

    @After
    public void clean() {
        verify(sessionServiceMock);
    }

    @Before
    public void setUp() throws Exception {

        SESSION_1 = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 20);

        SESSION_2 = new Session(2, "Lego movie", SIMPLE_DATE_FORMAT.parse("2017-6-4"), 20);

        mockMvc = standaloneSetup(sessionController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        reset(sessionServiceMock);
    }


    @Test
    public void getAll() throws Exception {
        List<Session> sessions = new ArrayList<>();
        sessions.add(SESSION_1);
        sessions.add(SESSION_2);

        expect(sessionServiceMock.getAll()).andReturn(sessions);
        replay(sessionServiceMock);

        mockMvc.perform(get(SESSION_GET_ALL).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void add() throws Exception {
        expect(sessionServiceMock.add(SESSION_1)).andReturn(SESSION_1.getSessionId());
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
        expect(sessionServiceMock.update(SESSION_1)).andReturn(EXPECTED);
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
        expect(sessionServiceMock.delete(SESSION_1.getSessionId())).andReturn(EXPECTED);
        replay(sessionServiceMock);

        mockMvc.perform(
                delete(SESSION_DELETE_ID_1)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED.toString()));
    }

    @Test
    public void getById() throws Exception {
        expect(sessionServiceMock.getById(SESSION_1.getSessionId())).andReturn(SESSION_1);
        replay(sessionServiceMock);

        mockMvc.perform(get(SESSION_GET_BY_ID).accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

}