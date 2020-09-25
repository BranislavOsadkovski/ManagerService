package com.school.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
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

import com.school.objects.Student;
import com.school.util.StudentJDBCTemplate;
import com.school.validations.StudentException;
import com.school.validations.StudentValidator;

@Singleton
@Path(value = "StudentService")
public class StudentService {
	Student student;
	private List<Student> list;
	private @Inject HttpServletRequest request;
	private @Inject HttpServletResponse response;

	private StudentJDBCTemplate template;
	final static Logger logger = Logger.getLogger(StudentService.class);

	@POST
	@Path(value = "newstudent")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response createStudent(@FormDataParam(value = "name") String name, @FormDataParam(value = "age") String age,
			@FormDataParam(value = "email") String email, @FormDataParam(value = "image") InputStream stream) {
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");

		try {
			if (StudentValidator.validateStudent(name, age, email)) {
				
				template.create(name, Integer.valueOf(age), email, imageBytes(stream));
			}
		} catch (StudentException e) {
			logger.error(e.getMessage(), e);
			try {
				response.sendRedirect("/school/index.jsp");
			} catch (IOException io) {
				logger.error(io.getMessage(), io);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return Response.ok().build();
	}

	@GET
	@Path(value = "student/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getStudent(@PathParam(value = "id") String id) {
		student = null;
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");

		try {
			if (StudentValidator.validateId(id)) { 
				student = template.getStudent(Integer.valueOf(id));
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (student != null) {
			return Response.ok(student).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path(value = "student/{name}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getStudentByName(@PathParam(value = "name") String name) {
		student = null;
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");

		try {
			if (StudentValidator.validatePathName(name)) {
			
				student = template.getStudentByName(name);
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
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
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");

		try {
			if (StudentValidator.validateStudent(id, name, age, email)) {
				
				template.updateStudent(Integer.valueOf(id), name, Integer.valueOf(age), email, imageBytes(stream));
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Response.ok().build();
	}

	@DELETE
	@Path(value = "student")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response deleteStudent(@FormDataParam(value = "id") String id) {

		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try {
			if (StudentValidator.validateId(id)) {
				
				template.deleteStudent(Integer.valueOf(id));
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Response.ok().build();
	}

	@GET
	@Path(value = "{id}/studentImage")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getStudentImage(@PathParam(value = "id") String id) {
		Response r = null;
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		byte[] image = new byte[1024];

		try {
			if (StudentValidator.validateId(id)) {
			
				image = template.getStudentImage(Integer.valueOf(id));
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (image == null) {
			r = Response.status(Response.Status.NO_CONTENT).build();
		} else if (image != null && image.length > 0) {
			r = Response.ok(image, MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
		}
		return r;

	}

	@PUT
	@Path(value = "setstudentimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response setStudentImage(@FormDataParam(value = "id") String id,
			@FormDataParam(value = "image") InputStream stream) {

		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try {
			if (StudentValidator.validateId(id)) {
				if (imageBytes(stream).length > 0) {
					
					template.setStudentImage(Integer.valueOf(id), imageBytes(stream));
				} else {
					throw new NullPointerException("No image found;");
				}
			}
		} catch (StudentException se) {
			logger.error(se.getMessage(), se);
		} catch (NullPointerException npe) {
			logger.error(npe.getMessage(), npe);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Response.ok().build();
	}

	@GET
	@Path(value = "students")
	@Produces(MediaType.APPLICATION_XML)
	public List<Student> getAllStudents() {

		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try {
			
			list = template.getAllStudents();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;

	}

	@PUT
	@Path(value = "students")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response batchUpdate(@FormDataParam(value = "batch") List<Student> batch) {

		List<Student> students = batch;
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		boolean valid = false;
		for (Student s : students) {
			try {
				valid = StudentValidator.validateStudent(String.valueOf(s.getId()), s.getName(),
						String.valueOf(s.getAge()), s.getEmail());
			} catch (StudentException e) {
				logger.error(e.getMessage(), e);
			}
		}
		try {
			if (valid) {
				
				template.executeBatchObjectUpdate(students);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Response.ok().build();
	}

	public static byte[] imageBytes(InputStream inputStream) {
		StringBuffer img = new StringBuffer();

		try (BufferedInputStream bos = new BufferedInputStream(inputStream)) {

			int content = bos.read();
			while (content != -1) {
				img.append((char) content);
				content = bos.read();
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		byte[] arr = img.toString().getBytes();
		return arr;
	}

}
