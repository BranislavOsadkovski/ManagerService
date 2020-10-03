package com.school.util;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationManager manages database connection and injects
 * org.springframework.context.ConfigurableApplicationContext configuration.
 * 
 * @author Branislav
 *
 */

public class ApplicationManager {
	private static ConfigurableApplicationContext springAppContext;
	private static final Logger logger = Logger.getLogger(ApplicationManager.class);

	/**
	 * Connects to database by injecting Spring Beans configuration from file
	 * 
	 * @return springAppContext
	 */
	public static ConfigurableApplicationContext getSpringAppContext() {
		if (springAppContext == null) {

			try {
				logger.warn("Injecting Beans configuration. ");
				springAppContext = new ClassPathXmlApplicationContext("Beans.xml");
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
			return springAppContext;
		} else {
			return springAppContext;
		}
	}

	/**
	 * Close database connection
	 */
	public static void closeSpringApplicationContext() {
		logger.warn("Closing database connection");
		springAppContext.close();
		if (!springAppContext.isRunning()) {
			logger.info("Database disconnected");
		}
	}
}
