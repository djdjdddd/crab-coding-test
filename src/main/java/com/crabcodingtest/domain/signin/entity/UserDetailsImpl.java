package com.crabcodingtest.domain.signin.entity;

import com.crabcodingtest.common.AuthorityType;
import com.crabcodingtest.common.BaseEntity;
import com.crabcodingtest.common.UserType;
import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : yong
 * @fileName : User
 * @date : 2024-02-19
 * @description : UserDetails 구현체이자 엔티티. 회원가입 도메인의 User 와 다르게 구현해보았다.
 * @see com.crabcodingtest.domain.signup.entity.User
 */
public class UserDetailsImpl extends BaseEntity implements org.springframework.security.core.userdetails.UserDetails {
    
    // UserDetails 는 Spring Security 가 사용자의 인증을 처리하는데 필요한 사용자 정보를 제공하는
    // 사용자의 정보(이름, 비밀번호, 권한, 계정 만료, 비밀번호 만료, 계정 잠금 등)를 담고 있는 인터페이스.

    private String name;

    private String mail;

    private String username;

    private String password;

    private String phoneNo;

    private String address;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserDetailsImpl(String name, String password, String mail, String username, String phoneNo, String address, UserType type, Collection<? extends GrantedAuthority> authorities){
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.username = username;
        this.phoneNo = phoneNo;
        this.address = address;
        this.authorities = authorities;
        this.type = type;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //참조 : https://chordplaylist.tistory.com/307
//        return Collections.singletonList(new SimpleGrantedAuthority(userStatus.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //TODO. 단순히 true, false 가 아니라 UserStatus 로부터 얻은 값을 이용하도록
        //참조 : https://chordplaylist.tistory.com/307
        //return UserStatus.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return UserStatus.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserStatus.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        //return UserStatus.isEnabled();
        return true;
    }
}
