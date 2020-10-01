package com.school.listeners;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.apache.log4j.Logger;

/**
 * Gets notified by ServletContextAttributeEvent when ServletContext Attributes
 * are changed and log it
 * 
 * @author Branislav
 *
 */
public class AppContextAttributeListener implements ServletContextAttributeListener {
	private static final Logger logger = Logger.getLogger(AppContextAttributeListener.class);

	/**
	 * Gets notified by ServletContextAttribute Event that an attribute has been
	 * added to the ServletContext.
	 */
	@Override
	public void attributeAdded(ServletContextAttributeEvent scae) {
		String name = (String) scae.getName();
		String strValue = scae.getValue().toString();
		logger.info("ServletContext attribute ADDED : + {" + name + " : " + strValue + "}");
	}

	/**
	 * Gets notified by ServletContextAttribute Event that an attribute has been
	 * removed from the ServletContext.
	 */
	@Override
	public void attributeRemoved(ServletContextAttributeEvent scae) {
		String name = (String) scae.getName();
		String strValue = scae.getValue().toString();
		logger.info("ServletContext attribute REMOVED : + {" + name + " : " + strValue + "}");

	}

	/**
	 * Gets notified by ServletContextAttribute Event that an attribute has been
	 * replaced from the ServletContext.
	 */
	@Override
	public void attributeReplaced(ServletContextAttributeEvent scae) {
		String name = (String) scae.getName();
		String strValue = scae.getValue().toString();
		logger.info("ServletContext attribute REPLACED : + {" + name + " : " + strValue + "}");
	}

}
