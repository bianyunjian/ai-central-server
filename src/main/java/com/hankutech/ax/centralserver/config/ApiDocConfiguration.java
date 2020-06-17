package com.hankutech.ax.centralserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
public class ApiDocConfiguration {
}
