package com.epam.test.client.rest;

import com.epam.test.client.exception.ServerDataAccessException;
import com.epam.test.client.rest.api.UsersConsumer;
import com.epam.test.dao.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UsersConsumerRest implements UsersConsumer {

    private String hostUrl;

    private String urlUsers;

    private String urlUser;

    RestTemplate restTemplate;

    public UsersConsumerRest(String hostUrl, String urlUsers, String urlUser) {
        this.hostUrl = hostUrl;
        this.urlUsers = urlUsers;
        this.urlUser = urlUser;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getAllUsers() throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUsers + "/all", List.class);
        List<User> users = (List<User>) responseEntity.getBody();

        return users;
    }

    @Override
    public User getUserById(Integer userId) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUsers + "/" + userId, User.class);
        User user = (User) responseEntity.getBody();

        return user;
    }

    @Override
    public User getUserByLogin(String login) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUsers + "/" + urlUser + "/" + login, User.class);
        User user = (User) responseEntity.getBody();

        return user;
    }

    @Override
    public Integer addUser(User user) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.postForEntity(hostUrl + "/" + urlUsers + "/add", user, Integer.class);
        Integer userId = (Integer) responseEntity.getBody();

        return userId;
    }

    @Override
    public Integer updateUser(User user) throws ServerDataAccessException {
        HttpEntity<User> entity = new HttpEntity<User>(user);
        restTemplate.put(hostUrl + "/" + urlUsers + "/upd", user);

        return user.getUserId();
    }

    @Override
    public Integer deleteUser(Integer userId) throws ServerDataAccessException {
        restTemplate.delete(hostUrl + "/" + urlUsers + "/del/" + userId);

        return userId;
    }


}
