package com.school.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationManager { 
	private static ConfigurableApplicationContext springAppContext;
	
	public static ConfigurableApplicationContext getSpringAppContext() {
		if(springAppContext==null) {
		     springAppContext = new ClassPathXmlApplicationContext("Beans.xml");
		   
			return springAppContext;
			}else {
				return springAppContext;
		}
	}
	
	public static void closeSpringApplicationContext() {
		springAppContext.close();
	}
}
