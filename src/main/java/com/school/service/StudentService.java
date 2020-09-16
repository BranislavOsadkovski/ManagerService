package com.school.service;

import java.io.BufferedInputStream; 
import java.io.IOException;
import java.io.InputStream; 
import java.util.List; 

import javax.inject.Inject;
import javax.servlet.ServletException;
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
 
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.school.objects.Student;
import com.school.util.StudentJDBCTemplate;
 
 

@Path(value = "StudentService")
public class StudentService  {
	 private List <Student> list;
	 @Inject HttpServletRequest request;
	 @Inject HttpServletResponse response;
	 private StudentJDBCTemplate template;
 
	
	 
	 @POST
	 @Path(value="newstudent")   
	 @Consumes(MediaType.MULTIPART_FORM_DATA)
	 public String createStudent(@FormDataParam(value="name")String name,
								 @FormDataParam (value="age") Integer age,
								 @FormDataParam(value="email")String email,
								 @FormDataParam(value="image")InputStream stream) {
		
		 template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		 template.create(name, age, email, imageBytes(stream));
		 System.out.println(name+age);
		 return name;
	} 
	 
	@GET
	@Path(value="student/{id}") 
	@Produces(MediaType.APPLICATION_XML)
	public Student getStudent(@PathParam(value = "id")Integer id) {
	 
		template = (StudentJDBCTemplate)request.getServletContext().getAttribute("studentJDBCtemplate"); 
		Student s = template.getStudent(id);
		return s;
	}
	
	@GET
	@Path(value="student/{name: [a-zA-Z][a-zA-Z_0-9]*}") 
	@Produces(MediaType.APPLICATION_XML)
	public Student getStudentByName(@PathParam(value = "name")String name) {
		template = (StudentJDBCTemplate)request.getServletContext().getAttribute("studentJDBCtemplate"); 
		Student s = template.getStudentByName(name);
		return s;
	}
	
	@PUT
	@Path(value="student")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateStudent(@FormDataParam(value="id")Integer id,
									@FormDataParam(value="name")String name, 
									@FormDataParam(value="age")Integer age,
									@FormDataParam(value="email")String email,
									@FormDataParam(value="image")InputStream stream) {
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		template.updateStudent(id, name, age, email, imageBytes(stream));
		return Response.ok().build();
	}
	@DELETE
	@Path(value="student")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response deleteStudent(@FormDataParam(value="id")Integer id) {
		template = (StudentJDBCTemplate) request.getServletContext().getAttribute("studentJDBCtemplate");
		template.deleteStudent(id);
		return Response.ok().build();
	}
	
	@GET
	@Path(value="{id}/studentImage") 
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getStudentImage(@PathParam(value = "id")Integer id) throws IOException {
			 Response r = null;
			template = (StudentJDBCTemplate)request.getServletContext().getAttribute("studentJDBCtemplate"); 
			byte[] image = template.getStudentImage(id);
			
			if(image==null) { 
			r=  Response.status(Response.Status.NO_CONTENT).build();  
			}else if(image!=null&&image.length>0) {
			r= Response.ok(image,MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
			}
			return r;
			 
	}
	
	@PUT
	@Path(value="setstudentimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response setStudentImage(@FormDataParam(value="id")Integer id,@FormDataParam(value="image")InputStream stream) throws IOException, ServletException {
	
		template = (StudentJDBCTemplate)request.getServletContext().getAttribute("studentJDBCtemplate"); 
	 
		template.setStudentImage(id, imageBytes(stream)); 
		 
		return Response.ok().build();
	}
	
	@GET
	@Path(value="getallstudents")
	@Produces(MediaType.APPLICATION_XML)
	public List<Student> getAllStudents() {
		 list=null; 
		 template = (StudentJDBCTemplate)request.getServletContext().getAttribute("studentJDBCtemplate"); 
				list= template.getAllStudents();
				return list;
		
	}
	public Response batchUpdate() {
		
		return Response.ok().build();
	}
	public Response multipleBatchUpdate() {
		
		return Response.ok().build();
	}
	public static byte[] imageBytes(InputStream inputStream ) {
		StringBuffer img= new StringBuffer();
		
		try(BufferedInputStream  bos = new BufferedInputStream(inputStream)){
			
			int content = bos.read();
			while(content !=-1) {
				img.append((char)content);
				content=bos.read();
			}
		
			}catch(Exception  ex) {ex.printStackTrace();}		 
		byte[] arr = img.toString().getBytes();
		return arr;
		
	}
	
}
