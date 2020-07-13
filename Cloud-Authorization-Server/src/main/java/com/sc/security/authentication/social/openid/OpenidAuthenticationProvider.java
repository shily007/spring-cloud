package com.sc.security.authentication.social.openid;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

public class OpenidAuthenticationProvider implements AuthenticationProvider {
	
	private SocialUserDetailsService  socialUserDetailsService;
	private UsersConnectionRepository  usersConnectionRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OpenidAuthenticationToken authenticationToken = (OpenidAuthenticationToken) authentication;
		Set<String> providerUserIds = new HashSet<String>();
		providerUserIds.add((String)authenticationToken.getPrincipal());
		Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIds);
		if(CollectionUtils.isEmpty(userIds) || userIds.size()!=1)
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		String userId = userIds.iterator().next();
		UserDetails user = socialUserDetailsService.loadUserByUserId(userId);
		if(user == null)
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		OpenidAuthenticationToken authenticationResult = new OpenidAuthenticationToken(user,user.getAuthorities());
		authenticationResult.setDetails(authenticationToken.getDetails());
		
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
