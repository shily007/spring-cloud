package com.sc.security.auth.code.email;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.sc.security.auth.code.AuthCode;
import com.sc.security.auth.code.AuthCodeGenerator;
import com.sc.security.auth.code.AuthCodeType;
import com.sc.security.properties.SecurityProperties;

/**
 * @Title EmailAuthCodeGenerator
 * @Description 邮箱证码生成器
 * @author dy
 * @date 2019年11月26日
 */
@Component("emailAuthCodeGenerator")
public class EmailAuthCodeGenerator implements AuthCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public AuthCode generate(ServletWebRequest request) {
		return new AuthCode(AuthCodeType.EMAIL,
				RandomStringUtils.randomNumeric(securityProperties.getCode().getEmail().getLength()),
				securityProperties.getCode().getEmail().getExpireIn());
	}

}
