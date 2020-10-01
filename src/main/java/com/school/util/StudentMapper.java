package com.school.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.school.objects.Student;

/**
 * StudentMapper is used for JdbcTemplate's for mapping rows for query methods
 * or for out parameters of stored procedures.
 * 
 * @author Branislav
 *
 */
public class StudentMapper implements RowMapper<Student> {

	/**
	 * Maps each row of Student object to a result object. Throws SQLException.
	 * 
	 * @param rs
	 * @param rowNum
	 * @return student
	 */
	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student student = new Student();
		student.setId(rs.getInt("id"));
		student.setName(rs.getString("name"));
		student.setAge(rs.getInt("age"));
		student.setEmail(rs.getString("email"));
		student.setImage(rs.getBytes("image"));

		return student;
	}

}
