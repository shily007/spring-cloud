/**
 * 
 */
package com.sc.security.properties;

import lombok.Data;

/**
 * @Title SocialProperties
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
@Data
public class SocialProperties {
	
	private String filterProcessesUrl = "/auth";

	private QQProperties qq = new QQProperties();
	
	private WeixinProperties weixin = new WeixinProperties();
	
}
