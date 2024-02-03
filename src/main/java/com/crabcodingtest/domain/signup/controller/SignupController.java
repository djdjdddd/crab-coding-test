package com.crabcodingtest.domain.signup.controller;

import com.crabcodingtest.api.mail.MailDTO;
import com.crabcodingtest.api.mail.MailSender;
import com.crabcodingtest.api.mail.MailService;
import com.crabcodingtest.domain.signup.service.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입", description = "회원가입 API") // swagger 적용 위한 어노테이션
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/mail-test")
    @Operation(summary = "메일 인증", description = "회원가입시 필요한 메일 인증을 요청합니다. 요청시 인증 코드가 발송됩니다.")
    public void mailTest(@Parameter MailDTO mailDTO){
        log.info("mail-test 요청 들어옴");

        MailDTO testDTO = new MailDTO();
        testDTO.setUserName("김용희");
        testDTO.setUserMail("djdjdddd@khu.ac.kr");

        signupService.sendAuthCodeMail(testDTO);
    }

}
