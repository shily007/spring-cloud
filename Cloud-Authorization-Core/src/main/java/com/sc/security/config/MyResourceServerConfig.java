package com.sc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.sc.security.properties.SecurityConstants;

/**
 * 资源服务器
 * 
 * @author dy
 *
 */
@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler myAuthenticationFailureHandler;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginProcessingUrl("/authentication/form").successHandler(myAuthenticationSuccessHandler)
				.failureHandler(myAuthenticationFailureHandler);
		http.authorizeRequests()
				// 不需要验证的页面
				.antMatchers("/code/*", "/swagger-ui.html", "/**/*.css", "/**/*.js", "/**/*.css?*", "**.js?*",
						"/**/*.html", "/swagger*/**", "/webjars/**", "/v2/**", "/file/**", "/show/**", "/publicKey",
						"/validateCode/**", "/signUp.html", SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
						"/qqLogin/**", SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_EMAIL)
				.permitAll().anyRequest().authenticated().and().csrf().disable();

	}

}
