package com.example.icebutler_server.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_DOCS_TITLE = "IceButler_SERVER API";
    private static final String API_DOCS_DESCRIPTION = "냉집사 서버 API";
    private static final String API_DOCS_VERSION = "2.0.0";
    private static final String Bearer = "Bearer";
    private static final String Authorization = "Authorization";
    private static final String JWT = "JWT";

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList(Bearer);
        Components components = new Components().addSecuritySchemes(Bearer,
                new SecurityScheme()
                        .name(Authorization)
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .scheme(Bearer)
                        .bearerFormat(JWT));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title(API_DOCS_TITLE)
                .description(API_DOCS_DESCRIPTION)
                .version(API_DOCS_VERSION);
    }

}