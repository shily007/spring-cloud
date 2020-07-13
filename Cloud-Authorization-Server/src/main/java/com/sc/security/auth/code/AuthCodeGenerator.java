package com.sc.security.auth.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器接口
 * @author dy
 *
 */
public interface AuthCodeGenerator {
	
	/**
	 * 生成验证码
	 * @param request
	 * @return
	 */
	AuthCode generate(ServletWebRequest request);

}
