package com.sc.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.api.utils.JsonResult;
import com.sc.api.utils.LoggerUtil;

/**
 * 登录失败处理器
 * @author dy
 *
 */
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		LoggerUtil.info("登录失败！");
		String msg = null;
//		if(StringUtils.equals(exception.getClass().getSimpleName(), InternalAuthenticationServiceException.class.getSimpleName()))
//			msg = "用户名或密码错误！";
//		else
			msg = exception.getMessage();
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<Object>(msg)));
	}

}
