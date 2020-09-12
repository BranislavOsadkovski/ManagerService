package com.school.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;  

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.school.objects.Student;
import com.school.util.ApplicationManager;
import com.school.util.StudentJDBCTemplate;
 

public class ServletContextHandler implements ServletContextListener {
	 
	private StudentJDBCTemplate studentTemplate;

	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		this.studentTemplate = (StudentJDBCTemplate)ApplicationManager.getSpringAppContext().getBean("studentJDBCTemplate");
		context.setAttribute("studentTemplate", studentTemplate);

		List<Student> list = studentTemplate.getAllStudents();
		for(Student s : list ) {
			System.out.printf("id: %s name: %s age: %s email: %s image: %s \n",s.getId(),s.getName(),s.getAge(),s.getEmail(),s.getImage());
		}
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
