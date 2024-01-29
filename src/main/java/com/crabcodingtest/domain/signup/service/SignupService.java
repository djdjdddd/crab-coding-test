package com.crabcodingtest.domain.signup.service;

import com.crabcodingtest.api.mail.MailAuthenticationMessage;
import com.crabcodingtest.api.mail.MailDTO;
import com.crabcodingtest.api.mail.MailService;
import com.crabcodingtest.api.redis.Authentication;
import com.crabcodingtest.api.redis.RedisRepository;
import com.crabcodingtest.api.redis.RedisService;
import com.crabcodingtest.domain.signup.request.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignupService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

//    private final MemberRepository memberRepository;

    private final MailService mailService;
    private final RedisService redisService;
    private final RedisRepository repository; // Spring Data의 장점

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    // 메일 인증 성공/실패 여부 체크
    public MailAuthenticationMessage test(UserRequestDTO userRequestDTO){
        String authCode = userRequestDTO.getAuthCode();

        Authentication find = repository.findByAuthCode(authCode);

        // **조건문이 뭔가 맘에 안듦.
        if (find == null) {
            log.info("인증코드 잘못 입력했거나 유효기간 만료");
            return MailAuthenticationMessage.FAIL;
        }else if(!find.getMail().equals(userRequestDTO.getMail())){
            log.info("인증코드가 중복된 아주 아주 드문 경우");
            return MailAuthenticationMessage.FAIL;
        }else{
            log.info("인증완료");
            return MailAuthenticationMessage.SUCCESS;
        }
    }

    public void sendAuthCodeMail(MailDTO mailDTO) {
        // 0. 메일 중복 체크
//        checkDuplicatedEmail(toEmail);

        // 1. 인증코드 생성
//        String title = "Travel with me 이메일 인증 번호";
        String authCode = createCode();
        log.info("authCode = {}", authCode);
        mailDTO.setAuthCode(authCode);

        // 2. 메일 전송
        mailService.sendMail(mailDTO);

        // 3. 인증코드 Redis에 저장
        // (1) RedisTemplate 사용한 버전
//        redisService.setValues(AUTH_CODE_PREFIX + mailDTO.getUserMail(), authCode, Duration.ofMillis(this.authCodeExpirationMillis));

        // (2) CrudRepository 사용한 버전
        repository.save(Authentication.of(authCode, mailDTO.getUserMail()));
    }

//    private void checkDuplicatedEmail(String email) {
//        Optional<Member> member = memberRepository.findByEmail(email);
//        if (member.isPresent()) {
//            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
//            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
//        }
//    }

    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            log.error("error", e);
//            throw new BusinessLogicException(ExceptionCode.NO_SUCH_ALGORITHM);
            throw new RuntimeException(e);
        }
    }

//    public EmailVerificationResult verifiedCode(String email, String authCode) {
//        this.checkDuplicatedEmail(email);
//        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
//        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
//
//        return EmailVerificationResult.of(authResult);
//    }
}
