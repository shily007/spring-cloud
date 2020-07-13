package com.sc.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.api.utils.JsonResult;
import com.sc.api.utils.LoggerUtil;
import com.sc.security.auth.code.AuthCodeProcessor;
import com.sc.security.auth.code.AuthCodeType;
import com.sc.security.properties.SecurityConstants;
import com.sc.security.properties.SecurityProperties;

/**
 * 验证码过滤器
 * 
 * @author dy
 *
 */
@Component("authCodeFilter")
public class AuthCodeFilter extends OncePerRequestFilter {

	@Autowired
	private Map<String, AuthCodeProcessor> authCodeProcessors;
	private Map<String, AuthCodeType> urlMap = new HashMap<String, AuthCodeType>();
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		// 短信验证码登录接口
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, AuthCodeType.SMS);
		// 邮箱验证码登录接口
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_EMAIL, AuthCodeType.EMAIL);
		String[] urls = securityProperties.getCode().getSms().getUrl().split(",");
		for (String url : urls) {
			urlMap.put(url, AuthCodeType.SMS);
		}
		urls = securityProperties.getCode().getEmail().getUrl().split(",");
		for (String url : urls) {
			urlMap.put(url, AuthCodeType.EMAIL);
		}
		urls = securityProperties.getCode().getImage().getUrl().split(",");
		for (String url : urls) {
			urlMap.put(url, AuthCodeType.IMAGE);
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			AuthCodeType type = getAuthCodeType(request);
			if (type != null) {
				LoggerUtil.info("开始校验验证码！");
				authCodeProcessors.get(type.toString().toLowerCase() + AuthCodeProcessor.class.getSimpleName())
						.validate(type, new ServletWebRequest(request, response));
			}     
			if(isDecrypt(request))
				filterChain.doFilter(new RequestWrapper(request), response);
			else
				filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=UTF-8");
			try {
				response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<Object>(e.getMessage())));
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	/**
	 * @Description 判断是否需要解密操作
	 * @param request
	 * @return 
	 * @author dy
	 * @date 2019年12月13日
	 */
	private boolean isDecrypt(HttpServletRequest request) {
		for (int i = 0; i < SecurityConstants.NEED_DECRYPT_PARAMS.length; i++) {
			Map<String, String[]> parameterMap = request.getParameterMap();
        	if(parameterMap.containsKey(SecurityConstants.NEED_DECRYPT_PARAMS[i])) 
        		return true;
		} 
		return false;
	}

	/**
	 * 获取验证码类型
	 * 
	 * @param request
	 * @return
	 */
	private AuthCodeType getAuthCodeType(HttpServletRequest request) {
		Set<String> urls = urlMap.keySet();
		LoggerUtil.info(request.getRequestURI());
		for (String url : urls) {
			if (antPathMatcher.match(url, request.getRequestURI()))
				return urlMap.get(url);
		}
		return null;
	}

}
