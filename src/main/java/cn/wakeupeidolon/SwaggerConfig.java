package cn.wakeupeidolon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Wang Yu
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * UI页面显示信息
     */
    private static final String SWAGGER2_API_BASEPACKAGE = "cn.wakeupeidolon.controller";
    private static final String SWAGGER2_API_TITLE = "crawl management API";
    private static final String SWAGGER2_API_DESCRIPTION = "crawl Management";
    private static final String SWAGGER2_API_VERSION = "1.0";
    /**
     * createRestApi
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER2_API_BASEPACKAGE))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * apiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(SWAGGER2_API_TITLE)
                .description(SWAGGER2_API_DESCRIPTION)
                .version(SWAGGER2_API_VERSION)
                .build();
    
    }
}
