package com.crabcodingtest.domain.signup.request;

import com.crabcodingtest.domain.signup.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequestDTO {

    @NotBlank
    @Size(max = 20, message = "너무 긴 이름입니다. (최대 20자)")
    private String name;                // 이름

    @NotBlank
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String mail;                // 이메일

    //@NotBlank
    private String nickname;            // 닉네임

    // TODO. 개발 완료되면 {1,20} ---> {8,20} 으로 바꾸기
    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{1,20}", message = "1~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")
    private String password;            // 비밀번호

    //@NotBlank
    private String phoneNo;             // 핸드폰 번호

    //@NotBlank
    private String address;             // 주소

    private String authenticationCode;  // 메일 인증코드

    @Builder
    public SignupRequestDTO(String name, String password, String mail, String nickname, String phoneNo, String address){
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.nickname = nickname;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    // TODO. 리팩토링 필요
    public User toEntity(){
        return User.builder()
                .name(name)
                .password(password)
                .mail(mail)
                .nickname(nickname)
                .phoneNo(phoneNo)
                .address(address)
                .build();
    }
}
