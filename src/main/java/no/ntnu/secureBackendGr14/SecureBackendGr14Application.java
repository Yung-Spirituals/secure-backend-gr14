package no.ntnu.secureBackendGr14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@SpringBootApplication
public class SecureBackendGr14Application {

	public static void main(String[] args)
	{
		SpringApplication.run(SecureBackendGr14Application.class, args);
	}

	@Bean
	public Docket swaggerConfig()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("no.ntnu"))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo()
	{
		return new ApiInfo(
				"Hiking backend API",
				"Backend API for group fourteen's web application",
				"1.0",
				"https://ntnu.no",
				new springfox.documentation.service.Contact(
						"Group 14", "https://gr14.appdev.cloudns.ph/", "TBD@placeholder.no"),
				"TBD",
				"https://ntnu.no",
				Collections.emptyList()
		);
	}
}
