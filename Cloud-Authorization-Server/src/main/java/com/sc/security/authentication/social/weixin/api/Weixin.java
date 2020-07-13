package com.sc.security.authentication.social.weixin.api;

/**
 * @Title Weixin
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
public interface Weixin {

	/* (non-Javadoc)
	 * @see com.ymt.pz365.framework.security.social.api.SocialUserProfileService#getUserProfile(java.lang.String)
	 */
	WeixinUserInfo getUserInfo(String openId);
	
}
