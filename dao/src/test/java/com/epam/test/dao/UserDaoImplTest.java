package com.epam.test.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * UserDaoImpl test
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class UserDaoImplTest {

    private static final User USER = new User("login", "qwerty");

    private static final Integer USER_ID = 1;
    private static final String USER_LOGIN = "userLogin1";
    private static final String USER_PASSWORD = "userPassword1";
    private static final String USER_DESCRIPTION = "userDescription1";

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImplTest.class);

    @Autowired
    UserDao userDao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LOGGER.error("execute: setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        LOGGER.error("execute: tearDownAfterClass()");
    }

    @Before
    public void beforeTest() {
        LOGGER.error("execute: beforeTest()");
    }

    @After
    public void afterTest() {
        LOGGER.error("execute: afterTest()");
    }

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");

        List<User> users = userDao.getAllUsers();

        assertNotNull(users);
        assertTrue(users.size() > 0);
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

        List<User> users = userDao.getAllUsers();
        Integer quantityBefore = users.size();

        Integer userId = userDao.addUser(USER);

        assertNotNull(userId);

        User newUser = userDao.getUserById(userId);

        assertNotNull(newUser);
        assertTrue(USER.getLogin().equals(newUser.getLogin()));
        assertTrue(USER.getPassword().equals(newUser.getPassword()));
        assertNull(USER.getDescription());

        users = userDao.getAllUsers();

        assertEquals(quantityBefore + 1, users.size());
    }

    @Test(expected = org.springframework.dao.DuplicateKeyException.class)
    public void testAddDuplicateUser() throws Exception {
        LOGGER.debug("test: testAddDuplicateUser()");

        User xUser = new User(USER_ID, USER_LOGIN, USER_PASSWORD, USER_DESCRIPTION);

        userDao.addUser(xUser);
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");

        User user = userDao.getUserById(1);
        user.setPassword("updated password");
        user.setDescription("updated description");

        int count = userDao.updateUser(user);

        assertEquals(1, count);

        User updatedUser = userDao.getUserById(user.getUserId());

        assertTrue(user.getLogin().equals(updatedUser.getLogin()));
        assertTrue(user.getPassword().equals(updatedUser.getPassword()));
        assertTrue(user.getDescription().equals(updatedUser.getDescription()));
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("test: deleteUser()");

        Integer userId = userDao.addUser(USER);

        assertNotNull(userId);

        List<User> users = userDao.getAllUsers();
        Integer quantityBefore = users.size();

        int count = userDao.deleteUser(userId);

        assertEquals(1, count);

        users = userDao.getAllUsers();

        assertEquals(quantityBefore - 1, users.size());
    }

    @Test
    public void getUserByLogin() throws Exception {
        LOGGER.debug("test: getUserByLogin()");

        User user = userDao.getUserByLogin(USER_LOGIN);

        assertNotNull(user);
        assertEquals("User login:", USER_LOGIN, user.getLogin());
    }
}