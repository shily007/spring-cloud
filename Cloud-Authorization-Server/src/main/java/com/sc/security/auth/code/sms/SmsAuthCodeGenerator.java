package com.sc.security.auth.code.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.sc.security.auth.code.AuthCode;
import com.sc.security.auth.code.AuthCodeGenerator;
import com.sc.security.auth.code.AuthCodeType;
import com.sc.security.properties.SecurityProperties;

/**
 * 短信验证码生成器
 * 
 * @author dy
 *
 */
@Component("smsAuthCodeGenerator")
public class SmsAuthCodeGenerator implements AuthCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public AuthCode generate(ServletWebRequest request) {
		return new AuthCode(AuthCodeType.SMS,
				RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength()),
				securityProperties.getCode().getSms().getExpireIn());
	}

}
