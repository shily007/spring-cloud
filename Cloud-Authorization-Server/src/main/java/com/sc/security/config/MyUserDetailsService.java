package com.sc.security.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import com.sc.api.utils.LoggerUtil;

/**
 *
 * @author dy
 *
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoggerUtil.info("登录用户：" + username);
		return new User(username.substring(username.indexOf("_")+1, username.length()), "$2a$10$QPHBHGNslx/ffG.xIu5hlOJeYLdyKs3PGapYb.maJOYP4h5/T.c1m", AuthorityUtils.createAuthorityList("ADMIN"));
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
