package com.school.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
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

@Singleton
@Path(value = "StudentService")
public class StudentService {
	Student student;
	private List<Student> list;
	private @Inject HttpServletRequest request;
	private StudentJDBCTemplate template;
	final static Logger logger = Logger.getLogger(StudentService.class);

	@POST
	@Path(value = "newstudent")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String createStudent(@FormDataParam(value = "name") String name, @FormDataParam(value = "age") Integer age,
			@FormDataParam(value = "email") String email, @FormDataParam(value = "image") InputStream stream) {
		logger.info("POST parameters={name:" + name + ",age:" + age + ",email:" + email + ",stream:"+ stream.hashCode()+ "}");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
	
		try {
			logger.warn("Inserting new record into database");
			template.create(name, age, email, imageBytes(stream));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return name;
	}

	@GET
	@Path(value = "student/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Student getStudent(@PathParam(value = "id") Integer id) {
		logger.info("GET parameters={id:" + id + "}");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
	
		try {
			logger.warn("Geting record from database");
			student = template.getStudent(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return student;
	}

	@GET
	@Path(value = "student/{name: [a-zA-Z][a-zA-Z_0-9]*}")
	@Produces(MediaType.APPLICATION_XML)
	public Student getStudentByName(@PathParam(value = "name") String name) {
		logger.info("GET parameters={name;" + name + "}");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
	
		try {
			logger.warn("Geting record from database");
			student = template.getStudentByName(name);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return student;
	}

	@PUT
	@Path(value = "student")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateStudent(@FormDataParam(value = "id") Integer id, @FormDataParam(value = "name") String name,
			@FormDataParam(value = "age") Integer age, @FormDataParam(value = "email") String email,
			@FormDataParam(value = "image") InputStream stream) {
		logger.info("PUT parameters={id:" + id + ",name:" + name + ",age:" + age + ",email:" + email + ",stream:"+ stream.hashCode() + "}");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try {
			logger.warn("Updating record in dabatase");
			template.updateStudent(id, name, age, email, imageBytes(stream));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return Response.ok().build();
	}

	@DELETE
	@Path(value = "student")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response deleteStudent(@FormDataParam(value = "id") Integer id) {
		logger.info("DELETE parameters={id:"+id+"}");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try{
			logger.warn("Deleting record from database");
			template.deleteStudent(id);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return Response.ok().build();
	}

	@GET
	@Path(value = "{id}/studentImage")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getStudentImage(@PathParam(value = "id") Integer id) {
		Response r = null;
		logger.info("GET parameters={id:"+id+"}");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		byte[] image = new byte[1024]; 
				try {
					logger.warn("Getting record image from database");
					image = template.getStudentImage(id); 
				}catch(Exception e) {
					logger.error(e.getMessage());
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
	public Response setStudentImage(@FormDataParam(value = "id") Integer id,
			@FormDataParam(value = "image") InputStream stream) {
		logger.info("PUT parameters={id:"+id+"}");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try { 
			logger.warn("Setting record image to database");
			template.setStudentImage(id, imageBytes(stream));
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return Response.ok().build();
	}

	@GET
	@Path(value = "students")
	@Produces(MediaType.APPLICATION_XML)
	public List<Student> getAllStudents() { 
		logger.info("GET");
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try {
			logger.warn("Geting all records from database");
			list = template.getAllStudents();
		
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return list;

	}

	@PUT
	@Path(value = "students")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response batchUpdate(@FormDataParam(value = "batch") List<Student> batch) {
		logger.info("PUT large update; size="+batch.size());
		List<Student> students = batch;
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		try { 
			logger.warn("Executing large BATCH update; BATCH size="+list.size()); 
			template.executeBatchObjectUpdate(students); 
		}catch(Exception e) {
			logger.error(e.getMessage());
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
			logger.error(ex.getMessage());
		}
		byte[] arr = img.toString().getBytes();
		return arr; 
	}

}
