package com.crabcodingtest.common.security;

import com.crabcodingtest.domain.signup.entity.User;
import com.crabcodingtest.domain.signup.repository.UserRepository;
import com.crabcodingtest.domain.signup.service.SignupService;
import com.crabcodingtest.domain.signup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author : yong
 * @fileName : MyUserDetailService
 * @date : 2024-02-18
 * @description :
 */
@Component
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 1. userRepository 대신 userService 써야 할 듯
        //
        Optional<User> optionalUser = userService.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

        // 1. User가 내가 만든 Entity가 아니라 Security꺼네..
        // 2. authorities 부분 맞는지 체크 필요
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
//                .authorities(user.getAuthorities()) // TODO. 에러 해결 필요... failed to lazily initialize a collection of role: com.crabcodingtest.domain.signup.entity.User.authorities: could not initialize proxy - no Session
                                                      // 에러 해결 레퍼런스 : https://www.inflearn.com/questions/33949/failed-to-lazily-initialize-a-collection-of-role-%EC%98%A4%EB%A5%98-%EA%B4%80%EB%A0%A8-%EB%AC%B8%EC%9D%98
                .build();
    }
}
