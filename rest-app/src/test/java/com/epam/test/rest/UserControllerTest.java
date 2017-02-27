package com.epam.test.rest;

import com.epam.test.dao.User;
import com.epam.test.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-rest-mock.xml")
public class UserControllerTest {

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(userController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
        verify(userService);
        reset(userService);
    }

    @Test
    public void getUsers() throws Exception {
        expect(userService.getAllUsers()).andReturn(Arrays.<User>asList(new User("l", "p")));
        replay(userService);

        mockMvc.perform(get("/users/all").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        expect(userService.getUserById(anyObject(Integer.class))).andReturn(new User("l", "p"));
        replay(userService);

        mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isFound());
    }

    @Test
    public void addUserTest() throws Exception {
        expect(userService.addUser(anyObject(User.class))).andReturn(3);
        replay(userService);

        String user = new ObjectMapper().writeValueAsString(new User("login2", "password2"));

        mockMvc.perform(
                post("/users/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("3"));
    }

    @Test
    public void updateUserTest() throws Exception {
        expect(userService.updateUser(anyObject(User.class))).andReturn(2);
        replay(userService);

        String user = new ObjectMapper()
                .writeValueAsString(new User(2,"login2", "password2", "kek"));

        mockMvc.perform(
                put("/users/upd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user)
        ).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string("2"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        expect(userService.deleteUser(anyObject(Integer.class))).andReturn(2);
        replay(userService);

        mockMvc.perform(
                delete("/users/del/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }
}
