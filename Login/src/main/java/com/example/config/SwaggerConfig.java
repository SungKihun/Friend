package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.*;

// 설정클래스
@Configuration
// Swagger 설정
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public UiConfiguration uiConfig() {
		// UI를 커스터마이징하지 않고 기본 UI를 사용
		return UiConfiguration.DEFAULT;
	}
	
	// RESTAPI에 대한 기본적인 정보
	private ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("JPub Spring Boot")
				.description("Spring Boot Rest API")
				.version("1.0")
				.build();
	}
	
	// 실제 swagger문서가 참조할 API경로와 메타 정보를 포함해서 Docket 인스턴스 생성
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				// 모든 문서
//				.paths(PathSelectors.any())
				// URI가 /basic만 인식하도록 설정
				.paths(regex("/.*"))
				.build()
				
				.apiInfo(metadata());
	}
}
