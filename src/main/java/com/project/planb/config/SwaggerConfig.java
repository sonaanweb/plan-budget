package com.project.planb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PlanB API 명세서")
                        .version("1.0.0")
                        .description("사용자 예산 지출 관리 프로젝트 플랜비 API 명세서"));
    }
}
