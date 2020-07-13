package com.sc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.sc.security.properties.SecurityConstants;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
public class Application_Authorization_Server {
	
	public static void main(String[] args) {
		System.setProperty("jasypt.encryptor.password", SecurityConstants.JASYPT_ENCRYPTOR_PASSWORD);
		SpringApplication.run(Application_Authorization_Server.class, args);
	}
	
}
