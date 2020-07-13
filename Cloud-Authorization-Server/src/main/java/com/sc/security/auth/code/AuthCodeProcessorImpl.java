package com.sc.security.auth.code;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sc.api.utils.LoggerUtil;
import com.sc.security.properties.SecurityProperties;

/**
 * 验证码验证创建实现
 * 
 * @author dy
 *
 */
@Component
public abstract class AuthCodeProcessorImpl implements AuthCodeProcessor {

	@Autowired
	private Map<String, AuthCodeGenerator> authCodeGenerators;
	@Autowired
	private StringEncryptor stringEncryptor;
	public static final String AUTH_COED_LIST = "authCodeList:";// 验证码列表
	public static final String AUTH_COED_EXPIRE = "authCodeExpire:";// 验证码过期时间前缀
	@Autowired
	public SecurityProperties securityProperties;

	@Override
	public void create(String type, ServletWebRequest servletWebRequest) throws Exception {
		canSend(servletWebRequest.getRequest());
		AuthCode authCode = generate(type, servletWebRequest);
		authCode.setKeyword(buildKey(servletWebRequest.getRequest(), type));
		if (!send(authCode, servletWebRequest))
			throw new AuthCodeException("验证码发送失败，请联系管理员！");
		authCode.setCode(stringEncryptor.encrypt(authCode.getCode()));
	}

	/**
	 * @Description 获取过期时间
	 * @return
	 * @author dy
	 * @date 2019年11月26日
	 */
	protected abstract long getExpireIn();

	/**
	 * @Description 获取验证码发送时间间隔
	 * @return
	 * @author dy
	 * @date 2019年11月26日
	 */
	protected abstract long getInterval();

	/**
	 * @Description 获取查询过期时间的键
	 * @param request
	 * @return
	 * @author dy
	 * @date 2019年11月26日
	 */
	public String getExpireKey(HttpServletRequest request) {
		return AUTH_COED_EXPIRE + getAuthCodeKey(request);
	}

	/**
	 * @Description 判断是否可用发送短信（若不需判断则只需重写该方法，不作实现即可）
	 * @author dy
	 * @param httpServletRequest
	 * @date 2019年11月25日
	 */
	public abstract void canSend(HttpServletRequest httpServletRequest);

	/**
	 * @Description 通过参数名称获取参数值
	 * @param request
	 * @param paramName
	 * @return
	 * @author dy
	 * @date 2019年11月25日
	 */
	public String getParamValue(HttpServletRequest request, String paramName) {
		try {
			return ServletRequestUtils.getRequiredStringParameter(request, paramName);
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 发送验证码
	 * 
	 * @param authCode
	 * @param servletWebRequest
	 * @throws Exception
	 */
	protected abstract boolean send(AuthCode authCode, ServletWebRequest servletWebRequest) throws Exception;

	/**
	 * 生成验证码
	 * 
	 * @param type
	 * @param servletWebRequest
	 * @return
	 */
	private AuthCode generate(String type, ServletWebRequest servletWebRequest) {
		String generatorName = type + AuthCodeGenerator.class.getSimpleName();
		AuthCodeGenerator authCodeGenerator = authCodeGenerators.get(generatorName);
		if (authCodeGenerator == null)
			throw new AuthCodeException("验证码生成器不存在：" + generatorName);
		return authCodeGenerator.generate(servletWebRequest);
	}

	/**
	 * 生成关键字
	 * 
	 * @param request
	 * @param validateCodeType
	 * @return
	 */
	public String buildKey(HttpServletRequest request, String validateCodeType) {
		String deviceId = getAuthCodeKey(request);
		return AUTH_COED_LIST + deviceId;
	}

	/**
	 * @Description 获取验证码的关键字
	 * @return
	 * @author dy
	 * @param httpServletRequest
	 * @date 2019年11月25日
	 */
	protected abstract String getAuthCodeKey(HttpServletRequest request);

	@Override
	public void validate(AuthCodeType type, ServletWebRequest servletWebRequest) {
		buildKey(servletWebRequest.getRequest(), type.toString().toLowerCase());
		List<Object> list = new ArrayList<Object>();
		if (list.size() == 0)
			throw new AuthCodeException("验证码不存在！");
		String codeInRequest = null;
		try {
			codeInRequest = ServletRequestUtils.getRequiredStringParameter(servletWebRequest.getRequest(),
					type.getParamNameInRequest());
		} catch (ServletRequestBindingException e) {
			throw new AuthCodeException("获取验证码失败！");
		}
		if (StringUtils.isBlank(codeInRequest))
			throw new AuthCodeException("验证码不能为空！");
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (object != null && !object.equals("")) {
				AuthCode authCode = JSONArray.toJavaObject((JSON) object, AuthCode.class);
				String decryptCode = null;
				try {
					decryptCode = stringEncryptor.decrypt(authCode.getCode());
				} catch (Exception e) {
					LoggerUtil.info("解密失败！");
					authCode.setDeleted();
					continue;
				}
				if (StringUtils.equals(decryptCode, codeInRequest)) {// 验证码相同
					if (authCode.getExpireTime().isBefore(LocalDateTime.now()))// 是否过期
						throw new AuthCodeException("验证码已过期！");
					if (authCode.isDeleted())// 是否使用过
						throw new AuthCodeException("验证码已被使用！");
					authCode.setDeleted();
					authCode.setDeleted();
					flag = false;
					break;
				}
			}
		}
		if (flag)
			throw new AuthCodeException("验证码错误！");
	}

}
