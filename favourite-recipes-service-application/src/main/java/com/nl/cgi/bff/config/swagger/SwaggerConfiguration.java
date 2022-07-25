package com.nl.cgi.bff.config.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfiguration {
    @Bean
    public Docket dishComponent() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("verify the dish details")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nl.cgi.bff.web"))
                .paths(PathSelectors.ant("/dish-frontend-service/**"))
                .build().apiInfo(dishComponentInfo());
    }

    private ApiInfo dishComponentInfo() {
        return new ApiInfoBuilder().title("This controller used to verify the dishes")
                .description("This controller used to verify the dishes")
                .version("0.0.1")
                .build();
    }
}