package com.crabcodingtest.domain.signup.repository;

import com.crabcodingtest.domain.signup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // CRUD랑 JPA 차이??

    User findByName(String userName);

    // 같은 이메일로 가입된 회원이 존재하는지 검사하는 메서드 1
    Optional<User> findByMail(String mail);

    // 같은 이메일로 가입된 회원이 존재하는지 검사하는 메서드 2
    boolean existsByMail(String mail);
    
    // username 으로 조회
    // MyUserDetailService 에서 사용
    Optional<User> findByUsername(String username);
}
