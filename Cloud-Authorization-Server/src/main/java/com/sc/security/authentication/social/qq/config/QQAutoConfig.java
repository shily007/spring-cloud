package com.sc.security.authentication.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

import com.sc.security.authentication.social.qq.connet.QQConnectionFactory;
import com.sc.security.properties.QQProperties;
import com.sc.security.properties.SecurityProperties;

/**
 * @Title QQAutoConfig
 * @Description
 * @author dy
 * @date 2019年12月5日
 */
@Configuration
@ConditionalOnProperty(prefix = "com.bms.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		QQProperties qqConfig = securityProperties.getSocial().getQq();
		connectionFactoryConfigurer.addConnectionFactory(
				new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret()));
	}

}
