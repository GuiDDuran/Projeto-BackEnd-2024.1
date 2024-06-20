package com.ibmec.backend.malldelivery.service;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.model.Profile;
import com.ibmec.backend.malldelivery.model.User;
import com.ibmec.backend.malldelivery.repository.ProfileRepository;
import com.ibmec.backend.malldelivery.repository.UserRepository;
import com.ibmec.backend.malldelivery.request.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProfileRepository perfilRepository;

    private User user;
    @BeforeEach
    public void Setup() {
        user = new User();
        user.setUsername("admin");
        user.setPassword("pwd");
    }

    @Test
    public void deveAcharUserByUsernameAndPassword() {

        given(this.userRepository.findByUsernameAndPassword("admin", "pwd")).willReturn(Optional.of(user));

        User userResponse = this.service.getUserByUsernameAndPassword("admin", "pwd");

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(user.getUsername(), userResponse.getUsername());
        Assertions.assertEquals(user.getPassword(), userResponse.getPassword());
    }

    @Test
    public void deveRetornarNullSeNaoAcharUserByUsernameAndPassword() {
        given(this.userRepository.findByUsernameAndPassword("admin", "pwd")).willReturn(Optional.empty());

        User userResponse = this.service.getUserByUsernameAndPassword("admin", "pwd");

        Assertions.assertNull(userResponse);
    }

    @Test
    public void DeveAcharUserById() {

        given(this.userRepository.findById(1)).willReturn(Optional.of(user));

        User userResponse = this.service.getById(1);

        Assertions.assertNotNull(userResponse);
    }

    @Test
    public void deveRetornarNullSeNaoAcharUserById() {
        given(this.userRepository.findById(1)).willReturn(Optional.empty());

        User userResponse = this.service.getById(1);

        Assertions.assertNull(userResponse);
    }

    @Test
    public void deveCriarUserComSucesso() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("ADMIN");

        given(this.perfilRepository.findById(1)).willReturn(Optional.of(profile));

        User user = this.service.create("admin", "pwd", 1);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("admin", user.getUsername());
        Assertions.assertEquals("pwd", user.getPassword());
        Assertions.assertEquals(1, user.getProfiles().size());
        Assertions.assertEquals("ADMIN", user.getProfiles().get(0).getName());
    }

    @Test
    public void deveRetornarNullSeNaoAcharPerfilAoCriarUsuario() throws Exception{
        given(this.perfilRepository.findById(1)).willReturn(Optional.empty());

        try {
            User user = this.service.create("admin", "pwd", -1);
        } catch (LojaException e) {
            Assertions.assertEquals("Perfil não encontrado", e.getMessage());
        }
    }

    @Test
    public void deveListarTodosUsuariosComSucesso() {

        given(this.userRepository.findAll()).willReturn(List.of(user));

        List<User> users = this.service.getAll();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(1, users.size());
    }

    @Test
    public void deveRetornarNullSeNaoAcharUsuarioAoListarTodos() {
        given(this.userRepository.findAll()).willReturn(List.of());

        List<User> users = this.service.getAll();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(0, users.size());
    }

}
