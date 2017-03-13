package com.cinema.controller.web;

import com.cinema.client.CustomerClient;
import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerClient customerClient;

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        LOGGER.debug(" / page.");

        return "hello";
    }

    @ResponseStatus(value = HttpStatus.FOUND)
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public String getAll(Model model){
        LOGGER.debug("web: getAll()");

        List<Customer> customers = customerClient.getAllCustomers();
        model.addAttribute("customers", customers);

        return "customers";
    }
}
