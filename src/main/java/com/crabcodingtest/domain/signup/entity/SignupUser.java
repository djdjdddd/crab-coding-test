package com.crabcodingtest.domain.signup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "signupUser")
@Getter
@Setter
@Builder // @AllArgsConstructor를 필요로 함.
@ToString
//@NoArgsConstructor // 에러남
@RequiredArgsConstructor
@AllArgsConstructor
public class SignupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String userPassword;
    private String userMail;
}
