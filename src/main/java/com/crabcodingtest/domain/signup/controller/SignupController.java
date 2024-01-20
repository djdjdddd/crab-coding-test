package com.crabcodingtest.domain.signup.controller;

import com.crabcodingtest.api.mail.MailDTO;
import com.crabcodingtest.api.mail.MailService;
import com.crabcodingtest.domain.signup.service.SignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignupController {

    private final SignupService signupService;

    @RequestMapping("/mail-test")
    public void mailTest(){
        log.info("mail-test 요청 들어옴");

        MailDTO mailDTO = new MailDTO();
        mailDTO.setUserName("김용희");
//        mailDTO.setUserMail("jisu3148496@naver.com");
        mailDTO.setUserMail("djdjdddd@khu.ac.kr");
//        mailDTO.setUserMail("hjmin1221@naver.com");

        //
        signupService.sendAuthCodeMail(mailDTO);
    }

}
