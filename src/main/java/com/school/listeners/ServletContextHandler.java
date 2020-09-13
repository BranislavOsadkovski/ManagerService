package com.school.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration; 

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;  

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread; 
import com.school.util.ApplicationManager;
import com.school.util.StudentJDBCTemplate;
 

public class ServletContextHandler implements ServletContextListener {
	 
	private StudentJDBCTemplate studentTemplate;

	 
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		this.studentTemplate = (StudentJDBCTemplate)ApplicationManager.getSpringAppContext().getBean("studentJDBCTemplate");
		context.setAttribute("studentTemplate", studentTemplate);
		System.out.println("ServletContextInit");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		 Enumeration<Driver> drivers = DriverManager.getDrivers();     

	        Driver driver = null;

	        // clear drivers
	        while(drivers.hasMoreElements()) {
	            try {
	                driver = drivers.nextElement();
	                DriverManager.deregisterDriver(driver);

	            } catch (SQLException ex) {
	                // deregistration failed, might want to do something, log at the very least
	            }
	        } 
	        try { 
	        	AbandonedConnectionCleanupThread.uncheckedShutdown();
	        }catch(Exception e){e.printStackTrace();}
	}    
	
}
