package com.sc.security.auth.code.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.sc.security.auth.code.AuthCode;
import com.sc.security.auth.code.AuthCodeGenerator;
import com.sc.security.auth.code.AuthCodeType;
import com.sc.security.properties.SecurityProperties;

/**
 * 图形验证码生成器
 * 
 * @author dy
 *
 */
@Component("imageAuthCodeGenerator")
public class ImageAuthCodeGenerator implements AuthCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public AuthCode generate(ServletWebRequest request) {
		AuthCode authCode = new AuthCode(AuthCodeType.IMAGE,
				RandomStringUtils.randomNumeric(securityProperties.getCode().getImage().getLength()),
				securityProperties.getCode().getImage().getExpireIn());
		authCode.setImage(generateImageCode(authCode.getCode(), request));
		return authCode;
	}

	/**
	 * 生成图片验证码的图片
	 * 
	 * @param code
	 * @param request
	 * @return
	 */
	public BufferedImage generateImageCode(String code, ServletWebRequest request) {
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
				securityProperties.getCode().getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
				securityProperties.getCode().getImage().getHeight());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 250));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
//		String sRand = "";
		for (int i = 0; i < code.length(); i++) {
//			String rand = String.valueOf(random.nextInt(10));
			String rand = code.charAt(i) + "";
//			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		g.dispose();
		return image;
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
