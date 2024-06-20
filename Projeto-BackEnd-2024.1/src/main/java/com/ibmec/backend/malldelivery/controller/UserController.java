package com.ibmec.backend.malldelivery.controller;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.model.User;
import com.ibmec.backend.malldelivery.request.UserRequest;
import com.ibmec.backend.malldelivery.response.TokenResponse;
import com.ibmec.backend.malldelivery.security.authentication.JwtTokenService;
import com.ibmec.backend.malldelivery.security.userdetails.UserDetailsImpl;
import com.ibmec.backend.malldelivery.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/criar")
    public ResponseEntity<User> create(@RequestBody @Valid UserRequest request) throws LojaException {
        User newUser = this.userService.create(request.getUsername(), request.getPassword(), request.getProfile_id());
        if (newUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid UserRequest request) throws LojaException {
        User user = this.userService.getUserByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        String jwtToken = jwtTokenService.generateToken(new UserDetailsImpl(user));
        TokenResponse response = new TokenResponse();
        response.setToken(jwtToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/buscar/todos")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = this.userService.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) {
        User user = this.userService.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
