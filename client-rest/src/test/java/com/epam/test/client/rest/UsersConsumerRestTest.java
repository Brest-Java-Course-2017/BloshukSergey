package com.epam.test.client.rest;

import com.epam.test.client.rest.api.UsersConsumer;
import com.epam.test.dao.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:client-test-mock.xml"})
public class UsersConsumerRestTest {

    private static final User USER_1 = new User(1, "userLogin1", "userPassword1", "");

    private static final User USER_2 = new User(2, "userLogin2", "userPassword2", "");

    @Autowired
    UsersConsumer usersConsumer;

    @Autowired
    private RestTemplate mockRestTemplate;

    @Value("${user.protocol}://${user.host}:${user.port}/")
    private String hostUrl;

    @Value("${point.users}")
    private String urlUsers;

    @Value("${point.user}")
    private String urlUser;

    @After
    public void tearDown() throws Exception {
        verify(mockRestTemplate);
    }

    @Before
    public void setup() throws Exception {
        reset(mockRestTemplate);
    }

    @Test
    public void getAllUsers() throws Exception {
        List expectedResult = new ArrayList(2);
        expectedResult.add(USER_1);
        expectedResult.add(USER_2);

        expect(mockRestTemplate.getForEntity(hostUrl + "/" + urlUsers + "/all", List.class))
                .andReturn(new ResponseEntity<List>(expectedResult, HttpStatus.OK));
        replay(mockRestTemplate);

        List<User> users = usersConsumer.getAllUsers();

        assertEquals(expectedResult.size(), users.size());
    }

    @Test
    public void getUserById() throws Exception {
        String url = hostUrl + "/" + urlUsers + "/" + USER_1.getUserId();

        expect(mockRestTemplate.getForEntity(url, User.class))
                .andReturn(new ResponseEntity<User>(USER_1, HttpStatus.FOUND));
        replay(mockRestTemplate);
        User user = usersConsumer.getUserById(USER_1.getUserId());

        assertEquals(USER_1.getLogin(), user.getLogin());
    }

    @Test
    public void getUserByLogin() throws Exception {
        String url = hostUrl + "/" + urlUsers + "/" + urlUser + "/" + USER_1.getLogin();

        expect(mockRestTemplate.getForEntity(url, User.class))
                .andReturn(new ResponseEntity<User>(USER_1, HttpStatus.FOUND));
        replay(mockRestTemplate);
        User user = usersConsumer.getUserByLogin(USER_1.getLogin());

        //assertEquals(USER_1.getLogin(), user.getLogin());
    }

    @Test
    public void addUser() throws Exception {
        String url = hostUrl + "/" + urlUsers + "/add";
        expect(mockRestTemplate.postForEntity(url, USER_1, Integer.class))
                .andReturn(new ResponseEntity<Integer>(USER_1.getUserId(), HttpStatus.CREATED));
        replay(mockRestTemplate);

        Integer userId = usersConsumer.addUser(USER_1);
        assertEquals(USER_1.getUserId(), userId);
    }

    @Test
    public void updateUser() throws Exception {
        String url = hostUrl + "/" + urlUsers + "/upd";

        mockRestTemplate.put(url, USER_1);
        expectLastCall().times(1);
        replay(mockRestTemplate);

        Integer userId = usersConsumer.updateUser(USER_1);
        assertEquals(USER_1.getUserId(), userId);
    }

    @Test
    public void deleteUser() throws Exception {
        String url = hostUrl + "/" + urlUsers + "/del/" + USER_1.getUserId();

        mockRestTemplate.delete(url);
        expectLastCall().times(1);
        replay(mockRestTemplate);

        Integer userId = usersConsumer.deleteUser(USER_1.getUserId());
        assertEquals(USER_1.getUserId(), userId);
    }

}