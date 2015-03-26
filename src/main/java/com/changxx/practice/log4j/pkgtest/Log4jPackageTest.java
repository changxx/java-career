package com.changxx.practice.log4j.pkgtest;

import org.apache.log4j.Logger;

/**
 * 包名字输出日志
 * @author changxx
 *
 */
public class Log4jPackageTest {
	private static Logger log = Logger.getLogger(Log4jPackageTest.class);

	public static void main(String[] args) {
		log.debug("log package debug");
		log.warn("log package warn");
		log.info("log package info");
		log.error("log package error");
	}
}
