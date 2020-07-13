package com.sc.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sc.security.auth.code.AuthCodeGenerator;
import com.sc.security.auth.code.image.ImageAuthCodeGenerator;
import com.sc.security.auth.code.sms.DefaultSmsAuthCodeSender;
import com.sc.security.auth.code.sms.SmsAuthCodeSender;

/**
 * security配置类
 * @author dy
 *
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public SmsAuthCodeSender smsAuthCodeSender() {
		return new DefaultSmsAuthCodeSender();
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "imageAuthCodeGenerator")
	public AuthCodeGenerator imageAuthCodeGenerator() {
		return new ImageAuthCodeGenerator();
	}

}
