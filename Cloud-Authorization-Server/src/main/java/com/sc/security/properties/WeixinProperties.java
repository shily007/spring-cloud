package com.sc.security.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Title WeixinProperties
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
public class WeixinProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
	 */
	private String providerId = "weixin";
	
	/**
	 * Application id.
	 */
	private String appId;

	/**
	 * Application secret.
	 */
	private String appSecret;

}
