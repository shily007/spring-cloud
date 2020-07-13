package com.sc.security.authentication.social.openid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sc.security.properties.SecurityConstants;

public class OpenidAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private String openidParameter = SecurityConstants.DEFAULT_PARAM_NAME_OPENID;
	private String providerIdParameter = SecurityConstants.DEFAULT_PARAM_NAME_PROVIDERID;
	private boolean postOnly = true;
	
	protected OpenidAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,"POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if(postOnly && !request.getMethod().equals("POST"))
			throw new AuthenticationServiceException("Authentication method not supported:"+request.getMethod());
		String openid = obtainOpenid(request);
		String providerId = obtainProviderId(request);
		if(openid == null)
			openid = "";
		if(providerId == null)
			providerId = "";
		openid = openid.trim();
		providerId = providerId.trim();
		OpenidAuthenticationToken authRequest = new OpenidAuthenticationToken(openid, providerId);
		setDetails(request,authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private void setDetails(HttpServletRequest request, OpenidAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
	
	private String obtainProviderId(HttpServletRequest request) {
		return request.getParameter(providerIdParameter);
	}

	private String obtainOpenid(HttpServletRequest request) {
		return request.getParameter(openidParameter);
	}

}
