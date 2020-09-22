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
 

public class ServletContextHandler implements ServletContextListener {
	 
	private StudentJDBCTemplate studentTemplate;
	private ServletContext context;
	final static Logger logger = Logger.getLogger(ServletContextHandler.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();
		logger.info("Context initialized");
		try {
			logger.warn("Initializing Database Connection");
			studentTemplate = (StudentJDBCTemplate)ApplicationManager.getSpringAppContext().getBean("studentJDBCTemplate");
		}catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		
		context.setAttribute("studentJDBCtemplate", studentTemplate); 	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
			logger.info("Destroying Context");
			
			try {
				logger.info("Closing Resources");
			ApplicationManager.closeSpringApplicationContext();
			
			}catch(Exception ex) {
				logger.error(ex.getMessage());
			}
		 Enumeration<Driver> drivers = DriverManager.getDrivers();     

	        Driver driver = null;

	        // clear drivers
	        while(drivers.hasMoreElements()) {
	            try {
	                driver = drivers.nextElement();
	                DriverManager.deregisterDriver(driver);

	            } catch (SQLException ex) {
	            	logger.info(ex.getMessage());
	            }
	        } 
	        try { 
	        	AbandonedConnectionCleanupThread.uncheckedShutdown();
	        }catch(Exception e){logger.error(e.getMessage());}
	}    
	
}
