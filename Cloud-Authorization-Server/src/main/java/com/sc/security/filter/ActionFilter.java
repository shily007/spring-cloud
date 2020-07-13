package com.sc.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sc.security.auth.code.AuthCodeException;

/**
 * @Title ActionFilter
 * @Description 权限过滤器
 * @author dy
 * @date 2019年11月29日
 */
@Component("actionFilter")
public class ActionFilter extends OncePerRequestFilter {

	@Autowired
	PasswordEncoder encoder;
	private final String[] swaggerurls = { ".css", ".js", "swagger", "webjars", "v2", ".html", "publicKey", "file",
			"show", "code/sms", "code/email", "code/mobile", "/sys/menu/navigation", "/user_info",
			"/sys/action/role_auth/user" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!isSwagger(request.getRequestURI())) {
			if (request.getMethod().equals("DELETE")) {
				String username = request.getUserPrincipal().getName();
				if (StringUtils.isEmpty(username))
					throw new AuthCodeException("请登录！");
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * @Description 检查是否是swaggerurl
	 * @param uri
	 * @return
	 * @author dy
	 * @date 2019年12月12日
	 */
	private boolean isSwagger(String uri) {
		for (int i = 0; i < swaggerurls.length; i++) {
			if (uri.contains(swaggerurls[i]))
				return true;
		}
		return false;
	}

}
