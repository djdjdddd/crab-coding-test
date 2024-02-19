package com.crabcodingtest.domain.signin.service;

import com.crabcodingtest.domain.signin.entity.UserDetailsImpl;
import com.crabcodingtest.domain.signup.entity.User;
import com.crabcodingtest.domain.signup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author : yong
 * @fileName : UserDetailsServiceImpl
 * @date : 2024-02-19
 * @description : 이미 common 에 만들긴 했지만, 공부용으로 임시로 만든 클래스.
 * @see com.crabcodingtest.common.security.MyUserDetailService
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. DB 로부터 User 객체(정보) 얻기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 회원입니다."));

        // ★잊지 말라고 다시 설명
        // UserDetails 는 Spring Security 가 사용자의 인증을 처리하는데 필요한 사용자 정보를 제공하는
        // 사용자의 정보(이름, 비밀번호, 권한, 계정 만료, 비밀번호 만료, 계정 잠금 등)를 담고 있는 인터페이스.

        // 2. 해당 User 객체(정보)를 이용하여 UserDetails 객체 생성
        return UserDetailsImpl.builder()
                .name(user.getName())
                .mail(user.getMail())
                .username(user.getUsername())
                .password(user.getPassword())
                .phoneNo(user.getPhoneNo())
                .address(user.getAddress())
                .type(user.getType())
                .authorities(user.getAuthorities())
                .build();
    }
}
