package com.tatasky.mcr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author vikaschoudhary
 *
 */

@SpringBootApplication(scanBasePackages = { "com.tatasky.mcr" })
@EnableSwagger2
public class FaultMcrApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FaultMcrApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FaultMcrApplication.class);
	}
}
