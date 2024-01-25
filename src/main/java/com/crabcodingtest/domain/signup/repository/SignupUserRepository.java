package com.crabcodingtest.domain.signup.repository;

import com.crabcodingtest.domain.signup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignupUserRepository extends JpaRepository<User, Long> { // CRUD랑 JPA 차이가 뭐더라??

    User findByUserName(String userName);
}
