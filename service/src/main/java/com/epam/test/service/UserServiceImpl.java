package com.epam.test.service;

import com.epam.test.dao.User;
import com.epam.test.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() throws DataAccessException {
        LOGGER.debug("getAllUsers()");

        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Integer userId) throws DataAccessException {
        LOGGER.debug("getUserById({})", userId);

        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByLogin(String login) throws DataAccessException {
        LOGGER.debug("getUserByLogin({})", login);

        Assert.hasText(login, "User login should not be null.");
        //TODO: check login
        return userDao.getUserByLogin(login);
    }

    @Override
    public Integer addUser(User user) throws DataAccessException {
        LOGGER.debug("addUser({})", user);

        Assert.notNull(user, "User should not be null.");
        Assert.isNull(user.getUserId(), "User ID should be null.");
        Assert.hasText(user.getLogin(), "User login should not be null.");
        Assert.hasText(user.getPassword(), "User password should not be null.");
        //TODO: check new user
        return userDao.addUser(user);
    }

    @Override
    public Integer updateUser(User user) throws DataAccessException {
        LOGGER.debug("updateUser({})", user);

        Assert.notNull(user, "User should not be null.");
        Assert.notNull(user.getUserId(), "User ID should not be null.");
        Assert.hasText(user.getLogin(), "User login should not be null.");
        Assert.hasText(user.getPassword(), "User password should not be null.");
        //TODO: check update user
        return userDao.updateUser(user);
    }

    @Override
    public Integer deleteUser(Integer userId) throws DataAccessException {
        LOGGER.debug("deleteUser({})", userId);

        Assert.notNull(userId, "User id should not be null.");

        LOGGER.debug("deleteUser(): user id = {} ", userId);

        return userDao.deleteUser(userId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(userDao == null) {
            throw new BeanCreationException("userDao is null");
        }
    }
}
