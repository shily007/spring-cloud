package com.sc.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
	
	private static Logger logger = LoggerFactory.getLogger(Object.class);
	
	public static void info(String msg) {
		logger.info(msg);
	}
	
	public static void debug(String msg) {
		logger.debug(msg);
	}
	
	public static void error(String msg) {
		logger.error(msg);
	}
	
	public static void warn(String msg) {
		logger.warn(msg);
	}
	
	public static void trace(String msg) {
		logger.trace(msg);
	}

}
