package com.sc.security.authentication.social.weixin.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.sc.security.authentication.social.weixin.api.Weixin;
import com.sc.security.authentication.social.weixin.api.WeixinUserInfo;

/**
 * @Title WeixinAdapter
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {
	
	private String openId;
	
	public WeixinAdapter() {}
	
	public WeixinAdapter(String openId){
		this.openId = openId;
	}

	/**
	 * @param api
	 * @return
	 */
	@Override
	public boolean test(Weixin api) {
		return true;
	}

	/**
	 * @param api
	 * @param values
	 */
	@Override
	public void setConnectionValues(Weixin api, ConnectionValues values) {
		WeixinUserInfo profile = api.getUserInfo(openId);
		values.setProviderUserId(profile.getOpenid());
		values.setDisplayName(profile.getNickname());
		values.setImageUrl(profile.getHeadimgurl());
	}

	/**
	 * @param api
	 * @return
	 */
	@Override
	public UserProfile fetchUserProfile(Weixin api) {
		return null;
	}

	/**
	 * @param api
	 * @param message
	 */
	@Override
	public void updateStatus(Weixin api, String message) {
		//do nothing
	}

}
