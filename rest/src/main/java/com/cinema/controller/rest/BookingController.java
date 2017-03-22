package com.cinema.controller.rest;

import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import com.cinema.service.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    private static final Logger LOGGER = LogManager.getLogger(BookingController.class);

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Autowired
    private BookingService bookingService;

    @ResponseBody
    @RequestMapping(value = "/getCustomersBySessionId", method = RequestMethod.GET)
    public List<Customer> getCustomersBySessionId(@RequestParam("id") Integer id) {
        LOGGER.debug("rest: getCustomersBySessionId()");

        List<Customer> customers = bookingService.getCustomersBySessionId(id);

        return customers;
    }

    @RequestMapping(value = "/getSessionsWithSeats", method = RequestMethod.GET)
    @ResponseBody
    public List<SessionWithSeats> getSessionsWithSeats(
            @RequestParam(value = "firstDate", required = false) String firstDate,
            @RequestParam(value = "secondDate", required = false) String secondDate) throws ParseException {
        LOGGER.debug("rest: getSessionsWithSeats({}, {})", firstDate, secondDate);

        List<SessionWithSeats> sessions;
        if(firstDate == null && secondDate == null) {
            sessions = bookingService.getSessionsWithSeats(null, null);
        }
        else {
            sessions = bookingService.getSessionsWithSeats(SIMPLE_DATE_FORMAT.parse(firstDate), SIMPLE_DATE_FORMAT.parse(secondDate));
        }

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
                       @RequestParam(value = "customerId") Integer customerId) throws IllegalArgumentException {
        LOGGER.debug("rest: add()");

        Integer quantity = bookingService.add(sessionId, customerId);

        return quantity;
    }

}
