package com.sc.security.authentication.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import com.sc.security.properties.SecurityProperties;

/**
 * @Title SocialConfig
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		repository.setTablePrefix("sys_");
		if(connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}

	@Bean
	public SpringSocialConfigurer mySocialSecurityConfig() {
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);
		configurer.signupUrl("/signUp.html");
		return configurer;
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}
	
}
