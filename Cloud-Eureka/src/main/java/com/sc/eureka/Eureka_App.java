package com.sc.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注冊中心
 * @author dy
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka_App {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Eureka_App.class, args);
	}
	
}
