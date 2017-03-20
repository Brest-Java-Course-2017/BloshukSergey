package com.cinema.controller.rest;

import com.cinema.model.Session;
import com.cinema.service.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/session")
public class SessionController {

    private static final Logger LOGGER = LogManager.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @ResponseBody
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<Session> getAll(){
        LOGGER.debug("rest: getAll()");

        List<Session> sessions = sessionService.getAll();

        return sessions;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestBody Session session) {
        LOGGER.debug("rest: add({})", session);

        Integer sessionId = sessionService.add(session);

        return sessionId;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Integer update(@RequestBody Session session) {
        LOGGER.debug("rest: update({})", session);

        Integer quantity = sessionService.update(session);

        return quantity;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Integer delete(@RequestParam("id") Integer sessionId) {
        LOGGER.debug("rest: delete({})", sessionId);

        Integer quantity = sessionService.delete(sessionId);

        return quantity;
    }

    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Session getById(@RequestParam("id") Integer sessionId) {
        LOGGER.debug("rest: getById({})", sessionId);

        Session session = sessionService.getById(sessionId);

        return session;
    }
}
