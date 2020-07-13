package com.sc.eureka;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
	        .anyRequest()
	        .authenticated()
	        .and()
        .httpBasic()
        	.and()
        .csrf().disable();//禁用CSRF会将微服务的注册也给过滤了，虽然不会影响注册中心，但是其他微服务是注册不了的。
	}

}
