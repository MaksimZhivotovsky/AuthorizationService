package com.example.AuthorizationServer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.AuthorizationServer.repository.LdapUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.ldap.LdapContext;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
//    private final PasswordEncoder passwordEncoder;
    private final LdapUserRepository ldapUserRepository;

    @Autowired
    private Environment env;

    @Value("${isLDAP}")
    private Boolean isLDAP;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CustomAuthenticationProvider authenticate start");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = customUserDetailsService.loadUserByUsername(username);
        return checkPassword(user,password);
    }

    private Authentication checkPassword(UserDetails user, String rawPassword) {

        log.info("CustomAuthenticationProvider checkPassword start");

        if (Boolean.TRUE.equals(isLDAP)) {
            try {
                ldapUserRepository.authenticate(user.getUsername(), rawPassword);
                return new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        rawPassword,
                        user.getAuthorities()
                );
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new BadCredentialsException("Неверный email или пароль");
            }

        } else {
//            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            if(rawPassword.equals(user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                );
            } else {
                throw new BadCredentialsException("Неверный email или пароль");
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
