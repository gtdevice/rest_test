package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.test")
public class DguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DguardApplication.class, args);
	}

}
