package com.raccoon.prefsimnotary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String NOTARY_OFFICE_TAG = "Notary Office Service";
    public static final String TERM_TAG = "Term Service";
    public static final String USER_TAG = "User Service";
    public static final String NOTARY_TAG = "Notary Service";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.raccoon.prefsimnotary.controller"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(apiEndPointsInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .tags(new Tag(NOTARY_OFFICE_TAG, "Notary Office REST Endpoints"),
                        new Tag(TERM_TAG, " Term REST Endpoints"),
                        new Tag(USER_TAG, " User REST Endpoints"));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Prefsim Notary")
                .description("Prefsim Notary Api Documentation")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

}
