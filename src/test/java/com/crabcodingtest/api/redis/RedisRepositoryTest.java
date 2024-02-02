package com.crabcodingtest.api.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class RedisRepositoryTest {

    @Autowired
    private RedisRepository redisRepository;

    private Authentication authentication;

    @BeforeEach
    void setUp() {
        authentication = Authentication.of("111111", "yong");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("유저가 틀린 인증코드를 입력한 경우.")
    void test3(){
        // given
        Authentication save = redisRepository.save(authentication);// 1. 유저가 메일인증 버튼 클릭시 -> 인증코드 생성 후 Redis에 저장
        String userInputAuthCode = "333333"; // 2. 틀린 인증코드 입력했다고 가정

        // when
        Authentication find = redisRepository.findByAuthCode(userInputAuthCode);

        // then
        assertThat(find).isEqualTo(null);
    }

    @Test
    @DisplayName("기간이 만료된 인증코드를 입력한 경우.")
    void test2() throws InterruptedException {
        // given
        Authentication save = redisRepository.save(authentication);// 1. 유저가 메일인증 버튼 클릭시 -> 인증코드 생성 후 Redis에 저장
        String userInputAuthCode = "111111"; // 2. 유저가 인증코드를 제대로 입력했다고 가정

        // when
        Thread.sleep(6000);
        Authentication find = redisRepository.findByAuthCode(userInputAuthCode);

        // then
        assertThat(find).isEqualTo(null);
    }

    @Test
    @DisplayName("유저가 올바른 인증코드를 입력한 경우.")
    void test1(){
        // given
        Authentication save = redisRepository.save(authentication);// 1. 유저가 메일인증 버튼 클릭시 -> 인증코드 생성 후 Redis에 저장
        String userInputAuthCode = "111111"; // 2. 유저가 인증코드를 제대로 입력했다고 가정

        // when
        Authentication find = redisRepository.findByAuthCode(userInputAuthCode);

        // then
//        assertThat(find).isEqualTo(save); // 객체 주소값이 달라 테스트 실패 (@640890bc != @1b78c34b)
        assertThat(find.getId()).isEqualTo(save.getId());
    }

    @Test
    @DisplayName("Spring Data Redis를 이용하여 데이터를 저장/조회/수정 합니다.")
    void springDataRedisTest(){
        // 저장
        Authentication save = redisRepository.save(authentication);
        log.info("save = {}", save);

        // 조회
//        Authentication find = redisRepository.findByAuthCode("123456");
//        log.info("find = {}", find);

        // 수정
//        Authentication update = redisRepository.save(authentication);
//        log.info("update = {}", update);
    }

}