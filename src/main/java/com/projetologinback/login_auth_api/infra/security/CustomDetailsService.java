package com.projetologinback.login_auth_api.infra.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.projetologinback.login_auth_api.domain.user.User;
import com.projetologinback.login_auth_api.repository.UserRepository;

public class CustomDetailsService implements UserDetailsService{
    
@Autowired
private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = this.repository.findByEMail(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
   
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),new  ArrayList<>());
                
            };
    }


