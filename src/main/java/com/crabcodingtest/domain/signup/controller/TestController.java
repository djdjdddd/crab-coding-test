package com.crabcodingtest.domain.signup.controller;

import com.crabcodingtest.domain.signup.request.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/front-to-back")
    public TestDTO frontToBack(@RequestBody TestDTO testDto){
        log.info("testDTO = {}", testDto);
        log.info("testDto.getTestEnum() = {}", testDto.getTestEnum());
        log.info("testDto.getTestEnumList() = {}", testDto.getTestEnumList());
        return testDto;
    }

    @GetMapping("/back-to-front")
    public TestDTO backToFront(){
        return TestDTO.builder()
            .testEnum(TestDTO.TestEnum.TEST)
            .testEnumList(List.of(TestDTO.TestEnum.SUCCESS, TestDTO.TestEnum.FAIL))
            .build();
    }
}
