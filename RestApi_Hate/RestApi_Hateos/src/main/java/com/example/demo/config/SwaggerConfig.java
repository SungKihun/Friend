package com.example.demo.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private int timeout = 5000;
	
	@Bean
	public UiConfiguration uiConfig() {
		return UiConfiguration.DEFAULT;
	}
	
	private ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("JPub Spring Boot")
				.description("Spring Boot Rest API")
				.version("1.0")
				.build();
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("basic")
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(regex("/basic/.*"))
				.build()
				
				.apiInfo(metadata());
	}
	
	@SuppressWarnings("unused")
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
				new HttpComponentsClientHttpRequestFactory();
		
		clientHttpRequestFactory.setConnectionRequestTimeout(timeout);
		clientHttpRequestFactory.setReadTimeout(timeout);
		return clientHttpRequestFactory;
	}
	
	@SuppressWarnings("unused")
	private ClientHttpRequestFactory getRestConfigHttpRequestFactory() {
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout)
				.build();
		
		CloseableHttpClient httpClient = HttpClientBuilder
				.create()
				.setDefaultRequestConfig(config)
				.build();
		
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
}
