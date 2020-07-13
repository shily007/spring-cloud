package com.sc.security.auth.code.sms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.sc.api.utils.WebUtil;
import com.sc.security.auth.code.AuthCode;
import com.sc.security.auth.code.AuthCodeException;
import com.sc.security.auth.code.AuthCodeProcessorImpl;
import com.sc.security.properties.SecurityConstants;

/**
 * 短信验证码处理器
 * 
 * @author dy
 *
 */
@Component("smsAuthCodeProcessor")
public class SmsAuthCodeProcessor extends AuthCodeProcessorImpl {

	@Autowired
	private SmsAuthCodeSender smsAuthCodeSender;

	@Override
	protected boolean send(AuthCode authCode, ServletWebRequest servletWebRequest) {
		String mobile = getParamValue(servletWebRequest.getRequest(), SecurityConstants.DEFAULT_PARAM_NAME_MOBILE);			
		return smsAuthCodeSender.send(mobile, authCode.getCode());
	}

	@Override
	public void canSend(HttpServletRequest request) {
		String mobile = getParamValue(request, SecurityConstants.DEFAULT_PARAM_NAME_MOBILE);
		if (mobile != null) {
			getExpireKey(request);

			// 判断发送短信的次数是否已达上限
//			List<Object> list = redisUtil.lGet(buildKey(request, SecurityConstants.DEFAULT_PARAM_NAME_CODE_SMS), 0, -1);
//			for (int i = 0; i < list.size(); i++) {
//				Object object = list.get(i);
//				if (object != null && !object.equals("")) {
//					try {
//						AuthCode authCode = JSONArray.toJavaObject((JSON) object, AuthCode.class);
//						if (authCode.getCtTime().isBefore(LocalDateTime.now().plusHours(-24))
//								|| authCode.getCtTime().getDayOfMonth() != LocalDate.now().getDayOfMonth())
//							redisUtil.lRemove(authCode.getKeyword(), 1, object);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			if(list.size() > securityProperties.getCode().getSms().getToplimit())
//				throw new AuthCodeException("您今日发送短信的次数已达上限，请明天再发！");
		}

	}

	@Override
	protected String getAuthCodeKey(HttpServletRequest request) {
		String mobile = getParamValue(request, SecurityConstants.DEFAULT_PARAM_NAME_MOBILE);
		if(mobile.contains("_"))
			mobile = mobile.split("_")[1];
		if (StringUtils.isBlank(mobile) || !WebUtil.isMobile(mobile))
			throw new AuthCodeException("请在参数值携带正确的mobile参数");
		return SecurityConstants.DEFAULT_PARAM_NAME_CODE_SMS + ":" + mobile;
	}

	@Override
	protected long getExpireIn() {
		return securityProperties.getCode().getSms().getExpireIn();
	}

	@Override
	protected long getInterval() {
		return securityProperties.getCode().getSms().getInterval();
	}

}
