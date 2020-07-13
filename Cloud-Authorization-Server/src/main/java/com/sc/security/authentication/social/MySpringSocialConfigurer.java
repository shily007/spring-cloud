package com.sc.security.authentication.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Title ImoocSpringSocialConfigurer
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
public class MySpringSocialConfigurer extends SpringSocialConfigurer {
	
	private String filterProcessesUrl;
	
	public MySpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T) filter;
	}

}
