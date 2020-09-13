package com.school.util;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
	@Override
	@Autowired(required = true)
	public void setDataSource(DataSource data) {
		this.dataSource=data;
		this.jdbcTemplate=  new JdbcTemplate(data);
		this.jdbcInsert = new SimpleJdbcInsert(data).withTableName("student");
	}

	@Override
	public void create(String name, Integer age,String email, byte [] image) {
//		String SQL = "insert into student (name,age,email,image) values (?,?,?,?)";
//		jdbcTemplate.update(SQL,name,age,email,image);
		Map <String,Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("age", age);
		parameters.put("email", email);
		parameters.put("image", image);
		jdbcInsert.execute(parameters);
		
		
	}
	@Override
	public List<Student> getAllStudents(){

		String SQL = "select * from student;";
 		List<Student> list = jdbcTemplate.query(SQL,new StudentMapper());
		return list;
	}
	@Override
	public Student getStudent(Integer id) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("getRecord");
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", id);
		Map<String,Object> out = jdbcCall.execute(in);
		Student student = new Student();
		student.setId(id);
		student.setName((String)out.get("out_name"));
		student.setAge((Integer)out.get("out_age"));
		student.setEmail((String)out.get("out_email"));
		student.setImage((byte[])out.get("out_image"));
		return student;
	}

	@Override
	public void updateStudent(Integer id, String name, Integer age) {
		String SQL = "update student set name=?,age=? where id = ?";
		jdbcTemplate.update(SQL,name,age,id);
		
	}

	@Override
	public void deleteStudent(Integer id) {
		String SQL = "delete from student where id = ?";
		jdbcTemplate.update(SQL,id);
	}
	
	@Override
	public String getStudentName(Integer id) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withFunctionName("get_student_name");
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", id);
		String name = jdbcCall.executeFunction(String.class, in);
		return name;
	}
	@Override
	public void setStudentImage(Integer id,byte [] image) {
		
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("id", id);
		in.addValue("image", new SqlLobValue(new ByteArrayInputStream(image),image.length, new DefaultLobHandler()),Types.BLOB);
		
		String SQL = "update student set image=:image where id= :id";
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplateObject.update(SQL, in);
		
	}
	
	@Override
	public void executeBatchUpdate(final List<Student> students) {
		
		String SQL= "update student set age=? where id=?";
		int [] updateCounts = jdbcTemplate.batchUpdate(SQL,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1	, students.get(i).getAge());
					ps.setInt(2, students.get(i).getId());				
			}
			
			@Override
			public int getBatchSize() {
				return students.size();
			}
		});
		System.out.println("Batch :" + updateCounts.length);
		
	}
	@Override
	public void executeBatchObjectUpdate(final List<Student> students){
		String SQL =  "update student set age =:age where id = :id";
		SqlParameterSource [] batch = SqlParameterSourceUtils.createBatch(students.toArray());
		NamedParameterJdbcTemplate templateObject = new NamedParameterJdbcTemplate(dataSource);
		int [] updateCounts = templateObject.batchUpdate(SQL, batch);
		System.out.println("Batch :" + updateCounts.length);
	}
}
