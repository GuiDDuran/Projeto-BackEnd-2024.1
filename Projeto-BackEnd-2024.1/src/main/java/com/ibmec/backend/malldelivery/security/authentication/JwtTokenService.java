package com.ibmec.backend.malldelivery.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ibmec.backend.malldelivery.security.userdetails.UserDetailsImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;

@Service // Anotação que indica que esta classe é um serviço
public class JwtTokenService {
    // Carrega as variáveis de ambiente
    private static final Dotenv dotenv = Dotenv.load();

    // Recupera a chave secreta do arquivo .env
    private static final String SECRET_KEY = dotenv.get("SECRET_KEY");

    // Define o emissor do token
    private static final String ISSUER = "mall-delivery-api";

    // Método para gerar um token JWT
    public String generateToken(UserDetailsImpl user) {
        try {
            // Define o algoritmo de assinatura do token
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            // Cria um novo token JWT
            return JWT.create()
                    .withIssuer(ISSUER) // Define o emissor do token
                    .withIssuedAt(creationDate()) // Define a data de criação do token
                    .withExpiresAt(expirationDate()) // Define a data de expiração do token
                    .withSubject(user.getUser().getUsername()) // Define o assunto do token como o nome de usuário
                    .sign(algorithm); // Assina o token com o algoritmo definido
        }
        catch (JWTCreationException exception) {
            // Lança uma exceção caso ocorra um erro ao gerar o token
            throw new JWTCreationException("Erro ao gerar o token", exception);
        }
    }

    // Método para obter o assunto de um token JWT
    public String getSubjectFromToken(String token) {
        try {
            // Define o algoritmo de assinatura do token
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            // Verifica o token e retorna o assunto
            return JWT.require(algorithm)
                      .withIssuer(ISSUER) // Verifica se o emissor do token é o esperado
                      .build() // Constrói o verificador de token
                      .verify(token) // Verifica o token
                      .getSubject(); // Retorna o assunto do token
        }
        catch (JWTVerificationException exception) {
            // Em caso de erro na verificação do token, lança uma exceção
            throw new JWTVerificationException("Token invalido", exception);
        }
    }

    // Método para obter a data de criação do token
    public Instant creationDate() {
        // Retorna a data e hora atuais
        return ZonedDateTime.now().toInstant();
    }

    // Método para obter a data de expiração do token
    public Instant expirationDate() {
        // Retorna a data e hora atuais mais 2 horas
        return ZonedDateTime.now().plusHours(2).toInstant();
    }
}
