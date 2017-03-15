package com.cinema.controller.rest;

import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import com.cinema.service.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/session")
public class SessionController implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @ResponseBody
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<Session> getAll(){
        LOGGER.debug("rest: getAll()");

        List<Session> sessions = sessionService.getAllSessions();

        return sessions;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllWithTickets",method = RequestMethod.GET)
    public List<SessionWithQuantityTickets> getAllWithTickets() {
        LOGGER.debug("rest: getAllWithTickets()");

        List<SessionWithQuantityTickets> sessions = sessionService.getAllSessionsWithQuantityTickets();

        return sessions;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllWithTicketsDateToDate",method = RequestMethod.GET)
    public List<SessionWithQuantityTickets> getAllWithTicketsDateToDate(@RequestParam("firstDate")
                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                        Date firstDate,
                                                                        @RequestParam("secondDate")
                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                        Date secondDate) {
        LOGGER.debug("rest: getAllWithTicketsDateToDate({}, {})", firstDate, secondDate);

        List<SessionWithQuantityTickets> sessions =
                sessionService.getAllSessionsWithQuantityTicketsDateToDate(firstDate, secondDate);

        return sessions;
    }

    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Session getById(@RequestParam("id") Integer sessionId) {
        LOGGER.debug("rest: getById({})", sessionId);

        Session session = sessionService.getSessionById(sessionId);

        return session;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestBody Session session) {
        LOGGER.debug("rest: add({})", session);

        Integer sessionId = sessionService.addSession(session);

        return sessionId;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Integer update(@RequestBody Session session) {
        LOGGER.debug("rest: update({})", session);

        Integer quantity = sessionService.updateSession(session);

        return quantity;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Integer delete(@RequestParam("id") Integer sessionId) {
        LOGGER.debug("rest: delete({})", sessionId);

        Integer quantity = sessionService.deleteSession(sessionId);

        return quantity;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sessionService == null) {
            throw new BeanCreationException("sessionService is null");
        }

        LOGGER.debug("rest: afterPropertiesSet is OK");
    }
}
