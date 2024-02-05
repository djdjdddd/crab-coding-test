package com.crabcodingtest.domain.signup.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class SignupRequestDTOTest {
    
    // ★ https://www.baeldung.com/java-validation 보고 틀린 부분 고쳐서 다시 테스트

    @Autowired
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void 사용자_이름_DTO_NotNull_체크() {
        //given
        SignupRequestDTO user = SignupRequestDTO.builder()
                .name(null)
                .mail("")
                .nickname(" ")
                .build();
        //when
        Set<ConstraintViolation<SignupRequestDTO>> violations = validator.validate(user);
        //then
        assertThat(violations.size()).isEqualTo(3);
    }

}