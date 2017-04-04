package com.cinema.controller.web;

import com.cinema.client.BookingClient;
import com.cinema.model.Customer;
import com.cinema.model.SessionWithSeats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/booking")
public class BookingController implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(SessionController.class);

    public static final String SESSIONS = "sessions";
    public static final String SESSION_CUSTOMERS = "sessionCustomers";
    public static final String REDIRECT_BOOKING_ID = "redirect:/booking?id=";

    @Autowired
    private BookingClient bookingClient;

    @RequestMapping(path = "/getSessionsWithSeats")
    public String getSessionsWithSeats(
                         @RequestParam(value = "firstDate", required = false)
                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDate,
                         @RequestParam(value = "secondDate", required = false)
                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date secondDate,
                         Model model){
        LOGGER.debug("web: getSessionsWithSeats({}, {})", firstDate, secondDate);

        List<SessionWithSeats> sessions = bookingClient.getSessionsWithSeats(firstDate, secondDate);
        model.addAttribute("sessionList", sessions);

        return SESSIONS;
    }

    @RequestMapping
    public String getCustomersBySessionId(@RequestParam(value = "id") Integer id, Model model) {
        LOGGER.debug("web: getSessionCustomers({})", id);

        List<Customer> customers = bookingClient.getCustomersBySessionId(id);
        model.addAttribute("customerList", customers);
        model.addAttribute("sessionId", id);

        return SESSION_CUSTOMERS;
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "sessionId") Integer sessionId, @RequestParam(value = "customerId") Integer customerId) {
        LOGGER.debug("web: delete({}, {})", sessionId, customerId);

        bookingClient.delete(sessionId, customerId);

        return REDIRECT_BOOKING_ID + sessionId;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String add(@RequestParam(value = "sessionId") Integer sessionId, @RequestParam(value = "customerId") Integer customerId) {
        LOGGER.debug("web: add({}, {})", sessionId, customerId);

        bookingClient.add(sessionId, customerId);

        return REDIRECT_BOOKING_ID + sessionId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (bookingClient == null) {
            throw new BeanCreationException("bookingClient is null");
        }
    }
}
