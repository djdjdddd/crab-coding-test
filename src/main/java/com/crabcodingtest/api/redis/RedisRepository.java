package com.crabcodingtest.api.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<Authentication, String> {

    // 유저가 메일로 받은 인증코드를 입력하면 ->
    Authentication findByAuthCode(String authCode);

}