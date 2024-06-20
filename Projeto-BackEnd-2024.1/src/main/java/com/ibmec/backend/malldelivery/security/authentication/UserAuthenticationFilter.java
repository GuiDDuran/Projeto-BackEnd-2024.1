package com.ibmec.backend.malldelivery.security.authentication;

import com.ibmec.backend.malldelivery.model.User;
import com.ibmec.backend.malldelivery.repository.UserRepository;
import com.ibmec.backend.malldelivery.security.userdetails.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

// Anotação que indica que esta classe é um componente
@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    // Injeção de dependência do serviço de token JWT
    @Autowired
    private JwtTokenService jwtTokenService;

    // Injeção de dependência do repositório de usuários
    @Autowired
    private UserRepository userRepository;

    // Método que é chamado uma vez por requisição
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Verifica se a autenticação já foi realizada
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // Obtém o token da requisição
            String token = obterToken(request);

            // Se o token não for nulo
            if (token != null) {
                // Obtém o assunto do token
                String subject = jwtTokenService.getSubjectFromToken(token);
                // Busca o usuário pelo nome de usuário (que é o assunto do token)
                Optional<User> user = userRepository.findByUsername(subject);

                // Se o usuário for encontrado
                if (user.isPresent()) {
                    // Cria os detalhes do usuário
                    UserDetailsImpl userDetails = new UserDetailsImpl(user.get());

                    // Cria o token de autenticação
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // Define os detalhes da autenticação
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Define a autenticação no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    // Se o usuário não for encontrado, retorna um erro 401
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não encontrado");
                }
            }

        }
        // Continua o processamento do filtro
        filterChain.doFilter(request, response);
    }

    // Método para obter o token da requisição
    private String obterToken(HttpServletRequest request) {
        // Obtém o cabeçalho de autorização
        String token = request.getHeader("Authorization");
        // Se o token não for nulo, remove o prefixo "Bearer "
        if (token != null) {
            return token.replace("Bearer ", "");
        }
        // Se o token for nulo, retorna nulo
        return null;
    }
}
