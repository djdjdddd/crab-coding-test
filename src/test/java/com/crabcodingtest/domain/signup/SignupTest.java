package com.crabcodingtest.domain.signup;

import com.crabcodingtest.domain.signup.entity.User;
import com.crabcodingtest.domain.signup.repository.SignupUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class SignupTest {
    
    @Autowired
    private SignupUserRepository repository;

    private User signupUser;

    @BeforeEach
    void before(){
        signupUser = User.builder()
                .name("김용희")
                .password("1234")
                .mail("djdjdddd@khu.ac.kr")
                .build();
    }

    @Test
    @DisplayName("findByUserName 테스트")
    @Transactional(readOnly = true) // 성능향상 (실제로 재보진 않음 ㅎ..)
    void findByUserName(){
        String name = signupUser.getName();
        User findUser = repository.findByName(name);

        log.info("findUser={}", findUser);
    }

    @Test
    @DisplayName("save 테스트")
    @Transactional
    void saveTest(){
        // given
        User signupUser = User.builder()
                .name("김용희")
                .password("1234")
                .mail("djdjdddd@khu.ac.kr")
                .build();

        // when
        User savedUser = repository.save(signupUser);
        log.info("signupUser={}", signupUser);
        log.info("savedUser={}", savedUser);

        // then
        assertThat(savedUser).isEqualTo(signupUser);
    }
    
    @Test
    @DisplayName("MySQL이 제대로 연결됐는지 테스트합니다.")
    void connectionTest(){
        long count = repository.count();
        log.info("count={}",count);
    }
}
