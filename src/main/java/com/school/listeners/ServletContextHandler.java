package com.school.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
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
	final static Logger logger = Logger.getLogger(ServletContextHandler.class);

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

		logger.warn("Initializing Database Connection");
		studentJDBCTemplate = (StudentJDBCTemplate) ApplicationManager.getSpringAppContext()
				.getBean("studentJDBCTemplate");

		context.setAttribute("studentJDBCtemplate", studentJDBCTemplate);
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

		try {
			logger.info("Closing Resources");
			ApplicationManager.closeSpringApplicationContext();

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		Enumeration<Driver> drivers = DriverManager.getDrivers();

		Driver driver = null;

		// clear drivers
		while (drivers.hasMoreElements()) {
			try {
				driver = drivers.nextElement();
				DriverManager.deregisterDriver(driver);

			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		try {
			AbandonedConnectionCleanupThread.uncheckedShutdown();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
