package com.sc.security.authentication.social.qq.connet;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.sc.security.authentication.social.qq.api.QQ;

/**
 * @Title QQConnectionFactory
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
