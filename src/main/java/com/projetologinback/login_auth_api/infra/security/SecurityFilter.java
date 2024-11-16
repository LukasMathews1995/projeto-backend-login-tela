package com.projetologinback.login_auth_api.infra.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projetologinback.login_auth_api.domain.user.User;
import com.projetologinback.login_auth_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SecurityFilter extends OncePerRequestFilter {

@Autowired
TokenService tokenService;
@Autowired
UserRepository userRepository;

    // esse metodo é o filter interno que chama no SecurityCOnfig no metodo addFilterBefore
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        var token = this.recoverToken(request);      
        var login = tokenService.validateToken(token);
        if(token!=null){
                
                User user = userRepository.findByEMail(login);// com o login com o email da pessoa , podemos acha-lo pelo email
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));// roles será apenas o basico so para testar
                var authentication = new UsernamePasswordAuthenticationToken(user, null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);


                SecurityContextHolder.getContext().setAuthentication(authentication);
        }    

        // aqui significa que já terminou e pode ir para o proximo filtro(o que seria no Security config)
        // o userNamePasswordAutenticationFilter
            filterChain.doFilter(request, response);
            
            }
            // retornar o valor do token para subir na aplicaçao e ser pego no doFilterInternal
            private String recoverToken(HttpServletRequest request){
          var authHeader = request.getHeader("Authorization");
          if(authHeader==null){
        return null;
          }
          
          return authHeader.replace("Bearer ", "");
            }

}