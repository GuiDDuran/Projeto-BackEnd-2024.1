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
import java.util.List;
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

        mvc.perform(MockMvcRequestBuilders.post("/usuario/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void naoDeveCriarUsuarioRetornandoNoContent() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername("teste");
        request.setPassword("teste");
        request.setProfile_id(1);

        given(this.userService.create(request.getUsername(), request.getPassword(), request.getProfile_id())).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/usuario/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void deveLogarUsuarioComSucesso() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername("teste");
        request.setPassword("teste");

        given(this.userService.getUserByUsernameAndPassword(request.getUsername(), request.getPassword())).willReturn(user);

        mvc.perform(MockMvcRequestBuilders.post("/usuario/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void naoDeveLogarUsuarioRetornandoNotFound() throws Exception {
        UserRequest request = new UserRequest();
        request.setUsername("teste");
        request.setPassword("teste");

        given(this.userService.getUserByUsernameAndPassword(request.getUsername(), request.getPassword())).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/usuario/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void deveAcharUsuarioPorId() throws Exception {
        int id = 1;
        given(this.userService.getById(id)).willReturn(user);

        mvc.perform(MockMvcRequestBuilders.get("/usuario/buscar/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void naoDeveAcharUsuarioPorIdRetornandoNotFound() throws Exception {
        int id = -1;
        given(this.userService.getById(id)).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/usuario/buscar/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void deveAcharTodosOsUsuariosComSucesso() throws Exception {
        given(this.userService.getAll()).willReturn(List.of(user));

        mvc.perform(MockMvcRequestBuilders.get("/usuario/buscar/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is(user.getUsername())));
    }

    @Test
    @WithMockUser(username = "teste", password = "teste", roles = "USER")
    public void naoDeveAcharTodosOsUsuariosRetornandoNotFound() throws Exception {
        given(this.userService.getAll()).willReturn(List.of());

        mvc.perform(MockMvcRequestBuilders.get("/usuario/buscar/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
