package com.crabcodingtest.api.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 참조 : https://velog.io/@najiexx/Spring-Boot-3%EC%97%90-Swagger-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0springdoc-openapi
@Configuration
public class SwaggerConfiguration {

    // 클래스에 @ 주는 거랑 똑같은 기능인 듯??
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("꽃게코딩")
                        .description("description 기입하는 곳")
                        .version("1.0.0")
                );
    }
}
