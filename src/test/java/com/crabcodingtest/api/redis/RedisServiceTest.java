package com.crabcodingtest.api.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
@SpringBootTest
public class RedisServiceTest {

    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    @Autowired
    private RedisService redisService;

    @BeforeEach
    void setValueBeforeTest(){
        redisService.setValues(KEY, VALUE, DURATION);
    }

    @AfterEach
    void deleteValueAfterTest(){
        redisService.deleteValues(KEY);
    }

    @Test
    @DisplayName("Redis에 데이터를 저장하면 정상적으로 조회된다.")
    void saveAndFindTest(){
        // when
        Optional<String> findValue = redisService.getValues(KEY);

        // then
        assertThat(VALUE).isEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 수정할 수 있다.")
    void updateTest(){
        // given
        String updateValue = "updateValue";
        redisService.setValues(KEY, updateValue, DURATION);

        // when
        Optional<String> findValue = redisService.getValues(KEY);

        // then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 삭제할 수 있다.")
    void deleteTest(){
        // when
        redisService.deleteValues(KEY);
        Optional<String> findValue = redisService.getValues(KEY);

        // then
        assertThat(findValue).isEqualTo("false");
    }

    @Test
    @DisplayName("Redis에 저장된 데이터는 만료시간이 지나면 삭제된다.")
    void expiredTest(){
        // when
        Optional<String> findValue = redisService.getValues(KEY);

        // 이 테스트는 제대로 작동 안하는거 같은데...
        await().pollDelay(Duration.ofMillis(1000)).untilAsserted(
                () -> {
                    Optional<String> expiredValue = redisService.getValues(KEY);
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo("false");
                }
        );
    }
}
