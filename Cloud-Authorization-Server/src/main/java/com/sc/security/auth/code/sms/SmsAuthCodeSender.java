package com.sc.security.auth.code.sms;

/**
 * 短信验证码发送接口
 * @author dy
 *
 */
public interface SmsAuthCodeSender {
	
	/**
	 * 发送短信验证码
	 * @param mobile
	 * @param code
	 */
	boolean send(String mobile, String code);

}
