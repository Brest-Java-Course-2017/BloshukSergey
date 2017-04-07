package com.cinema.controller.rest;

import com.cinema.aop.annotation.Loggable;
import com.cinema.model.Session;
import com.cinema.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Loggable
    @ResponseBody
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<Session> getAll(){
        List<Session> sessions = sessionService.getAll();

        return sessions;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestBody Session session) {
        Integer sessionId = sessionService.add(session);

        return sessionId;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Integer update(@RequestBody Session session) {
        Integer quantity = sessionService.update(session);

        return quantity;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Integer delete(@RequestParam("id") Integer sessionId) {
        Integer quantity = sessionService.delete(sessionId);

        return quantity;
    }

    @Loggable
    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Session getById(@RequestParam("id") Integer sessionId) {
        Session session = sessionService.getById(sessionId);

        return session;
    }
}
