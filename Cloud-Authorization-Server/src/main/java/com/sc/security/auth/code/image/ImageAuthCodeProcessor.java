package com.sc.security.auth.code.image;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.sc.api.utils.WebUtil;
import com.sc.security.auth.code.AuthCode;
import com.sc.security.auth.code.AuthCodeException;
import com.sc.security.auth.code.AuthCodeProcessorImpl;

/**
 * 图形验证码处理器
 * @author dy
 *
 */
@Component("imageAuthCodeProcessor")
public class ImageAuthCodeProcessor extends AuthCodeProcessorImpl {
	
	@Override
	protected boolean send(AuthCode authCode, ServletWebRequest servletWebRequest) throws Exception {
		ImageIO.write(authCode.getImage(), "JPEG", servletWebRequest.getResponse().getOutputStream());
		return true;
	}

	@Override
	public void canSend(HttpServletRequest request) {}

	@Override
	protected String getAuthCodeKey(HttpServletRequest request) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId) || !WebUtil.isMobile(deviceId))
			throw new AuthCodeException("请在请求头中携带deviceId参数");
		return deviceId;
	}

	@Override
	protected long getExpireIn() {
		return securityProperties.getCode().getImage().getExpireIn();
	}

	@Override
	protected long getInterval() {
		return 0;
	}

}
