package com.cinema.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-service.xml"})
@Transactional
public class SessionServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(SessionServiceImplTest.class);

    @Autowired
    private SessionService sessionService;

    public static final LocalDate FIRST_DATE = LocalDate.of(2017, 3, 1);

    public static final LocalDate SECOND_DATE = LocalDate.of(2017, 3, 22);

    public static final Integer BAD_ID = -100;

    @Test(expected = IllegalArgumentException.class)
    public void getAllSessionsWithQuantityTicketsDateToDateByBadDateThrowException() throws Exception {
        LOGGER.debug("test: getAllSessionsWithQuantityTicketsDateToDateByBadDateThrowException()");

        sessionService.getAllSessionsWithQuantityTicketsDateToDate(SECOND_DATE, FIRST_DATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllSessionsWithQuantityTicketsDateToDateByNullDateThrowException() throws Exception {
        LOGGER.debug("test: getAllSessionsWithQuantityTicketsDateToDateByNullDateThrowException()");

        sessionService.getAllSessionsWithQuantityTicketsDateToDate(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSessionByBadIdThrowException() throws Exception {
        LOGGER.debug("test: getSessionByBadIdThrowException()");

        sessionService.getSessionById(BAD_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullSessionThrowException() throws Exception {
        LOGGER.debug("test: addNullCustomerThrowException()");

        sessionService.addSession(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateSessionByNullThrowException() throws Exception {
        LOGGER.debug("test: addNullCustomerThrowException()");

        sessionService.updateSession(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSessionByNullIdThrowException() throws Exception {
        LOGGER.debug("test: addNullCustomerThrowException()");

        sessionService.deleteSession(null);
    }
}