package com.ibmec.backend.malldelivery.security.config;

import com.ibmec.backend.malldelivery.security.authentication.UserAuthenticationFilter;
import com.ibmec.backend.malldelivery.security.userdetails.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Anotação que indica que esta classe é uma configuração
@Configuration
// Anotação que habilita a segurança web
@EnableWebSecurity
public class SecurityConfiguration {
    // Define os endpoints que não requerem autenticação
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/usuario/criar",
            "/usuario/login"
    };

    // Define os endpoints que requerem autenticação
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/usuario/buscar/**",
            "/loja/**"
    };

    // Define um bean para o serviço de detalhes do usuário
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    // Define um bean para a cadeia de filtros de segurança
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity httpSecurity, @Autowired UserAuthenticationFilter userAuthenticationFilter) throws Exception {
        return httpSecurity
                // Desabilita o CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Configura as requisições autorizadas
                .authorizeHttpRequests((opt) -> {
                    // Permite todos os acessos aos endpoints que não requerem autenticação
                    opt.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                            // Requer autenticação para os endpoints que a requerem
                       .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated();
                })
                // Configura a gestão de sessão para ser stateless
                .sessionManagement((opt) -> opt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura o provedor de autenticação
                .authenticationProvider(authenticationProvider())
                // Adiciona o filtro de autenticação do usuário antes do filtro de autenticação de nome de usuário e senha
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Constrói a cadeia de filtros de segurança
                .build();
    }

    // Define um bean para o gerenciador de autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define um bean para o provedor de autenticação
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // Configura o serviço de detalhes do usuário
        authenticationProvider.setUserDetailsService(userDetailsService());
        // Configura o codificador de senha
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Define um bean para o codificador de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
