package com.school.util;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationManager manages database connection and Spring DI
 * ClassPathXmlApplicationContext configuration.
 * 
 * @author Branislav
 *
 */
public class ApplicationManager {
	private static ConfigurableApplicationContext springAppContext;
	private static Logger logger = Logger.getLogger(ApplicationManager.class);

	/**
	 * Connects to database by injecting Spring Beans configuration from Beans.xml
	 * file
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
	}
}
