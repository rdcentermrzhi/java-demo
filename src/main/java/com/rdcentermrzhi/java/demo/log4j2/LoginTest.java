package com.rdcentermrzhi.java.demo.log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class LoginTest {
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

	public static void main(final String... args) {

		for (int i = 0; i < 1; i++) {
			logger.info("你好 {} {}", "current id = ", i);
			System.err.println(i);
		}

		// Set up a simple configuration that logs on the console.
		FormattingTuple  tpl =  MessageFormatter.arrayFormat("你好 {} {}", new String[] {"12","ad"});
		System.out.println(tpl.getMessage());
	}
}
