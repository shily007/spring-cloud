package com.sc.security.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String obtainPassword = super.obtainPassword(request);
		return obtainPassword;
	}

}
