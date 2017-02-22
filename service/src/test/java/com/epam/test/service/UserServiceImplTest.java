package com.epam.test.service;

import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class UserServiceImplTest implements InitializingBean {

    public static final String USER_LOGIN_1 = "userLogin1";

    public static final User USER = new User("login", "password");

    public static final Integer USER_ID = 1;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");

        List<User> users = userService.getAllUsers();

        Assert.assertEquals("getAllUsers()", 2, users.size());
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("test: getUserById()");

        User user = userService.getUserById(USER_ID);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUserId());
        Assert.assertEquals(USER_ID, user.getUserId());
    }

    @Test
    public void getUserByLogin() throws Exception {
        LOGGER.debug("test: getUserByLogin()");

        User user = userService.getUserByLogin(USER_LOGIN_1);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getLogin());
        Assert.assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void addUser() throws Exception {
        LOGGER.debug("test: addUser()");

        List<User> users = userService.getAllUsers();
        Integer quantityBefore = users.size();
        userService.addUser(USER);
        users = userService.getAllUsers();

        assertEquals(quantityBefore + 1, users.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser() throws Exception {
        LOGGER.debug("test: addNullUser()");

        userService.addUser(null);
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");

        User user = userService.getUserById(USER_ID);
        user.setPassword("updated password");
        user.setDescription("updated description");
        Integer count = userService.updateUser(user);

        assertEquals(USER_ID, count);

        User updatedUser = userService.getUserById(user.getUserId());

        assertTrue(user.getLogin().equals(updatedUser.getLogin()));
        assertTrue(user.getPassword().equals(updatedUser.getPassword()));
        assertTrue(user.getDescription().equals(updatedUser.getDescription()));
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("test: deleteUser()");

        Integer userId = userService.addUser(USER);
        List<User> users = userService.getAllUsers();
        Integer quantityBefore = users.size();
        userService.deleteUser(userId);
        users = userService.getAllUsers();

        assertEquals(quantityBefore - 1, users.size());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(userService == null) {
            throw new BeanCreationException("UserService is null");
        }
    }
}