package com.cinema.controller.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        LOGGER.debug(" / page.");

        return "hello";
    }
}
