package com.crabcodingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CrabCodingTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrabCodingTestApplication.class, args);
    }

}
