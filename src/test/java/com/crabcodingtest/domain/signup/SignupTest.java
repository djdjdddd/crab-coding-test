package com.crabcodingtest.domain.signup;

import com.crabcodingtest.domain.signup.entity.SignupUser;
import com.crabcodingtest.domain.signup.repository.SignupUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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

    private SignupUser signupUser;

    @BeforeEach
    void before(){
        signupUser = SignupUser.builder()
                .userName("김용희")
                .userPassword("1234")
                .userMail("djdjdddd@khu.ac.kr")
                .build();
    }

    @Test
    @DisplayName("findByUserName 테스트")
    @Transactional(readOnly = true) // 성능향상 (실제로 재보진 않음 ㅎ..)
    void findByUserName(){
        String userName = signupUser.getUserName();
        SignupUser findUser = repository.findByUserName(userName);

        log.info("findUser={}", findUser);
    }

    @Test
    @DisplayName("save 테스트")
    @Transactional
    void saveTest(){
        // given
        SignupUser signupUser = SignupUser.builder()
                .userName("김용희")
                .userPassword("1234")
                .userMail("djdjdddd@khu.ac.kr")
                .build();

        // when
        SignupUser savedUser = repository.save(signupUser);
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
