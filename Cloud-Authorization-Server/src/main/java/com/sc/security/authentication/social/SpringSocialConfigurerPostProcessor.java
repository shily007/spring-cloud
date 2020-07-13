package com.sc.security.authentication.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Title SpringSocialConfigurerPostProcessor
 * @Description  
 * @author dy
 * @date 2019年12月5日
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(StringUtils.equals(beanName, "mySocialSecurityConfig")) {
			MySpringSocialConfigurer configurer = (MySpringSocialConfigurer) bean;
			configurer.signupUrl("/social/signUp");
		}
		return bean;
	}

}
