package com.fatehole.servicebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * Swagger API文档相关配置
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/08/22:51
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {

    /**
     * 配置了swagger的Docket的bean实例
     */
    @Bean
    public Docket docket(Environment environment) {

        // 设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev", "test");
        // 通过environment.acceptsProfiles监听环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .pathMapping("/")
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(flag)

                // 将api的元信息设置为包含在json ResourceListing响应中
                .apiInfo(apiInfo())

                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fatehole"))
                // 暂时全部扫描
                // .paths(PathSelectors.any())
                .build();
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Lemon 🍋 教育后台系统 Api Doc")
                .description("十目所视，十指所指! 本文档描述了课程中心微服务接口定义")
                .contact(new Contact("helaos", "https://www.fatehole.com", "cifanse@outlook.com"))
                .version("v1.0")
                .build();
    }
}
