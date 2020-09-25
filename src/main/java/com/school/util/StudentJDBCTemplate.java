package com.school.util;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import com.school.interfaces.StudentDAOInterface;
import com.school.objects.Student;

public class StudentJDBCTemplate implements StudentDAOInterface {
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	SimpleJdbcInsert jdbcInsert; // multi-threaded reusable object providing easy insert capabilities for a table
	final static Logger logger = Logger.getLogger(StudentJDBCTemplate.class);

	@Override
	@Autowired(required = true)
	public void setDataSource(DataSource data) {
		this.dataSource = data;
		this.jdbcTemplate = new JdbcTemplate(data);
		this.jdbcInsert = new SimpleJdbcInsert(data).withTableName("student");
	}

	@Override
	public DataSource getDataSource() {
		return this.dataSource;
	}

	@Override
	public void create(String name, Integer age, String email, byte[] image) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("age", age);
		parameters.put("email", email);
		parameters.put("image", image);
		try {
			jdbcInsert.execute(parameters);
			logger.info("Saved new record into database");

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

	@Override
	public Student getStudent(Integer id) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("getRecord");
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", id);
		Map<String, Object> out = null;
		Student student = new Student();
		try {
			out = jdbcCall.execute(in);	
			student.setId(id);
			student.setName((String) out.get("out_name"));
			student.setAge((Integer) out.get("out_age"));
			student.setEmail((String) out.get("out_email"));
			student.setImage((byte[]) out.get("out_image")); 
			logger.info("Found record from database by id");
		} catch (Exception ex) {
			 logger.error(ex.getMessage(), ex);	
		}   
		return student;
	}
 
	@Override
	public Student getDBStudentByName(String name) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("getRecordByName");
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_name", name);
		Map<String, Object> out = null;
		try {
			out = jdbcCall.execute(in);
			logger.info("Found record from database by name");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		
		Student student = new Student();
		student.setId((Integer) out.get("out_id"));
		student.setName(name);
		student.setAge((Integer) out.get("out_age"));
		student.setEmail((String) out.get("out_email"));
		student.setImage((byte[]) out.get("out_image"));
		return student;

	}

	@Override
	public void updateStudent(Integer id, String name, Integer age, String email, byte[] image) {
		String SQL = "update student set name=?,age=?,email=?,image=? where id = ?";
		try {
		jdbcTemplate.update(SQL, name, age, email, image, id);
		logger.info("Updated record in dabatase");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
	
	}

	@Override
	public void deleteStudent(Integer id) {
		String SQL = "delete from student where id = ?";
		try {
			jdbcTemplate.update(SQL, id);
			logger.info("Deleted record from database");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		
	}

	@Override
	public byte[] getStudentImage(Integer id) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withFunctionName("get_student_image");
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", id);
		byte[] image = null;
		try {
			image = (byte[]) jdbcCall.executeFunction(Object.class, in);
			logger.info("Uploaded image from database");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		
		return image;
	}

	@Override
	public void setStudentImage(Integer id, byte[] image) {

		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("id", id);
		in.addValue("image", new SqlLobValue(new ByteArrayInputStream(image), image.length, new DefaultLobHandler()),
				Types.BLOB);

		String SQL = "update student set image=:image where id= :id";
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
		try {
				jdbcTemplateObject.update(SQL, in);
				logger.info("Saved image to database");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	
	
	}

	@Override
	public List<Student> getAllStudents() {

		String SQL = "select * from student;";
		List<Student> list =null;
		try {
			list = jdbcTemplate.query(SQL, new StudentMapper());
			logger.info("Selected all records from database");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	
		return list;

	}

	@Override
	public void executeBatchObjectUpdate(final List<Student> students) {
		String SQL = "update student set name=:name,age=:age,email=:email,image=:image where id=:id";
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(students.toArray());
		NamedParameterJdbcTemplate templateObject = new NamedParameterJdbcTemplate(dataSource);
		try {
			templateObject.batchUpdate(SQL, batch);
			logger.info("Executed large BATCH update; BATCH size=" + students.size());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
	}

}
