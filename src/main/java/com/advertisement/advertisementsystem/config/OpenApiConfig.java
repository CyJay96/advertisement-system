package com.advertisement.advertisementsystem.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    private static final String TITLE = "Advertisement System";
    private static final String DESCRIPTION = "RESTful web-service that implements functionality " +
            "for working with advertisement system";
    private static final String VERSION = "1.0.0";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
