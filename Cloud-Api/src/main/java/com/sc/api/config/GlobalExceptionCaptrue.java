package com.sc.api.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.api.utils.JsonResult;

/**
 * 全局异常处理
 * 
 * @author dy 2020年7月17日
 */
@ResponseBody
@ControllerAdvice//本身也是基于AOP实现的
public class GlobalExceptionCaptrue {

	@ExceptionHandler(value = Exception.class)
	public JsonResult<Object> captrue(Exception e) {
		return new JsonResult<>(500, e.getMessage());
	}

}
