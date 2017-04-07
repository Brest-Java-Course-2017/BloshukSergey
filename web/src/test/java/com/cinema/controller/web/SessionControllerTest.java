package com.cinema.controller.web;

import com.cinema.client.SessionClient;
import com.cinema.configuration.SpringWebMockTestConfiguration;
import com.cinema.model.Session;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringWebMockTestConfiguration.class)
public class SessionControllerTest {

    private static final String SESSION_ADD = "/session/add";
    private static final String SESSION_UPDATE = "/session/update";
    private static final String SESSION_SESSION_VIEW = "/session/sessionView";
    private static final String SESSION_DELETE = "/session/delete";
    private static final String REDIRECT = "redirect:";
    private static final String BOOKING_URL = "/booking";
    private static final String SESSION_VIEW_PAGE = "sessionViewPage";
    private static final String REDIRECT_SESSION = "redirect:/session";

    private static Date FIRST_DATE;

    private static Date SECOND_DATE;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static Session SESSION;

    private static final Integer EXPECTED = 1;

    private MockMvc mockMvc;

    @Autowired
    private SessionClient sessionClientMock;

    @Autowired
    private SessionController sessionController;

    @After
    public void clean() {
        verify(sessionClientMock);
    }

    @Before
    public void setUp() throws Exception {
        FIRST_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-1");

        SECOND_DATE = SIMPLE_DATE_FORMAT.parse("2017-3-22");

        SESSION = new Session(1, "Logan", SIMPLE_DATE_FORMAT.parse("2017-3-3"), 20);

        mockMvc = standaloneSetup(sessionController).build();
        reset(sessionClientMock);
    }

    @Test
    public void getAll() throws Exception {
        replay(sessionClientMock);

        StringBuffer httpRequest = new StringBuffer().append(REDIRECT).append(BOOKING_URL).append("/getSessionsWithSeats");

        mockMvc.perform(get("/session/"))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(view().name(httpRequest.toString()));
    }

    @Test
    public void getAllDateToDate() throws Exception {
        replay(sessionClientMock);

        StringBuffer httpRequest = new StringBuffer().append(REDIRECT).append(BOOKING_URL).append("/getSessionsWithSeats");
        if(FIRST_DATE != null && SECOND_DATE != null) {
            httpRequest.append("?firstDate=").append(SIMPLE_DATE_FORMAT.format(FIRST_DATE))
                       .append("&secondDate=").append(SIMPLE_DATE_FORMAT.format(SECOND_DATE)).toString();
        }

        StringBuffer url = new StringBuffer()
                .append("/session?firstDate=").append(SIMPLE_DATE_FORMAT.format(FIRST_DATE))
                .append("&secondDate=").append(SIMPLE_DATE_FORMAT.format(SECOND_DATE));

        mockMvc.perform(get(url.toString()))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(view().name(httpRequest.toString()));
    }

    @Test
    public void deleteSession() throws Exception {
        expect(sessionClientMock.delete(SESSION.getSessionId())).andReturn(EXPECTED);
        replay(sessionClientMock);

        mockMvc.perform(delete(SESSION_DELETE).param("id", SESSION.getSessionId().toString()))
                .andDo(print())
                .andExpect(view().name(REDIRECT_SESSION));
    }

    @Test
    public void sessionView() throws Exception {
        expect(sessionClientMock.getById(SESSION.getSessionId())).andReturn(SESSION);
        replay(sessionClientMock);

        mockMvc.perform(get(SESSION_SESSION_VIEW).param("id", SESSION.getSessionId().toString()))
                .andDo(print())
                .andExpect(view().name(SESSION_VIEW_PAGE))
                .andExpect(model().attribute("item", is(SESSION)));
    }

    @Test
    public void updateSession() throws Exception {
        expect(sessionClientMock.update(SESSION)).andReturn(EXPECTED);
        replay(sessionClientMock);

        String customer = new ObjectMapper().writeValueAsString(SESSION);

        mockMvc.perform(put(SESSION_UPDATE).contentType(MediaType.APPLICATION_JSON).content(customer))
                .andDo(print())
                .andExpect(view().name(REDIRECT_SESSION));
    }

    @Test
    public void addSession() throws Exception {
        expect(sessionClientMock.add(SESSION)).andReturn(EXPECTED);
        replay(sessionClientMock);

        String customer = new ObjectMapper().writeValueAsString(SESSION);

        mockMvc.perform(post(SESSION_ADD).contentType(MediaType.APPLICATION_JSON).content(customer))
                .andDo(print())
                .andExpect(view().name(REDIRECT_SESSION));
    }

}