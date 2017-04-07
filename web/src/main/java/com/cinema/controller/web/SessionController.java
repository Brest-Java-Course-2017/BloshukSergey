package com.cinema.controller.web;

import com.cinema.aop.annotation.Loggable;
import com.cinema.client.SessionClient;
import com.cinema.model.Session;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping(value = "/session")
public class SessionController implements InitializingBean {

    public static final String REDIRECT = "redirect:";

    public static final String BOOKING_URL = "/booking";

    public static final String SESSION_VIEW_PAGE = "sessionViewPage";

    public static final String REDIRECT_SESSION = "redirect:/session";

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Autowired
    private SessionClient sessionClient;

    @Loggable
    @RequestMapping
    public String getAll(@RequestParam(value = "firstDate", required = false)
                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDate,
                         @RequestParam(value = "secondDate", required = false)
                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date secondDate,
                         Model model){
        StringBuffer httpRequest = new StringBuffer().append(REDIRECT).append(BOOKING_URL).append("/getSessionsWithSeats");

        if(firstDate != null && secondDate != null) {
            httpRequest.append("?firstDate=").append(SIMPLE_DATE_FORMAT.format(firstDate))
                       .append("&secondDate=").append(SIMPLE_DATE_FORMAT.format(secondDate)).toString();
        }

        return httpRequest.toString();
    }

    @Loggable
    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id") Integer id){
        sessionClient.delete(id);

        return REDIRECT_SESSION;
    }

    @Loggable
    @RequestMapping(path = "/sessionView")
    public String sessionView(@RequestParam(value = "id", required = false) Integer id, Model model){
        Session session = null;

        if(id == null)
            session = new Session(id, "", new Date(), 0);
        else
            session = sessionClient.getById(id);

        model.addAttribute("item", session);

        return SESSION_VIEW_PAGE;
    }

    @Loggable
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public String updateSession(@RequestBody Session session, Model model){
        sessionClient.update(session);

        return REDIRECT_SESSION;
    }

    @Loggable
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addSession(@RequestBody Session session, Model model){
        sessionClient.add(session);

        return REDIRECT_SESSION;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sessionClient == null) {
            throw new BeanCreationException("sessionClient is null");
        }
    }
}
