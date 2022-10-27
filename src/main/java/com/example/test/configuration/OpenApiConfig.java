package com.example.test.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI()
	{
		Info info=new Info().title("REST API")
				.version("1.0.0")
				.contact(new Contact().name("Oleg").email("gtdevice@gmail.com"))
				.license(new License().name("Some SUper License"))
				.description("JSON REST-API that allows a User to save and retrieve Superheroes.");
		info.addExtension("x-api-category", "ENTERPRISE");
		info.addExtension("x-api-application", "TR");

		List<Server> serverList=new ArrayList<>();
		serverList.add(new Server().url("http://localhost:8081").description("Test"));

		return new OpenAPI()
				.components(new Components())
				.info(info)
				.servers(serverList);
	}
}
