package com.crabcodingtest.domain.signup.repository;

import com.crabcodingtest.domain.signup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // CRUD랑 JPA 차이??
    User findByName(String userName);
    Optional<User> findByMail(String mail);
}
