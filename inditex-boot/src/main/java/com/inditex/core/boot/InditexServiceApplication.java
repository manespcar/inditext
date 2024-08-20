package com.inditex.core.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.inditex.core")
public class InditexServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InditexServiceApplication.class, args);
	}

}
