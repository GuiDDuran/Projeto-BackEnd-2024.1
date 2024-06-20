package com.ibmec.backend.malldelivery.security.userdetails;

import com.ibmec.backend.malldelivery.model.User;
import com.ibmec.backend.malldelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Anotação que indica que esta classe é um serviço
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // Injeção de dependência do repositório de usuários
    @Autowired
    private UserRepository usuarioRepository;

    // Método que carrega o usuário pelo nome de usuário
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo nome de usuário
        User user = usuarioRepository.findByUsername(username)
                // Se o usuário não for encontrado, lança uma exceção
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

        // Retorna uma nova instância de UserDetailsImpl com o usuário encontrado
        return new UserDetailsImpl(user);
    }
}
