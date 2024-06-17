package com.ibmec.backend.malldelivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibmec.backend.malldelivery.model.User;
import com.ibmec.backend.malldelivery.request.UserRequest;
import com.ibmec.backend.malldelivery.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();

        user.setUsername("teste");
        user.setPassword("teste");

        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void deveCriarUsuarioComSucesso() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername("teste");
        request.setPassword("teste");
        request.setProfile_id(1);

        given(this.userService.create(request.getUsername(), request.getPassword(), request.getProfile_id())).willReturn(user);

        mvc.perform(MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }
}
