package com.epam.test.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private static final String VERSION = "1.0";

    private static final Logger LOGGER = LogManager.getLogger(VersionController.class);

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String getVersion() {
        LOGGER.debug("getVersion()");

        return VERSION;
    }
}
