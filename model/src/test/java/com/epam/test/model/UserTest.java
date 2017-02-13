package com.epam.test.model;

import org.junit.Assert;

public class UserTest {

    public static final Integer USER_ID = 11;

    @org.junit.Test
    public void getUserId() throws Exception {
        User user = new User();
        user.setUserId(11);
        Assert.assertEquals("User id: ", USER_ID, user.getUserId());
    }

    @org.junit.Test
    public void getLogin() throws Exception {

    }

    @org.junit.Test
    public void getPassword() throws Exception {

    }

    @org.junit.Test
    public void getDescription() throws Exception {

    }

}