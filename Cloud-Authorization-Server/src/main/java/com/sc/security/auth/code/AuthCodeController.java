package com.sc.security.auth.code;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.api.utils.JsonResult;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @author dy
 *
 */
@RestController
@ApiModel(value = "验证码控制器")
public class AuthCodeController {

	@Autowired
	private Map<String, AuthCodeProcessor> authCodeProcessors;
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 生成验证码
	 * 
	 * @param type     验证码类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void GetCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			String authCodeProcessorName = type + AuthCodeProcessor.class.getSimpleName();
			AuthCodeProcessor authCodeProcessor = authCodeProcessors.get(authCodeProcessorName);
			if (authCodeProcessor == null)
				throw new AuthCodeException("该类型的验证码不存在！");
			authCodeProcessor.create(type, new ServletWebRequest(request, response));
			try {
				response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<Object>("验证码发送成功！")));
			} catch (IOException e1) {
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.OK.value());
			try {
				response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<Object>(e.getMessage())));
			} catch (IOException e1) {
			}
		}
	}

}
