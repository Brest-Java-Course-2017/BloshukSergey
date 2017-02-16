package com.epam.test.dao;

import org.junit.Assert;

/**
 * Test
 */

public class UserTest {

    public static final Integer USER_ID = 11;

    public static final String USER_LOGIN = "Sergey";

    public static final String USER_PASSWORD = "qwerty";

    public static final String USER_DESCRIPTION = "Description";

    @org.junit.Test
    public void getUserId() throws Exception {
        User user = new User();
        user.setUserId(11);
        Assert.assertEquals("User id: ", USER_ID, user.getUserId());
    }

    @org.junit.Test
    public void getLogin() throws Exception {
        User user = new User();
        user.setLogin("Sergey");
        Assert.assertEquals("User login: ", USER_LOGIN, user.getLogin());
    }

    @org.junit.Test
    public void getPassword() throws Exception {
        User user = new User();
        user.setPassword("qwerty");
        Assert.assertEquals("User password: ", USER_PASSWORD, user.getPassword());
    }

    @org.junit.Test
    public void getDescription() throws Exception {
        User user = new User();
        user.setDescription("Description");
        Assert.assertEquals("User description: ", USER_DESCRIPTION, user.getDescription());
    }

}