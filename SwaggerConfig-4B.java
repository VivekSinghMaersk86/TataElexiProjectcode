package com.tatasky.mcr.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author vikaschoudhary
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter{
	
	@Value("${swagger.api.name}")
	private String apiName;
	
	@Value("${swagger.api.desc}")
	private String apiDesc;
	
	@Value("${swagger.api.terms.of.service}")
	private String tremsOfService;
	
	@Value("${swagger.api.licence.url}")
	private String licenceURL;
	

	@Value("${swagger.api.contact.name}")
	private String contactName;
	
	@Value("${swagger.api.contact.email}")
	private String contactEmail;
	
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	    		apiName,
	    		apiDesc,
	      "1.0",
	      tremsOfService,
	      new Contact(contactName, "", contactEmail),
	      "License of API",
	      licenceURL,
	      Collections.emptyList());
	}
	
	 @Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {
	       registry.addResourceHandler("swagger-ui.html")
	       .addResourceLocations("classpath:/META-INF/resources/");

	       registry.addResourceHandler("/webjars/**")
	       .addResourceLocations("classpath:/META-INF/resources/webjars/");
	   }
}
