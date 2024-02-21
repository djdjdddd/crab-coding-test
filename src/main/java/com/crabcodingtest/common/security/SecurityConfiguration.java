package com.crabcodingtest.common.security;

import com.crabcodingtest.common.filter.StatelessCSRFFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * @author : yong
 * @fileName : SecurityConfiguration
 * @date : 2024-02-18
 * @description :
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // We’ll add method security annotations to enable processing based on different roles.
@RequiredArgsConstructor
public class SecurityConfiguration {

    // 특이사항 (스프링 부트 3.2.1 버전, 스프링 시큐리티 6.2.1 버전)
    // 레퍼런스(스프링 부트 2.2.1 버전, 스프링 시큐리티 5.2.1 버전)와 비교하여 크게 2가지 다른 점이 존재합니다.
    // 1. extends WebSecurityConfigurerAdapter 삭제
    // 2. 기존 builder 패턴의 세부 설정 방식이 lambda expression 방식으로 변경
    // csrf().disable() -> csrf(configurer -> configurer.disable())

    private final MyUserDetailService myUserDetailService;

    // config
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // SimplePasswordEncoder 도 있는데 왜 이걸 쓰는지??
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Reference
        // 1. 우선 이 블로그 보고 따라 함. https://nahwasa.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-30%EC%9D%B4%EC%83%81-Spring-Security-%EA%B8%B0%EB%B3%B8-%EC%84%B8%ED%8C%85-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0#3._%EB%A1%9C%EA%B7%B8%EC%9D%B8_%EC%95%88%ED%96%88%EC%9C%BC%EB%A9%B4_%EB%A1%9C%EA%B7%B8%EC%9D%B8_%ED%99%94%EB%A9%B4%EC%9C%BC%EB%A1%9C_%EB%B3%B4%EB%82%B4%EC%A4%98!
        // 2. https://github.com/changheedev/spring-security-jwt-social/blob/master/docs/Security.md
        // 3. https://velog.io/@woosim34/Spring-Security-6.1.0%EC%97%90%EC%84%9C-is-deprecated-and-marked-for-removal-%EC%98%A4%EB%A5%98
        // 4. 밸덩 - https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter

        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                    request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                            .requestMatchers("/signup/**", "/css/**").permitAll() // 인증 예외 URL 설정 (e.g. 회원가입)
                            .requestMatchers(HttpMethod.POST, "/authorize", "signin").anonymous()   // 로그인 API 를 미인증 상태에서만 호출할 수 있도록 anonymous() 로 추가합니다 (??)
                            .anyRequest().authenticated()   // 어떠한 요청이라도 인증필요
                )
                .formLogin(login ->                         // form 방식 로그인 사용
                        login.defaultSuccessUrl("/")
                                .permitAll())               //
                .logout(Customizer.withDefaults())          // 로그아웃은 기본설정으로 (/logout 으로 인증해제)
                .userDetailsService(myUserDetailService);

        // CSRF 필터 설정
        http.addFilterBefore(new StatelessCSRFFilter(), CsrfFilter.class);
//        http.addFilterBefore(new CsrfFilter());

        return http.build();
    }

}
