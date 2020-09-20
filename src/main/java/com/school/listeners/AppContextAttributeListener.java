package com.school.listeners;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.apache.log4j.Logger;

public class AppContextAttributeListener implements ServletContextAttributeListener {
	private static final Logger logger = Logger.getLogger(AppContextAttributeListener.class);
	@Override
	public void attributeAdded(ServletContextAttributeEvent scae) {
		String name =(String)scae.getName();
		String strValue =scae.getValue().toString();
		logger.info("ServletContext attribute ADDED : + {"+name+" : "+strValue+"}");
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent scae) {
		String name =(String)scae.getName();
		String strValue =scae.getValue().toString();
		logger.info("ServletContext attribute REMOVED : + {"+name+" : "+strValue+"}"); 
		
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent scae) {
		String name =(String)scae.getName();
		String strValue =scae.getValue().toString();
		logger.info("ServletContext attribute REPLACED : + {"+name+" : "+strValue+"}");
	}

}
