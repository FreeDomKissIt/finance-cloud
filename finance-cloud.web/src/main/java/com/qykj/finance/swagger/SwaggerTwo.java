package com.qykj.finance.swagger;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 *  Swagger2配置类
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
//@Configuration //生产库注释API地址
//@EnableSwagger2
public class SwaggerTwo {
	/**
	 * API文档 访问路径 /swagger-ui.html
	 * 
	 * @return
	 */
	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.qykj.finance.web.action"))// action路径
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("finance-web.API").termsOfServiceUrl("http://qykj.site")
				.description("前后端交互API文档")
				.contact(new Contact("qykj", "http://qykj.site", "240076915@qq.com")).build();
	}

}
