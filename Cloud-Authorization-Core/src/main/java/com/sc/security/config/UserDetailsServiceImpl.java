package com.sc.security.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sc.api.utils.LoggerUtil;

/**
 * 
 * @author dy
 *
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoggerUtil.info("登录用户：" + username);
		return new User(username, "$2a$10$27Q1VsnsYnw44RBHDLJXWu0d62FEOUBq19MkvNj/rxqAzqZnvGM2C", AuthorityUtils.createAuthorityList("ADMIN"));
	}

}
