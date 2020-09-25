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
	 
	private StudentJDBCTemplate studentJDBCTemplate;
	private ServletContext context;
	final static Logger logger = Logger.getLogger(ServletContextHandler.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();
		logger.info("Context initialized");
		 
			logger.warn("Initializing Database Connection");
			studentJDBCTemplate = (StudentJDBCTemplate)ApplicationManager.getSpringAppContext().getBean("studentJDBCTemplate");
	 
		context.setAttribute("studentJDBCtemplate", studentJDBCTemplate); 	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
			logger.info("Destroying Context");
			
			try {
				logger.info("Closing Resources");
			ApplicationManager.closeSpringApplicationContext();
			
			}catch(Exception ex) {
				logger.error(ex.getMessage(),ex);
			}
		 Enumeration<Driver> drivers = DriverManager.getDrivers();     

	        Driver driver = null;

	        // clear drivers
	        while(drivers.hasMoreElements()) {
	            try {
	                driver = drivers.nextElement();
	                DriverManager.deregisterDriver(driver);

	            } catch (SQLException ex) {
	            	logger.error(ex.getMessage(),ex);
	            }
	        } 
	        try { 
	        	AbandonedConnectionCleanupThread.uncheckedShutdown();
	        }catch(Exception e){logger.error(e.getMessage(),e);}
	}    
	
}
