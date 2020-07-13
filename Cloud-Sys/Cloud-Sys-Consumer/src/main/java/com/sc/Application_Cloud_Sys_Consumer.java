package com.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
public class Application_Cloud_Sys_Consumer {

	public static void main(String[] args) {
		SpringApplication.run(Application_Cloud_Sys_Consumer.class, args);
	}
	
	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

}
