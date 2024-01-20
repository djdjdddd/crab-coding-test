package com.crabcodingtest.domain.signup.repository;

import com.crabcodingtest.domain.signup.entity.SignupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SignupUserRepository extends JpaRepository<SignupUser, Long> { // CRUD랑 JPA 차이가 뭐더라??
    SignupUser findByUserName(String userName);
}
