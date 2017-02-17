package com.epam.test.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * UserDaoImpl test
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
public class UserDaoImplTest {
    private static final User USER = new User(Integer.valueOf(3), "TestLogin", "qwerty", "Test");
    private static final Integer USER_ID = 1;
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImplTest.class);

    @Autowired
    UserDao userDao;

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");

        List<User> users = userDao.getAllUsers();

        assertNotNull(users);
        assertTrue(users.size() == 2);
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("test: getUserById()");
        User user = userDao.getUserById(USER_ID);

        assertNotNull(user);
        assertEquals("User id:", USER_ID, user.getUserId());
    }

    @Test
    public void addUser() throws Exception {
        LOGGER.debug("test: addUser()");

        Integer response = userDao.addUser(USER);

        assertTrue("Response:", response.intValue() == 1);

        userDao.deleteUser(USER.getUserId());
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");

        userDao.addUser(USER);
        User tempUser = new User(USER.getUserId(), "login", "password", "description");
        userDao.updateUser(tempUser);

        User responseUser = userDao.getUserById(USER.getUserId());
        assertEquals("Update user:", tempUser, responseUser);

        userDao.deleteUser(USER.getUserId());
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("test: deleteUser()");

        userDao.addUser(USER);
        userDao.deleteUser(USER.getUserId());
        User tempUser = userDao.getUserById(USER.getUserId());

        assertNull("Delete user:", tempUser);
    }

}