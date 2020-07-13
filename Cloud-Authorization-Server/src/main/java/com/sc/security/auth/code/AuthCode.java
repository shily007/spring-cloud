package com.sc.security.auth.code;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @author dy
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "验证码")
public class AuthCode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@ApiModelProperty(value = "验证码类型")
	private AuthCodeType authCodeType = AuthCodeType.SMS;
	@ApiModelProperty(value = "验证码")
	private String code;
	@ApiModelProperty(value = "到期时间")
	private LocalDateTime expireTime;
	@ApiModelProperty(value = "多少秒后到期，默认5分钟")
	private long expireIn = 300;
	@ApiModelProperty(value = "图片验证码生成的图片")
	private BufferedImage image;
	@ApiModelProperty(value = "关键字")
	private String keyword;
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime ctTime = LocalDateTime.now();
	@ApiModelProperty(value = "已删除")
	private boolean deleted = false;
	@ApiModelProperty(value = "删除时间")
	private LocalDateTime deleteTime;
	
	public void setExpireIn(Long expireIn) {
		this.expireIn = expireIn;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public AuthCode(AuthCodeType authCodeType, String code, long expireIn) {
		super();
		this.authCodeType = authCodeType;
		this.code = code;		
		this.expireIn = expireIn;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public AuthCode() {
		super();
	}

	public AuthCode(Long id, AuthCodeType authCodeType, String code, LocalDateTime expireTime, long expireIn,
			BufferedImage image, String keyword, LocalDateTime ctTime, boolean deleted, LocalDateTime deleteTime) {
		super();
		this.id = id;
		this.authCodeType = authCodeType;
		this.code = code;
		this.expireTime = expireTime;
		this.expireIn = expireIn;
		this.image = image;
		this.keyword = keyword;
		this.ctTime = ctTime;
		this.deleted = deleted;
		this.deleteTime = deleteTime;
	}

	public void setDeleted() {
		this.deleted = true;
		this.deleteTime = LocalDateTime.now();
	}

}
