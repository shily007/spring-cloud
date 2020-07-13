package com.sc.security.auth.code.email;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.sc.api.utils.WebUtil;
import com.sc.security.auth.code.AuthCode;
import com.sc.security.auth.code.AuthCodeException;
import com.sc.security.auth.code.AuthCodeProcessorImpl;
import com.sc.security.properties.SecurityConstants;

/**
 * @Title EmailAuthCodeProcessor
 * @Description 邮箱验证码处理器
 * @author dy
 * @date 2019年11月26日
 */
@Component("emailAuthCodeProcessor")
public class EmailAuthCodeProcessor extends AuthCodeProcessorImpl {


	@Override
	protected boolean send(AuthCode authCode, ServletWebRequest servletWebRequest) throws Exception {
		return true;
	}

	@Override
	public void canSend(HttpServletRequest request) {
		String email = getParamValue(request, SecurityConstants.DEFAULT_PARAM_NAME_EMAIL);
		if (StringUtils.isNotBlank(email)) {
//			String key = getExpireKey(request);
//			if (redisUtil.hasKey(key)) {
//				long expire = redisUtil.getExpire(key);
//				throw new AuthCodeException("请在" + expire + "秒后重新发送验证码！");
//			}
			// 判断手机号码是否无短信发送次数限制

			// 判断发送短信的次数是否已达上限
//			List<Object> list = redisUtil.lGet(buildKey(request, SecurityConstants.DEFAULT_PARAM_NAME_CODE_EMAIL), 0,
//					-1);
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
//			if (list.size() > 10)
//				throw new AuthCodeException("您今日发送验证码的次数已达上限，请明天再发！");
		}

	}

	@Override
	protected String getAuthCodeKey(HttpServletRequest request) {
		String email = getParamValue(request, SecurityConstants.DEFAULT_PARAM_NAME_EMAIL);
		if(email.contains("_"))
			email = email.split("_")[1];
		if (StringUtils.isBlank(email) || !WebUtil.isEmail(email))
			throw new AuthCodeException("请在参数值携带正确的email参数");
		return SecurityConstants.DEFAULT_PARAM_NAME_CODE_EMAIL + ":" + email;
	}

	@Override
	protected long getExpireIn() {
		return securityProperties.getCode().getEmail().getExpireIn();
	}

	@Override
	protected long getInterval() {
		return securityProperties.getCode().getEmail().getInterval();
	}

}
