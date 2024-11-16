package com.projetologinback.login_auth_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetologinback.login_auth_api.domain.user.User;

public interface UserRepository extends JpaRepository<User,String> {

    

}
