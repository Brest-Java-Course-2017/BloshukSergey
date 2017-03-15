package com.cinema.controller.web;

import com.cinema.client.CustomerClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerClient customerClient;

}
