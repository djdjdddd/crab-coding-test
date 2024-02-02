package com.crabcodingtest.api.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

@Slf4j
@SpringBootTest
public class RedisServiceTest_v2 {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    static final String KEY = "용희";
    static final String VALUE = "5895";
    static final Duration DURATION = Duration.ofSeconds(5);

    @Test
    @DisplayName("StringRedisTemplate을 사용하여 Redis에 데이터가 제대로 저장되는지 확인합니다.")
    void stringRedisTemplateTest() throws InterruptedException {
        ValueOperations<String, String> values = stringRedisTemplate.opsForValue();
        values.set(KEY, VALUE, DURATION);

        String value = values.get(KEY);
        log.info("value={}", value);

        Thread.sleep(6000);
        String value2 = values.get(KEY);
        log.info("value2={}", value2);
    }
}
