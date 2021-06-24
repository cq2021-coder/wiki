package com.cq.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2//开启swagger2
public class SwaggerConfig {
    //配置了Swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("程崎")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cq.wiki.controller"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("程崎", "https://gitee.com/cq2021/wiki.git", "2972084238@qq.com");

        return new ApiInfo(
                "wiki",
                          "将一系列文件分类成视频，图片等",
                "cq&&hwh",
                "https://gitee.com/cq2021/wiki.git",
                contact,
                "仓库地址",
                "https://gitee.com/cq2021/wiki.git",
                new ArrayList());
    }

    private List<SecurityScheme> securitySchemes(){
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("token","token","header"));
        return securitySchemes;
    }

    private List<SecurityContext> securityContexts(){
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!auth).*$")).build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("token",authorizationScopes));
        return securityReferences;
    }
}
