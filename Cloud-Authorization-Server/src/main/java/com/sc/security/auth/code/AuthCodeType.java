package com.sc.security.auth.code;

import com.sc.security.properties.SecurityConstants;

/**
 * 验证码类型
 * @author dy
 *
 */
public enum AuthCodeType {
	
	IMAGE{//图片验证码
		@Override
		public String getParamNameInRequest() {
			return SecurityConstants.DEFAULT_PARAM_NAME_CODE_IMAGE;
		}
	},
	SMS{//短信验证码
		@Override
		public String getParamNameInRequest() {
			return SecurityConstants.DEFAULT_PARAM_NAME_CODE_SMS;
		}
	},
	EMAIL{//邮箱验证码
		@Override
		public String getParamNameInRequest() {
			return SecurityConstants.DEFAULT_PARAM_NAME_CODE_EMAIL;
		}
	};

	/**
	 * 从请求中获取的参数名称
	 * @return
	 */
	public abstract String getParamNameInRequest();
	
	/**
	 * 根据type获取验证码类型
	 * @param type
	 * @return
	 */
	public static AuthCodeType getAuthCodeType(String type) {
		for (AuthCodeType codeType : AuthCodeType.values()) {
			if(type.toUpperCase().equals(codeType.toString())) {
				return codeType;
			}
		}
		return null;
	}

}
