package com.ibmec.backend.malldelivery.security.userdetails;

import com.ibmec.backend.malldelivery.model.Profile;
import com.ibmec.backend.malldelivery.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Anotação que indica que os getters e setters serão gerados automaticamente
@Data
public class UserDetailsImpl implements UserDetails {
    // Variável de instância para o usuário
    private transient User user;

    // Construtor que recebe um usuário
    public UserDetailsImpl(User user){
        this.user = user;
    }

    // Construtor padrão
    public UserDetailsImpl(){

    }

    // Método que retorna as autoridades concedidas ao usuário
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Cria uma lista para armazenar as autoridades
        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        // Para cada perfil do usuário
        for (Profile perfil: this.user.getProfiles()) {
            // Adiciona a autoridade correspondente ao perfil na lista
            roles.add(new SimpleGrantedAuthority(perfil.getName().toUpperCase()));
        }
        // Retorna a lista de autoridades
        return roles;
    }

    // Método que retorna a senha do usuário
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    // Método que retorna o nome de usuário
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    // Método que indica se a conta do usuário não está expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Método que indica se a conta do usuário não está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Método que indica se a credencial não foi expirada
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Método que indica se o usuário está habilitado
    @Override
    public boolean isEnabled() {
        return true;
    }
}
