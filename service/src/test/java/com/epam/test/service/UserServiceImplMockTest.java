package com.epam.test.service;

import com.epam.test.dao.UserDao;
import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test-mock.xml"})
public class UserServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImplMockTest.class.getName());

    private static final User user = new User("userLogin3", "userPassword3");

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao mockUserDao;

    @After
    public void clean() {
        verify(mockUserDao);
        reset(mockUserDao);
    }

    @Test
    public void addUserTest() throws Exception {
        LOGGER.debug("test: addUserTest()");

        expect(mockUserDao.addUser(new User("userLogin3", "userPassword3"))).andReturn(3);
        replay(mockUserDao);

        Integer id = userService.addUser(user);

        Assert.isTrue(id == 3);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getUserByLoginExceptionTest() throws Exception {
        LOGGER.debug("test: getUserByLoginExceptionTest()");

        expect(mockUserDao.getUserByLogin(user.getLogin())).andThrow(new UnsupportedOperationException());
        replay(mockUserDao);

        userService.getUserByLogin(user.getLogin());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        LOGGER.debug("test: getUserByIdTest()");

        expect(mockUserDao.getUserById(3)).andReturn(user);
        replay(mockUserDao);

        User newUser = userService.getUserById(3);
        assertEquals("userLogin3", newUser.getLogin());
    }

    @Test
    public void getUserByNonexistentIdTest() throws Exception {
        LOGGER.debug("test: getUserByNonexistentIdTest()");

        expect(mockUserDao.getUserById(5)).andReturn(null);
        replay(mockUserDao);

        User newUser = userService.getUserById(5);
        assertNull(newUser);
    }

    @Test(expected = org.springframework.dao.DataAccessException.class)
    public void deleteNonexistentUser() throws Exception {
        LOGGER.debug("test: deleteNonexistentUser()");

        mockUserDao.deleteUser(5);
        EasyMock.expectLastCall().andThrow(new DataAccessException("There is no user with ID 5"){});
        replay(mockUserDao);

        userService.deleteUser(5);
    }
}