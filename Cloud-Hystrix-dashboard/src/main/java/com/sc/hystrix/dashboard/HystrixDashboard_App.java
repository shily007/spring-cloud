package com.sc.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
@EnableEurekaClient
@EnableDiscoveryClient
public class HystrixDashboard_App  extends WebSecurityConfigurerAdapter {
	 
	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboard_App.class, args);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login", "/client/**", "/actuator/**", "/hystrix/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.csrf()
			.disable();
	}
	
}
