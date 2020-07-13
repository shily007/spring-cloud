package com.sc.security.auth.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码异常处理
 * @author dy
 *
 */
public class AuthCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthCodeException(String msg) {
		super(msg);
	}

}
