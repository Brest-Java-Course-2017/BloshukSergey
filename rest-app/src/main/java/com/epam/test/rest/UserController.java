package com.epam.test.rest;

import com.epam.test.dao.User;
import com.epam.test.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsers() {
        LOGGER.debug("getUsers()");

        List users = userService.getAllUsers();
        LOGGER.debug("Users: {}", users);

        return users;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody User getUser(@PathVariable(value = "id") Integer id) {
        LOGGER.debug("getUser(id: {})", id);

        User user = userService.getUserById(id);
        LOGGER.debug("Response: {}", user);

        return user;
    }

    @RequestMapping(value = "/users/user/{login}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public  @ResponseBody User getUser(@PathVariable(value = "login") String login){
        LOGGER.debug("getUser(login: {})",login);

        User user = userService.getUserByLogin(login);

        return user;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Integer addUser(@RequestBody User user) {
        LOGGER.debug("addUser(user: {})", user);

        return userService.addUser(user);
    }

    // curl -H "Content-Type: application/json" -X PUT
    // -d '{"userId":"1","login":"xyz","password":"xyz","description":"kek"}'
    // -v localhost:8080/users/upd
    @RequestMapping(value = "/users/upd", method = RequestMethod.PUT)
    @ResponseStatus (value = HttpStatus.ACCEPTED)
    public @ResponseBody Integer updateUser (@RequestBody User user){
        LOGGER.debug("updateUser(user: {})", user);

        return userService.updateUser(user);
    }

    @RequestMapping(value = "/users/del/{id}", method = RequestMethod.DELETE)
    @ResponseStatus (value = HttpStatus.OK)
    public @ResponseBody Integer deleteUser (@PathVariable(value = "id") Integer id) {
        LOGGER.debug("deleteUser(id: {})", id);

        return userService.deleteUser(id);
    }
}
