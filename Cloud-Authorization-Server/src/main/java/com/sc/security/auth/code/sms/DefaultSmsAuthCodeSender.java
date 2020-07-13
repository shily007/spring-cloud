package com.sc.security.auth.code.sms;

import com.sc.api.utils.LoggerUtil;

/**
 * 短信验证码发送接口默认实现
 * @author dy
 *
 */
public class DefaultSmsAuthCodeSender implements SmsAuthCodeSender {

	@Override
	public boolean send(String mobile, String code) {
		LoggerUtil.info("向手机："+mobile+"发送短信验证码："+code);
		return true;
	}

}
