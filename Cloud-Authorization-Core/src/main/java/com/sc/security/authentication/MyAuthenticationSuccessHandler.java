package com.sc.security.authentication;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.websocket.AuthenticationException;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.api.utils.JsonResult;
import com.sc.api.utils.LoggerUtil;

/**
 * 登录成功处理器
 * @author dy
 *
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		LoggerUtil.info("登录成功！");
		try {		
			String header = request.getHeader("Authorization");
			/* BasicAuthenticationFilter */
			if (!StringUtils.isNotBlank(header) || !header.startsWith("Basic ")) 
				throw new AuthenticationException("请求头无信息！");
			String[] tokens  = extractAndDecodeHeader(header, request);	
			assert tokens.length == 2;
			/* BasicAuthenticationFilter */
			String clientId = tokens[0];
			String clientSecret = tokens[1];
			ClientDetails clientDetails = null;	
			try {
				clientDetails = clientDetailsService.loadClientByClientId(clientId);
			} catch (NoSuchClientException e) {
				throw new AuthenticationException("clientId对应的配置信息不存在："+clientId);
			}
			
			if(!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) 
				throw new AuthenticationException("clientSecret对应的配置信息不存在："+clientSecret);
			
			TokenRequest tokenRequest = new TokenRequest(new HashMap<String, String>(), clientId, clientDetails.getScope(), "password");
			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
			OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);		
			addLog(request,authentication.getPrincipal());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<Object>(token)));				
		} catch (Exception e) {
			response.setStatus(HttpStatus.OK.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new JsonResult<Object>(e.getMessage())));	
		}
	}
	
	/**
	 * 保存登录日志
	 * @param request
	 * @param object
	 */
	private void addLog(HttpServletRequest request, Object object) {
//		if(object != null) {
//			User user = (User) object;
//			if(user != null) {
//				String username = user.getUsername();
//				if(StringUtils.isNotBlank(username)) {
//					Log log = new Log()
//							.setMethod(request.getRequestURI())
//							.setIp(WebUtil.getIpAddr(request));
//					for (Category category : Category.values()) {
//						if(username.startsWith(category.getPrefiex())) {//管理员登录
//							username = username.replace(category.getPrefiex(), "");
//							log.setContent(category.getMsg()+"登录");
//							log.setCategory(category);
//							break;
//						}
//					}
//					log.setCreater(username);
//					logRepository.save(log);
//				}				
//			}
//		}
	}

	/**
	 * Decodes the header into a username and password.
	 *
	 * @throws BadCredentialsException if the Basic header is not present or is not valid
	 * Base64
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"请求头信息解码失败！");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("无效的token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
