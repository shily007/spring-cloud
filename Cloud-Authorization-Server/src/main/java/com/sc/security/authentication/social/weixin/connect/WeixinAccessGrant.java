package com.sc.security.authentication.social.weixin.connect;

import org.springframework.social.oauth2.AccessGrant;

/**
 * @Title WeixinAccessGrant
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
public class WeixinAccessGrant extends AccessGrant {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7243374526633186782L;
	
	private String openId;
	
	public WeixinAccessGrant() {
		super("");
	}

	public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
		super(accessToken, scope, refreshToken, expiresIn);
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
