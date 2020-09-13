package com.school.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.school.objects.Student;
import com.school.util.StudentJDBCTemplate;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

@Path(value = "StudentService")
public class StudentService {
	 private List <Student> list;
	 @Inject HttpServletRequest request;
	 private StudentJDBCTemplate template;
	@GET
	@Path(value="getallstudents")
	@Produces(MediaType.APPLICATION_XML)
	public List<Student> getAllStudents() {
		 list=null; 
		 template = (StudentJDBCTemplate)request.getServletContext().getAttribute("studentTemplate"); 
				list= template.getAllStudents();
	return list;
		
	}
	
	@GET
	@Path(value="student/{id}") 
	@Produces(MediaType.APPLICATION_XML)
	public Student getStudent(@PathParam(value = "id")Integer id) {
		template = (StudentJDBCTemplate)request.getServletContext().getAttribute("studentTemplate"); 
		Student s = template.getStudent(id);
		return s;
	}
	
}
