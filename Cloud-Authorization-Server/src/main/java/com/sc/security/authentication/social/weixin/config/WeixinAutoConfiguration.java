package com.sc.security.authentication.social.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.web.servlet.View;

import com.sc.security.authentication.social.MyConnectView;
import com.sc.security.authentication.social.weixin.connect.WeixinConnectionFactory;
import com.sc.security.properties.SecurityProperties;
import com.sc.security.properties.WeixinProperties;

/**
 * @Title WeixinAutoConfiguration
 * @Description
 * @author dy
 * @date 2019年12月5日
 */
@Configuration
@ConditionalOnProperty(prefix = "com.bms.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		connectionFactoryConfigurer.addConnectionFactory(new WeixinConnectionFactory(weixinConfig.getProviderId(),
				weixinConfig.getAppId(), weixinConfig.getAppSecret()));
	}

	@Bean({ "connect/weixinConnect", "connect/weixinConnected" })
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new MyConnectView();
	}

}
