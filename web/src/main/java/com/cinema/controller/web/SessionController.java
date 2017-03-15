package com.cinema.controller.web;

import com.cinema.client.SessionClient;
import com.cinema.model.Session;
import com.cinema.model.SessionWithQuantityTickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/session")
public class SessionController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    public static final String REDIRECT_GET_ALL_WITH_TICKETS = "redirect:/session/getAllWithTickets";

    @Autowired
    private SessionClient sessionClient;

    @ResponseStatus(value = HttpStatus.FOUND)
    @RequestMapping(value = "/getAllWithTickets",method = RequestMethod.GET)
    public String getAllWithTickets(Model model){
        LOGGER.debug("web: getAllWithTickets()");

        List<SessionWithQuantityTickets> sessions = sessionClient.getAllSessionsWithQuantityTickets();
        model.addAttribute("sessions", sessions);

        LOGGER.debug("Response: {}", sessions);

        return "sessionWithQuantityTickets";
    }

    @ResponseStatus(value = HttpStatus.FOUND)
    @RequestMapping(value = "/getAllWithTicketsDateToDate",method = RequestMethod.GET)
    public List<SessionWithQuantityTickets> getAllWithTicketsDateToDate(@RequestParam("firstDate")
                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                        Date firstDate,
                                                                        @RequestParam("secondDate")
                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                        Date secondDate,
                                                                        Model model) {
        LOGGER.debug("web: getAllWithTicketsDateToDate({}, {})", firstDate, secondDate);

        List<SessionWithQuantityTickets> sessions = sessionClient.getAllSessionsWithQuantityTicketsDateToDate(firstDate, secondDate);
        model.addAttribute("sessions", sessions);

        LOGGER.debug("Response: {}", sessions);

        return sessions;
    }

    @RequestMapping(value = "/showSession", method = RequestMethod.GET)
    public String showSession(@RequestParam(value = "id", required = false) Integer sessionId, Model model) {
        LOGGER.debug("web: showSession({})", sessionId);

        Session session;
        if(sessionId != null) {
            session = sessionClient.getSessionById(sessionId);
            model.addAttribute("method", "\'update\'");
        }
        else {
            session = new Session(0,"Movie name", new Date(System.currentTimeMillis()));
            model.addAttribute("method", "\'add\'");
        }

        LOGGER.debug("Response: {}", session);
        model.addAttribute("session", session);

        return "sessionAddEdit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") Integer sessionId) {
        LOGGER.debug("web: delete({})", sessionId);

        sessionClient.deleteSession(sessionId);

        return REDIRECT_GET_ALL_WITH_TICKETS;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam("sessionId") Integer sessionId,
                      @RequestParam("movieName") String movieName,
                      @RequestParam("sessionDate") Date sessionDate) {
        LOGGER.debug("web: add({}, {}, {})", sessionId, movieName, sessionDate);

        sessionClient.addSession(new Session(sessionId, movieName, sessionDate));

        return REDIRECT_GET_ALL_WITH_TICKETS;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@RequestParam("sessionId") Integer sessionId,
                         @RequestParam("movieName") String movieName,
                         @DateTimeFormat(pattern = "yyyy-MM-dd")
                         @RequestParam("sessionDate") Date sessionDate) {
        LOGGER.debug("web: update({}, {}, {})", sessionId, movieName, sessionDate);

        sessionClient.updateSession(new Session(sessionId, movieName, sessionDate));

        return REDIRECT_GET_ALL_WITH_TICKETS;
    }
}
