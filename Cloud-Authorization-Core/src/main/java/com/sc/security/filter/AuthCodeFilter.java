package com.sc.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.api.utils.JsonResult;
import com.sc.security.properties.SecurityConstants;

/**
 * 验证码过滤器
 * 
 * @author dy
 *
 */
@Component("authCodeFilter")
public class AuthCodeFilter extends OncePerRequestFilter {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();	
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {     
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

}
