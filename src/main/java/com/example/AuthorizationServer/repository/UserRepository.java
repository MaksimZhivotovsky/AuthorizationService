package com.example.AuthorizationServer.repository;

import com.example.AuthorizationServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByeMailAddress(String eMailAddress);
}
