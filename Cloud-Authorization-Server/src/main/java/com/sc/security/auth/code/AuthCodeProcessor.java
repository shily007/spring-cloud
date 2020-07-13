package com.sc.security.auth.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成验证
 * @author dy
 *
 */
public interface AuthCodeProcessor {

	/**
	 * 创建验证码
	 * @param type
	 * @param servletWebRequest
	 * @throws Exception 
	 */
	void create(String type, ServletWebRequest servletWebRequest) throws Exception;

	/**
	 * 校验验证码
	 * @param type 
	 * @param servletWebRequest
	 */
	void validate(AuthCodeType type, ServletWebRequest servletWebRequest);

}
