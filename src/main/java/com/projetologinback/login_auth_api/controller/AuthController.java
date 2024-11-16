package com.projetologinback.login_auth_api.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetologinback.login_auth_api.domain.user.User;
import com.projetologinback.login_auth_api.dto.LoginRequestDTO;
import com.projetologinback.login_auth_api.dto.RegisterRequestDTO;
import com.projetologinback.login_auth_api.dto.ResponseDTO;
import com.projetologinback.login_auth_api.infra.security.TokenService;
import com.projetologinback.login_auth_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

@PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){

        User user = repository.findByEMail(body.email()).orElseThrow(()-> new RuntimeException("User not found"));

        if(passwordEncoder.matches(user.getPassword(), body.password())){
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(),token));
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
      Optional<User> user =   repository.findByEMail(body.email());
      if(user.isEmpty()){
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
       repository.save(newUser);
            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(),token));
      }
      else 
       
            return ResponseEntity.badRequest().build();
        }
    }

