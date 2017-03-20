package com.cinema.controller.rest;

import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import com.cinema.service.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    private static final Logger LOGGER = LogManager.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @ResponseBody
    @RequestMapping(value = "/getCustomersBySessionId", method = RequestMethod.GET)
    public List<Customer> getCustomersBySessionId(@RequestParam("id") Integer id) {
        LOGGER.debug("rest: getCustomersBySessionId()");

        List<Customer> customers = bookingService.getCustomersBySessionId(id);

        return customers;
    }

    @ResponseBody
    @RequestMapping(value = "/getSessionsWithSeats", method = RequestMethod.GET)
    public List<SessionWithSeats> getSessionsWithSeats(@RequestParam(value = "firstDate", required = false)
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                       Date firstDate,
                                                       @RequestParam(value = "secondDate", required = false)
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                       Date secondDate) {
        LOGGER.debug("rest: getSessionsWithSeats()");

        List<SessionWithSeats> sessions = bookingService.getSessionsWithSeats(firstDate, secondDate);

        return sessions;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Integer delete(@RequestParam(value = "sessionId") Integer sessionId,
                          @RequestParam(value = "customerId") Integer customerId) {
        LOGGER.debug("rest: delete()");

        Integer quantity = bookingService.delete(sessionId, customerId);

        return quantity;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestParam(value = "sessionId") Integer sessionId,
                       @RequestParam(value = "customerId") Integer customerId) {
        LOGGER.debug("rest: add()");

        Integer quantity = bookingService.add(sessionId, customerId);

        return quantity;
    }

}
