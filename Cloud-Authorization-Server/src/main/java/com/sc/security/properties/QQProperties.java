package com.sc.security.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Title QQProperties
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
public class QQProperties {
	
	private String providerId = "qq";
	
	/**
	 * Application id.
	 */
	private String appId;

	/**
	 * Application secret.
	 */
	private String appSecret;

}
