package com.school.controllers;
 
import java.io.BufferedOutputStream; 
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.school.objects.Student; 
import com.school.service.StudentService; 

/**
 * StudentService class is a Web Service class and its methods receive
 * HttpRequests on paths mapped by javax.ws.rs.Path annotations and send
 * HttpResponse to client after they are processed
 * 
 * @author Branislav
 *
 */
@Path(value = "studentservice")
public class StudentController {
	final static Logger logger = Logger.getLogger(StudentController.class);
	private Student student;
	private List<Student> list; 
	private HttpServletResponse response;  
 	private StudentService studentService; 
 	
	@Autowired(required = true)
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
 
	/**
	 * @param studentService must not be null
	 */  
	@POST
	@Path(value = "newstudent")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response createStudent(@FormDataParam(value = "name") String name, 
			@FormDataParam(value = "age") String age,
			@FormDataParam(value = "email") String email, 
			@FormDataParam(value = "image") InputStream stream) {

		if (studentService.createStudent(name, age, email, studentService.imageBytes(stream))) {

			return Response.ok().build();
			
		} 
		
		return Response.serverError().build();
	}

	@GET
	@Path(value = "student/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getStudent(@PathParam(value = "id") String id) {
		student = studentService.getStudent(id);

		if (student != null) {
			return Response.ok(student).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


	@GET
	@Path(value = "student/name/{name}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getStudentByName(@PathParam(value = "name") String name) {
		student = studentService.getStudentByName(name);

		if (student != null) {
			return Response.ok(student).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	
	@PUT
	@Path(value = "student")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateStudent(@FormDataParam(value = "id") String id, @FormDataParam(value = "name") String name,
			@FormDataParam(value = "age") String age, @FormDataParam(value = "email") String email,
			@FormDataParam(value = "image") InputStream stream) {

		studentService.updateStudent(id, name, age, email);

		return Response.ok().build();
	}

	
	@DELETE
	@Path(value = "student")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response deleteStudent(@FormDataParam(value = "id") String id) {

		studentService.deleteStudent(id);

		return Response.ok().build();
	}

	 
	@GET
	@Path(value = "{id}/studentimage")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getStudentImage(@PathParam(value = "id") String id) {
		Response r = null;

		byte[] imageBytes = new byte[1024];

		try {
			imageBytes = studentService.getStudentImage(id);

			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

			if (imageBytes == null) {
				r = Response.status(Response.Status.NO_CONTENT).build();
			} else if (imageBytes != null && imageBytes.length > 0) {
				r = Response.ok(imageBytes, MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
				bos.write(imageBytes);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return r;
	}

	@PUT
	@Path(value = "studentimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response setStudentImage(@FormDataParam(value = "id") String id,
			@FormDataParam(value = "image") InputStream stream) {

		studentService.setImage(id, studentService.imageBytes(stream));

		return Response.ok().build();
	}

	
	@GET
	@Path(value = "students")
	@Produces(MediaType.APPLICATION_XML)
	public List<Student> getAllStudents() {
		try {
			list = studentService.getAllRecords();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;

	}

	@PUT
	@Path(value = "students")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response batchUpdate(@FormDataParam(value = "batch") List<Student> batch) {

		studentService.batchUpdate(batch);
		return Response.ok().build();
	}

	
	

}
