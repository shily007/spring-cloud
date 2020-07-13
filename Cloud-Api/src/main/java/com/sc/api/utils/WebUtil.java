package com.sc.api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Title WebUtil
 * @Description web 工具
 * @author dy
 * @date 2019年9月9日
 */
public class WebUtil {
	
	/**
	 * 获取当前登录用户名
	 * 
	 * @return
	 */
	private static String getLoginUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if(context != null) {
			Authentication authentication = context.getAuthentication();
			if(authentication != null && authentication.getPrincipal()!=null) 
				return authentication.getPrincipal().toString();
		}
		return null;
	}

	/**
	 * 获取当前登录用户去掉前缀后的用户名
	 * 
	 * @return
	 */
	public static String getUsername() {
		String username = getLoginUsername();
		if(username != null && username.contains("_"))
			return getLoginUsername().split("_")[1];
		return null;
	}
	
	/**
	 * @Description
	 * @return 获取登录类型
	 * @author dy
	 * @date 2019年12月11日
	 */
	public static String getLoginType() {
		String username = getLoginUsername();
		if(username != null && username.contains("_"))
			return getLoginUsername().split("_")[0];
		return null;
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
																// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		// ipAddress = this.getRequest().getRemoteAddr();

		return ipAddress;
	}

	/**
	 * 验证是否是邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (!StringUtils.isNotBlank(email))
			return false;
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		if (!StringUtils.isNotBlank(mobile))
			return false;
		String regex = "^1\\d{10}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证用户名是否合法
	 * 
	 * @param username 用户名
	 * @return
	 */
	public static boolean isUsername(String username) {
		if (!StringUtils.isNotBlank(username) || username.length() < 6 || username.length() > 18)
			return false;
		char c = username.charAt(0);
		String regexc = "^[a-zA-Z]+$";
		Pattern pc = Pattern.compile(regexc);
		Matcher mc = pc.matcher(c + "");
		if (!mc.matches())
			return false;
		String regex = "^[a-z0-9A-Z]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(username);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @method setSession(String key, Object value)
	 * @Description 设置session map类型
	 * @param key   键
	 * @param value 值
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static void setSession(String key, Object value) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.getSession().setAttribute(key, value);
	}

	/**
	 * @method setRequest(String key, Object value)
	 * @Description 向session中保存数据 可在页面中获取
	 * @param key   键
	 * @param value 值
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static void setRequest(String key, Object value) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.setAttribute(key, value);
	}

	/**
	 * @method getSession(String key)
	 * @Description 通过键获取session
	 * @param key 键
	 * @return
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static Object getSession(String key) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getSession().getAttribute(key);
	}

	/**
	 * @method removeSession(String key)
	 * @Description 通过键来移除session中的值
	 * @param key
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static void removeSession(String key) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.getSession().removeAttribute(key);
	}

	/**
	 * @method getContetPath()
	 * @Description 获取工程名称
	 * @return request.getContextPath()
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String getContetPath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getContextPath();// 获取工程名称
	}

	/**
	 * @method getRealPath(String dir)
	 * @Description 获取工程目录
	 * @param dir
	 * @return
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String getRealPath(String dir) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getServletPath();// 得到工程目录
	}

	/**
	 * @method getUrlPath()
	 * @Description 得到相对地址
	 * @return
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String getUrlPath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		StringBuffer url = request.getRequestURL(); // 得到相对地址
		return url.delete(url.length() - request.getRequestURI().length(), url.length())
				.append(request.getServletPath()).toString();
	}

	/**
	 * @method String isAdminSearchName(String searchName)
	 * @Description 判断查询名称是否正确
	 * @param searchName 查询名称
	 * @return
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String isAdminSearchName(String searchName) {
		if (!(searchName.equals("username") || searchName.equals("name") || searchName.equals("talk")
				|| searchName.equals("phone") || searchName.equals("idCard") || searchName.equals("email")
				|| searchName.equals("address") || searchName.equals("baoDanUsername") || searchName.equals("trueName")
				|| searchName.equals("isDj"))) {
			return "username";
		}
		return searchName;
	}

	/**
	 * @method urlDecode(String searchValue)
	 * @Description URL解码
	 * @param searchValue 编码后的URL地址
	 * @return searchValue
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String urlDecode(String searchValue) {
		try {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		return searchValue;
	}

	/**
	 * @method urlEncoding(String searchValue)
	 * @Description 对URL进行编码
	 * @param searchValue URL地址
	 * @return searchValue
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String urlEncoding(String searchValue) {
		try {
			if (searchValue != null && !searchValue.equals("")) {
				searchValue = new String(searchValue.getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			searchValue = "";
		}
		return searchValue;
	}

	/**
	 * @method randomLetter(Integer count)
	 * @Description 随机获取数字和大小写字母组成count位的字符串
	 * @param count 字符串位数
	 * @return str
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String randomLetter(Integer count) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String str = "";
		for (int i = 0; i < count; i++) {
			str += chars.charAt((int) (Math.random() * 62));
		}
		return str;
	}

	/**
	 * @method parseDate(String t)
	 * @Description 将时间字符串转换为时间"yyyy-MM-dd"
	 * @param t 时间字符串
	 * @return time
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static Date parseDate(String t) {
		if (t == null) {
			return null;
		}
		Date time = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			time = format.parse(t);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * @method parseDateHHMMSS(String date)
	 * @Description 将字符串转换为时间"yyyy-MM-dd HH:mm:ss"
	 * @param date 时间字符串
	 * @return
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static Date parseDateHHMMSS(String date) {
		if (date == null) {
			return null;
		}
		Date time = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			time = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String formatDouble(String format, Double d) {
		DecimalFormat f = new DecimalFormat(format);
		return f.format(d);
	}

	public static String urlEncode(String str, String encoding) {
		try {
			return URLEncoder.encode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlDecode(String str, String encoding) {
		try {
			return URLDecoder.decode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @method createFile(String realPath)
	 * @Description 创建文件不能创建文件夹
	 * @param realPath
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static void createFile(String realPath) {
		File file = new File(realPath);
		if (file.exists()) {
			if (file.exists()) {
				file.delete();
			}
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @method getTime(Integer lastMonth)
	 * @Description 获取距当月lastMonth个月的最初时刻和最后时刻 如：2016-9-30 00:00:00,2016-9-30
	 *              23:59:59
	 * @param lastMonth 月数
	 * @return date map类型
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static Map<String, String> getTime(Integer lastMonth) {
		Map<String, String> date = new HashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -lastMonth);
		LoggerUtil.info(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		date.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00");
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		date.put("month", new SimpleDateFormat("yyyy-MM").format(cal.getTime()));
		date.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 59:59:59");
		return date;
	}

	/**
	 * @method formatDateYYYYMMDD(Date time)
	 * @Description 格式化时间为："yyyy-MM-dd"字符串
	 * @param time
	 * @return
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String formatDateYYYYMMDD(Date time) {
		if (time != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(time);
		}
		return null;
	}

	/**
	 * @method monthFirst()
	 * @Description 获取当月最初时刻 如：2016-9-30 00:00:00
	 * @return new Date()
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static Date monthFirst() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format2.parse(format.format(c.getTime()) + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * @method monthLast()
	 * @Description 获取当月最后时刻 如：2016-09-01 23:59:59
	 * @return new Date()
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static Date monthLast() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 0);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format2.parse(format.format(c.getTime()) + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * @method getMyIP()
	 * @Description 获取外网IP
	 * @return
	 * @throws IOException
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String getMyIP() throws IOException {
		InputStream ins = null;
		try {
			URL url = new URL("http://1212.ip138.com/ic.asp");
			URLConnection con = url.openConnection();
			ins = con.getInputStream();
			InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
			BufferedReader bReader = new BufferedReader(isReader);
			StringBuffer webContent = new StringBuffer();
			String str = null;
			while ((str = bReader.readLine()) != null) {
				webContent.append(str);
			}
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");

			return webContent.substring(start, end);
		} finally {
			if (ins != null) {
				ins.close();
			}
		}
	}

	/**
	 * @method getMyIPLocal()
	 * @Description 获取内网IP
	 * @return
	 * @throws IOException
	 * @author dy
	 * @date 2016年9月14日
	 */
	public static String getMyIPLocal() throws IOException {
		InetAddress ia = InetAddress.getLocalHost();
		return ia.getHostAddress();
	}

	/**
	 * @method String getRandomCharAndNumr(Integer length)
	 * @Description 获取随机字母数字组合
	 * @param length 字符串长度
	 * @return
	 * @author dy
	 * @date 2017年11月10日
	 */
	public static String getRandomCharAndNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (b) { // 字符串
				// int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
				boolean c = random.nextBoolean();
				if (c) {
					str += (char) (97 + random.nextInt(26));// 取得小写字母
				} else {
					str += (char) (65 + random.nextInt(26));// 取得大写字母
				}
			} else { // 数字
				str += String.valueOf(random.nextInt(10));
			}
		}
		return str;
	}

	/**
	 * 随机获取指定长度的纯数字字符串
	 * 
	 * @param length 长度
	 * @return
	 */
	public static String getRandomNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			str += String.valueOf(random.nextInt(10));
		}
		return str;
	}

	/**
	 * 访问外部接口
	 * 
	 * @param url 接口地址
	 * @return StringBuffer
	 */
	public static StringBuffer outerRequest(String url) {
		InputStream ins = null;
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded/text/html");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");
			conn.connect();
			conn.setConnectTimeout(10000);
			ins = conn.getInputStream();
			InputStreamReader isReader = new InputStreamReader(ins, "UTF-8");
			BufferedReader bReader = new BufferedReader(isReader);
			StringBuffer webContent = new StringBuffer();
			String str = null;
			while ((str = bReader.readLine()) != null) {
				webContent.append(str);
			}
			return webContent;
		} catch (Exception e) {
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String dateTime2String(LocalDateTime dateTime) {
		String str = dateTime.toString();
		str = str.replace("-", "");
		str = str.replace("T", "");
		str = str.replace(":", "");
		str = str.replace(".", "");
		return str;
	}

	/**
	 * 加密盐值
	 */
	public static final String JASYPT_ENCRYPTOR_PASSWORD = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nLWJvb3"
			+ "QtYXBwbGljYXRpb24iXSwidXNlcl9uYW1lIjoibGVmdHNvIiwic2NvcGUiOlsicmVhZCJdLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV"
			+ "9VU0VSIn1dLCJleHAiOjE0OTENTkerDte345EGFXJOYW1lIjoibGVmdHNvIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6Ij"
			+ "gxNjI5NzQwLTRhZWQtNDM1Yy05MmM3LWZhOWIyODk5NmYzMiIsImNsaWVudF9pZCI6Im5vcm1hbC1hcHAifQ.YhDJkMSlyIN6uPfSFPbfRuu"
			+ "fndvylRmuGkrdprUSJIMretgerDFGFDS";

	/**
	 * @method String generatePassword(String pwd)
	 * @Description 生成加密密码
	 * @param pwd 密码
	 * @return
	 * @author dy
	 * @date 2017年11月22日
	 */
	public static String generatePassword(String pwd) {
		if (StringUtils.isNotEmpty(pwd)) {
			pwd = DigestUtils.md5DigestAsHex((pwd + JASYPT_ENCRYPTOR_PASSWORD).getBytes());
			int remainder = pwd.length() % 10;
			if (pwd.length() % 2 == 0) {
				return pwd.substring(0, 32 - remainder) + WebUtil.getRandomCharAndNumr(remainder);
			} else {
				return WebUtil.getRandomCharAndNumr(remainder) + pwd.substring(0, 32 - remainder);
			}
		}
		return null;
	}

	/**
	 * @method boolean inspectPwd(String pwd01,String pwd02)
	 * @Description 判断密码是否正确
	 * @param username  用户名
	 * @param encodePwd 用户加密后的密码
	 * @param pwd       用户输入的密码
	 * @return
	 * @author dy
	 * @date 2017年11月25日
	 */
	public static boolean inspectPwd(String encodePwd, String pwd) {
		if (encodePwd != null && !encodePwd.equals("") && encodePwd.length()==32 && pwd != null && !pwd.equals("")) {
			int remainder = pwd.length() % 10;
			pwd = generatePassword(pwd);
			if (pwd.length() % 2 == 0) {
				if (encodePwd.subSequence(0, 32 - remainder).equals(pwd.substring(0, 32 - remainder)))
					return true;
			} else {
				if (encodePwd.subSequence(remainder, 32).equals(pwd.substring(remainder, 32)))
					return true;
			}
		}
		return false;
	}
}
