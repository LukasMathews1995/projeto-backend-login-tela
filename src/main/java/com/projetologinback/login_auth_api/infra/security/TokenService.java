package com.projetologinback.login_auth_api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.projetologinback.login_auth_api.domain.user.User;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
private String secret;
public String generateToken(User user){
    try{

     Algorithm algorithm = Algorithm.HMAC256(secret);
     String token  =JWT.create().withIssuer("login-auth-api").withSubject(user.getEmail())
     .withExpiresAt(generateExpirationDate()).sign(algorithm);
     return token;
    }catch(JWTCreationException e ){
            throw new RuntimeException("Error while authenticating");
    }
}

public Instant generateExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"))
}

}