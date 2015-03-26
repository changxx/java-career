package com.changxx.practice.log4j;

import org.apache.log4j.Logger;

public class Log4jTest {
	private static Logger log = Logger.getLogger(Log4jTest.class);

	public static void main(String[] args) {
		log.debug("log debug");
		log.warn("log warn");
		log.info("log info");
		log.error("log error");
	}
}
