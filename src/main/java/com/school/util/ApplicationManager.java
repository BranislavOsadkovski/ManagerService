package com.school.util;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationManager { 
	private static ConfigurableApplicationContext springAppContext;
	private static Logger logger  = Logger.getLogger(ApplicationManager.class);
	public static ConfigurableApplicationContext getSpringAppContext() {
		if(springAppContext==null) {
	
			 try {
				 logger.warn("Injecting Beans configuration. ");
				 springAppContext = new ClassPathXmlApplicationContext("Beans.xml"); 
			 }catch(Exception ex) {
				 logger.error(ex.getMessage(),ex);
			 }
			 	return springAppContext;
			}else {
				return springAppContext;
		}
	}
	
	public static void closeSpringApplicationContext() {
		logger.warn("Closing database connection");
		springAppContext.close();
	}
}
