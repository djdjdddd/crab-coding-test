package com.crabcodingtest.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : yong
 * @fileName : MvcConfiguration
 * @date : 2024-02-19
 * @description :
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    // CORS 설정은 각 환경에 따라 허용하는 Origin 도메인을 적용할 수 있도록 프로퍼티에서 읽어오도록 구현했습니다.
    // https://github.com/changheedev/spring-security-jwt-social/blob/master/docs/Security.md

    @Value("${client.origins}")
    private String[] allowedOrigins;

    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(
                        HttpMethod.GET.name()
                        ,HttpMethod.HEAD.name()
                        ,HttpMethod.POST.name()
                        ,HttpMethod.PUT.name()
                        ,HttpMethod.DELETE.name()
                )
                .maxAge(3600)
                .allowCredentials(true);
    }

}
