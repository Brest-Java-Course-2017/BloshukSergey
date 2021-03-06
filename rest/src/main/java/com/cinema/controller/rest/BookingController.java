package com.cinema.controller.rest;

import com.cinema.aop.annotation.Loggable;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import com.cinema.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @ResponseBody
    @Loggable
    @RequestMapping(value = "/getCustomersBySessionId", method = RequestMethod.GET)
    public List<Customer> getCustomersBySessionId(@RequestParam("id") Integer id) {
        List<Customer> customers = bookingService.getCustomersBySessionId(id);

        return customers;
    }

    @Loggable
    @RequestMapping(value = "/getSessionsWithSeats", method = RequestMethod.GET)
    @ResponseBody
    public List<SessionWithSeats> getSessionsWithSeats(
            @RequestParam(value = "firstDate", required = false)  Date firstDate,
            @RequestParam(value = "secondDate", required = false)  Date secondDate) throws ParseException {
        List<SessionWithSeats> sessions = sessions = bookingService.getSessionsWithSeats(firstDate, secondDate);

        return sessions;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Integer delete(@RequestParam(value = "sessionId") Integer sessionId,
                          @RequestParam(value = "customerId") Integer customerId) {
        Integer quantity = bookingService.delete(sessionId, customerId);

        return quantity;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestParam(value = "sessionId") Integer sessionId,
                       @RequestParam(value = "customerId") Integer customerId) throws IllegalArgumentException {
        Integer quantity = bookingService.add(sessionId, customerId);

        return quantity;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
