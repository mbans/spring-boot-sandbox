package com.mbans.sandbox.springbootsandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableAutoConfiguration
@SpringBootApplication
public class SpringBootSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSandboxApplication.class, args);
	}
}
