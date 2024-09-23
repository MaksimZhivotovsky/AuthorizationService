package com.example.AuthorizationServer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.example.AuthorizationServer.entity.LdapUser;
import com.example.AuthorizationServer.entity.User;
import com.example.AuthorizationServer.repository.LdapUserRepository;
import com.example.AuthorizationServer.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LdapUserRepository ldapUserRepository;
//    private final PasswordEncoder passwordEncoder;

    @Value("${isLDAP}")
    private Boolean isLDAP;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername email {}", email);
        if (Boolean.TRUE.equals(isLDAP)) {
            LdapUser ldapUser = ldapUserRepository.findByEmail(email);
            if(ldapUser == null) {
                throw  new UsernameNotFoundException("No User Found");
            }

            return new org.springframework.security.core.userdetails.User(
                    ldapUser.getEmail(),
                    ldapUser.getPassword(),
//                    ldapUser.getDescription(),
//                    passwordEncoder.encode(ldapUser.getPassword()),
//                    ldapUser.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(List.of( "USER"))
            );


        } else {
            User user = userRepository.findByeMailAddress(email);
            if(user == null) {
                throw  new UsernameNotFoundException("Неверный email или пароль");
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getEMailAddress(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(List.of((user.getKeycloakId())))
            );
        }



    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority>  authorities = new ArrayList<>();
        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
