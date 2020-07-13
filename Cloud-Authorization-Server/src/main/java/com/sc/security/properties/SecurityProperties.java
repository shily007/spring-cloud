package com.sc.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.bms.security")
@Component
public class SecurityProperties {
	
	private AuthCodeProperties code = new AuthCodeProperties();
	private SocialProperties social = new SocialProperties();
	private Oauth2Properties oauth2 = new Oauth2Properties();

}
