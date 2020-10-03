package com.school.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import com.school.util.ApplicationManager;
import com.school.util.StudentJDBCTemplate;

/**
 * ServletContextHandler receives notification events about ServletContext
 * lifecycle changes. Loads initial parameters from xml file and connects to
 * database
 * 
 * @author Branislav
 *
 */
public class ServletContextHandler implements ServletContextListener {

	private StudentJDBCTemplate studentJDBCTemplate;
	private ServletContext context;
	private final static Logger logger = Logger.getLogger(ServletContextHandler.class);

	/**
	 * Gets notified by ServletContextEvent that the web application initialization
	 * process is starting. Creates SpringJDBCTemplate Spring Bean from
	 * ApplicationManager and set SpringJDBCTemplate instance object as
	 * ServletContext attribute.
	 * 
	 * @param sce
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();
		logger.info("Context initialized");

		try {
			logger.warn("Initializing Database Connection");
			this.studentJDBCTemplate = (StudentJDBCTemplate) ApplicationManager.getSpringAppContext()
					.getBean("studentJDBCTemplate");

			context.setAttribute("studentJDBCtemplate", studentJDBCTemplate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Gets notified by ServletContextEvent that the ServletContext is about to be
	 * shut down.
	 * 
	 * @param sce
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Destroying Context");

		ApplicationManager.closeSpringApplicationContext();
	}

}
