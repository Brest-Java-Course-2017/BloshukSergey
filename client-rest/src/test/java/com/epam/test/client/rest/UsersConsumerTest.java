package com.epam.test.client.rest;

import com.epam.test.client.rest.api.UsersConsumer;
import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class UsersConsumerTest {

    private static final String USER_LOGIN_1 = "userLogin1";
    private static final String USER_PASSWORD_1 = "userPassword1";
    private static final Integer USER_ID = 1;

    private static final User USER = new User("login", "password", "desc");
    private static final User USER_2 = new User(2,"newLogin", "newPassword", "newDesc");


    @Autowired
    UsersConsumer usersConsumer;

    private static final Logger LOGGER = LogManager.getLogger(UsersConsumerTest.class.getName());

    @Test
    public void getAllUsersTest() throws Exception {
        LOGGER.debug("test: getAllUsersTest()");

        List<User> users = usersConsumer.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void getUserByLoginTest() throws Exception {
        LOGGER.debug("test: getUserByLoginTest()");

        User user = usersConsumer.getUserByLogin(USER_LOGIN_1);
        assertEquals(user.getPassword(), USER_PASSWORD_1);
    }

    @Test
    public void getUserByIdTest() throws Exception {
        LOGGER.debug("test: getUserByIdTest()");

        User user = usersConsumer.getUserById(USER_ID);
        assertEquals(user.getLogin(), USER_LOGIN_1);
    }

    @Test
    public void addUserTest() throws Exception {
        LOGGER.debug("test: addUserTest()");

        Integer userId = usersConsumer.addUser(USER);
        User user = usersConsumer.getUserById(userId);

        assertEquals(USER.getPassword(), user.getPassword());

        usersConsumer.deleteUser(userId);
    }

    @Test
    public void updateUserTest() throws Exception {
        LOGGER.debug("test: updateUserTest()");

        Integer userId = usersConsumer.updateUser(USER_2);
        User user = usersConsumer.getUserById(USER_2.getUserId());

        assertNotNull(user);
        assertEquals(USER_2.getLogin(), user.getLogin());
        assertEquals(USER_2.getPassword(), user.getPassword());
        assertEquals(USER_2.getDescription(), user.getDescription());
    }

    @Test
    public void deleteUserTest() throws Exception {
        LOGGER.debug("test: deleteUserTest()");

        Integer beforeUserId = usersConsumer.addUser(USER);
        Integer afterUserId = usersConsumer.deleteUser(beforeUserId);

        assertEquals(beforeUserId, afterUserId);
    }

}
