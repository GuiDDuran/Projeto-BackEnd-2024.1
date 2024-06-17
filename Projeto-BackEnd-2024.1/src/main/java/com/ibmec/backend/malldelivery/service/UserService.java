package com.ibmec.backend.malldelivery.service;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.model.Profile;
import com.ibmec.backend.malldelivery.model.User;
import com.ibmec.backend.malldelivery.repository.ProfileRepository;
import com.ibmec.backend.malldelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository perfilRepository;

    public User create(String username, String password, int idPerfil) throws LojaException {

        Optional<Profile> optRole = this.perfilRepository.findById(idPerfil);

        if (optRole.isEmpty())
            throw new LojaException("perfil", "Perfil não encontrado");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.getProfiles().add(optRole.get());

        this.userRepository.save(user);

        return user;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        Optional<User> optuser = this.userRepository.findByUsernameAndPassword(username, password);

        if (optuser.isEmpty())
            return null;

        return optuser.get();
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public User getById(int id) {
        Optional<User> optuser = this.userRepository.findById(id);

        if (optuser.isEmpty())
            return null;

        return optuser.get();

    }
}
