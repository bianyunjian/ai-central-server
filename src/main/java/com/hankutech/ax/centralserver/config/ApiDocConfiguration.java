package com.hankutech.ax.centralserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * API文档
 *
 * @author ZhangXi
 */
@OpenAPIDefinition(
        info = @Info(
                title = "AX CENTRAL SERVER API",
                version = "v0.0.1",
                description = "爱信智慧医疗中心服务"
        )
)
@Configuration
@SecurityScheme(
        name = ApiDocConfiguration.AUTH_NAME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = ApiDocConfiguration.AUTH_BEARER_FORMAT,
        scheme = ApiDocConfiguration.AUTH_SCHEMA
)
public class ApiDocConfiguration {
    public static final String AUTH_NAME = "bearerAuth";
    public static final String AUTH_BEARER_FORMAT = "JWT";
    public static final String AUTH_SCHEMA = "bearer";
}
