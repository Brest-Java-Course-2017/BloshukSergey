package com.epam.test.web_app.controllers;

import com.epam.test.dao.User;
import com.epam.test.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Users Controller.
 */
@Controller
public class UsersController {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String REDIRECT_USERS_ALL = "redirect:users/all";
    public static final String USERS = "users";
    public static final String USER = "user";

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        LOGGER.debug(" / page.");

        return REDIRECT_USERS_ALL;
    }

    @GetMapping(value = "/users/all")
    public String users(Model model) {
        LOGGER.debug(" /users page.");

        List usersList = userService.getAllUsers();
        model.addAttribute("usersList", usersList);

        return USERS;
    }

    @GetMapping(value = "/users/user")
    public String editUser(@RequestParam("id") Integer id, Model model) {
        LOGGER.debug("/user({})",id);

        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        return USER;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String addUserPage(Model model) {
        LOGGER.debug("get: /users/add");

        User user = new User();
        model.addAttribute("user", user);

        return "user";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String createUser(@RequestParam("login") String login,
                             @RequestParam("password") String password,
                             @RequestParam("description") String description) {
        LOGGER.debug("post: /users/add login: {} password: {} description: {}.",login, password, description);

        User user = new User(login, password, description);
        userService.addUser(user);

        return REDIRECT_USERS_ALL;
    }

    @RequestMapping(value = "/users/del", method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam("userId") Integer id) {
        LOGGER.debug("delete: /users/del id: {}.", id);

        userService.deleteUser(id);

        return REDIRECT_USERS_ALL;
    }

    @RequestMapping(value = "/users/upd", method = RequestMethod.PUT)
    public String updateUser(@RequestParam("id") Integer id,
                             @RequestParam("login") String login,
                             @RequestParam("password") String password,
                             @RequestParam("description") String description) {
        LOGGER.debug("put: /users/upd id:{} login:{}  password:{} description:{}.", id, login, password, description);

        User user = new User(id, login, password, description);
        userService.updateUser(user);

        return REDIRECT_USERS_ALL;
    }
}