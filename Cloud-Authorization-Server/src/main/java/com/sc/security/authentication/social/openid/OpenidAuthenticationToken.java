package com.sc.security.authentication.social.openid;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

public class OpenidAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private final Object principal;
	private String providerId;
	
	public OpenidAuthenticationToken(String openid, String providerId) {
		super(null);
		this.principal = openid;
		this.providerId = providerId;
		setAuthenticated(false);
	}

	public OpenidAuthenticationToken(UserDetails user, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = user.getUsername();
		this.providerId = authorities.iterator().next().getAuthority();
		setAuthenticated(false);
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public String getProviderId() {
		return providerId;
	}

}
