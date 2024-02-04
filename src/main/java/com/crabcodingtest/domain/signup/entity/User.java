package com.crabcodingtest.domain.signup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter @Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    // 이름
    // 이메일
    // 비밀번호
    // 비밀번호 재입력 (이건 프론트단에서 끝내자)
    // 닉네임
    // 핸드폰 번호
    // 주소 (API 쓰기로 했었으니까)
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String password;
    private String mail;
    private String nickname;
    private String phoneNo;
}
