package com.sc.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.sc.security", ignoreInvalidFields = true)
@Component
public class SecurityProperties {	
	
	private Oauth2Properties oauth2 = new Oauth2Properties();
	private AuthCodeProperties code = new AuthCodeProperties();
	
	public Oauth2Properties getOauth2() {
		return oauth2;
	}
	public void setOauth2(Oauth2Properties oauth2) {
		this.oauth2 = oauth2;
	}
	public AuthCodeProperties getCode() {
		return code;
	}
	public void setCode(AuthCodeProperties code) {
		this.code = code;
	}
	
}
